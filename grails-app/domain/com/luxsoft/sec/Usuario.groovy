package com.luxsoft.sec

class Usuario {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	String apellidoPaterno
	String apellidoMaterno
	String nombres
	String nombre
	Integer numeroDeEmpleado
	String email
	String sucursal
	String puesto

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		apellidoPaterno()
		apellidoMaterno()
		nombre()
		enabled()
		accountExpired()
		accountLocked()
		passwordExpired()
		email nullable:true,email:true
		numeroDeEmpleado nullable:true
		sucursal nullable:true,maxSize:20
		puesto nullable:true,maxSize:30
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this).collect { it.rol }
	}

	def beforeInsert() {
		encodePassword()
		capitalizarNombre()
		
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
			
		}
		if (isDirty('apellidoPaterno') || isDirty('apellidoMaterno') || isDirty('nombres')) {
			capitalizarNombre()
			
		}
		
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	private capitalizarNombre(){
		apellidoPaterno=apellidoPaterno.toUpperCase()
		apellidoMaterno=apellidoMaterno.toUpperCase()
		nombres=nombres.toUpperCase()
		nombre="$nombres $apellidoPaterno $apellidoMaterno"
	}
}



