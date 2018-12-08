package teamFarnsworth.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import teamFarnsworth.Application.Builders.PersonBuilder;
import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Domain.Entities.WorkoutClass;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Membership;
import teamFarnsworth.Domain.Users.Person;
import teamFarnsworth.Domain.Users.Qualification;
import teamFarnsworth.Domain.Users.Trainer;

public class CLICreatorScreens {

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
			Trainer t = CLI.trainerController.addUser(person);
			boolean enterQualifications = CLI.getBooleanInput("Add Qualifications Now? (Y or N): ", "Please Enter a Valid Selection");
			while (enterQualifications) {
				Qualification q = new Qualification(CLI.getString("Enter Qualification: ", "Please Enter a Valid Qualification"));
				CLI.trainerController.addQualification(t, q);
				enterQualifications = CLI.getBooleanInput("Add Additional Qualification? (Y or N): ", "Please Enter a Valid Selection");
			}
			
			boolean enterHours = CLI.getBooleanInput("Add Work Hours Now? (Y or N): ", "Please Enter a Valid Selection");
			while (enterHours) {
				GymHours gh = CLI.addTimeEntry();
				if (gh != null) {
					CLI.trainerController.setSchedule(t, gh);
				}
				enterHours = CLI.getBooleanInput("Add More Work Hours? (Y or N): ", "Please Enter a Valid Selection");
			}
			// Creating a Customer and adding it to the global Customer Controller
		} else if (person.getClass().equals(Customer.class)) {
			Customer c = CLI.customerController.addUser(person);
			System.out.println("What Status is the Customer's Membership?:\n" + 
					"[1] Basic\n" + 
					"[2] Regular\n" +  
					"[3] Premium\n" + 
					"[4] Inactive\n");
			int statusAnswer = CLI.getIntSelection(1, 4);
			switch(statusAnswer) {
				case 1:
					CLI.customerController.setMembershipStatus(c, Membership.BASIC);
					break;
				case 2:
					CLI.customerController.setMembershipStatus(c, Membership.REGULAR);
					break;
				case 3:
					CLI.customerController.setMembershipStatus(c, Membership.PREMIUM);
					break;
				case 4:
					CLI.customerController.setMembershipStatus(c, Membership.INACTIVE);
					break;
			}
			System.out.println("User " + person.getID() + " has successfully been created.\n");
		}
	}

	// Creating an actual Person Class that is for use by all User Types that share same Person attributes
	public static Person createPerson(String accountType) {
		PersonBuilder personBuilder = new PersonBuilder();
		
		personBuilder.addFirstName(CLI.getString("Enter First Name: ", "Please Enter a Valid Name"));
		personBuilder.addLastName(CLI.getString("Enter Last Name: ", "Please Enter a Valid Name"));
		
		String pNumber = "";
		while (pNumber.length() != 10 || !(pNumber.matches("[0-9]+"))) {
			pNumber = CLI.getString("Enter 10-digit Phone Number: ", "Please Enter a Valid Phone Number");
		}
		
		personBuilder.addPhone(pNumber);
		
		personBuilder.addEmail(CLI.getString("Enter an Email Address: ", "Please Enter a Valid Email Address"));
		personBuilder.addID(CLI.getString("Enter an ID: ", "Please Enter a Valid ID"));
		
		personBuilder.addPassword(CLI.getString("Enter a Password: ", "Please Enter a Valid ID"));
		
		String street = CLI.getString("Enter a Street: ", "Please Enter a Valid Street");
		String city = CLI.getString("Enter a City: ", "Please Enter a Valid City");
		String state = CLI.getString("Enter a State: ", "Please Enter a Valid State");
	
		int zip = 0;
		while (String.valueOf(zip).length() != 5) {
			zip = CLI.getInt("Enter 5-digit Zip Code: ", "Please Enter a Valid Zip Code");
		}
		
		personBuilder.addAddress(street, city, state, zip);
		boolean enterHeatlthInsruance = CLI.getBooleanInput("Add Health Insurance Provider Now? (Y or N): ", "Please Enter a Valid Selection");
		if (enterHeatlthInsruance) {
			personBuilder.addHealthInsuranceProvider(CLI.getString("Enter a Health Insurance Provider Name: ", "Please Enter a Valid Health Insurance Provider"));
		}
		
		Person p = personBuilder.BuildPerson(accountType);
		System.out.println("Account for " + p.getFirstName() + " " + p.getLastName() + " has been created succesfully.");
		
		CLI.loginHandler.setAccountInSystem(p.getID(), p.getPassword());
		CLI.loginHandler.linkAccountType(p.getID(), accountType);
		return p;
	}

	/*
	 * Gym Entities Creation Views
	 */
	// Creates a new Equipment, only accessible by Managers
	public static void createEquipment(String name) {
		if (name == "") {
			name = CLI.getString("Enter Name of Equipment: ", "Please Enter a Valid Name");
		}
		Equipment e = new Equipment(name);
		boolean enterPhoto = CLI.getBooleanInput("Are you adding a Photo Right Now? (Y or N): ", "Please Enter a Valid Selection");
		if (enterPhoto) {
			String picturePath = CLI.getString("Enter File Location of Picture of Equipment): ", "Please Enter a Valid File Location");
			e.setPicturePath(picturePath);
		}
		boolean enterQuantity = CLI.getBooleanInput("Are you adding more than 1 of this Equipment? (Y or N): ", "Please Enter a Valid Selection");
		if (enterQuantity) {
			int qty = CLI.getInt("Enter the Quantity of this Equipment in Stock: ", "Please Enter a Valid Zip Code");
			e.setQuantity(qty);
		}
		
		if (!CLI.equipmentController.getAllEquipment().contains(e)) {
			CLI.equipmentController.addEquipment(e);
			System.out.println("Equipment " + e.getName() + " Has been Added to the Gym");
		} else {
			System.out.println("This Equipment already exists.");
			boolean updateQuantity = CLI.getBooleanInput("Would you like to update the Quantity of this Existing Equipment? (Y or N): ", "Please Enter a Valid Selection");
			if (updateQuantity) {
				CLI.equipmentController.getEquipment(name).addMoreQuantity(e.getQuantity());
				System.out.println("Equipment " + e.getName() + "Has been Updated");
			} else {
				System.out.println("Please select this Equipment through the Modify Equipment Selection to Modify it.");
			}
		}
	}

	// Creates a new Routine, only accessible by Trainers
	public static void createRoutine() {
		String name = CLI.getString("Enter Name of Routine: ", "Please Enter a Valid Name");
		System.out.println("Here are the Exercises currently available to use in your Routine");
		System.out.println(CLI.exerciseController.toStringExercises());
		boolean addNewExercise = CLI.getBooleanInput("Do you need to add a new Exercise for your Routine? (Y or N): ", "Please Enter a Valid Selection");
		if (addNewExercise) {
			createExercise();
		} 
		if (CLI.exerciseController.getExercises().isEmpty()) {
			System.out.println("There are no exercises to select and the Routine can not be created. Please add Exercises to the Gym.");
			return;
		}
		
		//else {
			//boolean addExercise = true;
			//while (addExercise) {
				
				Map<Integer, Exercise> exerciseMap = new HashMap<Integer, Exercise>();
				Set<Exercise> allExercises = CLI.exerciseController.getExercises();
				System.out.println("Select the Number of the Corresponding Exercise to add to Routine: ");
				int index = 1;
				for (Exercise exer : allExercises) {
					System.out.println("[" + index + "] " + exer.toString());
					exerciseMap.put(index, exer);
					index++;
				}
				System.out.println("[" + index + "] Return to Previous Screen");
				int answer = CLI.getIntSelection(1, index);
				
				if (answer >= 1 && answer < index) {
					Exercise routineExericse = exerciseMap.get(answer);
					Routine r = new Routine(name, routineExericse);
					if (CLI.routineController.getRoutines().contains(r)) {
						for (Routine rou : CLI.routineController.getRoutines()) {
							if (rou.getExercises().contains(routineExericse)) {
								boolean addExeriseToRoutine = CLI.getBooleanInput("This Routine already exists with this Exercise. Would you like to add it to the Routine? (Y or N): ", "Please Enter a Valid Selection");
								if (addExeriseToRoutine) {
									CLI.routineController.addExerciseToRoutine(name, routineExericse);
								}
							}
						}
						System.out.println("This Routine already exists with this Exercise");
					} else {
						CLI.routineController.createRoutine(name, routineExericse);
						System.out.println("Routine has been Created Successfully.");
						System.out.println("To add additional Exercises to your Routine, please navigate to the Modify Routine Screen");
					}
					
				} else if (answer == index) {
					return;
				}
			//}
		//}
	}

	// Creates a new Workout Class, only accessible by Trainers
	public static void createWorkoutClass(String name) {
		if (name == "") {
			name = CLI.getString("Enter Name of Workout Class: ", "Please Enter a Valid Name");
		}
		GymHours gh = CLI.addTimeEntry();
		WorkoutClass wc = null;
		if (gh == null) {
			System.out.println("Invalid Time entry, no Workout Class has been created.");
		} else {
			wc = new WorkoutClass(name, gh);
		}
		
		if (wc != null && (!CLI.workoutClassController.getWorkoutClasses().contains(wc))) {
			CLI.workoutClassController.createWorkoutClass(name, gh);
			System.out.println("Workout Class " + wc.getName() + " has been Added to the Gym");
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
		int answer = CLI.getIntSelection(1, 5);
		String name = CLI.getString("Enter Name of Exercise: ", "Please Enter a Valid Name");
		Duration d = new Duration();
		String equipmentName;
		Equipment e = new Equipment();
		int numberOfReps;
		boolean addSets;
		switch(answer) {
		case 1:
			d = new Duration(CLI.getInt("Enter the Duration (in minutes) for this Exercise: ", "Please Enter a Valid Duration"));
			CLI.exerciseController.createExercise(name, d);
			System.out.println("Exercise has been successfully added to the Gym.");
			break;
		case 2:
			numberOfReps = CLI.getInt("Enter the Number of Reps for this Exercise: ", "Please Enter a Valid Number");
			CLI.exerciseController.createExercise(name, numberOfReps);
			addSets = true;
			
			while (addSets) {
				addSets = CLI.getBooleanInput("Would you like to add an additional set or reps? (Y or N): ", "Please Enter a Valid Selection");
				if (addSets) {
					numberOfReps = CLI.getInt("Enter the Number of Reps: ", "Please Enter a Valid Quantity");
					CLI.exerciseController.getExercise(name).getWorkoutSet().addRep(numberOfReps);
					System.out.println("Workout Set has been updated");
				}
			}
			System.out.println("Exercise has been successfully added to the Gym.");
			break;
		case 3:
			System.out.println("Equipement available in Gym: ");
			System.out.println(CLI.equipmentController.toStringEquipment());
			equipmentName = CLI.getString("Enter the Name of the piece of Equipment you wish to Use in your Routine: ", "Please Enter a Valid Equipment");
			e = CLI.equipmentController.getEquipment(equipmentName);
			if (e == null) {
				System.out.println("This Equipment does not exist. Exercise can not be added");
			} else {
				d = new Duration(CLI.getInt("Enter the Duration (in minutes) for this Routine: ", "Please Enter a Valid Duration"));
				CLI.exerciseController.createExercise(name, e, d);
				System.out.println("Exercise has been successfully added to the Gym.");
			}
			break;
		case 4:
			System.out.println("Equipement available in Gym: ");
			System.out.println(CLI.equipmentController.toStringEquipment());
			equipmentName = CLI.getString("Enter the Name of the piece of Equipment you wish to Use in your Routine: ", "Please Enter a Valid Equipment");
			e = CLI.equipmentController.getEquipment(equipmentName);
			if (e == null) {
				System.out.println("This Equipment does not exist. Routine can not be added");
			} else {
				numberOfReps = CLI.getInt("Enter the Number of Reps for this Routine: ", "Please Enter a Valid Number");
				CLI.exerciseController.createExercise(name, e, numberOfReps);
				addSets = true;
				
				while (addSets) {
					addSets = CLI.getBooleanInput("Would you like to add an additional set or reps? (Y or N): ", "Please Enter a Valid Selection");
					if (addSets) {
						numberOfReps = CLI.getInt("Enter the Number of Reps: ", "Please Enter a Valid Quantity");
						CLI.exerciseController.getExercise(name).getWorkoutSet().addRep(numberOfReps);
						System.out.println("Workout Set has been updated");
					}
				}
				System.out.println("Exercise has been successfully added to the Gym.");
			}
			break;
		case 5:
			return;
		}
	}

}
