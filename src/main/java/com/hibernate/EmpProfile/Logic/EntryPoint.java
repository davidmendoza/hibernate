package com.hibernate.EmpProfile.Logic;

import com.hibernate.EmpProfile.Util.HibernateUtil;

public class EntryPoint {

	public static void main(String[] args) {
		HibernateUtil.getSessionFactory();
		EmployeeMenu.startProgram();
	}

}
