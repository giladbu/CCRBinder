package com.keas.ccr;

import java.util.Date;

import org.astm.ccr.Code;

public class LabResult implements ILabResult {
	private Code code;
	private Date date;
	private String name;
	private String sourceId;
	private String units;
	private String value;

	@Override
	public Code getCode() {
		return code;
	}
	public void setCode(Code code) {
		this.code = code;
	}
	
	@Override
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	@Override
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
