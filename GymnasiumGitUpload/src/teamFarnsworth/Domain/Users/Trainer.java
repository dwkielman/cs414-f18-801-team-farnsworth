package teamFarnsworth.Domain.Users;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import teamFarnsworth.Domain.Entities.GymHours;

import java.time.*;

public class Trainer extends Person {

	private List<GymHours> gymHours = new ArrayList<GymHours>();
	private Set<Qualification> qualifications = new HashSet<Qualification>();
	private List<Customer> customers = new ArrayList<Customer>();
	
	public Trainer() {}
	
	public Trainer(String firstName, String lastName, String id) {
		super(firstName, lastName, id);
	}
	
	public void addWorkHoursToSchedule(LocalTime startTime, LocalTime endTime, DayOfWeek day) {
		GymHours gh = new GymHours(startTime, endTime, day);
		gymHours.add(gh);
	}
	
	public void removeWorkHoursFromSchedule(GymHours gh) {
		gymHours.remove(gh);
	}
	
	public List<GymHours> getSchedule() {
		return gymHours;
	}
	
	public void clearSchedule() {
		gymHours = null;
	}
	
	public void addQualification(Qualification q) {
		qualifications.add(q);
	}
	
	public void removeQualification(Qualification q) {
		qualifications.remove(q);
	}
	
	public Set<Qualification> getQualifications() {
		return qualifications;
	}
	
	public List<Customer> getCustomers() {
		if (customers.isEmpty()) {
			return null;
		}
		return customers;
	}
	
	public void addCustomerToTrainer(Customer c) {
		if (!customers.contains(c)) {
			customers.add(c);
		}
	}
	
	public Customer removeCustomer(Customer c) {
		if (customers.contains(c)) {
			for (Customer returnCustomer : customers) {
				if (returnCustomer.equals(c)) {
					returnCustomer = c;
					customers.remove(c);
					return returnCustomer;
				}
			}
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(super.getID());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Trainer) {
			Trainer t = (Trainer) obj;
			return t.getID() == this.getID();
		}
	return false;
	}
	
	@Override
	public String toString() {
		String returnString = "";
		returnString = "Name: " + this.getFirstName() + " " + this.getLastName() + "\n" + 
				"ID: " + this.getID() + "\n" + 
				"Address: " + this.getAddress().toString() + "\n" + 
				"Phone Number: " + this.toStringPhone()  + "\n" + 
				"Email Address: " + this.getEmail() + "\n" + 
				"Health Insurance Provider: " + this.getHealthInsuranceProvider() + "\n" +
				"Work Schedule:\n";
				//Arrays.sort(gymHours);
		
				for (GymHours gh : gymHours) {
					returnString += gh.toString() + "\n";
				}
				returnString += "Qualifications: \n";
				for (Qualification q : qualifications) {
					returnString += q.toString() + "\n";
				}
		return returnString;
	}
	
}
