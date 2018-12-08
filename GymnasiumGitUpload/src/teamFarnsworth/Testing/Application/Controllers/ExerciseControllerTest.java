package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.EquipmentController;
import teamFarnsworth.Application.Controllers.ExerciseController;
import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;

public class ExerciseControllerTest {

	private EquipmentController equipmentController;
	private ExerciseController exerciseController;
	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	private Exercise durationExercise;
	private Exercise equipmentAndDurationExercise;
	private Exercise repExercise;
	private Exercise repAndEquipmentExercise;
	private String nameRunning;
	private String nameLunges;
	private String equipName;
	private String durationName;
	private Duration runningDuration;
	private Duration legsDuration;
	private int lungesRepNumber;
	private int weightReps;
	
	@BeforeEach
	void setUp() throws Exception {
		equipmentController = new EquipmentController();
		equipmentController.getAllEquipment().clear();
		exerciseController = new ExerciseController();
		exerciseController.getExercises().clear();
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
		nameRunning = "Running";
		runningDuration = new Duration(20);
		legsDuration = new Duration(10);
		lungesRepNumber = 15;
		weightReps = 10;
		nameLunges = "Lunges";
		equipName = "Weight Lifting";
		durationName = "Legs";
	}

	@Test
	void testGetAllExercises() {
		exerciseController.addExercise(durationExercise);
		exerciseController.addExercise(repExercise);
		assertTrue(exerciseController.getExercises().size() == 2);
	}
	
	@Test
	void testGetExerciseByNameReps() {
		exerciseController.addExercise(repExercise);
		assertTrue(exerciseController.getExercise(nameLunges, lungesRepNumber).equals(repExercise));
	}

	@Test
	void testGetExerciseByName() {
		exerciseController.addExercise(durationExercise);
		assertTrue(exerciseController.getExercise(nameRunning).equals(durationExercise));
	}

	@Test
	void testAddExerciseExercise() {
		exerciseController.addExercise(durationExercise);
		exerciseController.addExercise(repExercise);
		assertTrue(exerciseController.getExercises().contains(durationExercise));
		assertFalse(exerciseController.getExercises().contains(equipmentAndDurationExercise));
	}
	
	@Test
	void testRemoveExercise() {
		exerciseController.addExercise(durationExercise);
		exerciseController.addExercise(repExercise);
		exerciseController.removeExercise(durationExercise);
		assertFalse(exerciseController.getExercises().contains(durationExercise));
		assertTrue(exerciseController.getExercises().size() == 1);
	}

	@Test
	void testCreateExerciseNameReps() {
		exerciseController.createExercise(nameLunges, lungesRepNumber);
		assertTrue(exerciseController.getExercise(nameLunges).equals(repExercise));
	}

	@Test
	void testCreateExerciseNameDuration() {
		exerciseController.createExercise(nameRunning, runningDuration);
		assertTrue(exerciseController.getExercise(nameRunning).equals(durationExercise));
	}

	@Test
	void testCreateExerciseNameEquipmentReps() {
		exerciseController.createExercise(equipName, e1, weightReps);
		assertTrue(exerciseController.getExercise(equipName).equals(repAndEquipmentExercise));
	}
	
	@Test
	void testCreateExerciseNameEquipmentDuration() {
		exerciseController.createExercise(durationName, e3, legsDuration);
		assertTrue(exerciseController.getExercise(durationName).equals(equipmentAndDurationExercise));
	}
}
