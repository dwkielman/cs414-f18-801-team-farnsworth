package teamFarnsworth.View;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import teamFarnsworth.Application.Controllers.CustomerController;
import teamFarnsworth.Application.Controllers.EquipmentController;
import teamFarnsworth.Application.Controllers.ExerciseController;
import teamFarnsworth.Application.Controllers.ManagerController;
import teamFarnsworth.Application.Controllers.RoutineController;
import teamFarnsworth.Application.Controllers.TrainerController;
import teamFarnsworth.Application.Controllers.UserController;
import teamFarnsworth.Application.Controllers.UserControllerFactory;
import teamFarnsworth.Application.Controllers.WorkoutClassController;
import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Domain.Entities.WorkoutClass;
import teamFarnsworth.Domain.Entities.WorkoutSet;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.HealthInsuranceProvider;
import teamFarnsworth.Domain.Users.Membership;
import teamFarnsworth.Domain.Users.Password;
import teamFarnsworth.Domain.Users.Person;
import teamFarnsworth.Domain.Users.Qualification;
import teamFarnsworth.Domain.Users.Trainer;
import teamFarnsworth.Handlers.SystemHandlers.LoginHandler;

public class CLI {

	private static Scanner scanner = new Scanner(System.in);
	static TrainerController trainerController;
	static CustomerController customerController;
	private static ManagerController managerController;
	static EquipmentController equipmentController;
	static ExerciseController exerciseController;
	static RoutineController routineController;
	static WorkoutClassController workoutClassController;
	static LoginHandler loginHandler;
	
	static String currentUserId;
	
	/**
	 * Login View
	 */
	// Main Login Screen all users must start at
	public static void login() {
		String ID = getString("Enter Username: \n", "Please enter a valid Username");
		String password = getString("Enter Password", "Please enter a valid Password");
		Password p = new Password(password);
		if (loginHandler.login(ID, p)) {
			currentUserId = ID;
			switch (loginHandler.getAccountType(ID)) {
				case ("Admin"): adminScreen(); break;
				case ("Manager"): CLIUserScreens.managerScreen(); break;
				case ("Trainer"): CLIUserScreens.trainerScreen(); break;
				case ("Customer"): CLIUserScreens.customerScreen(); break;
			}
		}
		System.out.println("Goodbye");
	}
	
	/*
	 * User Views
	 */
	// administrator screen to login as any user type
	public static void adminScreen() {
		System.out.println("Simulate User Experience as a: \n" +
				"[1] Manager\n" + 
				"[2] Trainer\n" +
				"[3] Customer\n" + 
				"[4] Logout\n"
				);
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: 
				loginHandler.linkAccountType("admin", "Manager");
				CLIUserScreens.managerScreen(); break;
			case 2: 
				loginHandler.linkAccountType("admin", "Trainer");
				CLIUserScreens.trainerScreen(); break;
			case 3: 
				loginHandler.linkAccountType("admin", "Customer");
				CLIUserScreens.customerScreen(); break;
			case 4: return;
		}
	}
	
	// Trainer's Schedule
	public static void trainerScheduleScreen() {
		Trainer t = trainerController.getUser(currentUserId);
		if (t.getSchedule().size() > 0) {
			for (GymHours gh : t.getSchedule()) {
				System.out.println(gh.toString());
			}
		} else {
			System.out.println("You are currently not scheduled to teach any classes.");
		}
		
	}
	
	//
	public static void customerManageClasses() {
		System.out.println("[1] Enroll In Class\n" + 
				"[2] Unenroll From Class\n" + 
				"[3] Return\n"
				);
		int answer = getIntSelection(1, 3);
		switch (answer) {
			case 1: enrollInClass(); break;
			case 2: unenrollFromClass(); break;
			case 3: return;
		}
		customerManageClasses();
	}

	/**
	 * User Modification/Editing Views
	 */
	// Selections for how the user wishes to find the User Type
	public static void modifyUserScreen(String accountType) {
		System.out.println("[1] Display All " + accountType + "s\n" + 
				"[2] Search for " + accountType + " by ID\n" + 
				"[3] Select " + accountType + " from List\n" + 
				"[4] Return to Previous Screen\n" 
				);
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: displayAllUsers(accountType); break;
			case 2: findUser(accountType); break;
			case 3: findFromAllUsers(accountType); break;
			case 4: return;
		}
		modifyUserScreen(accountType);
	}
	
	// prints all of the users in the system of passed account type
	public static void displayAllUsers(String accountType) {
		UserControllerFactory ucf = new UserControllerFactory();
		UserController<?> uc = ucf.createController(accountType);
		System.out.println(uc.getAllUsers());
	}
	
	// prints all of the users of a passes account type and allows the user to select one from the list to edit (experimenting)
	public static void findFromAllUsers(String accountType) {
		UserControllerFactory ucf = new UserControllerFactory();
		UserController<?> uc = ucf.createController(accountType);
		
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		Set<?> users = uc.getUsers();
		int index = 1;
		for (Object u : users) {
			if (u instanceof Person) {
				Person p = (Person) u;
				System.out.println("[" + index + "] " + p.toStringBrief());
				userMap.put(index, ((Person) u).getID());
				index++;
			}
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		if (answer >= 1 && answer < index) {
			Person p = uc.getUser(userMap.get(answer));
			if (p != null) {
				System.out.println(p.toString());
				boolean editUser = getBooleanInput("Edit this User? (Y or N): ", "Please Enter a Valid Selection");
				if (editUser) {
					modifyUser(p);
				}
			}
		}
		
		if (answer == index) {
			return;
		}
		return;
	}
	
	// Find the User of Passed Account Type by their unique ID
	public static void findUser(String accountType) {
		UserControllerFactory ucf = new UserControllerFactory();
		UserController<?> uc = ucf.createController(accountType);
		String input = getString("Enter the ID to Search for:", "Please enter a valid ID");
		
		Person p = uc.getUser(input);
		if (p != null) {
			System.out.println(p.toString());
			boolean editUser = getBooleanInput("Edit this User? (Y or N): ", "Please Enter a Valid Selection");
			if (editUser) {
				modifyUser(p);
			}
		}
	}
	
	// Selections for Modifying the Values of a User
	public static void modifyUser(Person p) {
		String userSpecificOptions = "";
		int max = 7;
		if (p.getClass().equals(Trainer.class)) {
			userSpecificOptions = "[8] Qualifications\n" + 
					"[9] Work Hours\n" + 
					"[10] Return to Previous Screen\n";
			max = 10;
		} else if (p.getClass().equals(Customer.class)) {
			userSpecificOptions = "[8] Membership Status\n" + 
					"[9] Return to Previous Screen\n";
			max = 9;
		}
		
		System.out.println("Enter Number for Value you Want to Edit:\n" + 
				"[1] First Name\n" + 
				"[2] Last Name\n" +  
				"[3] Phone Number\n" + 
				"[4] Email\n" + 
				"[5] Address\n" + 
				"[6] Health Insurance Provider\n" + 
				"[7] Password\n" + 
				userSpecificOptions
				);
		int answer = getIntSelection(1, max);
		editUserDetailsScreen(p, answer);
	}
	
	// Edits passed field of the passed user
	public static void editUserDetailsScreen(Person p, int input) {
		switch(input) {
		case 1:
			System.out.println("Current First Name is: " + p.getFirstName());
			p.setFirstName(getString("Enter a New First Name: ", "Please Enter a Valid First Name"));
			break;
		case 2:
			System.out.println("Current Last Name is: " + p.getLastName());
			p.setLastName(getString("Enter a New Last Name: ", "Please Enter a Valid Last Name"));
			break;
		case 3:
			System.out.println("Current Phone Number is: " + p.getPhone());
			String pNumber = "";	
			while (pNumber.length() != 10 || !(pNumber.matches("[0-9]+"))) {
				pNumber = getString("Enter a New Phone Number: ", "Please Enter a Valid Phone Number");
			}
			p.setPhone(pNumber);
			break;
		case 4:
			System.out.println("Current Email Address is: " + p.getEmail());
			p.setEmail(getString("Enter a New Email Address: ", "Please Enter a Valid Email Address"));
			break;
		case 5:
			System.out.println("Current Address is: " + p.getAddress().toString());
			System.out.println("Which Part of the Address would you like to Edit?:\n" + 
					"[1] Street\n" + 
					"[2] City\n" +  
					"[3] State\n" + 
					"[4] Zip Code\n" + 
					"[5] No Modifications\n"
					);
			int answer = getIntSelection(1, 5);
			String stringField = "";
			int intField = 0;
			switch(answer) {
				case 1:
					stringField = getString("Enter a New Street: ", "Please Enter a Valid Street");
					p.getAddress().setStreet(stringField);
					break;
				case 2:
					stringField = getString("Enter a New City: ", "Please Enter a Valid City");
					p.getAddress().setCity(stringField);
					break;
				case 3:
					stringField = getString("Enter a New State: ", "Please Enter a Valid State");
					p.getAddress().setState(stringField);
					break;
				case 4:
					while (String.valueOf(intField).length() != 5) {
						intField = getInt("Enter a New Zip Code: ", "Please Enter a Valid Zip Code");
					}
					p.getAddress().setZip(intField);
					break;
				case 5:
					break;
			}
			break;
		case 6:
			System.out.println("Current Health Insurance Provider is: " + p.getHealthInsuranceProvider());
			p.setHealthInsuranceProvider(new HealthInsuranceProvider(getString("Enter a New Health Insurance Provider: ", "Please Enter a Valid Health Insurance Provider")));
			break;
		case 7:
			System.out.println("Current Password is: " + p.getPassword());
			p.setPassword(getString("Enter a New Password: ", "Please Enter a Valid Password"));
			break;
		}
		
		// Trainer only options
		if (p.getClass().equals(Trainer.class)) {
			Trainer t = (Trainer) p;
			switch(input) {
		case 8:
			if (t.getQualifications().size() == 1) {
				System.out.println("Current Qualification is: " + t.getQualifications().toString());
			} else if (t.getQualifications().size() > 1) {
				System.out.println("Current Qualification Are: " + t.getQualifications().toString());
			} else if (t.getQualifications().size() == 0) {
				System.out.println("Currently this Trainer has No Qualifications Logged");
			}
			t.addQualification(new Qualification(getString("Enter a New Qualification: ", "Please Enter a Valid Qualification")));
			break;
		case 9:
			if (t.getSchedule().size() > 0) {
				System.out.println("Trainer's Current Schedule is:");
				for (GymHours gh : t.getSchedule()) {
					System.out.println(gh.toString());
				}
			} else {
				System.out.println("Trainer is currently not scheduled to teach any classes.");
			}
			System.out.println("What do you want to do modify in this Trainer's schedule?:\n" + 
					"[1] Add Hours\n" + 
					"[2] Remove Hours\n" +  
					"[3] Clear Entire Schedule\n" + 
					"[4] No Change\n");
			int scheduleAnswer = getIntSelection(1, 4);
			boolean modSchedule = false;
			switch (scheduleAnswer) {
			case 1:
				GymHours gh = addTimeEntry();
				boolean isDuplicateDays = false;
				if (t.getSchedule() != null) {
					for (GymHours scheduleGymHours : t.getSchedule()) {
						if (scheduleGymHours.getDay().equals(gh.getDay())) {
							isDuplicateDays = true;
							modSchedule = getBooleanInput("Trainer already works on this Day. Do you want to change it to the new hours entered? (Y or N): ", "Please Enter a Valid Selection");
							if (modSchedule) {
								t.removeWorkHoursFromSchedule(scheduleGymHours);
								trainerController.setSchedule(t, gh);
								System.out.println("The Trainer's Schedule has been updated.");
							}
						}
					}
					if (isDuplicateDays == false) {
						trainerController.setSchedule(t, gh);
						System.out.println("The Trainer's Schedule has been updated.");
					}
				} else {
					trainerController.setSchedule(t, gh);
					System.out.println("The Trainer's Schedule has been updated.");
				}
				break;
			case 2:
				if (t.getSchedule().isEmpty()) {
					System.out.println("Trainer is currently not scheduled to teach any classes.");
				} else {
					Map<Integer, GymHours> gymHoursMap = new HashMap<Integer, GymHours>();
					System.out.println("Select the Number of the Corresponding Day of this Trainer's Schedule to Modify: ");
					int index = 1;
					for (GymHours trainerHours : t.getSchedule()) {
						System.out.println("[" + index + "] " + trainerHours.toString());
						gymHoursMap.put(index, trainerHours);
						index++;
					}
					System.out.println("[" + index + "] Return to Previous Screen");
					int answer = getIntSelection(1, index);
					
					if (answer >= 1 && answer < index) {
						GymHours gymHoursToRemove = gymHoursMap.get(answer);
						modSchedule = getBooleanInput("Are you sure you want to remove the selected Hours from the Trainer's Schedule? (Y or N): ", "Please Enter a Valid Selection");
						if (modSchedule) {
							t.removeWorkHoursFromSchedule(gymHoursToRemove);
							System.out.println("The Trainer's Schedule has been updated.");
						}
					}
				}
				break;
			case 3:
				modSchedule = getBooleanInput("Are you sure you want to clear this Trainer's entire Schedule? (Y or N): ", "Please Enter a Valid Selection");
				if (modSchedule) {
					t.clearSchedule();
					System.out.println("The Trainer's Schedule has been cleared.");
				}
				break;
			case 4: return;
			}
		case 10: return;
			}
		}
		
		// Customer only options
		if (p.getClass().equals(Customer.class)) {
			Customer c = (Customer) p;
			switch(input) {
		case 8:
			System.out.println("Currently this Customer is: " + c.getMembership());
			System.out.println("What Status do you want to change the Customer's Membership to?:\n" + 
					"[1] Basic\n" + 
					"[2] Regular\n" +  
					"[3] Premium\n" + 
					"[4] Inactive\n" +
					"[5] No Changes\n");
			int statusAnswer = getIntSelection(1, 5);
			boolean isActive = false;
			switch(statusAnswer) {
				case 1:
					if (!c.getMembership().equals(Membership.BASIC)) {
						isActive = getBooleanInput("Are you sure you want this Customer to have a Basic Status? (Y or N): ", "Please Enter a Valid Selection");
						if (isActive) {
							customerController.setMembershipStatus(c, Membership.BASIC);
						}
					} else {
						System.out.println("Customer is already Basic Status. No Change has been made.");
					}
					break;
				case 2:
					if (!c.getMembership().equals(Membership.REGULAR)) {
						isActive = getBooleanInput("Are you sure you want this Customer to have a Regular Status? (Y or N): ", "Please Enter a Valid Selection");
						if (isActive) {
							customerController.setMembershipStatus(c, Membership.REGULAR);
						}
					} else {
						System.out.println("Customer is already Regular Status. No Change has been made.");
					}
					break;
				case 3:
					if (!c.getMembership().equals(Membership.PREMIUM)) {
						isActive = getBooleanInput("Are you sure you want this Customer to have a Premium Status? (Y or N): ", "Please Enter a Valid Selection");
						if (isActive) {
							customerController.setMembershipStatus(c, Membership.PREMIUM);
						}
					} else {
						System.out.println("Customer is already Premium Status. No Change has been made.");
					}
					break;
				case 4:
					if (!c.getMembership().equals(Membership.INACTIVE)) {
						isActive = getBooleanInput("Are you sure you want this Customer to have an Inactive Status? (Y or N): ", "Please Enter a Valid Selection");
						if (isActive) {
							customerController.setMembershipStatus(c, Membership.INACTIVE);
						}
					} else {
						System.out.println("Customer is already Inactive Status. No Change has been made.");
					}
					break;
				case 5: return;
			}
		case 9: return;
			}
		}
	}
	
	/**
	 * Gym Entities Modification/Editing Views
	 */
	// Modifies an existing Equipment, only accessible by Managers or Trainers
	public static void modifyEquipment() {
		//System.out.println("Current Equipment in Gym:");
		//System.out.println(equipmentController.toStringEquipment());
		Map<Integer, Equipment> equipmentMap = new HashMap<Integer, Equipment>();
		Set<Equipment> allEquipment = equipmentController.getAllEquipment();
		System.out.println("Select the Number of the Corresponding Equipment to Modify: ");
		int index = 1;
		for (Equipment equip : allEquipment) {
			System.out.println("[" + index + "] " + equip.toString());
			equipmentMap.put(index, equip);
			index++;
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			Equipment e = equipmentController.getEquipment(equipmentMap.get(answer).getName());
			if (e == null) {
				System.out.println("The piece of Equipment you searched for does not Exist");
				boolean addEquipment = getBooleanInput("Would you like to Add this Equipment? (Y or N): ", "Please Enter a Valid Selection");
				if (addEquipment) {
					CLICreatorScreens.createEquipment(e.getName());
				}
			} else {
				System.out.println(e.toString());
				System.out.println("What Part of the Equipment would you like to Modify?\n" + 
						"[1] Name\n" + 
						"[2] Picture\n" +  
						"[3] Quantity\n" + 
						"[4] Delete Equipment\n" + 
						"[5] No Modifications\n"
						);
				answer = getIntSelection(1, 5);
				String stringField = "";
				int intField = 0;
				switch(answer) {
				case 1:
					stringField = getString("Enter the New Name of this piece of Equipment you wish to Modify: ", "Please Enter a Valid Equipment Name");
					boolean canModify = true;
					for (Routine r : routineController.getRoutines()) {
						if (r.getExercises().contains(e)) {
							System.out.println("Equipment is currently in use by Exercise " + r.getName() + ", please Remove it from a Routine before Modifying the name");
							canModify = false;
						}
					}
					if (canModify) {
						equipmentController.removeEquipment(e);
						e.setName(stringField);
						equipmentController.addEquipment(e);
						System.out.println("Equipemnt " + e.getName() + "'s Name has been Updated.");
					}
					break;
				case 2:
					stringField = getString("Enter the New Name of the Picture File Location of Equipment you wish to Modify: ", "Please Enter a Valid File Location");
					e.setPicturePath(stringField);
					System.out.println("Equipemnt " + e.getName() + "'s Photo been Updated.");
					break;
				case 3:
					while (intField <= 0) {
						intField = getInt("Enter a New Quantity of this Equipment: ", "Please Enter a Valid Quantity");
					}
					e.setQuantity(intField);
					System.out.println("Equipemnt " + e.getName() + "'s Quantity been Updated.");
					break;
				case 4:
					boolean deleteEquipment = getBooleanInput("Are you sure you would like to delete this Equipment? (Y or N): ", "Please Enter a Valid Selection");
					if (deleteEquipment) {
						equipmentController.removeEquipment(e);
						System.out.println("Equipment has been removed from the Gym");
					}
				case 5:
					return;
				}
			}
		} else if (answer == index) {
			return;
		}
		
	}
	
	// Modifies an existing Routine, only accessible by Trainers
	public static void modifyRoutine() {
		//System.out.println("Current Routines in Gym:");
		//System.out.println(routineController.toStringRoutines());
		Map<Integer, Routine> routineMap = new HashMap<Integer, Routine>();
		Set<Routine> allRoutines = routineController.getRoutines();
		System.out.println("Select the Number of the Corresponding Routine to Modify: ");
		int index = 1;
		for (Routine rou : allRoutines) {
			System.out.println("[" + index + "] " + rou.toString());
			routineMap.put(index, rou);
			index++;
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		int selection = 0;
		
		Map<Integer, Exercise> exerciseMap = new HashMap<Integer, Exercise>();
		if (answer >= 1 && answer < index) {
			Routine r = routineController.getRoutine(routineMap.get(answer));
			if (r == null) {
				System.out.println("The Routine you selected does not seem to Exist");
			} else {
				System.out.println(r.toString());
				if (r.getExercises().size() > 0) {
					for (Exercise e : r.getExercises()) {
						System.out.println(e);
					}
				}

				System.out.println("What Part of the Routine would you like to Modify?\n" + 
						"[1] Add Exercises\n" +
						"[2] Remove Exercises\n" +
						"[3] Delete Routine\n" + 
						"[4] No Modifications\n"
						);
				answer = getIntSelection(1, 4);
				switch(answer) {
				case 1:
					System.out.println("Select the Number of the Corresponding Exercise to Add to this Routine: ");
					selection = 1;
					for (Exercise exer : exerciseController.getExercises()) {
						if (!r.getExercises().contains(exer)) {
							System.out.println("[" + selection + "] " + exer.toString());
							exerciseMap.put(selection, exer);
							selection++;
						}
					}
					System.out.println("[" + selection + "] Mo Modification");
					answer = getIntSelection(1, selection);
					
					if (answer >= 1 && answer < selection) {
						Exercise selectedExercise = exerciseMap.get(answer);
						
						if (r.getExercises().contains(selectedExercise)) {
							System.out.println("The Exercise you selected seems to already be a part of this Routine");
						} else {
							boolean addExerciseToRoutine = getBooleanInput("Are you sure you would like to Add this Exercise to the Routine? (Y or N): ", "Please Enter a Valid Selection");
							if (addExerciseToRoutine) {
								routineController.addExerciseToRoutine(r.getName(), selectedExercise);
								System.out.println("Exercise has been added to the Routine");
							}
						}
					}
					break;
				case 2:
					if (r.getExercises().size() == 1) {
						System.out.println("Routine must have an Exercise. If you wish to remove this Exercise, please Add an Exercise to the Routine first");
						break;
					}
					
					System.out.println("Select the Number of the Corresponding Exercise to Remove in this Routine: ");
					selection = 1;
					for (Exercise exer : r.getExercises()) {
						System.out.println("[" + selection + "] " + exer.toString());
						exerciseMap.put(selection, exer);
						selection++;
					}
					System.out.println("[" + selection + "] Mo Modification");
					answer = getIntSelection(1, selection);
					
					if (answer >= 1 && answer < selection) {
						Exercise selectedExercise = exerciseMap.get(answer);
						
						boolean removeExercise = getBooleanInput("Are you sure you would like to Remove this Exercise from the Routine? (Y or N): ", "Please Enter a Valid Selection");
						if (removeExercise) {
							routineController.removeExerciseFromRoutine(r.getName(), selectedExercise);
							System.out.println("Exercise has been removed from the Routine");
						}
					}
					break;
				case 3:
					boolean deleteRoutine = getBooleanInput("Are you sure you would like to delete this Routine? (Y or N): ", "Please Enter a Valid Selection");
					if (deleteRoutine) {
						routineController.removeRoutine(r);
						System.out.println("Routine has been removed from the Gym");
					}
					break;
				case 4:
					return;
				}
			}
		}
	}
	
	// Modifies an existing Workout Class, only accessible by Trainers
	public static void modifyWorkoutClass() {
		Map<Integer, WorkoutClass> workoutClasseMap = new HashMap<Integer, WorkoutClass>();
		Set<WorkoutClass> allWorkoutClasses = workoutClassController.getWorkoutClasses();
		System.out.println("Select the Number of the Corresponding Wokrout Class to Modify: ");
		int index = 1;
		for (WorkoutClass workClass : allWorkoutClasses) {
			System.out.println("[" + index + "] " + workClass.toString());
			workoutClasseMap.put(index, workClass);
			index++;
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			WorkoutClass wc = workoutClasseMap.get(answer);
			if (wc == null) {
				System.out.println("The Workout Class you selected does not seem to Exist");
			} else {
				System.out.println(wc.getName());
				System.out.println("What Part of the Workout Class would you like to Modify?\n" + 
						"[1] Change Time of Class\n" +
						"[2] Delete Workout Class\n" +
						"[3] No Modification\n"
						);
				answer = getIntSelection(1, 3);
				switch(answer) {
				case 1: 
					GymHours gh = addTimeEntry();
					if (gh != null) {
						workoutClassController.getWorkoutClass(wc.getName(), wc.getGymHours()).setGymHours(gh);
						System.out.println("Workout Class " + wc.getName() + " now is at " + gh.toString());
					}
					break;
				case 2:
					boolean deleteWorkoutClass = getBooleanInput("Are you sure you would like to delete this Workout Class? (Y or N): ", "Please Enter a Valid Selection");
					if (deleteWorkoutClass) {
						workoutClassController.removeWorkoutClass(wc);
						System.out.println("Workout Class has been removed from the Gym");
					}
					break;
				case 3:
					return;
				}
			}
		} else if (answer == index) {
			return;
		}
	}
	
	// Modifies an existing Exercise, only accessible by Trainers via a Routine
	public static void modifyExercise() {
		//System.out.println("Exercises in Gym:");
		//System.out.println(exerciseController.toStringExercises());
		Map<Integer, Exercise> exerciseMap = new HashMap<Integer, Exercise>();
		Set<Exercise> allExercisees = exerciseController.getExercises();
		System.out.println("Select the Number of the Corresponding Exercise to Modify: ");
		int index = 1;
		for (Exercise exer : allExercisees) {
			System.out.println("[" + index + "] " + exer.toString());
			exerciseMap.put(index, exer);
			index++;
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			Exercise selectedExercise = exerciseMap.get(answer);
			if (selectedExercise == null) {
				System.out.println("The Exercise you selected does not seem to Exist");
			} else {
				System.out.println(selectedExercise.getName());
				System.out.println("What Part of the Exercise would you like to Modify?\n" + 
						"[1] Modify Duration\n" +
						"[2] Modify Equipemnt\n" +
						"[3] Modify Number of Reps (Currently Not Available)\n" + 
						"[4] Delete Exercise\n" + 
						"[5] No Modification\n"
						);
				answer = getIntSelection(1, 5);
				int intField = 0;
				String equipmentName = "";
				Equipment e = null;
				switch(answer) {
				case 1:
					if (selectedExercise.getDuration() != null) {
						System.out.println("Exercise currently has a Duration of: " + selectedExercise.getDuration().toString());
						intField = getInt("Enter a New Duration for this Exercise: ", "Please Enter a Valid Duration");
						selectedExercise.setDuration(new Duration(intField));
						System.out.println("Duration has been successfully modified");
					} else {
						System.out.println("This Exercise does not have a Duration, please create a new Exercise with a Duration should you wish to have it as an attribute.");
					}
					
					break;
				case 2:
					if (selectedExercise.getEquipment() != null) {
						System.out.println("Exercise currently makes use of the following Equipment:");
						System.out.println(selectedExercise.getEquipment().toString());
					}
					
					System.out.println("Equipement available in Gym: ");
					System.out.println(equipmentController.toStringEquipment());
					equipmentName = getString("Enter the Name of the New piece of Equipment you wish to Use in your Routine: ", "Please Enter a Valid Equipment");
					e = equipmentController.getEquipment(equipmentName);
					if (e == null) {
						System.out.println("This Equipment does not exist. Exercise can not be modified");
					} else {
						selectedExercise.setEquipment(e);
						System.out.println("Equipment has been successfully modified.");
					}
					break;
				case 3:
					if (selectedExercise.getWorkoutSet() != null) {
						System.out.println("Exercise currently has the following sets: ");
						System.out.println(selectedExercise.getWorkoutSet().toString());
						boolean addSets = getBooleanInput("Would you like to modify this set? (Y or N)\n(Selecting Y will remove your current Set and you will have to create the whole set): ", "Please Enter a Valid Selection");
						WorkoutSet wSet = new WorkoutSet();
						if (addSets) {
							intField = getInt("Enter the number of Sets: ", "Please Enter a Valid Quantity");
							int repsNumber = 0;
							for (int i = 0; i < intField; i++) {
								repsNumber = getInt("Enter the Number of Reps for Set " + i + 1 + ": ", "Please Enter a Valid Quantity");
								wSet.addRep(repsNumber);
							}
							selectedExercise.setWorkoutSet(wSet);
							System.out.println("Workout Set has been updated");
						}
					} else {
						System.out.println("This Exercise does not have a Set, please create a new Exercise with a Set should you wish to have it as an attribute.");
					}
					break;
				case 4:
					boolean deleteExercise = getBooleanInput("Are you sure you would like to delete this Exercise? (Y or N): ", "Please Enter a Valid Selection");
					if (deleteExercise) {
						exerciseController.removeExercise(selectedExercise);
						System.out.println("Exercise has been removed from the Gym");
					}
				case 5:
					return;
				}
			}
		} else if (answer == index) {
			return;
		}
	}
	
	// only regular and premium  members can enroll with a Trainer
	public static void enrollCustomerWithTrainer() {
		Customer currentC = customerController.getUser(currentUserId);
		
		if (!currentC.getMembership().getStatus().equals("P") && !currentC.getMembership().getStatus().equals("R")) {
			System.out.println("Only Regular and Premium Members can enroll with a Trainer.");
			System.out.println("Please consult a Trainer if you wish to change your Membership status.");
			return;
		}
		
		Map<Integer, Trainer> availableTrainers = new HashMap<Integer, Trainer>();
		int index = 1;
		String selectionsString = "";
		for (Trainer t : trainerController.getUsers()) {
			if (t.getCustomers() != null) {
				if (t.getCustomers().contains(currentC)) {
					System.out.println("You are already enrolled with Trainer " + t.getFirstName() + " " + t.getLastName());
					boolean unenrollTrainer = getBooleanInput("Do you wish to unenroll from this Trainer and select a new one? (Y or N): ", "Please Enter a Valid Selection");
					if (unenrollTrainer) {
						trainerController.unenrollCustomerFromTrainer(t, currentC);
						System.out.println("Customer has been unenrolled from this Trainer.");
					} else {
						System.out.println("No modification to your Trainer has been made.");
						return;
					}
				}
			}
			selectionsString += ("[" + index + "] " + t.getFirstName() + " " + t.getLastName() + "\n");
			availableTrainers.put(index, t);
			index++;
		}
		System.out.println("Please select a corresponding number of an available Trainer in which to enroll: ");
		System.out.print(selectionsString);
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			Trainer selectedTrainer = availableTrainers.get(answer);
			trainerController.enrollCustomerWithTrainer(selectedTrainer, currentC);
			System.out.println("You are now enrolled to Train with " + selectedTrainer.getFirstName() + " " + selectedTrainer.getLastName());
		} else if (answer == index) {
			return;
		}
	}
	
	// only regular and premium  members can unenroll from a Trainer
	public static void viewCurrentTrainer() {
		Customer currentC = customerController.getUser(currentUserId);
		
		if (!currentC.getMembership().getStatus().equals("P") && !currentC.getMembership().getStatus().equals("R")) {
			System.out.println("Only Regular and Premium Members can enroll with a Trainer.");
			System.out.println("Please consult a Trainer if you wish to change your Membership status.");
			return;
		}
		
		if (currentC.getPrivateTrainer() != null) {
			Trainer t = currentC.getPrivateTrainer();
			System.out.println("You are enrolled with Trainer " + t.getFirstName() + " " + t.getLastName());
		} else {
			System.out.println("You are currently not enrolled with a Trainer.");
		}
	}
	
	// only regular and premium  members can unenroll in fitness classes
	public static void unenrollCustomerWithTrainer() {
		Customer currentC = customerController.getUser(currentUserId);
		
		if (!currentC.getMembership().getStatus().equals("P") && !currentC.getMembership().getStatus().equals("R")) {
			System.out.println("Only Regular and Premium Members can enroll with a Trainer.");
			System.out.println("Please consult a Trainer if you wish to change your Membership status.");
			return;
		}
		
		if (currentC.getPrivateTrainer() != null) {
			Trainer t = currentC.getPrivateTrainer();
			System.out.println("You are already enrolled with Trainer " + t.getFirstName() + " " + t.getLastName());
			boolean unenrollTrainer = getBooleanInput("Do you wish to unenroll from this Trainer? (Y or N): ", "Please Enter a Valid Selection");
			if (unenrollTrainer) {
				trainerController.unenrollCustomerFromTrainer(t, currentC);
				System.out.println("Customer has been unenrolled from this Trainer.");
			} else {
				System.out.println("No change has been made.");
			}
		} else {
			System.out.println("You are currently not enrolled with a Trainer.");
		}
	}
	
	// only premium members can enroll in fitness classes
	public static void enrollInClass() {
		Customer currentC = customerController.getUser(currentUserId);
		
		if (!currentC.getMembership().getStatus().equals("P")) {
			System.out.println("Only Premium Members can enroll in Fitness Classes.");
			System.out.println("Please consult a Trainer if you wish to change your Membership status.");
			return;
		}
		
		System.out.println("Please select a corresponding number of an available class in which to enroll: ");
		
		Map<Integer, WorkoutClass> availableClasses = new HashMap<Integer, WorkoutClass>();
		int index = 1;
		for (WorkoutClass workc : workoutClassController.getWorkoutClasses()) {
			if (!workc.getAttendees().contains(currentC)) {
				System.out.println("[" + index + "] " + workc.toString());
				availableClasses.put(index, workc);
				index++;
			}
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			WorkoutClass selectedWorkoutClass = availableClasses.get(answer);
			workoutClassController.getWorkoutClass(selectedWorkoutClass.getName(), selectedWorkoutClass.getGymHours()).enrollInClass(currentC);
			currentC.enrollInClass(selectedWorkoutClass);
			System.out.println("You are now enrolled in the Workout Class " + selectedWorkoutClass.getName());
		} else if (answer == index) {
			return;
		}
	}
	
	// only premium members can unenroll in fitness classes
	public static void unenrollFromClass() {
		Customer currentC = customerController.getUser(currentUserId);
		
		if (!currentC.getMembership().getStatus().equals("P")) {
			System.out.println("Only Premium Members can enroll in Fitness Classes.");
			System.out.println("Please consult a Trainer if you wish to change your Membership status.");
			return;
		} else if (currentC.getEnrolledClasses().isEmpty()) {
			System.out.println("You are currently not enrolled in any classes.");
			return;
		}
		
		System.out.println("Please select a corresponding number of a class you are enrolled in to Unenroll: ");
		
		Map<Integer, WorkoutClass> enrolledClasses = new HashMap<Integer, WorkoutClass>();
		int index = 1;
		
		for (WorkoutClass workc : workoutClassController.getWorkoutClasses()) {
			if (workc.getAttendees().contains(currentC)) {
				System.out.println("[" + index + "] " + workc.toString());
				enrolledClasses.put(index, workc);
				index++;
			}
		}
		System.out.println("[" + index + "] Return to Previous Screen");
		int answer = getIntSelection(1, index);
		
		if (answer >= 1 && answer < index) {
			WorkoutClass selectedWorkoutClass = enrolledClasses.get(answer);
			workoutClassController.getWorkoutClass(selectedWorkoutClass.getName(), selectedWorkoutClass.getGymHours()).unenrollFromClass(currentC);
			currentC.unenrollFromClass(selectedWorkoutClass);
			System.out.println("You are now unenrolled from this Workout Class " + selectedWorkoutClass.getName());
		} else if (answer == index) {
			return;
		}
	}
	/*
	 * Helpers to get responses from user
	 */
	// Receives a String Value from the User
	public static String getString(String prompt, String exMessage) {
		String answer = "";
		while (answer.equals("")) {
			System.out.println(prompt);
			try {
				answer = scanner.nextLine();
				System.out.println();
				
			} catch (Exception e) {
				System.out.println(exMessage);
				answer = "";
				scanner.next();
			}
		}
		return answer;
	}
	
	// Receives an Int Selection for navigating screens from the User
	public static int getIntSelection(int first, int second) {
		int answer = 0;
		while (answer == 0) {
			try {
				answer = scanner.nextInt();
				scanner.nextLine();
				if (answer >= first && answer <= second) {
					//scanner.nextLine();
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println("Please enter a valid numeric selection");
				answer = 0;
				scanner.next();
			}
		}
		return answer;
	}
	
	// Receives an Int Value from the User
	public static int getInt(String prompt, String exMessage) {
		int answer = -1;
		while (answer == -1) {
			System.out.println(prompt);
			try {
				answer = scanner.nextInt();
				if (answer >= 0) {
					scanner.nextLine();
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println(exMessage);
				answer = -1;
				scanner.next();
			}
		}
		return answer;
	}
	
	// Receives a Boolean Selection for navigating screens from the User
	public static boolean getBooleanInput(String prompt, String exMessage) {
		String input = "";
		while (!input.equalsIgnoreCase("y") || !input.equalsIgnoreCase("n")) {
			System.out.println(prompt);
			try {
				input = scanner.nextLine();
				System.out.println();
				if (input.equalsIgnoreCase("y")) {
					return true;
				} else if (input.equalsIgnoreCase("n")) {
					return false;
				}
				
			} catch (Exception e) {
				System.out.println(exMessage);
				scanner.next();
			}
		}
		return false;
		
	}
	
	// Creates a new Time Entry in the GymHours format for System
	public static GymHours addTimeEntry() {
		GymHours gh = null;
		LocalTime startTime = null;;
		LocalTime endTime = null;
		DayOfWeek day = null;
		
		while (day == null) {
			System.out.println("Enter Number of Corresponding Day of the Week: \n" + 
					"[1] Monday\n" + 
					"[2] Tuesday\n" + 
					"[3] Wednesday\n" + 
					"[4] Thursday\n" + 
					"[5] Friday\n" +
					"[6] Saturday\n" + 
					"[7] Sunday\n" + 
					"[8] Return With no Modifications\n"
					);
			int answer = getIntSelection(1, 8);
			if (answer == 8) {
				return null;
			} else if (answer >= 1 && answer <=7) {
				day = DayOfWeek.of(answer);
			} else {
				day = null;
			}
		}
		
		while (gh == null) {
			int startHour = -1;
			while (startHour <= -1 || startHour >= 24) {
				startHour = getInt("Start at Hour (0-23): ", "Please enter a valid number");
			}
			int startMinute = -1;
			while (startMinute <= -1 || startMinute >= 60) {
				startMinute = getInt("Start at Minute (0-59): ", "Please enter a valid number");
			}
			
			int endHour = -1;
			while (endHour <= -1 || endHour >= 24) {
				endHour = getInt("End at Hour (0-23): ", "Please enter a valid number");
			}
			int endMinute = -1;
			while (endMinute <= -1 || endMinute >= 60) {
				endMinute = getInt("End at Minute (0-59): ", "Please enter a valid number");
			}
			
			startTime = LocalTime.of(startHour, startMinute);
			endTime = LocalTime.of(endHour, endMinute);
			
			if (startTime.isBefore(endTime)) {
				gh = new GymHours(startTime, endTime, day);
			} else {
				System.out.println("End Time must be AFTER the Start Time");
				boolean addTime = getBooleanInput("Would you like to Add a Start and End Time again? (Y for Yes, N to return with no modification)", "Please Enter a Valid Selection");
				if (!addTime) {
					return null;
				}
			}			
		}
		return gh;
	}
	
	public static void main(String args[]) {
		loginHandler = LoginHandler.getInstance();
		
		trainerController = new TrainerController();
		customerController = new CustomerController();
		managerController = new ManagerController();
		equipmentController = new EquipmentController();
		exerciseController = new ExerciseController();
		routineController = new RoutineController();
		workoutClassController = new WorkoutClassController();
		
		CLIHardCode.hardCodeTrainers();
		CLIHardCode.hardCodeCustomers();
		CLIHardCode.hardCodeEquipment();
		CLIHardCode.hardCodeExercise();
		CLIHardCode.hardCodeWorkoutClass();
		CLIHardCode.hardCodeRoutines();
		
		CLIHardCode.hardCodedUsers();
		login();
		
		
	}
	
}
