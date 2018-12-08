package teamFarnsworth.Domain.Users;

public class Address {

	private String street;
	private String city;
	private String state;
	private int zip;
	
	public Address() {}
	
	public Address(String street, String city, String state, int zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "Address: " + this.street + "\n" + this.city + ", " + this.state + " " + this.zip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + zip;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Address) {
			Address a = (Address) obj;
			return a.toString().equals(this.toString());
		}
		return false;
	}
	
}
