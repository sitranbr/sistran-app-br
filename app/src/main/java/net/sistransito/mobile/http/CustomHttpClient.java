package net.sistransito.mobile.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CustomHttpClient {
	public final String NORMAL = "NORMAL";
	public final int HTTP_TIMEOUT = 30 * 1000;
	private HttpClient mHttpClient;
	//public String placa, modelo_veiculo, cor_veiculo;

	public HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
//			final cz.msebera.android.httpclient.params.HttpParams params = mHttpClient.getParams();
//			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
//			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
//			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	public String executeHttpPost(Context context,
			ArrayList<NameValuePair> postParameters, String url) {
		String jsonText = "";

		try {
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEntity;
			formEntity = new UrlEncodedFormEntity(postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
			Log.d("jsontext: ", jsonText.toString() + "JSON");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return jsonText;

	}

	public String executeHttpGet(String url) throws Exception {
		String jsonText = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			//request.addHeader("Authorization", "Basic ZW5naW5IOmVuZ2luZQ==");
			request.addHeader("Cache-Control", "max-age=640000");
			request.addHeader("Accept", "application/json");
			//request.addHeader("Host", "131.221.217.144:8090");
			request.addHeader("Connection", "Keep-Alive");
			request.addHeader("Accept_Encoding", "gzip");
			request.addHeader("User-Agent", "okhttp/3.9.1");
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
			Log.d("jsontext: ", jsonText.toString() + "JSON");
		} catch (Exception e) {
		}
		return jsonText;
	}
}
