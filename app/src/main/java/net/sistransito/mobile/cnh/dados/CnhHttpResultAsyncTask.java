package net.sistransito.mobile.cnh.dados;


import android.content.Context;
import android.os.AsyncTask;

import com.gc.materialdesign.widgets.ProgressDialog;

import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.CallBackCnh;
import net.sistransito.mobile.http.WebClient;

public class CnhHttpResultAsyncTask extends AsyncTask<String, Integer, String> {
    public ProgressDialog pDialog;
    private String jsonText = null;
    private Context context;
    private boolean isOffline;
    private DataFromCnh dataFromCnh;
    public CallBackCnh listener;
    private CreateCnhRawDataFromJson createCNHRawData;
    private String sTypeSearch, sRegister, sCnh;

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        cancel(true);
        listener.callBack(null);
    }

    public CnhHttpResultAsyncTask(final CallBackCnh listener,
                                  Context context, final boolean isOffline, DataFromCnh dataFromCnh,
                                  String sRegister, String sTypeSearch) {
        this.context = context;
        this.isOffline = isOffline;
        this.dataFromCnh = dataFromCnh;
        this.sRegister = sRegister;
        this.listener = listener;
        this.sTypeSearch = sTypeSearch;
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

        //Log.i("isOffline", String.valueOf(isOffline) + " - " + registro);

        if (isOffline) {

            if(dataFromCnh == null){
                DataFromCnh dataFromCNH = new DataFromCnh();

                dataFromCNH.setRegister(sRegister);

                this.dataFromCnh = (DatabaseCreator
                        .getSearchDataInCard(context)).getCnhData(dataFromCNH, sTypeSearch);

            } else {

                dataFromCnh = (DatabaseCreator
                        .getSearchDataInCard(context)).getCnhData(dataFromCnh, sTypeSearch);

            }

        } else {

            try {

                jsonText = AppObject.getHttpClient()
                        .executeHttpGet(WebClient.CNH_URL + sCnh);

                createCNHRawData = new CreateCnhRawDataFromJson(jsonText,
                        context);
                dataFromCnh = createCNHRawData.getDataFromCNH();
                /*fromCNH.setLATITUDE(String.valueOf(location
                        .getLatitude()));
                fromCNH.setLONGITUDE(String.valueOf(location
                        .getLongitude()));
                (DatabaseCreator.getPlacaSearchDatabaseAdapter(context))
                        .insertPlacaSearchData(fromCNH);*/
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
        listener.callBack(dataFromCnh);
        super.onPostExecute(result);
    }

}
