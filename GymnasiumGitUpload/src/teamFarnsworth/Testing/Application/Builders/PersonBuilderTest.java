package teamFarnsworth.Testing.Application.Builders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Builders.PersonBuilder;
import teamFarnsworth.Domain.Users.Address;
import teamFarnsworth.Domain.Users.HealthInsuranceProvider;
import teamFarnsworth.Domain.Users.Password;
import teamFarnsworth.Domain.Users.Person;

public class PersonBuilderTest {

	private String validFirstName = "validFirst";
	private String validLastName = "validLast";
	private String validPhone = "1234567891";
	private String validEmail = "valid@email.com";
	private String validIDMan = "validManagerID";
	private String validIDTrainer = "validTrainerID";
	private String validIDCustomer = "validCUstomerID";
	private String validStreet = "validStreet";
	private String validCity = "validCity";
	private String validState = "validState";
	private int validZip = 12345;
	private String validHealthInsuranceProvider = "validHealthInsuranceProvider";
	private String validPassword = "password";
	private PersonBuilder personBuilder;
	private Person personManager;
	private Person personTrainer;
	private Person personCustomer;
	
	
	@BeforeEach
	void setUp() throws Exception {
		personBuilder = new PersonBuilder();
		
		personBuilder.addFirstName(validFirstName);
		personBuilder.addLastName(validLastName);
		personBuilder.addPhone(validPhone);
		personBuilder.addEmail(validEmail);
		personBuilder.addID(validIDMan);
		personBuilder.addAddress(validStreet, validCity, validState, validZip);
		personBuilder.addHealthInsuranceProvider(validHealthInsuranceProvider);
		personBuilder.addPassword(validPassword);
		personManager = personBuilder.BuildPerson("Manager");
		
		personBuilder = new PersonBuilder();
		
		personBuilder.addFirstName(validFirstName);
		personBuilder.addLastName(validLastName);
		personBuilder.addPhone(validPhone);
		personBuilder.addEmail(validEmail);
		personBuilder.addID(validIDTrainer);
		personBuilder.addAddress(validStreet, validCity, validState, validZip);
		personBuilder.addHealthInsuranceProvider(validHealthInsuranceProvider);
		personBuilder.addPassword(validPassword);
		personTrainer = personBuilder.BuildPerson("Trainer");
		
		personBuilder = new PersonBuilder();
		
		personBuilder.addFirstName(validFirstName);
		personBuilder.addLastName(validLastName);
		personBuilder.addPhone(validPhone);
		personBuilder.addEmail(validEmail);
		personBuilder.addID(validIDCustomer);
		personBuilder.addAddress(validStreet, validCity, validState, validZip);
		personBuilder.addHealthInsuranceProvider(validHealthInsuranceProvider);
		personBuilder.addPassword(validPassword);
		personCustomer = personBuilder.BuildPerson("Customer");
	}

	@Test
	void testBuildPersonManagerFirstName() {
		assertTrue(personManager.getFirstName().equals(validFirstName));
	}
	
	@Test
	void testBuildPersonManagerLastName() {
		assertTrue(personManager.getLastName().equals(validLastName));
	}
	
	@Test
	void testBuildPersonManagerPhone() {
		assertTrue(personManager.getPhone() == (validPhone));
	}

	@Test
	void testBuildPersonManagerEmail() {
		assertTrue(personManager.getEmail().equals(validEmail));
	}
	
	@Test
	void testBuildPersonManagerID() {
		assertTrue(personManager.getID().equals(validIDMan));
	}
	
	@Test
	void testBuildPersonManagerAddress() {
		Address a = new Address(validStreet, validCity, validState, validZip);
		assertTrue(personManager.getAddress().equals(a));
	}
	
	@Test
	void testBuildPersonManagerHealthInsurnaceProvider() {
		HealthInsuranceProvider hip = new HealthInsuranceProvider(validHealthInsuranceProvider);
		assertTrue(personManager.getHealthInsuranceProvider().equals(hip));
	}
	
	@Test
	void testBuildPersonManagerPassword() {
		Password p = new Password(validPassword);
		assertTrue(personManager.getPassword().equals(p));
	}
	
	@Test
	void testBuildPersonManagerTrainerNotEqual() {
		assertFalse(personManager.equals(personTrainer));
	}
	
	@Test
	void testBuildPersonManagerCustomerNotEqual() {
		assertFalse(personManager.equals(personCustomer));
	}
	
	@Test
	void testBuildPersonTrainerCustomerNotEqual() {
		assertFalse(personTrainer.equals(personCustomer));
	}
	
}
