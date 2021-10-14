package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.sistransito.mobile.plate.data.DataFromPlate;
import net.sistransito.mobile.timeandime.TimeAndIme;

//import net.sqlcipher.database.SQLiteDatabase;

public class SearchPlateDatabaseAdapter {

    private TimeAndIme ime;
	private PlateSearchDatabaseHelper plateSearchDatabaseHelper;
	private SQLiteDatabase database;

	public SearchPlateDatabaseAdapter(Context context) {
		ime = new TimeAndIme(context);
		plateSearchDatabaseHelper = new PlateSearchDatabaseHelper(context);
		//database = placaSearchDatabaseHelper.getWritableDatabase(ime.getIME());
		database = plateSearchDatabaseHelper.getWritableDatabase();
	}

	public boolean insertPlacaSearchData(DataFromPlate dataPlate) {
		long check_insert = -1;

		if (dataPlate != null) {

			if (!isSamePlate(dataPlate.getPlate())) {

				ContentValues values = new ContentValues();
				values.put(PlateSearchDatabaseHelper.PLATE,
						dataPlate.getPlate());
				values.put(PlateSearchDatabaseHelper.STATE,
						dataPlate.getState());
				values.put(PlateSearchDatabaseHelper.COUNTRY,
						dataPlate.getCountry());
				values.put(PlateSearchDatabaseHelper.CHASSIS,
						dataPlate.getChassi());
				values.put(PlateSearchDatabaseHelper.BRAND,
						dataPlate.getBrand());
				values.put(PlateSearchDatabaseHelper.MODEL,
						dataPlate.getModel());
				values.put(PlateSearchDatabaseHelper.COLOR,
						dataPlate.getColor());
				values.put(PlateSearchDatabaseHelper.TYPE,
						dataPlate.getType());
				values.put(PlateSearchDatabaseHelper.CATEGORY,
						dataPlate.getCategory());
				values.put(PlateSearchDatabaseHelper.LICENSING_YEAR,
						dataPlate.getLicenceYear());
				values.put(PlateSearchDatabaseHelper.LICENSING_DATE,
						dataPlate.getLicenceData());
				values.put(PlateSearchDatabaseHelper.LICENSING_STATUS,
						dataPlate.getLicenceStatus());
				values.put(PlateSearchDatabaseHelper.IPVA,
						dataPlate.getIpva());
				values.put(PlateSearchDatabaseHelper.INSURANCE,
						dataPlate.getInsurance());
				values.put(PlateSearchDatabaseHelper.STATUS,
						dataPlate.getStatus());
				values.put(PlateSearchDatabaseHelper.INFRATIONS,
						dataPlate.getInfractions());
				values.put(PlateSearchDatabaseHelper.RESTRICTIONS,
						dataPlate.getRestrictions());
				values.put(PlateSearchDatabaseHelper.DATE,
						dataPlate.getDate());
				values.put(PlateSearchDatabaseHelper.LATITUDE,
						dataPlate.getLatitude());
				values.put(PlateSearchDatabaseHelper.LONGITUDE,
						dataPlate.getLongitude());

				check_insert = database.insert(
						PlateSearchDatabaseHelper.TABLE_NAME, null, values);
			}
		}

		return check_insert > 0;

	}

	public boolean deletePlateData(String placaName) {
		return false;
	}

	public synchronized void close() {
		database.close();
	}

	public Cursor getPlateCursor() {
		Cursor myCursor = database.query(PlateSearchDatabaseHelper.TABLE_NAME,
				null, null, null, null, null,
				PlateSearchDatabaseHelper.COLUMN_ID + " DESC");
		return myCursor;
	}

	public Cursor getPlateCursor_2() {
		Cursor myCursor = database.query(PlateSearchDatabaseHelper.TABLE_NAME,
				null, null, null, null, null,
				PlateSearchDatabaseHelper.COLUMN_ID + " DESC");
		return myCursor;
	}

	public Cursor getPlateFromIDCursor(int id) {
		Cursor myCursor = this.database.query(
				PlateSearchDatabaseHelper.TABLE_NAME, null,
				PlateSearchDatabaseHelper.COLUMN_ID + "=?", new String[]{id
						+ ""}, null, null, null);
		return myCursor;
	}

	public boolean deleteDataFromIdField(String id) {
		return this.database
				.delete(PlateSearchDatabaseHelper.TABLE_NAME,
						PlateSearchDatabaseHelper.COLUMN_ID + "=?",
						new String[] { id }) > 0;
	}

	private boolean isSamePlate(String placa) {
		Cursor myCursor = database.query(PlateSearchDatabaseHelper.TABLE_NAME,
				null, PlateSearchDatabaseHelper.PLATE + "=?",
				new String[] { placa }, null, null, null);
		if (myCursor.getCount() > 0) {
			myCursor.close();
			return true;
		} else {
			myCursor.close();
			return false;
		}
	}

}


