package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sistransito.mobile.tca.TcaData;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TcaDatabaseAdapter {
	private TimeAndIme ime;
	private SQLiteDatabase database;
	private TcaDatabaseHelper databaseHelper;
	private Context context;

	public TcaDatabaseAdapter(Context context) {
		ime = new TimeAndIme(context);
		databaseHelper = new TcaDatabaseHelper(context);
		database = databaseHelper.getWritableDatabase(ime.getIME());
		this.context = context;
	}

	public void close() {
		database.close();
	}

	public boolean setData(TcaData data) {
		ContentValues values = new ContentValues();
		values.put(TcaDatabaseHelper.NOME_DO_CONDUTOR,
				data.getDriverName());
		values.put(TcaDatabaseHelper.CNH_PPD, data.getCnhPpd());
		values.put(TcaDatabaseHelper.CPF, data.getCpf());
		values.put(TcaDatabaseHelper.ENDERECO, data.getAddress());
		values.put(TcaDatabaseHelper.BAIRRO, data.getDistrict());
		values.put(TcaDatabaseHelper.MUNICIPIO, data.getCity());
		values.put(TcaDatabaseHelper.MUNICIPIO_UF, data.getState());
		values.put(TcaDatabaseHelper.PLACA, data.getPlate());
		values.put(TcaDatabaseHelper.PLACA_UF, data.getPlateState());
		values.put(TcaDatabaseHelper.MARCA_MODELO, data.getBrandModel());
		values.put(
				TcaDatabaseHelper.CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO,
				data.getDriverInvolvedInCarAccident());
		values.put(

		TcaDatabaseHelper.CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA,
				data.getDriverClaimsToHaveDrunkAlcohol());

		values.put(TcaDatabaseHelper.DATA_INGERIU_ALCOOL,
				data.getDateThatDrankAlcohol());
		values.put(TcaDatabaseHelper.HORA_INGERIU_ALCOOL,
				data.getTimeThatDrankAlcohol());

		values.put(

		TcaDatabaseHelper.CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA,
				data.getDriverClaimsToHaveUsedToxicSubstance());

		values.put(TcaDatabaseHelper.DATA_INGERIU_SUBSTANCIA,
				data.getDateIngestedSubstance());
		values.put(TcaDatabaseHelper.HORA_INGERIU_SUBSTANCIA,
				data.getTimeIngestedSubstance());

		values.put(TcaDatabaseHelper.O_CONDUTOR_APRESENTA_SINAIS_DE,
				data.getDriverShowsSignsOf());
		values.put(TcaDatabaseHelper.EM_SUA_ATITUDE_OCORRE,
				data.getInHisAttitudeOccurs());
		values.put(TcaDatabaseHelper.SABE_ONDE_ESTA, data.getKnowsWhereItIs());
		values.put(TcaDatabaseHelper.SABE_A_DATA_E_A_HORA,
				data.getKnowsTheDateAndTime());
		values.put(TcaDatabaseHelper.SABE_SEU_ENDERECO,
				data.getKnowsItsAddress());
		values.put(TcaDatabaseHelper.LEMBRA_DOS_ATOS_COMETIDOS,
				data.getRememberTheActsCommitted());
		values.put(

		TcaDatabaseHelper.EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE,
				data.getInRelationToTheirMotorAndVerbalAbilityOccurs());
		values.put(TcaDatabaseHelper.CONCLUSAO, data.getConclusion());

		values.put(TcaDatabaseHelper.NUMERO_TCA, data.getTcaNumber());
		values.put(TcaDatabaseHelper.NUMERO_AUTO, data.getAitNumber());

		long insert = this.database.insert(TcaDatabaseHelper.TABLE_NAME, null,
				values);
		if (insert > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteTcaNumber(data.getTcaNumber());

			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setTcaPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setTcaRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainTcaNumber());

			return true;
		} else {
			return false;
		}

	}

	public Cursor getTCACursor() {
		Cursor myCursor = this.database.query(TcaDatabaseHelper.TABLE_NAME,
				null, null, null, null, null, TcaDatabaseHelper.COLUMN_ID
						+ " DESC");
		return myCursor;

	}

	public Cursor getTCACursorFromID(int id) {
		Cursor myCursor = this.database.query(TcaDatabaseHelper.TABLE_NAME,
				null, TcaDatabaseHelper.COLUMN_ID + "=?", new String[] { id
						+ "" }, null, null, null);
		return myCursor;
	}

	public String tcaComposeJSONfromSQLite() {
		ArrayList<HashMap<String, String>> tcaList = new ArrayList<HashMap<String, String>>();
		Cursor cursor = this.database.query(TcaDatabaseHelper.TABLE_NAME, null,
				null, null, null, null, null);
		TcaData data;
		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			do {
				data = new TcaData();
				data.setTCADataFromCursor(cursor);
				HashMap<String, String> map = new HashMap<String, String>();

				// Condutor/Ve�culo
				map.put(TcaDatabaseHelper.NOME_DO_CONDUTOR,
						data.getDriverName());
				map.put(TcaDatabaseHelper.CNH_PPD, data.getCnhPpd());
				map.put(TcaDatabaseHelper.CPF, data.getCpf());
				map.put(TcaDatabaseHelper.ENDERECO, data.getAddress());
				map.put(TcaDatabaseHelper.BAIRRO, data.getDistrict());
				map.put(TcaDatabaseHelper.MUNICIPIO, data.getCity());
				map.put(TcaDatabaseHelper.MUNICIPIO_UF, data.getState());
				map.put(TcaDatabaseHelper.PLACA, data.getPlate());
				map.put(TcaDatabaseHelper.PLACA_UF, data.getPlateState());
				map.put(TcaDatabaseHelper.MARCA_MODELO, data.getBrandModel());

				// Question�rio

				map.put(TcaDatabaseHelper.CONDUTOR_ENVOLVEU_SE_EM_ACIDENTE_DE_TRANSITO,
						data.getDriverInvolvedInCarAccident());
				map.put(TcaDatabaseHelper.CONDUTOR_DECLARA_TER_INGERIDO_BEBIDA_ALCOOLICA,
						data.getDriverClaimsToHaveDrunkAlcohol());

				map.put(TcaDatabaseHelper.DATA_INGERIU_ALCOOL,
						data.getDateThatDrankAlcohol());
				map.put(TcaDatabaseHelper.HORA_INGERIU_ALCOOL,
						data.getTimeThatDrankAlcohol());

				map.put(TcaDatabaseHelper.CONDUTOR_DECLARA_TER_FEITO_USO_DE_SUBSTANCIA_TOXICA,
						data.getDriverClaimsToHaveUsedToxicSubstance());

				map.put(TcaDatabaseHelper.DATA_INGERIU_SUBSTANCIA,
						data.getDateIngestedSubstance());
				map.put(TcaDatabaseHelper.HORA_INGERIU_SUBSTANCIA,
						data.getTimeIngestedSubstance());

				map.put(TcaDatabaseHelper.O_CONDUTOR_APRESENTA_SINAIS_DE,
						data.getDriverShowsSignsOf());
				map.put(TcaDatabaseHelper.EM_SUA_ATITUDE_OCORRE,
						data.getInHisAttitudeOccurs());
				map.put(TcaDatabaseHelper.SABE_ONDE_ESTA,
						data.getKnowsWhereItIs());
				map.put(TcaDatabaseHelper.SABE_A_DATA_E_A_HORA,
						data.getKnowsTheDateAndTime());
				map.put(TcaDatabaseHelper.SABE_SEU_ENDERECO,
						data.getKnowsItsAddress());
				map.put(TcaDatabaseHelper.LEMBRA_DOS_ATOS_COMETIDOS,
						data.getRememberTheActsCommitted());
				map.put(TcaDatabaseHelper.EM_RELACO_A_SUA_CAPACIDADE_MOTORA_E_VERBAL_OCORRE,
						data.getInRelationToTheirMotorAndVerbalAbilityOccurs());
				map.put(TcaDatabaseHelper.CONCLUSAO, data.getConclusion());
				map.put(TcaDatabaseHelper.NUMERO_TCA, data.getTcaNumber());
				map.put(TcaDatabaseHelper.NUMERO_AUTO, data.getAitNumber());

				tcaList.add(map);
			} while (cursor.moveToNext());

		} else {

			return null;
		}
		cursor.close();
		Gson gson = new GsonBuilder().create();
		// Use GSON to serialize Array List to JSON
		return gson.toJson(tcaList);
	}

	public void tcaUpdateSyncStatus(String numero_tca) {
		this.database.delete(TcaDatabaseHelper.TABLE_NAME,
				TcaDatabaseHelper.NUMERO_TCA + "=?",
				new String[] { numero_tca });
	}
}
