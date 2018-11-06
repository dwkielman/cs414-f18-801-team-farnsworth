package teamFarnsworth.Handlers;

import java.util.*;

import teamFarnsworth.Domain.Password;

public class LoginHandler {

	private Map<String, Password> logins = new HashMap<String, Password>();
	private static Map<String, String> accountType = new HashMap<String, String>();
	private static LoginHandler loginHandler;
	
	public static LoginHandler getInstance() {
		if(loginHandler == null) {
			loginHandler = new LoginHandler();
		}
		return loginHandler;
	}
	
	public boolean login(String ID, Password p) {
		if (logins.get(ID) != null) {
			if (logins.get(ID).equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	public void setAccountInSystem(String ID, Password p) {
		logins.put(ID, p);
	}
	
	public void linkAccountType(String ID, String account) {
		accountType.put(ID, account);
	}
	
	public String getAccountType(String ID) {
		if (accountType.get(ID) != null) {
			
			return accountType.get(ID);
		}
		return "Does not exist";
	}
	
}
