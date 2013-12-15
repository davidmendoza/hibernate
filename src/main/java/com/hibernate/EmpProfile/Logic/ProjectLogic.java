package com.hibernate.EmpProfile.Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.hibernate.EmpProfile.Domain.Employee;
import com.hibernate.EmpProfile.Domain.Projects;
import com.hibernate.EmpProfile.Util.HibernateUtil;

public class ProjectLogic {
	
	public void addProject(){
		String projName;
		Scanner sc = new Scanner(System.in);
		Session session = null;
		
		System.out.println("\tAdd New Project");
		System.out.print("\tEnter Project Name :$ ");
		projName = sc.nextLine();
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Projects proj = new Projects(projName);
			session.save(proj);
			session.getTransaction().commit();
			System.out.println("\tProject Added!");
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			System.err.println("There was a problem with the database "+ e);
		}
		
	}
	
	public void addEmployeeToProject(){
		int projId;
		int empId;
		Session session = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\tHere is the list of the current projects: ");
		projId = ProjectLogic.getProjId();
		if (projId != 0) {
			System.out.print("\tEnter the Id of the Employee you wish to add to the project :$ ");
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next(); 
			}
			empId = sc.nextInt();
			
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				Employee ed = (Employee)session.get(Employee.class, empId);
				
				if (ed == null){
					System.out.println("\tEmployee Does not exist");
					session.getTransaction().rollback();
				} else {
					Projects proj = (Projects)session.get(Projects.class, projId);
					ed.getProjects().add(proj);
					session.getTransaction().commit();
					System.out.println("\tAdded "+ed.getLastName()+", "+ed.getFirstName()+" to Project "+proj.getProjName());
				}
			} catch(HibernateException e) {
				session.getTransaction().rollback();
				System.err.println("There was a problem with the database "+ e);
			}
		} else {
			System.out.println("\tProject not found");
		}
	}
	
	public void viewProjectTeams(){
		System.out.println("viewing project teams");
	}
	
	public void removeEmployeeFromProject(){
		System.out.println("removing employee from project");
	}
	
	public void removeProject(){
		System.out.println("removing project");
	}
	
	private static int getProjId(){
		Session session = null;
		Scanner sc = new Scanner(System.in);
		int projId;
		
		try {
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Projects> projects = session.createQuery("from Projects").list();
			Set<Integer> ids = new HashSet();
			
			System.out.println("\tProject Id - Project Name");
			for (Projects proj: projects){
				System.out.println("\t"+proj.getId()+" - "+proj.getProjName());
				ids.add(proj.getId());
			}
			session.getTransaction().rollback();
			
			System.out.print("\tChoose a project Id from the list :$ ");
			
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next(); 
			}
			projId = sc.nextInt();
			
			if (ids.contains(projId)){
				return projId;
			} 
			return 0;
			
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			System.err.println("There was a problem with the database "+ e);
		}
		return 0;
	}
}
