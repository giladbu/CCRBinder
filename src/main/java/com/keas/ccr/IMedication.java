package com.keas.ccr;

import java.math.BigDecimal;

public interface IMedication extends ICondition{
	public BigDecimal getDosage();
	public String getFrequency();
}
