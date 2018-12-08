package teamFarnsworth.Domain.Users;

public enum Membership {

	ACTIVE ("A"), INACTIVE ("I"), BASIC ("B"), REGULAR ("R"), PREMIUM ("P");
	
	private String status;
	
	private Membership(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
}
