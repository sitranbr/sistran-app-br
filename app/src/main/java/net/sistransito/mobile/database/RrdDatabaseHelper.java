package net.sistransito.mobile.database;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class RrdDatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME_RRD = "database_rrd.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "tb_rrd";
	public static final String COLUMN_ID = "_id";
	public static final String RRD_NUMBER = "numero_rrd";
	public static final String AIT_NUMBER = "numero_auto";
	public static final String DRIVER_NAME = "nome_condutor";
	public static final String DOCUMENT_TYPE = "documento";
	public static final String CRLV_NUMBER = "numero_crlv";
	public static final String CNH_NUMBER = "numero_cnh";
	public static final String CNH_STATE = "uf_cnh";
	public static final String CNH_VALIDITY = "validade_cnh";
	public static final String PLATE = "placa";
	public static final String PLATE_STATE = "uf_placa";
	public static final String REASON_FOR_COLLECTION = "motivo_recolhimento";
	public static final String DAYS_FOR_REGULARIZATION = "qtd_de_dias";
	public static final String COLLECTION_DATE = "data";
	public static final String COLLECTION_TIME = "hora";
	public static final String COLLECTION_CITY = "municipio";
	public static final String COLLECTION_STATE = "uf";
	public static final String RRD_TYPE = "tipo_rrd";

	public static final String RRD_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
			+ " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ RRD_NUMBER + " TEXT UNIQUE, "
			+ DRIVER_NAME + " TEXT, "
			+ DOCUMENT_TYPE + " TEXT, "
			+ CRLV_NUMBER + " TEXT, "
			+ CNH_NUMBER + " TEXT, "
			+ CNH_STATE + " TEXT, "
			+ CNH_VALIDITY + " TEXT, "
			+ PLATE + " TEXT, "
			+ PLATE_STATE + " TEXT, "
			+ REASON_FOR_COLLECTION + " TEXT, "
			+ AIT_NUMBER + " TEXT, "
			+ DAYS_FOR_REGULARIZATION + " TEXT, "
			+ COLLECTION_DATE + " TEXT, "
			+ COLLECTION_TIME + " TEXT, "
			+ COLLECTION_CITY + " TEXT, "
			+ COLLECTION_STATE + " TEXT, "
			+ RRD_TYPE + " TEXT)";

	/*public RRDDatabaseHelper(Context context) {
		super(context, TABLE_NAME, null, VERSION);

	}*/

	public RrdDatabaseHelper(Context context) {
		super(context, DATABASE_NAME_RRD, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Log.d("TABLE SQL", RRD_TABLE_SQL);
		db.execSQL(RRD_TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// UPGRADE LOGIC

	}
}
