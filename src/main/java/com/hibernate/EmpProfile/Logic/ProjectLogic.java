package com.hibernate.EmpProfile.Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
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
		int projId;
		
		System.out.println("\tHere is the list of the current projects: ");
		projId = ProjectLogic.getProjId();
		ProjectLogic.viewTeam(projId);
		
	}
	
	public void removeEmployeeFromProject(){
		int id;
		int projId;
		Session session = null;
		Set<Integer> employeeIds;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\tRemove employee from project");
		projId = ProjectLogic.getProjId();
		employeeIds = ProjectLogic.viewTeam(projId);
		if (!employeeIds.isEmpty()){
			
			System.out.print("\tEnter the id of the employee you wish to remove from the project :$ ");
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next(); 
			}
			id = sc.nextInt();
			
			if (employeeIds.contains((Integer)id)) {
				try{
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.getTransaction().begin();
					Employee emp = (Employee)session.get(Employee.class, id);
					Projects proj = (Projects)session.get(Projects.class, projId);
					String sql = "delete from Employee_Project where employee_id = :id and project_id = :projId";
					Query query = session.createSQLQuery(sql);
					query.setParameter("id", id);
					query.setParameter("projId", projId);
					query.executeUpdate();
					session.getTransaction().commit();
					System.out.println("\tRemoved "+emp.getLastName()+", "+emp.getFirstName()+" from project "+proj.getProjName());
				} catch(HibernateException e) {
					session.getTransaction().rollback();
					System.err.println("There was a problem with the database "+ e);
				}
			} else {
				System.out.println("Employee is not in the project");
			}
		}
	}
	
	public void removeProject(){
		int projId;
		Long empCount;
		int choice;
		Session session = null;
		Scanner sc = new Scanner(System.in);
		
		projId = ProjectLogic.getProjId();
		if (projId != 0) {
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				String hql = "select count(*) from Projects b JOIN b.employees e where b.id = :projId)";
				Query query = session.createQuery(hql);
				query.setParameter("projId", projId);
				empCount = (Long)query.list().get(0);
				System.out.print("\tThere are "+empCount+" employees working on this project.\n\tAre you sure you want to delete? [1]Yes/[2]No :$ ");
				
				while (!sc.hasNextInt()){
					System.out.print("\tPlease enter a valid number :$ ");
					sc.next(); 
				}
				choice = sc.nextInt();
				
				if (choice == 1){
					Projects proj = (Projects)session.get(Projects.class, projId);
					session.delete(proj);
					session.getTransaction().commit();
					System.out.println("\tDeleted Project "+proj.getProjName());
				} else {
					System.out.println("\tCancelled Deletion");
					session.getTransaction().rollback();
				}
			} catch(HibernateException e) {
				session.getTransaction().rollback();
				System.err.println("There was a problem with the database "+ e);
			}
		} else {
			System.out.println("\tProject not found");
		}
		
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
	
	private static Set viewTeam(int projId){
		Session session = null;
		Set<Integer> employeeIds = new HashSet();
		if (projId != 0) {
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				String hql = ("select e from Projects b JOIN b.employees e where b.id = :projId");
				Query query = session.createQuery(hql);
				query.setParameter("projId", projId);
				List<Employee> employees= query.list();

				if (employees.isEmpty()){	
					System.out.println("\tNo employees registered in this project");
				} else {
					System.out.println("\tEmployees registered under the project: ");
					System.out.println("\tEmployee Id - Employee Name ");
					for (Employee ed: employees){
						System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName());
						employeeIds.add(ed.getId());
					}
				}
				session.getTransaction().rollback();
				return employeeIds;
			} catch(HibernateException e) {
				session.getTransaction().rollback();
				System.err.println("There was a problem with the database "+ e);
			}
		} else {
			System.out.println("\tProject not found");
			return employeeIds;
		}
		return employeeIds;
	}
	
}
