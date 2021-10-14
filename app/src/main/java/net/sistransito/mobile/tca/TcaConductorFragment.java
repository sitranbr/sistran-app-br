package net.sistransito.mobile.tca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyDialogFragment;
import net.sistransito.mobile.fragment.AnyDialogListener;
import net.sistransito.mobile.number.NumberAnysListerner;
import net.sistransito.mobile.number.NumberHttpResultAnysTask;
import net.sistransito.R;

import java.util.Arrays;
import java.util.List;

public class TcaConductorFragment extends Fragment implements
        AnyDialogListener {
    private View view;
    private EditText etDriverName, etCnhPpd, etCpf,
            etAddress, etDistrict, etCity, etPlate,
            etPlateState, etModel;

    private Spinner spState;

    private List<String> listState;
    private AnyArrayAdapter<String> adapterState;
    private TcaData tcaData;
    private Bundle bundle;
    private AnyDialogFragment diaglogFragmentForFragment;

    public static TcaConductorFragment newInstance() {
        return new TcaConductorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tca_driver_fragment, null, false);
        initializedView();
        getTCAObject();
        addListener();
        checkAitNumber();
        return view;
    }

    private void checkAitNumber() {
        String value = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
                .getTcaNumber();
        if (value == null) {
            diaglogFragmentForFragment = new AnyDialogFragment();
            diaglogFragmentForFragment.setTargetFragment(this, 0);
            bundle = new Bundle();
            bundle.putInt(AppConstants.DIALOG_TITLE_ID, R.string.titulo_tela_sincronizacao);
            bundle.putInt(AppConstants.DIALOG_MGS_ID, R.string.mgs_sincronizacao);
            diaglogFragmentForFragment.setArguments(bundle);
            diaglogFragmentForFragment.setCancelable(false);
            diaglogFragmentForFragment
                    .show(getChildFragmentManager(), "dialog");
        } else {
            tcaData.setTcaNumber(value);
        }
    }

    private void getRecomandedUpdate() {

        if(tcaData.getTcaType().equals("avulso")) {
        }else{
            etDriverName.setText(tcaData.getDriverName());
            etCnhPpd.setText(tcaData.getCnhPpd());

            if (tcaData.getCpf() == null) {
                tcaData.setCpf("");
            } else {
                etCpf.setText(tcaData.getCpf());
            }

            etPlate.setText(tcaData.getPlate());
            etPlateState.setText(tcaData.getPlateState());
            etModel.setText(tcaData.getBrandModel());
        }
    }

    private void addListener() {
        etAddress.addTextChangedListener(new ChangeText(
                R.id.et_tca_endereco));
        etDistrict
                .addTextChangedListener(new ChangeText(R.id.et_tca_bairro));
        etCity.addTextChangedListener(new ChangeText(
                R.id.et_tca_municipio));

        spState
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        tcaData.setState((String) parent
                                .getItemAtPosition(pos));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
    }

    private void getTCAObject() {
        tcaData = TcaObject.getTCAOject();
        getRecomandedUpdate();

    }

    // private void initializedSelectetItems() {
    //
    // }

    private void initializedView() {
        etDriverName = (EditText) view
                .findViewById(R.id.et_tca_nome_do_condutor);
        etDriverName.setEnabled(false);
        etCnhPpd = (EditText) view.findViewById(R.id.et_tca_cnh_ppd);
        etCnhPpd.setEnabled(false);
        etCpf = (EditText) view.findViewById(R.id.et_tca_cpf);
        etCpf.setEnabled(false);
        etAddress = (EditText) view.findViewById(R.id.et_tca_endereco);
        etDistrict = (EditText) view.findViewById(R.id.et_tca_bairro);
        etCity = (EditText) view.findViewById(R.id.et_tca_municipio);
        etPlate = (EditText) view.findViewById(R.id.et_tca_placa);
        etPlate.setEnabled(false);
        etPlateState = (EditText) view.findViewById(R.id.et_tca_placa_uf);
        etPlateState.setEnabled(false);
        etModel = (EditText) view.findViewById(R.id.et_tca_model);
        etModel.setEnabled(false);
        spState = (Spinner) view
                .findViewById(R.id.sp_tca_municipio_uf);
        listState = Arrays.asList(getResources().getStringArray(
                R.array.state_array));
        adapterState = new AnyArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                listState);
        spState.setAdapter(adapterState);
    }

    private class ChangeText implements TextWatcher {
        private int id;

        public ChangeText(int id) {
            this.id = id;
        }

        @Override
        public void afterTextChanged(Editable value) {
            String s = (value.toString()).trim();
            switch (id) {
                case R.id.et_tca_endereco:
                    tcaData.setAddress(s);
                    break;
                case R.id.et_tca_bairro:
                    tcaData.setDistrict(s);
                    break;
                case R.id.et_tca_municipio:
                    tcaData.setCity(s);
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
    public void onDialogTaskWork(boolean isWork) {
        if (isWork) {

            (new NumberHttpResultAnysTask(new NumberAnysListerner() {

                @Override
                public void anysTaskComplete(boolean isComplete) {
                    if (isComplete) {
                        checkAitNumber();
                    }
                }
            }, getActivity(), AppConstants.TCA_NUMBER)).execute("");

        } else {
            getActivity().finish();
        }

    }
}