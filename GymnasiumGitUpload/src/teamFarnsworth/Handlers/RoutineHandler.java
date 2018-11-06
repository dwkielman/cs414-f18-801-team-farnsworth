package teamFarnsworth.Handlers;

import java.util.*;

import teamFarnsworth.Domain.Routine;

public class RoutineHandler {

	private static RoutineHandler routineHandler;
	private Set<Routine> routines = new HashSet<Routine>(); 
	
	private RoutineHandler(){ }
	public static RoutineHandler getInstance(){
		if(routineHandler == null){
			routineHandler = new RoutineHandler();
		}
		return routineHandler;	
    }
	
	public Set<Routine> getRoutines(){
		return routines;
	}
	public Routine addRoutine(Routine routine){
		routines.add(routine);
		return routine;
	}
	public Routine removeRoutine(Routine routine){
		routines.remove(routine);
		return routine;
	}
	
	
}
