package com.luxsoft.econta.polizas

import grails.transaction.Transactional
import com.luxsoft.impapx.contabilidad.Poliza
import com.luxsoft.impapx.contabilidad.*
import com.luxsoft.impapx.tesoreria.*
import com.luxsoft.impapx.Venta
import com.luxsoft.cfdi.*

@Transactional
class PolizaDeDescuentosSobreComprasService extends ProcesadorService{

    def procesar(Poliza poliza){
        poliza.descripcion = 'Poliza de descuentos sobre compras'

        def dia = poliza.fecha
    }
}
