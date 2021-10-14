package net.sistransito.mobile.plate.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.ait.lister.AitLister;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.fragment.CallBackPlate;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.plate.data.PlateHttpResultAsyncTask;
import net.sistransito.mobile.plate.data.DataFromPlate;
import net.sistransito.mobile.plate.data.PlateViewFormat;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

public class ConsultPlateFragment extends Fragment implements
        OnClickListener {

    private View view;
    private LinearLayout llParentResultView, llChildResultView, llPlateSearch, llVis;
    private TextView tvResultShow;
    private Button btnCreateAit, btnPlateSearch;
    private EditText etPlateLetters, etPlateNumbers, etChassi, etPlateMercosul, etVisNumber,
            etSealNumber, etEngineNumber;
    private CheckBox cbOfflineSerach;
    private RadioButton rbPlateSearch, rbChassiSearch, rbMercosulPlateSearch, rbVisNumber,
            rbSealNumber, rbEngineNumber;
    private RadioGroup rgTypeSearch, rgVisTypeSearch, rgSealTypeSearch;
    private String plateOrChassis, emptyField, typeSearch;
    private final int LENGTH_PLATE_CHARACTER = 3;
    private final int LENGTH_PLATE_NUMBER = 4;
    private final int LENGTH_CHASSI = 17;
    private final int LENGTH_PLATE_MERCOSUL = 7;
    private final int NUMERO_VIS = 8;
    private PlateHttpResultAsyncTask httpResultAnysTask;
    private EditTextChange etCharater, etNumber;
    private Boolean textChangeState = true;
    private DataFromPlate dataPlate;

    //private GPSTracker gps;

    public static ConsultPlateFragment newInstance() {
        return new ConsultPlateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.plate_search_fragment, null, false);
        //checkCancelAutos();
        initilizedView();
        return view;
    }

    private void checkCancelAutos() {

        String idAuto = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
                .getAitNumber();

        if(idAuto != null) {

            startActivity(new Intent(getActivity(), AitLister.class));
            getActivity().finish();

        }

    }

    private void initilizedView() {

        llPlateSearch = (LinearLayout) view.findViewById(R.id.ll_placa_search);
        llVis = (LinearLayout) view.findViewById(R.id.ll_radio_two);
        llParentResultView = (LinearLayout) view
                .findViewById(R.id.ll_parent_result_view);
        llChildResultView = (LinearLayout) view
                .findViewById(R.id.ll_child_result_view);
        tvResultShow = (TextView) view.findViewById(R.id.tv_result_placa_show);
        tvResultShow.setOnClickListener(this);
        btnCreateAit = (Button) view.findViewById(R.id.btn_autuar);
        btnCreateAit.setOnClickListener(this);
        btnPlateSearch = (Button) view.findViewById(R.id.btn_consultar_placa);
        btnPlateSearch.setOnClickListener(this);
        etPlateLetters = (EditText) view
                .findViewById(R.id.et_letras_da_placa);
        etPlateNumbers = (EditText) view
                .findViewById(R.id.et_numeros_da_placa);
        etChassi = (EditText) view.findViewById(R.id.et_chassi_input);
        etPlateMercosul = (EditText) view.findViewById(R.id.et_mercosul_input);
        etVisNumber = (EditText) view.findViewById(R.id.et_vis_input);
        etSealNumber = (EditText) view.findViewById(R.id.et_lacre_input);
        etEngineNumber = (EditText) view.findViewById(R.id.et_motor_input);
        cbOfflineSerach = (CheckBox) view
                .findViewById(R.id.cb_pesquisa_offline);
        rgTypeSearch = (RadioGroup) view.findViewById(R.id.rg_type_search);
        rgVisTypeSearch = (RadioGroup) view.findViewById(R.id.rg_type_search_two);
        rgSealTypeSearch = (RadioGroup) view.findViewById(R.id.rg_type_search_lacre);
        rbPlateSearch = (RadioButton) view.findViewById(R.id.rb_search_placa);
        rbChassiSearch = (RadioButton) view.findViewById(R.id.rb_search_chassi);
        rbMercosulPlateSearch = (RadioButton) view.findViewById(R.id.rb_search_mercosul);
        rbVisNumber = (RadioButton) view.findViewById(R.id.rb_search_vis);
        rbSealNumber = (RadioButton) view.findViewById(R.id.rb_search_lacre);
        rbEngineNumber = (RadioButton) view.findViewById(R.id.rb_search_motor);
        etCharater = new EditTextChange(R.id.et_letras_da_placa);
        etNumber = new EditTextChange(R.id.et_numeros_da_placa);

        etChassi.addTextChangedListener(new EditTextChange(
                R.id.et_chassi_input));

        etPlateMercosul.addTextChangedListener(new EditTextChange(
                R.id.et_mercosul_input));

        etVisNumber.addTextChangedListener(new EditTextChange(
                R.id.et_vis_input));

        etPlateLetters.addTextChangedListener(etCharater);
        etPlateNumbers.addTextChangedListener(etNumber);



        cbOfflineSerach.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Routine.openKeyboard(cbOfflineSerach, getActivity());
                     etPlateLetters.requestFocus();
                 }
             }
        );

        rbPlateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPlateSearch.isChecked()){
                    etPlateLetters.setVisibility(View.VISIBLE);
                    etPlateNumbers.setVisibility(View.VISIBLE);
                    etChassi.setVisibility(View.GONE);
                    etPlateMercosul.setVisibility(View.GONE);
                    etVisNumber.setVisibility(View.GONE);
                    etSealNumber.setVisibility(View.GONE);
                    etEngineNumber.setVisibility(View.GONE);

                    rgVisTypeSearch.clearCheck();
                    rgSealTypeSearch.clearCheck();


                    Routine.openKeyboard(etPlateLetters, getActivity());
                    etPlateLetters.requestFocus();

                    clearAllFields();

                }
            }
        });

        rbChassiSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbChassiSearch.isChecked()){

                    etPlateLetters.setVisibility(View.GONE);
                    etPlateNumbers.setVisibility(View.GONE);
                    etChassi.setVisibility(View.VISIBLE);
                    etPlateMercosul.setVisibility(View.GONE);
                    etVisNumber.setVisibility(View.GONE);
                    etSealNumber.setVisibility(View.GONE);
                    etEngineNumber.setVisibility(View.GONE);

                    rgVisTypeSearch.clearCheck();
                    rgSealTypeSearch.clearCheck();

                    Routine.openKeyboard(etChassi, getActivity());
                    etChassi.requestFocus();

                    clearAllFields();

                }
            }
        });

        rbMercosulPlateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbMercosulPlateSearch.isChecked()){

                    etPlateLetters.setVisibility(View.GONE);
                    etPlateNumbers.setVisibility(View.GONE);
                    etChassi.setVisibility(View.GONE);
                    etPlateMercosul.setVisibility(View.VISIBLE);
                    etVisNumber.setVisibility(View.GONE);
                    etSealNumber.setVisibility(View.GONE);
                    etEngineNumber.setVisibility(View.GONE);

                    rgTypeSearch.clearCheck();
                    rgSealTypeSearch.clearCheck();

                    Routine.openKeyboard(etPlateMercosul, getActivity());
                    etPlateMercosul.requestFocus();

                    clearAllFields();

                }
            }
        });

        rbVisNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbVisNumber.isChecked()){

                    etPlateLetters.setVisibility(View.GONE);
                    etPlateNumbers.setVisibility(View.GONE);
                    etChassi.setVisibility(View.GONE);
                    etPlateMercosul.setVisibility(View.GONE);
                    etVisNumber.setVisibility(View.VISIBLE);
                    etSealNumber.setVisibility(View.GONE);
                    etEngineNumber.setVisibility(View.GONE);

                    rgTypeSearch.clearCheck();
                    rgSealTypeSearch.clearCheck();

                    Routine.openKeyboard(etVisNumber, getActivity());
                    etVisNumber.requestFocus();

                    clearAllFields();

                }
            }
        });

        rbSealNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbSealNumber.isChecked()){

                    etPlateLetters.setVisibility(View.GONE);
                    etPlateNumbers.setVisibility(View.GONE);
                    etChassi.setVisibility(View.GONE);
                    etPlateMercosul.setVisibility(View.GONE);
                    etVisNumber.setVisibility(View.GONE);
                    etSealNumber.setVisibility(View.VISIBLE);
                    etEngineNumber.setVisibility(View.GONE);

                    rgTypeSearch.clearCheck();
                    rgVisTypeSearch.clearCheck();

                    Routine.openKeyboard(etVisNumber, getActivity());
                    etVisNumber.requestFocus();

                    clearAllFields();

                }
            }
        });

        rbEngineNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbEngineNumber.isChecked()){

                    etPlateLetters.setVisibility(View.GONE);
                    etPlateNumbers.setVisibility(View.GONE);
                    etChassi.setVisibility(View.GONE);
                    etPlateMercosul.setVisibility(View.GONE);
                    etVisNumber.setVisibility(View.GONE);
                    etSealNumber.setVisibility(View.GONE);
                    etEngineNumber.setVisibility(View.VISIBLE);

                    rgTypeSearch.clearCheck();
                    rgVisTypeSearch.clearCheck();

                    Routine.openKeyboard(etVisNumber, getActivity());
                    etVisNumber.requestFocus();

                    clearAllFields();

                }
            }
        });

        removeResultView();

        //gps = new GPSTracker(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_consultar_placa:
                if (iSVehicleSearch()) {
                    getVehicleResult();
                      //Log.d("Latitude", gps.getLocation().getLatitude() + " ");
                      //Log.d("Longitude", gps.getLocation().getLongitude() + " ");
                }
                break;

            case R.id.tv_show_result:
                llParentResultView.setVisibility(View.GONE);
                enableSearch();
                Routine.closeKeyboard(tvResultShow, getActivity());
                break;

            case R.id.btn_autuar:
                if (dataPlate != null) {

                    if ((DatabaseCreator
                            .getAitDatabaseAdapter(getActivity()))
                            .isSamePlateExist(dataPlate.getPlate())) {

                        openAit(DatabaseCreator.getAitDatabaseAdapter(getActivity())
                                .getAitDataFromPlate(dataPlate.getPlate()));

                    } else {
                        AitData aitData = new AitData();
                        aitData.setPlate(dataPlate.getPlate());
                        aitData.setChassi(dataPlate.getChassi());
                        aitData.setStateVehicle(dataPlate.getState());
                        aitData.setVehicleModel(dataPlate.getModel());
                        aitData.setVehicleBrand(dataPlate.getBrand());
                        aitData.setVehycleColor(dataPlate.getColor());
                        aitData.setVehicleSpecies(dataPlate.getSpecies().toUpperCase());
                        aitData.setVehicleCategory(dataPlate.getCategory().toUpperCase());
                        aitData.setStoreFullData(false);
                        openAit(aitData);

                        //Log.d("DadosAuto: ", String.valueOf(dataPlate.getCATEGORY().toUpperCase()));
                    }
                }
        }
    }

    private void getVehicleResult() {
        httpResultAnysTask = new PlateHttpResultAsyncTask(
                new CallBackPlate() {
                    @Override
                    public void callBack(DataFromPlate dataPlate, boolean isOffline) {
                        resultCallBack(dataPlate, isOffline);
                    }
                }, getActivity(), isOfflineSearch(), plateOrChassis, typeSearch, null);//gps.getLocation());
        httpResultAnysTask.execute("");
    }

    private void addResultView() {
        llParentResultView.setVisibility(View.VISIBLE);
    }

    private void removeResultView() {

        llParentResultView.setVisibility(View.GONE);
        tvResultShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llParentResultView.setVisibility(View.GONE);
                enableSearch();
            }
        });

    }

    public boolean iSVehicleSearch() {

        if (true){//gps.canGetLocation()) {

            if (checkInput()) {
                if (isOfflineSearch()) {
                    return true;
                } else {
                    if (isInternetConnected()) {
                        return true;
                    } else {
                        AnyAlertDialog.dialogShow(
                                getResources().getString(R.string.sem_conexao),
                                getActivity(), "info");
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {

            return false;
        }
    }

    private boolean isOfflineSearch() {
        return cbOfflineSerach.isChecked();
    }

    private boolean isInternetConnected() {
        return NetworkConnection.isNetworkAvailable(getActivity());
    }

    public void resultCallBack(DataFromPlate dataPlate, boolean isOffline) {
        this.dataPlate = null;
        //     gps.stopUsingGPS();

        //Log.d("resultCallBack", String.valueOf(dataPlate));

        if (dataPlate != null) {

            this.dataPlate = dataPlate;
            PlateViewFormat plateViewFormat = new PlateViewFormat(
                    dataPlate, getActivity());
            addResultView();

            tvResultShow.setText(plateViewFormat.getResultViewData(),
                    BufferType.SPANNABLE);
            plateViewFormat.setWarning();
            addResultView();
            disableSearch();
            btnCreateAit.setEnabled(true);

        } else {
            tvResultShow.setText(getResources().getString(
                    R.string.nehum_resultado_retornado));
            btnCreateAit.setEnabled(false);
            addResultView();
            disableSearch();
        }
    }

    // after result enable all search helper view ;
    private void enableSearch() {
        /*
        btnConsultarPlaca.setEnabled(true);
        etLetrasPlaca.setEnabled(true);
        etNumerosPlaca.setEnabled(true);
        cbPesquisaOffline.setEnabled(true);
        */
        llPlateSearch.setVisibility(View.VISIBLE);
        rgTypeSearch.setVisibility(View.VISIBLE);
        rgSealTypeSearch.setVisibility(View.VISIBLE);
        llVis.setVisibility(View.VISIBLE);
        etPlateLetters.requestFocus();
        Routine.openKeyboard(llParentResultView, getActivity());
    }

    // after show search result disable all search helper view ;
    private void disableSearch() {
        /*
        btnConsultarPlaca.setEnabled(false);
        etLetrasPlaca.setEnabled(false);
        etNumerosPlaca.setEnabled(false);
        cbPesquisaOffline.setEnabled(false);
        textChangeState = false;
        etNumerosPlaca.setText("");
        etLetrasPlaca.setText("");
        textChangeState = true;
        */
        textChangeState = false;
        etPlateNumbers.setText("");
        etPlateLetters.setText("");
        etChassi.setText("");
        etPlateMercosul.setText("");
        etVisNumber.setText("");
        llPlateSearch.setVisibility(View.GONE);
        llVis.setVisibility(View.GONE);
        rgTypeSearch.setVisibility(View.GONE);
        rgSealTypeSearch.setVisibility(View.GONE);
        textChangeState = true;
    }

    private void clearAllFields(){
        etPlateLetters.setText("");
        etPlateNumbers.setText("");
        etChassi.setText("");
        etPlateMercosul.setText("");
        etVisNumber.setText("");
    }

    private boolean checkInput() {
        String character = etPlateLetters.getText().toString().trim();
        String number = etPlateNumbers.getText().toString().trim();
        String chassi = etChassi.getText().toString().trim();
        String placaMercosul = etPlateMercosul.getText().toString().trim();
        String numeroVis = etVisNumber.getText().toString().trim();
        String numeroLacre = etSealNumber.getText().toString().trim();
        String numeroMotor = etEngineNumber.getText().toString().trim();

        if (rbPlateSearch.isChecked()){
            plateOrChassis = character + number;
            typeSearch = "plate";
            if (character.length() != LENGTH_PLATE_CHARACTER) {
                etPlateLetters.setError(getResources().getString(
                        R.string.letters_of_the_plate));
                etPlateLetters.requestFocus();
                return false;
            } else if (number.length() != LENGTH_PLATE_NUMBER) {
                etPlateNumbers.setError(getResources().getString(
                        R.string.plate_numbers));
                etPlateNumbers.requestFocus();
                return false;
            } else {
                return true;
            }
        } else if (rbChassiSearch.isChecked()){
            plateOrChassis = chassi;
            typeSearch = "chassi";
            if (chassi.length() != LENGTH_CHASSI) {
                etChassi.setError(getResources().getString(
                        R.string.chassi_field));
                etChassi.requestFocus();
                return false;
            } else {
                return true;
            }
        } else if (rbMercosulPlateSearch.isChecked()){
            plateOrChassis = placaMercosul;
            typeSearch = "plate";
            if (placaMercosul.length() != LENGTH_PLATE_MERCOSUL) {
                etPlateMercosul.setError(getResources().getString(
                        R.string.plate_mercosul_field));
                etPlateMercosul.requestFocus();
                return false;
            } else {
                return true;
            }
        } else if (rbVisNumber.isChecked()){
            plateOrChassis = numeroVis;
            typeSearch = "vis";
            if (numeroVis.length() != NUMERO_VIS) {
                etVisNumber.setError(getResources().getString(
                        R.string.vis_field));
                etVisNumber.requestFocus();
                return false;
            } else {
                return true;
            }
        }else if (rbSealNumber.isChecked()){
            plateOrChassis = numeroLacre;
            typeSearch = "plateSeal";
            if (numeroLacre.equals(null)) {
                etSealNumber.setError(getResources().getString(
                        R.string.seal_field));
                etSealNumber.requestFocus();
                return false;
            } else {
                return true;
            }
        }else if (rbEngineNumber.isChecked()){
            plateOrChassis = numeroMotor;
            typeSearch = "motor";
            if (numeroMotor.equals(null)) {
                etEngineNumber.setError(getResources().getString(
                        R.string.motor_field));
                etEngineNumber.requestFocus();
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }

    }

    private class EditTextChange implements TextWatcher {
        private int id;

        public EditTextChange(int id) {
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable text) {
            if (textChangeState) {

                switch (id) {
                    case R.id.et_letras_da_placa:

                        etPlateLetters.setError(null);
                        if (text.toString().length() == LENGTH_PLATE_CHARACTER) {
                            if (etPlateNumbers.getText().toString().length() != LENGTH_PLATE_NUMBER) {
                                etPlateNumbers.requestFocus();
                            } else {
                                Routine.openKeyboard(btnPlateSearch, getActivity());
                                btnPlateSearch.setFocusableInTouchMode(true);
                                btnPlateSearch.requestFocus();
                            }
                        }
                        break;
                    case R.id.et_numeros_da_placa:
                        etPlateNumbers.setError(null);
                        if (text.toString().length() == LENGTH_PLATE_NUMBER) {
                            if (etPlateLetters.getText().toString().length() != LENGTH_PLATE_CHARACTER) {
                                etPlateLetters.requestFocus();
                            } else {
                                //btnConsultarPlaca.setFocusableInTouchMode(true);
                                //btnConsultarPlaca.requestFocus();
                                if (iSVehicleSearch()) {
                                    getVehicleResult();
                                    //       Log.d("Latitude", gps.getLocation().getLatitude() + " ");
                                    //    Log.d("Longitude", gps.getLocation().getLongitude() + " ");
                                }
                            }
                        }
                        break;
                    case R.id.et_chassi_input:
                        etChassi.setError(null);
                        if (text.toString().length() == LENGTH_CHASSI) {
                            if (etChassi.getText().toString().length() != LENGTH_CHASSI) {
                                etChassi.requestFocus();
                            } else {
                                if (iSVehicleSearch()) {
                                    getVehicleResult();
                                }
                            }
                        }
                        break;
                    case R.id.et_mercosul_input:
                        etPlateMercosul.setError(null);
                        if (text.toString().length() == LENGTH_PLATE_MERCOSUL) {
                            if (etPlateMercosul.getText().toString().length() != LENGTH_PLATE_MERCOSUL) {
                                etPlateMercosul.requestFocus();
                            } else {
                                if (iSVehicleSearch()) {
                                    getVehicleResult();
                                }
                            }
                        }
                        break;
                    case R.id.et_vis_input:
                        etVisNumber.setError(null);
                        if (text.toString().length() == NUMERO_VIS) {
                            if (etVisNumber.getText().toString().length() != NUMERO_VIS) {
                                etVisNumber.requestFocus();
                            } else {
                                if (iSVehicleSearch()) {
                                    getVehicleResult();
                                }
                            }
                        }
                        break;
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        }
    }

    private void openAit(AitData aitData) {
        Intent intent = new Intent(getActivity(), AitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AitData.getIDAuto(), aitData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
