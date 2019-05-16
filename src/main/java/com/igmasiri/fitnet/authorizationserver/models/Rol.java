package com.igmasiri.fitnet.authorizationserver.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rol extends GenericEntity {

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permiso_rol", joinColumns = {
			@JoinColumn(name = "rol_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "permiso_id", referencedColumnName = "id") })
	private List<Permiso> permisos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

}