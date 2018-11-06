package teamFarnsworth.Handlers;

import java.util.*;

import teamFarnsworth.Domain.Customer;
import teamFarnsworth.Domain.Manager;
import teamFarnsworth.Domain.Trainer;

public class MemberHandler {
	
	private Set<Manager> managers = new HashSet<Manager>();
	private Set<Trainer> trainers = new HashSet<Trainer>();
	private Set<Customer> customers = new HashSet<Customer>();
	
	private static MemberHandler memberHandler;
	
	private MemberHandler() {}
	
	public static MemberHandler getInstance() {
		if (memberHandler == null) {
			memberHandler = new MemberHandler();
		}
		return memberHandler;
	}
	
	public Set<Manager> getManagers() {
		return managers;
	}
	
	public void addManager(Manager m) {
		managers.add(m);
	}
	
	public void removeManager(Manager m) {
		managers.remove(m);
	}
	
	public Set<Trainer> getTrainers() {
		return trainers;
	}
	
	public void addTrainer(Trainer t) {
		trainers.add(t);
	}
	
	public void removeTrainer(Trainer t) {
		trainers.remove(t);
	}
	
	public Set<Customer> getCustomers() {
		return customers;
	}
	
	public void addCustomer(Customer c) {
		customers.add(c);
	}
	
	public void removeCustomer(Customer c) {
		customers.remove(c);
	}
	

}
