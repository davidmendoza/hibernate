package com.hibernate.EmpProfile.Logic;

import java.util.Scanner;

public class ProjectMenu {

	public void startProjectManagement(){
		
		int choice;
		Scanner sc = new Scanner(System.in);
		ProjectLogic projLogic = new ProjectLogic();
		System.out.println("\tManage Projects");
		do {
			System.out.println("\n[1] Add a New Project");
			System.out.println("[2] Add Employee to Project");
			System.out.println("[3] View Project Teams");
			System.out.println("[4] Remove Employee from Project");
			System.out.println("[5] Remove Project");
			System.out.println("[0] Back to Main Menu");
			System.out.print(":$ ");
			
			while (!sc.hasNextInt()){
				System.out.print("Please enter a valid number :$ ");
				sc.next();
			}
			
			choice = sc.nextInt();
			switch(choice){
			case 1:
				projLogic.addProject();
			break;
			
			case 2:
				projLogic.addEmployeeToProject();
			break;
			
			case 3:
				projLogic.viewProjectTeams();
			break;
			
			case 4:
				projLogic.removeEmployeeFromProject();
			break;
			
			case 5:
				projLogic.removeProject();
			break;
			
			default:
			break;
			}
			
		} while (choice != 0);
	}
}
