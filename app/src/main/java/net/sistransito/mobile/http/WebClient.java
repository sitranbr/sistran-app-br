package net.sistransito.mobile.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.sistransito.mobile.database.TinyDB;

public class WebClient {

    private static final String API_URl = "http://191.252.221.62:3000/";

    private static final String BASE_URl = "http://api.sistran.app/api/";
    //private static final String BASE_URl = "http://sistransito.com.br/movel/dosis.pl?";
    //public static final String BASE_URl_TESTE = "http://131.221.217.144:8090/api/v1/renavam/consultaPorPlacaTeste?placa=QDA3455&idEstado=0";

    public static final String CNH_URL = BASE_URl + "cnh?dadoscnh=";

    public static final String PLACA_SEARCH = API_URl + "api/plate/";
    public static final String LOGIN_URl = BASE_URl + "auth/dologin?user=";


    public static final String URL_AIT_NUMBER = BASE_URl + "ait_number";
    public static final String URL_TAV_NUMBER = BASE_URl + "tav_number";
    public static final String URL_TCA_NUMBER = BASE_URl + "tca_number";
    public static final String URL_RRD_NUMBER = BASE_URl + "rrd_number";
    public static final String URL_EQUIPMENTS = BASE_URl + "equipment";
    public static final String URL_CLOGIN = BASE_URl + "clogin";

    public static final String URL_REQUEST_SSL = "https://sistransito.com.br/movel/api/uploadApi.php";
    public static final String URL_REQUEST = BASE_URl + "ait_canceled";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler, Context c) {
        TinyDB tdb = new TinyDB(c);
        params.put("X-API-KEY", tdb.getString("token"));
        client.cancelAllRequests(true);
        client.get(url, params, responseHandler);
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.setConnectTimeout(1000 * 1000);
        client.cancelAllRequests(true);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.cancelAllRequests(true);
        client.setURLEncodingEnabled(true);
        client.post(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler, Context c) {
        TinyDB tdb = new TinyDB(c);
        params.put("X-API-KEY", tdb.getString("token"));
        client.cancelAllRequests(true);
        client.post(url, params, responseHandler);
    }

    public static String getLoginUrl(String email, String password) {
        return LOGIN_URl + email + "&password=" + password;
    }
}
