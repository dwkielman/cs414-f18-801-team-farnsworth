package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.WorkoutSet;

public class ExerciseTest {

	private Exercise ex1;
	private Exercise ex2;
	private Exercise ex3;
	private Exercise nullExercise;
	
	private String n1 = "ex1";
	private String n2 = "ex2";
	private int num1 = 5;
	private Equipment e1;
	private Duration d1;
	private WorkoutSet ws1;
	
	@BeforeEach
	void setUp() throws Exception {
		e1 = new Equipment("eq", "path", 1);
		d1 = new Duration(1);
		ws1 = new WorkoutSet(1);
		
		ex1 = new Exercise(n1, d1);
		ex2 = new Exercise(n2, e1, num1);
		ex3 = new Exercise(n1, d1);
	}

	@Test
	void testExerciseEqual() {
		assertTrue(ex1.equals(ex3));
	}
	
	@Test
	void testExerciseNotEqual() {
		assertFalse(ex1.equals(ex2));
	}
	
	@Test
	void testExerciseNull() {
		assertTrue(nullExercise == null);
	}
	
 
}
