package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Equipment;

public class EquipmentTest {

	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	private Equipment nullEquipment;
	
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
	void testEquipmentNull() {
		assertTrue(nullEquipment == null);
	}
 
	@Test
	void testEquipmentAddMoreQuantity() {
		assertTrue(e1.getQuantity() == 1);
		e1.addMoreQuantity(4);
		assertTrue(e1.getQuantity() == 5);
	}
	
	@Test
	void testEquipmentAddMoreQuantityWithNegativeNumber() {
		assertTrue(e1.getQuantity() == 1);
		e1.addMoreQuantity(-1);
		assertTrue(e1.getQuantity() == 1);
	}
	
	@Test
	void testEquipmentAddMoreQuantityWithZero() {
		assertTrue(e1.getQuantity() == 1);
		e1.addMoreQuantity(0);
		assertTrue(e1.getQuantity() == 1);
	}
	
	@Test
	void testEquipmentSetQuantity() {
		assertTrue(e1.getQuantity() == 1);
		e1.setQuantity(2);
		assertTrue(e1.getQuantity() == 2);
	}
	
	@Test
	void testEquipmentSetQuantityWithNegativeNumber() {
		assertTrue(e1.getQuantity() == 1);
		e1.setQuantity(-1);
		assertTrue(e1.getQuantity() == 1);
	}
	
	@Test
	void testEquipmentSetQuantityWithZero() {
		assertTrue(e1.getQuantity() == 1);
		e1.setQuantity(0);
		assertTrue(e1.getQuantity() == 1);
	}

}
