package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.EquipmentController;
import teamFarnsworth.Application.Controllers.ExerciseController;
import teamFarnsworth.Application.Controllers.RoutineController;
import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.Routine;

public class RoutineControllerTest {

	private EquipmentController equipmentController;
	private ExerciseController exerciseController;
	private RoutineController routineController;
	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	private Exercise durationExercise;
	private Exercise equipmentAndDurationExercise;
	private Exercise repExercise;
	private Exercise repAndEquipmentExercise;
	private Routine r1;
	private Routine r1copy;
	private Routine r2;
	private Routine r3;
	private Routine r4;
	
	@BeforeEach
	void setUp() throws Exception {
		
		equipmentController = new EquipmentController();
		equipmentController.getAllEquipment().clear();
		exerciseController = new ExerciseController();
		exerciseController.getExercises().clear();
		routineController = new RoutineController();
		routineController.getRoutines().clear();
		e1 = new Equipment("Bench Press");
		e2 = new Equipment("Rowing Machine");
		e3 = new Equipment("Stairmaster");
		equipmentController.addEquipment(e1);
		equipmentController.addEquipment(e2);
		equipmentController.addEquipment(e3);
		durationExercise = new Exercise("Running", new Duration(20));
		equipmentAndDurationExercise = new Exercise("Legs", equipmentController.getEquipment("Stairmaster"), new Duration(10));
		repExercise = new Exercise("Lunges", 15);
		repAndEquipmentExercise = new Exercise("Weight Lifting", equipmentController.getEquipment("Bench Press"), 10);
		r1 = new Routine("Running Routine", durationExercise);
		r1copy = new Routine("Running Routine", durationExercise);
		r2 = new Routine("Legs Routine", equipmentAndDurationExercise);
		r3 = new Routine("Lunges Routine", repExercise);
		r4 = new Routine("Weight Lifting Routine", repAndEquipmentExercise);
	}

	@Test
	void testGetAllRoutines() {
		routineController.createRoutine("Running Routine", durationExercise);
		routineController.createRoutine("Legs Routine", equipmentAndDurationExercise);
		assertTrue(routineController.getRoutines().size() == 2);
	}
	
	@Test
	void testGetRoutine() {
		routineController.createRoutine("Running Routine", durationExercise);
		routineController.createRoutine("Legs Routine", equipmentAndDurationExercise);
		assertTrue(routineController.getRoutine(r1).equals(r1copy));
	}
	
	@Test
	void testRemoveRoutine() {
		routineController.createRoutine("Running Routine", durationExercise);
		routineController.createRoutine("Legs Routine", equipmentAndDurationExercise);
		routineController.removeRoutine(r1);
		assertFalse(routineController.getRoutines().contains(r1));
		assertTrue(routineController.getRoutines().size() == 1);
	}
	
	@Test
	void testCreateRoutine() {
		routineController.createRoutine("Running Routine", durationExercise);
		assertTrue(routineController.getRoutine(r1).getName( )== "Running Routine");
	}
	
	@Test
	void testAddExerciseToRoutine() {
		routineController.createRoutine("Running Routine", durationExercise);
		routineController.addExerciseToRoutine("Running Routine", repExercise);
		assertTrue(routineController.getRoutine(r1).getExercises().size() == 2);
	}
	
	@Test
	void testRemoveExerciseFromRoutine() {
		routineController.createRoutine("Running Routine", durationExercise);
		routineController.addExerciseToRoutine("Running Routine", repExercise);
		routineController.addExerciseToRoutine("Running Routine", repAndEquipmentExercise);
		routineController.removeExerciseFromRoutine("Running Routine", repAndEquipmentExercise);
		assertTrue(routineController.getRoutine(r1).getExercises().size() == 2);
	}
	
}
