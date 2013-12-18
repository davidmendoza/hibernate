package com.hibernate.EmpProfile.Logic;

import java.util.HashSet;
import java.util.Iterator;
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
		
		try {
			System.out.println("\tAdd New Project");
			System.out.print("\tEnter Project Name :$ ");
			projName = sc.nextLine();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Projects proj = new Projects(projName);
			session.save(proj);
			System.out.println("\tProject Added!");
		} catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.close();
			}
		}
		
	}
	
	public void addEmployeeToProject(){
		int projId;
		int empId;
		Session session = null;
		Scanner sc = new Scanner(System.in);

		projId = getProjId();
		
		if (projId != 0) {
			System.out.print("\tEnter the Id of the Employee you wish to add to the project :$ ");
			empId = EmployeeMenu.checkIntInput(sc);
			
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				Employee ed = (Employee)session.get(Employee.class, empId);
				
				if (ed == null){
					System.out.println("\tEmployee Does not exist");
				} else {
					Projects proj = (Projects)session.get(Projects.class, projId);
					ed.getProjects().add(proj);
					System.out.println("\tAdded "+ed.getLastName()+", "+ed.getFirstName()+" to Project "+proj.getProjName());				
				}
			} catch(HibernateException e) {
				if (session!= null) { 
					session.getTransaction().rollback();
				}
				System.err.println("\tThere was an error in the database: "+e);
			} finally {
				if (session!= null) { 
					session.getTransaction().commit();
				}
			}
		} else {
			System.out.println("\tProject not found");
		}
	}
	
	public void viewProjectTeams(){
		int projId;
		projId = ProjectLogic.getProjId();
		viewTeam(projId);
	}
	
	public void removeEmployeeFromProject(){
		int id;
		int projId;
		Session session = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\tRemove employee from project");
		projId = ProjectLogic.getProjId();
		Set<Integer> employeeIds = viewTeam(projId);
		
		if (!employeeIds.isEmpty()){
			
			System.out.print("\tEnter the id of the employee you wish to remove from the project :$ ");
			id = EmployeeMenu.checkIntInput(sc);
			
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
					System.out.println("\tRemoved "+emp.getLastName()+", "+emp.getFirstName()+" from project "+proj.getProjName());
				} catch(HibernateException e) {
					if (session!= null) { 
						session.getTransaction().rollback();
					}
					System.err.println("\tThere was an error in the database: "+e);
				} finally {
					if (session!= null) { 
						session.getTransaction().commit();
					}
				}
			} else {
				System.out.println("\tEmployee is not in the project");
			}
		}
	}
	
	public void removeProject(){
		int projId;
		Long empCount;
		int choice;
		Session session = null;
		Scanner sc = new Scanner(System.in);
		
		projId = getProjId();
		if (projId != 0) {
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				String hql = "select count(*) from Projects b JOIN b.employees e where b.id = :projId";
				Query query = session.createQuery(hql);
				query.setParameter("projId", projId);
				empCount = (Long)query.list().get(0);
				System.out.print("\tThere are "+empCount+" employees working on this project.\n\tAre you sure you want to delete? [1]Yes/[2]No :$ ");
				
				choice = EmployeeMenu.checkIntInput(sc);
				
				if (choice == 1){
					Projects proj = (Projects)session.get(Projects.class, projId);
					session.delete(proj);
					System.out.println("\tDeleted Project "+proj.getProjName());
				} else {
					System.out.println("\tCancelled Deletion");
				}
				
			} catch(HibernateException e) {
				if (session!= null) { 
					session.getTransaction().rollback();
				}
				System.err.println("\tThere was an error in the database: "+e);
			} finally {
				if (session!= null) { 
					session.getTransaction().commit();
				}
			}
		} else {
			System.out.println("\tProject not found");
		}
		
	}
	
	public void projPerEmployee(){
		Session session = null;
		
		try{
			System.out.println("\tThe following employees have projects under them: ");
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			String hql = "select distinct e from Employee e join e.projects";
			Query query = session.createQuery(hql);
			List<Employee> result = query.list();
			for (Employee ed: result){
				System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getDept().getDeptName());
				System.out.print("\tProjects:   ");
				Iterator<Projects> iter = ed.getProjects().iterator();
				while (iter.hasNext()){
					System.out.print(iter.next().getProjName()+"  |");
				}
				System.out.println();
			}
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.close();
			}
		}
	}
	
	private static int getProjId(){
		Session session = null;
		Scanner sc = new Scanner(System.in);
		int projId;
		
		try {
			System.out.println("\tHere is the list of the current projects: ");
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Projects> projects = session.createQuery("from Projects").list();
			Set<Integer> ids = new HashSet();
			
			System.out.println("\tProject Id - Project Name");
			for (Projects proj: projects){
				System.out.println("\t"+proj.getId()+" - "+proj.getProjName());
				ids.add(proj.getId());
			}
			
			System.out.print("\tChoose a project Id from the list :$ ");
			
			projId = EmployeeMenu.checkIntInput(sc);
			
			if (ids.contains(projId)){
				return projId;
			}
			
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session!= null) { 
				session.close();
			}
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
					System.out.println("\tEmployee Id - Employee Name - Department");
					for (Employee ed: employees){
						System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getDept().getDeptName());
						employeeIds.add(ed.getId());
					}
				}
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			} finally {
				if (session!= null) { 
					session.close();
				}
			}
			
		} else {
			System.out.println("\tProject not found");
		}
		return employeeIds;
	}
	
}
