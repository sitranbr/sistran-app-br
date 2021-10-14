package net.sistransito.mobile.aitcompany;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.aitcompany.lister.PJLister;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PrepopulatedDBOpenHelper;
import net.sistransito.mobile.fragment.UpdateFragment;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.util.ArrayList;
import java.util.List;

public class TabPJInfracaoFragment extends Fragment implements View.OnClickListener,
        UpdateFragment, RadioGroup.OnCheckedChangeListener {

    private View view, separador;
    private AutoCompleteTextView autoCompleteInfracao;
    private pjData dadosAuto;
    private EditText etAutoEnquadra, etAutoDesdob, etAutoArt, etAutoObservacao;
    private FrameLayout frameLayoutObs;
    private TextView tvClearSearch, tvClearObs, tvAutoObs, tvSalvarDados;
    private List<String> autoInfracaoArray, infracaoArray,
            autoObservacao, autoDesdob, autoArt, autoEnquadra;

    private ArrayAdapter<String> adapterInfracao;
    private CheckBox cbAutoConfirmar;

    private LinearLayout layoutEnquadramento;

    public static TabPJInfracaoFragment newInstance() {
        return new TabPJInfracaoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autopj_infracao_fragment, null, false);
        initializedView();
        getAutoObject();
        return view;
    }

    private void getAutoObject() {
        dadosAuto = ObjectAutoPJ.getDadosAuto();
        addListener();
    }

    private void initializedView() {

        layoutEnquadramento = (LinearLayout) view.findViewById(R.id.ll_enquadramento);

        frameLayoutObs = (FrameLayout) view.findViewById(R.id.fl_auto_obs);

        autoCompleteInfracao = (AutoCompleteTextView) view.findViewById(R.id.tv_autocomplete_infracao);
        cbAutoConfirmar = (CheckBox) view.findViewById(R.id.cb_pj_confirmar);

        etAutoEnquadra = (EditText) view.findViewById(R.id.et_auto_enquadramento);
        etAutoDesdob = (EditText) view.findViewById(R.id.et_auto_desdobramento);
        etAutoArt = (EditText) view.findViewById(R.id.et_auto_amparo_legal);
        etAutoObservacao = (EditText) view.findViewById(R.id.et_auto_observacao);

        separador = (View) view.findViewById(R.id.separador);

        etAutoEnquadra.setEnabled(false);
        etAutoDesdob.setEnabled(false);
        etAutoArt.setEnabled(false);

        tvClearSearch = (TextView) view.findViewById(R.id.tv_clear_search);
        tvClearObs = (TextView) view.findViewById(R.id.tv_clear_obs);
        tvAutoObs = (TextView) view.findViewById(R.id.tv_ait_observations);
        tvSalvarDados = (TextView) view.findViewById(R.id.auto_pj_fab);

        frameLayoutObs.setVisibility(view.GONE);

        setInfracaoAutoComplete();

    }

    private void hideComponents(){

        tvClearSearch.setVisibility(TextView.GONE);
        tvClearObs.setVisibility(TextView.GONE);
        layoutEnquadramento.setVisibility(EditText.GONE);
        frameLayoutObs.setVisibility(view.GONE);

    }

    private void  showComponents(){

        tvClearSearch.setVisibility(TextView.VISIBLE);
        tvClearObs.setVisibility(TextView.VISIBLE);
        layoutEnquadramento.setVisibility(EditText.VISIBLE);
        frameLayoutObs.setVisibility(view.VISIBLE);

    }

    private void setInfracaoAutoComplete() {

        Cursor cursor = (DatabaseCreator
                .getPrepopulatedDBOpenHelper(getActivity()))
                .getInfrationCursor();

        infracaoArray = new ArrayList<String>();
        autoInfracaoArray = new ArrayList<String>();
        autoDesdob = new ArrayList<String>();
        autoArt = new ArrayList<String>();
        autoEnquadra = new ArrayList<String>();
        autoObservacao = new ArrayList<String>();

        for (int i = 0; i < cursor.getCount(); i++) {
            autoInfracaoArray.add(
                    cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)) + " - " +
                            cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION))
            );
            infracaoArray
                    .add(cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION))
                    );
            autoEnquadra
                    .add(cursor.getString(cursor
                            .getColumnIndex(PrepopulatedDBOpenHelper.AIT_FLAMING_CODE)));
            autoDesdob
                    .add(cursor.getString(cursor
                            .getColumnIndex(PrepopulatedDBOpenHelper.AIT_UNFOLDING)));
            autoArt.add(cursor.getString(cursor
                    .getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)));

            autoObservacao
                    .add(cursor.getString(cursor
                            .getColumnIndex(PrepopulatedDBOpenHelper.AIT_OBSERVATION)));

            cursor.moveToNext();
        }
        cursor.close();

        adapterInfracao = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_autocomplete, R.id.autoCompleteItem,
                autoInfracaoArray);

        autoCompleteInfracao.setAdapter(adapterInfracao);

        autoCompleteInfracao
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int pos, long id) {

                        Routine.closeKeyboard(autoCompleteInfracao, getActivity());

                        dadosAuto.setInfration((String) parent.getItemAtPosition(pos));
                        int real_position = autoInfracaoArray.indexOf(dadosAuto.getInfration());

                        dadosAuto.setInfration(infracaoArray.get(real_position));
                        //etAutoEnquadra.setText(dadosAuto.getCodigoInfracao());

                        dadosAuto.setFramingCode(autoEnquadra.get(real_position));
                        etAutoEnquadra.setText(dadosAuto.getFramingCode());

                        dadosAuto.setUnfolding(autoDesdob.get(real_position));
                        etAutoDesdob.setText(dadosAuto.getUnfolding());

                        dadosAuto.setArticle(autoArt.get(real_position));
                        etAutoArt.setText(dadosAuto.getArticle());

                        dadosAuto.setObservation(autoObservacao.get(real_position));
                        etAutoObservacao.setText(autoObservacao.get(real_position));

                        showComponents();

                    }
                });

    }

    private void addListener() {

        tvClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoCompleteInfracao.setText(""); // clear your TextView
                etAutoArt.setText("");
                etAutoEnquadra.setText("");
                etAutoDesdob.setText("");
                etAutoObservacao.setText("");
                tvAutoObs.setText("");

                Routine.openKeyboard(autoCompleteInfracao, getActivity());

                hideComponents();
            }
        });

        tvClearObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvAutoObs.setVisibility(View.VISIBLE);
                tvClearObs.setVisibility(View.GONE);
                tvAutoObs.setText(dadosAuto.getObservation());
                etAutoObservacao.setText("");
                etAutoObservacao.requestFocus();

                Routine.openKeyboard(tvClearObs, getActivity());

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
                if (checkInput()) {

                    if (!DatabaseCreator.getAitPJDatabaseAdapter(getActivity()).setDadosPJInfracao(dadosAuto))
                        Routine.showAlert(getResources().getString(R.string.update_infracao), getActivity());

                    startActivity(new Intent(getActivity(), PJLister.class));
                    getActivity().finish();

                }
            }
        });

    }

    private boolean checkInput(){

        if (autoCompleteInfracao.getText().toString().isEmpty()) {
            autoCompleteInfracao.setError(getResources().getString(
                    R.string.texto_inserir_infracao));
            autoCompleteInfracao.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void Update() {

    }

    @Override
    public void onCheckedChanged(RadioGroup v, int check_id) {
       /*
        switch (v.getId()) {
            case R.id.rg_procedimentos:
                if (check_id != -1) {
                    dadosAuto.setProcedimentos(((RadioButton) view
                            .findViewById(rgProcedimentos
                                    .getCheckedRadioButtonId())).getText()
                            .toString());
                }
                break;
        }
        */
    }

    @Override
    public void onClick(View v) {

    }

}
