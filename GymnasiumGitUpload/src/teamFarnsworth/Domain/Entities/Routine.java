package teamFarnsworth.Domain.Entities;

import java.util.*;
import java.util.Set;

public class Routine {

	private String name;
	private Set<Exercise> exercises = new HashSet<Exercise>();
	
	public Routine() {}
	
	public Routine(String name, Exercise exercise) {
		this.name = name;
		this.exercises.add(exercise);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Exercise> getExercises() {
		return exercises;
	}
	
	public void removeExercise(Exercise exercise) {
		this.exercises.remove(exercise);
	}

	public void addExercise(Exercise exercise) {
		if (!exercises.contains(exercise)) {
			exercises.add(exercise);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exercises == null) ? 0 : exercises.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Routine) {
			Routine r = (Routine) obj;
			if (r.getName().equals(this.getName())) {
				return true;
				}
			}
		return false;
	}

	@Override
	public String toString() {
		String returnString = "Routine Name: " + name + "\n";
		return returnString;
	}
}
