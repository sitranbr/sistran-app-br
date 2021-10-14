package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sistransito.R;
import android.database.sqlite.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteDatabase;

public class BalanceDatabaseAdapter {
	private BalanceDatabaseHelper databaseHelper;
	private Context context;
	private SQLiteDatabase db;
	private TimeAndIme ime;

	public BalanceDatabaseAdapter(Context context) {
		this.context = context;
		databaseHelper = new BalanceDatabaseHelper(context);
		ime = new TimeAndIme(context);
		//db = databaseHelper.getWritableDatabase(ime.getIME());
		db = databaseHelper.getWritableDatabase();
		checkTable();
	}

	private int getValue(String columnName) {
		Cursor cursor = db.query(BalanceDatabaseHelper.TABLE_NAME, null, null,
				null, null, null, null);
		cursor.moveToFirst();
		int value = cursor.getInt(cursor.getColumnIndex(columnName));
		cursor.close();
		return value;

	}

	public int getAitPerformed() {
		return getValue(BalanceDatabaseHelper.AID_PERFORMED);
	}

	public int getTavPerformed() {
		return getValue(BalanceDatabaseHelper.TAV_PERFORMED);
	}

	public int getTcaPerformed() {
		return getValue(BalanceDatabaseHelper.TCA_PERFORMED);
	}

	public int getRrdPerformed() {
		return getValue(BalanceDatabaseHelper.RRD_PERFORMED);
	}

	public int getAitRemaining() {
		return getValue(BalanceDatabaseHelper.AIT_REMAINING);
	}

	public int getTavRemaining() {
		return getValue(BalanceDatabaseHelper.TAV_REMAINING);
	}

	public int getTcaRemaining() {
		return getValue(BalanceDatabaseHelper.TCA_REMAINING);
	}

	public int getRrdRemaining() {
		return getValue(BalanceDatabaseHelper.RRD_REMAINING);
	}

	private void setValue(String columnName, int value) {
		ContentValues values = new ContentValues();
		values.put(columnName, value);
		db.update(BalanceDatabaseHelper.TABLE_NAME, values,
				SetttingDatabaseHelper.SETTING_ID + "= ? ",
				new String[] { "1" });

	}

	public void setAitPerformed() {
		setValue(BalanceDatabaseHelper.AID_PERFORMED,
				getAitPerformed() + 1);
	}

	public void setTavPerformed() {
		setValue(BalanceDatabaseHelper.TAV_PERFORMED, getTavPerformed() + 1);
	}

	public void setTcaPerformed() {
		setValue(BalanceDatabaseHelper.TCA_PERFORMED, getTcaPerformed() + 1);
	}

	public void setRrdPerformed() {
		setValue(BalanceDatabaseHelper.RRD_PERFORMED, getRrdPerformed() + 1);
	}

	public void setAitPerformed(int autos_realizados) {
		setValue(BalanceDatabaseHelper.AID_PERFORMED, autos_realizados);
	}

	public void setTavPerformed(int tav_realizados) {
		setValue(BalanceDatabaseHelper.TAV_PERFORMED, tav_realizados);
	}

	public void setTcaPerformed(int tca_realizados) {
		setValue(BalanceDatabaseHelper.TCA_PERFORMED, tca_realizados);
	}

	public void setRrdPerformed(int rrd_realizados) {
		setValue(BalanceDatabaseHelper.RRD_PERFORMED, rrd_realizados);
	}

	public void setAitRemaining(int aitRemaining) {
		setValue(BalanceDatabaseHelper.AIT_REMAINING, aitRemaining);
	}

	public void setTavRemaining(int tavRemaining) {
		setValue(BalanceDatabaseHelper.TAV_REMAINING, tavRemaining);
	}

	public void setTcaRemaining(int tcaRemaining) {
		setValue(BalanceDatabaseHelper.TCA_REMAINING, tcaRemaining);
	}

	public void setRrdRemaining(int rrdRemaining) {
		setValue(BalanceDatabaseHelper.RRD_REMAINING, rrdRemaining);
	}

	public synchronized void close() {
		db.close();
	}

	private void checkTable() {

		Cursor mcursor = db.rawQuery("SELECT count(*) FROM "
				+ BalanceDatabaseHelper.TABLE_NAME, null);

		mcursor.moveToFirst();
		int icount = mcursor.getInt(0);
		if (icount > 0) {
			mcursor.close();
			return;
		} else {

			ContentValues values = new ContentValues();
			values.put(BalanceDatabaseHelper.ID_BALANCE, 1);
			long a = db.insert(BalanceDatabaseHelper.TABLE_NAME, null, values);

			boolean c = a > 0;
			mcursor.close();
		}
	}

	public String getShowView() {
		String data = AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.autos_de_Infracao)
				+ AppConstants.NEW_LINE + AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.autos_realizados)
				+ getAitPerformed() + "     "
				+ context.getResources().getString(R.string.restante)
				+ getAitRemaining() + AppConstants.NEW_LINE;

		data += AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.tav)
				+ AppConstants.NEW_LINE + AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.tav_realizados)
				+ getTavPerformed() + "     "
				+ context.getResources().getString(R.string.restante)
				+ getTavRemaining() + AppConstants.NEW_LINE;

		data += AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.tca)
				+ AppConstants.NEW_LINE + AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.tca_realizados)
				+ getTcaPerformed() + "     "
				+ context.getResources().getString(R.string.restante)
				+ getTcaRemaining() + AppConstants.NEW_LINE;

		data += AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.rrd)
				+ AppConstants.NEW_LINE + AppConstants.NEW_LINE
				+ context.getResources().getString(R.string.rrd_realizados)
				+ getRrdPerformed() + "     "
				+ context.getResources().getString(R.string.restante)
				+ getRrdRemaining() + AppConstants.NEW_LINE;

		return data;
	}
}