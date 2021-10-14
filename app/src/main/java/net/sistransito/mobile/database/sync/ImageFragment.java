package net.sistransito.mobile.database.sync;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.util.AESCrypt2;
import net.sistransito.mobile.util.AESEncryption;
import net.sistransito.mobile.util.RetrovitActivity;
import net.sistransito.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class ImageFragment extends Fragment implements
        View.OnClickListener{

    private View view;
    public static final String UPLOAD_URL = "https://sistransito.com.br/movel/api/upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonView;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;

    public static ImageFragment newInstance() {
        return new ImageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_image, null, false);

        buttonChoose = (Button) view.findViewById(R.id.buttonChoose);
        buttonUpload = (Button) view.findViewById(R.id.buttonUpload);
        buttonView = (Button) view.findViewById(R.id.buttonViewImage);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        String login = "sandro.araujo";
        String senha = "stasera10";

        String senhaMD5 = MD5(senha);

        Log.d("Autorization: ", getB64Auth(login, senhaMD5));
        Log.d("Senha: ", senha);
        Log.d("Senha MD5: ", senhaMD5);

        String dataEncrypt = null;
        String dataDecrypt = null;
        String secreatKey = null;

        try {
            dataEncrypt = AESEncryption.encrypt("Digirir sem possur CNH e PPD");
            dataDecrypt = AESEncryption.decrypt(dataEncrypt);
            //secreatKey = AESEncryption.setSecreatKey("sampleTextsample", "exampleSalt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("Senha AES: ", dataEncrypt);
        Log.i("Normal: ", dataDecrypt);

        AESCrypt2 aesCrypt;

        try {
            aesCrypt = new AESCrypt2();
            dataEncrypt = aesCrypt.encrypt("Digirir sem possur CNH e PPD");
            dataDecrypt = aesCrypt.decrypt(dataEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Senha AES_: ", dataEncrypt);
        Log.d("Normal_: ", dataDecrypt);

        return view;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //getActivity().startActivityFromFragment(ImageFragment.this, Intent.createChooser(intent, "Selecionar arquivo"),PICK_IMAGE_REQUEST);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog pDialog;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = null;
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMax(100);
                pDialog.setCancelable(false);
                pDialog.setMessage(getActivity().getResources().getString(R.string.sincronizacao_mgs));
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                if(result.equals("1")) {
                    Toast.makeText(getContext(), "Upload concluído.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Ocorreu um erro no upload.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {

                /*String root = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.folder_app) + "/";
                int tamanhoRoot = root.length();

                Log.d("Tamanho Root", String.valueOf(tamanhoRoot) + "-" + root);*/


                    AitData aitData = DatabaseCreator
                            .getAitDatabaseAdapter(getActivity())
                            .getDataFromAitNumber("TL00009279");

                    //Bitmap bitmap = params[0];
                    Bitmap bitmap = BitmapFactory.decodeFile(aitData.getPhoto1());
                    String uploadImage = getStringImage(bitmap);

                    HashMap<String, String> data = new HashMap<>();

                    data.put("nome", aitData.getConductorName());
                    //data.put("foto", dadosAuto.getFoto1().substring(tamanhoRoot));
                     Log.d("Imagem", uploadImage);
                    data.put(UPLOAD_KEY, uploadImage);

                    String result = rh.sendPostRequest(UPLOAD_URL, data);

                    return result;

            }

        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }

        if(v == buttonUpload){
            if (NetworkConnection.isInternetConnected(getActivity())) {
               /* SyncFiles sync = new SyncFiles(getContext());
                sync.uploadImage("TL00007114", "/storage/emulated/0/storage/emulated/0/SistransitoMobile");*/
                uploadImage();
            } else {
                Toast.makeText(getContext(),getResources().getString(R.string.sem_conexao),Toast.LENGTH_LONG).show();
            }
        }

        if(v == buttonView){
            viewImage();
        }
    }

    private void viewImage() {
        getActivity().startActivity(new Intent(getActivity(), RetrovitActivity.class));
        getActivity().finish();
    }

    private String getB64Auth (String login, String pass) {
        String source=login+":"+pass;
        String ret="Basic "+Base64.encodeToString(source.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
        return ret;
    }

}
