package teamFarnsworth.Domain;

import java.util.*;

public class Customer extends Person {

	private Membership membership;
	private Set<WorkoutClass> workoutClasses = new HashSet<WorkoutClass>();
	
	public Customer() {}
	
	public Customer(String firstName, String lastName, String id) {
		super(firstName, lastName, id);
		this.membership = Membership.ACTIVE;
	}
	
	public void setMembership(Membership m) {
		this.membership = m;
	}
	
	public Membership getMembership() {
		return this.membership;
	}
	
	@Override
	public String toString() {
		return ("Name:" + this.getFirstName() + " " + this.getLastName() + "\n" + 
				"ID: " + this.getID() + "\n" + 
				"Address: " + this.getAddress().toString() + "\n" + 
				"Phone Number: " + this.toStringPhone()  + "\n" + 
				"Email Address: " + this.getEmail() + "\n" + 
				"Health Insurance Provider: " + this.getHealthInsuranceProvider() + "\n" + 
				"Membership Status: " + this.getMembership().toString());
	}
	
	public void enrollInClass(WorkoutClass workoutClass) {
		if(!workoutClasses.contains(workoutClass)) {
			workoutClasses.add(workoutClass);
		}
	}
	
	public void unenrollFromClass(WorkoutClass workoutClass) {
		if (workoutClasses.contains(workoutClass)) {
			workoutClasses.remove(workoutClass);
		}
	}
	
	public Set<WorkoutClass> getEnrolledClasses() {
		return workoutClasses;
	}
	
	public String workoutClassesToString() {
		String classes = "";
		
		if (!workoutClasses.isEmpty()) {
			for (WorkoutClass wc : workoutClasses) {
				classes += wc.toString() + "\n";
			}
		}
		return classes;
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.getID());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Customer) {
			Customer c = (Customer) obj;
			return c.getID() == this.getID();
		}
	return false;
	}
}
