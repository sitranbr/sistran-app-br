package net.sistransito.mobile.tca;

import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.TcaDatabaseHelper;
import net.sistransito.R;

import java.io.Serializable;

public class TcaData implements Serializable {

	// NOME, CNH_PPD, CPF, ENDERECO, BAIRRO, MUNICIPIO,MUNICIPIO_UF, PLACA,
	// PLACA_UF, MARCA_MODELO, LOCAL_DA_OCORRENCIA,DATA, HORA, N_AUTO,
	// CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO,CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA,CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA,
	// O_CONDUTOR_APRESENTA_SINAIS_DE,EM_SUA_ATITUDE_OCORRE, SABE_ONDE_ESTA,
	// SABE_A_DATA_E_A_HORA,SABE_SEU_ENDERECO,
	// LEMBRA_DOS_ATOS_COMETIDOS,EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE,
	// CONCLUSAO;

	private static final String TCA_ID = "tca_id";
	private static final long serialVersionUID = 14393860L;

	public static String getTcaId() {
		return TCA_ID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String driverName, cnhPpd, cpf, address, district, city,
			state, plate, plateState, brandModel,
			driverInvolvedInCarAccident,
			driverClaimsToHaveDrunkAlcohol,
			driverClaimsToHaveUsedToxicSubstance,
			driverShowsSignsOf, inHisAttitudeOccurs,
			knowsWhereItIs, knowsTheDateAndTime, knowsItsAddress,
			rememberTheActsCommitted,
			inRelationToTheirMotorAndVerbalAbilityOccurs, conclusion,
			aitNumber, tcaNumber, timeThatDrankAlcohol, dateThatDrankAlcohol,
			timeIngestedSubstance, dateIngestedSubstance, tcaType;

	public String getTcaType() {
		return tcaType;
	}

	public void setTcaType(String tipo) {
		this.tcaType = tipo;
	}

	public String getTimeThatDrankAlcohol() {
		return timeThatDrankAlcohol;
	}

	public String getDateThatDrankAlcohol() {
		return dateThatDrankAlcohol;
	}

	public String getTimeIngestedSubstance() {
		return timeIngestedSubstance;
	}

	public String getDateIngestedSubstance() {
		return dateIngestedSubstance;
	}

	public void setTimeThatDrankAlcohol(String timeThatDrankAlcohol) {
		this.timeThatDrankAlcohol = timeThatDrankAlcohol;
	}

	public void setDateThatDrankAlcohol(String dateThatDrankAlcohol) {
		this.dateThatDrankAlcohol = dateThatDrankAlcohol;
	}

	public void setTimeIngestedSubstance(String timeIngestedSubstance) {
		this.timeIngestedSubstance = timeIngestedSubstance;
	}

	public void setDateIngestedSubstance(String dateIngestedSubstance) {
		this.dateIngestedSubstance = dateIngestedSubstance;
	}

	public String getAitNumber() {
		return aitNumber;
	}

	public String getTcaNumber() {
		return tcaNumber;
	}

	public void setAitNumber(String aitNumber) {
		this.aitNumber = aitNumber;
	}

	public void setTcaNumber(String tcaNumber) {
		this.tcaNumber = tcaNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getCnhPpd() {
		return cnhPpd;
	}

	public String getCpf() {
		return cpf;
	}

	public String getAddress() {
		return address;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPlate() {
		return plate;
	}

	public String getPlateState() {
		return plateState;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public String getDriverInvolvedInCarAccident() {
		return driverInvolvedInCarAccident;
	}

	public String getDriverClaimsToHaveDrunkAlcohol() {
		return driverClaimsToHaveDrunkAlcohol;
	}

	public String getDriverClaimsToHaveUsedToxicSubstance() {
		return driverClaimsToHaveUsedToxicSubstance;
	}

	public String getDriverShowsSignsOf() {
		return driverShowsSignsOf;
	}

	public String getInHisAttitudeOccurs() {
		return inHisAttitudeOccurs;
	}

	public String getKnowsWhereItIs() {
		return knowsWhereItIs;
	}

	public String getKnowsTheDateAndTime() {
		return knowsTheDateAndTime;
	}

	public String getKnowsItsAddress() {
		return knowsItsAddress;
	}

	public String getRememberTheActsCommitted() {
		return rememberTheActsCommitted;
	}

	public String getInRelationToTheirMotorAndVerbalAbilityOccurs() {
		return inRelationToTheirMotorAndVerbalAbilityOccurs;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public void setCnhPpd(String cnhPpd) {
		this.cnhPpd = cnhPpd;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public void setDriverInvolvedInCarAccident(
			String driverInvolvedInCarAccident) {
		this.driverInvolvedInCarAccident = driverInvolvedInCarAccident;
	}

	public void setDriverClaimsToHaveDrunkAlcohol(
			String driverClaimsToHaveDrunkAlcohol) {
		this.driverClaimsToHaveDrunkAlcohol = driverClaimsToHaveDrunkAlcohol;
	}

	public void setDriverClaimsToHaveUsedToxicSubstance(
			String driverClaimsToHaveUsedToxicSubstance) {
		this.driverClaimsToHaveUsedToxicSubstance = driverClaimsToHaveUsedToxicSubstance;
	}

	public void setDriverShowsSignsOf(
			String driverShowsSignsOf) {
		this.driverShowsSignsOf = driverShowsSignsOf;
	}

	public void setInHisAttitudeOccurs(String inHisAttitudeOccurs) {
		this.inHisAttitudeOccurs = inHisAttitudeOccurs;
	}

	public void setKnowsWhereItIs(String knowsWhereItIs) {
		this.knowsWhereItIs = knowsWhereItIs;
	}

	public void setKnowsTheDateAndTime(String knowsTheDateAndTime) {
		this.knowsTheDateAndTime = knowsTheDateAndTime;
	}

	public void setKnowsItsAddress(String knowsItsAddress) {
		this.knowsItsAddress = knowsItsAddress;
	}

	public void setRememberTheActsCommitted(String rememberTheActsCommitted) {
		this.rememberTheActsCommitted = rememberTheActsCommitted;
	}

	public void setInRelationToTheirMotorAndVerbalAbilityOccurs(
			String inRelationToTheirMotorAndVerbalAbilityOccurs) {
		this.inRelationToTheirMotorAndVerbalAbilityOccurs = inRelationToTheirMotorAndVerbalAbilityOccurs;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public TcaData() {

		driverName = cnhPpd = cpf = address = district = city = state = plate = plateState
				= brandModel = driverInvolvedInCarAccident = driverClaimsToHaveDrunkAlcohol
				= driverClaimsToHaveUsedToxicSubstance = driverShowsSignsOf = inHisAttitudeOccurs
				= knowsWhereItIs = knowsTheDateAndTime = knowsItsAddress = rememberTheActsCommitted
				= inRelationToTheirMotorAndVerbalAbilityOccurs = conclusion = aitNumber = tcaNumber = timeThatDrankAlcohol
				= dateThatDrankAlcohol = timeIngestedSubstance = dateIngestedSubstance = tcaType = "";

	}

	public String getTCAViewData(Context context) {
		String tcaData = getNameViewData(context)
				+ getCnhPpdViewData(context)
				+ getFilterViewData(context, false);
		return tcaData;

	}

	private String getNewline() {
		return AppConstants.NEW_LINE;
	}

	private String getNewline_2() {
		return AppConstants.NEW_LINE + AppConstants.NEW_LINE;
	}

	public void setTCADataFromCursor(Cursor myCursor) {

		setDriverName(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.NOME_DO_CONDUTOR)));
		setCnhPpd(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.CNH_PPD)));
		setCpf(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.CPF)));

		setAddress(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.ENDERECO)));

		setDistrict(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.BAIRRO)));

		setCity(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.MUNICIPIO)));

		setState(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.MUNICIPIO_UF)));

		setPlate(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.PLACA)));

		setPlateState(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.PLACA_UF)));

		setBrandModel(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.MARCA_MODELO)));

		setDriverInvolvedInCarAccident(myCursor
				.getString(myCursor
						.getColumnIndex(TcaDatabaseHelper.CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO)));

		setDriverClaimsToHaveDrunkAlcohol(myCursor
				.getString(myCursor
						.getColumnIndex(TcaDatabaseHelper.CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA)));

		setDateThatDrankAlcohol(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.DATA_INGERIU_ALCOOL)));

		setTimeThatDrankAlcohol(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.HORA_INGERIU_ALCOOL)));

		setDriverClaimsToHaveUsedToxicSubstance(myCursor
				.getString(myCursor
						.getColumnIndex(TcaDatabaseHelper.CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA)));

		
		setDateIngestedSubstance(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.DATA_INGERIU_SUBSTANCIA)));

		setTimeIngestedSubstance(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.HORA_INGERIU_SUBSTANCIA)));
		
		
		setDriverShowsSignsOf(myCursor
				.getString(myCursor
						.getColumnIndex(TcaDatabaseHelper.O_CONDUTOR_APRESENTA_SINAIS_DE)));

		setInHisAttitudeOccurs(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.EM_SUA_ATITUDE_OCORRE)));

		setKnowsWhereItIs(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.SABE_ONDE_ESTA)));

		setKnowsTheDateAndTime(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.SABE_A_DATA_E_A_HORA)));

		setKnowsItsAddress(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.SABE_SEU_ENDERECO)));

		setRememberTheActsCommitted(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.LEMBRA_DOS_ATOS_COMETIDOS)));

		setInRelationToTheirMotorAndVerbalAbilityOccurs(myCursor
				.getString(myCursor
						.getColumnIndex(TcaDatabaseHelper.EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE)));
		setConclusion(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.CONCLUSAO)));

		setTcaNumber(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.NUMERO_TCA)));
		setAitNumber(myCursor.getString(myCursor
				.getColumnIndex(TcaDatabaseHelper.NUMERO_AUTO)));

	}

	public String getTcaListViewData(Context context) {
		return getFilterViewData(context, true);

	}

	public String getPlateViewData(Context context) {
		return context.getResources().getString(R.string.placa) + getNewline()
				+ plate + getNewline_2();

	}

	public String getNameViewData(Context context) {
		return context.getResources().getString(R.string.tca_nome)
				+ getNewline() + driverName + getNewline_2();

	}

	public String getCnhPpdViewData(Context context) {
		return context.getResources().getString(R.string.tca_CNH_PPD)
				+ getNewline() + cnhPpd + getNewline_2();

	}

	private String getFilterViewData(Context context, boolean isLister) {

		String viewTca = context.getResources().getString(R.string.tca_CPF)
				+ getNewline() + cpf + getNewline_2()
				+ context.getResources().getString(R.string.tca_endereco)
				+ getNewline() + address + getNewline_2()
				+ context.getResources().getString(R.string.tca_bairro)
				+ getNewline() + district + getNewline_2()
				+ context.getResources().getString(R.string.tca_municipio)
				+ getNewline() + city + getNewline_2()
				+ context.getResources().getString(R.string.tca_UF)
				+ getNewline() + state + getNewline_2();

		// if (!isLister) {
		viewTca += getPlateViewData(context);
		// }

		viewTca += context.getResources().getString(R.string.tca_UF)
				+ getNewline()
				+ plateState
				+ getNewline_2()
				+ context.getResources().getString(R.string.tca_condutor_1)
				+ getNewline()
				+ driverInvolvedInCarAccident
				+ getNewline_2()
				+ context.getResources().getString(R.string.tca_condutor_2)
				+ getNewline()
				+ driverClaimsToHaveDrunkAlcohol
				+ getNewline()
				+ context.getResources().getString(R.string.data)
				+ getNewline()
				+ dateThatDrankAlcohol
				+ getNewline()
				+ context.getResources().getString(R.string.hora)
				+ getNewline()
				+ timeThatDrankAlcohol
				+ getNewline_2()
				+ context.getResources().getString(R.string.tca_condutor_3)
				+ getNewline()
				+ driverClaimsToHaveUsedToxicSubstance
				+ getNewline()
				+ context.getResources().getString(R.string.data)
				+ getNewline()
				+ dateIngestedSubstance
				+ getNewline()
				+ context.getResources().getString(R.string.hora)
				+ getNewline()
				+ timeIngestedSubstance
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_o_condutor_apresenta)
				+ getNewline()
				+ driverShowsSignsOf
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_em_sua_atitude_ocorre)
				+ getNewline()
				+ inHisAttitudeOccurs
				+ getNewline_2()
				+ context.getResources().getString(R.string.tca_sabe_onde_esta)
				+ getNewline()
				+ knowsWhereItIs
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_sabe_a_data_e_a_hora)
				+ getNewline()
				+ knowsTheDateAndTime
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_sabe_seu_enderecao)
				+ getNewline()
				+ knowsItsAddress
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_lembra_dos_atos_cometidos)
				+ getNewline()
				+ rememberTheActsCommitted
				+ getNewline_2()
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.tca_em_relacao_a_sua_verbal_ocorre)
				+ getNewline()
				+ inRelationToTheirMotorAndVerbalAbilityOccurs
				+ getNewline_2() + getNewline_2()
				+ context.getResources().getString(R.string.tca_se_constatei)
				+ getNewline() + conclusion + getNewline_2()
				+ context.getResources().getString(R.string.numero_tca)
				+ getNewline() + tcaNumber + getNewline_2();

		return viewTca;

	}

}
