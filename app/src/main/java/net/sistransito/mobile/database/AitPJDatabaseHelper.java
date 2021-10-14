package net.sistransito.mobile.database;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class AitPJDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_PJ_AIT = "auto_pj_database.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "auto_pj";

    // conductor TAB
    public static final String COLUMN_ID = "_id";
    public static final String AIT_NUMBER = "ait";
    public static final String COMPANY_SOCIAL = "nome_razao_social";
    public static final String CPF = "cpf";
    public static final String CNPJ = "cnpj";
    public static final String ADDRESS = "endereco";

    // address TAB

    public static final String CITY = "municipio";
    public static final String STATE = "uf";
    public static final String PLACE = "local";
    public static final String DATE = "data";
    public static final String TIME = "hora";

    // infration TAB
    public static final String INFRATION = "infracao";
    public static final String FLAMING_CODE = "enquadramento";
    public static final String UNFOLDING = "desdobramento";
    public static final String ARTICLE = "artigo";
    public static final String OBSERVATION = "observacao";
    public static final String CANCELL_STATUS = "status_cancelamento";
    public static final String CANCELL_REASON = "motivo_cancelamento";
    public static final String SYNC_STATUS = "status_sincronizacao";
    public static final String AIT_LATITUDE = "latitude";
    public static final String AIT_LONGITUDE = "longitude";
    public static final String AIT_DATE_TIME = "data_hora_ait";
    public static final String COMPLETED_STATUS = "status_concluido";

    // SQL TABLE

    public static final String TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AIT_NUMBER + " TEXT UNIQUE, " + COMPANY_SOCIAL + " TEXT, "
            + CPF + " TEXT, " + CNPJ + " TEXT, " + ADDRESS + " TEXT, " + CITY + " TEXT, "
            + STATE + " TEXT, " + PLACE + " TEXT, " + DATE + " TEXT, "
            + TIME + " TEXT, " + INFRATION + " TEXT, " + FLAMING_CODE + " TEXT, "
            + UNFOLDING + " TEXT, " + ARTICLE + " TEXT, " + OBSERVATION + " TEXT, "
            + CANCELL_STATUS + " TEXT, " + CANCELL_REASON + " TEXT, "
            + SYNC_STATUS + " TEXT, " + AIT_LATITUDE + " TEXT, "
            + AIT_LONGITUDE + " TEXT, " + AIT_DATE_TIME + " TEXT, "
            + COMPLETED_STATUS + " TEXT )";

    public AitPJDatabaseHelper(Context context) {
        super(context, DATABASE_PJ_AIT, null, VERSION);
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
