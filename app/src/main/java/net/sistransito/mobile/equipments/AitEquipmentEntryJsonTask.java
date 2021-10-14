package net.sistransito.mobile.equipments;

import android.content.Context;

import net.sistransito.mobile.database.DatabaseCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AitEquipmentEntryJsonTask {
	private AitEquipmentEntry aitEquipmentEntry;
	private ArrayList<AitEquipmentEntry> entries;
	private JSONArray jsonArray;
	private JSONObject object;
	private Context context;
	private String json;
	public AitEquipmentEntryJsonTask(String json, Context context) {
		this.context = context;
		this.json = json;
	}

	public void prepareEntry() {

		try {
			entries = new ArrayList<AitEquipmentEntry>();
			
			//
			jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				object = jsonArray.getJSONObject(i);
				aitEquipmentEntry = new AitEquipmentEntry(
						object.getString("description"),
						object.getString("brand"),
						object.getString("model"),
						object.getString("validity"),
						object.getString("serial_number"));
				entries.add(aitEquipmentEntry);
			}
			(DatabaseCreator.getPrepopulatedDBOpenHelper(context))
					.setEquipmentCursor(entries);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
