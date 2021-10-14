package net.sistransito.mobile.number;

import android.content.Context;
import android.util.Log;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateNumberDataFromJson {
	private int type;
	private Context context;
	private String jsonString;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private ArrayList<String> arrayList;
	private boolean isSave;

	public CreateNumberDataFromJson(int type, Context context, String jsonString) {
		this.type = type;
		this.context = context;
		this.jsonString = jsonString;

	}

	public boolean saveNumeroData() {
		if (jsonString != null) {
			try {
				jsonArray = new JSONArray(jsonString);
				arrayList = new ArrayList<String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					arrayList.add(jsonObject.getString("numero_tab"));
				}

				//Log.d("AitNumber: ", String.valueOf(jsonArray));

			} catch (JSONException e) {
				e.printStackTrace();
			}
			switch (type) {
			case AppConstants.AIT_NUMBER:
				isSave = (DatabaseCreator.getNumberDatabaseAdapter(context))
						.setAitNumber(arrayList);
				break;
			case AppConstants.TAV_NUMBER:
				isSave = (DatabaseCreator.getNumberDatabaseAdapter(context))
						.setTavNumber(arrayList);
				break;
			case AppConstants.TCA_NUMBER:
				isSave = (DatabaseCreator.getNumberDatabaseAdapter(context))
						.setTcaNumber(arrayList);
				break;
			case AppConstants.RRD_NUMBER:
				isSave = (DatabaseCreator.getNumberDatabaseAdapter(context))
						.setRrdNumber(arrayList);
				break;
			}
		}
		return isSave;
	}

}
