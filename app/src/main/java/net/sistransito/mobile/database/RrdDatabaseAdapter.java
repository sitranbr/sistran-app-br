package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sistransito.mobile.rrd.RrdData;
import net.sistransito.mobile.timeandime.TimeAndIme;
import android.database.sqlite.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RrdDatabaseAdapter {
	private RrdDatabaseHelper rrdDBHelper;
	private Context context;
	private SQLiteDatabase database;
	private TimeAndIme ime;

	public RrdDatabaseAdapter(Context context) {
		this.context = context;
		rrdDBHelper = new RrdDatabaseHelper(this.context);
		ime = new TimeAndIme(context);
		openWriteableDatabase();
	}

	public void openWriteableDatabase() {
		/// ime.getIME() return the device ime number and this is the password ok
		//database = rrdDBHelper.getWritableDatabase(ime.getIME());
		database = rrdDBHelper.getWritableDatabase();
	}

	public void openReadableDatabase() {
		//database = rrdDBHelper.getReadableDatabase(ime.getIME());
	     database = rrdDBHelper.getReadableDatabase();
	}

	public synchronized void close() {
		database.close();
	}

	public boolean insertRrdInformation(RrdData data) {

		ContentValues values = new ContentValues();
		values.put(RrdDatabaseHelper.RRD_NUMBER, data.getRrdNumber());
		values.put(RrdDatabaseHelper.AIT_NUMBER,
				data.getAitNumber());
		values.put(RrdDatabaseHelper.RRD_NUMBER, data.getRrdNumber());
		values.put(RrdDatabaseHelper.DRIVER_NAME,
				data.getDriverName());
		values.put(RrdDatabaseHelper.DOCUMENT_TYPE, data.getDocumentType());
		values.put(RrdDatabaseHelper.CRLV_NUMBER, data.getCrlvNumber());
		values.put(RrdDatabaseHelper.PLATE, data.getPlate());
		values.put(RrdDatabaseHelper.PLATE_STATE, data.getPlateState());
		values.put(RrdDatabaseHelper.CNH_NUMBER, data.getRegistrationNumber());
		values.put(RrdDatabaseHelper.CNH_STATE, data.getRegistrationState());
		values.put(RrdDatabaseHelper.CNH_VALIDITY, data.getValidity());
		values.put(RrdDatabaseHelper.DAYS_FOR_REGULARIZATION,
				data.getDaysForRegularization());
		values.put(RrdDatabaseHelper.REASON_FOR_COLLECTION,
				data.getReasonCollected());
		values.put(RrdDatabaseHelper.COLLECTION_DATE,
				data.getDateCollected());
		values.put(RrdDatabaseHelper.COLLECTION_TIME,
				data.getTimeCollected());
		values.put(RrdDatabaseHelper.COLLECTION_CITY,
				data.getCityCollected());
		values.put(RrdDatabaseHelper.COLLECTION_STATE,
				data.getStateCollected());
		values.put(RrdDatabaseHelper.RRD_TYPE,
				data.getRrdType());


		long insert = database.insert(RrdDatabaseHelper.TABLE_NAME, null, values);

		if (insert > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteRrdNumber(data.getRrdNumber());

			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setRrdPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setRrdRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainNumberRRD());
			return true;
		} else {
			return false;
		}
	}

	public Cursor getRrdCursor() {
		Cursor myCursor = this.database.query(RrdDatabaseHelper.TABLE_NAME, null, null,
				null, null, null, RrdDatabaseHelper.COLUMN_ID + " DESC");
		return myCursor;

	}

	public long deleteData(String tableName, String whereClause,
			String[] whereArgs) {
		return database.delete(tableName, whereClause, whereArgs);
	}

	public Cursor getRrdCursorFromID(int id) {
		Cursor myCursor = this.database.query(RrdDatabaseHelper.TABLE_NAME, null,
				RrdDatabaseHelper.COLUMN_ID + "=?", new String[] { id + "" },
				null, null, null);
		return myCursor;

	}

	public String composeRrdJSONfromSQLite() {
		ArrayList<HashMap<String, String>> rrdList = new ArrayList<HashMap<String, String>>();
		Cursor cursor = this.database.query(RrdDatabaseHelper.TABLE_NAME, null, null,
				null, null, null, null);
		RrdData data;
		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			do {
				data = new RrdData();
				data.setRRDDataFromCursor(cursor);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(RrdDatabaseHelper.RRD_NUMBER, data.getRrdNumber());
				map.put(RrdDatabaseHelper.AIT_NUMBER, data.getAitNumber());
				map.put(RrdDatabaseHelper.DRIVER_NAME, data.getDriverName());
				map.put(RrdDatabaseHelper.DOCUMENT_TYPE, data.getDocumentType());
				map.put(RrdDatabaseHelper.CRLV_NUMBER, data.getCrlvNumber());
				map.put(RrdDatabaseHelper.CNH_NUMBER, data.getRegistrationNumber());
				map.put(RrdDatabaseHelper.CNH_STATE, data.getRegistrationState());
				map.put(RrdDatabaseHelper.CNH_VALIDITY, data.getValidity());
				map.put(RrdDatabaseHelper.PLATE, data.getPlate());
				map.put(RrdDatabaseHelper.PLATE_STATE, data.getPlateState());
				map.put(RrdDatabaseHelper.REASON_FOR_COLLECTION, data.getReasonCollected());
				map.put(RrdDatabaseHelper.DAYS_FOR_REGULARIZATION, data.getDaysForRegularization());
				map.put(RrdDatabaseHelper.COLLECTION_DATE, data.getDateCollected());
				map.put(RrdDatabaseHelper.COLLECTION_TIME, data.getTimeCollected());
				map.put(RrdDatabaseHelper.COLLECTION_CITY, data.getCityCollected());
				map.put(RrdDatabaseHelper.COLLECTION_STATE, data.getStateCollected());

				rrdList.add(map);
			} while (cursor.moveToNext());

		} else {

			return null;
		}
		cursor.close();
		Gson gson = new GsonBuilder().create();
		// Use GSON to serialize Array List to JSON
		return gson.toJson(rrdList);
	}

	public void updateRrdSyncStatus(String numero_rrd) {
		this.database.delete(RrdDatabaseHelper.TABLE_NAME,
				RrdDatabaseHelper.RRD_NUMBER + "=?",
				new String[] { numero_rrd });
	}

}