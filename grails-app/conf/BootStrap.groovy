import com.luxsoft.sx4.sec.*
import org.bouncycastle.jce.provider.BouncyCastleProvider


class BootStrap {

    def init = { servletContext ->

    	Date.metaClass.inicioDeMes{ ->
    		def d1=delegate.clone()
    		d1.putAt(Calendar.DATE,1)
    		return d1.clearTime()
    	
    	}
    	
    	Date.metaClass.finDeMes{ ->
    		Calendar c2=delegate.clone().toCalendar()
    		c2.putAt(Calendar.DATE,c2.getActualMaximum(Calendar.DATE))
    		return c2.getTime().clearTime()
    	}
    	
    	Date.metaClass.text{ ->
    		return delegate.format('dd/MM/yyyy')
    	}
    	
    	Date.metaClass.toMonth{ ->
    		return delegate.getAt(Calendar.MONTH)+1
    	}
    	Date.metaClass.toYear{
    		return delegate.getAt(Calendar.YEAR)
    	}
    	Date.metaClass.asPeriodoText{
    		return delegate.format('MMMM - yyyy')
    	}

    	java.security.Security.addProvider(new BouncyCastleProvider())
		def userRole=Role.findOrSaveWhere(authority:'USUARIO')
		def mostradorRole=Role.findOrSaveWhere(authority:'OPERADOR')
		def administracionRole=Role.findOrSaveWhere(authority:'ADMINISTRACION')
		def adminRole=Role.findOrSaveWhere(authority:'ADMIN')
		def ventasRole=Role.findOrSaveWhere(authority:'VENTAS')
		def tesoreriaRole=Role.findOrSaveWhere(authority:'TESORERIA')
		def gastosRole=Role.findOrSaveWhere(authority:'GASTOS')
		Role.findOrSaveWhere(authority:'COMPRAS')


		def admin=Usuario.findByUsername('admin')
		if(!admin){
			admin=new Usuario(username:'admin'
				,password:'admin'
				,apellidoPaterno:'admin'
				,apellidoMaterno:'admin'
				,nombres:'admin'
				,nombre:' ADMIN ADMIN'
				,numeroDeEmpleado:'0000')
			.save(flush:true,failOnError:true)
			UsuarioRole.create(admin,userRole,true)
			UsuarioRole.create(admin,adminRole,true)
		}

		def contaRol=Role.findOrSaveWhere(authority:'CONTABILIDAD')
		if(!admin.getAuthorities().contains(contaRol))
			UsuarioRole.create(admin,contaRol,true)	

		
		com.luxsoft.econta.polizas.PolizaUtils.buildProcesadores()
		
		

    }
    def destroy = {
    }
}
