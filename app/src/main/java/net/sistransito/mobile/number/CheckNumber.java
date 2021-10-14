package net.sistransito.mobile.number;

import android.content.Context;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.http.WebClient;

public class CheckNumber {

    private Context context;
    private String jsonText = null;
    private CreateNumberDataFromJson numberDataFromJson;

    public CheckNumber(Context context) {
        super();
        this.context = context;
    }

    public void check() {

        if ((DatabaseCreator.getNumberDatabaseAdapter(context))
                .isNeedUpdateAit()) {

            try {
                jsonText = AppObject.getHttpClient().executeHttpGet(
                        WebClient.URL_AIT_NUMBER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            numberDataFromJson = new CreateNumberDataFromJson(
                    AppConstants.AIT_NUMBER, context, jsonText);
            numberDataFromJson.saveNumeroData();

        }
        if ((DatabaseCreator.getNumberDatabaseAdapter(context))
                .isNeedTavUpdate()) {


            try {
                jsonText = AppObject.getHttpClient().executeHttpGet(
                        WebClient.URL_TAV_NUMBER);
            } catch (Exception e) {
            }
            numberDataFromJson = new CreateNumberDataFromJson(
                    AppConstants.TAV_NUMBER, context, jsonText);
            numberDataFromJson.saveNumeroData();

        }
        if ((DatabaseCreator.getNumberDatabaseAdapter(context))
                .isNeedTcaUpdate()) {


            try {
                jsonText = AppObject.getHttpClient().executeHttpGet(
                        WebClient.URL_TCA_NUMBER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            numberDataFromJson = new CreateNumberDataFromJson(
                    AppConstants.TCA_NUMBER, context, jsonText);
            numberDataFromJson.saveNumeroData();

        }
        if ((DatabaseCreator.getNumberDatabaseAdapter(context))
                .isNeedRrdUpdate()) {


            try {
                jsonText = AppObject.getHttpClient().executeHttpGet(
                        WebClient.URL_RRD_NUMBER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            numberDataFromJson = new CreateNumberDataFromJson(
                    AppConstants.RRD_NUMBER, context, jsonText);
            numberDataFromJson.saveNumeroData();
        }
    }
}

