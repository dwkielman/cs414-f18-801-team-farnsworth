package teamFarnsworth.Application.Controllers;

import java.util.*;

import teamFarnsworth.Domain.Entities.Equipment;
import teamFarnsworth.Handlers.EntityHandlers.EquipmentHandler;

public class EquipmentController {

	private EquipmentHandler equipmentHandler;
	
	public EquipmentController() {
		equipmentHandler = EquipmentHandler.getInstance();
	}
	
	public Set<Equipment> getAllEquipment(){
		return equipmentHandler.getEquipment();
	}
	
	public Equipment getEquipment(String name){
		for (Equipment e : equipmentHandler.getEquipment()){
			if (e.getName().equals(name)){
				return e;
			}
		}
		return null;
	}
	
	public Equipment addEquipment(String name, String picturePath){
		Equipment e = new Equipment(name, picturePath);
		equipmentHandler.addEquipment(e);
		return e;
	}
	public Equipment addEquipment(Equipment e){
		equipmentHandler.addEquipment(e);
		return e;
	}
	
	public void removeEquipment(Equipment e){
		equipmentHandler.removeEquipment(e);
	}
	
	public String toStringEquipment(){
		String reurnString = "";
		for (Equipment e : equipmentHandler.getEquipment()){
			reurnString += e.toString() + "\n";
		}
		return reurnString;
	}
	
}
