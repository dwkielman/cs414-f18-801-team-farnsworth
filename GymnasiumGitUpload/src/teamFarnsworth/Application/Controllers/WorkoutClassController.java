package teamFarnsworth.Application.Controllers;

import java.util.*;

import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.WorkoutClass;
import teamFarnsworth.Domain.Users.Customer;
import teamFarnsworth.Handlers.EntityHandlers.WorkoutClassHandler;

public class WorkoutClassController {

	private WorkoutClassHandler workoutClassHandler;
	
	public WorkoutClassController(){
		workoutClassHandler = WorkoutClassHandler.getInstance();
	}
	
	public Set<WorkoutClass> getWorkoutClasses(){
		return workoutClassHandler.getWorkoutClasses();
	}
	
	public WorkoutClass getWorkoutClass(String name, GymHours gh){
		WorkoutClass wc = new WorkoutClass(name, gh);
		for (WorkoutClass workoutClass : workoutClassHandler.getWorkoutClasses()){
			if (workoutClass.equals(wc)){
				return workoutClass;
			}
		}
		return null;
	}
	
	public WorkoutClass removeWorkoutClass(WorkoutClass workoutClass){
		workoutClassHandler.removeWorkoutClass(workoutClass);
		return workoutClass;
	}

	public WorkoutClass createWorkoutClass(String name, GymHours gh){
		WorkoutClass wc = new WorkoutClass(name, gh);
		workoutClassHandler.addWorkoutClass(wc);
		return wc;
	}
	
	public Set<Customer> getCustomers(WorkoutClass workoutClass){
		return workoutClass.getAttendees();
	}
	
	public String toStringWorkoutClasses(){
		String returnString = "";
		
		for (WorkoutClass workoutClass : workoutClassHandler.getWorkoutClasses()) {
			returnString += (workoutClass.toString()) + "\n";
		}
		return returnString;
	}
	
}
