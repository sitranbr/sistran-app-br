package net.sistransito.mobile.database;

		import android.content.ContentValues;
		import android.content.Context;
		import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
		import android.database.sqlite.SQLiteDatabase;
		import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

		import net.sistransito.mobile.equipments.AitEquipmentEntry;

        import java.io.File;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.io.InputStream;
		import java.io.OutputStream;
		import java.util.ArrayList;

public class PrepopulatedDBOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "sitransitoctb.db";
	public static String DB_PATH;
	private SQLiteDatabase database;
	private Context context;

	// Infrations entry

	public static final String AIT_TABLE = "infracoes";
	public static final String AIT_ID = "_id";
	public static final String AIT_DESCRIPTION = "infracao";
	public static final String AIT_FLAMING_CODE = "enquadramento";
	public static final String AIT_UNFOLDING = "desdobramento";
	public static final String AIT_ARTICLE = "artigo";
	public static final String AIT_RESPONSIBLE = "responsavel";
	public static final String AIT_POINTS = "pontos";
	public static final String AIT_GRAVITY = "gravidade";
	public static final String AIT_AMOUNT = "valor";
	public static final String AIT_TRANSIT_AUTHORITY = "competencia";
	public static final String AIT_ADMINISTRATIVE_PROCEDURE = "medida_adm";
	public static final String AIT_OBSERVATION = "observacao";
	public static final String AIT_ANNOTATIONS = "notas";

	// Cities entry
	public static final String CITIES_TABLE = "municipios";
	public static final String CITY_NAME = "municipio";
	public static final String CITY_CODE = "cod";
	public static final String STATE = "uf";

	// Equipment entry

	public static final String EQUIPMENT_TABLE_NAME = "ait_equipment";
	public static final String EQUIPMENT_DESCRIPTION = "description";
	public static final String EQUIPMENT_BRAND = "brand";
	public static final String EQUIPMENT_MODEL = "model";
	public static final String EQUIPMENT_VALID = "validity";
	public static final String EQUIPMENT_SERIAL = "serial_number";

	public PrepopulatedDBOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;

		/*super(context, Environment.getExternalStorageDirectory()
				+ "/SistransitoMobile/data/"
				+ DB_NAME, null, 1);
		this.context = context;

		DB_PATH = Environment.getExternalStorageDirectory()
				+ "/SistransitoMobile/data/";*/

		if(android.os.Build.VERSION.SDK_INT <= 4.2){
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}

		this.database = openDatabase();

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			db.disableWriteAheadLogging();
		}
	}

	public SQLiteDatabase getDatabase() {
		return this.database;
	}

	public SQLiteDatabase openDatabase() {

		String path = DB_PATH + DB_NAME;

		if (database == null) {
			createDatabase();
			database = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
		}

		return database;
	}

	private void createDatabase() {
		boolean dbExists = checkDB();
		if (!dbExists) {
			this.getReadableDatabase();
			Log.e(getClass().getName(), "Database doesn't exist. Copying database from assets...");
			copyDatabase();
		} else {
			Log.e(getClass().getName(), "Database already exists");
		}
	}

	private void copyDatabase() {
		try {
			InputStream dbInputStream = context.getAssets().open(DB_NAME);
			String path = DB_PATH + DB_NAME;
			OutputStream dbOutputStream = new FileOutputStream(path);
			byte[] buffer = new byte[4096];
			int readCount = 0;
			while ((readCount = dbInputStream.read(buffer)) > 0) {
				dbOutputStream.write(buffer, 0, readCount);
			}

			dbInputStream.close();
			dbOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean checkDB() {
		String path = DB_PATH + DB_NAME;
		File file = new File(path);
		if (file.exists()) {
			Log.e(getClass().getName(), "Database already exists");
			return true;
		}
		Log.e(getClass().getName(), "Database does not exists");
		return false;
	}

	public synchronized void close() {
		if (this.database != null) {
			this.database.close();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// autocomplete infracao
	public Cursor getInfrationCursor() {
		Cursor myCursor = this.database.query(AIT_TABLE, null, null, null, null, null, null);
		myCursor.moveToFirst();
		return myCursor;
	}


	public Cursor getInfrationCursor(int id) {
		Cursor myCursor = this.database.query(
				AIT_TABLE, null,
				PrepopulatedDBOpenHelper.AIT_ID + "=?", new String[]{id
						+ ""}, null, null, null);
		myCursor.moveToFirst();
		return myCursor;
	}

	public Cursor getInfrationCursor(String texto) {
		Cursor myCursor = this.database.rawQuery("SELECT * FROM "
						+ AIT_TABLE
						+ " WHERE " + AIT_DESCRIPTION + " || " + AIT_ARTICLE + " like '%" + texto + "%'"
						//+ " AND " + INFRACOES_OBSERVACAO + " like '%" + texto + "%'"
				, null);
		myCursor.moveToFirst();
		return myCursor;
	}

	public String getCityNameCursor(String id) {

		String cityName = null;

		Cursor myCursor = this.database.rawQuery("SELECT * FROM "
						+ CITIES_TABLE
						+ " where " + PrepopulatedDBOpenHelper.CITY_CODE
						+ " like '%" + id + "%'"
				, null);

		if (myCursor.getCount() > 0) {
			myCursor.moveToFirst();

			cityName = (myCursor.getString(myCursor
					.getColumnIndex(CITY_NAME)));

			myCursor.close();
		} else {
			myCursor.close();
			return null;
		}

		return cityName;

	}

	public Cursor getInitialsStateCursor(String initials) {

		Cursor myCursor = this.database.rawQuery("SELECT * FROM "
						+ CITIES_TABLE
						+ " where " + PrepopulatedDBOpenHelper.STATE
						+ " like '%" + initials + "%'"
				, null);
		myCursor.moveToFirst();
		return myCursor;
		//initials = uf
	}

	// autocomplete municipio
	public Cursor getCityCursor() {
		Cursor myCursor = this.database.query(CITIES_TABLE, null,
				null, null, null, null, null);
		myCursor.moveToFirst();
		return myCursor;
	}

	// autocomplete municipio
	public Cursor getStateCursor() {
		Cursor myCursor = this.database.query(AIT_TABLE, null,
				null, null, null, null, null);
		myCursor.moveToFirst();
		return myCursor;
	}

	// AutoEquipamentoEntry

	public Cursor getEquipmentCursor() {
		Cursor myCursor = this.database.query(EQUIPMENT_TABLE_NAME,
				null, null, null, null, null, null);
		myCursor.moveToFirst();

		//Log.d("equipamentos", myCursor.getCount() + "v");
		return myCursor;
	}

	public void setEquipmentCursor(

			ArrayList<AitEquipmentEntry> entries) {

		this.database.delete(EQUIPMENT_TABLE_NAME, null, null);

		AitEquipmentEntry equipmentEntry;

		if (entries != null) {

			for (int i = 0; i < entries.size(); i++) {
				ContentValues values = new ContentValues();
				equipmentEntry = entries.get(i);
				values.put(EQUIPMENT_DESCRIPTION,
						equipmentEntry.getEquipmentDescription());
				values.put(EQUIPMENT_BRAND,
						equipmentEntry.getEquipmentBrand());
				values.put(EQUIPMENT_MODEL,
						equipmentEntry.getEquipmentModel());
				values.put(EQUIPMENT_VALID,
						equipmentEntry.getEquipmentValidate());
				values.put(EQUIPMENT_SERIAL,
						equipmentEntry.getEquipmentSerial());
				this.database.insert(EQUIPMENT_TABLE_NAME, null,
						values);

			}
		}
	}

}
