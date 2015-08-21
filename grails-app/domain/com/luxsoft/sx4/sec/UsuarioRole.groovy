package com.luxsoft.sx4.sec

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioRole implements Serializable {

	private static final long serialVersionUID = 1

	static auditable = true

	Usuario usuario
	Role role

	UsuarioRole(Usuario u, Role r) {
		this()
		usuario = u
		role = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioRole)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.role?.id == role?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static UsuarioRole get(long usuarioId, long roleId) {
		criteriaFor(usuarioId, roleId).get()
	}

	static boolean exists(long usuarioId, long roleId) {
		criteriaFor(usuarioId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long roleId) {
		UsuarioRole.where {
			usuario == Usuario.load(usuarioId) &&
			role == Role.load(roleId)
		}
	}

	static UsuarioRole create(Usuario usuario, Role role, boolean flush = false) {
		def instance = new UsuarioRole(usuario, role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Usuario u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = UsuarioRole.where { usuario == u && role == r }.deleteAll()

		if (flush) { UsuarioRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioRole.where { usuario == u }.deleteAll()

		if (flush) { UsuarioRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		UsuarioRole.where { role == r }.deleteAll()

		if (flush) { UsuarioRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, UsuarioRole ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioRole.withNewSession {
				existing = UsuarioRole.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'role']
		version false
	}
}
