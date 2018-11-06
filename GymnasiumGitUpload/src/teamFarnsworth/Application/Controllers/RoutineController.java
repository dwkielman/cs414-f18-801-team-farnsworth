package teamFarnsworth.Application.Controllers;

import java.util.*;

import teamFarnsworth.Domain.Routine;
import teamFarnsworth.Domain.Exercise;
import teamFarnsworth.Handlers.RoutineHandler;

public class RoutineController {

private RoutineHandler routineHandler;
	
	public RoutineController(){
		routineHandler = RoutineHandler.getInstance();
	}
	
	public Set<Routine> getRoutines(){
		return routineHandler.getRoutines();
	}
	public Routine getRoutine(String name, Exercise e){
		Routine routine = new Routine(name, e);
		for (Routine r : routineHandler.getRoutines()){
			if (r.equals(routine)){
				return r;
			}
		}
		return null;
	}
	
	public Routine getRoutine(Routine r){
		for (Routine rou : routineHandler.getRoutines()){
			if (rou.equals(r)){
				return rou;
			}
		}
		return null;
	}
	
	public void removeRoutine(Routine r){
		routineHandler.removeRoutine(r);
	}
	
	public Routine createRoutine(String name, Exercise e){
		Routine r = new Routine(name, e);
		if (routineHandler.getRoutines().contains(r)){
			return null;
		}
		routineHandler.addRoutine(r);
		return r;
	}
	
	public void addExerciseToRoutine(String name, Exercise e){
		for (Routine r : routineHandler.getRoutines()){
			if (r.getName().equals(name)){
				if (!r.getExercises().contains(e)) {
					r.addExercise(e);;
				}
			}
		}
	}
	
	public void removeExerciseFromRoutine(String name, Exercise e){
		for (Routine r : routineHandler.getRoutines()){
			if (r.getName().equals(name)){
				r.removeExercise(e);
				if (r.getExercises().isEmpty()){
					routineHandler.removeRoutine(r);
				}
			}
		}
	}
	
	public String toStringRoutines(){
		String returnString = "";
		for (Routine r : routineHandler.getRoutines()){
			returnString += r.toString() + "\n";
		}
		return returnString;
	}
}
