package com.luxsoft.impapx.contabilidad

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

class CuentaContableController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = 100
		params.sort='clave'
		params.order='asc'
		def cuentas=CuentaContable.findAllByDetalle(false,params)
        [cuentaContableInstanceList:cuentas, cuentaContableInstanceTotal: cuentas.size()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[cuentaContableInstance: new CuentaContable(params)]
			break
		case 'POST':
	        def cuentaContableInstance = new CuentaContable(params)
	        if (!cuentaContableInstance.save(flush: true)) {
	            render view: 'create', model: [cuentaContableInstance: cuentaContableInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), cuentaContableInstance.id])
	        redirect action: 'show', id: cuentaContableInstance.id
			break
		}
    }

    def show() {
        def cuentaContableInstance = CuentaContable.get(params.id)
        if (!cuentaContableInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
            redirect action: 'list'
            return
        }

        [cuentaContableInstance: cuentaContableInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def cuentaContableInstance = CuentaContable.findById(params.id,[fetch:[subCuentas:'eager']])
	        if (!cuentaContableInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [cuentaContableInstance: cuentaContableInstance]
			break
		case 'POST':
	        def cuentaContableInstance = CuentaContable.get(params.id)
	        if (!cuentaContableInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (cuentaContableInstance.version > version) {
	                cuentaContableInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'cuentaContable.label', default: 'CuentaContable')] as Object[],
	                          "Another user has updated this CuentaContable while you were editing")
	                render view: 'edit', model: [cuentaContableInstance: cuentaContableInstance]
	                return
	            }
	        }

	        cuentaContableInstance.properties = params

	        if (!cuentaContableInstance.save(flush: true)) {
	            render view: 'edit', model: [cuentaContableInstance: cuentaContableInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), cuentaContableInstance.id])
	        redirect action: 'show', id: cuentaContableInstance.id
			break
		}
    }

    def delete() {
        def cuentaContableInstance = CuentaContable.get(params.id)
        if (!cuentaContableInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
            redirect action: 'list'
            return
        }

        try {
            cuentaContableInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), params.id])
            redirect action: 'show', id: params.id
        }
    }
	
	def agregarSubCuenta(long id){
		println 'agregando sub cuenta: '+params
		def cuenta=CuentaContable.get(id)
		cuenta.addToSubCuentas(
			clave:params.clave
			,descripcion:params.descripcion
			,detalle:params.detalle
			,tipo:cuenta.tipo
			,subTipo:cuenta.subTipo
			,naturaleza:cuenta.naturaleza
		)
		if (!cuenta.save(flush: true)) {
			render view: 'edit', model: [cuentaContableInstance: cuenta]
			return
		}
		flash.message = message(code: 'default.updated.message', args: [message(code: 'cuentaContable.label', default: 'CuentaContable'), cuenta.id])
		render view: 'edit', model: [cuentaContableInstance: cuenta]
		
	}
	
	def cuentasDeDetalleJSONList(){
		def cuentas=CuentaContable.findAllByClaveIlikeAndDetalle(params.term+"%",true,[max:100,sort:"clave",order:"desc"])
		
		def cuentasList=cuentas.collect { it ->
			def desc="$it.clave  $it.descripcion"
			[id:it.id,label:it.toString(),value:it.toString()]
		}
		//println 'Cuentas: '+cuentasList
		render cuentasList as JSON
	}
	
	def eliminarSubCuentas(){
		def data=[:]
		def cuentaInstance = CuentaContable.findById(params.cuentaId,[fetch:[subCuentas:'eager']])
		JSONArray jsonArray=JSON.parse(params.partidas);
		jsonArray.each{ it ->
			def sub=cuentaInstance.subCuentas.find{ sb->
				return sb.id==it.toLong()
			}
			cuentaInstance.removeFromSubCuentas(sub);
		}
		cuentaInstance.save(flash:true)
		render data as JSON
	}
	
	def importarCuentas(){
		def registros=[
			['102','0001','BANAMEX, S.A.'],
			['102','0004','UBS 398322 ( CTA DLLS )'],
			['102','0005','BANAMEX G.F'],
			['103','0001','INVERSIONES BANAMEX'],
			['103','0005','INTERACCIONES CASA DE BOLSA S'],
			['104','0001','PAPEL, S.A. DE C.V.'],
			['104','0002','METODO DE PARTICIPACION'],
			['106','0007','PAPEL, S.A. DE C.V.'],
			['106','0014','TUSCOR LLOYDS UK DE MEXICO, S.'],
			['109','C004','CARLOS MONTES DE OCA E HIJOS'],
			['109','H005','HERRERA MIER SC'],
			['109','M003','MEDINA FORWARDING MEXICO S.'],
			['109','T001','TUSCOR LLOYDS UK DE MEXICO'],
			['109','V001','VARIOS'],
			['111','P002','POLYFLEX INTERNATIONAL, LT'],
			['114','0003','CENTRAL DE ACTIVOS PUBLICITA'],
			['117','0003','I.V.A. DE IMPORTACION'],
			['117','0006','I.V.A. ACREDITABLE 16%'],
			['117','0007','I.V.A. ACREDITABLE 11%'],
			['117','0002','I.V.A. RETENIDO S/FLETES'],
			['117','0003','I.V.A. RETENIDO PENDIENTE.'],
			['117','0004','I.V.A. ACRED. PENDIENTE 16%'],
			['117','0005','I.V.A. ACRED. PENDIENTE 11%'],
			['118','0001','IMP. ADUANAL RECT. PEDIMENTO'],
			['118','0002','I.V.A. A FAVOR'],
			['118','0003','IMP. ADUANAL RECT. PED. A.D.'],
			['118','0004','IMP. ADUANAL RECT. PED. D.T.'],
			['118','0005','I.S.R. DECL. ANUAL 00'],
			['118','0006','I.S.R. A FAVOR'],
			['118','0007','I.S.R. A FAVOR POR DIVIDEND'],
			['118','0008','IVA DE IMPORTACION'],
			['118','0009','I.S.R. A FAVOR 2007'],
			['118','0010','I.S.R. A FAVOR EJERCICIO 2008'],
			['118','0011','I.E.T.U. A FAVOR'],
			['118','0012','I.S.R. A FAVOR EJERCICIO 2009'],
			['118','0013','ISR EJERCICIO 2010'],
			['119','0001','INVENTARIOS'],
			['119','0002','MERCANCIA EN TRANSITO'],
			['119','0003','MERCANCIAS EN TRANSITO EXT.'],
			['119','0004','INVENTARIOS G.F'],
			['160','0001','PRIMA NETA'],
			['160','0002','INTERES POR FINANCIAMIENTO'],
			['160','0001','CREDITO MERCANTIL'],
			['160','0002','ISR. DIFERIDO G.F'],
			['121','0001','MOBILIARIO Y EQUIPO DE OFICI'],
			['121','0002','ACTUAL. DE MOBILIARIO Y EQ.'],
			['121','0001','DEP ACUM. MOBILIARIO Y EQ. O'],
			['121','0002','ACTUAL. DEP. ACUM. MOBILIARI'],
			['121','0001','GASTOS DE INSTALACION'],
			['121','0002','ACTUAL. GASTOS DE INSTALACIO'],
			['121','0001','AMORT. ACUM . GASTOS INSTALA'],
			['121','0002','ACTUAL. AMORT ACUM GASTOS IN'],
			['200','B001','BACA INTERNACIONAL INC.'],
			['200','B004','BURGO GROUP S.P.A.'],
			['200','C005','CELLMARK PULP & PAPER, INC'],
			['200','M001','M-REAL ZANDERS GMBH'],
			['200','P006','PALS INTERNATIONAL L.L.C'],
			['200','T002','TORRASPAPEL SA'],
			['200','W001','WEBSOURCE'],
			['204','0001','PAPEL, S.A. DE C.V.'],
			['203','G005','GRUPO NACIONAL PROVINCIAL S.'],
			['203','T001','TUSCOR LLOYDS UK DE MEXICO'],
			['203','V001','VARIOS'],
			['205','0001','4% I.V.A. RETENIDO PEND. DE'],
			['205','0002','10% I.V.A. RETENIDO PEND. DE'],
			['205','0003','10% I.S.R. RETENIDO PEND. DE'],
			['205','0004','I.S.R. HON. AL CONSEJO'],
			['205','0002','I.S.R. HON. AL CONSEJO'],
			['205','0006','4 % I.V.A. DE FLETES'],
			['205','0008','I.S.R. DEL PERIODO'],
			['207','0001','I.S.R. D-4'],
			['207','0002','I.S.R. DIFERIDO 2000.'],
			['207','0003','I.S.R. DIFERIDO 2001.'],	
			['301','0001','CAPITAL SOCIAL VARIABLE'],
			['301','0002','ACT. DEL CAPITAL SOCIAL VARI'],
			['303','0001','EJERCICIO 1993.'],
			['303','0002','ACT. DE LA RESERVA LEGAL'],
			['207','0001','I.S.R. DIFERIDO D-4 (2000)'],
			['304','0001','RESULTADO EJERC. 1992'],
			['304','0002','RESULTADO EJERC. 1994.'],
			['304','0006','ACT. RESULTADO DE EJERC. ANT'],
			['304','0012','RESULTADO EJERC. 2006'],
			['304','0013','RESULTADO EJERC. 2007'],
			['304','0014','RESULTADO EJERCICIO 2008'],
			['304','0015','RESULTADO EJERCICIO 2009'],
			['304','0016','RESULTADO EJERCICIO 2010'],
			['304','0017','RESULTADO DEL EJERCICIO 201'],
			['304','0098','INSUFICIENCIA DEL CAP. CONT'],
			['304','0099','EXCESO DEL CAPITAL CONTABLE'],
			['305','0001','INSUFICIENCIA DEL CAP. CONTA'],
			['305','0002','EXCESO DEL CAPITAL CONTABLE'],
			['306','0001','METODO DE PARTICIPACION'],
			['401','0001','PAPEL, S.A. DE C.V.'],
			['401','0002','OTROS INGRESOS AFECTOS A I.V'],
			['401','0008','BURGO GROUP S.P.A.'],
			['401','0009','TUSCOR LLOYDS UK DE MEXICO, S.'],
			['406','0001','DESC. SOBRE VENTAS.'],
			['403','0001','PROV. PARA DESC. S/VENTAS.'],
			['501','0001','COSTO DE VENTAS'],
			['502','D001','DUPON DE MEXICO S.A. DE C.V.'],
			['502','E001','ELOF HANSSON DE MEXICO, S. D'],
			['501','B001','BACA INTERNATIONAL INC'],
			['501','B002','BACA INTERNATIONAL, S.A., IN'],
			['501','B004','BURGO GROUP S.P.A.'],
			['501','B005','BIBLOS-DAM, INC.'],
			['501','C007','CELMARK PULP & PAPER INC.'],
			['501','I001','INCREMENTABLES'],
			['501','M001','M-REAL ZANDERS GMBH'],
			['501','P002','PEREZ TRADING COMPANY'],
			['501','P005','POLYFLEX INTERNATIONAL, LTD.'],
			['501','P006','PALS INTERNATIONAL L.L.C'],
			['501','R004','ROXCEL CORPORATION'],
			['501','T002','TORRASPAPEL SA'],
			['501','W001','WEBSOURCE'],
			['501','0001','MANIOBRAS'],
			['501','0002','FLETES'],
			['501','0003','IMPUESTOS EXENTOS'],
			['501','0004','COMPRAS EXENTAS'],
			['501','0005','FLETES EXENTOS'],
			['501','0006','LAMINADO'],
			['501','0007','COMPLEMENTARIOS'],
			['501','0001','IMPUESTO A.D.V.'],
			['501','0002','IMPUESTO D.T.A.'],
			['501','0003','TRAMITE ADUANERO'],
			['501','0004','IMPUESTO I.G.I'],
			['501','0001','REB. Y DESC. S/COMPRAS NAC.'],
			['501','0002','REB. Y DESC. S/COMPRAS EXT.'],
			['501','0003','DEVOLUCIONES SOBRE COMPRAS'],
			['507','0001','REBAJAS Y DEVOLUCIONES MERC. E'],
			['600','0001','CUOTAS Y SUSCRIPCIONES'],
			['600','0002','PAPELERIA Y ART. DE ESCRITOR'],
			['600','0003','SEGUROS Y FIANZAS'],
			['600','0004','NO DEDUCIBLES'],
			['600','0005','RECARGOS'],
			['600','0006','2% SOBRE NOMINAS'],
			['600','0007','MENSAJERIA.'],
			['600','0008','IMP. DERECHOS Y EROGACIONES'],
			['600','0009','HOJAS PARA MUESTREOS.'],
			['600','0010','ABOGADOS.'],
			['600','0011','DIVERSOS'],
			['600','0012','IMPUESTO SEGURIDAD CONTEN.('],
			['600','0013','COMISIONES BANCARIAS'],
			['600','0014','PUBLICIDAD Y PROPAGANDA'],
			['600','0015','RECONOCIMIENTO ADUANERO DE C'],
			['600','0016','FIANZAS'],
			['600','0017','DEPRECIACIONES'],
			['600','0018','AMORTIZACIONES'],
			['600','0019','MATERIAL DE EMPAQUE'],
			['600','0020','SERVICIOS ADMINISTRATIVOS'],
			['600','A001','ANA PATRICIA BANDALA TOLENTI'],
			['600','C004','CARLOS VIÑAS Y ASOCIADOS, S.C.'],
			['600','C005','CARLOS MONTES DE OCA E HIJOS'],
			['600','C006','CA MEXICO ASESORES PATRIMON'],
			['600','H005','HERRERA MIER S.C'],
			['600','I001','INTERNATIONAL SUPPORT SERVIC'],
			['600','M004','MEDINA FORWARDING MEXICO S.C'],
			['600','R002','RSM BOGARIN SC'],
			['600','T001','TUSCO LLOYDS UK DE MEXICO S'],
			['600','V002','2000 PLUS S.A DE C.V'],
			['600','0001','RAFAEL SANCHEZ MARTINEZ'],
			['600','0003','JOSE SANCHEZ LIZARDI'],
			['600','0004','RAFAEL SANCHEZ LIZARDI'],
			['600','0005','MARIA DEL CONSUELO SANCHEZ L'],
			['600','0006','JULIO ALVARADO IBARRA'],
			['701','0001','INTERES BANCARIO'],
			['701','0002','DIF. CAMBIARIAS A FAVOR'],
			['701','0004','INTERESES'],
			['701','0005','DESCTOS SOBRE COMPRAS MON EX'],
			['702','0001','SALDOS A FAVOR Y ACTUALIZACI'],
			['702','0002','METODO DE PARTICIPACION'],
			['705','0001','COMISIONES BANCARIAS'],
			['705','0002','DIF. CAMBIARIAS A CARGO'],
			['705','0003','COMISIONES BANK BOSTON SIN I'],
			['705','0004','COMISION SIN IVA'],
			['705','0005','PERDIDAS EN TITULOS'],
			['705','0006','INTERESES BANCARIOS'],
			['715','0001','INGRESOS GRAVADOS CON I.V.A.'],
			['702','0002','OTROS.'],
			['702','0003','INGRESOS GRAVADOS CON I.V.A'],
			['702','0004','INGRESOS POR DIVIDENDOS'],
			['702','0005','METODO DE PARTICIPACION'],
			['702','0006','CONTABLES NO FISCALES'],
			['704','0001','METODO DE PARTICIPACION'],
			['704','0002','DIVERSOS'],
			['730','0001','REEXPRESION DEL EJERCICIO'],
			['750','0001','I.S.R. ANUAL'],
			['750','0002','PAGO PROV. DE I.S.R.'],
			['750','0003','I.S.R. BANCARIO'],
			['750','0004','AJUSTE DE I.S.R.'],
			['750','0005','I.E.T.U. PAGO PROVISIONAL'],
			['760','0001','I.S.R. DIFERIDO'],
			['760','0002','I.S.R. DIFERIDO D-4'],
			['800','0001','CORRECCION POR REEXPRESION'],
			['900','0001','COMPRAS NACIONALES'],
			['900','0002','COMPRAS IMPORTACION'],
			['900','0003','GASTOS'],
			['900','0004','GASTOS DE IMPORTACION'],
			['900','0005','OTRAS CONTRIBUCIONES Y DERECHO'],
			['900','0006','SERVICIOS INDEPENDIENTES'],
			['900','0007','SEGUROS Y FIANZAS'],
			['901','0001','COMPRAS NACIONALES'],
			['901','0002','COMPRAS IMPORTACION'],
			['901','0003','GASTOS'],
			['901','0004','GASTOS DE IMPORTACION'],
			['901','0005','OTRAS CONTRIBUCIONES Y DERECHO'],
			['901','0006','SERVICIOS INDEPENDIENTES'],
			['901','0007','SEGUROS Y FIANZAS'],
			['902','0001','VENTAS'],
			['902','0002','INTERESES'],
			['902','0003','OTROS INGRESOS'],
			['903','0001','VENTAS'],
			['903','0002','INTERESES'],
			['903','0003','OTROS INGRESOS']
		]
		registros.each{ it->
			
			
			CuentaContable cuenta=CuentaContable.findByClave(it[0])
			println "Importando sub cuentas para :"+it[0]+" cuenta:  $cuenta"
			if(cuenta){
				cuenta.addToSubCuentas(
					clave:cuenta.clave+'-'+it[1]
					,descripcion:it[2]
					,detalle:true
					,tipo:cuenta.tipo
					,subTipo:cuenta.subTipo
					,naturaleza:cuenta.naturaleza)
				cuenta.save()
			}
				
			
		}
	}
}
