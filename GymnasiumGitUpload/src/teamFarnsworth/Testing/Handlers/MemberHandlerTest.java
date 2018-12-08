package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Manager;
import teamFarnsworth.Domain.Users.Trainer;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class MemberHandlerTest {

	private MemberHandler memberHandler;
	private Customer c1;
	private Customer c2;
	private Trainer t1;
	private Trainer t2;
	private Manager m1;
	private Manager m2;
	
	@BeforeEach
	void setUp() throws Exception {
		memberHandler = MemberHandler.getInstance();
		memberHandler.getCustomers().clear();
		memberHandler.getManagers().clear();
		memberHandler.getTrainers().clear();
		c1 = new Customer("Carl", "Customer", "Carl");
		c2 = new Customer("Christina", "Customer", "Christina");
		t1 = new Trainer("Tiffany", "Trainer", "Tiffany");
		t2 = new Trainer("Trevor", "Trainer", "Trevor");
		m1 = new Manager("Michelle", "Manager", "Michelle");
		m2 = new Manager("Mike", "Manager", "Mike");
	}

	@Test
	void testGetAllManagers() {
		memberHandler.addManager(m1);
		memberHandler.addManager(m2);
		assertTrue(memberHandler.getManagers().size() == 2);
	}
	
	@Test
	void testAddManager() {
		memberHandler.addManager(m1);
		memberHandler.addManager(m2);
		assertTrue(memberHandler.getManagers().contains(m1));
	}
	
	@Test
	void testRemoveManager() {
		memberHandler.addManager(m1);
		memberHandler.addManager(m2);
		memberHandler.removeManager(m1);
		assertFalse(memberHandler.getManagers().contains(m1));
		assertTrue(memberHandler.getManagers().size() == 1);
	}

	@Test
	void testGetAllTrainers() {
		memberHandler.addTrainer(t1);
		memberHandler.addTrainer(t2);
		assertTrue(memberHandler.getTrainers().size() == 2);
	}
	
	@Test
	void testAddTrainer() {
		memberHandler.addTrainer(t1);
		memberHandler.addTrainer(t2);
		assertTrue(memberHandler.getTrainers().contains(t1));
	}
	
	@Test
	void testRemoveTrainer() {
		memberHandler.addTrainer(t1);
		memberHandler.addTrainer(t2);
		memberHandler.removeTrainer(t1);
		assertFalse(memberHandler.getTrainers().contains(t1));
		assertTrue(memberHandler.getTrainers().size() == 1);
	}
	
	@Test
	void testGetAllCustomers() {
		memberHandler.addCustomer(c1);
		memberHandler.addCustomer(c2);
		assertTrue(memberHandler.getCustomers().size() == 2);
	}
	
	@Test
	void testAddCustomer() {
		memberHandler.addCustomer(c1);
		memberHandler.addCustomer(c2);
		assertTrue(memberHandler.getCustomers().contains(c1));
	}
	
	@Test
	void testRemoveCustomer() {
		memberHandler.addCustomer(c1);
		memberHandler.addCustomer(c2);
		memberHandler.removeCustomer(c1);
		assertFalse(memberHandler.getCustomers().contains(c1));
		assertTrue(memberHandler.getCustomers().size() == 1);
	}
}
