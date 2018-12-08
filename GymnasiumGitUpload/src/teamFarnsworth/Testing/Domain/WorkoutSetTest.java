package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.WorkoutSet;

public class WorkoutSetTest {

	private WorkoutSet ws1;
	private WorkoutSet ws1Copy;
	private WorkoutSet ws2;
	private WorkoutSet nullWs;
	
	
	@BeforeEach
	void setUp() throws Exception {
		ws1 = new WorkoutSet(10);
		ws1Copy = new WorkoutSet(10);
		ws2 = new WorkoutSet(25);
	}

	@Test
	void testWorkoutSetEqual() {
		assertTrue(ws1.equals(ws1Copy));
	}
	
	@Test
	void testWorkoutSetNotEqual() {
		assertFalse(ws1.equals(ws2));
	}
	
	@Test
	void testNullWorkoutSet() {
		assertTrue(nullWs == null);
	}
	
	@Test
	void testWorkoutSetEqualWithMultipleReps() {
		ws1.addRep(20);
		ws1Copy.addRep(20);
		assertTrue(ws1.equals(ws1Copy));
	}
	
	@Test
	void testAddRepToWorkoutSet() {
		ws1.addRep(20);
		assertTrue(ws1.getWorkoutSet().size() == 2);
	}
	
	@Test
	void testRemoveRepToWorkoutSet() {
		ws1.addRep(20);
		assertTrue(ws1.getWorkoutSet().size() == 2);
		ws1.removeRep(0);
		assertTrue(ws1.getWorkoutSet().size() == 1);
		assertFalse(ws1.getWorkoutSet().contains(10));
	}
	
	@Test
	void testAddZeroToWorkoutSetFailure() {
		ws1.addRep(0);
		assertTrue(ws1.getWorkoutSet().size() == 1);
	}
	
	@Test
	void testAddNegativeNumberToWorkoutSetFailure() {
		ws1.addRep(-7);
		assertTrue(ws1.getWorkoutSet().size() == 1);
	}

}
