package net.sistransito.mobile.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rey.material.widget.TextView;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.fragment.AnyPageChangeListener;
import net.sistransito.mobile.http.WebClient;
import net.sistransito.mobile.main.MainUserActivity;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sistransito.mobile.util.AESEncryption;
import net.sistransito.mobile.util.DialogMaterial;
import net.sistransito.mobile.util.Routine;
import net.sistransito.mobile.util.User;
import net.sistransito.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoginFragment extends Fragment implements OnClickListener {

    private View view;
    private AnyPageChangeListener anyPageChangeListener;
    private TextView btnRegister;
    private Button btnLogin;
    private EditText etEmail, etPassword;
    private InputMethodManager imm;
    private ProgressDialog pd;
    private TimeAndIme ime;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        anyPageChangeListener = (ActivityLogin) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, null, false);
        imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        initializedView();
        return view;
    }

    private void initializedView() {
        ime = new TimeAndIme(getActivity());
        btnRegister = (TextView) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        pd = new ProgressDialog(getActivity(), getResources().getString(R.string.processing));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                anyPageChangeListener.onPageChange(AppConstants.LOGIN_FRAGMENT_1);
                break;
            case R.id.btn_login:
                if (checkInput()) {
                    if (NetworkConnection.isNetworkAvailable(getActivity())) {
                        //imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
                        Routine.closeKeyboard(btnLogin,getActivity());
                        userLogin();
                    } else {
                        DialogMaterial.getBottomSheet(getResources().getString(R.string.sem_conexao), Color.RED, getActivity()).show();
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError(getResources().getString(
                    R.string.texto_inserir_email));
            etEmail.requestFocus();
            return false;
        } else if (etPassword.getText().toString().equals("")) {
            etPassword.setError(getResources().getString(
                    R.string.texto_inserir_senha));
            etPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        String url = WebClient.getLoginUrl(email, password);

        WebClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pd.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pd.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);

                LoginData userPassword = new LoginData();
                String dataEncrypt = null;

                try {
                    dataEncrypt = AESEncryption.encrypt(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("Success", user.toString());
                if (user.getSuccess().compareTo("1") == 0) {
                    userPassword.setPassword(dataEncrypt);
                    AppObject.getTinyDB(getActivity()).putBoolean(AppConstants.isLogin, true);
                    AppObject.getTinyDB(getActivity()).putObject(AppConstants.user, user);
                    getActivity().startActivity(new Intent(getActivity(), MainUserActivity.class));
                    getActivity().finish();
                    Log.d("Imei: ", ime.getIME() + userPassword.getPassword());
                } else {
                    showLoginFailMgs();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showLoginFailMgs();
            }
        });
    }

    private void showLoginFailMgs() {
        DialogMaterial.getBottomSheet(getResources().getString(R.string.login_fail), Color.RED, getActivity()).show();
    }

}
