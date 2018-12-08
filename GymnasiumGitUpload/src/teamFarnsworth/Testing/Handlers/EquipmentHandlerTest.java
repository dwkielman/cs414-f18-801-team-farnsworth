package teamFarnsworth.Testing.Handlers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Handlers.EntityHandlers.EquipmentHandler;

public class EquipmentHandlerTest {

	private EquipmentHandler equipmentHandler;
	private Equipment e1;
	private Equipment e2;
	private Equipment e1Copy;
	
	@BeforeEach
	void setUp() throws Exception {
		equipmentHandler = EquipmentHandler.getInstance();
		equipmentHandler.getEquipment().clear();
		e1 = new Equipment("Bench Press");
		e2 = new Equipment("Rowing Machine");
		e1Copy = new Equipment("Bench Press");
	}

	@Test
	void testGetAllEquipment() {
		equipmentHandler.addEquipment(e1);
		assertTrue(equipmentHandler.getEquipment().size() == 1);
	}
	
	@Test
	void testGetEquipment() {
		equipmentHandler.addEquipment(e1);
		assertTrue(equipmentHandler.getEquipment(e1).equals(e1Copy));
	}
	
	@Test
	void testAddEquipment() {
		equipmentHandler.addEquipment(e1);
		assertTrue(equipmentHandler.getEquipment().contains(e1));
	}
	
	@Test
	void testRemoveEquipment() {
		equipmentHandler.addEquipment(e1);
		equipmentHandler.addEquipment(e2);
		equipmentHandler.removeEquipment(e1);
		assertFalse(equipmentHandler.getEquipment().contains(e1));
		assertTrue(equipmentHandler.getEquipment().size() == 1);
	}

}
