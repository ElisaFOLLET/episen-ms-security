package com.episen.ms.model;

import java.util.List;

public class UserContext {

	private String subject;
	
	private List<String> roles;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserContext [subject=" + subject + "]";
	}
	
}
