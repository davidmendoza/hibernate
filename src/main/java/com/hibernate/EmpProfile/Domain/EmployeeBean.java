package com.hibernate.EmpProfile.Domain;

public class EmployeeBean {
	
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private Department dept;
	
	public EmployeeBean(String firstName, String lastName, String gender, int age, Department dept) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.dept = dept;
	}
	
	public EmployeeBean() {
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
}
