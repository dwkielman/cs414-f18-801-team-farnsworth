package teamFarnsworth.View;

import java.time.DayOfWeek;
import java.time.LocalTime;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Domain.Users.Address;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Membership;
import teamFarnsworth.Domain.Users.Password;
import teamFarnsworth.Domain.Users.Trainer;

public class CLIHardCode {

	/**
	 * Hardcode Login for Debugging
	 */
	public static void hardCodedUsers() {
		CLI.loginHandler.setAccountInSystem("admin", new Password("pass"));
		CLI.loginHandler.linkAccountType("admin", "Admin");
		CLI.loginHandler.setAccountInSystem("man", new Password("pass"));
		CLI.loginHandler.linkAccountType("man", "Manager");
		CLI.loginHandler.setAccountInSystem("train", new Password("pass"));
		CLI.loginHandler.linkAccountType("train", "Trainer");
		CLI.loginHandler.setAccountInSystem("cus", new Password("pass"));
		CLI.loginHandler.linkAccountType("cus", "Customer");
		CLI.loginHandler.setAccountInSystem("regCus", new Password("pass"));
		CLI.loginHandler.linkAccountType("regCus", "Customer");
		CLI.loginHandler.setAccountInSystem("premCus", new Password("pass"));
		CLI.loginHandler.linkAccountType("premCus", "Customer");
	}

	public static void hardCodeTrainers() {
		Trainer t1 = new Trainer("Jack", "Trainer", "Jack");
		Trainer t2 = new Trainer("Amy", "Worker", "Amy");
		Trainer t3 = new Trainer("Linda", "Teacher", "Linda");
		Trainer t4 = new Trainer("Trainer", "Tester", "train");
		Address a1 = new Address("123 Main Street", "Sacramento", "CA", 95652);
		Address a2 = new Address("987 Market Avenue", "Los Angeles", "CA", 81825);
		Address a3 = new Address("456 Taylor Road", "San Francisco", "CA", 94678);
		Address a4 = new Address("1 Test Street", "Test City", "TS", 12345);
		t1.setAddress(a1);
		t2.setAddress(a2);
		t3.setAddress(a3);
		t4.setAddress(a4);
		t1.setPassword("pass");
		t2.setPassword("pass");
		t3.setPassword("pass");
		t4.setPassword("pass");
		t1.setPhone("1234567891");
		t2.setPhone("1234567891");
		t3.setPhone("1234567891");
		t4.setPhone("1234567891");
		
		CLI.trainerController.addUser(t1);
		CLI.trainerController.addUser(t2);
		CLI.trainerController.addUser(t3);
		CLI.trainerController.addUser(t4);
	}

	public static void hardCodeCustomers() {
		Customer c1 = new Customer("Aaron", "Athletic", "Aaron");
		Customer c2 = new Customer("Randy", "Runner", "Randy");
		Customer c3 = new Customer("Sally", "Swimmer", "Sally");
		Customer c4 = new Customer("Customer", "Tester", "cus");
		Customer c5 = new Customer("RegCustomer", "Tester", "regCus");
		Customer c6 = new Customer("PremCustomer", "Tester", "premCus");
		Address a1 = new Address("123 Main Street", "Sacramento", "CA", 95652);
		Address a2 = new Address("987 Market Avenue", "Los Angeles", "CA", 81825);
		Address a3 = new Address("456 Taylor Road", "San Francisco", "CA", 94678);
		Address a4 = new Address("1 Test Street", "Test City", "TS", 12345);
		c1.setAddress(a1);
		c2.setAddress(a2);
		c3.setAddress(a3);
		c4.setAddress(a4);
		c5.setAddress(a4);
		c6.setAddress(a4);
		c1.setPassword("pass");
		c2.setPassword("pass");
		c3.setPassword("pass");
		c4.setPassword("pass");
		c5.setPassword("pass");
		c6.setPassword("pass");
		c1.setPhone("1234567891");
		c2.setPhone("1234567891");
		c3.setPhone("1234567891");
		c4.setPhone("1234567891");
		c5.setPhone("1234567891");
		c6.setPhone("1234567891");
		c5.setMembership(Membership.REGULAR);
		c6.setMembership(Membership.PREMIUM);
		Routine r1 = new Routine("Running Routine", CLI.exerciseController.getExercise("Running"));
		c4.addRoutine(r1);
		
		CLI.customerController.addUser(c1);
		CLI.customerController.addUser(c2);
		CLI.customerController.addUser(c3);
		CLI.customerController.addUser(c4);
		CLI.customerController.addUser(c5);
		CLI.customerController.addUser(c6);
	}

	public static void hardCodeEquipment() {
		Equipment e1 = new Equipment("Bench Press");
		Equipment e2 = new Equipment("Rowing Machine");
		Equipment e3 = new Equipment("Stairmaster");
		
		CLI.equipmentController.addEquipment(e1);
		CLI.equipmentController.addEquipment(e2);
		CLI.equipmentController.addEquipment(e3);
	}

	public static void hardCodeWorkoutClass() {
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		GymHours gh3 = new GymHours(LocalTime.of(18, 0), LocalTime.of(19, 0), DayOfWeek.WEDNESDAY);
		
		CLI.workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		CLI.workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		CLI.workoutClassController.createWorkoutClass("Evening Yoga", gh3);
	}

	public static void hardCodeExercise() {
		Exercise durationExercise = new Exercise("Running", new Duration(20));
		Exercise equipmentAndDurationExercise = new Exercise("Legs", CLI.equipmentController.getEquipment("Stairmaster"), new Duration(10));
		Exercise repExercise = new Exercise("Lunges", 15);
		Exercise repAndEquipmentExercise = new Exercise("Weight Lifting", CLI.equipmentController.getEquipment("Bench Press"), 10);
		
		CLI.exerciseController.addExercise(durationExercise);
		CLI.exerciseController.addExercise(equipmentAndDurationExercise);
		CLI.exerciseController.addExercise(repAndEquipmentExercise);
		CLI.exerciseController.addExercise(repExercise);
	}

	public static void hardCodeRoutines() {
		CLI.routineController.createRoutine("Running Routine", CLI.exerciseController.getExercise("Running"));
		CLI.routineController.createRoutine("Legs Routine", CLI.exerciseController.getExercise("Legs"));
		CLI.routineController.createRoutine("Lunges Routine", CLI.exerciseController.getExercise("Lunges"));
		CLI.routineController.createRoutine("Weight Lifting Routine", CLI.exerciseController.getExercise("Weight Lifting"));
		
	}

}
