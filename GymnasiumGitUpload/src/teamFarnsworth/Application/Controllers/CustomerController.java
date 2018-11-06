package teamFarnsworth.Application.Controllers;

import java.util.Set;

import teamFarnsworth.Domain.Customer;
import teamFarnsworth.Domain.Membership;
import teamFarnsworth.Domain.Person;
import teamFarnsworth.Handlers.MemberHandler;

public class CustomerController implements UserController<Customer> {

	private MemberHandler mHandler;

	public CustomerController() {
		mHandler = MemberHandler.getInstance();
	}
	
	@Override
	public Set<Customer> getUsers() {
		return mHandler.getCustomers();
	}

	@Override
	public Person getUser(Person person) {
		for (Customer c : mHandler.getCustomers()) {
			if (c.equals(person)) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public Customer getUser(String ID) {
		for (Customer c : mHandler.getCustomers()){
			if (c.getID().equals(ID)){
				return c;
			}
		}
		return null;
	}

	@Override
	public Customer addUser(Person person) {
		if (person instanceof Customer) {
			Customer c = (Customer) person;
			mHandler.addCustomer(c);
			return c;
		}
		return null;
	}

	@Override
	public Customer removeUser(Person person) {
		if (person instanceof Customer) {
			Customer c = (Customer) person;
			mHandler.removeCustomer(c);
			return c;
		}
		return null;
	}

	@Override
	public String getAllUsers() {
		String returnString = "";
		for (Customer c : mHandler.getCustomers()) {
			returnString += c.toStringBrief() + "\n";
		}
		return returnString;
	}
	
	public String getWorkoutClasses(Customer c) {
		if (mHandler.getCustomers().contains(c)) {
			return c.workoutClassesToString();
		}
		return "Customer is not enrolled in any Workout Classes\n";
	}
	
	public void setMembershipStatus(Customer c, Membership m) {
		for (Customer customer : mHandler.getCustomers()) {
			if (customer.equals(c)) {
				customer.setMembership(m);
			}
		}
	}
	
	
	
	
}
