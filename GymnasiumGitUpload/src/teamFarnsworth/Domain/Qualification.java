package teamFarnsworth.Domain;

import java.util.*;

public class Qualification {
	
	private String description;
	
	public Qualification(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Qualification) {
			Qualification q = (Qualification) obj;
			return q.getDescription() == this.description;
		}
	return false;
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(description);
	}

}
