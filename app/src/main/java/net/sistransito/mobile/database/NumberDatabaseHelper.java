package net.sistransito.mobile.database;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;

public class NumberDatabaseHelper extends SQLiteOpenHelper {

	// DATABASE NAME
	public static final String DATABASE_NUMBER_NAME = "database_numeracao.db";
	public static final int VERSION = 1;
	// ID FILED
	public static final String COLUNM_ID = "_id";
	// TABLE NAME
	public static final String AIT_NUMBER_TABLE = "tb_numero_auto";
	public static final String TAV_NUMBER_TABLE = "tb_numero_tav";
	public static final String TCA_NUMBER_TABLE = "tb_numero_tca";
	public static final String RRD_NUMBER_TABLE = "tb_numero_rrd";

	// FIELD
	public static final String AIT_NUMBER = "numero_auto";
	public static final String TAV_NUMBER = "numero_tav";
	public static final String TCA_NUMBER = "numero_tca";
	public static final String RRD_NUMBER = "numero_rrd";
	

	public static final String TABLE_SQl_AUTO = "CREATE TABLE "
			+ AIT_NUMBER_TABLE + " (" + COLUNM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + AIT_NUMBER + " TEXT )";

	public static final String TABLE_SQl_TAV = "CREATE TABLE "
			+ TAV_NUMBER_TABLE + " (" + COLUNM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAV_NUMBER + " TEXT )";
	public static final String TABLE_SQl_TCA = "CREATE TABLE "
			+ TCA_NUMBER_TABLE + " (" + COLUNM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TCA_NUMBER +  " TEXT )";
	public static final String TABLE_SQl_RRD = "CREATE TABLE "
			+ RRD_NUMBER_TABLE + " (" + COLUNM_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + RRD_NUMBER + " TEXT )";

	public NumberDatabaseHelper(Context context) {
		super(context, DATABASE_NUMBER_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_SQl_AUTO);
		db.execSQL(TABLE_SQl_TAV);
		db.execSQL(TABLE_SQl_TCA);
		db.execSQL(TABLE_SQl_RRD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
