package com.hibernate.EmpProfile.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.hibernate.EmpProfile.Domain.Department;
import com.hibernate.EmpProfile.Domain.Employee;
import com.hibernate.EmpProfile.Util.HibernateUtil;

public class EmployeeLogic {

	
	public void add(){
		
		Session session = null;
		System.out.println("\n\tEnter Employee Details: ");
		
		List<String>userInput = EmployeeLogic.getUserInput();
		
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			Department dept = (Department)session.get(Department.class, Integer.parseInt(userInput.get(3)));
			Employee emp = new Employee(userInput.get(0),userInput.get(1),Integer.parseInt(userInput.get(2)));
			emp.setDept(dept);
			session.save(emp);
			session.getTransaction().commit();
		} catch(HibernateException e) {
			if (session!= null) session.getTransaction().rollback();
			System.err.println("\tThere was an error in the database: "+e);
		} 
		
	}
	
	public void view(){
		Session session = null;
		int choice;
		String lName;
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		System.out.print("\t[1]View All [2]Search by lastname [3]Search by Department :$ ");
		
		while (!sc.hasNextInt()){
			System.out.print("\tPlease enter a valid number :$ ");
			sc.next(); 
		}
		choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			try {
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				List<Employee> employees = session.createQuery("from Employee").list();
				System.out.println("\tId - Employee name - Age - Department");
				for (Employee ed: employees){
					System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getAge()+" - "+ed.getDept().getDeptName());
				}
				session.getTransaction().rollback();
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			}
		break;
		
		case 2:
			System.out.print("\tEnter Employee's last name :$ ");
			lName = sc2.nextLine();
			
			try{
				
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.getTransaction().begin();
				String hql = ("from Employee where last_name like :lName");
				Query query = session.createQuery(hql);
				query.setParameter("lName", lName);
				List<Employee> employees = query.list();
				
				if (employees.isEmpty()){	
					System.out.println("\tEmployee Not Found");
				} else {
					System.out.println("\tId - Employee name - Age - Department");
					for (Employee ed: employees){
						System.out.println("\t"+ed.getId()+" - "+ed.getLastName()+", "+ed.getFirstName()+" - "+ed.getAge()+" - "+ed.getDept().getDeptName());
					}
				}
				session.getTransaction().rollback();
				
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			}
		break;
		
		case 3:
			try {
				
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			}
		break;
		default:
		break;
		}
	}
	
	public void delete(){
		
		Session session;
		Scanner sc = new Scanner(System.in);
		int id;
		
			System.out.print("\n\tEnter the ID of the employee you wish to delete :$ ");
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next();
			}
			
			id = sc.nextInt();
			
			
				
			Employee ed = EmployeeLogic.getEmployee(id);
			
			try {	
				if (ed == null){
					System.out.println("\tEmployee Does not exist");
				} else {
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.getTransaction().begin();
					session.delete(ed);
					session.getTransaction().commit();
					System.out.println("\tEmployee "+ed.getLastName()+" deleted");
				}
				
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			}
		
	}
	
	public void update(){
		int id;
		Scanner sc = new Scanner(System.in);
		Session session;
		
		System.out.print("\n\tEnter the employee id of the employee you wish to update :$ ");
		while (!sc.hasNextInt()){
			System.out.print("\tPlease enter a valid number :$ ");
			sc.next();
		}
		id = sc.nextInt();
		Employee ed = EmployeeLogic.getEmployee(id);
		
		
			if (ed == null){
				System.out.println("\tEmployee Does not exist");
			} else {
				
				List<String> userInput = EmployeeLogic.getUserInput();
				
				try {		
					session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.getTransaction().begin();
					
					System.out.println("\tEmployee "+ed.getLastName()+", "+ed.getFirstName());
					Department dept = (Department)session.get(Department.class, Integer.parseInt(userInput.get(3)));
					ed.setFirstName(userInput.get(0));
					ed.setLastName(userInput.get(1));
					ed.setAge(Integer.parseInt(userInput.get(2)));
					ed.setDept(dept);
					
					session.update(ed);
					session.getTransaction().commit();
					System.out.println("\tSuccessfully Updated Employee Details");
					
			} catch(HibernateException e) {
				System.err.println("\tThere was an error in the database: "+e);
			}
			
		} 
	}
	
	private static Employee getEmployee(int id){
		Session session;
		Employee emp = null;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			emp = (Employee)session.get(Employee.class, id);
			System.out.println("\t"+emp.getId()+" - "+emp.getLastName()+", "+emp.getFirstName()+" - "+emp.getDept().getDeptName());
			session.getTransaction().rollback();
			return emp;
		} catch(HibernateException e) {
			System.err.println("\tThere was an error in the database: "+e);
		}
		
		return emp;
	}
	
	private static List<String> getUserInput(){
		List<String> userInput = new ArrayList<String>();
		
		try{
			String fname;
			String lname;
			int age;
			int deptId;
			Session session = null;
			Scanner sc = new Scanner(System.in);
			
			System.out.print("\tEnter first name :$ ");
			fname = sc.nextLine();
			
			
			System.out.print("\tEnter last name :$ ");
			lname = sc.nextLine();
			
			
			System.out.print("\tEnter age :$ ");
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next();
			}
			age = sc.nextInt();
			
			
			System.out.println("\tChoose Department");
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.getTransaction().begin();
			List<Department> all = session.createQuery("from Department").list();
			for (Department dep: all){
				System.out.println("\t["+dep.getId()+"] "+dep.getDeptName()+"  ");
			}
			System.out.print("\t: $ ");
			session.getTransaction().rollback();
		
		
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next(); 
			}
			deptId = sc.nextInt();
			
			while (deptId < 1 || deptId > all.size()) {
				System.out.print("\tPlease choose one of the choices :$ ");
				while (!sc.hasNextInt()){
					System.out.print("\tPlease enter a valid number :$ ");
					sc.next(); 
				}
				deptId = sc.nextInt();
			}
			
			
			userInput.add(fname);
			userInput.add(lname);
			userInput.add(Integer.toString(age));
			userInput.add(Integer.toString(deptId));
			
			return userInput;
		} catch (HibernateException e){
			System.err.println("There was an error in the database: "+ e);
		}
		return userInput;
	}
}
