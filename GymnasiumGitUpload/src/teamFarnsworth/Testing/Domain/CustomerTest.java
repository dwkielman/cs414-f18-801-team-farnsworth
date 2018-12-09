package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Domain.Entities.WorkoutClass;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Trainer;

public class CustomerTest {

	private Customer c1;
	private Customer c2;
	private Customer c1Copy;
	private Customer nullCustomer;
	private String n1 = "Jake";
	private String n2 = "Cindy";
	private String l1 = "Smith";
	private String l2 = "Johnson";
	private String id1 = "J";
	private String id2 = "C";
	private GymHours gh1;
	private GymHours gh2;
	private GymHours gh3;
	private WorkoutClass wc1;
	private WorkoutClass wc1Copy;
	private WorkoutClass wc2;
	private WorkoutClass wc3;
	private Trainer t1;
	private Trainer t2;
	private String fName;
	private String lName;
	private String trainerID;
	private String fName2;
	private String lName2;
	private String trainerID2;
	private Exercise durationExercise;
	private Routine r1;
	
	@BeforeEach
	void setUp() throws Exception {
		c1 = new Customer(n1, l1, id1);
		c2 = new Customer(n2, l2, id2);
		c1Copy = new Customer(n1, l1, id1);
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		GymHours gh3 = new GymHours(LocalTime.of(18, 0), LocalTime.of(19, 0), DayOfWeek.WEDNESDAY);
		wc1 = new WorkoutClass("Morning Aerobics", gh1);
		wc1Copy = new WorkoutClass("Morning Aerobics", gh1);
		wc2 = new WorkoutClass("Afternoon Weights", gh2);
		wc3 = new WorkoutClass("Evening Yoga", gh3);
		fName = "Ted";
		lName = "Trainer";
		trainerID = "Ted";
		fName2 = "Tina";
		lName2 = "Trainer";
		trainerID2 = "Tina";
		t1 = new Trainer(fName, lName, trainerID);
		t2 = new Trainer(fName2, lName2, trainerID2);
		durationExercise = new Exercise("Running", new Duration(20));
		r1 = new Routine("Running Routine", durationExercise);
	}
	
	@Test
	void testCustomersNotEqual() {
		assertFalse(c1.equals(c2));
	}
	
	@Test
	void testCustomersEqual() {
		assertTrue(c1.equals(c1Copy));
	}
	
	@Test
	void testCustomerNull() {
		assertTrue(nullCustomer == null);
	}

	@Test
	void testErollInClass() {
		c1.enrollInClass(wc1);
		assertTrue(c1.getEnrolledClasses().contains(wc1));
	}
	
	@Test
	void testErollInClassDuplicate() {
		c1.enrollInClass(wc1);
		wc1Copy = new WorkoutClass("Morning Aerobics", gh1);
		assertTrue(c1.getEnrolledClasses().size() == 1);
	}
	
	@Test
	void testUnerollInClass() {
		c1.enrollInClass(wc1);
		c1.enrollInClass(wc2);
		c1.unenrollFromClass(wc1);
		assertTrue(c1.getEnrolledClasses().size() == 1);
		assertFalse(c1.getEnrolledClasses().contains(wc1));
	}
	
	@Test
	void testUnerollInClassNotEnrolledIn() {
		c1.enrollInClass(wc1);
		c1.enrollInClass(wc2);
		assertTrue(c1.getEnrolledClasses().size() == 2);
		c1.unenrollFromClass(wc3);
		assertTrue(c1.getEnrolledClasses().size() == 2);
	}
	
	@Test
	void testGetnEnrolledClasses() {
		c1.enrollInClass(wc1);
		c1.enrollInClass(wc2);
		assertTrue(c1.getEnrolledClasses().size() == 2);
	}
	
	@Test
	void testEnrollWithPrivateTrainer() {
		c1.enrollWithTrainer(t1);
		assertTrue(c1.getPrivateTrainer().equals(t1));
	}
	
	@Test
	void testUnenrollFromPrivateTrainer() {
		c1.enrollWithTrainer(t1);
		c1.unenrollFromTrainer();
		assertTrue(c1.getPrivateTrainer() == null);
	}
	
	@Test
	void testUnenrollFromPrivateTrainerWithNoPrivateTrainer() {
		c1.unenrollFromTrainer();
		assertTrue(c1.getPrivateTrainer() == null);
	}
	
	@Test
	void testassignRoutineToCustomer() {
		c1.addRoutine(r1);
		assertTrue(c1.getRoutines().size() == 1);
	}
	
	@Test
	void testunassignRoutineFromCustomer() {
		c1.addRoutine(r1);
		assertTrue(c1.getRoutines().size() == 1);
		c1.removeRoutine(r1);
		assertTrue(c1.getRoutines().isEmpty());
	}

}
