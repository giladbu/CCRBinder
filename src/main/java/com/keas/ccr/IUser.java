package com.keas.ccr;

import java.util.Date;

import org.astm.ccr.Address;

public interface IUser {
	public String getFirstName();
	public String getLastName();
	public String getId();
	public Date getDob();
	public Address getAddress();
	public String getEmail();
	public Gender getGender();
}
