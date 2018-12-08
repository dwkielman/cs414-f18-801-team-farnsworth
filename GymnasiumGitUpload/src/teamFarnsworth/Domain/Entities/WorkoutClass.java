package teamFarnsworth.Domain.Entities;

import java.util.*;

import teamFarnsworth.Domain.Users.Customer;

public class WorkoutClass {

	private String name;
	private GymHours gymHours;
	private Set<Customer> attendees = new HashSet<Customer>();
	
	public WorkoutClass(String name, GymHours gymHours) {
		this.name = name;
		this.gymHours = gymHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GymHours getGymHours() {
		return gymHours;
	}

	public void setGymHours(GymHours gymHours) {
		this.gymHours = gymHours;
	}
	
	public Set<Customer> getAttendees() {
		return attendees;
	}
	
	public void enrollInClass(Customer c) {
		attendees.add(c);
	}
	
	public void unenrollFromClass(Customer c) {
		if (attendees.contains(c)) {
			attendees.remove(c);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WorkoutClass) {
			WorkoutClass c = (WorkoutClass) obj;
			return (this.getName() == c.getName());
		}
	return false;
	}

	@Override
	public String toString() {
		return ("Workout Class Name: " + name + "\n" +
				"Time of Class: " + gymHours.toString()
				);
	}
	
	
	
	
}
