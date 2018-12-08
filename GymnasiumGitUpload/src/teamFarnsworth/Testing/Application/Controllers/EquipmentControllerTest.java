package teamFarnsworth.Testing.Application.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Application.Controllers.EquipmentController;
import teamFarnsworth.Domain.Entities.Equipment;

public class EquipmentControllerTest {

	private EquipmentController equipmentController;
	private Equipment e1;
	private Equipment e2;
	private Equipment e3;
	private String name;
	private String pPath;
	
	@BeforeEach
	void setUp() throws Exception {
		equipmentController = new EquipmentController();
		equipmentController.getAllEquipment().clear();
		e1 = new Equipment("Bench Press");
		e2 = new Equipment("Rowing Machine");
		e3 = new Equipment("Stairmaster");
		name = "Stairmaster";
		pPath = "stairs.gif";
	}

	@Test
	void testGetAllEquipment() {
		equipmentController.addEquipment(e1);
		equipmentController.addEquipment(e2);
		assertTrue(equipmentController.getAllEquipment().size() == 2);
	}

	@Test
	void testGetEquipment() {
		equipmentController.addEquipment(e1);
		assertTrue(equipmentController.getEquipment(e1.getName()).equals(e1));
	}
	
	@Test
	void testAddEquipmentByNameAndPath() {
		e3.setPicturePath(pPath);
		equipmentController.addEquipment(name, pPath);
		assertTrue(equipmentController.getEquipment(name).equals(e3));
	}
	
	@Test
	void testAddEquipemntByEquipment() {
		equipmentController.addEquipment(e1);
		equipmentController.addEquipment(e2);
		equipmentController.addEquipment(e3);
		assertTrue(equipmentController.getAllEquipment().contains(e3));
	}
	
	@Test
	void testRemoveEquipment() {
		equipmentController.addEquipment(e1);
		equipmentController.addEquipment(e2);
		equipmentController.removeEquipment(e1);
		assertTrue(equipmentController.getAllEquipment().size() == 1);
		assertFalse(equipmentController.getAllEquipment().contains(e1));
	}
	
}
