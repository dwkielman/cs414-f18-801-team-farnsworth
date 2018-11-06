package teamFarnsworth.View;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import teamFarnsworth.Application.Builders.PersonBuilder;
import teamFarnsworth.Application.Controllers.CustomerController;
import teamFarnsworth.Application.Controllers.EquipmentController;
import teamFarnsworth.Application.Controllers.ExerciseController;
import teamFarnsworth.Application.Controllers.ManagerController;
import teamFarnsworth.Application.Controllers.RoutineController;
import teamFarnsworth.Application.Controllers.TrainerController;
import teamFarnsworth.Application.Controllers.UserController;
import teamFarnsworth.Application.Controllers.UserControllerFactory;
import teamFarnsworth.Application.Controllers.WorkoutClassController;
import teamFarnsworth.Domain.Address;
import teamFarnsworth.Domain.Customer;
import teamFarnsworth.Domain.Duration;
import teamFarnsworth.Domain.Equipment;
import teamFarnsworth.Domain.Exercise;
import teamFarnsworth.Domain.GymHours;
import teamFarnsworth.Domain.HealthInsuranceProvider;
import teamFarnsworth.Domain.Membership;
import teamFarnsworth.Domain.Password;
import teamFarnsworth.Domain.Person;
import teamFarnsworth.Domain.Qualification;
import teamFarnsworth.Domain.Routine;
import teamFarnsworth.Domain.Trainer;
import teamFarnsworth.Domain.WorkoutClass;
import teamFarnsworth.Handlers.LoginHandler;

public class CLI {

	private static Scanner scanner = new Scanner(System.in);
	private static TrainerController trainerController;
	private static CustomerController customerController;
	private static ManagerController managerController;
	private static EquipmentController equipmentController;
	private static ExerciseController exerciseController;
	private static RoutineController routineController;
	private static WorkoutClassController workoutClassController;
	private static LoginHandler loginHandler;
	
	private static String currentUserId;
	
	/**
	 * Hardcode Login for Debugging
	 */
	public static void hardCodedUsers() {
		loginHandler.setAccountInSystem("admin", new Password("pass"));
		loginHandler.linkAccountType("admin", "Admin");
		loginHandler.setAccountInSystem("man", new Password("pass"));
		loginHandler.linkAccountType("man", "Manager");
		loginHandler.setAccountInSystem("train", new Password("pass"));
		loginHandler.linkAccountType("train", "Trainer");
		loginHandler.setAccountInSystem("cus", new Password("pass"));
		loginHandler.linkAccountType("cus", "Customer");
	}
	
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
				case ("Manager"): managerScreen(); break;
				case ("Trainer"): trainerScreen(); break;
				case ("Customer"): customerScreen(); break;
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
				managerScreen(); break;
			case 2: 
				loginHandler.linkAccountType("admin", "Trainer");
				trainerScreen(); break;
			case 3: 
				loginHandler.linkAccountType("admin", "Customer");
				customerScreen(); break;
			case 4: return;
		}
	}
	
	// Manager Main Screen
	public static void managerScreen() {
		System.out.println("[1] Manage Trainers\n" + 
				"[2] Manage Customers\n" +
				"[3] Manage Equipment\n" + 
				"[4] Logout\n"
				);
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: managePerson("Trainer"); break;
			case 2: managePerson("Customer"); break;
			case 3: manageEquipmentScreen(); break;
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
		int answer = getIntSelection(1, 6);
		switch (answer) {
			case 1: trainerScheduleScreen(); break;
			case 2: managePerson("Customer"); break;
			case 3: manageWorkoutClassesScreen(); break;
			case 4: manageRoutineScreen(); break;
			case 5: manageExerciseScreen(); break;
			case 6: return;
		}
		trainerScreen();
	}
	
	// Customer Main Screen
	public static void customerScreen() {
		System.out.println("[1] View All Classes\n" + 
				"[2] View Enrolled Classes\n" + 
				"[3] Manage Classes\n" +
				"[4] Logout\n"
				);
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: 
				workoutClassController.toStringWorkoutClasses();
				break;
			case 2: customerClassesScreen(); break;
			case 3: customerManageClasses(); break;
			case 4: return;
		}
		customerScreen();
	}
	
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
		int answer = getIntSelection(1, 3);
		switch (answer) {
		case 1: createUser(accountType); break;
		case 2: modifyUserScreen(accountType); break;
		case 3: return;
		}
		managePerson(accountType);
	}
	
	// Trainer's Schedule
	public static void trainerScheduleScreen() {
		Trainer t = trainerController.getUser(currentUserId);
		if (t.getSchedule().size() > 0) {
			for (GymHours gh : t.getSchedule()) {
				System.out.println(gh.toString());
			}
		}
		
	}
	
	// Customer's Workout Class Schedule
	public static void customerClassesScreen() {
		Customer c = customerController.getUser(currentUserId);
		if (!c.getEnrolledClasses().isEmpty()) {
			System.out.println(c.workoutClassesToString());
		} else {
			System.out.println("You are currently not enrolled in any classes");
		}
		
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
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(equipmentController.toStringEquipment());
				break;
			case 2: createEquipment(""); break;
			case 3: modifyEquipment(); break;
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
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(routineController.toStringRoutines());
				break;
			case 2: createRoutine(); break;
			case 3: modifyRoutine(); break;
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
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(workoutClassController.toStringWorkoutClasses());
				break;
			case 2: createWorkoutClass(""); break;
			case 3: modifyWorkoutClass(); break;
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
		int answer = getIntSelection(1, 4);
		switch (answer) {
			case 1: System.out.println(exerciseController.toStringExercises());
				break;
			case 2: createExercise(); break;
			case 3: modifyExercise(); break;
			case 4: return;
		}
		manageExerciseScreen();
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

	/*
	 * User Creation Views
	 */
	// Creates a new Trainer or Customer, only accessible by Managers or Trainers
	public static void createUser(String accountType) {
		Person person = createPerson(accountType);
		
		if (person == null) {
			return;
		}
		
		// Creating a Trainer and adding it to the global Trainer Controller
		if (person.getClass().equals(Trainer.class)) {
			Trainer t = trainerController.addUser(person);
			boolean enterQualifications = getBooleanInput("Add Qualifications Now? (Y or N): ", "Please Enter a Valid Selection");
			while (enterQualifications) {
				Qualification q = new Qualification(getString("Enter Qualification: ", "Please Enter a Valid Qualification"));
				trainerController.addQualification(t, q);
				enterQualifications = getBooleanInput("Add Additional Qualification? (Y or N): ", "Please Enter a Valid Selection");
			}
			
			boolean enterHours = getBooleanInput("Add Work Hours Now? (Y or N): ", "Please Enter a Valid Selection");
			while (enterHours) {
				GymHours gh = addTimeEntry();
				if (gh != null) {
					trainerController.setSchedule(t, gh);
				}
				enterHours = getBooleanInput("Add More Work Hours? (Y or N): ", "Please Enter a Valid Selection");
			}
			// Creating a Customer and adding it to the global Customer Controller
		} else if (person.getClass().equals(Customer.class)) {
			Customer c = customerController.addUser(person);
			Membership m = Membership.INACTIVE;
			boolean isActive = getBooleanInput("Is Customer Active? (Y or N): ", "Please Enter a Valid Selection");
			if (isActive) {
				m = Membership.ACTIVE;
			}
			customerController.setMembershipStatus(c, m);
		}
		
		System.out.println("User " + person.getID() + " has successfully been created.\n");
	}
	
	// Creating an actual Person Class that is for use by all User Types that share same Person attributes
	public static Person createPerson(String accountType) {
		PersonBuilder personBuilder = new PersonBuilder();
		
		personBuilder.addFirstName(getString("Enter First Name: ", "Please Enter a Valid Name"));
		personBuilder.addLastName(getString("Enter Last Name: ", "Please Enter a Valid Name"));
		
		personBuilder.addPhone(getInt("Enter 10-digit Phone Number: ", "Please Enter a Valid Phone Number"));
		
		personBuilder.addEmail(getString("Enter an Email Address: ", "Please Enter a Valid Email Address"));
		personBuilder.addID(getString("Enter an ID: ", "Please Enter a Valid ID"));
		
		personBuilder.addPassword(getString("Enter a Password: ", "Please Enter a Valid ID"));
		
		String street = getString("Enter a Street: ", "Please Enter a Valid Street");
		String city = getString("Enter a City: ", "Please Enter a Valid City");
		String state = getString("Enter a State: ", "Please Enter a Valid State");
		int zip = getInt("Enter 5-digit Zip Code: ", "Please Enter a Valid Zip Code");
		
		personBuilder.addAddress(street, city, state, zip);
		boolean enterHeatlthInsruance = getBooleanInput("Add Health Insurance Provider Now? (Y or N): ", "Please Enter a Valid Selection");
		if (enterHeatlthInsruance) {
			personBuilder.addHealthInsuranceProvider(getString("Enter a Health Insurance Provider Name: ", "Please Enter a Valid Health Insurance Provider"));
		}
		
		Person p = personBuilder.BuildPerson(accountType);
		
		loginHandler.setAccountInSystem(p.getID(), p.getPassword());
		loginHandler.linkAccountType(p.getID(), accountType);
		return p;
	}
	
	/*
	 * Gym Entities Creation Views
	 */
	// Creates a new Equipment, only accessible by Managers
	public static void createEquipment(String name) {
		if (name == "") {
			name = getString("Enter Name of Equipment: ", "Please Enter a Valid Name");
		}
		Equipment e = new Equipment(name);
		boolean enterPhoto = getBooleanInput("Are you adding a Photo Right Now? (Y or N): ", "Please Enter a Valid Selection");
		if (enterPhoto) {
			String picturePath = getString("Enter File Location of Picture of Equipment): ", "Please Enter a Valid File Location");
			e.setPicturePath(picturePath);
		}
		boolean enterQuantity = getBooleanInput("Are you adding more than 1 of this Equipment? (Y or N): ", "Please Enter a Valid Selection");
		if (enterQuantity) {
			int qty = getInt("Enter the Quantity of this Equipment in Stock: ", "Please Enter a Valid Zip Code");
			e.setQuantity(qty);
		}
		
		if (!equipmentController.getAllEquipment().contains(e)) {
			equipmentController.addEquipment(e);
			System.out.println("Equipment " + e.getName() + " Has been Added to the Gym");
		} else {
			System.out.println("This Equipment already exists.");
			boolean updateQuantity = getBooleanInput("Would you like to update the Quantity of this Existing Equipment? (Y or N): ", "Please Enter a Valid Selection");
			if (updateQuantity) {
				equipmentController.getEquipment(name).updateQuantity(e.getQuantity());
				System.out.println("Equipment " + e.getName() + "Has been Updated");
			} else {
				System.out.println("Please select this Equipment through the Modify Equipment Selection to Modify it.");
			}
		}
	}
	
	// Creates a new Routine, only accessible by Trainers
	public static void createRoutine() {
		String name = getString("Enter Name of Routine: ", "Please Enter a Valid Name");
		System.out.println("Here are the Exercises currently available to use in your Routine");
		exerciseController.toStringExercises();
		boolean addNewExercise = getBooleanInput("Do you need to add a new Exercise for your Routine? (Y or N): ", "Please Enter a Valid Selection");
		if (addNewExercise) {
			createExercise();
		} else {
			boolean addExercise = true;
			while (addExercise) {
				
				Map<Integer, Exercise> exerciseMap = new HashMap<Integer, Exercise>();
				Set<Exercise> allExercises = exerciseController.getExercises();
				System.out.println("Select the Number of the Corresponding Exercise to add to Routine: ");
				int index = 1;
				for (Exercise exer : allExercises) {
					System.out.println("[" + index + "] " + exer.toString());
					exerciseMap.put(index, exer);
					index++;
				}
				System.out.println("[" + index + "] Return to Previous Screen");
				int answer = getIntSelection(1, index);
				
				if (answer >= 1 && answer < index) {
					Exercise routineExericse = exerciseMap.get(answer);
					Routine r = new Routine(name, routineExericse);
					if (routineController.getRoutines().contains(r)) {
						for (Routine rou : routineController.getRoutines()) {
							if (rou.getExercises().contains(routineExericse)) {
								boolean addExeriseToRoutine = getBooleanInput("This Routine already exists with this Exercise. Would you like to add it to the Routine? (Y or N): ", "Please Enter a Valid Selection");
								if (addExeriseToRoutine) {
									routineController.addExerciseToRoutine(name, routineExericse);
								}
							}
						}
						System.out.println("This Routine already exists with this Exercise");
					} else {
						routineController.createRoutine(name, routineExericse);
						System.out.println("Routine has been Created Successfully.");
					}
					
				} else if (answer == index) {
					return;
				}
				addExercise = getBooleanInput("Add an additional Existing Exercise to Routine? (Y or N): ", "Please Enter a Valid Selection");
			}
		}
	}
	
	// Creates a new Workout Class, only accessible by Trainers
	public static void createWorkoutClass(String name) {
		if (name == "") {
			name = getString("Enter Name of Workout Class: ", "Please Enter a Valid Name");
		}
		GymHours gh = addTimeEntry();
		WorkoutClass wc = new WorkoutClass(name, gh);
		if (!workoutClassController.getWorkoutClasses().contains(wc)) {
			workoutClassController.createWorkoutClass(name, gh);
			System.out.println("Workout Class " + wc.getName() + "Has been Added to the Gym");
		} else {
			System.out.println("This Workout Class already exists.");
			System.out.println("Please select this Workout Class through the Modify Workout Class Selection to Modify it.");
		}
	}
	
	// Creates a new Exercise, only accessible by Trainers via a Routine
	public static void createExercise() {
		System.out.println("Please select what Fields are needed for Exercise:\n" + 
				"[1] Duration\n" + 
				"[2] Reps\n" + 
				"[3] Equipment & Duration\n" +
				"[4] Equipment & Reps\n" + 
				"[5] Return\n"
				);
		int answer = getIntSelection(1, 5);
		String name = getString("Enter Name of Exercise: ", "Please Enter a Valid Name");
		Duration d = new Duration();
		String equipmentName;
		Equipment e = new Equipment();
		int numberOfReps;
		switch(answer) {
		case 1:
			d = new Duration(getInt("Enter the Duration (in minutes) for this Routine: ", "Please Enter a Valid Duration"));
			exerciseController.createExercise(name, d);
			break;
		case 2:
			numberOfReps = getInt("Enter the Number of Reps for this Routine: ", "Please Enter a Valid Number");
			exerciseController.createExercise(name, numberOfReps);
			break;
		case 3:
			System.out.println("Equipement available in Gym: ");
			equipmentController.toStringEquipment();
			equipmentName = getString("Enter the Name of the piece of Equipment you wish to Use in your Routine: ", "Please Enter a Valid Equipment");
			e = equipmentController.getEquipment(equipmentName);
			if (e == null) {
				System.out.println("This Equipment does not exist. Routine can not be added");
			} else {
				d = new Duration(getInt("Enter the Duration (in minutes) for this Routine: ", "Please Enter a Valid Duration"));
				exerciseController.createExercise(name, e, d);
			}
			break;
		case 4:
			System.out.println("Equipement available in Gym: ");
			equipmentController.toStringEquipment();
			equipmentName = getString("Enter the Name of the piece of Equipment you wish to Use in your Routine: ", "Please Enter a Valid Equipment");
			e = equipmentController.getEquipment(equipmentName);
			if (e == null) {
				System.out.println("This Equipment does not exist. Routine can not be added");
			} else {
				numberOfReps = getInt("Enter the Number of Reps for this Routine: ", "Please Enter a Valid Number");
				exerciseController.createExercise(name, e, numberOfReps);
			}
			break;
		case 5:
			return;
		}
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
			p.setPhone(getInt("Enter a New Phone Number: ", "Please Enter a Valid Phone Number"));
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
					intField = getInt("Enter a New Zip Code: ", "Please Enter a Zip Code");
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
			System.out.println("Current Schedule is: " + t.getSchedule());
			GymHours gh = addTimeEntry();
			t.addWorkHoursToSchedule(gh.getStartTime(), gh.getEndTime(), gh.getDay());
			break;
		case 10: return;
			}
		}
		
		// Customer only options
		if (p.getClass().equals(Customer.class)) {
			Customer c = (Customer) p;
			switch(input) {
		case 8:
			System.out.println("Currently this Customer is: " + c.getMembership());
			Membership m = Membership.INACTIVE;
			boolean isActive = getBooleanInput("Is Customer Active? (Y or N): ", "Please Enter a Valid Selection");
				if (isActive) {
					m = Membership.ACTIVE;
				}
			c.setMembership(m);
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
					createEquipment(e.getName());
				}
			} else {
				System.out.println(e.toString());
				System.out.println("What Part of the Equipment would you like to Modify?" + 
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
					System.out.println("[" + index + "] Mo Modification");
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
					System.out.println("Select the Number of the Corresponding Exercise to Remove in this Routine: ");
					selection = 1;
					for (Exercise exer : r.getExercises()) {
						System.out.println("[" + selection + "] " + exer.toString());
						exerciseMap.put(selection, exer);
						selection++;
					}
					System.out.println("[" + index + "] Mo Modification");
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
		//System.out.println("Current Workout Classes in Gym:");
		//System.out.println(workoutClassController.toStringWorkoutClasses());
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
						"[1] Modify Duration (Currently Not Available)\n" +
						"[2] Modify Equipemnt (Currently Not Available)\n" +
						"[3] Modify Number of Reps (Currently Not Available)\n" + 
						"[4] Delete Exercise\n" + 
						"[5] No Modification\n"
						);
				answer = getIntSelection(1, 5);
				switch(answer) {
				case 1: 
					break;
				case 2:
					break;
				case 3:
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
	
	
	
	public static void enrollInClass() {
		System.out.println("Please select a corresponding number of an available class in which to enroll: ");
		Customer currentC = customerController.getUser(currentUserId);
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
			System.out.println("You are now enrolled in the Workout Class " + selectedWorkoutClass.getName());
		} else if (answer == index) {
			return;
		}
	}
	
	public static void unenrollFromClass() {
		System.out.println("Please select a corresponding number of a class you are enrolled in to Unenroll: ");
		Customer currentC = customerController.getUser(currentUserId);
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
		while (answer == "") {
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
				if (answer >= first && answer <= second) {
					scanner.nextLine();
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
		int answer = 0;
		while (answer == 0) {
			System.out.println(prompt);
			try {
				answer = scanner.nextInt();
				if (answer > 0) {
					scanner.nextLine();
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println(exMessage);
				answer = 0;
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
		GymHours gh = new GymHours();
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
			int startHour = getInt("Start at hour: ", "Please enter a valid number");
			int startMinute = getInt("Minutes: ", "Please enter a valid number");
			startTime = LocalTime.of(startHour, startMinute);
			startHour = getInt("End at hour: ", "Please enter a valid number");
			startMinute = getInt("Minutes: ", "Please enter a valid number");
			endTime = LocalTime.of(startHour, startMinute);
			if (startTime.isBefore(endTime)) {
				gh = new GymHours(startTime, endTime, day);
			}
		}
			
		return gh;
	}
	
	public static void hardCodeTrainers() {
		Trainer t1 = new Trainer("Jack", "Trainer", "Jack");
		Trainer t2 = new Trainer("Amy", "Worker", "Amy");
		Trainer t3 = new Trainer("Linda", "Teacher", "Linda");
		Address a1 = new Address("123 Main Street", "Sacramento", "CA", 95652);
		Address a2 = new Address("987 Market Avenue", "Los Angeles", "CA", 81825);
		Address a3 = new Address("456 Taylor Road", "San Francisco", "CA", 94678);
		t1.setAddress(a1);
		t2.setAddress(a2);
		t3.setAddress(a3);
		t1.setPhone(1234567891);
		t2.setPhone(1234567891);
		t3.setPhone(1234567891);
		
		trainerController.addUser(t1);
		trainerController.addUser(t2);
		trainerController.addUser(t3);
	}
	
	public static void hardCodeCustomers() {
		Customer c1 = new Customer("Aaron", "Athletic", "Aaron");
		Customer c2 = new Customer("Randy", "Runner", "Randy");
		Customer c3 = new Customer("Sally", "Swimmer", "Sally");
		Address a1 = new Address("123 Main Street", "Sacramento", "CA", 95652);
		Address a2 = new Address("987 Market Avenue", "Los Angeles", "CA", 81825);
		Address a3 = new Address("456 Taylor Road", "San Francisco", "CA", 94678);
		c1.setAddress(a1);
		c2.setAddress(a2);
		c3.setAddress(a3);
		c1.setPhone(1234567891);
		c2.setPhone(1234567891);
		c3.setPhone(1234567891);
		
		customerController.addUser(c1);
		customerController.addUser(c2);
		customerController.addUser(c3);
	}
	
	public static void hardCodeEquipment() {
		Equipment e1 = new Equipment("Bench Press");
		Equipment e2 = new Equipment("Rowing Machine");
		Equipment e3 = new Equipment("Stairmaster");
		
		equipmentController.addEquipment(e1);
		equipmentController.addEquipment(e2);
		equipmentController.addEquipment(e3);
	}
	
	public static void hardCodeWorkoutClass() {
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		GymHours gh3 = new GymHours(LocalTime.of(18, 0), LocalTime.of(19, 0), DayOfWeek.WEDNESDAY);
		
		workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		workoutClassController.createWorkoutClass("Evening Yoga", gh3);
	}
	
	public static void hardCodeExercise() {
		Exercise durationExercise = new Exercise("Running", new Duration(20));
		Exercise equipmentAndDurationExercise = new Exercise("Legs", equipmentController.getEquipment("Stairmaster"), new Duration(10));
		Exercise repExercise = new Exercise("Lunges", 15);
		Exercise repAndEquipmentExercise = new Exercise("Weight Lifting", equipmentController.getEquipment("Bench Press"), 10);
		
		exerciseController.addExercise(durationExercise);
		exerciseController.addExercise(equipmentAndDurationExercise);
		exerciseController.addExercise(repAndEquipmentExercise);
		exerciseController.addExercise(repExercise);
	}
	
	public static void hardCodeRoutines() {
		routineController.createRoutine("Running Routine", exerciseController.getExercise("Running"));
		routineController.createRoutine("Legs Routine", exerciseController.getExercise("Legs"));
		routineController.createRoutine("Lunges Routine", exerciseController.getExercise("Lunges"));
		routineController.createRoutine("Weight Lifting Routine", exerciseController.getExercise("Weight Lifting"));
		
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
		
		hardCodeTrainers();
		hardCodeCustomers();
		hardCodeEquipment();
		hardCodeExercise();
		hardCodeWorkoutClass();
		hardCodeRoutines();
		
		hardCodedUsers();
		login();
		
		
	}
	
}
