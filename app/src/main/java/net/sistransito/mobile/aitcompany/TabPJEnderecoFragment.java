package net.sistransito.mobile.aitcompany;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import net.sistransito.library.datepicker.DateListener;
import net.sistransito.library.datepicker.MyDatePicker;
import net.sistransito.library.datepicker.MyTimePicker;
import net.sistransito.library.datepicker.TimeListener;
import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PrepopulatedDBOpenHelper;
import net.sistransito.mobile.database.SetttingDatabaseHelper;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.util.ArrayList;
import java.util.List;

public class TabPJEnderecoFragment extends
        Fragment implements
        View.OnClickListener, TimeListener, DateListener {

    private View view;
    private AutoCompleteTextView autoCompleteMunicipios;
    private pjData dadosAuto;
    private EditText editAutoLocalInfracao, editAutoUfLocal;
    private Button btnDataInfracao, btnHoraInfracao;
    private LinearLayout llAutoMunicipioUf;
    private TextView tvSalvarDados;
    private CheckBox cbAutoConfirmar;

    private List<String> municipioArray, codArray, ufArray;
    private ArrayAdapter<String> adapterMunicipio;

    private Bundle bundle;

    public static TabPJEnderecoFragment newInstance() {
        return new TabPJEnderecoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autopj_endereco_fragment, null, false);
        initializedView();
        getAutoObject();
        return view;
    }

    private void initializedView() {

        llAutoMunicipioUf = (LinearLayout) view.findViewById(R.id.ll_auto_municipio_uf);

        autoCompleteMunicipios = (AutoCompleteTextView) view
                .findViewById(R.id.auto_auto_municipio);

        editAutoUfLocal = (EditText) view.findViewById(R.id.et_auto_end_uf);

        editAutoLocalInfracao = (EditText) view.findViewById(R.id.et_auto_local_infracao);
        btnDataInfracao = (Button) view
                .findViewById(R.id.btn_end_data_infracao);
        btnHoraInfracao = (Button) view
                .findViewById(R.id.btn_end_hora_infracao);

        tvSalvarDados = (TextView) view.findViewById(R.id.ait_fab);
        cbAutoConfirmar = (CheckBox) view.findViewById(R.id.cb_ait_confirm);

        editAutoUfLocal.setEnabled(false);

        setMunicipioAutoComplete();
        hideComponents();

    }

    private void hideComponents(){

        llAutoMunicipioUf.setVisibility(LinearLayout.GONE);

    }

    private void  showComponents(){

        llAutoMunicipioUf.setVisibility(LinearLayout.VISIBLE);
        Routine.closeKeyboard(autoCompleteMunicipios, getActivity());

    }

    public void setMunicipioAutoComplete() {

        municipioArray = new ArrayList<String>();
        codArray = new ArrayList<String>();
        ufArray = new ArrayList<String>();
        Cursor myCursor;

        Cursor cursor = (DatabaseCreator
                .getSettingDatabaseAdapter(getActivity()))
                .getSettingCursor();

        String ufMunicipio = cursor.getString(cursor.getColumnIndex(SetttingDatabaseHelper.SETTING_UF));

        //Log.d("UfLocal", ufMunicipio);

        myCursor = ((DatabaseCreator
                .getPrepopulatedDBOpenHelper(getActivity()))
                .getInitialsStateCursor(ufMunicipio));

        if (myCursor.getCount() <= 0) {
            myCursor = ((DatabaseCreator
                    .getPrepopulatedDBOpenHelper(getActivity()))
                    .getCityCursor());
        }

        do {
            municipioArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.CITY_NAME)));
            ufArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.STATE)));
            codArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.CITY_CODE)));
        } while (myCursor.moveToNext());
        myCursor.close();

        adapterMunicipio = new ArrayAdapter<String>(getActivity(), R.layout.custom_autocomplete, R.id.autoCompleteItem, municipioArray);
        autoCompleteMunicipios.setAdapter(adapterMunicipio);

        autoCompleteMunicipios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long arg3) {

                dadosAuto.setCity((String) parent.getItemAtPosition(pos));
                int real_position = municipioArray.indexOf(parent.getItemAtPosition(pos));

                dadosAuto.setState(ufArray.get(real_position));
                editAutoUfLocal.setText(dadosAuto.getState());

                showComponents();

            }
        });
    }

    private void addListener() {

        editAutoLocalInfracao.addTextChangedListener(new TabPJEnderecoFragment.ChangeText(
                R.id.et_auto_local_infracao));

        btnDataInfracao.setOnClickListener(this);
        btnHoraInfracao.setOnClickListener(this);

        hideComponents();

        cbAutoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbAutoConfirmar.isChecked()) {
                    tvSalvarDados.setVisibility(view.VISIBLE);
                    Routine.closeKeyboard(tvSalvarDados, getActivity());
                }else{
                    tvSalvarDados.setVisibility(view.GONE);
                }
            }
        });

        tvSalvarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInput()) {

                    if (!DatabaseCreator.getAitPJDatabaseAdapter(getActivity()).setDadosPJEnd(dadosAuto))
                        Routine.showAlert(getResources().getString(R.string.update_erro), getActivity());

                    ((AitActivity) getActivity()).setActiveTab(3);

                }

            }
        });

    }

    private boolean checkInput(){

        //Log.d("TextoData", btnDataInfracao.getText().toString());
        //Log.d("TextoHora", btnHoraInfracao.getText().toString());

        if (editAutoLocalInfracao.getText().toString().isEmpty()) {
            editAutoLocalInfracao.setError(getResources().getString(
                    R.string.texto_inserir_local));
            editAutoLocalInfracao.requestFocus();
            return false;
        } else if (autoCompleteMunicipios.getText().toString().isEmpty()) {
            autoCompleteMunicipios.setError(getResources().getString(
                    R.string.texto_inserir_municipio));
            autoCompleteMunicipios.requestFocus();
            return false;
        } else if (btnDataInfracao.getText().toString().equals("Data")) {
            Routine.showAlert(getActivity().getString(R.string.texto_inserir_data), getActivity());
            btnDataInfracao.requestFocus();
            return false;
        } else if (btnHoraInfracao.getText().toString().equals("Hora")) {
            Routine.showAlert(getActivity().getString(R.string.texto_inserir_hora), getActivity());
            btnHoraInfracao.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    private void getAutoObject() {
        dadosAuto = ObjectAutoPJ.getDadosAuto();
        if (dadosAuto.isDataisPJNull()) {
            addListener();
        } else if (dadosAuto.isStorePJFullData()) {
            getRecomandedUpdate();
            addListener();
            setViewNovoAuto();
        } else {
            addListener();
            initializedSelectetItems();
        }
        addListener();
    }

    private void initializedSelectetItems() {
		/*
		editAutoLocalInfracao.setText("");
		editAutoCodigoMunicipio.setText("");
		editAutoUfLocal.setText("");
		btnDataInfracao.setText("");
		btnHoraInfracao.setText("");
		spinner_auto_descricao.setSelection(0);
        */
    }

    private void getRecomandedUpdate() {

        editAutoUfLocal.setText(dadosAuto.getState());
        editAutoLocalInfracao.setText(dadosAuto.getPlace());

    }

    private class ChangeText implements TextWatcher {
        private int id;

        public ChangeText(int id) {
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable edit) {
            String s = (edit.toString()).trim();
            switch (id) {
                case R.id.et_auto_local_infracao:
                    dadosAuto.setPlace(s.toString());
                    break;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_end_data_infracao:
                MyDatePicker pickerdate = new MyDatePicker();
                bundle = new Bundle();
                bundle.putInt(MyDatePicker.MY_DATE_PICKER_ID,
                        R.id.btn_end_data_infracao);
                pickerdate.setArguments(bundle);
                pickerdate.setTargetFragment(this, 0);
                pickerdate.show(getActivity().getSupportFragmentManager(), "date");
                break;

            case R.id.btn_end_hora_infracao:
                MyTimePicker picker = new MyTimePicker();
                bundle = new Bundle();
                bundle.putInt(MyTimePicker.MY_TIME_PICKER_ID,
                        R.id.btn_end_hora_infracao);
                picker.setArguments(bundle);
                picker.setTargetFragment(this, 0);
                picker.show(getActivity().getSupportFragmentManager(), "time");
                break;
        }

    }

    @Override
    public void time(String time, int view_id) {
        if (time != null) {
            dadosAuto.setAitTime(time);
            btnHoraInfracao.setText(dadosAuto.getAitTime());
        }

    }

    @Override
    public void date(String date, int view_id) {
        if (date != null) {
            dadosAuto.setAitDate(date);
            btnDataInfracao.setText(dadosAuto.getAitDate());
        }

    }

    private void setViewNovoAuto() {

        autoCompleteMunicipios.setVisibility(AutoCompleteTextView.GONE);
        editAutoUfLocal.setVisibility(EditText.GONE);
        btnDataInfracao.setVisibility(Button.GONE);
        btnHoraInfracao.setVisibility(Button.GONE);

    }

}
