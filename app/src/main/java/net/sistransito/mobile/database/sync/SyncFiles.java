package net.sistransito.mobile.database.sync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.AitDatabaseHelper;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.http.WebClient;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SyncFiles {

    private final SQLiteDatabase database;
    private final AitDatabaseHelper databaseHelper;

    StringRequest stringRequest;
    RequestQueue requestQueue;

    private final Context context;

    public SyncFiles(Context context) {
        databaseHelper = new AitDatabaseHelper(context);
        //database = databaseHelper.getReadableDatabase(ime.getIME());
        database = databaseHelper.getReadableDatabase();
        this.context = context;
    }

    public void sendAitCancelled(final String ait){

        AitData aitData = DatabaseCreator
                .getAitDatabaseAdapter(context.getApplicationContext())
                .getDataFromAitNumber(ait);

            Ion.with(context)
                .load(WebClient.URL_REQUEST)
                .setMultipartParameter(AitDatabaseHelper.AIT_NUMBER, aitData.getAitNumber())
                .setMultipartParameter(AitDatabaseHelper.CANCELL_STATUS, aitData.getCancellationStatus())
                .setMultipartParameter(AitDatabaseHelper.REASON_FOR_CANCELL, aitData.getReasonForCancellation())
                .setMultipartParameter(AitDatabaseHelper.COMPLETED_STATUS, aitData.getCompletedStatus())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result.get("sucess").getAsString().equals("true")) {
                            Log.d("Sucess: ", "true");
                            (DatabaseCreator.getAitDatabaseAdapter(context))
                                    .synchronizeAit(ait);
                        }else{
                            Log.d("Erro: ", String.valueOf(e));
                        }
                    }
                });

    }

    public void sendDataAuto(final String ait){

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, WebClient.URL_REQUEST_SSL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String isSucess = jsonObject.getString("sucess");

                            if(isSucess.equals("YES")){
                                Log.v("Response: ", isSucess);
                                DatabaseCreator.getAitDatabaseAdapter(context).synchronizeAit(ait);
                            }else{
                                Log.v("Response: ", isSucess);
                            }

                        }catch (Exception e){
                            Log.v("Erro: ", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Error 109: ", error.getMessage());
                    }
                }) {
                @Override
                protected Map<String, String> getParams(){

                    String stringImage1, stringImage2, stringImage3, stringImage4;

                    String root = Environment.getExternalStorageDirectory().toString() + "/" + context.getApplicationContext().getString(R.string.folder_app) + "/";
                    int lengthRoot = root.length();

                    AitData aitData = DatabaseCreator
                            .getAitDatabaseAdapter(context.getApplicationContext())
                            .getDataFromAitNumber(ait);

                    Map<String, String> params = new HashMap<>();

                    if (aitData.getPhoto1() != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto1());
                        stringImage1 = Routine.getStringImage(bitmap);
                        params.put("foto1", aitData.getPhoto1().substring(lengthRoot));
                        params.put("image1", stringImage1);
                    } else if (aitData.getPhoto2() != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto2());
                        stringImage2 = Routine.getStringImage(bitmap);
                        params.put("foto2", aitData.getPhoto2().substring(lengthRoot));
                        params.put("image2", stringImage2);
                    } else if (aitData.getPhoto3() != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto3());
                        stringImage3 = Routine.getStringImage(bitmap);
                        params.put("foto3", aitData.getPhoto3().substring(lengthRoot));
                        params.put("image3", stringImage3);
                    } else if (aitData.getPhoto4() != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto4());
                        stringImage4 = Routine.getStringImage(bitmap);
                        params.put("foto4", aitData.getPhoto4().substring(lengthRoot));
                        params.put("image4", stringImage4);
                    }

                    params.put(AitDatabaseHelper.AIT_NUMBER, aitData.getAitNumber());
                    params.put(AitDatabaseHelper.PLATE, aitData.getPlate());
                    params.put(AitDatabaseHelper.VEHICLE_STATE, aitData.getStateVehicle());
                    params.put(AitDatabaseHelper.CHASSI, aitData.getChassi());
                    params.put(AitDatabaseHelper.COUNTRY, aitData.getCountry());
                    params.put(AitDatabaseHelper.VEHICLE_MODEL, aitData.getVehicleModel());
                    params.put(AitDatabaseHelper.VEHICLE_COLOR, aitData.getVehycleColor());
                    params.put(AitDatabaseHelper.SPECIES, aitData.getVehicleSpecies());
                    params.put(AitDatabaseHelper.CATEGORY, aitData.getVehicleCategory());
                    params.put(AitDatabaseHelper.CANCELL_STATUS, aitData.getCancellationStatus());
                    params.put(AitDatabaseHelper.REASON_FOR_CANCELL, aitData.getReasonForCancellation());
                    params.put(AitDatabaseHelper.SYNC_STATUS, aitData.getSyncStatus());
                    params.put(AitDatabaseHelper.AIT_LATITUDE, aitData.getAitLatitude());
                    params.put(AitDatabaseHelper.AIT_LONGITUDE, aitData.getAitLongitude());
                    params.put(AitDatabaseHelper.AIT_DATE_TIME, aitData.getAitDateTime());
                    //params.put(AitDatabaseHelper.COMPLETED_STATUS, aitData.getCompletedStatus());
                    params.put(AitDatabaseHelper.APPROACH, aitData.getApproach());
                    params.put(AitDatabaseHelper.DRIVER_NAME, aitData.getConductorName());
                    params.put(AitDatabaseHelper.DRIVER_LICENSE, aitData.getCnhPpd());
                    params.put(AitDatabaseHelper.DRIVER_LICENSE_STATE, aitData.getCnhState());
                    params.put(AitDatabaseHelper.DOCUMENT_TYPE, aitData.getDocumentType());
                    params.put(AitDatabaseHelper.DOCUMENT_NUMBER, aitData.getDocumentNumber());
                    params.put(AitDatabaseHelper.ADDRESS, aitData.getAddress());
                    params.put(AitDatabaseHelper.AIT_DATE, aitData.getAitDate());
                    params.put(AitDatabaseHelper.AIT_TIME, aitData.getAitTime());
                    params.put(AitDatabaseHelper.CITY_CODE, aitData.getCityCode());
                    params.put(AitDatabaseHelper.CITY, aitData.getCity());
                    params.put(AitDatabaseHelper.STATE, aitData.getState());
                    params.put(AitDatabaseHelper.INFRATION, aitData.getInfraction());
                    params.put(AitDatabaseHelper.FLAMING_CODE, aitData.getFramingCode());
                    params.put(AitDatabaseHelper.UNFOLDING, aitData.getUnfolding());
                    params.put(AitDatabaseHelper.ARTICLE, aitData.getArticle());
                    params.put(AitDatabaseHelper.TCA_NUMBER, aitData.getTcaNumber());
                    params.put(AitDatabaseHelper.EQUIPMENT_DESCRIPTION, aitData.getDescription());
                    params.put(AitDatabaseHelper.EQUIPMENT_BRAND, aitData.getEquipmentBrand());
                    params.put(AitDatabaseHelper.EQUIPMENT_MODEL, aitData.getEquipmentModel());
                    params.put(AitDatabaseHelper.EQUIPMENT_SERIAL, aitData.getSerialNumber());
                    params.put(AitDatabaseHelper.MEASUREMENT_PERFORMED, aitData.getMeasurementPerformed());
                    params.put(AitDatabaseHelper.REGULATED_VALUE, aitData.getRegulatedValue());
                    params.put(AitDatabaseHelper.VALUE_CONSIDERED, aitData.getValueConsidered());
                    params.put(AitDatabaseHelper.ALCOHOL_TEST_NUMBER, aitData.getAlcoholTestNumber());
                    params.put(AitDatabaseHelper.RETREAT, aitData.getRetreat());
                    params.put(AitDatabaseHelper.PROCEDURES, aitData.getProcedures());
                    params.put(AitDatabaseHelper.OBSERVATION, aitData.getObservation());
                    params.put(AitDatabaseHelper.SHIPPER_IDENTIFICATION, aitData.getShipperIdentification());
                    params.put(AitDatabaseHelper.CPF_SHIPPER, aitData.getCpfShipper());
                    params.put(AitDatabaseHelper.CNPJ_SHIPPER, aitData.getCnpShipper());
                    params.put(AitDatabaseHelper.CARRIER_IDENTIFICATION, aitData.getCarrierIdentification());
                    params.put(AitDatabaseHelper.CPF_CARRIER, aitData.getCpfCarrier());
                    params.put(AitDatabaseHelper.CNPJ_CARRIER, aitData.getCnpjCarrier());
                    params.put(AitDatabaseHelper.COMPLETED_STATUS, aitData.getCompletedStatus());

                    //Log.d("Params", String.valueOf(params));

                    return params;

                }

        };

        requestQueue.add(stringRequest);

    }

}
