package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Customer;

public class CustomerTest {

	private Customer c1;
	private Customer c2;
	private Customer c1Copy;
	private String n1 = "Jake";
	private String n2 = "Cindy";
	private String l1 = "Smith";
	private String l2 = "Johnson";
	private String id1 = "J";
	private String id2 = "C";
	
	@BeforeEach
	void setUp() throws Exception {
		c1 = new Customer(n1, l1, id1);
		c2 = new Customer(n2, l2, id2);
		c1Copy = new Customer(n1, l1, id1);
		
	}

	@Test
	void testCustomersNotEqual() {
		assertFalse(c1.equals(c2));
	}
	
	@Test
	void testCustomersEqual() {
		assertTrue(c1.equals(c1Copy));
	}

}
