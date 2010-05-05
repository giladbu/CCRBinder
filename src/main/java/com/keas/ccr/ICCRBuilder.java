package com.keas.ccr;

import org.astm.ccr.ContinuityOfCareRecord;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;


public interface ICCRBuilder {
	public void setUser(IUser user);
	public void addLabResult(ILabResult lab);
	public void addMedication(IMedication med);
	public void addProblem(ICondition element);
	public void addSocialHistory(ICondition element);
	public void addFamilyHistory(IFamilyHistoryProblem element);
	public String toXml() throws MarshalException, ValidationException;
	public String toXml(boolean asDocument) throws MarshalException,ValidationException;
	public ContinuityOfCareRecord toCCR();
}
