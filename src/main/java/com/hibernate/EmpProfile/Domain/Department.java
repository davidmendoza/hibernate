package com.hibernate.EmpProfile.Domain;

public class Department {
	
	private int id;
	private String deptName;

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


	
	
}
