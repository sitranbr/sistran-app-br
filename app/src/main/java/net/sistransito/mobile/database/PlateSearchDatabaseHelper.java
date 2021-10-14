package net.sistransito.mobile.database;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlateSearchDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_VEHICLE_NAME = "database_veiculos.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "veiculos";
	public static final String COLUMN_ID = "_id";
	public static final String PLATE = "placa";
	public static final String STATE = "uf";
	public static final String COUNTRY = "pais";
	public static final String CHASSIS = "chassi";
	public static final String BRAND = "marca";
	public static final String MODEL = "modelo";
	public static final String COLOR = "cor";
	public static final String TYPE = "tipo";
	public static final String CATEGORY = "categoria";
	public static final String LICENSING_YEAR = "ano_licenciamento";
	public static final String LICENSING_DATE = "data_licenciamento";
	public static final String LICENSING_STATUS = "status_licenciamento";
	public static final String IPVA = "ipva";
	public static final String INSURANCE = "seguro";
	public static final String STATUS = "status";
	public static final String INFRATIONS = "infracoes";
	public static final String RESTRICTIONS = "restricoes";
	public static final String DATE = "date";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	
	
	private final String TABLE_SQL = "CREATE TABLE [veiculos] (_id integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,placa text,uf text,pais text DEFAULT 'Brasil',chassi text, marca text," +
			"modelo text,cor text,tipo text,categoria text, ano_licenciamento text,data_licenciamento text,status_licenciamento text,ipva text,seguro text," +
			"status text,infracoes text,restricoes text,date text,latitude text,longitude text)";

	public PlateSearchDatabaseHelper(Context context) {
		super(context, DATABASE_VEHICLE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
