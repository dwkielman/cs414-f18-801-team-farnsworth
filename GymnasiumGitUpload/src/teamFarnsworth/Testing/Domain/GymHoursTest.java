package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.GymHours;

public class GymHoursTest {

	private GymHours gh1;
	private GymHours gh2;
	private GymHours gh3;
	
	private LocalTime st1 = LocalTime.of(1, 0);
	private LocalTime et1 = LocalTime.of(2, 0);
	private DayOfWeek d1 = DayOfWeek.MONDAY;
	private LocalTime st2 = LocalTime.of(10, 0);
	private LocalTime et2 = LocalTime.of(11, 0);
	private DayOfWeek d2 = DayOfWeek.TUESDAY;
	private LocalTime st3 = LocalTime.of(1, 0);
	private LocalTime et3 = LocalTime.of(2, 0);
	private DayOfWeek d3 = DayOfWeek.MONDAY;
	
	@BeforeEach
	void setUp() throws Exception {
		gh1 = new GymHours(st1, et1, d1);
		gh2 = new GymHours(st2, et2, d2);
		gh3 = new GymHours(st3, et3, d3);
	}

	@Test
	void testGymHoursEqual() {
		assertTrue(gh1.equals(gh3));
	}
	
	@Test
	void testGymHoursNotEqual() {
		assertFalse(gh1.equals(gh2));
	}
	
	@Test
	void testSetStartTimeToAfterEndTime() {
		gh1.setStartTime(LocalTime.of(3, 0));
		assertTrue(gh1.getStartTime().equals(st1));
	}
	
	@Test
	void testSetEndTimeToBeforeStartTime() {
		gh2.setEndTime(LocalTime.of(6, 30));
		assertTrue(gh2.getEndTime().equals(et2));
	}

}
