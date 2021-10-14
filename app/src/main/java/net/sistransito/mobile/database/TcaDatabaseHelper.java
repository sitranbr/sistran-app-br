package net.sistransito.mobile.database;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class TcaDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME_TCA = "database_tca.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "tca";
	public static final String COLUMN_ID = "_id";
	// Condutor/Ve�culo
	public static final String NOME_DO_CONDUTOR = "nome_do_condutor";
	public static final String CNH_PPD = "cnh_pdd";
	public static final String CPF = "cpf";
	public static final String ENDERECO = "endereco";
	public static final String BAIRRO = "bairro";
	public static final String MUNICIPIO = "municipio";
	public static final String MUNICIPIO_UF = "municipio_uf";
	public static final String PLACA = "placa";
	public static final String PLACA_UF = "placa_uf";
	public static final String MARCA_MODELO = "modelo_veiculo";
	// Question�rio

	public static final String CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO = "condutor_envolveu_transito";
	public static final String CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA = "condutor_declara_alcoolica";
	public static final String DATA_INGERIU_ALCOOL = "data_ingeriu_alcool";
	public static final String HORA_INGERIU_ALCOOL = "hora_ingeriu_alcool";
	public static final String DATA_INGERIU_SUBSTANCIA = "data_ingeriu_substancia";
	public static final String HORA_INGERIU_SUBSTANCIA = "hora_ingeriu_substancia";

	public static final String CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA = "condutor_declara_toxica";
	public static final String O_CONDUTOR_APRESENTA_SINAIS_DE = "o_condutor_sinais";
	public static final String EM_SUA_ATITUDE_OCORRE = "em_sua_atitude";
	public static final String SABE_ONDE_ESTA = "sabe_onde_esta";
	public static final String SABE_A_DATA_E_A_HORA = "sabe_data_hora";
	public static final String SABE_SEU_ENDERECO = "sabe_seu_endereco";
	public static final String LEMBRA_DOS_ATOS_COMETIDOS = "lembra_cometidos";
	public static final String EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE = "em_relaco_ocorre";
	public static final String CONCLUSAO = "conclusao";

	public static final String NUMERO_TCA = "numero_tca";
	public static final String NUMERO_AUTO = "numero_auto";

	// TCA DE TABLE SQL
	public static final String TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NOME_DO_CONDUTOR + " TEXT, " + CNH_PPD + " TEXT, " + CPF
			+ " TEXT, " + ENDERECO + " TEXT, " + BAIRRO + " TEXT, " + MUNICIPIO
			+ " TEXT, " + MUNICIPIO_UF + " TEXT, " + PLACA + " TEXT, "
			+ PLACA_UF + " TEXT, " + MARCA_MODELO + " TEXT, "
			+ CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO + " TEXT, "
			+ CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA + " TEXT, "
			+ DATA_INGERIU_ALCOOL + " TEXT, " + HORA_INGERIU_ALCOOL + " TEXT, "
			+ CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA + " TEXT, "
			+ DATA_INGERIU_SUBSTANCIA + " TEXT, " + HORA_INGERIU_SUBSTANCIA
			+ " TEXT, " + O_CONDUTOR_APRESENTA_SINAIS_DE + " TEXT, "
			+ EM_SUA_ATITUDE_OCORRE + " TEXT, " + SABE_ONDE_ESTA + " TEXT, "
			+ SABE_A_DATA_E_A_HORA + " TEXT, " + SABE_SEU_ENDERECO + " TEXT, "
			+ LEMBRA_DOS_ATOS_COMETIDOS + " TEXT, "
			+ EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE + " TEXT, "
			+ CONCLUSAO + " TEXT, " + NUMERO_TCA + " TEXT UNIQUE , "
			+ NUMERO_AUTO + " TEXT )";

	public TcaDatabaseHelper(Context context) {
		super(context, DATABASE_NAME_TCA, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("TABLE SQL", TABLE_SQL);
		db.execSQL(TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// UPGRADE LOGIC

	}

}