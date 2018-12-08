package teamFarnsworth.Domain.Entities;

import java.util.*;
import java.util.Set;

public class Exercise {

	private String name;
	private Equipment equipment;
	private Duration duration;
	private WorkoutSet workoutSet;
	
	public Exercise() {}
	
	public Exercise(String name, Duration duration) {
		this.name = name;
		this.duration = duration;
	}
	
	public Exercise(String name, int reps) {
		this.name = name;
		this.workoutSet = new WorkoutSet();
		this.workoutSet.addRep(reps);
	}
	
	public Exercise(String name) {
		this.name = name;
		this.duration = new Duration(1);
	}
	
	public Exercise(String name, Equipment equipment, Duration duration) {
		this.name = name;
		this.duration = duration;
	}
	
	public Exercise(String name, Equipment equipment, int reps) {
		this.name = name;
		this.equipment = equipment;
		this.workoutSet = new WorkoutSet();
		this.workoutSet.addRep(reps);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public void removeDuration() {
		this.duration = null;
	}

	public WorkoutSet getWorkoutSet() {
		return workoutSet;
	}
	
	public void removeWorkoutSet( ) {
		this.workoutSet = null;
	}
	
	public boolean removeSet(WorkoutSet set) {
		if (workoutSet.getWorkoutSet().contains(set)) {
			return (workoutSet.getWorkoutSet().remove(set));
		}
	return false;
	}
	
	public void setWorkoutSet(WorkoutSet set) {
		this.workoutSet = set;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Exercise) {
			Exercise e = (Exercise) obj;
			
			if (e.getName().equals(this.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (this.getDuration() != null) {
			if (this.getEquipment() != null) {
				return "Exercise: " + name + ", performed for " + duration.toString() + " minutes, on the " + equipment.getName() + "\n";
			}
		return "Exercise: " + name + ", performed for " + duration.toString() + " minutes" + "\n";
		} else if (this.getWorkoutSet() != null) {
			if (this.getEquipment() != null) {
				return "Exercise: " + name + ", performed on the " + equipment.getName() + "\n" + workoutSet.toString();
			}
			return "Exercise: " + name + "\n" + workoutSet.toString();
		}
		return "";
	}
}
