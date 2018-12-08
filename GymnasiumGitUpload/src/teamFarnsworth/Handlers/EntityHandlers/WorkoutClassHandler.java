package teamFarnsworth.Handlers.EntityHandlers;

import java.util.*;

import teamFarnsworth.Domain.Entities.WorkoutClass;

public class WorkoutClassHandler {

	private static WorkoutClassHandler workoutClassHandler;
	private Set<WorkoutClass> workoutClasses = new HashSet<WorkoutClass>();
	
	private WorkoutClassHandler(){ }
	public static WorkoutClassHandler getInstance(){
		if(workoutClassHandler == null){
			workoutClassHandler = new WorkoutClassHandler();
		}
		return workoutClassHandler;	
    }
	
	public Set<WorkoutClass> getWorkoutClasses(){
		return workoutClasses;
	}
	
	public WorkoutClass addWorkoutClass(WorkoutClass workoutClass){
		workoutClasses.add(workoutClass);
		return workoutClass;
	}
	
	public WorkoutClass removeWorkoutClass(WorkoutClass workoutClass){
		workoutClasses.remove(workoutClass);
		return workoutClass;
	}
	
}
