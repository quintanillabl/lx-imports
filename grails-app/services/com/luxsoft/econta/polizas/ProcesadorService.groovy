package com.luxsoft.econta.polizas

import grails.transaction.Transactional


import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.utils.Periodo

import static com.luxsoft.econta.polizas.PolizaUtils.*
import org.apache.commons.lang.StringUtils

@Transactional
class ProcesadorService {

	def generar(def fecha,def procesador){
        //log.info "Generando poliza con $procesador para ${fecha.text()} "
        return generar(procesador.tipo,procesador.subTipo,fecha)
    }

    def generar(String tipo,String subTipo,Date fecha){

        log.info "Generando poliza   $subTipo ${fecha.text()}"

        def poliza = Poliza.where {
            subTipo==subTipo && fecha == fecha
        }.find()

        if(!poliza){

            poliza=build(fecha,tipo,subTipo)

        }else{

            poliza.partidas.clear()
            log.info "Actualizando poliza ${subTipo } "+fecha.format('dd/MM/yyyy');
        }

        procesar(poliza)
        cuadrar(poliza)
        depurar(poliza)
    	save poliza
    }

    def procesar(Poliza poliza){
        log.info 'PENDIENTE DE IMPLEMENTAR EL PROCESAMIENTO DE ESTE TIPO DE POLIZAS: '
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

	def cargoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
		poliza.addToPartidas(
        	cuenta:cuenta,
        	concepto:cuenta.descripcion,
            debe:importe.abs(),
            haber:0.0,
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad.id.toString(),
            entidad:entidad.class.toString()
		)
	}

	def abonoA(def poliza,def cuenta,def importe,def descripcion,def asiento,def referencia,def entidad){
		poliza.addToPartidas(
        	cuenta:cuenta,
        	concepto:cuenta.descripcion,
            debe:0.0,
            haber:importe.abs(),
            descripcion:StringUtils.substring(descripcion,0,255),
            asiento:asiento,
            referencia:referencia,
            origen:entidad.id.toString(),
            entidad:entidad.class.toString()
		)
	}

    

    def build(def fecha,def tipo,def subTipo){
		def mes=Periodo.obtenerMes(fecha)+1
		def ejercicio=Periodo.obtenerYear(fecha)
		def poliza= new Poliza(tipo:tipo,subTipo:subTipo,ejercicio:ejercicio,mes:mes)
		poliza.fecha=fecha
		log.debug 'Poliaza preparada: '+poliza
		return poliza
	}

    def cuadrar(def poliza){
        def dif=poliza.debe.abs()-poliza.haber.abs()
        if(dif.abs()<=1.0){
            log.info "Cuadrando diferencia de $dif"
            if(dif<0.0){
                def cuenta=PolizaUtils.OtrosGastosNoFiscales(poliza.empresa)
                poliza.addToPartidas(
                    cuenta:cuenta,
                    concepto:cuenta.descripcion,
                    debe:dif.abs(),
                    haber:0.0,
                    descripcion:'CUADRE AUTOMATICO',
                    asiento:'CUADRE',
                    referencia:'CUADRE',
                    origen:'NO APLICA',
                    entidad:'NO APLICA'
                )

            }else if( dif > 0.0 ){
                def cuenta=PolizaUtils.ContablesNoFiscales(poliza.empresa)
                poliza.addToPartidas(
                    cuenta:cuenta,
                    concepto:cuenta.descripcion,
                    debe:0.0,
                    haber:dif.abs(),
                    descripcion:'CUADRE AUTOMATICO',
                    asiento:'CUADRE',
                    referencia:'CUADRE',
                    origen:'NO APLICA',
                    entidad:'NO APLICA'
                )
            }
        }
    }

    def depurar(def poliza){
       def eliminar = poliza.partidas.findAll {it.debe<=0.009 && it.haber<=0.009}
      
       eliminar.each{
            poliza.removeFromPartidas(it)
       }
    }
}

