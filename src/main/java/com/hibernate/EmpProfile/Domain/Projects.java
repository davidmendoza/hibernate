package com.hibernate.EmpProfile.Domain;

import java.util.HashSet;
import java.util.Set;

public class Projects {
	
	private int id;
	private String projName;
	private Set employees = new HashSet();
	
	public Projects(){
		
	}

	public Projects(String projName) {
		this.projName = projName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public Set getEmployees() {
		return employees;
	}

	public void setEmployees(Set employees) {
		this.employees = employees;
	}
	

}
