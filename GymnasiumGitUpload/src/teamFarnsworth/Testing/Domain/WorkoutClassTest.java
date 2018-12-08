package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.GymHours;
import teamFarnsworth.Domain.Entities.WorkoutClass;

public class WorkoutClassTest {

	private GymHours gh1;
	private GymHours gh2;
	private WorkoutClass wc1;
	private WorkoutClass wc1Copy;
	private WorkoutClass wc2;
	private WorkoutClass wcNull;
	
	@BeforeEach
	void setUp() throws Exception {
		GymHours gh1 = new GymHours(LocalTime.of(8, 0), LocalTime.of(9, 0), DayOfWeek.MONDAY);
		GymHours gh2 = new GymHours(LocalTime.of(15, 0), LocalTime.of(16, 0), DayOfWeek.TUESDAY);
		wc1 = new WorkoutClass("Morning Aerobics", gh1);
		wc2 = new WorkoutClass("Afternoon Weights", gh2);
		wc1Copy = new WorkoutClass("Morning Aerobics", gh1);
	}

	@Test
	void testWorkoutClassEqual() {
		assertTrue(wc1.equals(wc1Copy));
	}

	@Test
	void testWorkoutClassNotEqual() {
		assertFalse(wc1.equals(wc2));
	}
	
	@Test
	void testNullWorkoutClass() {
		assertTrue(wcNull == null);
	}
	
}
