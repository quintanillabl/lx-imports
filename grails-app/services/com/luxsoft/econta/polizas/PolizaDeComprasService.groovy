package com.luxsoft.econta.polizas


import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.CuentaDeGastos
import com.luxsoft.impapx.CuentaPorPagar
import com.luxsoft.impapx.Embarque
import com.luxsoft.impapx.EmbarqueDet
import com.luxsoft.impapx.FacturaDeImportacion
import com.luxsoft.impapx.GastosDeImportacion
import com.luxsoft.impapx.Pedimento
import com.luxsoft.impapx.TipoDeCambio
import com.luxsoft.impapx.contabilidad.*
import grails.transaction.Transactional
import com.luxsoft.lx.utils.MonedaUtils
import util.Rounding

@Transactional
class PolizaDeComprasService extends ProcesadorService{

	

	def procesar(Poliza poliza){
        poliza.descripcion = "Poliza de compras "
        log.info "Generando poliza de $poliza.subTipo para el ${poliza.fecha.text()}"
        def dia = poliza.fecha
        
        procearCuentaPorPagarMateriaPrima(poliza, dia)
        procearImpuestosAduanales(poliza, dia)
        proceaCuentaDeGastos(poliza, dia)
        procesarComplementos(poliza)
        cuadrar(poliza)
        depurar(poliza)
        save poliza
        
    }

    private procearCuentaPorPagarMateriaPrima(def poliza , def dia){
        
        def asiento="Cuenta por pagar"
        
        def embarques=EmbarqueDet.findAll ("from EmbarqueDet e where e.factura is not null and date(e.pedimento.fecha)=?",[dia])
        
        def data=[:]
        
        embarques.each{it->
            
            data[it.factura]=it
        }
        
        data.each{ entry->
            
           
            def factura=entry.key
            def embarqueDet=entry.value
            def pedimento=embarqueDet.pedimento
            def fechaF=factura.fecha.text()     
            def peds=EmbarqueDet.executeQuery('select d.pedimento.pedimento from EmbarqueDet d where d.factura=?',[factura]) as Set 
            def pedimentos=peds.join(',')   
            
            //Verificar si la factura es provisionada
            int periodoAnterior=dia.toYear()-1
            if(periodoAnterior==factura.provisionada){
                
                asiento="Cancelacion de Provision"
                
                //Cancelar provision
                // 1. Abono al inventario
                def cuenta=CuentaContable.buscarPorClave('115-0003')
                def fechaTc=factura.fecha-1
                def tipoDeCambioInstance=TipoDeCambio.find("from TipoDeCambio t where date(t.fecha)=? and t.monedaFuente=?",[fechaTc,factura.moneda])
                assert tipoDeCambioInstance,"Debe existir tipo de cambio para el :"+fechaTc.text()
                def tipoCambioPed=EmbarqueDet.executeQuery('select d.pedimento.tipoDeCambio from EmbarqueDet d where d.factura=?',[factura]).get(0)
                
                def valorFactTC=factura.importe*tipoDeCambioInstance.factor
                def valorProvTC=factura.importe*tipoCambioPed
                def variacionCambiaria=valorFactTC-valorProvTC
                poliza.addToPartidas(
                    cuenta:cuenta,
                    debe:0.0,
                    haber:valorFactTC,
                    asiento:asiento,
                    descripcion:"Canc provision $factura.proveedor ($fechaF) $factura.importe T.C:$tipoDeCambioInstance.factor",
                    referencia:"$factura.documento",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:'CuentaPorPagar'
                    ,origen:factura.id)
                // Cargo a proveedor
                def clave="201-$factura.proveedor.subCuentaOperativa"
                cuenta=CuentaContable.buscarPorClave(clave)
                poliza.addToPartidas(
                    cuenta:cuenta,
                    debe:valorProvTC,
                    haber:0.0,
                    asiento:asiento,
                    descripcion:"Canc provision $factura.proveedor ($fechaF) $factura.importe T.C:$tipoCambioPed",
                    referencia:"$factura.documento",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:'CuentaPorPagar'
                    ,origen:factura.id)
                if(variacionCambiaria>0){
                    cuenta=CuentaContable.buscarPorClave("701-0002")
                    poliza.addToPartidas(
                        cuenta:cuenta,
                        debe:variacionCambiaria,
                        haber:0.0,
                        asiento:asiento,
                        descripcion:"Canc prov variacion $factura.proveedor ($fechaF) ",
                        referencia:"$factura.documento",
                        ,fecha:poliza.fecha
                        ,tipo:poliza.tipo
                        ,entidad:'CuentaPorPagar'
                        ,origen:factura.id)
                }
                if(variacionCambiaria<0){
                    cuenta=CuentaContable.buscarPorClave("703-0003")
                    poliza.addToPartidas(
                        cuenta:cuenta,
                        debe:0.0,
                        haber:variacionCambiaria.abs(),
                        asiento:asiento,
                        descripcion:"Canc prov variacion $factura.proveedor ($fechaF)",
                        referencia:"$factura.documento",
                        ,fecha:poliza.fecha
                        ,tipo:poliza.tipo
                        ,entidad:'CuentaPorPagar'
                        ,origen:factura.id)
                }
            }
            
            asiento="Cuenta por pagar"
            
            // 1. Cargo al inventario
            //def cuenta=CuentaContable.findByClave('115-0001')
            def claveProv="502-$factura.proveedor.subCuentaOperativa"
            def cuenta = CuentaContable.findByClave(claveProv)
            if(cuenta==null){
                throw new RuntimeException("No existe la cuenta $claveProv")
            }
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:factura.importe*pedimento.tipoDeCambio,
                haber:0.0,
                asiento:asiento,
                descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
                //descripcion:"$factura.proveedor ($fechaF) $factura.importe  T.C. $pedimento.tipoDeCambio Ped:$pedimento.pedimento ",
                referencia:"$factura.documento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CuentaPorPagar'
                ,origen:factura.id)
            
            def clave="201-$factura.proveedor.subCuentaOperativa"
            cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $factura.proveedor")
            }
            
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:0.0,
                haber:factura.importe*pedimento.tipoDeCambio,
                asiento:asiento,
                //descripcion:"$factura.proveedor ($fechaF) $factura.importe  T.C. $pedimento.tipoDeCambio Ped:$pedimento.pedimento ",
                descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
                referencia:"$factura.documento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CuentaPorPagar'
                ,origen:factura.id)
            
            // Cargo a Inventarios
            
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave('115-0001'),
                debe:factura.importe*pedimento.tipoDeCambio,
                haber:0.0,
                asiento:asiento,
                descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
                referencia:"$factura.documento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CuentaPorPagar'
                ,origen:factura.id)

            poliza.addToPartidas(
                cuenta: CuentaContable.buscarPorClave(claveProv),
                debe:0.0,
                haber:factura.importe*pedimento.tipoDeCambio,
                asiento:asiento,
                descripcion:"$factura.proveedor ($fechaF) $factura.importe  Pedimentos:$pedimentos ",
                referencia:"$factura.documento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'CuentaPorPagar'
                ,origen:factura.id)
        }
    }
    
    private procearImpuestosAduanales(def poliza , def dia){
        
        def asiento="IMPUESTOS ADUANALES"
        def pedimentos=Pedimento.findAll ("from Pedimento p where date(p.fecha)=?",[dia])   
        
        pedimentos.each{pedimento->
            
            def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
            def provImp=provImportacion.join(',')
            
            //println 'Procesando pedimento: '+pedimento
            //1 Cargo a DTA
            def clave="501-0003"
            def cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
            }
            
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:pedimento.dta,
                haber:0.0,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)

            
            def contraPrestacion=pedimento.contraPrestacion 
            
            def contraPrestacionImp=MonedaUtils.round(pedimento.contraPrestacion*(pedimento.impuestoTasa/100) ,0)
            
            contraPrestacion+=contraPrestacionImp

            def prevalidacionImp=MonedaUtils.round(pedimento.prevalidacion*(pedimento.impuestoTasa/100),0)

            contraPrestacion+=prevalidacionImp
            
            poliza.addToPartidas(
                cuenta:CuentaContable.buscarPorClave('501-0008'),
                debe:contraPrestacion,
                haber:0.0,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)
                
            
        }
        
        pedimentos.each{pedimento->
            
            def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
            def provImp=provImportacion.join(',')
            
            //2 Cargo a Prevalidacion
            def clave="501-0004"
            def cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
            }
            def imp=pedimento.prevalidacion //*(1+(pedimento.impuestoTasa/100))
            imp=Rounding.round(imp, 0)
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:imp,
                haber:0.0,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)
            
        }
        
        pedimentos.each{pedimento->
            
            def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
            def provImp=provImportacion.join(',')
            
            //2 Cargo a Prevalidacion
            def clave="118-0003"
            def cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
            }
            def incrementableImp=pedimento.incrementables
            
            def imp2=pedimento.impuestoMateriaPrima+
                MonedaUtils.round( ((pedimento.dta+pedimento.arancel+incrementableImp)*(pedimento.impuestoTasa/100)),0 )
            
            imp2=Rounding.round(imp2, 0)            
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:imp2,
                haber:0.0,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)
            
        }
        
        pedimentos.each{pedimento->
            
            def provImportacion=EmbarqueDet.executeQuery('select d.embarque.proveedor.nombre from EmbarqueDet d where d.pedimento=?',[pedimento]) as Set
            def provImp=provImportacion.join(',')
            
            //6 Cargo a Arancel
            def clave="501-0005"
            def cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
            }
            
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:pedimento.arancel,
                haber:0.0,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)
            
            if(pedimento.incrementable1){
                clave="501-0007"
                cuenta=CuentaContable.findByClave(clave)
                if(cuenta==null){
                    throw new RuntimeException("No existe la sub cuenta operativa para el proveedor $cuenta")
                }
                
                poliza.addToPartidas(
                    cuenta:cuenta,
                    debe:Rounding.round(pedimento.incrementable1.importe*pedimento.tipoDeCambio,0),
                    haber:0.0,
                    asiento:asiento,
                    descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                    referencia:"$pedimento.pedimento",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:'Pedimento'
                    ,origen:pedimento.id)
            }
            
            //Abono deudores diversos
    
            def impuesto=MonedaUtils.round(pedimento.calcularImpuestoDinamico(),0)
            def incrementableImp=pedimento.incrementables
            //def imp2=pedimento.impuestoMateriaPrima+((pedimento.dta+pedimento.arancel+incrementableImp)*(pedimento.impuestoTasa/100))
            def imp2=MonedaUtils.round( ((incrementableImp)*(pedimento.impuestoTasa/100)) ,0 )
            
            
            
            clave="107-"+pedimento.proveedor.subCuentaOperativa

            cuenta=CuentaContable.findByClave(clave)
            if(cuenta==null){
                throw new RuntimeException("No existe la sub cuenta  $clave")
            }
            
            def cpIva = MonedaUtils.round(pedimento.contraPrestacion*(pedimento.impuestoTasa/100),0)
            def haber = impuesto+imp2+pedimento.contraPrestacion+cpIva

            log.info "Impuesto: $impuesto Imp2: $imp2 Contra pres:$pedimento.contraPrestacion contra pre iva:$cpIva"
            poliza.addToPartidas(
                cuenta:cuenta,
                debe:0.0,
                haber:haber,
                asiento:asiento,
                descripcion:"Ped:$pedimento.pedimento ${pedimento.fecha.text()} Ref:$pedimento.referenciacg Prov:$provImp",
                referencia:"$pedimento.pedimento",
                ,fecha:poliza.fecha
                ,tipo:poliza.tipo
                ,entidad:'Pedimento'
                ,origen:pedimento.id)
            
        }
    }
    
    
    private proceaCuentaDeGastos(def poliza, def dia){
        
        def asiento="CUENTA DE GASTOS"
        
        def gastos=CuentaDeGastos.findAll ("from CuentaDeGastos c where date(c.fecha)=?",[dia])
        
        gastos.each{ cg->
            
            // Registrar incrementables
            def incrementable=cg.facturas.sum(0.0,{it.incrementable?it.importe*it.tc:0.0})
            incrementable=Rounding.round(incrementable, 2)
            if(incrementable>0){
                def fac1=cg.facturas.find {it.incrementable}
                poliza.addToPartidas(
                    cuenta:CuentaContable.buscarPorClave("501-0007"),
                    debe:incrementable,
                    haber:0.0,
                    asiento:asiento,
                    descripcion:"$cg.proveedor.nombre Ref:$cg.referencia T.C. $fac1.tc ",
                    referencia:"$cg.referencia",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:fac1.class.getSimpleName()
                    ,origen:fac1.id)
            }
            
            //1.Cargo a cuenta de gastos
            //def clave="504-$cg.proveedor.subCuentaOperativa"
            //def importe=cg.facturas.sum(0.0,{!it.incrementable?it.importe:0.0})
            
            def facturas = cg.facturas.findAll({!it.incrementable})
            facturas.each { fac ->
                assert fac.proveedor.subCuentaOperativa,' No se ha registrado la sub cuenta operativa para el proveedor: '+fac.proveedor
                def cuentaProveedor = CuentaContable.buscarPorClave("504-$fac.proveedor.subCuentaOperativa")
                def importe=fac.importe
                def desc = "Fac:$fac.documento ${fac.fecha.text()} $fac.proveedor.nombre  "
                
                //1.Cargo a cuenta de gastos
                poliza.addToPartidas(
                    cuenta:cuentaProveedor,
                    debe:importe,
                    haber:0.0,
                    asiento:asiento,
                    descripcion:desc,
                    referencia:"$cg.referencia",
                    fecha:poliza.fecha,
                    tipo:poliza.tipo,
                    entidad:fac.class.getSimpleName(),
                    origen:fac.id)

                ///********* Cargo al costo (501-0020) con abono a gastos de importacion (504) **********/
                poliza.addToPartidas(
                    cuenta:CuentaContable.buscarPorClave('501-0020'),
                    debe:importe,
                    haber:0.0,
                    asiento:asiento,
                    descripcion:" COSTOS: "+desc,
                    referencia:"$cg.referencia",
                    fecha:poliza.fecha,
                    tipo:poliza.tipo,
                    entidad:fac.class.getSimpleName(),
                    origen:fac.id)

                poliza.addToPartidas(
                    cuenta: cuentaProveedor,
                    debe: 0.0,
                    haber:importe,
                    asiento:asiento,
                    descripcion:" GASTO: "+desc,
                    referencia:"$cg.referencia",
                    fecha:poliza.fecha,
                    tipo:poliza.tipo,
                    entidad:fac.class.getSimpleName(),
                    origen:fac.id)
                /*****************************************************************************************/

                //2 Cargo a IVA al 11 de cuenta de gastos
                if(fac.tasaDeImpuesto==11.0){
                    def impuesto11=fac.impuestos*fac.tc
                    impuesto11=Rounding.round(impuesto11, 2)
                    poliza.addToPartidas(
                        cuenta:CuentaContable.buscarPorClave("118-0002"),
                        debe:impuesto11,
                        haber:0.0,
                        asiento:asiento,
                        descripcion:desc,
                        referencia:"$cg.referencia",
                        ,fecha:poliza.fecha
                        ,tipo:poliza.tipo
                        ,entidad:fac.class.getSimpleName()
                        ,origen:fac.id)
                }
                //2 Cargo a IVA al 16 de cuenta de gastos
                if(fac.tasaDeImpuesto==16.0){
                    def impuesto16=fac.impuestos*fac.tc
                    impuesto16=Rounding.round(impuesto16, 2)
                    poliza.addToPartidas(
                        cuenta:CuentaContable.buscarPorClave("118-0001"),
                        debe:impuesto16,
                        haber:0.0,
                        asiento:asiento,
                        descripcion:desc,
                        referencia:"$cg.referencia",
                        ,fecha:poliza.fecha
                        ,tipo:poliza.tipo
                        ,entidad:fac.class.getSimpleName()
                        ,origen:fac.id)
                }
            }

            //3 Abono a Deudores
            assert cg.proveedor.subCuentaOperativa,' No existe la cuenta operativa para el proveedor: '+cg.proveedor.subCuentaOperativa
            poliza.addToPartidas(
                    cuenta:CuentaContable.buscarPorClave("107-$cg.proveedor.subCuentaOperativa"),
                    debe:0.0,
                    haber:cg.total,
                    asiento:asiento,
                    descripcion:"$cg.proveedor.nombre Ref:$cg.referencia ${cg.fecha.text()} ",
                    referencia:"$cg.referencia",
                    ,fecha:poliza.fecha
                    ,tipo:poliza.tipo
                    ,entidad:'CuentaDeGastos'
                    ,origen:cg.id)
                
            def ietuCg=cg.facturas.sum(0.0,{
                it.incrementable?0.0:it.importe*it.tc}
            )
                
                
        }
    }

    def procesarComplementos(def poliza){

        poliza.partidas.each {polizaDet ->

            if(polizaDet.entidad == 'CuentaPorPagar' || polizaDet.entidad == 'GastosDeImportacion'){
                def cxp = com.luxsoft.impapx.CuentaPorPagar.get(polizaDet.origen)
                if(cxp.instanceOf(FacturaDeImportacion)){
                    def comprobante = new ComprobanteExtranjero(
                        polizaDet:polizaDet,
                        numFacExt: cxp.documento,
                        montoTotal: cxp.total,
                        moneda: cxp.moneda.getCurrencyCode(),
                        tipCamb: cxp.tc
                    )
                    polizaDet.comprobanteExtranjero = comprobante
                }
                if(cxp?.comprobante){
                    def cfdi = cxp.comprobante
                    def comprobante = new ComprobanteNacional(
                      polizaDet:polizaDet,
                      uuidcfdi:cfdi.uuid,
                      rfc: cfdi.emisorRfc,
                      montoTotal: cfdi.total,
                      moneda: cxp.moneda.getCurrencyCode(),
                      tipCamb: cxp.tc
                    )
                    polizaDet.comprobanteNacional = comprobante
                }
            }
        }
    }

	String toString(){
        return "Procesador de polizas de compras"
    }
   
}
