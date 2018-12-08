package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Handlers.EntityHandlers.ExerciseHandler;

public class ExerciseHandlerTest {

	private ExerciseHandler exerciseHandler;
	private Exercise e1;
	private Exercise e2;
	
	@BeforeEach
	void setUp() throws Exception {
		exerciseHandler = ExerciseHandler.getInstance();
		exerciseHandler.getExercises().clear();
		e1 = new Exercise("Running", new Duration(20));
		e2 = new Exercise("Walking", new Duration(60));
	}

	@Test
	void testGetAllExercises() {
		exerciseHandler.addExercise(e1);
		exerciseHandler.addExercise(e2);
		assertTrue(exerciseHandler.getExercises().size() == 2);
	}
	
	@Test
	void testAddExercise() {
		exerciseHandler.addExercise(e1);
		exerciseHandler.addExercise(e2);
		assertTrue(exerciseHandler.getExercises().contains(e1));
	}

	@Test
	void testRemoveExercise() {
		exerciseHandler.addExercise(e1);
		exerciseHandler.addExercise(e2);
		exerciseHandler.removeExercise(e1);
		assertFalse(exerciseHandler.getExercises().contains(e1));
		assertTrue(exerciseHandler.getExercises().size() == 1);
	}
}
