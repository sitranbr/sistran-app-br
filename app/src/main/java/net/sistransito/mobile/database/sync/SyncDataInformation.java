package net.sistransito.mobile.database.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.http.CustomHttpClient;
import net.sistransito.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SyncDataInformation extends AsyncTask<String, String, String> {

	private Context context;
	private ProgressDialog pDialog;
	private String autoSyncTitle = "AUTOJSON";
	private String tavSyncTitle = "TAVJSON";
	private String tcaSyncTitle = "TCAJSON";
	private String rrdSyncTitle = "RRDJSON";

	public static final String UPLOAD_URL = "https://sistransito.com.br/movel/replicar/insert_auto.php";
	public static final int B_STATS_VERSION = 1;

	private String urlAutoSync = "https://sistransito.com.br/movel/replicar/insert_auto.php";
	private String urlTavSync = "http://sistransito.com.br/movel/replicar/insert_tav.php";
	private String urlTcaSync = "http://sistransito.com.br/movel/replicar/insert_tca.php";
	private String urlRrdSync = "http://sistransito.com.br/movel/replicar/insert_rrd.php";

	private String userResponse;
	private ArrayList<NameValuePair> listParamsUsers;
	private String autoJson;
	private String tavJson;
	private String tcaJson;
	private String rrdJson;
	private boolean syncStatus;

	public SyncDataInformation(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		pDialog = null;
		pDialog = new ProgressDialog(context);
		pDialog.setMax(100);
		pDialog.setCancelable(false);
		pDialog.setMessage(context.getResources().getString(R.string.sincronizacao_mgs));
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {

		autoJson = (DatabaseCreator.getAitDatabaseAdapter(context))
				.aitComposeJSONfromSQLite();

		/*autoJson = (DatabaseCreator.getDatabaseAdapterAutoInfracao(context))
				.getResults();*/

		/*rrdJson = (DatabaseCreator.getDatabaseAdapterRRD(context))
				.rrdComposeJSONfromSQLite();

		tavJson = (DatabaseCreator.getDatabaseAdapterTAV(context))
				.tavComposeJSONfromSQLite();

		tcaJson = (DatabaseCreator.getDatabaseAdapterTCA(context))
				.tcaComposeJSONfromSQLite();*/

		Log.d("autoJson", String.valueOf(autoJson));
		/*Log.d("rrdJson", "rrd" + rrdJson);
		Log.d("tavJson", "tav" + tavJson);
		Log.d("tcaJson", "tca" + tcaJson);*/

		if (autoJson != null || tavJson != null || tcaJson != null
				|| rrdJson != null) {
			syncStatus = true;
			if (autoJson != null) {
				syncAuto();
				/*try {
					sendJsonData(autoJson);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
			/*if (tavJson != null) {
				syncTav();
			}
			if (tcaJson != null) {
				syncTca();
			}
			if (rrdJson != null) {
				syncRrd();
			}*/
		} else {
			syncStatus = false;
		}

		Log.d("syncStatus", "" + syncStatus);
		return null;

	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		pDialog.setProgress(Integer.parseInt(values[0]));
	}

	@Override
	protected void onPostExecute(String result) {
		//Log.d("onPostExecute", result);
		if ((pDialog != null) && (pDialog.isShowing())) {
			pDialog.dismiss();
		}
		if (syncStatus) {
			AnyAlertDialog.dialogShow(
					context.getResources().getString(R.string.sincronizacao_concluida),
					context,
					context.getResources().getString(R.string.titulo_sincronizacao));
		} else {
			AnyAlertDialog.dialogShow(
					context.getResources().getString(R.string.nao_precisa_sincronizar),
					context,
					context.getResources().getString(R.string.titulo_sincronizacao));
		}
		Log.d("syncStatus", String.valueOf(syncStatus));
		super.onPostExecute(result);
	}

	private void syncAuto() {
		listParamsUsers = new ArrayList<NameValuePair>();
		//listParamsUsers.add(new BasicNameValuePair(autoSyncTitle, autoJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlAutoSync);

			Log.d("Parâmetros", String.valueOf(listParamsUsers));

			JSONArray resultJsonArray = new JSONArray(userResponse);
			//Log.d("json array ", resultJsonArray + " " + resultJsonArray.length());
			for (int i = 0; i < resultJsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) resultJsonArray.get(i);
				Log.d("jsonArray", jsonObject.getString("status"));
				if ((jsonObject.getString("status")).equals("1")) {
					/*(DatabaseCreator.getDatabaseAdapterNumero(context))
							.deleteNumeroAuto(jsonObject.getString("id"));
					(DatabaseCreator.getDatabaseAdapterAutoInfracao(context))
							.autoUpdateSyncStatus(jsonObject.getString("id"));*/
					Log.i("Sincronizacão", userResponse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void syncTav() {
		listParamsUsers = new ArrayList<NameValuePair>();
		listParamsUsers.add(new BasicNameValuePair(tavSyncTitle, tavJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlTavSync);

			JSONArray resultJsonArray = new JSONArray(userResponse);
			Log.d("jason array ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject jsonObject = (JSONObject) resultJsonArray.get(loparg);
				Log.d("Tav atual: ",
						jsonObject.getString("id") + " " + jsonObject.getString("status"));
				if ((jsonObject.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteTavNumber(jsonObject.getString("id"));
					(DatabaseCreator.getTavDatabaseAdapter(context))
							.TavUpdateSyncStatus(jsonObject.getString("id"));

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void syncTca() {
		listParamsUsers = new ArrayList<NameValuePair>();
		listParamsUsers.add(new BasicNameValuePair(tcaSyncTitle, tcaJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlTcaSync);

			JSONArray resultJsonArray = new JSONArray(userResponse);
			Log.d("jason array ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject jsonObject = (JSONObject) resultJsonArray.get(loparg);
				Log.d("Tca atual: ",
						jsonObject.getString("id") + " " + jsonObject.getString("status"));
				if ((jsonObject.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteTcaNumber(jsonObject.getString("id"));
					(DatabaseCreator.getTcaDatabaseAdapter(context))
							.tcaUpdateSyncStatus(jsonObject.getString("id"));

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void syncRrd() {

		listParamsUsers = new ArrayList<NameValuePair>();
		listParamsUsers.add(new BasicNameValuePair(rrdSyncTitle, rrdJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlRrdSync);

			JSONArray resultJsonArray = new JSONArray(userResponse);
			Log.d("jason aaray ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject obj = (JSONObject) resultJsonArray.get(loparg);
				Log.d("ssssssssssss",
						obj.getString("id") + " " + obj.getString("status"));
				if ((obj.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteRrdNumber(obj.getString("id"));

					(DatabaseCreator.getRrdDatabaseAdapter(context))
							.updateRrdSyncStatus(obj.getString("id"));
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void sendJsonData(JSONArray jsonData) throws Exception {
		//Validate.notNull(data, "Data cannot be null");

		Log.d("sendJsonData", String.valueOf(jsonData));

		String url = UPLOAD_URL;
		URL obj = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

		// Compress the data to save bandwidth
		byte[] compressedData = compress(jsonData.toString());

		// Add headers
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Connection", "close");
		connection.addRequestProperty("Content-Encoding", "gzip"); // We gzip our request
		//connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
		connection.setRequestProperty("Content-Type", "application/json"); // We send our data in JSON format
		connection.setRequestProperty("User-Agent", "MC-Server/" + B_STATS_VERSION);

		// Send data
		connection.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.write(compressedData);
		outputStream.flush();
		outputStream.close();

		connection.getInputStream().close(); // We don't care about the response - Just send our data :)
	}

	private static byte[] compress(final String str) throws IOException {
		if (str == null) {
			return null;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
		gzip.write(str.getBytes("UTF-8"));
		gzip.close();
		return outputStream.toByteArray();
	}


}
