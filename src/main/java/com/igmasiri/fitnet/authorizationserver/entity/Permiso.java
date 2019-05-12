package com.igmasiri.fitnet.authorizationserver.entity;

import javax.persistence.Entity;

@Entity
public class Permiso extends GenericEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
