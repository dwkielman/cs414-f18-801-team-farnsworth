package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Password;

public class PasswordTest {

	private String passName1;
	private String passName2;
	private Password p1;
	private Password p1Copy;
	private Password p2;
	private Password nullPassword;
	
	
	@BeforeEach
	void setUp() throws Exception {
		passName1 = "pass";
		passName2 = "diffPass";
		p1 = new Password(passName1);
		p1Copy = new Password(passName1);
		p2 = new Password(passName2);
	}
	
	@Test
	void testPasswordEquals() {
		assertTrue(p1.equals(p1Copy));
	}
	
	@Test
	void testPasswordNotEqual() {
		assertFalse(p1.equals(p2));
	}
	
	@Test
	void testNullPassword() {
		assertTrue(nullPassword == null);
	}
	

}
