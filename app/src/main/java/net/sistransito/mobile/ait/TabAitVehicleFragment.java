package net.sistransito.mobile.ait;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyDialogFragment;
import net.sistransito.mobile.fragment.AnyDialogListener;
import net.sistransito.mobile.fragment.CallBackPlate;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.number.NumberAnysListerner;
import net.sistransito.mobile.number.NumberHttpResultAnysTask;

import net.sistransito.mobile.plate.data.DataFromPlate;
import net.sistransito.mobile.plate.data.PlateHttpResultAsyncTask;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class TabAitVehicleFragment extends DebugFragment implements
        AnyDialogListener {

    private PlateHttpResultAsyncTask httpResultAnysTask;
    private View view;
    private MaterialSpinner spinner;
    private EditText editVehiclePlate, editVehicleBrand, editVehicleModel,
            editVehicleColor, editVehicleChassi;
    private TextView tvSearchPlate, tvIdAit, tvSaveData;
    private Spinner spinnerSpecies, spinnerCategory,
            spinnerCountry, spinnerPlateState;
    private List<String> listCategory, listSpecies, listCountry, listStatePlate;

    private AnyArrayAdapter<String> aaaCategory, aaaSpecies,
            aaaCountry, aaaStatePlate;

    private ArrayAdapter<String> aalistSpecies;

    private AitData aitData;
    private String sAitNumberRetained, sSearchType;
    private Bundle bundle;
    private AnyDialogFragment dialogFragment;
    private LinearLayout llVehicleState, llVehicleCountry;
    private CheckBox cbIfForeignVehicle, cbAitConfirm;


    public static TabAitVehicleFragment newInstance() {
        return new TabAitVehicleFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salve state
        outState.putString("AitNumber", aitData.getAitNumber());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_ait_vehicle_fragment, null, false);

        if (savedInstanceState != null) {
            sAitNumberRetained = savedInstanceState.getString("AitNumber");
            //Log.i("AitNumber: ", numeroAutoRetained);
        }

        initializedView();
        getObjectAuto();
        checkAitNumber();
        return view;
    }


    private void initializedView() {

        tvSaveData = (TextView) view.findViewById(R.id.ait_fab);
        tvIdAit = (TextView) view.findViewById(R.id.tv_id_ait);
        editVehiclePlate = (EditText) view.findViewById(R.id.edit_vehicle_plate);
        editVehicleChassi = (EditText) view.findViewById(R.id.edit_vehicle_chassi);

        editVehicleBrand = (EditText) view.findViewById(R.id.edit_vehicle_brand);
        editVehicleModel = (EditText) view
                .findViewById(R.id.edit_vehicle_model);
        editVehicleColor = (EditText) view
                .findViewById(R.id.edit_vehicle_color);

        listCategory = Arrays.asList(getResources().getStringArray(
                R.array.list_category));

        listCountry = Arrays.asList(getResources().getStringArray(
                R.array.filter_list_country));

        aaaCountry = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                listCountry);

        aaaCategory = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                listCategory);
        //Spinner Species
        listSpecies = Arrays.asList(getResources().getStringArray(
                R.array.auto_species));
        aaaSpecies = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                listSpecies);
        spinnerSpecies = (Spinner) view.findViewById(R.id.spinner_ait_species);
        spinnerSpecies.setAdapter(aaaSpecies);

        //Spinner vehiclePlate state
        listStatePlate = Arrays.asList(getResources().getStringArray(
                R.array.state_array));
        aaaStatePlate = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                listStatePlate);
        spinnerPlateState = (Spinner) view.findViewById(R.id.spinner_vechicle_state);
        spinnerPlateState.setAdapter(aaaStatePlate);

        //
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner_ait_species);
        //aalisteSpecie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,listEspecie);
        aalistSpecies = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1,
                listSpecies);
        aalistSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aalistSpecies);

        //

        spinnerCountry = (Spinner) view.findViewById(R.id.spinner_ait_country);
        spinnerCountry.setAdapter(aaaCountry);

        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_ait_category);
        spinnerCategory.setAdapter(aaaCategory);

        llVehicleState = (LinearLayout) view.findViewById(R.id.ll_field_vehycle_state);
        llVehicleCountry = (LinearLayout) view.findViewById(R.id.ll_field_vehicle_country);
        cbIfForeignVehicle = (CheckBox) view.findViewById(R.id.cb_if_foreign_vehicle);
        cbAitConfirm = (CheckBox) view.findViewById(R.id.cb_ait_confirm);

        tvSearchPlate = (TextView) view.findViewById(R.id.tv_search_plate);

    }

    private void addListener() {

        editVehicleChassi.addTextChangedListener(new ChangeText(
                R.id.edit_vehicle_chassi));

        spinnerCountry
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long Id) {

                        aitData.setCountry((String) parent.getItemAtPosition(pos));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        spinnerCategory
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long Id) {

                        aitData.setVehicleCategory((String) parent
                                .getItemAtPosition(pos));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        spinnerSpecies
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long Id) {
                        aitData.setVehicleSpecies((String) parent.getItemAtPosition(pos));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        spinnerPlateState
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long Id) {
                        aitData.setStateVehicle((String) parent.getItemAtPosition(pos));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        cbIfForeignVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbIfForeignVehicle.isChecked()){
                    llVehicleState.setVisibility(View.GONE);
                    llVehicleCountry.setVisibility(View.VISIBLE);
                }else{
                    llVehicleState.setVisibility(View.VISIBLE);
                    llVehicleCountry.setVisibility(View.GONE);
                }
            }
        });

        cbAitConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbAitConfirm.isChecked()) {
                    tvSaveData.setVisibility(view.VISIBLE);
                    Routine.closeKeyboard(tvSaveData, getActivity());
                }else{
                    tvSaveData.setVisibility(view.GONE);
                }
            }
        });

        tvSearchPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSearchType = "plate";

                if (!editVehiclePlate.getText().toString().equals("")) {
                    if (NetworkConnection.isNetworkAvailable(getActivity())) {

                        httpResultAnysTask = new PlateHttpResultAsyncTask(
                                new CallBackPlate() {
                                    @Override
                                    public void callBack(DataFromPlate fromPlate, boolean isOffline) {
                                        resultCallBack(fromPlate, isOffline);
                                    }
                                }, getActivity(), true, editVehiclePlate.getText().toString(), sSearchType,null);//gps.getLocation());
                        httpResultAnysTask.execute("");

                    } else {
                        Routine.showAlert(getResources().getString(R.string.sem_conexao), getActivity());
                    }
                } else {
                    Routine.showAlert(getResources().getString(R.string.titulo_tela_consulta_placa), getActivity());
                }

            }
        });

        tvSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkInput()) {

                    if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).insertAidData(aitData))
                        Routine.showAlert(getResources().getString(R.string.update_erro), getActivity());

                    ((AitActivity) getActivity()).setActiveTab(1);

                }

            }
        });

    }

    private boolean checkInput(){

        if (editVehiclePlate.getText().toString().isEmpty()) {
            editVehiclePlate.setError(getResources().getString(
                    R.string.texto_inserir_placa));
            editVehiclePlate.requestFocus();
            return false;
        /*} else if (editChassiVeiculo.getText().toString().isEmpty()) {
            editChassiVeiculo.setError(getResources().getString(
                    R.string.texto_inserir_municipio));
            editChassiVeiculo.requestFocus();
            return false;*/
        } else if (editVehicleBrand.getText().toString().isEmpty()) {
            editVehicleBrand.setError(getResources().getString(
                    R.string.texto_inserir_marca));
            editVehicleBrand.requestFocus();
            return false;
        } else if (editVehicleModel.getText().toString().isEmpty()) {
            editVehicleModel.setError(getResources().getString(
                    R.string.texto_inserir_modelo));
            editVehicleModel.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private void getObjectAuto() {
        aitData = ObjectAit.getAitData();
        if (aitData.isDataisNull()) {
            setViewEnable(true);
            addListener();
        } else if (aitData.isStoreFullData()) {
            setViewEnable(true);
            getRecomandedUpdate();
            getOtherUpdate();
            addListener();
        } else {
            setViewEnable(true);
            getRecomandedUpdate();
            getOtherUpdate();
            addListener();

        }
    }

    private void getRecomandedUpdate() {

        editVehiclePlate.setText(aitData.getPlate());
        editVehicleBrand.setText(aitData.getVehicleBrand());
        editVehicleModel.setText(aitData.getVehicleModel());
        editVehicleColor.setText(aitData.getVehycleColor());
        editVehicleChassi.setText(aitData.getChassi());

    }

    private void getOtherUpdate() {

        int selection_1 = 0, selection_2 = 0, selection_3 = 0, selection_4 = 0;

        selection_1 = listStatePlate.indexOf(aitData.getStateVehicle());
        selection_2 = listCountry.indexOf(aitData.getCountry());
        selection_3 = listSpecies.indexOf(aitData.getVehicleSpecies());
        selection_4 = listCategory.indexOf(aitData.getVehicleCategory());

        spinnerPlateState.setSelection(selection_1 + 1);
        spinnerCountry.setSelection(selection_2 + 1);
        spinnerSpecies.setSelection(selection_3 + 1);
        spinnerCategory.setSelection(selection_4 + 1);

    }

    private class ChangeText implements TextWatcher {
        private int id;

        public ChangeText(int id) {
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.toString() != null) {
                switch (id) {
                    case R.id.edit_vehicle_chassi:
                        aitData.setChassi(s.toString());
                        break;
                    case R.id.edit_vehicle_plate:
                        aitData.setPlate(s.toString());
                        break;
                    case R.id.edit_vehicle_color:
                        aitData.setVehycleColor(s.toString());
                        break;
                    case R.id.edit_vehicle_brand:
                        aitData.setVehicleBrand(s.toString());
                        break;
                    case R.id.edit_vehicle_model:
                        aitData.setVehicleModel(s.toString());
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

    @Override
    public void onDialogTaskWork(boolean isWork) {
        if (isWork) {

            (new NumberHttpResultAnysTask(new NumberAnysListerner() {

                @Override
                public void anysTaskComplete(boolean isComplete) {
                    if (isComplete) {
                        checkAitNumber();
                    }
                }
            }, getActivity(), AppConstants.AIT_NUMBER)).execute("");

        } else {
            getActivity().finish();
        }
    }

    private void setViewEnable(boolean isEnable) {

        editVehiclePlate.setEnabled(isEnable);
        //editUfVeiculo.setEnabled(isEnable);
        editVehicleColor.setEnabled(isEnable);
        editVehicleBrand.setEnabled(isEnable);
        editVehicleModel.setEnabled(isEnable);

        if(isEnable){

            editVehiclePlate.addTextChangedListener(new ChangeText(R.id.edit_vehicle_plate));
            //editUfVeiculo.addTextChangedListener(new ChangeText(R.id.editUfVeiculo));
            editVehicleColor.addTextChangedListener(new ChangeText(R.id.edit_vehicle_color));
            editVehicleBrand.addTextChangedListener(new ChangeText(R.id.edit_vehicle_brand));
            editVehicleModel.addTextChangedListener(new ChangeText(R.id.edit_vehicle_model));

        }

    }

    private void resultCallBack(DataFromPlate plateFormat, boolean offLine) {

        if (plateFormat != null) {

            int selection_1 = 0, selection_2 = 0, selection_3 = 0;

            selection_1 = listStatePlate.indexOf(plateFormat.getState().toUpperCase());
            selection_2 = listSpecies.indexOf(plateFormat.getSpecies().toUpperCase());
            selection_3 = listCategory.indexOf(plateFormat.getCategory().toUpperCase());

            editVehicleChassi.setText(plateFormat.getChassi());
            editVehicleBrand.setText(plateFormat.getBrand());
            editVehicleModel.setText(plateFormat.getModel());
            editVehicleColor.setText(plateFormat.getColor());

            spinnerPlateState.setSelection(selection_1 + 1);
            spinnerSpecies.setSelection(selection_2 + 1);
            spinnerCategory.setSelection(selection_3 + 1);

            Routine.closeKeyboard(editVehiclePlate, getActivity());

        } else {
            Routine.showAlert(getResources().getString(R.string.nehum_resultado_retornado), getActivity());
        }

    }

    private void checkAitNumber() {

        String sAitNumber = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
                .getAitNumber();

        if (sAitNumber == null) {
            dialogFragment = new AnyDialogFragment();
            dialogFragment.setTargetFragment(this, 0);
            bundle = new Bundle();
            bundle.putInt(AppConstants.DIALOG_TITLE_ID,
                    R.string.titulo_tela_sincronizacao);
            bundle.putInt(AppConstants.DIALOG_MGS_ID,
                    R.string.carregando_autos);
            dialogFragment.setArguments(bundle);
            dialogFragment.setCancelable(false);
            dialogFragment
                    .show(getFragmentManager(), "dialog");
            //show(getActivity().getSupportFragmentManager(), null);
        } else {

            if(sAitNumberRetained != null){

                aitData.setAitNumber(sAitNumberRetained);
                tvIdAit.setText(getResources().getString(R.string.ait_numero) + aitData.getAitNumber());

            } else {

                aitData.setAitNumber(sAitNumber);
                tvIdAit.setText(getResources().getString(R.string.ait_numero) + aitData.getAitNumber());
                DatabaseCreator.getAitDatabaseAdapter(getActivity()).insertAitNumber(aitData);

            }

        }
    }

}