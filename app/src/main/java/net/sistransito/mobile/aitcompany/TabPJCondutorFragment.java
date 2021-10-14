package net.sistransito.mobile.aitcompany;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyDialogListener;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.util.List;

public class TabPJCondutorFragment extends Fragment implements
        AnyDialogListener {
    private View view;
    private pjData dadosAuto;
    private EditText editNomeInfrator, editCpf, editCnpj, editEndereco;
    private Spinner spinnerUfCondutor;
    private List<String> listUfCondutor;
    private AnyArrayAdapter<String> adapterUfCondutor;
    private CheckBox cbSeInfratorAbordado, cbAutoConfirmar;
    private RadioButton rbTypeJuridica, rbTypeFisica;
    private TextInputLayout tilPjCpf, tilPjCnpj;
    private LinearLayout llHideAll;
    private RadioGroup rgTypePJ;
    private TextView tvSalvarDados;

    public static TabPJCondutorFragment newInstance() {
        return new TabPJCondutorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autopj_condutor_fragment, null, false);
        initializedView();
        getObjectAuto();
        return view;
    }

    private void initializedView() {

        llHideAll = (LinearLayout) view.findViewById(R.id.ll_hide_all);
        cbSeInfratorAbordado =  (CheckBox) view.findViewById(R.id.cb_se_infrator_abordado);

        rbTypeFisica = (RadioButton) view.findViewById(R.id.rb_pj_fisica);
        rbTypeJuridica = (RadioButton) view.findViewById(R.id.rb_pj_juridica);

        tilPjCpf = (TextInputLayout) view.findViewById(R.id.til_pj_cpf);
        tilPjCnpj = (TextInputLayout) view.findViewById(R.id.til_pj_cnpj);

        editNomeInfrator = (EditText) view
                .findViewById(R.id.et_pj_nome_infrator);

        editCpf = (EditText) view.findViewById(R.id.et_pj_cpf);
        editCnpj = (EditText) view.findViewById(R.id.et_pj_cnpj);

        editEndereco = (EditText) view.findViewById(R.id.et_pj_endereco);

        cbAutoConfirmar = (CheckBox) view.findViewById(R.id.cb_pj_confirmar);
        tvSalvarDados = (TextView) view.findViewById(R.id.autopj_fab);

    }

    private void getObjectAuto() {
        dadosAuto = ObjectAutoPJ.getDadosAuto();
        if (dadosAuto.isDataisPJNull()) {
            addListener();
        } else if (dadosAuto.isStorePJFullData()) {
            getRecomandedUpdate();
            getOtherUpdate();
            addListener();
        } else {
            getRecomandedUpdate();
            addListener();
        }
        addListener();
    }

    private void addListener() {

        editNomeInfrator.addTextChangedListener(new TabPJCondutorFragment.ChangeText(
                R.id.et_pj_nome_infrator));

        editCpf.addTextChangedListener(new TabPJCondutorFragment.ChangeText(
                R.id.et_pj_cpf));

        editCnpj.addTextChangedListener(new TabPJCondutorFragment.ChangeText(
                R.id.et_pj_cnpj));

        editEndereco.addTextChangedListener(new TabPJCondutorFragment.ChangeText(
                R.id.et_pj_endereco));

        cbSeInfratorAbordado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbSeInfratorAbordado.isChecked()){
                    llHideAll.setVisibility(View.GONE);
                } else {
                    llHideAll.setVisibility(View.VISIBLE);
                }
            }
        });

        rbTypeFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbTypeFisica.isChecked()){
                    tilPjCpf.setVisibility(View.VISIBLE);
                    tilPjCnpj.setVisibility(View.GONE);
                    //Rotinas.openKeyboard(editNomeInfrator, getActivity());
                    editNomeInfrator.requestFocus();
                }
            }
        });

        rbTypeJuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbTypeJuridica.isChecked()){
                    tilPjCpf.setVisibility(View.GONE);
                    tilPjCnpj.setVisibility(View.VISIBLE);
                    //Rotinas.openKeyboard(editNomeInfrator, getActivity());
                    editNomeInfrator.requestFocus();
                }
            }
        });

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

                if (!DatabaseCreator.getAitPJDatabaseAdapter(getActivity()).setDadosPJInfrator(dadosAuto))
                    Routine.showAlert(getResources().getString(R.string.update_erro), getActivity());

                ((AitActivity) getActivity()).setActiveTab(2);

            }
        });

    }

    private void getRecomandedUpdate() {
        editNomeInfrator.setText(dadosAuto.getCompanySocial());
        editCpf.setText(dadosAuto.getCpf());
        editCnpj.setText(dadosAuto.getCnpj());
        editEndereco.setText(dadosAuto.getAddress());
    }

    private void getOtherUpdate() {

        editNomeInfrator.setText(dadosAuto.getCompanySocial());
        editCpf.setText(dadosAuto.getCpf());
        editCnpj.setText(dadosAuto.getCnpj());
        editEndereco.setText(dadosAuto.getAddress());

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
                    case R.id.et_pj_nome_infrator:
                        dadosAuto.setCompanySocial(s.toString());
                        break;
                    case R.id.et_pj_cpf:
                        dadosAuto.setCpf(s.toString());
                        //closeKeyboard(editRegistroCondutor);
                        break;
                    case R.id.et_pj_cnpj:
                        dadosAuto.setCnpj(s.toString());
                        //closeKeyboard(editRegistroCondutor);
                        break;
                    case R.id.spinner_license_state:
                        dadosAuto.setState(s.toString());
                        Routine.openKeyboard(spinnerUfCondutor, getActivity());
                        break;
                    case R.id.et_pj_endereco:
                        dadosAuto.setAddress(s.toString());
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
    }

}
