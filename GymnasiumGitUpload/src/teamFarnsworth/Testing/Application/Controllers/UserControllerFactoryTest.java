package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.CustomerController;
import teamFarnsworth.Application.Controllers.ManagerController;
import teamFarnsworth.Application.Controllers.TrainerController;
import teamFarnsworth.Application.Controllers.UserController;
import teamFarnsworth.Application.Controllers.UserControllerFactory;

public class UserControllerFactoryTest {

	private UserControllerFactory userControllerFactory;
	private UserController userController;
	private String m;
	private String t;
	private String c;
	
	@BeforeEach
	void setUp() throws Exception {
		userControllerFactory = new UserControllerFactory();
		m = "Manager";
		t = "Trainer";
		c = "Customer";
	}
	
	@Test
	void testCreateControllerForManager() {
		ManagerController managerController = new ManagerController();
		userController = userControllerFactory.createController(m);
		assertTrue(userController.getClass() == managerController.getClass());
	}

	@Test
	void testCreateControllerForTrainer() {
		TrainerController trainerController = new TrainerController();
		userController = userControllerFactory.createController(t);
		assertTrue(userController.getClass() == trainerController.getClass());
	}
	
	@Test
	void testCreateControllerForCustomer() {
		CustomerController customerController = new CustomerController();
		userController = userControllerFactory.createController(c);
		assertTrue(userController.getClass() == customerController.getClass());
	}
	
	@Test
	void testCreateControllerForNullController() {
		userController = userControllerFactory.createController("NotAType");
		assertTrue(userController == null);
	}
}
