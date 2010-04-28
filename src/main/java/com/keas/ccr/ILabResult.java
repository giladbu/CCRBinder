package com.keas.ccr;

import java.util.Date;

import org.astm.ccr.Code;

public interface ILabResult {
	public String getName();
	public Code getCode();
	public String getSourceId();
	public String getValue();
	public String getUnits();
	public Date getDate();	
}
