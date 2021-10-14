package net.sistransito.mobile.database.sync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.http.WebClient;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyFragment extends Fragment{

    View view;
    Button btnEnviar;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public static VolleyFragment newInstance() {
        return new VolleyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_volley, null, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        initView();

        return view;

    }

    private void initView() {

        btnEnviar = (Button) view.findViewById(R.id.btn_button);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkConnection.isInternetConnected(getActivity())) {
                    validUser();
                } else {
                    //Toast.makeText(getContext(),getResources().getString(R.string.sem_conexao),Toast.LENGTH_LONG).show();
                    Routine.showAlert(getActivity().getResources().getString(R.string.sem_conexao), getActivity());
                    //Criar um alarm para enviar assim que houver conexão
                }

            }
        });
    }

    public void validUser(){

        stringRequest = new StringRequest(Request.Method.POST, WebClient.URL_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            Boolean isError = jsonObject.getBoolean("erro");
                            String nome = jsonObject.getString(("nome"));
                            String foto = jsonObject.getString(("foto"));
                            String image = jsonObject.getString(("image"));

                            if(isError){
                                Log.v("Response: ", String.valueOf(isError));
                                Routine.showAlert("Erro no upload", getContext());
                            }else{
                                Log.v("Response: ", String.valueOf(isError));
                                Log.v("Nome: ", nome);
                                //Log.v("Foto: ", foto);
                                //Log.v("Image: ", image);
                                Routine.showAlert("Upload realizado com sucesso", getContext());
                            }

                        }catch (Exception e){
                            Log.v("Erro: ", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Error: ", error.getMessage());
                    }
                }) {
                @Override
                protected Map<String, String> getParams(){

                    String root = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.folder_app) + "/";
                    int lengthRoot = root.length();

                    AitData aitData = DatabaseCreator
                            .getAitDatabaseAdapter(getActivity())
                            .getDataFromAitNumber("TL00009191");

                    Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto1());
                    String stringImage = Routine.getStringImage(bitmap);

                    Map<String, String> params = new HashMap<>();
                    params.put("name", aitData.getConductorName());
                    params.put("photo", aitData.getPhoto1().substring(lengthRoot));
                    params.put("image", stringImage);
                    return params;

                }

        };

        requestQueue.add(stringRequest);

    }

}
