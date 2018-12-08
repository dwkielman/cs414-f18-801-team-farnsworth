package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Trainer;

public class TrainerTest {

	private String fName;
	private String lName;
	private String trainerID;
	private String fName2;
	private String lName2;
	private String trainerID2;
	private Trainer t1;
	private Trainer t1Copy;
	private Trainer t2;
	private Trainer tNull;
	
	@BeforeEach
	void setUp() throws Exception {
		fName = "Ted";
		lName = "Trainer";
		trainerID = "Ted";
		fName2 = "Tina";
		lName2 = "Trainer";
		trainerID2 = "Tina";
		t1 = new Trainer(fName, lName, trainerID);
		t2 = new Trainer(fName2, lName2, trainerID2);
		t1Copy = new Trainer(fName, lName, trainerID);
	}

	@Test
	void testTrainerEquals() {
		assertTrue(t1.equals(t1Copy));
	}
	
	@Test
	void testTrainerNotEqual() {
		assertFalse(t1.equals(t2));
	}
	
	@Test
	void testNullTrainer() {
		assertTrue(tNull == null);
	}

}
