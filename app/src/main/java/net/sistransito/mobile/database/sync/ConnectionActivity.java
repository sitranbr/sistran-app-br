package net.sistransito.mobile.database.sync;

/**
 * Está Activity deve sar transformada em classe java para ser usada sempre que for necessário efetuar conexão
 * com o servidor via https
 * @See SyncFiles.java, method sendDataAuto
 *
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import net.sistransito.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class ConnectionActivity extends AppCompatActivity {

    public SSLContext context = null;
    public SSLContext context1 = null;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

    }

    public void doBasicAuth(View view) {
        new Connection().execute();
    }

    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                int isSucess = jsonObject.getInt("success");


                if(isSucess == 1) {
                    Log.d("SucessHttps: ", String.valueOf(isSucess));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void connect() {
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(
                    new HostnameVerifier() { @Override public boolean verify(String hostname, SSLSession session) { return true; } });
            InputStream caInput = getAssets().open("load-der.crt");
            //InputStream caInput = new BufferedInputStream(f);
            Certificate ca = null;
            try {
                ca = cf.generateCertificate(caInput);
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                caInput.close();
            }

           // openssl s_client -servername abc.xyz.cc -connect abc.xyz.cc:443 2>/dev/null | openssl x509 -trustout

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            // URL responsável por fazer o login no sistema
            url = new URL("https://sistransito.com.br/movel/dosis.pl?op=fazlogin&login=sac&senha=letera");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection)url.openConnection();
            final String basicAuth = "Basic " + Base64.encodeToString("user:pass".getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", basicAuth);
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlConnection.setSSLSocketFactory(context.getSocketFactory());
        try {
            System.out.println(urlConnection.getResponseMessage());
            System.out.println(urlConnection.getResponseCode());
            if(urlConnection.getResponseCode() == 200) {
                InputStream in = urlConnection.getInputStream();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder out = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                //System.out.println(out.toString());
                response = out.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}