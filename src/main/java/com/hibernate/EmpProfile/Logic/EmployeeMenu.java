package com.hibernate.EmpProfile.Logic;

import java.util.Scanner;

public class EmployeeMenu {
	private static final int ADD_EMPLOYEES = 1;
	private static final int VIEW_EMPLOYEES = 2;
	private static final int DELETE_EMPLOYEES = 3;
	private static final int UPDATE_EMPLOYEES = 4;
	private static final int MANAGE_PROJECTS = 5;
	private static final int TEST_HQL = 6;
	
	public static void startProgram() {
		
		int choice;
		Scanner sc = new Scanner(System.in);
		EmployeeLogic empLogic = new EmployeeLogic();
		ProjectMenu projMenu = new ProjectMenu();
		
		System.out.println("Employee Profile Application Using Hibernate");
		
		do {
			System.out.println("\n[1] Add New Employee");
			System.out.println("[2] View Employee/s");
			System.out.println("[3] Delete Employee/s");
			System.out.println("[4] Update Employee Details");
			System.out.println("[5] Manage Projects");
			System.out.println("[6] Test HQL");
			System.out.print(":$ ");
			
			choice = checkIntInput(sc);
			
			switch(choice){
			case ADD_EMPLOYEES:
				empLogic.add();
			break;
			
			case VIEW_EMPLOYEES:
				empLogic.view();
			break;
			
			case DELETE_EMPLOYEES:
				empLogic.delete();
			break;
			
			case UPDATE_EMPLOYEES:
				empLogic.update();
			break;
			
			case MANAGE_PROJECTS:
				projMenu.startProjectManagement();
			break;
			
			case TEST_HQL:
				TestSearch.testSearch();
			break;
			default:
			break;
			}
			
		} while (choice != 0);
		sc.close();
		System.out.println("Goodbye!");
	}
	
	public static int checkIntInput(Scanner sc){
		
		int choice;
		while (!sc.hasNextInt()){
			System.out.print("\tPlease enter a valid number :$ ");
			sc.next();
		}
		
		choice = sc.nextInt();
		
		return choice;
	}

}
