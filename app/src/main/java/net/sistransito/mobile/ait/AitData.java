package net.sistransito.mobile.ait;

import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.AitDatabaseHelper;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sistransito.R;

import java.io.Serializable;

public class AitData implements Serializable {

	public boolean isStoreFullData;
	public boolean isDataisNull=false;

	public boolean isDataisNull() {
		return isDataisNull;
	}

	public void setDataisNull(boolean isDataisNull) {
		this.isDataisNull = isDataisNull;
	}

	TimeAndIme time;

	public AitData(Context context) {
		time = new TimeAndIme(context);
		setAitDateTime(time.getDate() + time.getTime());
	}

	public static final String IDAuto = "ait_id";
	public static final long serialVersionUID = 14393745L;
	private String idAit;
	private String AitNumber;
	private String plate;
	private String stateVehicle;
	private String country;
	private String vehicleBrand;
	private String vehicleModel;
	private String chassi;
	private String vehycleColor;
	private String vehicleSpecies;
	private String vehicleCategory;
	private String approach;
	private String conductorName;
	private String foreignDriver;
	private String driverCountry;
	private String qualifiedDriver;
	private String cnhPpd;
	private String documentType;
	private String documentNumber;
	private String cnhState;
	private String infraction;
	private String framingCode;
	private String unfolding;
	private String article;
	private String cityCode;
	private String city;
	private String state;
	private String address;
	private String aitDate;
	private String aitTime;
	private String description;
	private String equipmentBrand;
	private String equipmentModel;
	private String serialNumber;
	private String measurementPerformed;
    private String regulatedValue;
	private String valueConsidered;
	private String tcaNumber;
	private String alcoholTestNumber;
	private String retreat;
	private String procedures;
	private String observation;
	private String shipperIdentification;
	private String cpfShipper;
	private String cnpShipper;
	private String carrierIdentification;
	private String cpfCarrier;
	private String cnpjCarrier;
	private String cancellationStatus;
	private String reasonForCancellation;
	private String syncStatus;
	private String AitLatitude;
	private String AitLongitude;
	private String aitDateTime;
	private String photo1, photo2, photo3, photo4;
	private String completedStatus;

	public String getApproach() {
		return approach;
	}

	public void setApproach(String approach) {
		this.approach = approach;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String getPhoto4() {
		return photo4;
	}

	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}

	public String getTcaNumber() {
		return tcaNumber;
	}

	public void setTcaNumber(String tcaNumber) {
		this.tcaNumber = tcaNumber;
	}

    public String getRegulatedValue() {
        return regulatedValue;
    }

    public void setRegulatedValue(String regulatedValue) {
        this.regulatedValue = regulatedValue;
    }

    public String getCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(String completedStatus) {
		this.completedStatus = completedStatus;
	}

	public String getAitLatitude() {
		return AitLatitude;
	}

	public void setAitLatitude(String aitLatitude) {
		this.AitLatitude = aitLatitude;
	}

	public String getAitLongitude() {
		return AitLongitude;
	}

	public void setAitLongitude(String aitLongitude) {
		this.AitLongitude = aitLongitude;
	}

	public String getAitDateTime() {
		return aitDateTime;
	}

	public void setAitDateTime(String aitDateTime) {
		this.aitDateTime = aitDateTime;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getCancellationStatus() {
		return cancellationStatus;
	}

	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}

	public String getReasonForCancellation() {
		return reasonForCancellation;
	}

	public void setReasonForCancellation(String reasonForCancellation) {
		this.reasonForCancellation = reasonForCancellation;
	}

	public String getStateVehicle() {
		return stateVehicle;
	}

	public void setStateVehicle(String stateVehicle) {
		this.stateVehicle = stateVehicle;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getCnhState() {
		return cnhState;
	}

	public void setCnhState(String cnhState) {
		this.cnhState = cnhState;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAitNumber() {
		return AitNumber;
	}

	public void setAitNumber(String aitNumber) {
		this.AitNumber = aitNumber;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getVehycleColor() {
		return vehycleColor;
	}

	public void setVehycleColor(String vehycleColor) {
		this.vehycleColor = vehycleColor;
	}

	public String getVehicleSpecies() {
		return vehicleSpecies;
	}

	public void setVehicleSpecies(String vehicleSpecies) {
		this.vehicleSpecies = vehicleSpecies;
	}

	public String getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public String getConductorName() {
		return conductorName;
	}

	public void setConductorName(String conductorName) {
		this.conductorName = conductorName;
	}

	public String getForeignDriver() {
		return foreignDriver;
	}

	public void setForeignDriver(String foreignDriver) {
		this.foreignDriver = foreignDriver;
	}

	public String getDriverCountry() {
		return driverCountry;
	}

	public void setDriverCountry(String driverCountry) {
		this.driverCountry = driverCountry;
	}

	public String getQualifiedDriver() {
		return qualifiedDriver;
	}

	public void setQualifiedDriver(String qualifiedDriver) {
		this.qualifiedDriver = qualifiedDriver;
	}

	public String getCnhPpd() {
		return cnhPpd;
	}

	public void setCnhPpd(String cnhPpd) {
		this.cnhPpd = cnhPpd;
	}

	public String getInfraction() {
		return infraction;
	}

	public void setInfraction(String infraction) {
		this.infraction = infraction;
	}

	public String getFramingCode() {
		return framingCode;
	}

	public void setFramingCode(String framingCode) {
		this.framingCode = framingCode;
	}

	public String getUnfolding() {
		return unfolding;
	}

	public void setUnfolding(String unfolding) {
		this.unfolding = unfolding;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAitDate() {
		return aitDate;
	}

	public void setAitDate(String aitDate) {
		this.aitDate = aitDate;
	}

	public String getAitTime() {
		return aitTime;
	}

	public void setAitTime(String aitTime) {
		this.aitTime = aitTime;
	}

	public String getEquipmentBrand() {
		return equipmentBrand;
	}

	public void setEquipmentBrand(String equipmentBrand) {
		this.equipmentBrand = equipmentBrand;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMeasurementPerformed() {
		return measurementPerformed;
	}

	public void setMeasurementPerformed(String measurementPerformed) {
		this.measurementPerformed = measurementPerformed;
	}

	public String getValueConsidered() {
		return valueConsidered;
	}

	public void setValueConsidered(String valueConsidered) {
		this.valueConsidered = valueConsidered;
	}

	public String getAlcoholTestNumber() {
		return alcoholTestNumber;
	}

	public void setAlcoholTestNumber(String alcoholTestNumber) {
		this.alcoholTestNumber = alcoholTestNumber;
	}

	public String getRetreat() {
		return retreat;
	}

	public String getProcedures() {
		return procedures;
	}

	public void setRetreat(String retreat) {
		this.retreat = retreat;
	}

	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getShipperIdentification() {
		return shipperIdentification;
	}

	public void setShipperIdentification(String shipperIdentification) {
		this.shipperIdentification = shipperIdentification;
	}

	public String getCpfShipper() {
		return cpfShipper;
	}

	public void setCpfShipper(String cpfShipper) {
		this.cpfShipper = cpfShipper;
	}

	public String getCnpShipper() {
		return cnpShipper;
	}

	public void setCnpShipper(String cnpjShipper) {
		this.cnpShipper = cnpjShipper;
	}

	public String getCarrierIdentification() {
		return carrierIdentification;
	}

	public void setCarrierIdentification(
			String carrierIdentification) {
		this.carrierIdentification = carrierIdentification;
	}

	public String getCpfCarrier() {
		return cpfCarrier;
	}

	public void setCpfCarrier(String cpfCarrier) {
		this.cpfCarrier = cpfCarrier;
	}

	public String getCnpjCarrier() {
		return cnpjCarrier;
	}

	public void setCnpjCarrier(String cnpjCarrier) {
		this.cnpjCarrier = cnpjCarrier;
	}

	public static String getIDAuto() {
		return IDAuto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AitData() {
		super();
		initializedAllData();
	}

	public boolean isStoreFullData() {
		return isStoreFullData;
	}

	public void setStoreFullData(boolean isStoreFullData) {
		this.isStoreFullData = isStoreFullData;
	}

	public String getIdAit() {
		return idAit;
	}

	public void setIdAit(String idAit) {
		this.idAit = idAit;
	}

	public void initializedAllData() {
		address = aitDate = aitTime = idAit = plate = vehicleModel = chassi = vehycleColor = vehicleSpecies = vehicleCategory = approach = conductorName =
				foreignDriver = driverCountry = qualifiedDriver = documentNumber = cnhPpd = cnhState = documentType = infraction =
						framingCode = unfolding = article = cityCode =
				city = state = address = aitDate = aitTime = description = equipmentBrand = equipmentModel = serialNumber = measurementPerformed
                        = regulatedValue = valueConsidered = tcaNumber = alcoholTestNumber = retreat = procedures =
				observation = shipperIdentification = cpfShipper = cnpShipper =
						carrierIdentification = cpfCarrier = cnpjCarrier = cancellationStatus = reasonForCancellation
								= syncStatus = AitLatitude = AitLongitude = aitDateTime = completedStatus = "";
	}

	public void setAitDataFromCursor(Cursor myCursor) {

		this.setAitNumber(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_NUMBER)));
		this.setPlate(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.PLATE)));
		this.setStateVehicle(myCursor.getString(myCursor.getColumnIndex(AitDatabaseHelper.VEHICLE_STATE)));
		this.setChassi(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CHASSI)));
		this.setCountry(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.COUNTRY)));
        this.setVehicleBrand(myCursor.getString(myCursor
                .getColumnIndex(AitDatabaseHelper.VEHICLE_BRAND)));
		this.setVehicleModel(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.VEHICLE_MODEL)));
		this.setVehycleColor(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.VEHICLE_COLOR)));
		this.setVehicleSpecies(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.SPECIES)));
		this.setVehicleCategory(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CATEGORY)));
		this.setApproach(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.APPROACH)));
		this.setConductorName(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DRIVER_NAME)));
		this.setForeignDriver(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DRIVER_FOREIGN)));
		this.setDriverCountry(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DRIVER_COUNTRY)));
		this.setQualifiedDriver(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.ENABLED_DRIVER)));
		this.setCnhPpd(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DRIVER_LICENSE)));
		this.setCnhState(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DRIVER_LICENSE_STATE)));
		this.setDocumentType(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DOCUMENT_TYPE)));
		this.setDocumentNumber(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.DOCUMENT_NUMBER)));
		this.setInfraction(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.INFRATION)));
		this.setFramingCode(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.FLAMING_CODE)));
		this.setUnfolding(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.UNFOLDING)));
		this.setArticle(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.ARTICLE)));
		this.setCityCode(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CITY_CODE)));
		this.setCity(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CITY)));
		this.setState(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.STATE)));
		this.setAddress(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.ADDRESS)));
		this.setAitDate(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_DATE)));
		this.setAitTime(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_TIME)));
		this.setDescription(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.EQUIPMENT_DESCRIPTION)));
		this.setEquipmentBrand(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.EQUIPMENT_BRAND)));
		this.setEquipmentModel(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.EQUIPMENT_MODEL)));
		this.setSerialNumber(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.EQUIPMENT_SERIAL)));
		this.setMeasurementPerformed(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.MEASUREMENT_PERFORMED)));
        this.setRegulatedValue(myCursor.getString(myCursor
                .getColumnIndex(AitDatabaseHelper.REGULATED_VALUE)));
		this.setValueConsidered(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.VALUE_CONSIDERED)));
		this.setTcaNumber(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.TCA_NUMBER)));
		this.setAlcoholTestNumber(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.ALCOHOL_TEST_NUMBER)));
		this.setRetreat(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.RETREAT)));
		this.setProcedures(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.PROCEDURES)));
		this.setObservation(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.OBSERVATION)));
		this.setShipperIdentification(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.SHIPPER_IDENTIFICATION)));
		this.setCpfShipper(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CPF_SHIPPER)));
		this.setCnpShipper(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CNPJ_SHIPPER)));
		this.setCarrierIdentification(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CARRIER_IDENTIFICATION)));
		this.setCpfCarrier(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CPF_CARRIER)));
		this.setCnpjCarrier(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CNPJ_CARRIER)));
		this.setCancellationStatus(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.CANCELL_STATUS)));
		this.setReasonForCancellation(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.REASON_FOR_CANCELL)));
		this.setSyncStatus(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.SYNC_STATUS)));
		this.setAitDateTime(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_DATE_TIME)));
		this.setPhoto1(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_PHOTO1)));
		this.setPhoto2(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_PHOTO2)));
		this.setPhoto3(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_PHOTO3)));
		this.setPhoto4(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.AIT_PHOTO4)));
		this.setCompletedStatus(myCursor.getString(myCursor
				.getColumnIndex(AitDatabaseHelper.COMPLETED_STATUS)));

		this.setStoreFullData(true);

	}

	public String getAutoListViewData(Context context) {
		String dadosAutoConferir =
				context.getResources().getString(R.string.placa)
						+ getNewLine()
						+ plate
						+ getTwoLines()
				+ context.getResources().getString(R.string.numero_do_chassi)
				+ getNewLine()
				+ chassi
				+ getTwoLines()
		        + context.getResources().getString(R.string.rrd_uf)
				+ getNewLine()
				+ state
				+ getTwoLines()
				+ context.getResources().getString(R.string.auto_pais)
				+ getNewLine()
				+ country
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.auto_de_cor_do_veiculo)
				+ getNewLine()
				+ vehycleColor
				+ getTwoLines()
				+ context.getResources().getString(R.string.especie)
				+ getNewLine()
				+ vehicleSpecies
				+ getTwoLines()
				+ context.getResources().getString(R.string.categoria)
				+ getNewLine()
				+ vehicleCategory
				+ getTwoLines()
				+ context.getResources().getString(R.string.nome_condutor)
				+ getNewLine()
				+ conductorName
				+ getTwoLines()
				+ context.getResources().getString(R.string.se_condutor_estrangeiro)
				+ getNewLine()
				+ foreignDriver
				+ getTwoLines()
				+ context.getResources().getString(R.string.pais_do_condutor)
				+ getNewLine()
				+ driverCountry
				+ getTwoLines()
				+ context.getResources().getString(R.string.se_condutor_habilitado)
				+ getNewLine()
				+ qualifiedDriver
				+ getTwoLines()
				+ context.getResources().getString(R.string.CNH_PPD)
				+ getNewLine()
				+ cnhPpd
				+ getTwoLines()
				+ context.getResources().getString(R.string.uf_cnh)
				+ getNewLine()
				+ cnhState
				+ getTwoLines()

				+ context.getResources().getString(
						R.string.tipo_documento_apresentado)
				+ getNewLine()
				+ documentType
				+ getNewLine()
				+ documentNumber
				+ getTwoLines()
				+ context.getResources().getString(R.string.infracao)
				+ getNewLine()
				+ infraction
				+ getTwoLines()
				+ context.getResources().getString(R.string.enquadra)
				+ getNewLine()
				+ framingCode
				+ getTwoLines()
				+ context.getResources().getString(R.string.desdob)
				+ getNewLine()
				+ unfolding
				+ getTwoLines()
				+ context.getResources().getString(R.string.amparo_legal)
				+ getNewLine()
				+ article
				+ getTwoLines()
				+ context.getResources().getString(R.string.auto_de_municipio)
				+ getNewLine()
				+ city
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.auto_de_codigo_do_municipio)
				+ getNewLine()
				+ cityCode
				+ getTwoLines()
				+ context.getResources().getString(R.string.auto_uf)
				+ getNewLine()
				+ state
				+ getTwoLines()
				+ context.getResources().getString(R.string.local)
				+ getNewLine()
				+ address
				+ getTwoLines()
				+ context.getResources().getString(R.string.data)
				+ getNewLine()
				+ aitDate
				+ getTwoLines()
				+ context.getResources().getString(R.string.hora)
				+ getNewLine()
				+ aitTime
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.descricao_do_equipamento)
				+ getNewLine()
				+ description
				+ getTwoLines()
				+ context.getResources().getString(R.string.auto_de_marca)
				+ getNewLine()
				+ equipmentBrand
				+ getTwoLines()

				+ context.getResources().getString(R.string.auto_de_modelo)
				+ getNewLine()
				+ equipmentModel
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.auto_de_numero_de_serie)
				+ getNewLine()
				+ serialNumber
				+ getTwoLines()
				+ context.getResources().getString(R.string.medicao_realizada)
				+ getNewLine()
				+ measurementPerformed
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.auto_de_valor_considerado)
				+ getNewLine()
				+ valueConsidered
						+ getTwoLines()
						+ context.getResources().getString(
						R.string.auto_numero_tca)
						+ getNewLine()
						+ tcaNumber
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.auto_numero_da_amostra)
				+ getNewLine()
				+ alcoholTestNumber
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.recolhimento_de_documentos)
				+ getNewLine()
				+ retreat
				+ getTwoLines()
				+ context.getResources().getString(R.string.procedimentos)
				+ getNewLine()
				+ procedures
				+ getTwoLines()
				+ context.getResources().getString(R.string.observacao)
				+ getNewLine()
				+ observation
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.identificacao_do_embarcador)
				+ getNewLine()
				+ shipperIdentification
				+ getTwoLines()
				+ context.getResources().getString(R.string.CNPJ_CPF)
				+ getNewLine()
				+ cpfShipper
				+ getTwoLines()
				+ context.getResources().getString(
						R.string.identificacao_transportador)
				+ getTwoLines()
				+ carrierIdentification
				+ getTwoLines()
				+ context.getResources().getString(R.string.CNPJ_CPF)
				+ getTwoLines()
				+ cpfCarrier
				+ getTwoLines()
				+ context.getResources().getString(R.string.titulo_cancelar)
				+ getTwoLines()
				+ reasonForCancellation +
				getNewLine();
		return dadosAutoConferir;
	}

	/*public String getAutoViewData(Context context) {

		String dadosAit = context.getResources().getString(R.string.numero_auto) + getNewLine() + numeroAuto + getTwoLines()
				+ context.getResources().getString(R.string.placa) + getNewLine() + placa + getTwoLines()
				//+ context.getResources().getString(R.string.auto_de_UF) + getNewLine() + uf_placa + getTwoLines()
				+ context.getResources().getString(R.string.model) + getNewLine() + modeloVeiculo + getTwoLines()
				+ getAutoListViewData(context);
		return dadosAit;
	}

    public String getAutoPreViewData(Context context) {

        String dadosAit = context.getResources().getString(R.string.numero_auto) + getNewLine() + numeroAuto + getTwoLines()
                + context.getResources().getString(R.string.placa) + getNewLine() + placa + getTwoLines()
                //+ context.getResources().getString(R.string.auto_de_UF) + getNewLine() + uf_placa + getTwoLines()
                + context.getResources().getString(R.string.model) + getNewLine() + modeloVeiculo + getTwoLines()
                + getAutoListViewData(context);
        return dadosAit;
    }*/

	public String getNewLine() {
		return AppConstants.NEW_LINE;
	}

	public String getTwoLines() {
		return AppConstants.TWO_LINES;
	}
}
