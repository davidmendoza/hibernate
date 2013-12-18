package com.hibernate.EmpProfile.Logic;

import java.util.Scanner;

public class ProjectMenu {

	private static final int ADD_NEW_PROJECT = 1;
	private static final int ADD_EMPLOYEE_TO_PROJECT = 2;
	private static final int VIEW_PROJECT_TEAMS = 3;
	private static final int REMOVE_EMPLOYEE_FROM_PROJECT = 4;
	private static final int REMOVE_PROJECT = 5;
	private static final int CHECK_PROJECTS_PER_EMPLOYEE = 6;
	
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
			System.out.println("[6] Check Projects per Employee");
			System.out.println("[0] Back to Main Menu");
			System.out.print(":$ ");
			
			choice = EmployeeMenu.checkIntInput(sc);
			
			switch(choice){
			case ADD_NEW_PROJECT:
				projLogic.addProject();
			break;
			
			case ADD_EMPLOYEE_TO_PROJECT:
				projLogic.addEmployeeToProject();
			break;
			
			case VIEW_PROJECT_TEAMS:
				projLogic.viewProjectTeams();
			break;
			
			case REMOVE_EMPLOYEE_FROM_PROJECT:
				projLogic.removeEmployeeFromProject();
			break;
			
			case REMOVE_PROJECT:
				projLogic.removeProject();
			break;
			
			case CHECK_PROJECTS_PER_EMPLOYEE:
				projLogic.projPerEmployee();
			break;
			default:
			break;
			}
			
		} while (choice != 0);
	}
}
