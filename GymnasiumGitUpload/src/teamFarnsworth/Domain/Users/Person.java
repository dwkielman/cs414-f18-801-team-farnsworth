package teamFarnsworth.Domain.Users;

import java.util.Objects;

public abstract class Person {

		private String firstName;
		private String lastName;
		private String phone;
		private String email;
		private String ID;
		private Address address;
		private HealthInsuranceProvider healthInsuranceProvider;
		private Password password;

		public Person() {}
		
		public Person(String firstName, String lastName, String id) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.ID = id;
		}
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPhone() {
			return phone;
		}
		public String toStringPhone() {
			return ("(" + this.phone.substring(0, 3) + ")-" + this.phone.substring(3, 6) + "-" + this.phone.substring(6, 10));
		}
		public void setPhone(String phone) {
			if (phone.length() == 10) {
				this.phone = phone;
			}
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		
		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public HealthInsuranceProvider getHealthInsuranceProvider() {
			return healthInsuranceProvider;
		}

		public void setHealthInsuranceProvider(HealthInsuranceProvider healthInsuranceProvider) {
			this.healthInsuranceProvider = healthInsuranceProvider;
		}
		
		public Password getPassword() {
			return password;
		}

		public void setPassword(String pw) {
			Password passwordNew = new Password(pw);
			this.password = passwordNew;
		}
		
		public String toStringBrief() {
			return "Name: " + this.firstName + " " + this.lastName + ", " + this.ID;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Person) {
				Person p = (Person) obj;
				return this.ID == p.getID();
			}
		return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.getID());
		}
		
}
