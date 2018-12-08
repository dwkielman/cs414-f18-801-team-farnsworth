package teamFarnsworth.Application.Controllers;

import java.util.Set;

import teamFarnsworth.Domain.Users.Manager;
import teamFarnsworth.Domain.Users.Person;
import teamFarnsworth.Handlers.UserHandlers.MemberHandler;

public class ManagerController implements UserController<Manager> {
	
	private MemberHandler mHandler;

	public ManagerController() {
		mHandler = MemberHandler.getInstance();
	}
	
	@Override
	public Set<Manager> getUsers() {
		return mHandler.getManagers();
	}

	@Override
	public Person getUser(Person person) {
		for (Manager m : mHandler.getManagers()) {
			if (m.equals(person)) {
				return m;
			}
		}
		return null;
	}
	
	@Override
	public Person getUser(String ID) {
		for (Manager m : mHandler.getManagers()){
			if (m.getID().equals(ID)){
				return m;
			}
		}
		return null;
	}

	@Override
	public Manager addUser(Person person) {
		if (person instanceof Manager) {
			Manager m = (Manager) person;
			mHandler.addManager(m);
			return m;
		}
		return null;
	}

	@Override
	public Manager removeUser(Person person) {
		if (person instanceof Manager) {
			Manager m = (Manager) person;
			mHandler.removeManager(m);
			return m;
		}
		return null;
	}

	@Override
	public String getAllUsers() {
		String returnString = "";
		for (Manager t : mHandler.getManagers()) {
			returnString += t.toStringBrief() + "\n";
		}
		return returnString;
	}
	
	

}
