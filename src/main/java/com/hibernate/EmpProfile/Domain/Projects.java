package com.hibernate.EmpProfile.Domain;

public class Projects {
	
	private int id;
	private String projName;
	
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
	

}
