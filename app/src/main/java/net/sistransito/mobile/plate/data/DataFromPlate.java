package net.sistransito.mobile.plate.data;

import android.content.Context;

import net.sistransito.mobile.timeandime.TimeAndIme;

public class DataFromPlate {

	TimeAndIme time;

	public DataFromPlate(Context context) {
		time = new TimeAndIme(context);
		setDate(time.getDate() + "\n" + time.getTime());

	}

	private String plate;
	private String state;
	private String city;
	private String country;
	private String brand;
	private String model;
	private String color;
	private String chassi;
	private String ranavam;
	private String species;
	private String type;
	private String category;
	private String licenceYear;
	private String licenceData;
	private String licenceStatus;
	private String ipva;
	private String insurance;
	private String status;
	private String infractions;
	private String restrictions;
	private String retentionIndication;
	private String engineNumber;
	private String ownerId;
	private String ownerName;
	private String sealNumber;
	private String duble;
	private String yearModel;
	private String yearManufacture;
	private String date;
	private String latitude;
	private String longitude;
	private String offSideRecord;
	private String theftRecord;
	private String infrationAmout;

	public String getInfrationAmout() {
		return infrationAmout;
	}

	public void setInfrationAmout(String infrationAmout) {
		this.infrationAmout = infrationAmout;
	}

	public String getOffSideRecord() {
		return offSideRecord;
	}

	public void setOffSideRecord(String offSideRecord) {
		this.offSideRecord = offSideRecord;
	}

	public String getTheftRecord() {
		return theftRecord;
	}

	public void setTheftRecord(String theftRecord) {
		this.theftRecord = theftRecord;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRanavam() {
		return ranavam;
	}

	public void setRanavam(String ranavam) {
		this.ranavam = ranavam;
	}

	public String getRetentionIndication() {
		return retentionIndication;
	}

	public void setRetentionIndication(String retentionIndication) {
		this.retentionIndication = retentionIndication;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSealNumber() {
		return sealNumber;
	}

	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}

	public String getDuble() {
		return duble;
	}

	public void setDuble(String duble) {
		this.duble = duble;
	}

	public String getYearModel() {
		return yearModel;
	}

	public void setYearModel(String yearModel) {
		this.yearModel = yearModel;
	}

	public String getYearManufacture() {
		return yearManufacture;
	}

	public void setYearManufacture(String yearManufacture) {
		this.yearManufacture = yearManufacture;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLicenceYear() {
		return licenceYear;
	}

	public void setLicenceYear(String licenseYear) {
		this.licenceYear = licenseYear;
	}

	public String getLicenceData() {
		return licenceData;
	}

	public void setLicenceData(String licenseDate) {
		this.licenceData = licenseDate;
	}

	public String getLicenceStatus() {
		return licenceStatus;
	}

	public void setLicenceStatus(String licenseStatus) {
		this.licenceStatus = licenseStatus;
	}

	public String getIpva() {
		return ipva;
	}

	public void setIpva(String ipva) {
		this.ipva = ipva;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfractions() {
		return infractions;
	}

	public void setInfractions(String infractions) {
		this.infractions = infractions;
	}

	public String getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
