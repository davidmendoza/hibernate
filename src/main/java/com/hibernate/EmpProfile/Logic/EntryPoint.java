package com.hibernate.EmpProfile.Logic;

import org.hibernate.Session;

import com.hibernate.EmpProfile.Util.HibernateUtil;

public class EntryPoint {
	
	private static Session session =  HibernateUtil.configureSessionFactory().openSession();
	
	public static void main(String[] args) {
		
		session.beginTransaction();
		EmployeeMenu.startProgram();
	}
	
	public static Session getSession(){
		return session;
	}

}
