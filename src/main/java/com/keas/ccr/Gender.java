package com.keas.ccr;

public enum Gender {
	MALE("M","Male"),
	FEMALE("F","Female");
	
	private final String name;
	private final String code;

	private Gender(String code, String name){
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
