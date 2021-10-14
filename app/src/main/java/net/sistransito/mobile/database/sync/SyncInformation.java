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

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SyncInformation extends AsyncTask<String, String, String> {

	private Context context;
	private ProgressDialog pDialog;
	private String syncTitle = "AURODEJSON";
	private String tavSyncTitle = "TAVJSON";
	private String tcaSyncTitle = "TCAJSON";
	private String rrdSyncTitle = "RRDJSON";

	private String urlAitSync = "http://sistransito.net/movel/replicar/insert_auto_de.php";
	private String urlTavSync = "http://sistransito.net/movel/replicar/insert_tav.php";
	private String urlTcaSync = "http://sistransito.net/movel/replicar/insert_tca.php";
	private String urlRrdSync = "http://sistransito.net/movel/replicar/insert_rrd.php";

	private String userResponse;
	private ArrayList<NameValuePair> listParamsUsers;
	private String aitJson, tavJson, tcaJson, rrdJson;
	private boolean syncStatus;

	public SyncInformation(Context con) {
		context = con;
	}

	@Override
	protected void onPreExecute() {
		Log.d("callllll", "pree");
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

		rrdJson = (DatabaseCreator.getRrdDatabaseAdapter(context))
				.composeRrdJSONfromSQLite();

		tavJson = (DatabaseCreator.getTavDatabaseAdapter(context))
				.tavComposeJSONfromSQLite();

		tcaJson = (DatabaseCreator.getTcaDatabaseAdapter(context))
				.tcaComposeJSONfromSQLite();

		aitJson = (DatabaseCreator.getAitDatabaseAdapter(context))
				.aitComposeJSONfromSQLite();

		Log.d("rrd", "rrd" + rrdJson);
		Log.d("tav", "tav" + tavJson);
		Log.d("tca", "tca" + tcaJson);
		Log.d("ait", "ait" + aitJson);

		if (aitJson != null || tavJson != null || tcaJson != null
				|| rrdJson != null) {
			syncStatus = true;
			if (aitJson != null) {
				syncAit();
			}
			if (tavJson != null) {
				syncTav();
			}
			if (tcaJson != null) {
				syncTca();
			}
			if (rrdJson != null) {
				syncRrd();
			}
		} else {
			syncStatus = false;
		}

		Log.d("syncStatus", "" + syncStatus);
		return null;

	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		// pDialog.setProgress(Integer.parseInt(values[0]));
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d("callllllpost", "b");
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
		Log.d("callllllpost", "a");
		super.onPostExecute(result);
	}

	private void syncTav() {
		listParamsUsers = new ArrayList<NameValuePair>();
		listParamsUsers.add(new BasicNameValuePair(tavSyncTitle, tavJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlTavSync);

			JSONArray resultJsonArray = new JSONArray(userResponse);
			Log.d("jason aaray ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject obj = (JSONObject) resultJsonArray.get(loparg);
				Log.d("ssssssssssss",
						obj.getString("id") + " " + obj.getString("status"));
				if ((obj.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteTavNumber(obj.getString("id"));
					(DatabaseCreator.getTavDatabaseAdapter(context))
							.TavUpdateSyncStatus(obj.getString("id"));

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
			Log.d("jason aaray ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject obj = (JSONObject) resultJsonArray.get(loparg);
				Log.d("ssssssssssss",
						obj.getString("id") + " " + obj.getString("status"));
				if ((obj.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteTcaNumber(obj.getString("id"));
					(DatabaseCreator.getTcaDatabaseAdapter(context))
							.tcaUpdateSyncStatus(obj.getString("id"));

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

	private void syncAit() {
		listParamsUsers = new ArrayList<NameValuePair>();
		listParamsUsers.add(new BasicNameValuePair(syncTitle,
				aitJson));
		try {
			CustomHttpClient client = new CustomHttpClient();

			userResponse = client.executeHttpPost(context, listParamsUsers,
					urlAitSync);

			JSONArray resultJsonArray = new JSONArray(userResponse);
			Log.d("jason array ",
					resultJsonArray + " " + resultJsonArray.length());
			for (int loparg = 0; loparg < resultJsonArray.length(); loparg++) {
				JSONObject obj = (JSONObject) resultJsonArray.get(loparg);
				Log.d("ssssssssssss",
						obj.getString("id") + " " + obj.getString("status"));
				if ((obj.getString("status")).equals("yes")) {
					(DatabaseCreator.getNumberDatabaseAdapter(context))
							.deleteAitNumber(obj.getString("id"));
					(DatabaseCreator.getAitDatabaseAdapter(context))
							.aitUpdateSyncStatus(obj.getString("id"));
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
