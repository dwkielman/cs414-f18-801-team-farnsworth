package teamFarnsworth.Domain.Entities;

public class Duration {

	private int minutes;
	
	public Duration() {}
	
	public Duration(int minutes) {
		this.minutes = minutes;
	}

	public int getDuration() {
		return minutes;
	}

	public void setDuration(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + minutes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Duration) {
			Duration d = (Duration) obj;
			return (d.getDuration() == this.getDuration());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Integer.toString(minutes);
	}

}
