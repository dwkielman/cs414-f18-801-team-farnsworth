package teamFarnsworth.Domain.Entities;

import java.util.*;

public class Equipment {

	private String name;
	private String picturePath;
	private int quantity;
	
	public Equipment() {}
	
	public Equipment(String name, String picturePath, int quantity) {
		this.name = name;
		this.picturePath = picturePath;
		this.quantity = quantity;
	}
	
	public Equipment(String name, String picturePath) {
		this.name = name;
		this.picturePath = picturePath;
		this.quantity = 1;
	}

	public Equipment(String name) {
		this.name = name;
		this.picturePath = "Picture Not Available";
		this.quantity = 1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity > 0) {
			this.quantity = quantity;
		}
	}
	
	public void addMoreQuantity(int quantity) {
		if (quantity > 0) {
			this.quantity += quantity;
		}
	}

	@Override
	public String toString() {
		return "Equipment Name: " + name + ", Qty: " + quantity + "\n" + picturePath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Equipment) {
			Equipment e = (Equipment) obj;
			return (e.getName().equals(this.getName()));
		}
		return false;
	}
	
	
}
