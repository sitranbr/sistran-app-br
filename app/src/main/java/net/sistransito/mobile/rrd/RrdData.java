package net.sistransito.mobile.rrd;

import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.RrdDatabaseHelper;
import net.sistransito.R;

import java.io.Serializable;

public class RrdData implements Serializable {

	private static final String RRD_ID = "rrd_id";
	private static final long SERIAL_VERSION_UID = 14393750L;
	private String idField, rrdNumber, aitNumber, driverName, documentType,
			crlvNumber, plate, plateState, registrationNumber, registrationState, validity,
			reasonCollected, daysForRegularization, dateCollected, timeCollected, cityCollected, stateCollected, rrdType;

	public String getStateCollected() {
		return stateCollected;
	}

	public void setStateCollected(String stateCollected) {
		this.stateCollected = stateCollected;
	}

	public String getRrdType() {
        return rrdType;
    }

    public void setRrdType(String rrdType) {
        this.rrdType = rrdType;
    }

    public String getRegistrationState() {
        return registrationState;
    }

    public void setRegistrationState(String registrationState) {
        this.registrationState = registrationState;
    }

    public String getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(String dateCollected) {
        this.dateCollected = dateCollected;
    }

    public String getTimeCollected() {
        return timeCollected;
    }

    public void setTimeCollected(String timeCollected) {
        this.timeCollected = timeCollected;
    }

    public String getCityCollected() {
        return cityCollected;
    }

    public void setCityCollected(String cityCollected) {
        this.cityCollected = cityCollected;
    }

    public String getRrdNumber() {
		return rrdNumber;
	}

	public void setRrdNumber(String rrdNumber) {
		this.rrdNumber = rrdNumber;
	}

	public static String getRRDId() {
		return RRD_ID;
	}

	public static long getSerialversionuid() {
		return SERIAL_VERSION_UID;
	}

	public String getIdField() {
		return idField;
	}

	public String getAitNumber() {
		return aitNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getCrlvNumber() {
		return crlvNumber;
	}

	public String getPlate() {
		return plate;
	}

	public String getPlateState() {
		return plateState;
	}

	public String getDaysForRegularization() {
		return daysForRegularization;
	}

	public String getReasonCollected() {
		return reasonCollected;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setAitNumber(String aitNumber) {
		this.aitNumber = aitNumber;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public void setCrlvNumber(String crlvNumber) {
		this.crlvNumber = crlvNumber;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}

	public void setDaysForRegularization(
			String daysForRegularization) {
		this.daysForRegularization = daysForRegularization;
	}

	public void setReasonCollected(String reasonCollected) {
		this.reasonCollected = reasonCollected;
	}

	public RrdData() {
		idField = rrdNumber = aitNumber = driverName = documentType = crlvNumber = plate = registrationNumber = registrationState = validity = plateState
                = daysForRegularization = reasonCollected = dateCollected = timeCollected = cityCollected = stateCollected = rrdType = "";

	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getValidity() {
		return validity;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	private String getNewline() {
		return AppConstants.NEW_LINE;
	}

	private String getNewline_2() {
		return AppConstants.NEW_LINE + AppConstants.NEW_LINE;
	}

	public void setRRDDataFromCursor(Cursor myCursor) {
        setRrdNumber(myCursor.getString(myCursor
                .getColumnIndex(RrdDatabaseHelper.RRD_NUMBER)));
		setAitNumber(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.AIT_NUMBER)));

		setDriverName(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.DRIVER_NAME)));
		
		setDocumentType(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.DOCUMENT_TYPE)));
		setCrlvNumber(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.CRLV_NUMBER)));
		
		setPlate(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.PLATE)));

		setRegistrationNumber(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.CNH_NUMBER)));
        setRegistrationNumber(myCursor.getString(myCursor
                .getColumnIndex(RrdDatabaseHelper.CNH_NUMBER)));

		setValidity(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.CNH_VALIDITY)));

		setPlateState(myCursor.getString(myCursor.getColumnIndex(RrdDatabaseHelper.PLATE_STATE)));
		setReasonCollected(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.REASON_FOR_COLLECTION)));
		setDaysForRegularization(myCursor
				.getString(myCursor
						.getColumnIndex(RrdDatabaseHelper.DAYS_FOR_REGULARIZATION)));
		setDateCollected(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.COLLECTION_DATE)));
        setTimeCollected(myCursor.getString(myCursor
                .getColumnIndex(RrdDatabaseHelper.COLLECTION_TIME)));
        setCityCollected(myCursor.getString(myCursor
                .getColumnIndex(RrdDatabaseHelper.COLLECTION_CITY)));
		setStateCollected(myCursor.getString(myCursor
				.getColumnIndex(RrdDatabaseHelper.COLLECTION_STATE)));
        setRrdType(myCursor.getString(myCursor
                .getColumnIndex(RrdDatabaseHelper.RRD_TYPE)));
	}

	public String getRRDViewData(Context context) {
		String rrd = "";
		return rrd;
	}

	public String getRRDListViewData(Context context) {
		String rrd =

		context.getResources().getString(R.string.rrd_numero_auto)
				+ getNewline()
				+ aitNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_numero)
				+ getNewline()
				+ rrdNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_recebemos_de)
				+ getNewline()
				+ driverName
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_numero_crlv)
				+ getNewline()
				+ crlvNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_numero_registro)
				+ getNewline()
				+ registrationNumber
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_validade)
				+ getNewline()
				+ validity
				+ getNewline_2()
				+ context.getResources().getString(R.string.rrd_uf)
				+ getNewline()
				+ plateState
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.rrd_qtd_de_dias_para_regularizacao)
				+ getNewline()
				+ daysForRegularization
				+ getNewline_2()
				+ context.getResources().getString(
						R.string.rrd_motivo_para_recolhimento) + getNewline()
				+ reasonCollected + getNewline_2();

		return rrd;

	}
}
