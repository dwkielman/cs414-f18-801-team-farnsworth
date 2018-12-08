package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.CustomerController;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Domain.Users.Membership;
import teamFarnsworth.Domain.Users.Person;
import teamFarnsworth.Domain.Users.Trainer;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class CustomerControllerTest {
	
	private CustomerController customerController;
	private MemberHandler memberHandler;
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
		customerController = new CustomerController();
		memberHandler = MemberHandler.getInstance();
		memberHandler.getCustomers().clear();
		firstName = "Jay";
		lastName = "Smith";
		id = "Jay";
		firstName2 = "Alice";
		lastName2 = "Jones";
		id2 = "Alice";
		c1 = new Customer(firstName, lastName, id);
		c2 = new Customer(firstName2, lastName2, id2);
	}

	@Test
	void testGetCustomersSet() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		assertTrue(customerController.getUsers().size() == 2);
	}
	
	@Test
	void testGetCustomerByPerson() {
		customerController.addUser(c1);
		assertTrue(customerController.getUser(c2) == null);
		assertTrue(customerController.getUser(c1).equals(c1));
	}
	
	@Test
	void testGetCustomerByID() {
		customerController.addUser(c1);
		assertTrue(customerController.getUser(id).equals(c1));
	}
	
	@Test
	void testAddCustomer() {
		customerController.addUser(c1);
		assertTrue(c1.equals(customerController.getUser(id)));
	}
	
	@Test
	void testRemoveCustomer() {
		customerController.addUser(c1);
		customerController.removeUser(c1);
		assertTrue(customerController.getUsers().isEmpty());
	}
	
	@Test
	void testSetMembershipStatusToActive() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		
		Customer c = customerController.getUser(id);
		c.setMembership(Membership.ACTIVE);
		
		for (Customer cus : customerController.getUsers()) {
			if (cus.equals(c)) {
				assertTrue(cus.getMembership().equals(Membership.ACTIVE));
			}
		}
		
		customerController.setMembershipStatus(c2, Membership.ACTIVE);
		assertTrue(customerController.getUser(id2).getMembership().equals(Membership.ACTIVE));
	}
	
	@Test
	void testSetMembershipStatusToBasic() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		
		Customer c = customerController.getUser(id);
		c.setMembership(Membership.BASIC);
		
		for (Customer cus : customerController.getUsers()) {
			if (cus.equals(c)) {
				assertTrue(cus.getMembership().equals(Membership.BASIC));
			}
		}
		
		customerController.setMembershipStatus(c2, Membership.BASIC);
		assertTrue(customerController.getUser(id2).getMembership().equals(Membership.BASIC));
	}
	
	@Test
	void testSetMembershipStatusToRegular() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		
		Customer c = customerController.getUser(id);
		c.setMembership(Membership.REGULAR);
		
		for (Customer cus : customerController.getUsers()) {
			if (cus.equals(c)) {
				assertTrue(cus.getMembership().equals(Membership.REGULAR));
			}
		}
		
		customerController.setMembershipStatus(c2, Membership.REGULAR);
		assertTrue(customerController.getUser(id2).getMembership().equals(Membership.REGULAR));
	}
	
	@Test
	void testSetMembershipStatusToPremium() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		
		Customer c = customerController.getUser(id);
		c.setMembership(Membership.PREMIUM);
		
		for (Customer cus : customerController.getUsers()) {
			if (cus.equals(c)) {
				assertTrue(cus.getMembership().equals(Membership.PREMIUM));
			}
		}
		
		customerController.setMembershipStatus(c2, Membership.PREMIUM);
		assertTrue(customerController.getUser(id2).getMembership().equals(Membership.PREMIUM));
	}

	@Test
	void testSetMembershipStatusToInactive() {
		customerController.addUser(c1);
		customerController.addUser(c2);
		
		Customer c = customerController.getUser(id);
		c.setMembership(Membership.INACTIVE);
		
		for (Customer cus : customerController.getUsers()) {
			if (cus.equals(c)) {
				assertTrue(cus.getMembership().equals(Membership.INACTIVE));
			}
		}
		
		customerController.setMembershipStatus(c2, Membership.INACTIVE);
		assertTrue(customerController.getUser(id2).getMembership().equals(Membership.INACTIVE));
	}
	
}
