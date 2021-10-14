package net.sistransito.mobile.plate.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class CreatePlateRawDataFromJson {
	private DataFromPlate dataFromPlate;

	public CreatePlateRawDataFromJson(String data, Context context) {
		setDataFromPlate(data);
		dataFromPlate = new DataFromPlate(context);
		setDataFromPlate(data);
	}

	public DataFromPlate getDataFromPlate() {
		return dataFromPlate;
	}

	public void setDataFromPlate(String data) {
		JSONObject jsonObject;

		int success = 10;
		final int SUCCESS = 1;

		try {
			jsonObject = new JSONObject(data);
			success = jsonObject.getInt("success");

			if (success == SUCCESS) {

				try {

					dataFromPlate.setPlate(jsonObject.getString("placa"));

					dataFromPlate.setCity(jsonObject.getString("municipio"));

					dataFromPlate.setState(jsonObject.getString("uf"));

					dataFromPlate.setRanavam(jsonObject.getString("renavam"));

					dataFromPlate.setChassi(jsonObject.getString("chassi"));

					dataFromPlate.setBrand(jsonObject.getString("marca"));

					dataFromPlate.setModel(jsonObject.getString("modelo"));

					dataFromPlate.setColor(jsonObject.getString("cor"));

					dataFromPlate.setType(jsonObject.getString("tipo"));

					dataFromPlate.setSpecies(jsonObject.getString("especie"));

					dataFromPlate.setCategory(jsonObject.getString("categoria"));

					dataFromPlate.setYearManufacture(jsonObject.getString("ano_fabricacao"));

					dataFromPlate.setYearModel(jsonObject
							.getString("ano_modelo"));

					dataFromPlate.setLicenceYear(jsonObject
							.getString("ano_licenciamento"));

					dataFromPlate.setLicenceData(jsonObject
							.getString("data_licenciamento"));

					dataFromPlate.setLicenceStatus(jsonObject
							.getString("status_licenciamento"));

					dataFromPlate.setIpva(jsonObject.getString("ipva"));

					dataFromPlate.setInsurance(jsonObject.getString("seguro"));

					dataFromPlate.setInfractions(jsonObject
							.getString("infracoes"));

					dataFromPlate.setInfrationAmout(jsonObject.getString("valor_infracoes"));

					dataFromPlate.setTheftRecord(jsonObject.getString("registro_furto"));

					dataFromPlate.setRestrictions(jsonObject
							.getString("restricoes"));

				} catch (Exception e) {

				}

			} else {
				dataFromPlate = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
