package teamFarnsworth.Handlers.EntityHandlers;

import java.util.*;

import teamFarnsworth.Domain.Entities.Equipment;

public class EquipmentHandler {
	
	private Set<Equipment> equipment = new HashSet<Equipment>();
	private static EquipmentHandler equipmentHandler;
	
	private EquipmentHandler() { }
	
	public static EquipmentHandler getInstance(){
		if(equipmentHandler == null){
			equipmentHandler = new EquipmentHandler();
		}
		return equipmentHandler;
    }
	
	public Set<Equipment> getEquipment(){
		return equipment;
	}
	public Equipment getEquipment(Equipment e){
		for (Equipment equipment : equipment){
			if (equipment.equals(e)){
				return equipment;
			}
		}
		return null;
	}
	public Equipment addEquipment(Equipment e){
		equipment.add(e);
		return e;
	}
	public Equipment removeEquipment(Equipment e){
		equipment.remove(e);
		return e;
	}
}
