package com.hibernate.EmpProfile.Logic;

import java.util.Scanner;

public class EmployeeMenu {
	
	public static void startProgram(){
		
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
			System.out.print(":$ ");
			
			while (!sc.hasNextInt()){
				System.out.print("Please enter a valid number :$ ");
				sc.next();
			}
			
			choice = sc.nextInt();
			switch(choice){
			case 1:
				empLogic.add();
			break;
			
			case 2:
				empLogic.view();
			break;
			
			case 3:
				empLogic.delete();
			break;
			
			case 4:
				empLogic.update();
			break;
			
			case 5:
				projMenu.startProjectManagement();
			break;
			
			default:
			break;
			}
			
		} while (choice != 0);
		sc.close();
		System.out.println("Goodbye!");
	}

}
