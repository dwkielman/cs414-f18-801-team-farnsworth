package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.Routine;

public class RoutineTest {

	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	private Exercise durationExercise;
	private Exercise durationExerciseCopy;
	private Exercise equipmentAndDurationExercise;
	private Exercise repExercise;
	private Exercise repAndEquipmentExercise;
	private Routine r1;
	private Routine r1copy;
	private Routine rNull;
	private Routine r2;
	
	@BeforeEach
	void setUp() throws Exception {
		e1 = new Equipment("Bench Press");
		e2 = new Equipment("Rowing Machine");
		e3 = new Equipment("Stairmaster");
		durationExercise = new Exercise("Running", new Duration(20));
		durationExerciseCopy = new Exercise("Running", new Duration(20));
		equipmentAndDurationExercise = new Exercise("Legs", e3, new Duration(10));
		repExercise = new Exercise("Lunges", 15);
		repAndEquipmentExercise = new Exercise("Weight Lifting", e1, 10);
		r1 = new Routine("Running Routine", durationExercise);
		r1copy = new Routine("Running Routine", durationExercise);
		r2 = new Routine("Legs Routine", equipmentAndDurationExercise);
	}

	@Test
	void testRoutineEquals() {
		assertTrue(r1.equals(r1copy));
	}
	
	@Test
	void testNotRoutineEqual() {
		assertFalse(r1.equals(r2));
	}
	
	@Test
	void testNullRoutine() {
		assertTrue(rNull == null);
	}
	
	@Test
	void testAddExercise() {
		r1.addExercise(repExercise);
		assertTrue(r1.getExercises().size() == 2);
		assertTrue(r1.getExercises().contains(repExercise));
	}
	
	@Test
	void testAddDuplicateExercise() {
		r1.addExercise(durationExerciseCopy);
		assertTrue(r1.getExercises().size() == 1);
	}
	
	@Test
	void testRemoveExercise() {
		r1.removeExercise(durationExercise);
		assertTrue(r1.getExercises().size() == 0);
		assertFalse(r1.getExercises().contains(durationExercise));
	}
	
	@Test
	void testRemoveExerciseThatIsNotInRoutine() {
		assertTrue(r1.getExercises().size() == 1);
		r1.removeExercise(repAndEquipmentExercise);
		assertTrue(r1.getExercises().size() == 1);
	}

}
