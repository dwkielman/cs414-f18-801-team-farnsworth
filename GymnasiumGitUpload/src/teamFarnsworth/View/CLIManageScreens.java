package teamFarnsworth.View;

public class CLIManageScreens {

	// Manage Trainers or Customers, including adding new Users and Modifying current ones
	// only accessible to Trainers and Managers
	public static void managePerson(String accountType) {
		String option1 = "";
		
		if (accountType == "Trainer") {
			option1 = "Hire";
		}
		if (accountType == "Customer") {
			option1 = "Register";
		}
		System.out.println("[1] " + option1 + " " + accountType + "\n" + 
				"[2] Modify " + accountType + "\n" + 
				"[3] Return to Previous Screen\n" 
				);
		int answer = CLI.getIntSelection(1, 3);
		switch (answer) {
		case 1: CLICreatorScreens.createUser(accountType); break;
		case 2: CLI.modifyUserScreen(accountType); break;
		case 3: return;
		}
		managePerson(accountType);
	}

	/**
	 * Gym Entities Views
	 */
	// Main Equipment Selections Screen, only accessible by Managers
	public static void manageEquipmentScreen() {
		System.out.println("[1] Display All Equipment\n" + 
				"[2] Add Equipment\n" + 
				"[3] Modify Equipment\n" +
				"[4] Return\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(CLI.equipmentController.toStringEquipment());
				break;
			case 2: CLICreatorScreens.createEquipment(""); break;
			case 3: CLI.modifyEquipment(); break;
			case 4: return;
		}
		manageEquipmentScreen();
	}

	// Main Routine Selections Screen
	public static void manageRoutineScreen() {
		System.out.println("[1] Display All Routines\n" + 
				"[2] Add Routine\n" + 
				"[3] Modify Routine\n" +
				"[4] Return\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(CLI.routineController.toStringRoutines());
				break;
			case 2: CLICreatorScreens.createRoutine(); break;
			case 3: CLI.modifyRoutine(); break;
			case 4: return;
		}
		manageRoutineScreen();
	}

	// Main Workout classes Selections Screen, only accessible by Trainers
	public static void manageWorkoutClassesScreen() {
		System.out.println("[1] Display All Classes\n" + 
				"[2] Add Class\n" + 
				"[3] Modify Class\n" +
				"[4] Return\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(CLI.workoutClassController.toStringWorkoutClasses());
				break;
			case 2: CLICreatorScreens.createWorkoutClass(""); break;
			case 3: CLI.modifyWorkoutClass(); break;
			case 4: return;
		}
		manageWorkoutClassesScreen();
	}

	// Main Exercise Selections Screen
	public static void manageExerciseScreen() {
		System.out.println("[1] Display All Exercises\n" + 
				"[2] Add Exercise\n" + 
				"[3] Modify Exercise\n" +
				"[4] Return\n"
				);
		int answer = CLI.getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(CLI.exerciseController.toStringExercises());
				break;
			case 2: CLICreatorScreens.createExercise(); break;
			case 3: CLI.modifyExercise(); break;
			case 4: return;
		}
		manageExerciseScreen();
	}

}
