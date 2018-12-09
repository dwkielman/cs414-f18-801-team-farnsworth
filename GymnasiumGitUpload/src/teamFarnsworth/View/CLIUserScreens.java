package teamFarnsworth.View;

import teamFarnsworth.Domain.Users.Customer;

public class CLIUserScreens {

	// Manager Main Screen
	public static void managerScreen() {
		System.out.println("[1] Manage Trainers\n" + 
				"[2] Manage Customers\n" +
				"[3] Manage Equipment\n" + 
				"[4] Logout\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: CLIManageScreens.managePerson("Trainer"); break;
			case 2: CLIManageScreens.managePerson("Customer"); break;
			case 3: CLIManageScreens.manageEquipmentScreen(); break;
			case 4: return;
		}
		managerScreen();
	}

	// Trainer Main Screen
	public static void trainerScreen() {
		System.out.println("[1] View Schedule\n" + 
				"[2] Manage Customers\n" + 
				"[3] Manage Classes\n" +
				"[4] Manage Routines\n" + 
				"[5] Manage Exercises\n" + 
				"[6] Logout\n"
				);
		int answer = CLI.getIntSelection(1, 6);
		switch (answer) {
			case 1: CLI.trainerScheduleScreen(); break;
			case 2: CLIManageScreens.managePerson("Customer"); break;
			case 3: CLIManageScreens.manageWorkoutClassesScreen(); break;
			case 4: CLIManageScreens.manageRoutineScreen(); break;
			case 5: CLIManageScreens.manageExerciseScreen(); break;
			case 6: return;
		}
		trainerScreen();
	}

	// Customer Main Screen
	public static void customerScreen() {
		System.out.println("[1] View Assigned Routines\n" +
				"[2] Manage Private Trainer\n" +
				"[3] View All Classes\n" + 
				"[4] View Enrolled Classes\n" + 
				"[5] Manage Classes\n" +
				"[6] Logout\n"
				);
		int answer = CLI.getIntSelection(1, 6);
		switch (answer) {
			case 1: CLIUserScreens.customerRoutinesScreen(); break;
			case 2: CLIUserScreens.customerTrainerScreen(); break;
			case 3: 
				System.out.println(CLI.workoutClassController.toStringWorkoutClasses());
				break;
			case 4: CLIUserScreens.customerClassesScreen(); break;
			case 5: CLI.customerManageClasses(); break;
			case 6: return;
		}
		customerScreen();
	}

	// Customer's Workout Class Schedule
	public static void customerClassesScreen() {
		Customer c = CLI.customerController.getUser(CLI.currentUserId);
		if (!c.getEnrolledClasses().isEmpty()) {
			System.out.println(c.workoutClassesToString());
		} else {
			System.out.println("You are currently not enrolled in any classes");
		}
	}
	
	// Customer's Routines
	public static void customerRoutinesScreen() {
		Customer c = CLI.customerController.getUser(CLI.currentUserId);
		if (!c.getRoutines().isEmpty()) {
			System.out.println(c.routinesToString());
		} else {
			System.out.println("You are currently not assigned any Routines.");
		}
	}
	
	// Customer's Enrollment with Trainers Screen
	public static void customerTrainerScreen() {
		System.out.println("[1] View Current Trainer\n" +
				"[2] Enroll With a Trainer\n" + 
				"[3] Unenroll From a Trainer\n" + 
				"[4] Return to Previous Screen\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: CLI.viewCurrentTrainer(); break;
			case 2: CLI.enrollCustomerWithTrainer(); break;
			case 3: CLI.unenrollCustomerWithTrainer(); break;
			case 4: return;
		}
		customerTrainerScreen();
	}

}
