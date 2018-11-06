package teamFarnsworth.Application.Accessors;

import teamFarnsworth.Domain.Customer;
import teamFarnsworth.Domain.Manager;
import teamFarnsworth.Domain.Person;
import teamFarnsworth.Domain.Trainer;

public class PersonFactory extends Person {
	
	public Person createPerson(String userType) {
		Person person = null;
		
		if (userType.equals("Manager")) {
			person = new Manager();
		} else if (userType.equals("Trainer")) {
			person = new Trainer();
		}else if (userType.equals("Customer")) {
			person = new Customer();
		}
		
		return person;
	}
	
	

}
