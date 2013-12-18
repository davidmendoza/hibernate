package com.hibernate.EmpProfile.Logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hibernate.EmpProfile.Domain.Department;
import com.hibernate.EmpProfile.Domain.Employee;
import com.hibernate.EmpProfile.Domain.EmployeeBean;
import com.hibernate.EmpProfile.Util.HibernateUtil;

public class EmployeeLogic {

	private static final int VIEW_ALL_EMPLOYEES = 1;
	private static final int SEARCH_BY_NAME = 2;
	private static final int VIEW_EMPLOYEES_BY_DEPARTMENT = 3;
	
	public void add(){
		
		Session session = null;
		
		try{
			System.out.println("\n\tEnter Employee Details: ");
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			EmployeeBean eb = getUserInput(session);
			Employee emp = new Employee(eb.getFirstName(),eb.getLastName(),eb.getGender(),eb.getAge());
			emp.setDept(eb.getDept());
			session.save(emp);
			System.out.println("\tRegistered "+emp.getLastName()+", "+emp.getFirstName());
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
	
	public void view(){
		Session session = null;
		int choice;
		Scanner sc = new Scanner(System.in);
		System.out.print("\t[1]View All [2]Search by name [3]Search by Department :$ ");
		
		choice = EmployeeMenu.checkIntInput(sc);
		
		switch(choice) {
		case VIEW_ALL_EMPLOYEES :
			viewAllEmployees(session);
		break;
		
		case SEARCH_BY_NAME:
			searchByName(session);
		break;
		
		case VIEW_EMPLOYEES_BY_DEPARTMENT:
			viewEmployeesByDept(session);
		break;
		
		default:
		break;
		}
	}
	
	private static void viewAllEmployees(Session session){
		
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Criteria crit = session.createCriteria(Employee.class);
			List<Employee> employees = crit.addOrder(Order.asc("lastName")).list();
			System.out.println("\tId - Employee name - Gender - Age - Department");
			for (Employee ed: employees){
				System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getGender()+" - "+ed.getAge()+" - "+ed.getDept().getDeptName());
			}
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally{
			if (session != null){
				session.close();
			}
		}
		
	}
	
	private static void searchByName(Session session){

		String lName;
		Scanner sc2 = new Scanner(System.in);
		try{
			System.out.print("\tEnter Employee's last name or first name :$ ");
			lName = sc2.nextLine();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			//String hql = ("from Employee where last_name like :lName  or first_name like :lName");
			//Query query = session.createQuery(hql);
			//query.setParameter("lName", lName+"%");
			//List<Employee> employees = query.list();
			List<Employee> employees = session.createCriteria(Employee.class)
					.add(Restrictions.disjunction()
						.add(Restrictions.like("lastName", lName+"%"))
						.add(Restrictions.like("firstName", lName+"%")))
					.list();
			if (employees.isEmpty()){	
				System.out.println("\tEmployee Not Found");
			} else {
				System.out.println("\tId - Employee name - Gender - Age - Department");
				for (Employee ed: employees){
					System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getGender()+" - "+ed.getAge()+" - "+ed.getDept().getDeptName());
				}
			}
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		} finally{
			if (session != null){
				session.close();
			}
		}
		
	}
	
	private static void viewEmployeesByDept(Session session){
		int deptId;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("\tEnter Department ID:$ ");
			
			deptId = EmployeeMenu.checkIntInput(sc);
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			String hql = ("from Employee where deptId = :deptId");
			Query query = session.createQuery(hql);
			query.setParameter("deptId", deptId);
			List<Employee> employees = query.list();
			
			if (employees.isEmpty()){	
				System.out.println("\tThere are no Employees in that Department");
			} else {
				System.out.println("\t"+employees.get(0).getDept().getDeptName()+" Department Employees");
				System.out.println("\tId - Employee name - Gender - Age");
				for (Employee ed: employees){
					System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getGender()+" - "+ed.getAge());
				}
			}
			
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session != null){
				session.getTransaction().commit();
			}
		}
	}
	
	public void delete(){
		
		Session session = null;
		Scanner sc = new Scanner(System.in);
		int id;
		int choice;
		
		try {
			System.out.print("\n\tEnter the ID of the employee you wish to delete :$ ");
			
			id = EmployeeMenu.checkIntInput(sc);
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Employee ed = (Employee)session.get(Employee.class, id);
			
			if (ed == null){
				System.out.println("\tEmployee Does not exist");
			} else {
				if (!ed.getProjects().isEmpty()){
					System.out.print("\tThis employee is still working on "+ed.getProjects().size()+
							" projects.\n\tDo you still want to delete? [1]Yes/[2]No :$ ");
					choice = EmployeeMenu.checkIntInput(sc);
					deleteThisEmployee(choice, session, ed);
					
				} else {
					System.out.print("\tDelete this employee? [1]Yes/[2]No :$ ");
					choice = EmployeeMenu.checkIntInput(sc);
					deleteThisEmployee(choice, session, ed);
				}
			} 
		} catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session != null){
				session.getTransaction().commit();
			}
		}	
	}
	
	private static void deleteThisEmployee(int choice, Session session, Employee ed){
		
		if (choice == 1) {
			session.delete(ed);
			System.out.println("\tEmployee "+ed.getLastName()+" deleted");
		} else {
			System.out.println("\tCancelled Deletion");
		}
	}
	
	public void update(){
		int id;
		Scanner sc = new Scanner(System.in);
		Session session = null;
		
		try {		
			System.out.print("\n\tEnter the employee id of the employee you wish to update :$ ");
			id = EmployeeMenu.checkIntInput(sc);
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Employee ed = (Employee)session.get(Employee.class, id);
			if (ed == null){
				System.out.println("\tEmployee Does not exist");
			} else {
				System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getDept().getDeptName());
				EmployeeBean eb = getUserInput(session);
				ed.setFirstName(eb.getFirstName());
				ed.setLastName(eb.getLastName());
				ed.setGender(eb.getGender());
				ed.setAge(eb.getAge());
				ed.setDept(eb.getDept());
				session.update(ed);
				System.out.println("\tSuccessfully Updated Employee Details");
			} 
			
		} catch(HibernateException e) {
			if (session!= null) { 
				session.getTransaction().rollback();
			}
			System.err.println("\tThere was an error in the database: "+e);
		} finally {
			if (session != null){
				session.getTransaction().commit();
			}
		}
	}
	
	private static EmployeeBean getUserInput(Session session){
		
		EmployeeBean eb = new EmployeeBean();
		Set<Integer> deptIdSet = new HashSet();
		String fname;
		String lname;
		String gender;
		int genChoice;
		int age;
		int deptId;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\tEnter first name :$ ");
		fname = sc.nextLine();
		
		System.out.print("\tEnter last name :$ ");
		lname = sc.nextLine();
		
		System.out.print("\tEnter gender [1]Male/[2]Female :$ ");
		genChoice = EmployeeMenu.checkIntInput(sc);
		
		while (genChoice < 1 || genChoice > 2){
			System.out.print("\tPlease enter either [1]Male/[2]Female :$ ");
			genChoice = EmployeeMenu.checkIntInput(sc);
		}
		
		if (genChoice == 1){
			gender = "Male";
		} else {
			gender = "Female";
		}
		
		System.out.print("\tEnter age :$ ");
		age = EmployeeMenu.checkIntInput(sc);
		
		System.out.println("\tChoose Department");
		List<Department> all = session.createQuery("from Department").list();
		for (Department dep: all){
			System.out.println("\t["+dep.getId()+"] "+dep.getDeptName()+"  ");
			deptIdSet.add(dep.getId());
		}
		System.out.print("\t: $ ");
		deptId = EmployeeMenu.checkIntInput(sc);
		
		while (!deptIdSet.contains(deptId)) {
			System.out.print("\tPlease choose one of the choices :$ ");
			deptId = EmployeeMenu.checkIntInput(sc);
		}
		Department dept = (Department)session.get(Department.class, deptId);
		
		eb.setFirstName(fname);
		eb.setLastName(lname);
		eb.setGender(gender);
		eb.setAge(age);
		eb.setDept(dept);
		return eb;
	}
}
