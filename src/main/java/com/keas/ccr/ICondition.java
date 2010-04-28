package com.keas.ccr;

import java.util.Date;
import java.util.List;

import org.astm.ccr.Code;

public interface ICondition {
	public String getName();
	public String getValue();
	public List<Code> getCodes();
	public String getSourceId();
	public Date getStartDate();
	public Date getEndDate();
	public boolean isActive();
}
