package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.WorkoutClass;
import teamFarnsworth.Handlers.EntityHandlers.WorkoutClassHandler;

public class WorkoutClassHandlerTest {
	
	private WorkoutClassHandler workoutClassHandler;
	private GymHours gh1;
	private GymHours gh2;
	private WorkoutClass wc1;
	private WorkoutClass wc2;

	@BeforeEach
	void setUp() throws Exception {
		workoutClassHandler = WorkoutClassHandler.getInstance();
		workoutClassHandler.getWorkoutClasses().clear();
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		wc1 = new WorkoutClass("Morning Aerobics", gh1);
		wc2 = new WorkoutClass("Afternoon Weights", gh2);
	}

	@Test
	void testGetAllWorkoutClasses() {
		workoutClassHandler.addWorkoutClass(wc1);
		workoutClassHandler.addWorkoutClass(wc2);
		assertTrue(workoutClassHandler.getWorkoutClasses().size() == 2);
	}
	
	@Test
	void testAddWorkoutClass() {
		workoutClassHandler.addWorkoutClass(wc1);
		workoutClassHandler.addWorkoutClass(wc2);
		assertTrue(workoutClassHandler.getWorkoutClasses().contains(wc1));
	}

	@Test
	void testRemoveWorkoutClass() {
		workoutClassHandler.addWorkoutClass(wc1);
		workoutClassHandler.addWorkoutClass(wc2);
		workoutClassHandler.removeWorkoutClass(wc1);
		assertFalse(workoutClassHandler.getWorkoutClasses().contains(wc1));
		assertTrue(workoutClassHandler.getWorkoutClasses().size() == 1);
	}
	
}
