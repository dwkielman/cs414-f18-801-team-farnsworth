package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Password;
import teamFarnsworth.Handlers.SystemHandlers.LoginHandler;

public class LoginHandlerTest {

	private String customerLogin;
	private String trainerLogin;
	private String managerLogin;
	private Password password;
	private Password newPassword;
	private Password wrongPassword;
	private LoginHandler loginHandler;
	
	@BeforeEach
	void setUp() throws Exception {
		customerLogin = "C";
		trainerLogin = "T";
		managerLogin = "M";
		password = new Password("pass");
		newPassword = new Password("newPass");
		wrongPassword = new Password("gibberish");
		loginHandler = LoginHandler.getInstance();
		loginHandler.setAccountInSystem(customerLogin, password);
		loginHandler.linkAccountType(customerLogin, "Customer");
		loginHandler.linkAccountType(trainerLogin, "Trainer");
	}

	@Test
	void testCorrectPasswordLogin() {
		assertTrue(loginHandler.login(customerLogin, password));
	}
	
	@Test
	void testWrongPasswordLogin() {
		assertFalse(loginHandler.login(customerLogin, wrongPassword));
	}

	@Test
	void testAccountNotSyncedInSystem() {
		assertFalse(loginHandler.login(managerLogin, password));
	}
	
	@Test
	void testGetAccountType() {
		assertTrue(loginHandler.getAccountType(customerLogin).equals("Customer"));
	}
	
	@Test
	void testUpdatePasswordInSystem() {
		loginHandler.setAccountInSystem(customerLogin, newPassword);
		assertFalse(loginHandler.login(customerLogin, password));
		assertTrue(loginHandler.login(customerLogin, newPassword));
	}
	
	@Test
	void testGetNonExistentAccountType() {
		assertTrue(loginHandler.getAccountType(managerLogin).equals("Does not exist"));
	}
}
