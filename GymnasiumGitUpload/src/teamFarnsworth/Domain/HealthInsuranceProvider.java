package teamFarnsworth.Domain;

import java.util.*;

public class HealthInsuranceProvider {

	private String name;

	public HealthInsuranceProvider(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HealthInsuranceProvider) {
			HealthInsuranceProvider h = (HealthInsuranceProvider) obj;
			return h.getName() == this.name;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
}
