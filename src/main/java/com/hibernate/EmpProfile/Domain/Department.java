package com.hibernate.EmpProfile.Domain;

import java.util.HashSet;
import java.util.Set;

public class Department {
	
	private int id;
	private String deptName;
	private Set employees = new HashSet();
	
	public Department() {
		 
	}

	public Department(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set getEmployees() {
		return employees;
	}

	public void setEmployees(Set employees) {
		this.employees = employees;
	}


	
	
}
