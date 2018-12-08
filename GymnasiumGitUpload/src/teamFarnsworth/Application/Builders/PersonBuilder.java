package teamFarnsworth.Application.Builders;

import teamFarnsworth.Application.Accessors.PersonFactory;
import teamFarnsworth.Domain.Users.Address;
import teamFarnsworth.Domain.Users.HealthInsuranceProvider;
import teamFarnsworth.Domain.Users.Password;
import teamFarnsworth.Domain.Users.Person;

public class PersonBuilder {
	
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String ID;
	private Address address;
	private HealthInsuranceProvider healthInsuranceProvider;
	private Password password;
	
	public String addFirstName(String name) {
		this.firstName = name;
		return firstName;
	}
	public String addLastName(String name) {
		this.lastName = name;
		return lastName;
	}
	public String addPhone(String number) {
		this.phone = number;
		return phone;
	}
	public String addEmail(String emailAddress) {
		this.email = emailAddress;
		return email;
	}
	public String addID(String id) {
		this.ID = id;
		return ID;
	}
	public Address addAddress(String street, String city, String state, int zip) {
		Address a = new Address(street, city, state, zip);
		this.address = a;
		return address;
	}
	public HealthInsuranceProvider addHealthInsuranceProvider(String name) {
		HealthInsuranceProvider hip = new HealthInsuranceProvider(name);
		this.healthInsuranceProvider = hip;
		return healthInsuranceProvider;
	}
	public Password addPassword(String pass) {
		Password pw = new Password(pass);
		this.password = pw;
		return password;
	}
	
	
	public Person BuildPerson(String userType) {
		PersonFactory personFactory = new PersonFactory();
		Person person = personFactory.createPerson(userType);
		
		if (firstName != null) {
			person.setFirstName(firstName);
		}
		if (lastName != null) {
			person.setLastName(lastName);
		}
		if (phone != null) {
			person.setPhone(phone);
		}
		if (email != null) {
			person.setEmail(email);
		}
		if (ID != null) {
			person.setID(ID);
		}
		if (address != null) {
			person.setAddress(address);
		}
		if (healthInsuranceProvider != null) {
			person.setHealthInsuranceProvider(healthInsuranceProvider);
		}
		if (password != null) {
			person.setPassword(password.getPassword());
		}
		return person;
		
		
	}

}
