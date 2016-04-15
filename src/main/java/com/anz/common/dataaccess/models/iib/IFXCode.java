package com.anz.common.dataaccess.models.iib;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class IFXCode {

	@Id
	private String code;
	
	private String severity;
	
	private String status;
	
	private String descr;
	
	@OneToMany(mappedBy="ifxCode")
	private List<IFXProviderCode> ifxProviderCodes;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<IFXProviderCode> getIfxProviderCodes() {
		return ifxProviderCodes;
	}
	
	public void setIfxProviderCodes(List<IFXProviderCode> ifxProviderCodes) {
		this.ifxProviderCodes = ifxProviderCodes;
	}
}
