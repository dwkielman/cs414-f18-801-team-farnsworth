package teamFarnsworth.Domain.Users;

import java.util.Objects;

public class Manager extends Person {
	
	public Manager() {}
	
	public Manager(String firstName, String lastName, String id) {
		super(firstName, lastName, id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.getID());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Manager) {
			Manager m = (Manager) obj;
			return m.getID() == this.getID();
		}
	return false;
	}
	
	@Override
	public String toString() {
		return ("Name: " + this.getFirstName() + " " + this.getLastName() + "\n" + 
				"ID: " + this.getID() + "\n" + 
				"Address: " + this.getAddress().toString() + "\n" + 
				"Phone Number: " + this.toStringPhone()  + "\n" + 
				"Email Address: " + this.getEmail() + "\n" + 
				"Health Insurance Provider: " + this.getHealthInsuranceProvider() + "\n"
				);
	}
	
	
}
