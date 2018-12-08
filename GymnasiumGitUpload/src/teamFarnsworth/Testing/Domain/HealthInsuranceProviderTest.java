package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.HealthInsuranceProvider;

public class HealthInsuranceProviderTest {

	private String name1;
	private String name1Copy;
	private String name2;
	private HealthInsuranceProvider h1;
	private HealthInsuranceProvider h2;
	private HealthInsuranceProvider h1Copy;
	private HealthInsuranceProvider hnull;
	
	@BeforeEach
	void setUp() throws Exception {
		name1 = "Liberty";
		name1Copy = "Liberty";
		name2 = "Kaiser";
		h1 = new HealthInsuranceProvider(name1);
		h2 = new HealthInsuranceProvider(name2);
		h1Copy = new HealthInsuranceProvider(name1Copy);
	}

	@Test
	void testHealthInsuranceProviderEqual() {
		assertTrue(h1.equals(h1Copy));
	}

	@Test
	void testHealthInsuranceProviderNotEqual() {
		assertFalse(h1.equals(h2));
	}
	
	@Test
	void testNullHealthInsuranceProvider() {
		assertTrue(hnull == null);
	}
	
}
