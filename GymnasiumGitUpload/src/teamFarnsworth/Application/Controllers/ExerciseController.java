package teamFarnsworth.Application.Controllers;

import java.util.*;

import teamFarnsworth.Domain.Duration;
import teamFarnsworth.Domain.Equipment;
import teamFarnsworth.Domain.Exercise;
import teamFarnsworth.Handlers.ExerciseHandler;

public class ExerciseController {

	private ExerciseHandler exerciseHandler;
	
	public ExerciseController(){
		exerciseHandler = ExerciseHandler.getInstance();
	}
	
	public Set<Exercise> getExercises(){
		return exerciseHandler.getExercises();
	}
	
	public Exercise getExercise(String name, int reps){
		Exercise exercise = new Exercise(name, reps);
		for (Exercise e : exerciseHandler.getExercises()){
			if (e.equals(exercise)) {
				return e;
			}
		}
		return null;
	}
	
	public Exercise getExercise(String name) {
		Exercise exercise = new Exercise(name);
		for (Exercise e : exerciseHandler.getExercises()){
			if (e.equals(exercise)) {
				return e;
			}
		}
		return null;
	}
	
	public Exercise addExercise(String name, Duration duration){
		Exercise exercise = new Exercise(name, duration);
		for (Exercise e : exerciseHandler.getExercises()){
			if (e.equals(exercise)) {
				return e;
			}
		}
		return null;
	}
	
	public Exercise addExercise(String name, Equipment eq, int reps){
		Exercise exercise = new Exercise(name, eq, reps);
		for (Exercise e : exerciseHandler.getExercises()){
			if (e.equals(exercise)) {
				return e;
			}
		}
		return null;
	}
	
	public Exercise addExercise(String name, Equipment eq, Duration duration){
		Exercise exercise = new Exercise(name, eq, duration);
		for (Exercise e : exerciseHandler.getExercises()){
			if (e.equals(exercise)) {
				return e;
			}
		}
		return null;
	}
	
	public Exercise addExercise(Exercise e){
		exerciseHandler.addExercise(e);
		return e;
	}
	
	public Exercise removeExercise(Exercise e){
		exerciseHandler.removeExercise(e);
		return e;
	}
	
	public Exercise createExercise(String name, int reps){
		Exercise exercise = new Exercise(name, reps);
		
		if (exerciseHandler.getExercises().contains(exercise)){
			return null;
		}
		exerciseHandler.addExercise(exercise);
		return exercise;
	}
	
	public Exercise createExercise(String name, Duration duration){
		Exercise exercise = new Exercise(name, duration);
		
		if (exerciseHandler.getExercises().contains(exercise)){
			return null;
		}
		exerciseHandler.addExercise(exercise);
		return exercise;
	}
	
	public Exercise createExercise(String name, Equipment eq, int reps){
		Exercise exercise = new Exercise(name, eq, reps);
		
		if (exerciseHandler.getExercises().contains(exercise)){
			return null;
		}
		exerciseHandler.addExercise(exercise);
		return exercise;
	}
	
	public Exercise createExercise(String name, Equipment eq, Duration duration){
		Exercise exercise = new Exercise(name, eq, duration);
		
		if (exerciseHandler.getExercises().contains(exercise)){
			return null;
		}
		exerciseHandler.addExercise(exercise);
		return exercise;
	}
	
	public String toStringExercises(){
		String resultString = "";
		for (Exercise e : exerciseHandler.getExercises()){
			resultString += e.toString() + "\n";
		}
		return resultString;
	}
}
