package teamFarnsworth.Domain.Users;

import java.util.*;

import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Domain.Entities.WorkoutClass;

public class Customer extends Person {

	private Membership membership;
	private Set<WorkoutClass> workoutClasses = new HashSet<WorkoutClass>();
	private Set<Routine> routines = new HashSet<Routine>();
	private Trainer privateTrainer;
	
	public Customer() {}
	
	public Customer(String firstName, String lastName, String id) {
		super(firstName, lastName, id);
		this.membership = Membership.BASIC;
	}
	
	public void setMembership(Membership m) {
		this.membership = m;
	}
	
	public Membership getMembership() {
		return this.membership;
	}
	
	@Override
	public String toString() {
		return ("Name: " + this.getFirstName() + " " + this.getLastName() + "\n" + 
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
	
	public void enrollWithTrainer(Trainer t) {
		privateTrainer = t;
	}
	
	
	public void unenrollFromTrainer() {
		if (privateTrainer != null) {
			privateTrainer = null;
		}
	}
	
	public Trainer getPrivateTrainer() {
		return privateTrainer;
	}
	
	public Set<Routine> getRoutines() {
		return routines;
	}
	
	public void addRoutine(Routine r) {
		routines.add(r);
	}
	
	public void removeRoutine(Routine r) {
		routines.remove(r);
	}
	
	public String routinesToString() {
		String routineString = "";
		
		if (!routines.isEmpty()) {
			for (Routine rou : routines) {
				routineString += rou.toString();
			}
		}
		return routineString;
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
