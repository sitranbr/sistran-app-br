package net.sistransito.mobile.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import net.sistransito.mobile.cnh.dados.DataFromCnh;
import net.sistransito.mobile.plate.data.DataFromPlate;
import net.sistransito.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.StringTokenizer;

public class SearchDataInCard extends SQLiteOpenHelper {

    public static final String DB_NAME = "talonariopara.db";
    public static String DB_PATH;
    private SQLiteDatabase database;
    private final Context context;

    // Vehicle data

    public static final String TABLE_NAME = "VEICULO_RENAVAM";
    public static final String COLUMN_ID = "ID";
    public static final String PLATE = "PLACA";
    public static final String CHASSIS = "CHASSI";
    public static final String STATE = "ID_ESTADO_VEICULO";
    public static final String CITY = "ID_MUNICIPIO";
    public static final String RENAVAM = "RENAVAM";
    public static final String IDENTITY_OWNER = "NUMERO_IDENT_PROPRIETARIO";
    public static final String VEHICLE_OWNER = "NM_PROPRIETARIO";
    public static final String MODEL = "NM_MARCA_MODELO";
    public static final String COLOR = "COR";
    public static final String SPECIES = "ID_ESPECIE";
    public static final String TYPE = "TIPO_VEICULO";
    public static final String CATEGORY = "CATEGORIA";
    public static final String LICENSING_YEAR = "ANO_LICENCIADO";
    public static final String LICENSING_DATE = "DTH_ULT_LICENC";
    public static final String MODEL_YEAR = "ANO_MODELO";
    public static final String YEAR_MANUFACTURE = "ANO_FABRICACAO";
    public static final String LICENSING_STATUS = "INDICADOR_LICENCIAMENTO";
    public static final String RETENTION_INDICATOR = "INDICADOR_RETENCAO_PARQUE";
    public static final String IMPEDIMENT_INDICATOR = "INDICADOR_IMPEDIMENTO_JUDICIAL_ADMIN";
    public static final String OCCURRENCE_INDICATOR = "INDICADOR_OCORRENCIA_POLICIAL";
    public static final String NUMBER_SEAL = "NUMERO_LACRE";
    public static final String NUMBER_ENGINER = "NUMERO_MOTOR";
    public static final String DOUBLE = "DUBLE";

    //CNH data

    public static final String TABLE_RENACH = "CONDUTOR_RENACH";
    public static final String CNH_ID = "ID";
    public static final String CNH_NAME = "NM_CONDUTOR";
    public static final String CNH_NUMBER = "CNH";
    public static final String CNH_CPF = "CPF";
    public static final String CNH_MATHERS_NAME = "NM_MAE";
    public static final String CNH_BIRTHDAY = "DTH_NASCIMENTO";
    public static final String CNH_REGISTER = "RG";
    public static final String CNH_STATE = "ID_ESTADO";
    public static final String CNH_VALIDITY = "DTH_VALID_CNH";
    public static final String CNH_CATEGORY = "CATEGORIA";
    public static final String CNH_SITUATION = "SITUACAO_CNH";
    public static final String CNH_POINTS = "PONTUACAO_ATIVA";
    public static final String CNH_BLOCK = "BLOQUEIO_ATIVO";


    public SearchDataInCard(Context context) {
        super(context, Environment.getExternalStorageDirectory() + "/"
                + DB_NAME, null, 1);
        this.context = context;

        DB_PATH = Environment.getExternalStorageDirectory() + "/";

        this.database = openDatabase();

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
            int readCount;
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

    // offLine searchPlate
    @SuppressLint("DefaultLocale")
    public DataFromPlate getPlateData(String sPlate, String searchType) {

        DataFromPlate dataPlate = new DataFromPlate(context);

        Cursor myCursor = null;

        String fieldToSearch = PLATE;
        String indexedField = "placas";

        switch (searchType) {
            case "plate":
                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_NAME
                                + " INDEXED BY " + indexedField + " WHERE " + fieldToSearch + "=?",
                        new String[]{sPlate}, null);
                break;
            case "chassis":
                fieldToSearch = CHASSIS;
                indexedField = "chassi";

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_NAME
                                + " INDEXED BY " + indexedField + " WHERE " + fieldToSearch + "=?",
                        new String[]{sPlate}, null);
                break;
            case "plateSeal":
                fieldToSearch = NUMBER_SEAL;
                indexedField = "lacre";

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_NAME
                                + " INDEXED BY " + indexedField + " WHERE " + fieldToSearch + "=?",
                        new String[]{sPlate}, null);
                break;
            case "motor":
                fieldToSearch = NUMBER_ENGINER;
                indexedField = "motor";

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_NAME
                                + " INDEXED BY " + indexedField + " WHERE " + fieldToSearch + "=?",
                        new String[]{sPlate}, null);
                break;
            case "vis":
                fieldToSearch = CHASSIS;
                indexedField = "chassi";

                String query = "SELECT * FROM "
                        + TABLE_NAME
                        + " INDEXED BY " + indexedField
                        + " WHERE " + fieldToSearch + " LIKE '%" + sPlate + "'";
                myCursor = this.database.rawQuery(query, null);
                break;
        }

        //if (myCursor > 0) {
        if ((myCursor != null ? myCursor.getCount() : 0) > 0) {

            myCursor.moveToFirst();

            dataPlate.setPlate(myCursor.getString(myCursor
                    .getColumnIndex(PLATE)));

            dataPlate.setState(filter(myCursor.getString(myCursor
                    .getColumnIndex(STATE)), STATE));

            dataPlate.setCity(getCityName(myCursor.getString(myCursor
                    .getColumnIndex(CITY))));

            dataPlate.setChassi(myCursor.getString(myCursor
                    .getColumnIndex(CHASSIS)));

            dataPlate.setRanavam(myCursor.getString(myCursor
                    .getColumnIndex(RENAVAM)));

            StringTokenizer tokens = new StringTokenizer(myCursor.getString(myCursor
                    .getColumnIndex(MODEL)), "/");

            String sBrand = tokens.nextToken();
            String sModel = tokens.nextToken();

            dataPlate.setBrand(sBrand);
            dataPlate.setModel(sModel);

            dataPlate.setColor(filter(myCursor.getString(myCursor
                    .getColumnIndex(COLOR)), COLOR));

            dataPlate.setSpecies(filter(myCursor.getString(myCursor
                    .getColumnIndex(SPECIES)), SPECIES));

            //String tipo = filter(myCursor.getString(myCursor.getColumnIndex(COLUMN_TIPO)), PrepopulatedDBOpenHelper.COLUMN_TIPO);
            dataPlate.setType(filter(myCursor.getString(myCursor
                    .getColumnIndex(TYPE)), TYPE));

            dataPlate.setCategory(filter(myCursor.getString(myCursor
                    .getColumnIndex(CATEGORY)), CATEGORY));

            dataPlate.setOwnerId(myCursor.getString(myCursor
                    .getColumnIndex(IDENTITY_OWNER)));

            dataPlate.setOwnerName(myCursor.getString(myCursor
                    .getColumnIndex(VEHICLE_OWNER)));

            dataPlate.setLicenceYear(myCursor.getString(myCursor
                    .getColumnIndex(LICENSING_YEAR)));

            dataPlate.setLicenceData(myCursor.getString(myCursor
                    .getColumnIndex(LICENSING_DATE)));

            dataPlate.setLicenceStatus(filter(myCursor.getString(myCursor
                    .getColumnIndex(LICENSING_STATUS)), LICENSING_STATUS));

            dataPlate.setYearModel(myCursor.getString(myCursor
                    .getColumnIndex(MODEL_YEAR)));

            dataPlate.setYearManufacture(myCursor.getString(myCursor
                    .getColumnIndex(YEAR_MANUFACTURE)));

            dataPlate.setRetentionIndication(myCursor.getString(myCursor
                    .getColumnIndex(RETENTION_INDICATOR)));

            dataPlate.setDuble(myCursor.getString(myCursor
                    .getColumnIndex(DOUBLE)));

            dataPlate.setSealNumber(myCursor.getString(myCursor
                    .getColumnIndex(NUMBER_SEAL)));

            dataPlate.setEngineNumber(myCursor.getString(myCursor
                    .getColumnIndex(NUMBER_ENGINER)));

            dataPlate.setOffSideRecord(filter(myCursor.getString(myCursor
                    .getColumnIndex(IMPEDIMENT_INDICATOR)), IMPEDIMENT_INDICATOR));

            dataPlate.setTheftRecord(filter(myCursor.getString(myCursor
                    .getColumnIndex(OCCURRENCE_INDICATOR)), OCCURRENCE_INDICATOR));

            myCursor.close();
        } else {
            Objects.requireNonNull(myCursor).close();
            return null;
        }

        return dataPlate;

    }

    private String getCityName(String id) {

        String sCityName = null;

        PrepopulatedDBOpenHelper database = new PrepopulatedDBOpenHelper(context);

        try {
            sCityName = database.getCityNameCursor(id);
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            Log.d("ErroCity: ", String.valueOf(exception));
        }

        return sCityName;

    }

    private String filter(String filterData, String xml) {

        String[] listXml;
        String xmlText = null;
        int valorFilter;

        switch (xml){
            case LICENSING_STATUS:
                listXml = context.getResources().getStringArray(R.array.filter_vehicle_licensing_status);
                //Log.d("xmlStatus: ", String.valueOf(listXml) + " - Filter: " + filterData);
                xmlText = listXml[Integer.parseInt(filterData)];
                break;
            case TYPE:
                listXml = context.getResources().getStringArray(R.array.filter_vehicle_type);
                //Log.d("xmlTipo: ", String.valueOf(listXml) + " - Filter: " + filterData);
                try {
                    xmlText = listXml[Integer.parseInt(filterData)];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
            case SPECIES:
                listXml = context.getResources().getStringArray(R.array.auto_species);
                //Log.d("xmlEspecie: ", String.valueOf(listXml) + " - Filter: " + filterData);
                try {
                    xmlText = listXml[Integer.parseInt(filterData)];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
            case COLOR:
                listXml = context.getResources().getStringArray(R.array.filter_color);
                //Log.d("xmlCor: ", String.valueOf(listXml) + " - Filter: " + filterData);
                try {
                    xmlText = listXml[Integer.parseInt(filterData)];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
            case STATE:
            case CNH_STATE:
                listXml = context.getResources().getStringArray(R.array.state_array);
                //Log.d("xmlUf: ", String.valueOf(listXml) + " - Filter: " + filterData);
                try {
                    xmlText = listXml[Integer.parseInt(filterData)];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
            //Log.d("xmlUf: ", String.valueOf(listXml) + " - Filter: " + filterData);
            case IMPEDIMENT_INDICATOR:
                listXml = context.getResources().getStringArray(R.array.filter_theft_occurrence);
                //Log.d("xmlImpedimento: ", String.valueOf(listXml) + " - Filter: " + filterData);
                xmlText = listXml[Integer.parseInt(filterData)];
                break;
            case OCCURRENCE_INDICATOR:
                listXml = context.getResources().getStringArray(R.array.filter_theft_occurrence);
                //Log.d("xmlOcorrencia: ", String.valueOf(listXml) + " - Filter: " + filterData);
                try {
                    xmlText = listXml[Integer.parseInt(filterData)];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
            case CATEGORY:
                listXml = context.getResources().getStringArray(R.array.list_category);
                //Log.d("xmlCategoria: ", String.valueOf(listXml) + " - Filter: " + filterData);
                valorFilter = Integer.parseInt(filterData);
                valorFilter = valorFilter/10;
                try {
                    xmlText = listXml[valorFilter];
                }
                catch(ArrayIndexOutOfBoundsException exception) {
                    Log.d("Erro: ", String.valueOf(exception));
                }
                break;
        }

        return xmlText;

    }

    public DataFromCnh getCnhData(DataFromCnh fromCNH, String typeSearch) {

        Cursor myCursor;

        switch (typeSearch) {
            case "cnh":

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_RENACH
                                + " INDEXED BY cnh WHERE " + CNH_NUMBER + "=?",
                        new String[]{fromCNH.getRegister()}, null);

                break;
            case "rg":

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_RENACH
                                + " INDEXED BY rg WHERE " + CNH_REGISTER + "=?",
                        new String[]{fromCNH.getIdentity()}, null);

                break;
            case "cpf":

                myCursor = this.database.rawQuery("SELECT * FROM "
                                + TABLE_RENACH
                                + " INDEXED BY cpf WHERE " + CNH_CPF + "=?",
                        new String[]{fromCNH.getCpf()}, null);
                break;
            default:

                String query = "SELECT * FROM "
                        + TABLE_RENACH + " INDEXED BY nascimento "
                        + "WHERE " + CNH_NAME + " LIKE '%" + fromCNH.getName() + "%'"
                        + " AND " + CNH_MATHERS_NAME + " LIKE '%" + fromCNH.getMothersName() + "%'"
                        + " AND " + CNH_BIRTHDAY + " = '" + fromCNH.getBirthDate() + "'";

                myCursor = this.database.rawQuery(query, null);

                break;
        }

        if (myCursor.getCount() > 0) {

            myCursor.moveToFirst();

            fromCNH.setName(myCursor.getString(myCursor
                    .getColumnIndex(CNH_NAME)));

            fromCNH.setRegister(myCursor.getString(myCursor
                    .getColumnIndex(CNH_NUMBER)));

            fromCNH.setState(filter(myCursor.getString(myCursor
                    .getColumnIndex(CNH_STATE)), CNH_STATE));

            fromCNH.setBirthDate(myCursor.getString(myCursor
                    .getColumnIndex(CNH_BIRTHDAY)));

            fromCNH.setCpf(myCursor.getString(myCursor
                    .getColumnIndex(CNH_CPF)));

            fromCNH.setMothersName(myCursor.getString(myCursor
                    .getColumnIndex(CNH_MATHERS_NAME)));

            fromCNH.setIdentity(myCursor.getString(myCursor
                    .getColumnIndex(CNH_REGISTER)));

            fromCNH.setCnhSituation(myCursor.getString(myCursor
                    .getColumnIndex(CNH_SITUATION)));

            fromCNH.setCnhBlock(myCursor.getString(myCursor
                    .getColumnIndex(CNH_BLOCK)));

            fromCNH.setCnhCategory(myCursor.getString(myCursor
                    .getColumnIndex(CNH_CATEGORY)));

            fromCNH.setCnhValidity(myCursor.getString(myCursor
                    .getColumnIndex(CNH_VALIDITY)));

            fromCNH.setCnhPoints(myCursor.getString(myCursor
                    .getColumnIndex(CNH_POINTS)));

            myCursor.close();
        } else {
            myCursor.close();
            return null;
        }

        return fromCNH;

    }

}

