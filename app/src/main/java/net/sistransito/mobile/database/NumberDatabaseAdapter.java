package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.timeandime.TimeAndIme;
//import net.sqlcipher.database.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NumberDatabaseAdapter {
	private TimeAndIme ime;
	private SQLiteDatabase database;
	private NumberDatabaseHelper databaseHelper;
	private final int CHECK_NUM_SIZE = 10;

	public NumberDatabaseAdapter(Context context) {
		ime = new TimeAndIme(context);
		databaseHelper = new NumberDatabaseHelper(context);
		//database = databaseHelper.getWritableDatabase(ime.getIME());
		database = databaseHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public boolean deleteData(String table_name, String colume_name, String data) {
		return this.database.delete(table_name, colume_name + "=?",
				new String[] { data }) > 0;
	}

	public boolean deleteAitNumber(String data) {
		return deleteData(NumberDatabaseHelper.AIT_NUMBER_TABLE,
				NumberDatabaseHelper.AIT_NUMBER, data);
	}

	public boolean deleteTcaNumber(String data) {
		return deleteData(NumberDatabaseHelper.TCA_NUMBER_TABLE,
				NumberDatabaseHelper.TCA_NUMBER, data);
	}

	public boolean deleteTavNumber(String data) {
		return deleteData(NumberDatabaseHelper.TAV_NUMBER_TABLE,
				NumberDatabaseHelper.TAV_NUMBER, data);
	}

	public boolean deleteRrdNumber(String data) {
		return deleteData(NumberDatabaseHelper.RRD_NUMBER_TABLE,
				NumberDatabaseHelper.RRD_NUMBER, data);
	}

	private String getNumber(String tableName, String filedName) {
		Cursor myCursor = this.database.query(tableName, null, null, null,
				null, null, filedName + " DESC");
		if (myCursor.getCount() > 0) {
			myCursor.moveToLast();
			String num = myCursor
					.getString(myCursor.getColumnIndex(filedName));
			myCursor.close();
			return num;
		} else {
			myCursor.close();
			return null;
		}
	}

	public String getAitNumber() {
		return getNumber(NumberDatabaseHelper.AIT_NUMBER_TABLE,
				NumberDatabaseHelper.AIT_NUMBER);
	}

	public String geTavNumber() {

		return getNumber(NumberDatabaseHelper.TAV_NUMBER_TABLE,
				NumberDatabaseHelper.TAV_NUMBER);
	}

	public String getTcaNumber() {

		return getNumber(NumberDatabaseHelper.TCA_NUMBER_TABLE,
				NumberDatabaseHelper.TCA_NUMBER);
	}

	public String getRrdNumber() {

		return getNumber(NumberDatabaseHelper.RRD_NUMBER_TABLE,
				NumberDatabaseHelper.RRD_NUMBER);
	}

	private boolean updateTable(String tableName, String clume_name,
			ArrayList<String> list) {
		this.database.delete(tableName, null, null);
		long count = 0;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ContentValues values = new ContentValues();
				values.put(clume_name, list.get(i));
				count += this.database.insert(tableName, null, values);
			}
		}
		return count > 0;
	}

	public boolean setAitNumber(ArrayList<String> list) {

		return updateTable(NumberDatabaseHelper.AIT_NUMBER_TABLE,
				NumberDatabaseHelper.AIT_NUMBER, list);
	}

	public boolean setTavNumber(ArrayList<String> list) {

		return updateTable(NumberDatabaseHelper.TAV_NUMBER_TABLE,
				NumberDatabaseHelper.TAV_NUMBER, list);
	}

	public boolean setTcaNumber(ArrayList<String> list) {

		return updateTable(NumberDatabaseHelper.TCA_NUMBER_TABLE,
				NumberDatabaseHelper.TCA_NUMBER, list);
	}

	public boolean setRrdNumber(ArrayList<String> list) {

		return updateTable(NumberDatabaseHelper.RRD_NUMBER_TABLE,
				NumberDatabaseHelper.RRD_NUMBER, list);
	}

	private boolean getcheck(String table_name) {
		Cursor myCursor = this.database.query(table_name, null, null, null,
				null, null, null);
		if (myCursor.getCount() <= CHECK_NUM_SIZE) {
			myCursor.close();
			return true;
		} else {
			myCursor.close();
			return false;
		}
	}

	public boolean isNeedUpdateAit() {
		return getcheck(NumberDatabaseHelper.AIT_NUMBER_TABLE);
	}

	public boolean isNeedTavUpdate() {
		return getcheck(NumberDatabaseHelper.TAV_NUMBER_TABLE);
	}

	public boolean isNeedTcaUpdate() {
		return getcheck(NumberDatabaseHelper.TCA_NUMBER_TABLE);
	}

	public boolean isNeedRrdUpdate() {
		return getcheck(NumberDatabaseHelper.RRD_NUMBER_TABLE);
	}

	public int getRemainNumberRRD() {
		return getNumber(NumberDatabaseHelper.RRD_NUMBER_TABLE);

	}

	public int getRemainAitNumber() {
		return getNumber(NumberDatabaseHelper.AIT_NUMBER_TABLE);

	}

	public int getRemainTcaNumber() {
		return getNumber(NumberDatabaseHelper.TCA_NUMBER_TABLE);

	}

	public int getRemainTavNumber() {
		return getNumber(NumberDatabaseHelper.TAV_NUMBER_TABLE);

	}

	private int getNumber(String tableName) {
		Cursor myCursor = this.database.query(tableName, null, null, null,
				null, null, null);
		if (myCursor != null) {
			int number = myCursor.getCount();
			myCursor.close();
			return number;
		} else {
			return 0;
		}

	}

}
