package com.luxsoft.sx4.sec

import grails.transaction.Transactional
import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.exception.ExceptionUtils

@Transactional
class UsuarioService {

	def grailsApplication

    def importar(Long id){
    	log.info 'Importando empleado: '+id
    	def sql=sql()
    	try {
    		sql.eachRow("""
    		        select u.clave as sucursal
    		        ,pu.clave as puesto
    		        , e.id as empleadoId
    		        , e.apellido_paterno as apellidoPaterno
    		        ,e.apellido_materno as apellidoMaterno
    		        ,e.nombres as nombres
    		        ,p.numero_de_trabajador as numeroDeEmpleado
    		        from empleado e
    		        join perfil_de_empleado p on e.id=p.empleado_id
    		        join ubicacion u on p.ubicacion_id=u.id
    		        join puesto pu on p.puesto_id=pu.id
    		        where e.id=?
    		    """,[id]){ row->

    		    def found=Usuario.findByNumeroDeEmpleado(row.numeroDeEmpleado)
    		    
    		    if(!found){
    		        def usuario=new Usuario(row.toRowResult())
    		        usuario.nombre="$usuario.nombres $usuario.apellidoPaterno?:'' $usuario.apellidoMaterno?:''"
    		        //usuario.nip=RandomStringUtils.randomNumeric(4)
    		        usuario.username=RandomStringUtils.random(6, true, true)
    		        usuario.password='123'
                    usuario.passwordExpired=true
    		        usuario.save flush:true,failOnError:true
    		    }
    		}
    	}
    	catch(Exception e) {
    		log.error e
    		def msg=ExceptionUtils.getRootCauseMessage(e)
    		throw new RuntimeException("Error importando empleado ${id}" +msg)
    	}
    	finally {
    		sql.close()
    	}
    }

    private sql(){
    	def db=grailsApplication.config.luxor.empleadosDb
    	SingleConnectionDataSource ds=new SingleConnectionDataSource(
            driverClassName:'com.mysql.jdbc.Driver',
            url:db.url,
            username:db.username,
            password:db.password)
        Sql sql=new Sql(ds)
        return sql
    }


    def getEmpleadosAsJSON(def term) {
        term=term.toLowerCase()+'%'
        //term=term.toLowerCase()+'%'
        def sql=sql()

        def rows=sql.rows("""
            select u.clave as sucursal,pu.clave as puesto, e.id, e.apellido_paterno,e.apellido_materno,e.nombres
            from empleado e
            join perfil_de_empleado p on e.id=p.empleado_id
            join ubicacion u on p.ubicacion_id=u.id
            join puesto pu on p.puesto_id=pu.id
            where lower(e.apellido_paterno) like ? or lower(e.apellido_materno) like ? or lower(e.nombres) like ?
            LIMIT 50
            """,[term,term,term])
        
        
        def list=rows.collect{ c->
            def nombre="$c.nombres $c.apellido_paterno $c.apellido_materno"
            [id:c.id,
            label:nombre+"( $c.sucursal / $c.puesto)",
            value:nombre
            ]
        }
        def res=list as JSON
        return res
    }
    
}
