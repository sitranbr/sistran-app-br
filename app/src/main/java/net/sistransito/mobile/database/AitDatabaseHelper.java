package net.sistransito.mobile.database;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AitDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "database_auto.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "auto_infracao";

	// conductor TAB
	public static final String COLUMN_ID = "_id";
	public static final String AIT_NUMBER = "ait";
	public static final String PLATE = "placa";
	public static final String VEHICLE_STATE = "uf_veiculo";
	public static final String CHASSI = "chassi";
	public static final String VEHICLE_BRAND = "marca_veiculo";
	public static final String VEHICLE_MODEL = "modelo_veiculo";
	public static final String COUNTRY = "pais";
	public static final String VEHICLE_COLOR = "cor_veiculo";
	public static final String SPECIES = "especie";
	public static final String CATEGORY = "categoria";
	public static final String APPROACH = "abordagem";
	public static final String DRIVER_NAME = "condutor";
	public static final String DRIVER_FOREIGN = "condutor_estrangeiro";
	public static final String DRIVER_COUNTRY = "pais_condutor";
	public static final String ENABLED_DRIVER = "condutor_habilitado";
	public static final String DRIVER_LICENSE = "cnh_pdd";
	public static final String DRIVER_LICENSE_STATE = "uf_cnh";
	public static final String DOCUMENT_TYPE = "tipo_documento";
	public static final String DOCUMENT_NUMBER = "numero_documento";

	// infration TAB
	public static final String INFRATION = "infracao";
	public static final String FLAMING_CODE = "enquadramento";
	public static final String UNFOLDING = "desdobramento";
	public static final String ARTICLE = "artigo";
	public static final String CITY_CODE = "codigo_muncipio";
	public static final String CITY = "municipio";
	public static final String STATE = "uf";
	public static final String ADDRESS = "local";
	public static final String AIT_DATE = "data";
	public static final String AIT_TIME = "hora";
	public static final String EQUIPMENT_DESCRIPTION = "descricao_equipamento";
	public static final String EQUIPMENT_BRAND = "marca_equipamento";
	public static final String EQUIPMENT_MODEL = "modelo_equipamento";
	public static final String EQUIPMENT_SERIAL = "numero_de_serie";
	public static final String MEASUREMENT_PERFORMED = "medicao_realizada";
	public static final String REGULATED_VALUE = "valor_regulamentado";
	public static final String VALUE_CONSIDERED = "valor_considerada";
	public static final String ALCOHOL_TEST_NUMBER = "numero_amostra";
	public static final String TCA_NUMBER = "numero_tca";

	// generation TAB ,
	public static final String RETREAT = "recolhimento";
	public static final String PROCEDURES = "procedimentos";
	public static final String OBSERVATION = "observacao";
	public static final String SHIPPER_IDENTIFICATION = "embarcador";
	public static final String CPF_SHIPPER = "cpf_embarcador";
	public static final String CNPJ_SHIPPER = "cnpj_embarcador";
	public static final String CARRIER_IDENTIFICATION = "transportador";
	public static final String CPF_CARRIER = "cpf_transportador";
	public static final String CNPJ_CARRIER = "cnpj_transportador";
	public static final String CANCELL_STATUS = "status_cancelamento";
	public static final String REASON_FOR_CANCELL = "motivo_cancelamento";
	public static final String SYNC_STATUS = "status_sincronizacao";
	public static final String AIT_LATITUDE = "latitude";
	public static final String AIT_LONGITUDE = "longitude";
	public static final String AIT_DATE_TIME = "data_hora_ait";
	public static final String AIT_PHOTO1 = "foto1";
	public static final String AIT_PHOTO2 = "foto2";
	public static final String AIT_PHOTO3 = "foto3";
	public static final String AIT_PHOTO4 = "foto4";
	public static final String COMPLETED_STATUS = "status_concluido";
	public static final String TIME_CREATION = "hora_criacao";
	public static final String TIME_LIMIT = "hora_limite";

	// AUTO DE TABLE SQL

	public static final String TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ AIT_NUMBER + " TEXT UNIQUE, " + PLATE + " TEXT, " + VEHICLE_STATE + " TEXT, "
			+ CHASSI + " TEXT, " + COUNTRY + " TEXT, " + VEHICLE_BRAND + " TEXT, "
			+ VEHICLE_MODEL + " TEXT, " + VEHICLE_COLOR + " TEXT, "
			+ SPECIES + " TEXT, " + CATEGORY + " TEXT, " + APPROACH + " TEXT, "
			+ DRIVER_NAME + " TEXT, " + DRIVER_FOREIGN + " TEXT, "
			+ DRIVER_COUNTRY + " TEXT, " + ENABLED_DRIVER + " TEXT, "
			+ DRIVER_LICENSE + " TEXT, " + DRIVER_LICENSE_STATE + " TEXT, "
			+ DOCUMENT_TYPE + " TEXT, " + DOCUMENT_NUMBER + " TEXT, "
			+ INFRATION + " TEXT, " + FLAMING_CODE + " TEXT, " + UNFOLDING + " TEXT, "
			+ ARTICLE + " TEXT, " + TCA_NUMBER + " TEXT, " + CITY_CODE + " TEXT, "
			+ CITY + " TEXT, " + STATE + " TEXT, "
			+ ADDRESS + " TEXT, " + AIT_DATE + " TEXT, "
			+ AIT_TIME + " TEXT, " + EQUIPMENT_DESCRIPTION + " TEXT, " + EQUIPMENT_BRAND + " TEXT, "
			+ EQUIPMENT_MODEL + " TEXT, " + EQUIPMENT_SERIAL + " TEXT, "
			+ MEASUREMENT_PERFORMED + " TEXT, " + REGULATED_VALUE + " TEXT, " + VALUE_CONSIDERED + " TEXT, "
			+ ALCOHOL_TEST_NUMBER + " TEXT, " + RETREAT + " TEXT, "
			+ PROCEDURES + " TEXT, " + OBSERVATION + " TEXT, "
			+ SHIPPER_IDENTIFICATION + " TEXT, "
			+ CPF_SHIPPER + " TEXT, "
			+ CNPJ_SHIPPER + " TEXT, "
			+ CARRIER_IDENTIFICATION + " TEXT, "
			+ CPF_CARRIER + " TEXT,"
			+ CNPJ_CARRIER + " TEXT,"
			+ CANCELL_STATUS + " TEXT, "
			+ REASON_FOR_CANCELL + " TEXT, "
			+ SYNC_STATUS + " TEXT, "
			+ AIT_LATITUDE + " TEXT, "
			+ AIT_LONGITUDE + " TEXT, "
			+ AIT_DATE_TIME + " TEXT, "
			+ AIT_PHOTO1 + " TEXT, "
			+ AIT_PHOTO2 + " TEXT, "
			+ AIT_PHOTO3 + " TEXT, "
			+ AIT_PHOTO4 + " TEXT, "
			+ COMPLETED_STATUS + " TEXT, "
			+ TIME_CREATION + " TEXT, "
			+ TIME_LIMIT + " TEXT )";

	public AitDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Log.d("TABLE SQL", TABLE_SQL);
		db.execSQL(TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// UPGRADE LOGIC
	}

}