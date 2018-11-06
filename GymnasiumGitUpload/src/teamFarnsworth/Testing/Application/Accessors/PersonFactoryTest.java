package teamFarnsworth.Testing.Application.Accessors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Accessors.PersonFactory;
import teamFarnsworth.Domain.Customer;
import teamFarnsworth.Domain.Manager;
import teamFarnsworth.Domain.Person;
import teamFarnsworth.Domain.Trainer;

public class PersonFactoryTest {
	
	private PersonFactory personFactory;
	private Person p;
	
	@BeforeEach
	void setUp() throws Exception {
		personFactory = new PersonFactory();
	}

	@Test
	void testManagerCreation() {
		Manager m = new Manager();
		p = personFactory.createPerson("Manager");
		assertTrue(p.getClass().equals(m.getClass()));
	}
	
	@Test
	void testTrainerCreation() {
		Trainer t = new Trainer();
		p = personFactory.createPerson("Trainer");
		assertTrue(p.getClass().equals(t.getClass()));
	}
	
	@Test
	void testCustomerCreation() {
		Customer c = new Customer();
		p = personFactory.createPerson("Customer");
		assertTrue(p.getClass().equals(c.getClass()));
	}
	
	@Test
	void testNullPerson() {
		p = personFactory.createPerson("Invalid Class Name");
		assertTrue(p == null);
	}

}
