package teamFarnsworth.Domain.Entities;

import java.util.*;

public class WorkoutSet {

	private List<Integer> workoutSet = new ArrayList<Integer>();
	private int numberOfReps;
	
	public WorkoutSet() {}
	public WorkoutSet(int reps) {
		this.workoutSet.add(reps);
	}

	public List<Integer> getWorkoutSet() {
		return workoutSet;
	}

	public void addRep(int reps) {
		if (reps > 0) {
			this.workoutSet.add(reps);
		}
	}
	
	public void removeRep(int index) {
		if (index < workoutSet.size() && index >= 0) {
			this.workoutSet.remove(index);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfReps;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// two WorkoutSets are equal if they are the same number of reps over the same number of sets
		if (obj instanceof WorkoutSet) {
			WorkoutSet ws = (WorkoutSet) obj;
			if (ws.getWorkoutSet().size() == this.getWorkoutSet().size()) {
				for (int i=0; i<ws.getWorkoutSet().size(); i++) {
					if (ws.getWorkoutSet().get(i) != this.getWorkoutSet().get(i)) {
						return false;
					}
				}
				// size and all sets were the same
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String returnString = "";
		for (int i=0; i<this.getWorkoutSet().size(); i++) {
			returnString += "Set " + (i + 1) + ": " + workoutSet.get(i) + " Reps\n";
		}
		return returnString;
	}
}
