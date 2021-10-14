package net.sistransito.mobile.plate.data;


import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.gc.materialdesign.widgets.ProgressDialog;

import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.CallBackPlate;
import net.sistransito.mobile.http.WebClient;

public class PlateHttpResultAsyncTask extends AsyncTask<String, Integer, String> {
    public ProgressDialog pDialog;
    private String jsonText = null;
    private Context context;
    private boolean isOffline;
    private DataFromPlate dataPlate;
    private String sPlate, searchType;
    public CallBackPlate listener;
    private CreatePlateRawDataFromJson createPlacaRawData;
    private Location location;

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        cancel(true);
        listener.callBack(null, isOffline);
    }

    public PlateHttpResultAsyncTask(final CallBackPlate listener,
                                    Context context, final boolean isOffline, String sPlate, String searchType,
                                    Location location) {
        this.context = context;
        this.isOffline = isOffline;
        this.sPlate = sPlate;
        this.searchType = searchType;
        this.listener = listener;
        this.location = location;
        pDialog = null;
        pDialog = new ProgressDialog(context, "Carregando\n" + " .....");
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {

        if (isOffline) {
            dataPlate = (DatabaseCreator
                    .getSearchDataInCard(context)).getPlateData(sPlate, searchType);

            (DatabaseCreator.getSearchPlateDatabaseAdapter(context))
                    .insertPlacaSearchData(dataPlate);
        } else {

            try {
                jsonText = AppObject.getHttpClient()
                        .executeHttpGet(WebClient.PLACA_SEARCH + sPlate);

                createPlacaRawData = new CreatePlateRawDataFromJson(jsonText,
                        context);
                dataPlate = createPlacaRawData.getDataFromPlate();
                /*dataPlate.setLATITUDE(String.valueOf(location
                        .getLatitude()));
                dataPlate.setLONGITUDE(String.valueOf(location
                        .getLongitude()));*/
                (DatabaseCreator.getSearchPlateDatabaseAdapter(context))
                        .insertPlacaSearchData(dataPlate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if ((pDialog != null) && (pDialog.isShowing())) {
            pDialog.dismiss();
        }
        listener.callBack(dataPlate, isOffline);
        super.onPostExecute(result);
    }

}
