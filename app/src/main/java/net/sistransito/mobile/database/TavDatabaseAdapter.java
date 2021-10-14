package net.sistransito.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sistransito.mobile.tav.TavData;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TavDatabaseAdapter {
	private TimeAndIme ime;
	private SQLiteDatabase database;
	private TavDatabaseHelper databaseHelper;
	private Context context;

	public TavDatabaseAdapter(Context context) {
		ime = new TimeAndIme(context);
		databaseHelper = new TavDatabaseHelper(context);
		database = databaseHelper.getWritableDatabase(ime.getIME());
		this.context = context;
	}

	// public DadosAuto getDataAutoFromPlate(String placa) {
	//
	// Cursor myCursor = this.database.query(TAVDatabaseHelper.TABLE_NAME,
	// null, TAVDatabaseHelper.PLACA + "=?",
	// new String[] { placa + "" }, null, null, null);
	//
	// if (myCursor.getCount() > 0) {
	// DadosAuto autode_data = new DadosAuto();
	// myCursor.moveToFirst();
	// autode_data.setAutoDeDataFromCursor(myCursor);
	// myCursor.close();
	// return autode_data;
	// } else {
	// myCursor.close();
	// return null;
	// }
	// }

	// public boolean isSamePlacaExist(String placa) {
	// Cursor myCursor = this.database.query(
	// TAVDatabaseHelper.TABLE_NAME, null,
	// TAVDatabaseHelper.PLACA + "=?", new String[] { placa
	// + "" }, null, null, null);
	// if (myCursor.getCount() > 0) {
	// myCursor.close();
	// return true;
	// } else {
	// myCursor.close();
	// return false;
	// }
	// }

	public void close() {
		database.close();
	}

	public boolean setData(TavData data) {
		ContentValues values = new ContentValues();

		values.put(TavDatabaseHelper.PLATE, data.getPlate());

		values.put(TavDatabaseHelper.AIT_NUMBER, data.getAitNumber());
		values.put(TavDatabaseHelper.OWNER_NAME,
				data.getOwnerName());
		values.put(TavDatabaseHelper.CPF_CNPJ, data.getCpfCnpj());
		values.put(TavDatabaseHelper.RENAVAM_NUMBER,
				data.getRenavamNumber());
		values.put(TavDatabaseHelper.CHASSI_NUMBER,
				data.getChassisNumber());

		// Estrutur Veiculo

		values.put(TavDatabaseHelper.CABECA_DE_ALAVANCA,
				data.getLeverHead());
		values.put(TavDatabaseHelper.CARROCERIA, data.getCarBody());
		values.put(TavDatabaseHelper.FORRO, data.getLining());
		values.put(TavDatabaseHelper.LATARIA_CAPO, data.getHoodBody());
		values.put(TavDatabaseHelper.LATARIA_LADO_DIREITO,
				data.getRightSideBody());
		values.put(TavDatabaseHelper.LATARIA_LADO_ESQUERDO,
				data.getLeftSideBody());
		values.put(TavDatabaseHelper.LATARIA_TAPA_PORTA_MALA,
				data.getTrunkBodywork());
		values.put(TavDatabaseHelper.LATARIA_TETO, data.getRoofBodywork());

		// public static final String MOTOR = "motor";
		// public static final String PAINEL = "painel";
		// public static final String PINTURA_CAPO = "pintura_capo";
		// public static final String PINTURA_LADO_DIREITO =
		// "pintura_lado_direito";
		// public static final String PINTURA_LADO_ESQUERDO =
		// "pintura_lado_esquerdo";
		// public static final String PINTURA_PORTA_MALA = "pintura_porta_mala";
		// public static final String PINTURA_TETO = "pintura_teto";
		// public static final String RADIADOR = "radiador";
		// public static final String VIDROS_LATERAIS = "vidros_laterais";
		// public static final String VIDRO_PARA_BRISA = "vidro_para_brisa";
		// public static final String VIDRO_TRASEIRO = "vidro_traseiro";

		values.put(TavDatabaseHelper.MOTOR, data.getEnginer());
		values.put(TavDatabaseHelper.PAINEL, data.getDashboard());
		values.put(TavDatabaseHelper.PINTURA_CAPO, data.getHoodPaint());
		values.put(TavDatabaseHelper.PINTURA_LADO_DIREITO,
				data.getRightSidePaint());
		values.put(TavDatabaseHelper.PINTURA_LADO_ESQUERDO,
				data.getLeftSidePaint());
		values.put(TavDatabaseHelper.PINTURA_PORTA_MALA,
				data.getTrunkPainting());
		values.put(TavDatabaseHelper.PINTURA_TETO, data.getCeilingPainting());
		values.put(TavDatabaseHelper.RADIADOR, data.getRadiator());
		values.put(TavDatabaseHelper.VIDROS_LATERAIS, data.getSideWindows());
		values.put(TavDatabaseHelper.VIDRO_PARA_BRISA,
				data.getWindShieldGlass());
		values.put(TavDatabaseHelper.VIDRO_TRASEIRO, data.getRearWindow());

		// Ve�culo
		// Acess�rios
		// public static final String ANTENA_DE_RADIO = "antena_de_radio";
		// public static final String BAGAGEIRO = "bagageiro";
		// public static final String BANCOS = "bancos";
		// public static final String BATERIA = "bateria";
		// public static final String CALOTA = "calota";
		values.put(TavDatabaseHelper.ANTENA_DE_RADIO, data.getAntenna());
		values.put(TavDatabaseHelper.BAGAGEIRO, data.getTrunk());
		values.put(TavDatabaseHelper.BANCOS, data.getSeats());
		values.put(TavDatabaseHelper.BATERIA, data.getBaterry());
		values.put(TavDatabaseHelper.CALOTA, data.getWheelCover());

		// public static final String CONDICIONADOR_DE_AR =
		// "condicionador_de_ar";
		// public static final String EXTINTOR_DE_INCENDIO =
		// "extintor_de_incendio";
		// public static final String FAROLETE_DIANTEIRO = "farolete_dianteiro";
		// public static final String FAROLETE_TRASEIRO = "farolete_traseiro";
		// public static final String MACACO = "macaco";

		values.put(TavDatabaseHelper.CONDICIONADOR_DE_AR,
				data.getAirConditioner());
		values.put(TavDatabaseHelper.EXTINTOR_DE_INCENDIO,
				data.getFireExtinguisher());
		values.put(TavDatabaseHelper.FAROLETE_DIANTEIRO,
				data.getHeadLight());
		values.put(TavDatabaseHelper.FAROLETE_TRASEIRO,
				data.getTaiLight());
		values.put(TavDatabaseHelper.MACACO, data.getEnginer());

		// public static final String PARA_CHOQUE_DIANTEIRO =
		// "para_choque_dianteiro";
		// public static final String PARA_CHOQUE_TRASEIRO =
		// "para_choque_traseiro";
		// public static final String PARA_SOL_DO_CONDUTOR =
		// "para_sol_do_condutor";
		// public static final String PNEUS = "pneus";
		// public static final String PNEUS_ESTEPE = "pneus_estepe";

		values.put(TavDatabaseHelper.PARA_CHOQUE_DIANTEIRO,
				data.getFrontBumper());
		values.put(TavDatabaseHelper.PARA_CHOQUE_TRASEIRO,
				data.getBackBumper());
		values.put(TavDatabaseHelper.PARA_SOL_DO_CONDUTOR,
				data.getDriverSunVisor());
		values.put(TavDatabaseHelper.PNEUS, data.getTires());
		values.put(TavDatabaseHelper.PNEUS_ESTEPE, data.getSpareTire());

		// public static final String RADIO = "radio";
		// public static final String RETROVISOR_INTERNO = "retrovisor_interno";
		// public static final String RETROVISOR_EXTERNO_DIREITO =
		// "retrovisor_externo_direito";
		// public static final String TAPETE = "tapete";
		// public static final String TRIANGULO = "triangulo";
		// public static final String VOLANTE = "volante";
		// public static final String GUIDAM = "guidam";

		values.put(TavDatabaseHelper.RADIO, data.getRadio());
		values.put(TavDatabaseHelper.RETROVISOR_INTERNO,
				data.getRearviewMirror());
		values.put(TavDatabaseHelper.RETROVISOR_EXTERNO_DIREITO,
				data.getRightSideMirror());
		values.put(TavDatabaseHelper.TAPETE, data.getCarpet());
		values.put(TavDatabaseHelper.TRIANGULO, data.getTriangle());
		values.put(TavDatabaseHelper.VOLANTE, data.getSteeringWheel());
		values.put(TavDatabaseHelper.GUIDAM, data.getMotorcycleHandlebar());

		// // Geral
		// public static final String ODOMETRO = "odometro";
		// public static final String MARCADOR_DE_CONBUTIVEL =
		// "marcador_de_conbutivel";
		// public static final String REMOCAO_ATRAVES_DE = "remocao_atraves_de";
		// public static final String OBSERVACAO = "observacao";
		//
		// public static final String NOME_DA_EMPRESA = "nome_da_empresa";
		// public static final String NOME_DO_CONDUTOR_DO_GUINCHO =
		// "nome_condutor_guincho";

		values.put(TavDatabaseHelper.ODOMETRO, data.getOdometer());
		values.put(TavDatabaseHelper.MARCADOR_DE_CONBUTIVEL,
				data.getFuelGauge());
		values.put(TavDatabaseHelper.REMOCAO_ATRAVES_DE,
				data.getRemovedVia());
		values.put(TavDatabaseHelper.OBSERVACAO, data.getObservation());
		values.put(TavDatabaseHelper.NOME_DA_EMPRESA, data.getCompanyName());
		values.put(TavDatabaseHelper.NOME_DO_CONDUTOR_DO_GUINCHO,
				data.getWinchDriverName());

		values.put(TavDatabaseHelper.TAV_NUMBER, data.getTavNumber());

		long insert = this.database.insert(TavDatabaseHelper.TABLE_NAME, null,
				values);

		if (insert > 0) {
			(DatabaseCreator.getNumberDatabaseAdapter(context))
					.deleteTavNumber(data.getTavNumber());

			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setTavPerformed();
			(DatabaseCreator.getBalanceDatabaseAdapter(context))
					.setTavRemaining((DatabaseCreator
							.getNumberDatabaseAdapter(context))
							.getRemainTavNumber());

			return true;
		} else {
			return false;
		}
	}

	public Cursor getTavCursor() {
		Cursor myCursor = this.database.query(TavDatabaseHelper.TABLE_NAME,
				null, null, null, null, null, TavDatabaseHelper.COLUMN_ID
						+ " DESC");
		return myCursor;

	}

	public Cursor getTAVCursorFromID(int id) {
		Cursor myCursor = this.database.query(TavDatabaseHelper.TABLE_NAME,
				null, TavDatabaseHelper.COLUMN_ID + "=?", new String[] { id
						+ "" }, null, null, null);
		return myCursor;
	}

	public String tavComposeJSONfromSQLite() {
		ArrayList<HashMap<String, String>> tavList = new ArrayList<HashMap<String, String>>();
		Cursor cursor = this.database.query(TavDatabaseHelper.TABLE_NAME, null,
				null, null, null, null, null);
		TavData data;
		if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
			do {
				data = new TavData();
				data.setTAVDataFromCursor(cursor);
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				Log.d("ppp", data.getPlate());
				map.put(TavDatabaseHelper.PLATE, data.getPlate());
				// Condutor
				map.put(TavDatabaseHelper.AIT_NUMBER,
						data.getAitNumber());
				map.put(TavDatabaseHelper.OWNER_NAME,
						data.getOwnerName());
				map.put(TavDatabaseHelper.CPF_CNPJ, data.getCpfCnpj());
				map.put(TavDatabaseHelper.RENAVAM_NUMBER,
						data.getRenavamNumber());
				map.put(TavDatabaseHelper.CHASSI_NUMBER,
						data.getChassisNumber());
				// Ve�culo
				// Estrutur
				map.put(TavDatabaseHelper.CABECA_DE_ALAVANCA,
						data.getLeverHead());
				map.put(TavDatabaseHelper.CARROCERIA, data.getCarBody());
				map.put(TavDatabaseHelper.FORRO, data.getLining());
				map.put(TavDatabaseHelper.LATARIA_CAPO, data.getHoodBody());
				map.put(TavDatabaseHelper.LATARIA_LADO_DIREITO,
						data.getRightSideBody());
				map.put(TavDatabaseHelper.LATARIA_LADO_ESQUERDO,
						data.getLeftSideBody());
				map.put(TavDatabaseHelper.LATARIA_TAPA_PORTA_MALA,
						data.getTrunkBodywork());
				map.put(TavDatabaseHelper.LATARIA_TETO, data.getRoofBodywork());
				map.put(TavDatabaseHelper.MOTOR, data.getEnginer());
				map.put(TavDatabaseHelper.PAINEL, data.getDashboard());
				map.put(TavDatabaseHelper.PINTURA_CAPO, data.getHoodPaint());
				map.put(TavDatabaseHelper.PINTURA_LADO_DIREITO,
						data.getRightSidePaint());
				map.put(TavDatabaseHelper.PINTURA_LADO_ESQUERDO,
						data.getLeftSidePaint());
				map.put(TavDatabaseHelper.PINTURA_PORTA_MALA,
						data.getTrunkPainting());
				map.put(TavDatabaseHelper.PINTURA_TETO, data.getCeilingPainting());
				map.put(TavDatabaseHelper.RADIADOR, data.getRadiator());
				map.put(TavDatabaseHelper.VIDROS_LATERAIS,
						data.getSideWindows());
				map.put(TavDatabaseHelper.VIDRO_PARA_BRISA,
						data.getWindShieldGlass());
				map.put(TavDatabaseHelper.VIDRO_TRASEIRO,
						data.getRearWindow());

				// Ve�culo
				// Acess�rios

				map.put(TavDatabaseHelper.ANTENA_DE_RADIO,
						data.getAntenna());
				map.put(TavDatabaseHelper.BAGAGEIRO, data.getSeats());
				map.put(TavDatabaseHelper.BANCOS, data.getSeats());
				map.put(TavDatabaseHelper.BATERIA, data.getBaterry());
				map.put(TavDatabaseHelper.CALOTA, data.getWheelCover());
				map.put(TavDatabaseHelper.CONDICIONADOR_DE_AR,
						data.getAirConditioner());
				map.put(TavDatabaseHelper.EXTINTOR_DE_INCENDIO,
						data.getFireExtinguisher());
				map.put(TavDatabaseHelper.FAROLETE_DIANTEIRO,
						data.getHeadLight());
				map.put(TavDatabaseHelper.FAROLETE_TRASEIRO,
						data.getTaiLight());
				map.put(TavDatabaseHelper.MACACO, data.getJack());
				map.put(TavDatabaseHelper.PARA_CHOQUE_DIANTEIRO,
						data.getFrontBumper());
				map.put(TavDatabaseHelper.PARA_CHOQUE_TRASEIRO,
						data.getBackBumper());
				map.put(TavDatabaseHelper.PARA_SOL_DO_CONDUTOR,
						data.getDriverSunVisor());
				map.put(TavDatabaseHelper.PNEUS, data.getTires());
				map.put(TavDatabaseHelper.PNEUS_ESTEPE, data.getSpareTire());
				map.put(TavDatabaseHelper.RADIO, data.getRadio());
				map.put(TavDatabaseHelper.RETROVISOR_INTERNO,
						data.getRearviewMirror());
				map.put(TavDatabaseHelper.RETROVISOR_EXTERNO_DIREITO,
						data.getRightSideMirror());
				map.put(TavDatabaseHelper.TAPETE, data.getCarpet());
				map.put(TavDatabaseHelper.TRIANGULO, data.getTriangle());
				map.put(TavDatabaseHelper.VOLANTE, data.getSteeringWheel());
				map.put(TavDatabaseHelper.GUIDAM, data.getMotorcycleHandlebar());

				// Geral
				map.put(TavDatabaseHelper.ODOMETRO, data.getOdometer());
				map.put(TavDatabaseHelper.MARCADOR_DE_CONBUTIVEL,
						data.getFuelGauge());
				map.put(TavDatabaseHelper.REMOCAO_ATRAVES_DE,
						data.getRemovedVia());
				map.put(TavDatabaseHelper.OBSERVACAO, data.getObservation());
				map.put(TavDatabaseHelper.NOME_DA_EMPRESA,
						data.getCompanyName());
				map.put(TavDatabaseHelper.NOME_DO_CONDUTOR_DO_GUINCHO,
						data.getWinchDriverName());
				map.put(TavDatabaseHelper.TAV_NUMBER, data.getTavNumber());
	
				tavList.add(map);
			} while (cursor.moveToNext());
		} else {

			return null;
		}
		cursor.close();
		Gson gson = new GsonBuilder().create();
		// Use GSON to serialize Array List to JSON
		return gson.toJson(tavList);
	}

	public void TavUpdateSyncStatus(String numero_tav) {
		this.database.delete(TavDatabaseHelper.TABLE_NAME,
				TavDatabaseHelper.TAV_NUMBER + "=?",
				new String[] { numero_tav });
	}
}
