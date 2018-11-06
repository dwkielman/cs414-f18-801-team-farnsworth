package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Equipment;

public class EquipmentTest {

	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	
	private String n1 = "name1";
	private String pp1 = "path1";
	private int q1 = 1;
	private String n2 = "name2";
	private String pp2 = "path2";
	private int q2 = 2;
	private String n3 = "name1";
	private String pp3 = "path2";
	private int q3 = 2;
	
	@BeforeEach
	void setUp() throws Exception {
		e1 = new Equipment(n1, pp1, q1);
		e2 = new Equipment(n2, pp2, q2);
		e3 = new Equipment(n3, pp3, q3);
	}

	@Test
	void testEquipmentEqual() {
		assertTrue(e1.equals(e3));
	}
	
	@Test
	void testEquipmentNotEqual() {
		assertFalse(e1.equals(e2));
	}
 
	@Test
	void testEquipmentUpdateQuantity() {
		assertTrue(e1.getQuantity() == 1);
		e1.updateQuantity(4);
		assertTrue(e1.getQuantity() == 5);
	}

}
