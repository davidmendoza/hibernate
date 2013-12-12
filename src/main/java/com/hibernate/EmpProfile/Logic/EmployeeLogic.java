package com.hibernate.EmpProfile.Logic;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.EmpProfile.Domain.EmployeeDetails;

public class EmployeeLogic {

	
	public void add(){
		Session session = EntryPoint.getSession();
		session.getTransaction().begin();
		
		String fname;
		String lname;
		int age;
		int deptId;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n\tEnter Employee Details: ");
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
		System.out.print("\t[1]HR [2]Admin [3]QA [4]Dev :$ ");
		OUTERLOOP:
		do {
			
			while (!sc.hasNextInt()){
				System.out.print("\tPlease enter a valid number :$ ");
				sc.next(); 
			}
			
			deptId = sc.nextInt();
			
			if (deptId > 0 && deptId < 5){
				break OUTERLOOP; 
			} 
			
			System.out.print("\tPlease choose one of the choices :$ ");
			
		} while (deptId < 1 || deptId > 4);
		
		
		session.save(new EmployeeDetails(fname,lname,age,deptId));
		session.getTransaction().commit();
	
		System.out.println("Success!");
		
	}
	
	public static void view(){
		
	}
	
	public static void delete(){
		
	}
	
	public static void update(){
		
	}
}
