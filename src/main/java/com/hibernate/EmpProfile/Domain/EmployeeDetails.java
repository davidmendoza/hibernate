package com.hibernate.EmpProfile.Domain;

public class EmployeeDetails {
	
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private int deptId;
	
	public EmployeeDetails(){
		
	}
	
	public EmployeeDetails(String firstName, String lastName, int age, int deptId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.deptId = deptId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	
}
