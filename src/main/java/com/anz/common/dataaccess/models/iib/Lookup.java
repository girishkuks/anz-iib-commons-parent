package com.anz.common.dataaccess.models.iib;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lookup {

	@Id
	private String qualifier;
	
	private String name;
	
	private String value;

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
