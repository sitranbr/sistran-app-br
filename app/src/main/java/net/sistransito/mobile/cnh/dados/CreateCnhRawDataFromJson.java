package net.sistransito.mobile.cnh.dados;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateCnhRawDataFromJson {
	private DataFromCnh dataFromCNH;

	public CreateCnhRawDataFromJson(String data, Context context) {
		setDataFromCNH(data);
		dataFromCNH = new DataFromCnh();
		setDataFromCNH(data);
	}

	public DataFromCnh getDataFromCNH() {
		return dataFromCNH;
	}

	public void setDataFromCNH(String data) {
		JSONObject jsonObject;

		int success;
		final int SUCCESS = 1;

		try {
			jsonObject = new JSONObject(data);
			success = jsonObject.getInt("success");

			if (success == SUCCESS) {

				try {

					dataFromCNH.setName(jsonObject.getString("nome"));

					dataFromCNH.setBirthDate(jsonObject.getString("data_nascimento"));

					dataFromCNH.setRegister(jsonObject.getString("registro"));

					dataFromCNH.setState(jsonObject.getString("uf"));

					dataFromCNH.setCnhValidity(jsonObject.getString("validade"));

					dataFromCNH.setCnhPoints(jsonObject.getString("pontos"));

					dataFromCNH.setCnhObservation(jsonObject.getString("obs"));

				} catch (Exception e) {

				}

			} else {
				dataFromCNH = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
