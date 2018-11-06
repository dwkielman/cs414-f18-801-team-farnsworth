package teamFarnsworth.Domain;

public enum Membership {

	ACTIVE ("A"), INACTIVE ("I");
	
	private String status;
	
	private Membership(String status) {
		this.status = status;
	}
	
}
