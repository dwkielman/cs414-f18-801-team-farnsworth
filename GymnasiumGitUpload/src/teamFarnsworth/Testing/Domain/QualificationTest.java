package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Users.Qualification;

public class QualificationTest {

	private String name1;
	private String name1Copy;
	private String name2;
	private Qualification q1;
	private Qualification q2;
	private Qualification q1Copy;
	private Qualification qnull;
	
	@BeforeEach
	void setUp() throws Exception {
		name1 = "Works Nights";
		name1Copy = "Works Nights";
		name2 = "Teaching";
		q1 = new Qualification(name1);
		q1Copy = new Qualification(name1Copy);
		q2 = new Qualification(name2);
	}

	@Test
	void testQualificationEquals() {
		assertTrue(q1.equals(q1Copy));
	}
	
	@Test
	void testQualificationNotEqual() {
		assertFalse(q1.equals(q2));
	}

	@Test
	void testNullQualification() {
		assertTrue(qnull == null);
	}
}
