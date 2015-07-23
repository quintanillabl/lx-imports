import com.luxsoft.sec.*
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
		def userRole=Rol.findOrSaveWhere(authority:'USUARIO')
		def mostradorRole=Rol.findOrSaveWhere(authority:'OPERADOR')
		def administracionRole=Rol.findOrSaveWhere(authority:'ADMINISTRACION')
		def adminRole=Rol.findOrSaveWhere(authority:'ADMIN')
		def ventasRole=Rol.findOrSaveWhere(authority:'VENTAS')
		def tesoreriaRole=Rol.findOrSaveWhere(authority:'TESORERIA')
		def gastosRole=Rol.findOrSaveWhere(authority:'GASTOS')
		Rol.findOrSaveWhere(authority:'COMPRAS')


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
			UsuarioRol.create(admin,userRole,true)
			UsuarioRol.create(admin,adminRole,true)
		}

		def contaRol=Rol.findOrSaveWhere(authority:'CONTABILIDAD')
		if(!admin.getAuthorities().contains(contaRol))
			UsuarioRol.create(admin,contaRol,true)	

		
		
		
		

    }
    def destroy = {
    }
}
