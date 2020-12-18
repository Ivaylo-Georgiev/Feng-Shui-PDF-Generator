package com.fmi.fengshuipdfgenerator.pojo;

import com.fmi.fengshuipdfgenerator.enums.Gender;

public class RPCParameters {

	private int year;
	private int hour;
	private Gender gender;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
