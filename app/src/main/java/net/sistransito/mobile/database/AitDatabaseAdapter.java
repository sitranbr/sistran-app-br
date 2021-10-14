package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.sync.SyncFiles;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.timeandime.TimeAndIme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

//import net.sqlcipher.database.SQLiteDatabase;

public class AitDatabaseAdapter {
	private final TimeAndIme ime;
	private final SQLiteDatabase database;
	private final AitDatabaseHelper aitDatabaseHelper;
	private final Context context;

	public AitDatabaseAdapter(Context context) {
		ime = new TimeAndIme(context);
		aitDatabaseHelper = new AitDatabaseHelper(context);
		//database = databaseHelper.getReadableDatabase(ime.getIME());
		database = aitDatabaseHelper.getReadableDatabase();
		this.context = context;
	}

	public AitData getAitDataFromPlate(String plate) {

		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.PLATE + "=?", new String[] { plate
						+ "" }, null, null, null);

		if (myCursor.getCount() > 0) {
			AitData aitData = new AitData();
			myCursor.moveToFirst();
			aitData.setAitDataFromCursor(myCursor);
			myCursor.close();
			return aitData;
		} else {
			myCursor.close();
			return null;
		}
	}

	public boolean isSamePlateExist(String placa) {
		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.PLATE + "=?", new String[] { placa
						+ "" }, null, null, null);
		if (myCursor.getCount() > 0) {
			myCursor.close();
			return true;
		} else {
			myCursor.close();
			return false;
		}
	}

	public void close() {
		database.close();
	}

	public boolean setAitData(AitData data) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.AIT_NUMBER,
				data.getAitNumber());
		values.put(AitDatabaseHelper.PLATE, data.getPlate());
		values.put(AitDatabaseHelper.VEHICLE_STATE, data.getStateVehicle());
		values.put(AitDatabaseHelper.CHASSI, data.getChassi());
		values.put(AitDatabaseHelper.COUNTRY, data.getCountry());
		values.put(AitDatabaseHelper.VEHICLE_MODEL,
				data.getVehicleModel());
		values.put(AitDatabaseHelper.VEHICLE_COLOR,
				data.getVehycleColor());
		values.put(AitDatabaseHelper.SPECIES, data.getVehicleSpecies());
		values.put(AitDatabaseHelper.CATEGORY, data.getVehicleCategory());
		values.put(AitDatabaseHelper.DRIVER_NAME,
				data.getConductorName());
		values.put(AitDatabaseHelper.DRIVER_FOREIGN,
				data.getForeignDriver());
		values.put(AitDatabaseHelper.DRIVER_COUNTRY,
				data.getDriverCountry());
		values.put(AitDatabaseHelper.ENABLED_DRIVER,
				data.getQualifiedDriver());
		values.put(AitDatabaseHelper.DRIVER_LICENSE, data.getCnhPpd());
		values.put(AitDatabaseHelper.DRIVER_LICENSE_STATE, data.getCnhState());
		values.put(AitDatabaseHelper.DOCUMENT_TYPE,
				data.getDocumentType());
		values.put(AitDatabaseHelper.DOCUMENT_NUMBER,
				data.getDocumentNumber());
		values.put(AitDatabaseHelper.INFRATION, data.getInfraction());
		values.put(AitDatabaseHelper.FLAMING_CODE, data.getFramingCode());
		values.put(AitDatabaseHelper.UNFOLDING, data.getUnfolding());
		values.put(AitDatabaseHelper.ARTICLE, data.getArticle());
		values.put(AitDatabaseHelper.CITY_CODE,
				data.getCityCode());
		values.put(AitDatabaseHelper.CITY, data.getCity());
		values.put(AitDatabaseHelper.STATE, data.getState());
		values.put(AitDatabaseHelper.ADDRESS, data.getAddress());
		values.put(AitDatabaseHelper.AIT_DATE, data.getAitDate());
		values.put(AitDatabaseHelper.AIT_TIME, data.getAitTime());
		values.put(AitDatabaseHelper.EQUIPMENT_DESCRIPTION, data.getDescription());
		values.put(AitDatabaseHelper.EQUIPMENT_BRAND,
				data.getVehicleBrand());
		values.put(AitDatabaseHelper.EQUIPMENT_MODEL,
				data.getVehicleModel());
		values.put(AitDatabaseHelper.EQUIPMENT_SERIAL,
				data.getSerialNumber());
		values.put(AitDatabaseHelper.MEASUREMENT_PERFORMED,
				data.getMeasurementPerformed());
		values.put(AitDatabaseHelper.REGULATED_VALUE,
				data.getRegulatedValue());
		values.put(AitDatabaseHelper.VALUE_CONSIDERED,
				data.getValueConsidered());
		values.put(AitDatabaseHelper.ALCOHOL_TEST_NUMBER,
				data.getAlcoholTestNumber());
		values.put(AitDatabaseHelper.RETREAT,
				data.getRetreat());
		values.put(AitDatabaseHelper.PROCEDURES,
				data.getProcedures());
		values.put(AitDatabaseHelper.OBSERVATION,
				data.getObservation());
		values.put(AitDatabaseHelper.SHIPPER_IDENTIFICATION,
				data.getShipperIdentification());
		values.put(AitDatabaseHelper.CPF_SHIPPER,
				data.getCpfShipper());
		values.put(AitDatabaseHelper.CNPJ_SHIPPER,
				data.getCnpShipper());
		values.put(AitDatabaseHelper.CARRIER_IDENTIFICATION,
				data.getCarrierIdentification());
		values.put(AitDatabaseHelper.CPF_CARRIER,
				data.getCpfCarrier());
		values.put(AitDatabaseHelper.CNPJ_CARRIER,
				data.getCnpjCarrier());
		values.put(AitDatabaseHelper.CANCELL_STATUS,
				data.getCancellationStatus());
		values.put(AitDatabaseHelper.REASON_FOR_CANCELL,
				data.getReasonForCancellation());
		values.put(AitDatabaseHelper.SYNC_STATUS,
				data.getSyncStatus());

		long insert = this.database.insert(AitDatabaseHelper.TABLE_NAME, null, values);

		if (insert > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteAitNumber(data.getAitNumber());
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainAitNumber());
			return true;
		} else {
			return false;
		}

	}

	//Inseri o ID do auto sempre que se iniciar um novo AIT
	public boolean insertAitNumber(AitData data){

		ContentValues values = new ContentValues();
		values.put(AitDatabaseHelper.AIT_NUMBER,
				data.getAitNumber());

		long insert = this.database.insert(AitDatabaseHelper.TABLE_NAME, null, values);

		if (insert > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteAitNumber(data.getAitNumber());
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainAitNumber());
			return true;
		} else {
			return false;
		}

	}

	public boolean cancelAit(AitData data, String motivo) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.CANCELL_STATUS, "1");
		values.put(AitDatabaseHelper.REASON_FOR_CANCELL, motivo);
		values.put(AitDatabaseHelper.COMPLETED_STATUS,"1");

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { data.getAitNumber() });

		Log.d("setCancel", update + "-" + motivo + " | Ait number: " + data.getAitNumber());

		if (update > 0 && NetworkConnection.isInternetConnected(context)) {
			SyncFiles sync = new SyncFiles(context);
			sync.sendAitCancelled(data.getAitNumber());
			return true;
		} else {
			return false;
		}

	}

	public boolean synchronizeAit(String ait) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.SYNC_STATUS,"1");

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { ait });

		Log.d("setSynchronized", "ait number: " + ait + " - update: " + update);

		return update > 0;

	}

	public boolean insertAidData(AitData aitData) {

		ContentValues values = new ContentValues();

		String country;

		if(aitData.getCountry() == null){
			country = "BR";
		}else{
			country = aitData.getCountry();
		}

		values.put(AitDatabaseHelper.PLATE, aitData.getPlate());
		values.put(AitDatabaseHelper.VEHICLE_STATE, aitData.getStateVehicle());
		values.put(AitDatabaseHelper.CHASSI, aitData.getChassi());
		values.put(AitDatabaseHelper.COUNTRY, country);
		values.put(AitDatabaseHelper.VEHICLE_MODEL,
				aitData.getVehicleModel());
		values.put(AitDatabaseHelper.VEHICLE_COLOR,
				aitData.getVehycleColor());
		values.put(AitDatabaseHelper.SPECIES, aitData.getVehicleSpecies());
		values.put(AitDatabaseHelper.CATEGORY, aitData.getVehicleCategory());
		values.put(AitDatabaseHelper.CANCELL_STATUS, "0");
		values.put(AitDatabaseHelper.REASON_FOR_CANCELL, "");
		values.put(AitDatabaseHelper.SYNC_STATUS, "0");
		values.put(AitDatabaseHelper.AIT_LATITUDE, aitData.getAitLatitude());
		values.put(AitDatabaseHelper.AIT_LONGITUDE, aitData.getAitLongitude());
		values.put(AitDatabaseHelper.AIT_DATE_TIME, aitData.getAitDateTime());
		values.put(AitDatabaseHelper.COMPLETED_STATUS, "0");

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { aitData.getAitNumber() });

		Log.d("setVehicle", update + "-" + aitData);

		if (update > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteAitNumber(aitData.getAitNumber());
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setAitRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainAitNumber());
			return true;
		} else {
			return false;
		}

	}

	public boolean updateAitDataConductor(AitData data) {

		ContentValues values = new ContentValues();

        values.put(AitDatabaseHelper.APPROACH, data.getApproach());
		values.put(AitDatabaseHelper.DRIVER_NAME, data.getConductorName());
		values.put(AitDatabaseHelper.DRIVER_LICENSE, data.getCnhPpd());
		values.put(AitDatabaseHelper.DRIVER_LICENSE_STATE, data.getCnhState());
		values.put(AitDatabaseHelper.DOCUMENT_TYPE, data.getDocumentType());
		values.put(AitDatabaseHelper.DOCUMENT_NUMBER, data.getDocumentNumber());

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { data.getAitNumber() });

		Log.d("setDriver", update + "-" + data);

		return update > 0;

	}

	public boolean updateAitDataAddress(AitData data) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.ADDRESS, data.getAddress());
		values.put(AitDatabaseHelper.AIT_DATE, data.getAitDate());
		values.put(AitDatabaseHelper.AIT_TIME, data.getAitTime());
		values.put(AitDatabaseHelper.CITY_CODE, data.getCityCode());
		values.put(AitDatabaseHelper.CITY, data.getCity());
		values.put(AitDatabaseHelper.STATE, data.getState());

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { data.getAitNumber() });

		Log.d("setAddress", update + "-" + data);

		return update > 0;

	}

	public boolean updateAitDataInfraction(AitData data) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.INFRATION, data.getInfraction());
		values.put(AitDatabaseHelper.FLAMING_CODE, data.getFramingCode());
		values.put(AitDatabaseHelper.UNFOLDING, data.getUnfolding());
		values.put(AitDatabaseHelper.ARTICLE, data.getArticle());
		values.put(AitDatabaseHelper.TCA_NUMBER, data.getTcaNumber());
		values.put(AitDatabaseHelper.EQUIPMENT_DESCRIPTION, data.getDescription());
		values.put(AitDatabaseHelper.EQUIPMENT_BRAND, data.getEquipmentBrand());
		values.put(AitDatabaseHelper.EQUIPMENT_MODEL, data.getEquipmentModel());
		values.put(AitDatabaseHelper.EQUIPMENT_SERIAL, data.getSerialNumber());
		values.put(AitDatabaseHelper.MEASUREMENT_PERFORMED, data.getMeasurementPerformed());
		values.put(AitDatabaseHelper.REGULATED_VALUE, data.getRegulatedValue());
		values.put(AitDatabaseHelper.VALUE_CONSIDERED, data.getValueConsidered());
		values.put(AitDatabaseHelper.ALCOHOL_TEST_NUMBER, data.getAlcoholTestNumber());
		values.put(AitDatabaseHelper.RETREAT, data.getRetreat());
		values.put(AitDatabaseHelper.PROCEDURES, data.getProcedures());
		values.put(AitDatabaseHelper.OBSERVATION, data.getObservation());
		values.put(AitDatabaseHelper.SHIPPER_IDENTIFICATION, data.getShipperIdentification());
		values.put(AitDatabaseHelper.CPF_SHIPPER, data.getCpfShipper());
		values.put(AitDatabaseHelper.CNPJ_SHIPPER, data.getCnpShipper());
		values.put(AitDatabaseHelper.CARRIER_IDENTIFICATION, data.getCarrierIdentification());
		values.put(AitDatabaseHelper.CPF_CARRIER, data.getCpfCarrier());
		values.put(AitDatabaseHelper.CNPJ_CARRIER, data.getCnpjCarrier());

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { data.getAitNumber() });

		Log.d("setInfracao", update + "-" + data);

		return update > 0;

	}

	public boolean insertAitDataPhotos(String ait, String local, String photo) {

		ContentValues values = new ContentValues();

		switch (local){
			case "1":
				values.put(AitDatabaseHelper.AIT_PHOTO1, photo);
				break;
			case "2":
				values.put(AitDatabaseHelper.AIT_PHOTO2, photo);
				break;
			case "3":
				values.put(AitDatabaseHelper.AIT_PHOTO3, photo);
				break;
			case "4":
				values.put(AitDatabaseHelper.AIT_PHOTO4, photo);
				break;
		}

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { ait });

		if (update > 0 && NetworkConnection.isInternetConnected(context)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean updateAitDataPhotos(AitData aitData) {

		ContentValues values = new ContentValues();

		values.put(AitDatabaseHelper.COMPLETED_STATUS, "1");

		int update = this.database.update(AitDatabaseHelper.TABLE_NAME,
				values, AitDatabaseHelper.AIT_NUMBER + "= ? ",
				new String[] { aitData.getAitNumber() });

		//Log.d("setFotos", update + "-" + dadosAuto);

		if (update > 0 && NetworkConnection.isInternetConnected(context)) {
			SyncFiles sync = new SyncFiles(context);
			sync.sendDataAuto(aitData.getAitNumber());
			return true;
		} else {
			return false;
		}

	}

	public Cursor getAitCursor() {

		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null, null, null, null,
				null, AitDatabaseHelper.COLUMN_ID + " DESC");
		return myCursor;

	}

	public String getIdAit() {
		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null, null, null, null,
				null, null);
		if (myCursor.getCount() > 0) {
			myCursor.moveToLast();
			String num = myCursor
					.getString(myCursor.getColumnIndex(AitDatabaseHelper.COLUMN_ID));
			myCursor.close();
			return num;
		} else {
			myCursor.close();
			return null;
		}
	}

	public String getAitDataFromId(String idAuto) {
		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.COLUMN_ID + "=?",
				new String[] { idAuto }, null, null, null);

		if (myCursor.getCount() > 0) {
			myCursor.moveToLast();
			String num = myCursor
					.getString(myCursor.getColumnIndex(AitDatabaseHelper.AIT_NUMBER));
			myCursor.close();
			return num;
		} else {
			myCursor.close();
			return null;
		}

	}

	public String getStatusCursor(String idAuto) {

		String completedStatus;

		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.COLUMN_ID + "=?",
				new String[] { idAuto }, null, null, null);

		if (myCursor.getCount() > 0) {
			myCursor.moveToFirst();

			completedStatus = (myCursor.getString(myCursor
					.getColumnIndex(AitDatabaseHelper.COMPLETED_STATUS)));

			myCursor.close();
		} else {
			myCursor.close();
			return null;
		}

		return completedStatus;

	}

	public Cursor getAitActiveCursor() {
		Cursor myCursor = this.database.query(AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.COMPLETED_STATUS + "=?",
						new String[] { "1" }, null,
						null, AitDatabaseHelper.COLUMN_ID + " DESC");
		return myCursor;

	}

	public Cursor getAitCursorFromID(int id) {
		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.COLUMN_ID + "=?", new String[] { id
						+ "" }, null, null, null);
		return myCursor;
	}

	public Cursor getAitCancelCursor() {
		Cursor myCursor = this.database.query(AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.AIT_NUMBER + "=?",
				new String[] { "TL00007452" }, null, null, null);
		return myCursor;
	}

	public AitData getDataFromAitNumber(String aitNumber) {
		Cursor myCursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null,
				AitDatabaseHelper.AIT_NUMBER + "=?",
				new String[] { aitNumber + "" }, null, null, null);

		if (myCursor != null && myCursor.moveToFirst()) {
			AitData aitData = new AitData();
			aitData.setAitDataFromCursor(myCursor);
			myCursor.close();
			return aitData;
		}
		return null;

	}

	public String aitDataToJSon() {

		Cursor cursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null, null, null, null,
				null, null);

		AitData aitData;

			if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
				do {

				    try{

						// Here we convert Java Object to JSON
						aitData = new AitData();
						aitData.setAitDataFromCursor(cursor);

						JSONObject jsonArr = new JSONObject();

						jsonArr.put(AitDatabaseHelper.AIT_NUMBER,
								aitData.getAitNumber());

						jsonArr.put("AUTOJSON", jsonArr);

						return jsonArr.toString();

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

				} while (cursor.moveToNext());

			} else {
				return null;
			}

		cursor.close();
		return null;

	}

	public JSONArray getResults() {

		Cursor cursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null, null, null, null,
				null, AitDatabaseHelper.COLUMN_ID,"3");

		JSONArray resultSet = new JSONArray();

		cursor.moveToFirst();

		while (cursor.isAfterLast() == false) {

			int totalColumn = cursor.getColumnCount();
			JSONObject rowObject = new JSONObject();
			for (int i = 0; i < totalColumn; i++) {
				if (cursor.getColumnName(i) != null) {

					try {

						if (cursor.getString(i) != null) {
							Log.d("TAG_NAME", cursor.getString(i));
							rowObject.put(cursor.getColumnName(i), cursor.getString(i));
						} else {
							rowObject.put(cursor.getColumnName(i), "");
						}
					} catch (Exception e) {
						Log.d("TAG_NAME", e.getMessage());
					}

				}

			}
			resultSet.put(rowObject);
			cursor.moveToNext();
		}

		cursor.close();
		//Log.d("TAG_NAME", resultSet.toString());
		return resultSet;
	}

	public String aitComposeJSONfromSQLite() {

		ArrayList<HashMap<String, String>> arrayListAit = new ArrayList<HashMap<String, String>>();

		Cursor cursor = this.database.query(
				AitDatabaseHelper.TABLE_NAME, null, null, null, null,
				null, AitDatabaseHelper.COLUMN_ID,"1");

		AitData aitData;

		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			do {
				aitData = new AitData();
				aitData.setAitDataFromCursor(cursor);
				HashMap<String, String> map = new HashMap<String, String>();

				// condutor
				map.put(AitDatabaseHelper.AIT_NUMBER,
						aitData.getAitNumber());
				map.put(AitDatabaseHelper.PLATE, aitData.getPlate());
				map.put(AitDatabaseHelper.VEHICLE_STATE, aitData.getStateVehicle());
				map.put(AitDatabaseHelper.CHASSI, aitData.getChassi());
				map.put(AitDatabaseHelper.COUNTRY, aitData.getCountry());
				map.put(AitDatabaseHelper.VEHICLE_MODEL,
						aitData.getVehicleModel());
				map.put(AitDatabaseHelper.VEHICLE_COLOR,
						aitData.getVehycleColor());
				map.put(AitDatabaseHelper.SPECIES, aitData.getVehicleSpecies());
				map.put(AitDatabaseHelper.CATEGORY,
						aitData.getVehicleCategory());
				map.put(AitDatabaseHelper.DRIVER_NAME,
						aitData.getConductorName());
				map.put(AitDatabaseHelper.DRIVER_LICENSE, aitData.getCnhPpd());
				map.put(AitDatabaseHelper.DRIVER_LICENSE_STATE, aitData.getCnhState());
				map.put(AitDatabaseHelper.DOCUMENT_TYPE,
						aitData.getDocumentType());
				map.put(AitDatabaseHelper.DOCUMENT_NUMBER,
						aitData.getDocumentNumber());

				// infracao

				map.put(AitDatabaseHelper.INFRATION, aitData.getInfraction());
				map.put(AitDatabaseHelper.FLAMING_CODE, aitData.getFramingCode());
				map.put(AitDatabaseHelper.UNFOLDING, aitData.getUnfolding());
				map.put(AitDatabaseHelper.ARTICLE, aitData.getArticle());
				map.put(AitDatabaseHelper.CITY_CODE,
						aitData.getCityCode());
				map.put(AitDatabaseHelper.CITY,
						aitData.getCity());
				map.put(AitDatabaseHelper.STATE, aitData.getState());
				map.put(AitDatabaseHelper.ADDRESS, aitData.getAddress());
				map.put(AitDatabaseHelper.AIT_DATE, aitData.getAitDate());
				map.put(AitDatabaseHelper.AIT_TIME, aitData.getAitTime());

				map.put(AitDatabaseHelper.EQUIPMENT_DESCRIPTION,
						aitData.getDescription());
				map.put(AitDatabaseHelper.EQUIPMENT_BRAND, aitData.getVehicleBrand());
				map.put(AitDatabaseHelper.EQUIPMENT_MODEL, aitData.getVehicleModel());
				map.put(AitDatabaseHelper.EQUIPMENT_SERIAL,
						aitData.getSerialNumber());
				map.put(AitDatabaseHelper.MEASUREMENT_PERFORMED,
						aitData.getMeasurementPerformed());
				map.put(AitDatabaseHelper.VALUE_CONSIDERED,
						aitData.getValueConsidered());
				map.put(AitDatabaseHelper.ALCOHOL_TEST_NUMBER,
						aitData.getAlcoholTestNumber());

				// gerar
				map.put(AitDatabaseHelper.RETREAT,
						aitData.getRetreat());
				map.put(AitDatabaseHelper.PROCEDURES,
						aitData.getProcedures());
				map.put(AitDatabaseHelper.OBSERVATION,
						aitData.getObservation());
				map.put(AitDatabaseHelper.SHIPPER_IDENTIFICATION,
						aitData.getShipperIdentification());
				map.put(AitDatabaseHelper.CPF_SHIPPER,
						aitData.getCpfShipper());
				map.put(AitDatabaseHelper.CNPJ_SHIPPER,
						aitData.getCnpShipper());
				map.put(AitDatabaseHelper.CARRIER_IDENTIFICATION,
						aitData.getCarrierIdentification());
				map.put(AitDatabaseHelper.CPF_CARRIER,
						aitData.getCpfCarrier());
				map.put(AitDatabaseHelper.CNPJ_CARRIER,
						aitData.getCnpjCarrier());
				map.put(AitDatabaseHelper.CANCELL_STATUS,
						aitData.getCancellationStatus());
				map.put(AitDatabaseHelper.REASON_FOR_CANCELL,
						aitData.getReasonForCancellation());
				map.put(AitDatabaseHelper.AIT_PHOTO1,
						aitData.getPhoto1());
				//compressBitmapToFile(destination);
				map.put(AitDatabaseHelper.AIT_PHOTO2,
						aitData.getPhoto2());
				map.put(AitDatabaseHelper.AIT_PHOTO3,
						aitData.getPhoto3());
				map.put(AitDatabaseHelper.AIT_PHOTO4,
						aitData.getPhoto4());
				map.put(AitDatabaseHelper.SYNC_STATUS,
						aitData.getSyncStatus());

				arrayListAit.add(map);
			} while (cursor.moveToNext());

		} else {
			return null;
		}

		cursor.close();

		Gson gson = new GsonBuilder().create();
		return gson.toJson(arrayListAit);

	}

	private String getStringImage(Bitmap bmp) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		return Base64.encodeToString(imageBytes, Base64.DEFAULT);
	}



	public void aitUpdateSyncStatus(String ait) {
		this.database.delete(AitDatabaseHelper.TABLE_NAME,
				AitDatabaseHelper.AIT_NUMBER + "=?",
				new String[] { ait });
	}

}
