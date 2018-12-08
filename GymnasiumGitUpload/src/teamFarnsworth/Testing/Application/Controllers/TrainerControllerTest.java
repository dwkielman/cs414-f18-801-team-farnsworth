package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.TrainerController;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Qualification;
import teamFarnsworth.Domain.Users.Trainer;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class TrainerControllerTest {

	private TrainerController trainerController;
	private MemberHandler memberHandler;
	private String fName;
	private String lName;
	private String trainerID;
	private String fName2;
	private String lName2;
	private String trainerID2;
	private GymHours gh1;
	private GymHours gh2;
	private Qualification q1;
	private Qualification q2;
	private Trainer t1;
	private Trainer t2;
	private Customer c1;
	private Customer c2;
	private String firstName;
	private String lastName;
	private String id;
	private String firstName2;
	private String lastName2;
	private String id2;
	
	@BeforeEach
	void setUp() throws Exception {
		trainerController = new TrainerController();
		memberHandler = MemberHandler.getInstance();
		memberHandler.getTrainers().clear();
		fName = "Ted";
		lName = "Trainer";
		trainerID = "Ted";
		fName2 = "Tina";
		lName2 = "Trainer";
		trainerID2 = "Tina";
		firstName = "Carl";
		lastName = "Customer";
		id = "Carl";
		firstName2 = "Christina";
		lastName2 = "Customer";
		id2 = "Christina";
		gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		gh2 = new GymHours(LocalTime.of(20, 0), LocalTime.of(21, 0), DayOfWeek.WEDNESDAY);;
		q1 = new Qualification("Weights");
		q2 = new Qualification("Aerobics");
		t1 = new Trainer(fName, lName, trainerID);
		t2 = new Trainer(fName2, lName2, trainerID2);
		c1 = new Customer(firstName, lastName, id);
		c2 = new Customer(firstName2, lastName2, id2);
	}

	@Test
	void testGetTrainersSet() {
		trainerController.addUser(t1);
		trainerController.addUser(t2);
		assertTrue(trainerController.getUsers().size() == 2);
	}
	
	@Test
	void testGetTrainerByPerson() {
		trainerController.addUser(t1);
		assertTrue(trainerController.getUser(t2) == null);
		assertTrue(trainerController.getUser(t1).equals(t1));
	}

	@Test
	void testGetTrainerByID() {
		trainerController.addUser(t1);
		assertTrue(trainerController.getUser(trainerID).equals(t1));
	}
	
	@Test
	void testAddTrainer() {
		trainerController.addUser(t1);
		assertTrue(t1.equals(trainerController.getUser(trainerID)));
	}
	
	@Test
	void testRemoveTrainer() {
		trainerController.addUser(t1);
		trainerController.removeUser(t1);
		assertTrue(trainerController.getUsers().isEmpty());
	}
	
	@Test
	void testSetGymHoursToTrainerSchedule() {
		trainerController.addUser(t1);
		trainerController.setSchedule(t1, gh1);
		assertTrue(trainerController.getUser(t1).getSchedule().contains(gh1));
	}
	
	@Test
	void testAddQualificationToTrainer() {
		trainerController.addUser(t1);
		trainerController.addQualification(t1, q1);
		assertTrue(trainerController.getUser(t1).getQualifications().contains(q1));
	}
	
	@Test
	void testEnrollCustomerWithTrainer() {
		trainerController.addUser(t1);
		trainerController.enrollCustomerWithTrainer(t1, c1);
		assertTrue(trainerController.getUser(t1).getCustomers().contains(c1));
	}
	
	@Test
	void testUnenrollCustomerFromTrainer() {
		trainerController.addUser(t1);
		trainerController.enrollCustomerWithTrainer(t1, c1);
		trainerController.enrollCustomerWithTrainer(t1, c2);
		trainerController.unenrollCustomerFromTrainer(t1, c1);
		assertTrue(trainerController.getUser(t1).getCustomers().size() == 1);
		assertFalse(trainerController.getUser(t1).getCustomers().contains(c1));
	}
}
