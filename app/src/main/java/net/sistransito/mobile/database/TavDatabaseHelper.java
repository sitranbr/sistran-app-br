package net.sistransito.mobile.database;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class TavDatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_TAV_NAME = "database_tav.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "tav";
	public static final String COLUMN_ID = "_id";

	public static final String PLATE = "placa";

	// Conductor
	public static final String AIT_NUMBER = "numero_do_auto";
	public static final String TAV_NUMBER = "numero_tav";
	public static final String OWNER_NAME = "nome_do_proprietario";
	public static final String CPF_CNPJ = "cpf_cnpj";
	public static final String RENAVAM_NUMBER = "numero_do_renavam";
	public static final String CHASSI_NUMBER = "numero_do_chassi";

	// Vehicle
	// Structure
	public static final String CABECA_DE_ALAVANCA = "cabeca_de_alavanca";
	public static final String CARROCERIA = "carroceria";
	public static final String FORRO = "forro";
	public static final String LATARIA_CAPO = "lataria_capo";
	public static final String LATARIA_LADO_DIREITO = "lataria_lado_direito";
	public static final String LATARIA_LADO_ESQUERDO = "lataria_lado_esquerdo";
	public static final String LATARIA_TAPA_PORTA_MALA = "lataria_tapa_porta_mala";
	public static final String LATARIA_TETO = "lataria_teto";
	public static final String MOTOR = "motor";
	public static final String PAINEL = "painel";
	public static final String PINTURA_CAPO = "pintura_capo";
	public static final String PINTURA_LADO_DIREITO = "pintura_lado_direito";
	public static final String PINTURA_LADO_ESQUERDO = "pintura_lado_esquerdo";
	public static final String PINTURA_PORTA_MALA = "pintura_porta_mala";
	public static final String PINTURA_TETO = "pintura_teto";
	public static final String RADIADOR = "radiador";
	public static final String VIDROS_LATERAIS = "vidros_laterais";
	public static final String VIDRO_PARA_BRISA = "vidro_para_brisa";
	public static final String VIDRO_TRASEIRO = "vidro_traseiro";

	// Vehicle
	// Acccessories
	public static final String ANTENA_DE_RADIO = "antena_de_radio";
	public static final String BAGAGEIRO = "bagageiro";
	public static final String BANCOS = "bancos";
	public static final String BATERIA = "bateria";
	public static final String CALOTA = "calota";
	public static final String CONDICIONADOR_DE_AR = "condicionador_de_ar";
	public static final String EXTINTOR_DE_INCENDIO = "extintor_de_incendio";
	public static final String FAROLETE_DIANTEIRO = "farolete_dianteiro";
	public static final String FAROLETE_TRASEIRO = "farolete_traseiro";
	public static final String MACACO = "macaco";
	public static final String PARA_CHOQUE_DIANTEIRO = "para_choque_dianteiro";
	public static final String PARA_CHOQUE_TRASEIRO = "para_choque_traseiro";
	public static final String PARA_SOL_DO_CONDUTOR = "para_sol_do_condutor";
	public static final String PNEUS = "pneus";
	public static final String PNEUS_ESTEPE = "pneus_estepe";
	public static final String RADIO = "radio";
	public static final String RETROVISOR_INTERNO = "retrovisor_interno";
	public static final String RETROVISOR_EXTERNO_DIREITO = "retrovisor_externo_direito";
	public static final String TAPETE = "tapete";
	public static final String TRIANGULO = "triangulo";
	public static final String VOLANTE = "volante";
	public static final String GUIDAM = "guidam";

	// Geral
	public static final String ODOMETRO = "odometro";
	public static final String MARCADOR_DE_CONBUTIVEL = "marcador_de_conbutivel";
	public static final String REMOCAO_ATRAVES_DE = "remocao_atraves_de";
	public static final String OBSERVACAO = "observacao";

	public static final String NOME_DA_EMPRESA = "nome_da_empresa";
	public static final String NOME_DO_CONDUTOR_DO_GUINCHO = "nome_condutor_guincho";

	// TCA DE TABLE SQL
	public static final String TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PLATE
			+ " TEXT, " + AIT_NUMBER + " TEXT, " + OWNER_NAME
			+ " TEXT, " + CPF_CNPJ + " TEXT, " + RENAVAM_NUMBER + " TEXT, "
			+ CHASSI_NUMBER + " TEXT, " + CABECA_DE_ALAVANCA + " TEXT, "
			+ CARROCERIA + " TEXT, " + FORRO + " TEXT, " + LATARIA_CAPO
			+ " TEXT, " + LATARIA_LADO_DIREITO + " TEXT, "
			+ LATARIA_LADO_ESQUERDO + " TEXT, " + LATARIA_TAPA_PORTA_MALA
			+ " TEXT, " + LATARIA_TETO + " TEXT, " + MOTOR + " TEXT, " + PAINEL
			+ " TEXT, " + PINTURA_CAPO + " TEXT, " + PINTURA_LADO_DIREITO
			+ " TEXT, " + PINTURA_LADO_ESQUERDO + " TEXT, "
			+ PINTURA_PORTA_MALA + " TEXT, " + PINTURA_TETO + " TEXT, "
			+ RADIADOR + " TEXT, " + VIDROS_LATERAIS + " TEXT, "
			+ VIDRO_PARA_BRISA + " TEXT, " + VIDRO_TRASEIRO + " TEXT, "
			+ ANTENA_DE_RADIO + " TEXT, " + BAGAGEIRO + " TEXT, " + BANCOS
			+ " TEXT, " + BATERIA + " TEXT, " + CALOTA + " TEXT, "
			+ CONDICIONADOR_DE_AR + " TEXT, " + EXTINTOR_DE_INCENDIO
			+ " TEXT, " + FAROLETE_DIANTEIRO + " TEXT, " + FAROLETE_TRASEIRO
			+ " TEXT, " + MACACO + " TEXT, " + PARA_CHOQUE_DIANTEIRO
			+ " TEXT, " + PARA_CHOQUE_TRASEIRO + " TEXT, "
			+ PARA_SOL_DO_CONDUTOR + " TEXT, " + PNEUS + " TEXT, "
			+ PNEUS_ESTEPE + " TEXT, " + RADIO + " TEXT, " + RETROVISOR_INTERNO
			+ " TEXT, " + RETROVISOR_EXTERNO_DIREITO + " TEXT, " + TAPETE
			+ " TEXT, " + TRIANGULO + " TEXT, " + VOLANTE + " TEXT, " + GUIDAM
			+ " TEXT, " + ODOMETRO + " TEXT, " + MARCADOR_DE_CONBUTIVEL
			+ " TEXT, " + REMOCAO_ATRAVES_DE + " TEXT, " + NOME_DA_EMPRESA
			+ " TEXT, " + NOME_DO_CONDUTOR_DO_GUINCHO + " TEXT, " + OBSERVACAO
			+ " TEXT, " + TAV_NUMBER + " TEXT UNIQUE )";

	public TavDatabaseHelper(Context context) {
		super(context, DATABASE_TAV_NAME, null, VERSION);
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