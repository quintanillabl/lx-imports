package com.luxsoft.econta.polizas

import grails.transaction.Transactional


import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.utils.Periodo
import com.luxsoft.lx.utils.MonedaUtils

//import static com.luxsoft.econta.polizas.PolizaUtils.*
import org.apache.commons.lang.StringUtils


//@Transactional
class ProcesadorService {

	def generar(def fecha,def procesador){
        //log.debug "Generando poliza con $procesador para ${fecha.text()} "
        return generar(procesador.tipo,procesador.subTipo,fecha)
    }

    def generar(String tipo,String subTipo,Date fecha){

        log.debug "Generando poliza   $subTipo ${fecha.text()}"

        def poliza = Poliza.where {
            subTipo==subTipo && fecha == fecha
        }.find()

        if(!poliza){

            poliza=build(fecha,tipo,subTipo)

        }else{

            poliza.partidas.clear()
            log.debug "Actualizando poliza ${subTipo } "+fecha.format('dd/MM/yyyy');
        }

        procesar(poliza)
        cuadrar(poliza)
        depurar(poliza)
    	save poliza
    }

    def procesar(Poliza poliza){
        log.debug 'PENDIENTE DE IMPLEMENTAR EL PROCESAMIENTO DE ESTE TIPO DE POLIZAS: '
    }

    def save(Poliza poliza){
        if(!poliza.folio || poliza.folio==0){
            poliza.folio=nextFolio(poliza)
        }
        poliza.save(failOnError:true,flush:true)
        return poliza
    }

    def nextFolio(Poliza poliza){
        def year=poliza.fecha.toYear()
        def mes=poliza.fecha.toMonth()
        def max=Poliza.executeQuery(
            "select max(p.folio) from Poliza p where p.subTipo=? and year(p.fecha)=? and month(p.fecha)=?"
            ,[poliza.subTipo,year,mes])
        def folio=max[0]?:0
        folio+=1
    }

    /// Metodos comunes

	Poliza cargoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
		poliza.addToPartidas(
        	cuenta:cuenta,
        	concepto:cuenta.descripcion,
            debe:importe.abs(),
            haber:0.0,
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad.id.toString(),
            entidad:entidad.class.getSimpleName()
		)
        return poliza
	}

	Poliza abonoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
		poliza.addToPartidas(
        	cuenta:cuenta,
        	concepto:cuenta.descripcion,
            debe:0.0,
            haber:importe.abs(),
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad?.id?.toString(),
            entidad:entidad.class.getSimpleName()
		)
        return poliza
	}

    def build(def fecha,def tipo,def subTipo){
		def mes=Periodo.obtenerMes(fecha)+1
		def ejercicio=Periodo.obtenerYear(fecha)
		def poliza= new Poliza(tipo:tipo,subTipo:subTipo,ejercicio:ejercicio,mes:mes)
		poliza.fecha=fecha
		log.debug 'Poliaza preparada: '+poliza
		return poliza
	}

    Poliza cuadrar(Poliza poliza){
        
        poliza.actualizar()
        
        
        
        def dif = MonedaUtils.round(poliza.getDebe())-MonedaUtils.round(poliza.getHaber())

        log.debug "Cudrando poliza Debe: $poliza.debe Haber: $poliza.haber Dif: $dif"
        
        if( dif.abs()>0.0 && dif.abs() <5.0){
            //Otros productos/gastos
                if(dif>0.0){
                    //Producto
                    def cta=CuentaContable.buscarPorClave("704-0006")
                    log.debug "Cuadrando poliza en OTROS INGRESOS por dif :$dif en cta:$cta.clave"
                    poliza.addToPartidas(
                        cuenta:cta,
                        debe:0.0, 
                        haber:dif.abs(),
                        asiento:"OTROS INGRESOS "+poliza.subTipo,
                        descripcion:"OTROS INGRESOS",
                        referencia:"",
                        fecha:poliza.fecha,
                        tipo:poliza.tipo
                    )
                }else{

                    //Gasto
                    def cta=CuentaContable.buscarPorClave("703-0003")
                    log.debug "Cuadrando poliza en OTROS GASTOS por dif :$dif en cta:$cta.clave"
                    poliza.addToPartidas(
                        cuenta:cta,
                        debe:dif.abs(),
                        haber:0.0,
                        asiento:"OTROS GASTOS "+poliza.subTipo,
                        descripcion:"OTROS GASTOS",
                        referencia:"",
                        fecha:poliza.fecha,
                        tipo:poliza.tipo
                    )
                }
        }
        poliza.actualizar()
        //poliza.cuadrar()
        return poliza
    }

    Poliza depurar(Poliza poliza){
       def eliminar = poliza.partidas.findAll {it.debe<=0.009 && it.haber<=0.009}
      
       eliminar.each{
            poliza.removeFromPartidas(it)
       }
       return poliza
    }
}

