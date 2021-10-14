package net.sistransito.mobile.equipments;


public class AitEquipmentEntry {
	private String equipmentDescription, equipmentBrand, equipmentModel, equipmentValidate, equipmentSerial;

	public AitEquipmentEntry(String description, String brand, String model,
							 String validate, String serial) {
		super();
		equipmentDescription = description;
		equipmentBrand = brand;
		equipmentModel = model;
		equipmentValidate = validate;
		this.equipmentSerial = serial;
	}

	public String getEquipmentSerial() {
		return equipmentSerial;
	}

	public String getEquipmentDescription() {
		return equipmentDescription;
	}

	public String getEquipmentBrand() {
		return equipmentBrand;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public String getEquipmentValidate() {
		return equipmentValidate;
	}

}
