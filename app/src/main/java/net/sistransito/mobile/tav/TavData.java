package net.sistransito.mobile.tav;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.TavDatabaseHelper;
import net.sistransito.R;

import java.io.Serializable;

public class TavData implements Serializable {

	private static final String TAV_ID = "tav_id";
	private static final long serialVersionUID = 18393745L;

	private String plate, aitNumber, ownerName, cpfCnpj,
			renavamNumber, chassisNumber, leverHead,
			carBody, lining, hoodBody, rightSideBody,
			leftSideBody, trunkBodywork, roofBodywork,
			enginer, dashboard, hoodPaint, rightSidePaint,
			leftSidePaint, trunkPainting, ceilingPainting, radiator,
			sideWindows, windShieldGlass, rearWindow, antenna,
			trunk, seats, baterry, wheelCover, airConditioner,
			fireExtinguisher, headLight, taiLight,
			jack, frontBumper, backBumper,
			driverSunVisor, tires, spareTire, radio,
			rearviewMirror, rightSideMirror, carpet, triangle,
			steeringWheel, motorcycleHandlebar, odometer, fuelGauge,
			removedVia, companyName, winchDriverName,
			observation, tavNumber, tavType;

	public String getTavType() {
		return tavType;
	}

	public void setTavType(String tavType) {
		this.tavType = tavType;
	}

	public String getTavNumber() {
		return tavNumber;
	}

	public void setTavNumber(String tavNumber) {
		this.tavNumber = tavNumber;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getWinchDriverName() {
		return winchDriverName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setWinchDriverName(
			String winchDriverName) {
		this.winchDriverName = winchDriverName;
	}

	public String getAitNumber() {
		return aitNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public String getRenavamNumber() {
		return renavamNumber;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public String getLeverHead() {
		return leverHead;
	}

	public String getCarBody() {
		return carBody;
	}

	public String getLining() {
		return lining;
	}

	public String getHoodBody() {
		return hoodBody;
	}

	public String getRightSideBody() {
		return rightSideBody;
	}

	public String getLeftSideBody() {
		return leftSideBody;
	}

	public String getTrunkBodywork() {
		return trunkBodywork;
	}

	public String getRoofBodywork() {
		return roofBodywork;
	}

	public String getEnginer() {
		return enginer;
	}

	public String getDashboard() {
		return dashboard;
	}

	public String getHoodPaint() {
		return hoodPaint;
	}

	public String getRightSidePaint() {
		return rightSidePaint;
	}

	public String getLeftSidePaint() {
		return leftSidePaint;
	}

	public String getTrunkPainting() {
		return trunkPainting;
	}

	public String getCeilingPainting() {
		return ceilingPainting;
	}

	public String getRadiator() {
		return radiator;
	}

	public String getSideWindows() {
		return sideWindows;
	}

	public String getWindShieldGlass() {
		return windShieldGlass;
	}

	public String getRearWindow() {
		return rearWindow;
	}

	public String getAntenna() {
		return antenna;
	}

	public String getTrunk() {
		return trunk;
	}

	public String getSeats() {
		return seats;
	}

	public String getBaterry() {
		return baterry;
	}

	public String getWheelCover() {
		return wheelCover;
	}

	public String getAirConditioner() {
		return airConditioner;
	}

	public String getFireExtinguisher() {
		return fireExtinguisher;
	}

	public String getHeadLight() {
		return headLight;
	}

	public String getTaiLight() {
		return taiLight;
	}

	public String getJack() {
		return jack;
	}

	public String getFrontBumper() {
		return frontBumper;
	}

	public String getBackBumper() {
		return backBumper;
	}

	public String getDriverSunVisor() {
		return driverSunVisor;
	}

	public String getTires() {
		return tires;
	}

	public String getSpareTire() {
		return spareTire;
	}

	public String getRadio() {
		return radio;
	}

	public String getRearviewMirror() {
		return rearviewMirror;
	}

	public String getRightSideMirror() {
		return rightSideMirror;
	}

	public String getCarpet() {
		return carpet;
	}

	public String getTriangle() {
		return triangle;
	}

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public String getMotorcycleHandlebar() {
		return motorcycleHandlebar;
	}

	public String getOdometer() {
		return odometer;
	}

	public String getFuelGauge() {
		return fuelGauge;
	}

	public String getRemovedVia() {
		return removedVia;
	}

	public String getObservation() {
		return observation;
	}

	public void setAitNumber(String aitNumber) {
		this.aitNumber = aitNumber;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public void setRenavamNumber(String renavamNumber) {
		this.renavamNumber = renavamNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public void setLeverHead(String leverHead) {
		this.leverHead = leverHead;
	}

	public void setCarBody(String carBody) {
		this.carBody = carBody;
	}

	public void setLining(String lining) {
		this.lining = lining;
	}

	public void setHoodBody(String hoodBody) {
		this.hoodBody = hoodBody;
	}

	public void setRightSideBody(String rightSideBody) {
		this.rightSideBody = rightSideBody;
	}

	public void setLeftSideBody(String leftSideBody) {
		this.leftSideBody = leftSideBody;
	}

	public void setTrunkBodywork(String trunkBodywork) {
		this.trunkBodywork = trunkBodywork;
	}

	public void setRoofBodywork(String roofBodywork) {
		this.roofBodywork = roofBodywork;
	}

	public void setEnginer(String enginer) {
		this.enginer = enginer;
	}

	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	public void setHoodPaint(String hoodPaint) {
		this.hoodPaint = hoodPaint;
	}

	public void setRightSidePaint(String rightSidePaint) {
		this.rightSidePaint = rightSidePaint;
	}

	public void setLeftSidePaint(String leftSidePaint) {
		this.leftSidePaint = leftSidePaint;
	}

	public void setTrunkPainting(String trunkPainting) {
		this.trunkPainting = trunkPainting;
	}

	public void setCeilingPainting(String ceilingPainting) {
		this.ceilingPainting = ceilingPainting;
	}

	public void setRadiator(String radiator) {
		this.radiator = radiator;
	}

	public void setSideWindows(String sideWindows) {
		this.sideWindows = sideWindows;
	}

	public void setWindShieldGlass(String windShieldGlass) {
		this.windShieldGlass = windShieldGlass;
	}

	public void setRearWindow(String rearWindow) {
		this.rearWindow = rearWindow;
	}

	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}

	public void setTrunk(String trunk) {
		this.trunk = trunk;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public void setBaterry(String baterry) {
		this.baterry = baterry;
	}

	public void setWheelCover(String wheelCover) {
		this.wheelCover = wheelCover;
	}

	public void setAirConditioner(String airConditioner) {
		this.airConditioner = airConditioner;
	}

	public void setFireExtinguisher(String fireExtinguisher) {
		this.fireExtinguisher = fireExtinguisher;
	}

	public void setHeadLight(String headLight) {
		this.headLight = headLight;
	}

	public void setTaiLight(String taiLight) {
		this.taiLight = taiLight;
	}

	public void setJack(String jack) {
		this.jack = jack;
	}

	public void setFrontBumper(String frontBumper) {
		this.frontBumper = frontBumper;
	}

	public void setBackBumper(String backBumper) {
		this.backBumper = backBumper;
	}

	public void setDriverSunVisor(String driverSunVisor) {
		this.driverSunVisor = driverSunVisor;
	}

	public void setTires(String tires) {
		this.tires = tires;
	}

	public void setSpareTire(String spareTire) {
		this.spareTire = spareTire;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public void setRearviewMirror(String rearviewMirror) {
		this.rearviewMirror = rearviewMirror;
	}

	public void setRightSideMirror(String rightSideMirror) {
		this.rightSideMirror = rightSideMirror;
	}

	public void setCarpet(String carpet) {
		this.carpet = carpet;
	}

	public void setTriangle(String triangle) {
		this.triangle = triangle;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}

	public void setMotorcycleHandlebar(String motorcycleHandlebar) {
		this.motorcycleHandlebar = motorcycleHandlebar;
	}

	public void setOdometer(String odometer) {
		this.odometer = odometer;
	}

	public void setFuelGauge(String fuelGauge) {
		this.fuelGauge = fuelGauge;
	}

	public void setRemovedVia(String removedVia) {
		this.removedVia = removedVia;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public TavData() {

		aitNumber = ownerName = cpfCnpj = renavamNumber = chassisNumber = leverHead = carBody = lining = hoodBody = rightSideBody = leftSideBody = trunkBodywork = roofBodywork = enginer = dashboard = hoodPaint = rightSidePaint = leftSidePaint = trunkPainting = ceilingPainting = radiator = sideWindows = windShieldGlass = rearWindow = antenna = trunk = seats = baterry = wheelCover = airConditioner = fireExtinguisher = headLight = taiLight = jack = frontBumper = backBumper = driverSunVisor = tires = spareTire = radio = rearviewMirror = rightSideMirror = carpet = triangle = steeringWheel = motorcycleHandlebar = odometer = fuelGauge = removedVia = companyName = winchDriverName = observation = tavNumber = tavType = "";

	}

	public static String getTavId() {
		return TAV_ID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTAVListerView(Context context) {
		String tav = "";
		tav = context.getResources().getString(R.string.numero_do_auto)
				+ getNewline()
				+ aitNumber
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.nome_do_proprietario) + getNewline()
				+ ownerName + getNewline_2() + getviewData(context);

		return tav;

	}

	private String getNewline() {
		return AppConstants.NEW_LINE;
	}

	private String getNewline_2() {
		return AppConstants.NEW_LINE + AppConstants.NEW_LINE;
	}

	public void setTAVDataFromCursor(Cursor myCursor) {

		plate = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PLATE));
		
		
		Log.d("placa", plate);
		aitNumber = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.AIT_NUMBER));
		ownerName = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.OWNER_NAME));
		cpfCnpj = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CPF_CNPJ));
		renavamNumber = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.RENAVAM_NUMBER));
		chassisNumber = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CHASSI_NUMBER));
		leverHead = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CABECA_DE_ALAVANCA));
		carBody = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CARROCERIA));
		lining = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.FORRO));
		hoodBody = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.LATARIA_CAPO));

		rightSideBody = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.LATARIA_LADO_DIREITO));
		leftSideBody = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.LATARIA_LADO_ESQUERDO));
		trunkBodywork = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.LATARIA_TAPA_PORTA_MALA));
		roofBodywork = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.LATARIA_TETO));

		enginer = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.MOTOR));
		dashboard = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PAINEL));
		hoodPaint = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PINTURA_CAPO));
		rightSidePaint = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PINTURA_LADO_DIREITO));
		leftSidePaint = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PINTURA_LADO_ESQUERDO));
		trunkPainting = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PINTURA_PORTA_MALA));

		ceilingPainting = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PINTURA_TETO));
		radiator = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.RADIADOR));
		sideWindows = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.VIDROS_LATERAIS));

		windShieldGlass = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.VIDRO_PARA_BRISA));
		rearWindow = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.VIDRO_TRASEIRO));

		antenna = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.ANTENA_DE_RADIO));
		trunk = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.BAGAGEIRO));
		seats = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.BANCOS));
		baterry = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.BATERIA));
		wheelCover = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CALOTA));

		airConditioner = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.CONDICIONADOR_DE_AR));

		fireExtinguisher = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.EXTINTOR_DE_INCENDIO));
		headLight = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.FAROLETE_DIANTEIRO));
		taiLight = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.FAROLETE_TRASEIRO));
		jack = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.MACACO));

		frontBumper = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PARA_CHOQUE_DIANTEIRO));
		backBumper = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PARA_CHOQUE_TRASEIRO));
		driverSunVisor = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PARA_SOL_DO_CONDUTOR));
		tires = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PNEUS));

		spareTire = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.PNEUS_ESTEPE));
		radio = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.RADIO));
		rearviewMirror = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.RETROVISOR_INTERNO));

		rightSideMirror = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.RETROVISOR_EXTERNO_DIREITO));
		carpet = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.TAPETE));
		triangle = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.TRIANGULO));

		steeringWheel = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.VOLANTE));
		motorcycleHandlebar = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.GUIDAM));
		odometer = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.ODOMETRO));

		fuelGauge = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.MARCADOR_DE_CONBUTIVEL));
		removedVia = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.REMOCAO_ATRAVES_DE));
		companyName = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.NOME_DA_EMPRESA));
		winchDriverName = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.NOME_DO_CONDUTOR_DO_GUINCHO));
		observation = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.OBSERVACAO));
		tavNumber = myCursor.getString(myCursor
				.getColumnIndex(TavDatabaseHelper.TAV_NUMBER));

	}

	public CharSequence getTCAListViewData(Context mycontext) {

		return getviewData(mycontext);
	}

	private String getviewData(Context context) {

		String data = context.getResources().getString(R.string.CPF_CNPJ)
				+ getNewline()
				+ cpfCnpj
				+ getNewline_2()
				+ context.getResources().getString(R.string.numero_do_renavam)
				+ getNewline()
				+ renavamNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.numero_do_chassi)
				+ getNewline()
				+ chassisNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.cabeca_de_alavanca)
				+ getNewline()
				+ leverHead
				+ getNewline_2()
				+ context.getResources().getString(R.string.carroceria)
				+ getNewline()
				+ carBody
				+ getNewline_2()
				+ context.getResources().getString(R.string.forro)
				+ getNewline()
				+ lining
				+ getNewline_2()
				+ context.getResources().getString(R.string.lataria_capo)
				+ getNewline()
				+ hoodBody
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.lataria_lado_direito)
				+ getNewline()
				+ rightSideBody
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.lataria_lado_esquerdo)
				+ getNewline()
				+ leftSideBody
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.lataria_tapa_porta_mala)
				+ getNewline()
				+ trunkBodywork
				+ getNewline_2()
				+ context.getResources().getString(R.string.lataria_teto)
				+ getNewline()
				+ roofBodywork
				+ getNewline_2()
				+ context.getResources().getString(R.string.motor)
				+ getNewline()
				+ enginer
				+ getNewline_2()
				+ context.getResources().getString(R.string.painel)
				+ getNewline()
				+ dashboard
				+ getNewline_2()
				+ context.getResources().getString(R.string.pintura_capo)
				+ getNewline()
				+ hoodPaint
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.pintura_lado_direito)
				+ getNewline()
				+ rightSidePaint
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.pintura_lado_esquerdo)
				+ getNewline()
				+ leftSidePaint
				+ getNewline_2()
				+ context.getResources().getString(R.string.pintura_porta_mala)
				+ getNewline()
				+ trunkPainting
				+ getNewline_2()
				+ context.getResources().getString(R.string.pintura_teto)
				+ getNewline()
				+ ceilingPainting
				+ getNewline_2()
				+ context.getResources().getString(R.string.radiador)
				+ getNewline()
				+ radiator
				+ getNewline_2()
				+ context.getResources().getString(R.string.vidros_laterais)
				+ getNewline()
				+ sideWindows
				+ getNewline_2()
				+ context.getResources().getString(R.string.vidro_para_brisa)
				+ getNewline()
				+ windShieldGlass
				+ getNewline_2()
				+ context.getResources().getString(R.string.vidro_traseiro)
				+ getNewline()
				+ rearWindow
				+ getNewline_2()
				+ context.getResources().getString(R.string.antena_de_radio)
				+ getNewline()
				+ antenna
				+ getNewline_2()
				+ context.getResources().getString(R.string.bagageiro)
				+ getNewline()
				+ trunk
				+ getNewline_2()
				+ context.getResources().getString(R.string.bancos)
				+ getNewline()
				+ seats
				+ getNewline_2()
				+ context.getResources().getString(R.string.bateria)
				+ getNewline()
				+ baterry
				+ getNewline_2()
				+ context.getResources().getString(R.string.calota)
				+ getNewline()
				+ wheelCover
				+ getNewline_2()
				+ context.getResources()
						.getString(R.string.condicionador_de_ar)
				+ getNewline()
				+ airConditioner
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.extintor_de_incendio)
				+ getNewline()
				+ fireExtinguisher
				+ getNewline_2()
				+ context.getResources().getString(R.string.farolete_dianteiro)
				+ getNewline()
				+ headLight
				+ getNewline_2()
				+ context.getResources().getString(R.string.farolete_traseiro)
				+ getNewline()
				+ taiLight
				+ getNewline_2()
				+ context.getResources().getString(R.string.macaco)
				+ getNewline()
				+ jack
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.para_choque_dianteiro)
				+ getNewline()
				+ frontBumper
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.para_choque_traseiro)
				+ getNewline()
				+ backBumper
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.para_sol_do_condutor)
				+ getNewline()
				+ driverSunVisor
				+ getNewline_2()
				+ context.getResources().getString(R.string.pneus)
				+ getNewline()
				+ tires
				+ getNewline_2()
				+ context.getResources().getString(R.string.pneus_estepe)
				+ getNewline()
				+ spareTire
				+ getNewline_2()
				+ context.getResources().getString(R.string.radio)
				+ getNewline()
				+ radio
				+ getNewline_2()
				+ context.getResources().getString(R.string.retrovisor_interno)
				+ getNewline()
				+ rearviewMirror
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.retrovisor_externo_direito)
				+ getNewline()
				+ rightSideMirror
				+ getNewline_2()
				+ context.getResources().getString(R.string.tapete)
				+ getNewline()
				+ carpet
				+ getNewline_2()
				+ context.getResources().getString(R.string.triangulo)
				+ getNewline()
				+ triangle
				+ getNewline_2()
				+ context.getResources().getString(R.string.volante)
				+ getNewline()
				+ steeringWheel
				+ getNewline_2()
				+ context.getResources().getString(R.string.guidam)
				+ getNewline()
				+ motorcycleHandlebar
				+ getNewline_2()
				+ context.getResources().getString(R.string.odometro)
				+ getNewline()
				+ odometer
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.marcador_de_combustivel)
				+ getNewline()
				+ fuelGauge
				+ getNewline_2()
				+ context.getResources().getString(R.string.remocao_atraves_de)
				+ getNewline()
				+ removedVia
				+ getNewline_2()
				+ context.getResources().getString(R.string.nome_da_empresa)
				+ getNewline()
				+ companyName
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.nome_do_condutor_do_guincho) + getNewline()
				+ winchDriverName + getNewline_2()
				+ context.getResources().getString(R.string.observacao)
				+ getNewline() + observation + getNewline_2()
				+ context.getResources().getString(R.string.numero_tav)
				+ getNewline() + tavNumber + getNewline_2();

		return data;

	}

}
