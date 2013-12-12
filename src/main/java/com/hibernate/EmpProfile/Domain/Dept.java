package com.hibernate.EmpProfile.Domain;

public class Dept {
	
	private int id;
	private int deptName;
	
	public Dept() {
		 
	}
	
	public Dept(int id, int deptName) {
		this.id = id;
		this.deptName = deptName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeptName() {
		return deptName;
	}

	public void setDeptName(int deptName) {
		this.deptName = deptName;
	}
	
	
}
