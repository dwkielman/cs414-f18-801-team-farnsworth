package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.WorkoutClassController;
import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.WorkoutClass;

public class WorkoutClassControllerTest {

	private WorkoutClassController workoutClassController;
	private GymHours gh1;
	private GymHours gh2;
	private GymHours gh3;
	private WorkoutClass wc1;
	private WorkoutClass wc2;
	private WorkoutClass wc3;
	
	@BeforeEach
	void setUp() throws Exception {
		
		workoutClassController = new WorkoutClassController();
		workoutClassController.getWorkoutClasses().clear();
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		GymHours gh3 = new GymHours(LocalTime.of(18, 0), LocalTime.of(19, 0), DayOfWeek.WEDNESDAY);
		wc1 = new WorkoutClass("Morning Aerobics", gh1);
		wc2 = new WorkoutClass("Afternoon Weights", gh2);
		wc3 = new WorkoutClass("Evening Yoga", gh3);
	}

	@Test
	void testGetAllWorkoutClasses() {
		workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		workoutClassController.createWorkoutClass("Evening Yoga", gh3);
		assertTrue(workoutClassController.getWorkoutClasses().size() == 3);
	}
	@Test
	void testGetWorkoutClass() {
		workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		assertTrue(workoutClassController.getWorkoutClass("Morning Aerobics", gh1).equals(wc1));
	}
	
	@Test
	void testRemoveWorkoutClass() {
		workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		workoutClassController.removeWorkoutClass(wc1);
		assertTrue(workoutClassController.getWorkoutClasses().size() == 1);
		assertFalse(workoutClassController.getWorkoutClasses().contains(wc1));
	}
	
	@Test
	void testCreateWorkoutClass() {
		workoutClassController.createWorkoutClass("Morning Aerobics", gh1);
		workoutClassController.createWorkoutClass("Afternoon Weights", gh2);
		assertTrue(workoutClassController.getWorkoutClasses().contains(wc1));
		assertTrue(workoutClassController.getWorkoutClasses().contains(wc2));
		assertFalse(workoutClassController.getWorkoutClasses().contains(wc3));
	}
}
