package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.timeandime.TimeAndIme;
import android.database.sqlite.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteDatabase;

public class SettingDatabaseAdapter {
	private TimeAndIme ime;
	private SetttingDatabaseHelper databaseHelperSettting;
	private SQLiteDatabase database;

	public SettingDatabaseAdapter(Context context) {
		databaseHelperSettting = new SetttingDatabaseHelper(context);
		ime = new TimeAndIme(context);
		//database = databaseHelperSettting.getWritableDatabase(ime.getIME());
		database = databaseHelperSettting.getWritableDatabase();
		checkTable();
	}

	private String getNumero(String table_name, String filed_name) {
		Cursor myCursor = this.database.query(table_name, null, null, null,
				null, null, filed_name + " DESC");
		if (myCursor.getCount() > 0) {
			myCursor.moveToLast();
			String num = myCursor
					.getString(myCursor.getColumnIndex(filed_name));
			myCursor.close();
			return num;
		} else {
			myCursor.close();
			return null;
		}
	}

	public Cursor getSettingCursor() {
		Cursor myCursor = this.database.query(SetttingDatabaseHelper.SETTING_TABLE_NAME,
				null, null, null, null, null, null);
		myCursor.moveToFirst();
		return myCursor;
	}

	public String getSettingNumero() {
		return getNumero(SetttingDatabaseHelper.SETTING_TABLE_NAME,
				SetttingDatabaseHelper.SETTING_ID);
	}

	public String getFont() {
		Cursor cursor = database.query(
				SetttingDatabaseHelper.SETTING_TABLE_NAME, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		String value=cursor.getString(cursor
				.getColumnIndex(SetttingDatabaseHelper.SETTING_FONT));
		cursor.close();
		return value;
	}

	public boolean setFont(String font) {

		ContentValues values = new ContentValues();
		values.put(SetttingDatabaseHelper.SETTING_RINGTONE, font);
		int check = database.update(SetttingDatabaseHelper.SETTING_TABLE_NAME,
				values, SetttingDatabaseHelper.SETTING_ID + "= ? ",
				new String[] { "1" });

		return check > 1;

	}

	public Boolean getAutobackup() {
		Cursor cursor = database.query(
				SetttingDatabaseHelper.SETTING_TABLE_NAME, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if ((cursor.getString(cursor
				.getColumnIndex(SetttingDatabaseHelper.SETTING_AUTO_BACKUP)))
				.equals(AppConstants.TRUE)) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}
	}

	public Boolean getRingtone() {
		Cursor cursor = database.query(
				SetttingDatabaseHelper.SETTING_TABLE_NAME, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if ((cursor.getString(cursor
				.getColumnIndex(SetttingDatabaseHelper.SETTING_RINGTONE)))
				.equals(AppConstants.TRUE)) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}
	}

	public Boolean getVibrator() {
		Cursor cursor = database.query(
				SetttingDatabaseHelper.SETTING_TABLE_NAME, null, null, null,
				null, null, null);
		cursor.moveToFirst();
		if ((cursor.getString(cursor
				.getColumnIndex(SetttingDatabaseHelper.SETTING_VIBRATOR)))
				.equals(AppConstants.TRUE)) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}
	}

	public void setVibrator(boolean check) {
		String value;
		if (check) {
			value = AppConstants.TRUE;
		} else {
			value = AppConstants.FALSE;
		}
		ContentValues values = new ContentValues();
		values.put(SetttingDatabaseHelper.SETTING_VIBRATOR, value);
		int w = database.update(SetttingDatabaseHelper.SETTING_TABLE_NAME,
				values, SetttingDatabaseHelper.SETTING_ID + "= ? ",
				new String[] { "1" });
		Log.d("setVibrator", w + "");

	}

	public void setRingtone(boolean check) {
		String value;
		if (check) {
			value = AppConstants.TRUE;
		} else {
			value = AppConstants.FALSE;
		}
		ContentValues values = new ContentValues();
		values.put(SetttingDatabaseHelper.SETTING_RINGTONE, value);
		database.update(SetttingDatabaseHelper.SETTING_TABLE_NAME, values,
				SetttingDatabaseHelper.SETTING_ID + "= ? ",
				new String[] { "1" });

	}

	public void setAutobackup(boolean check) {
		String value;
		if (check) {
			value = AppConstants.TRUE;
		} else {
			value = AppConstants.FALSE;
		}
		ContentValues values = new ContentValues();
		values.put(SetttingDatabaseHelper.SETTING_AUTO_BACKUP, value);
		database.update(SetttingDatabaseHelper.SETTING_TABLE_NAME, values,
				SetttingDatabaseHelper.SETTING_ID + "= ? ",
				new String[] { "1" });

	}

	public boolean setUpdatePrinterUf(String uf, String printer) {
		ContentValues values = new ContentValues();
		values.put(SetttingDatabaseHelper.SETTING_UF, uf);
		values.put(SetttingDatabaseHelper.SETTING_PRINTER, printer);
		int update = database.update(SetttingDatabaseHelper.SETTING_TABLE_NAME, values,
                SetttingDatabaseHelper.SETTING_ID + "= ? ",
                new String[] { "1" });

        Log.d("setPrinter", update + "|Uf: " +uf + "|Printer: " + printer);

		if (update > 0) {
			return true;
		} else {
			return false;
		}

	}

	private void checkTable() {

		Cursor mcursor = database.rawQuery("SELECT count(*) FROM "
				+ SetttingDatabaseHelper.SETTING_TABLE_NAME, null);

		mcursor.moveToFirst();
		int icount = mcursor.getInt(0);
		if (icount > 0) {
			mcursor.close();
			return;
		} else {

			ContentValues values = new ContentValues();

			values.put(SetttingDatabaseHelper.SETTING_ID, "1");
			values.put(SetttingDatabaseHelper.SETTING_AUTO_BACKUP,
					AppConstants.TRUE);
			values.put(SetttingDatabaseHelper.SETTING_VIBRATOR,
					AppConstants.TRUE);
			values.put(SetttingDatabaseHelper.SETTING_RINGTONE,
					AppConstants.TRUE);
			values.put(SetttingDatabaseHelper.SETTING_FONT, AppConstants.FONT_1);
			values.put(SetttingDatabaseHelper.SETTING_PRINTER, SetttingDatabaseHelper.SETTING_PRINTER);
			values.put(SetttingDatabaseHelper.SETTING_UF, SetttingDatabaseHelper.SETTING_UF);

			database.insert(SetttingDatabaseHelper.SETTING_TABLE_NAME, null,
					values);
			mcursor.close();
		}
	}

	public synchronized void close() {
		database.close();
	}

}
