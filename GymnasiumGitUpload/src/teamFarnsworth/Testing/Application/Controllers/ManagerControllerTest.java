package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.ManagerController;
import teamFarnsworth.Application.Controllers.TrainerController;
import teamFarnsworth.Domain.Users.Manager;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class ManagerControllerTest {

	private ManagerController managerController;
	private MemberHandler memberHandler;
	private String nameFirst;
	private String nameLast;
	private String managerID;
	private String nameFirst2;
	private String nameLast2;
	private String managerID2;
	private Manager m1;
	private Manager m2;
	
	@BeforeEach
	void setUp() throws Exception {
		managerController = new ManagerController();
		memberHandler = MemberHandler.getInstance();
		memberHandler.getManagers().clear();
		nameFirst = "Mandy";
		nameLast = "Manager";
		managerID = "Mandy";
		nameFirst2 = "Mike";
		nameLast2 = "Manager";
		managerID2 = "Mike";
		m1 = new Manager(nameFirst, nameLast, managerID);
		m2 = new Manager(nameFirst2, nameLast2, managerID2);
		
	}

	@Test
	void testGetManagersSet() {
		managerController.addUser(m1);
		managerController.addUser(m2);
		assertTrue(managerController.getUsers().size() == 2);
	}
	
	@Test
	void testGetManagerByPerson() {
		managerController.addUser(m1);
		assertTrue(managerController.getUser(m2) == null);
		assertTrue(managerController.getUser(m1).equals(m1));
	}

	@Test
	void testGetManagerByID() {
		managerController.addUser(m1);
		assertTrue(managerController.getUser(managerID).equals(m1));
	}
	
	@Test
	void testAddManager() {
		managerController.addUser(m1);
		assertTrue(m1.equals(managerController.getUser(managerID)));
	}
	
	@Test
	void testRemoveManager() {
		managerController.addUser(m1);
		managerController.removeUser(m1);
		assertTrue(managerController.getUsers().isEmpty());
	}

}
