package teamFarnsworth.Handlers.EntityHandlers;

import java.util.*;

import teamFarnsworth.Domain.Entities.Exercise;

public class ExerciseHandler {

	private static ExerciseHandler exerciseHandler;
	private Set<Exercise> exercises = new HashSet<Exercise>();
	
	private ExerciseHandler(){ }
	public static ExerciseHandler getInstance(){
		if(exerciseHandler == null){
			exerciseHandler = new ExerciseHandler();
		}
		return exerciseHandler;	
    }
	
	public Set<Exercise> getExercises(){
		return exercises;
	}
	public Exercise addExercise(Exercise e){
		exercises.add(e);
		return e;
	}
	public Exercise removeExercise(Exercise e){
		exercises.remove(e);
		return e;
	}
}
