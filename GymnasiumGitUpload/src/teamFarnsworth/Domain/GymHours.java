package teamFarnsworth.Domain;

import java.util.*;
import java.time.*;

public class GymHours implements Comparator<GymHours> {

	private LocalTime startTime;
	private LocalTime endTime;
	private DayOfWeek day;
	
	public GymHours() {}
	
	public GymHours(LocalTime startTime, LocalTime endTime, DayOfWeek day) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}
	
	//LocalTime myTime = LocalTime.of(1, 1);
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		if (this.endTime.isBefore(startTime)) {
			System.out.println("Start Time must be before current End Time");
			return;
		}
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		if (this.startTime.isBefore(endTime)) {
			System.out.println("End Time must be after current Start Time");
			return;
		}
		this.endTime = endTime;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return (day + ": " + startTime + "-" + endTime);
	}

	@Override
	// Sort schedule by day of week
	// currently not implemented
	public int compare(GymHours gh1, GymHours gh2) {
		return gh1.day.compareTo(gh2.day);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GymHours) {
			GymHours gh = (GymHours) obj;
			if (this.startTime.equals(gh.getStartTime())) {
				if (this.endTime.equals(gh.getEndTime())) {
					if (this.day.equals(gh.getDay())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
