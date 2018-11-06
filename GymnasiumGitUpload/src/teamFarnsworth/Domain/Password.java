package teamFarnsworth.Domain;

public class Password {

	private String password;
	
	public Password(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Password) {
			Password p = (Password) obj;
			return p.getPassword().equals(this.password);
		}
		return false;
	}
	
	
	
}
