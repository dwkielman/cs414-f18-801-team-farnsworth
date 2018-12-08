package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;
import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Domain.Entities.Exercise;
import teamFarnsworth.Domain.Entities.Routine;
import teamFarnsworth.Handlers.EntityHandlers.RoutineHandler;

public class RoutineHandlerTest {
	
	private RoutineHandler routineHandler;
	private Exercise e1;
	private Exercise e2;
	private Routine r1;
	private Routine r2;

	@BeforeEach
	void setUp() throws Exception {
		routineHandler = RoutineHandler.getInstance();
		routineHandler.getRoutines().clear();
		e1 = new Exercise("Running", new Duration(20));
		e2 = new Exercise("Walking", new Duration(60));
		r1 = new Routine("Running Routine", e1);
		r2 = new Routine("Walking Routine", e2);
	}

	@Test
	void testGetAllRoutines() {
		routineHandler.addRoutine(r1);
		routineHandler.addRoutine(r2);
		assertTrue(routineHandler.getRoutines().size() == 2);
	}
	
	@Test
	void testAddRoutine() {
		routineHandler.addRoutine(r1);
		routineHandler.addRoutine(r2);
		assertTrue(routineHandler.getRoutines().contains(r1));
	}

	@Test
	void testRemoveRoutine() {
		routineHandler.addRoutine(r1);
		routineHandler.addRoutine(r2);
		routineHandler.removeRoutine(r1);
		assertFalse(routineHandler.getRoutines().contains(r1));
		assertTrue(routineHandler.getRoutines().size() == 1);
	}
}
