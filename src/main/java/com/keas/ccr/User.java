package com.keas.ccr;

import java.util.Date;

import org.astm.ccr.Address;

public class User implements IUser {

	private Address address;
	private String lastName;
	private String id;
	private Gender gender;
	private String firstName;
	private String email;
	private Date dob;

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public Date getDob() {
		return dob;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

}
