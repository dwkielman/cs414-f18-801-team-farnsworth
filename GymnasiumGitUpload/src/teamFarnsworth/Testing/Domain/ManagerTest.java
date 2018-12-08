package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Manager;

public class ManagerTest {

	private String nameFirst;
	private String nameLast;
	private String managerID;
	private String nameFirst2;
	private String nameLast2;
	private String managerID2;
	private Manager m1;
	private Manager m1Copy;
	private Manager m2;
	private Manager nullManager;
	
	@BeforeEach
	void setUp() throws Exception {
		nameFirst = "Mandy";
		nameLast = "Manager";
		managerID = "Mandy";
		nameFirst2 = "Mike";
		nameLast2 = "Manager";
		managerID2 = "Mike";
		m1 = new Manager(nameFirst, nameLast, managerID);
		m2 = new Manager(nameFirst2, nameLast2, managerID2);
		m1Copy = new Manager(nameFirst, nameLast, managerID);
	}

	@Test
	void testManagerEqual() {
		assertTrue(m1.equals(m1Copy));
	}
	
	@Test
	void testManagerNotEqual() {
		assertFalse(m1.equals(m2));
	}
	
	@Test
	void testNullManager() {
		assertTrue(nullManager == null);
	}

}
