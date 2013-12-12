package com.hibernate.EmpProfile.Logic;

import org.hibernate.Session;

import com.hibernate.EmpProfile.Util.HibernateUtil;

public class EntryPoint {

	public static void main(String[] args) {
		Session session =  HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
	}

}
