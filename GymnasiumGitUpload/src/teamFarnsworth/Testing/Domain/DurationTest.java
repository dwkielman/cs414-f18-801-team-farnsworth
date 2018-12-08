package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Duration;

public class DurationTest {

	private Duration d1;
	private Duration d2;
	private Duration d3;
	private int num1 = 1;
	private int num2 = 2;
	private int num3 = 1;
	private Duration nullDuration;
	
	
	@BeforeEach
	void setUp() throws Exception {
		d1 = new Duration(num1);
		d2 = new Duration(num2);
		d3 = new Duration(num3);
	}

	@Test
	void testDurationEquals() {
		assertTrue(d1.equals(d3));
	}
	
	@Test
	void testDurationNotEqual() {
		assertFalse(d1.equals(d2));
	}
	
	@Test
	void testDurationNull() {
		assertTrue(nullDuration == null);
	}

}
