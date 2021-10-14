package net.sistransito.mobile.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;

public class SetttingDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME_SETTING = "database_setting.db";
	public static final int VERSION = 1;
	public static final String SETTING_TABLE_NAME = "setting";
	public static final String SETTING_ID = "_id";
	public static final String SETTING_AUTO_BACKUP = "auto_backup";
	public static final String SETTING_LANGUAGE = "language";
	public static final String SETTING_VIBRATOR = "vibrator";
	public static final String SETTING_RINGTONE = "ringtone";
	public static final String SETTING_FONT = "font";
	public static final String SETTING_UF = "uf";
	public static final String SETTING_PRINTER = "printer";

	public final String SETTING_TABLE_SQL = "CREATE TABLE [setting] (_id integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
			"auto_backup text DEFAULT false,language text,vibrator text DEFAULT true," +
			"ringtone text DEFAULT true,font text DEFAULT 1,uf text DEFAULT PA,printer text DEFAULT MPT_III)";

	public SetttingDatabaseHelper(Context context) {
		super(context, DATABASE_NAME_SETTING, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SETTING_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
