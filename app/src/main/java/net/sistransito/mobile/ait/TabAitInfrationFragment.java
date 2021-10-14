package net.sistransito.mobile.ait;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PrepopulatedDBOpenHelper;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.fragment.UpdateFragment;
import net.sistransito.mobile.util.Cnpj;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabAitInfrationFragment extends Fragment implements OnClickListener,
		UpdateFragment, OnCheckedChangeListener {

	private View view, separator;
	private AutoCompleteTextView autoCompleteInfration;
	private AitData aitData;
	private RadioGroup rgProcedures, rgrRetreat, rgShipperIdentification, rgCarrierIdentification;
	private RadioButton rbAlcoholTestNumber, rbTca, rbCpfShipper, rbCnpjShipper,
			rbCpfCarrier, rbCnpjCarrier;
	private EditText etFramingCode, etUnfolding, etArticle, etObservation,
			etEquipmentBrand, etEquipmentModel, etSerialNumber,
			etMeasurementPerformed, etRegulatedValue, etValueConsidered, etAlcoholTestNumber, etTcaNumber,
			etShipperName, etShipperCpf, etShipperCnpj,
			etCarrierName, etCarrierCpf, etCarrierCnpj;
	private TextInputLayout tilAutoTca,tilCpfEmbarcador, tilCnpjEmbarcador, tilCpfTransportador, tilCnpjTransportador;
	private TextView tvClearSearch, tvClearObs, tvAitObservation, TvSaveData;
	private Spinner spinnerAitDescription, spinnerMeasuringUnit;
	private List<String> listDescription, listBrand, listModel, listMeasurement,
			listSerialNumber, listAitArray, listInfrationArray,
			listAitObservation, listAitUnfolding, listArticle, listAitFramingCode;
	private AnyArrayAdapter<String> aaaEquipmentDescription, aaaMeasuringUnit;

	private ArrayAdapter<String> aaInfration;
	private CheckBox cbTca, cbEquipment, cbProcedures, cbCarrier, cbConfirm;

	private LinearLayout llFramingCode, llEquipment, llProcedures, llCarrier;

	private FrameLayout flObservation;

	private String sMeasuringUnit;
	boolean ifCalculate = false;

	public static TabAitInfrationFragment newInstance() {
		return new TabAitInfrationFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tab_ait_infration_fragment, null, false);
		initializedView();
		getAutoObject();
		return view;
	}

	private void initializedView() {

		llFramingCode = (LinearLayout) view.findViewById(R.id.ll_enquadramento);
		llEquipment = (LinearLayout) view.findViewById(R.id.ll_auto_equipamento);
		llProcedures = (LinearLayout) view.findViewById(R.id.ll_auto_procedimentos);
		llCarrier = (LinearLayout) view.findViewById(R.id.ll_auto_transportador);

		flObservation = (FrameLayout) view.findViewById(R.id.fl_auto_obs);

		autoCompleteInfration = (AutoCompleteTextView) view.findViewById(R.id.tv_autocomplete_infracao);
		cbTca = (CheckBox) view.findViewById(R.id.cb_auto_tca);
		cbEquipment = (CheckBox) view.findViewById(R.id.cb_equipamento_afericao);
		cbProcedures = (CheckBox) view.findViewById(R.id.cb_procedimentos);
		cbCarrier = (CheckBox) view.findViewById(R.id.cb_transportador);
		cbConfirm = (CheckBox) view.findViewById(R.id.cb_ait_confirm);

		etTcaNumber = (EditText) view.findViewById(R.id.et_auto_numero_tca);
		etFramingCode = (EditText) view.findViewById(R.id.et_auto_enquadramento);
		etUnfolding = (EditText) view.findViewById(R.id.et_auto_desdobramento);
		etArticle = (EditText) view.findViewById(R.id.et_auto_amparo_legal);
		etObservation = (EditText) view.findViewById(R.id.et_auto_observacao);

		etEquipmentBrand = (EditText) view.findViewById(R.id.et_auto_marca_equipamento);
		etEquipmentModel = (EditText) view
				.findViewById(R.id.et_auto_modelo_equipamento);

		etSerialNumber = (EditText) view
				.findViewById(R.id.et_auto_numero_de_serie);

		etMeasurementPerformed = (EditText) view
				.findViewById(R.id.et_auto_medicao_realizada);
		etRegulatedValue = (EditText) view.findViewById(R.id.et_auto_limite_regulamentado);
		etValueConsidered = (EditText) view.findViewById(R.id.et_auto_valor_medicao);
		//etAutoValor.setEnabled(false);
		etAlcoholTestNumber = (EditText) view
				.findViewById(R.id.et_auto_numero_amostra);

		etShipperName = (EditText) view.findViewById(R.id.et_auto_nome_embarcador) ;
		etCarrierName = (EditText) view.findViewById(R.id.et_auto_nome_transportador);

		etShipperCpf = (EditText) view.findViewById(R.id.et_cpf_embarcador);
		etShipperCnpj = (EditText) view.findViewById(R.id.et_cnpj_embarcador);
		etCarrierCpf = (EditText) view.findViewById(R.id.et_cfp_transportador);
		etCarrierCnpj = (EditText) view.findViewById(R.id.et_cnpj_transportador);

		tilAutoTca = (TextInputLayout) view.findViewById(R.id.til_auto_tca);
		tilCpfEmbarcador = (TextInputLayout) view.findViewById(R.id.til_cpf_embarcador);
		tilCnpjEmbarcador = (TextInputLayout) view.findViewById(R.id.til_cnpj_embarcador);
		tilCpfTransportador = (TextInputLayout) view.findViewById(R.id.til_cpf_transportador);
		tilCnpjTransportador = (TextInputLayout) view.findViewById(R.id.til_cnpj_transportador);

		rgrRetreat = (RadioGroup) view.findViewById(R.id.rg_recolhimento);
		rgProcedures = (RadioGroup) view
				.findViewById(R.id.rg_procedimentos);
		rgShipperIdentification = (RadioGroup) view.findViewById(R.id.rg_tipo_documento_embarcador);
		rgCarrierIdentification = (RadioGroup) view.findViewById(R.id.rg_tipo_documento_transportador);
		//radioAmostra = (RadioButton) view.findViewById(R.id.radioAmostra);
		//radioTca = (RadioButton) view.findViewById(R.id.radioTca);
		rbCpfShipper = (RadioButton) view.findViewById(R.id.rg_cpf_embarcador);
		rbCnpjShipper = (RadioButton) view.findViewById(R.id.rg_cnpj_embarcador);
		rbCpfCarrier = (RadioButton) view.findViewById(R.id.rg_cpf_transportador);
		rbCnpjCarrier = (RadioButton) view.findViewById(R.id.rg_cnpj_transportador);
		separator = (View) view.findViewById(R.id.separador);

		etFramingCode.setEnabled(false);
		etUnfolding.setEnabled(false);
		etArticle.setEnabled(false);

		tvClearSearch = (TextView) view.findViewById(R.id.tv_clear_search);
		tvClearObs = (TextView) view.findViewById(R.id.tv_clear_obs);
		tvAitObservation = (TextView) view.findViewById(R.id.tv_ait_observations);
		TvSaveData = (TextView) view.findViewById(R.id.ait_fab);

		etEquipmentBrand.setEnabled(false);
		etEquipmentModel.setEnabled(false);
		etSerialNumber.setEnabled(false);

		etRegulatedValue.setEnabled(false);
		etValueConsidered.setEnabled(false);

		flObservation.setVisibility(view.GONE);
		tilAutoTca.setVisibility(view.GONE);
		tilCnpjEmbarcador.setVisibility(view.GONE);
		tilCnpjTransportador.setVisibility(view.GONE);

		setAutoCompleteInfration();
		setSpinnerDescription();
		setSpinnerUnidades();

	}

	private void setSpinnerUnidades(){

		listMeasurement = Arrays.asList(getResources().getStringArray(
				R.array.array_measurement_units));

		aaaMeasuringUnit = new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				listMeasurement);

		spinnerMeasuringUnit = (Spinner) view.findViewById(R.id.spinner_medicao_unidade);
		spinnerMeasuringUnit.setAdapter(aaaMeasuringUnit);

		spinnerMeasuringUnit
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
											   View view, int pos, long Id) {

						sMeasuringUnit = ((String) parent.getItemAtPosition(pos));

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		spinnerMeasuringUnit.setSelection(3);

	}

	private void hideComponents(){

		tvClearSearch.setVisibility(TextView.GONE);
		tvClearObs.setVisibility(TextView.GONE);
		llFramingCode.setVisibility(EditText.GONE);
		flObservation.setVisibility(view.GONE);

	}

	private void  showComponents(){

		tvClearSearch.setVisibility(TextView.VISIBLE);
		tvClearObs.setVisibility(TextView.VISIBLE);
		llFramingCode.setVisibility(EditText.VISIBLE);
		flObservation.setVisibility(view.VISIBLE);

	}

	private void setSpinnerDescription() {

		listDescription = new ArrayList<String>();
		listBrand = new ArrayList<String>();
		listModel = new ArrayList<String>();
		listSerialNumber = new ArrayList<String>();

		Cursor cursor = ((DatabaseCreator
				.getPrepopulatedDBOpenHelper(getActivity()))
				.getEquipmentCursor());
		cursor.moveToFirst();

		for (int i = 0; i < cursor.getCount(); i++) {

			listDescription.add(
					cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.EQUIPMENT_DESCRIPTION)));

			listBrand
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.EQUIPMENT_BRAND)));

			listModel
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.EQUIPMENT_MODEL)));

			listSerialNumber
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.EQUIPMENT_SERIAL)));

			cursor.moveToNext();
		}
		cursor.close();

		aaaEquipmentDescription = new AnyArrayAdapter<String>(getActivity(),
				R.layout.spinner_custom_item, android.R.id.text1,
				listDescription);

		spinnerAitDescription = (Spinner) view.findViewById(R.id.spinner_auto_descricao_equipamento);
		spinnerAitDescription.setAdapter(aaaEquipmentDescription);

		spinnerAitDescription
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {

						aitData.setDescription((String) parent.getItemAtPosition(pos));

						int realposition = listDescription.indexOf(aitData.getDescription());

						if (realposition == 0){
							ifCalculate = true;
							spinnerMeasuringUnit.setSelection(4);
							spinnerMeasuringUnit.setEnabled(false);
							etRegulatedValue.setEnabled(false);
							etValueConsidered.setEnabled(false);
							etMeasurementPerformed.setText("");
							etMeasurementPerformed.requestFocus();
							etValueConsidered.setText("");
							etRegulatedValue.setText("");
							etRegulatedValue.setText("0,00 Mg/L");
							aitData.setRegulatedValue("0,00 Mg/L");
						} else {
							ifCalculate = false;
							spinnerMeasuringUnit.setSelection(3);
							spinnerMeasuringUnit.setEnabled(true);
							etRegulatedValue.setEnabled(true);
							etValueConsidered.setEnabled(true);
							etMeasurementPerformed.setText("");
							etMeasurementPerformed.requestFocus();
							etValueConsidered.setText("");
							etRegulatedValue.setText("");
							aitData.setRegulatedValue(null);
						}

						//Toast.makeText(getActivity(), "Posição " + realposition, Toast.LENGTH_SHORT).show();

						if (realposition == -1) {
							clearAllFildsEquipament();
						} else {
							aitData.setEquipmentBrand(listBrand.get(realposition));
							aitData.setEquipmentModel(listModel.get(realposition));
							aitData.setSerialNumber(listSerialNumber.get(realposition));

							etEquipmentBrand.setText(aitData.getEquipmentBrand());
							etEquipmentModel.setText(aitData.getEquipmentModel());
							etSerialNumber.setText(aitData.getSerialNumber());

						}

						//Toast.makeText(getActivity(), "Posição atual" + realposition, Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void setAutoCompleteInfration() {

		Cursor cursor = (DatabaseCreator
				.getPrepopulatedDBOpenHelper(getActivity()))
				.getInfrationCursor();

		listInfrationArray = new ArrayList<String>();
		listAitArray = new ArrayList<String>();
		listAitUnfolding = new ArrayList<String>();
		listArticle = new ArrayList<String>();
		listAitFramingCode = new ArrayList<String>();
		listAitObservation = new ArrayList<String>();

		for (int i = 0; i < cursor.getCount(); i++) {

			listAitArray.add(
					cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)) + " - " +
							cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION))
			);
			listInfrationArray
					.add(cursor.getString(cursor.getColumnIndex(PrepopulatedDBOpenHelper.AIT_DESCRIPTION))
					);
			listAitFramingCode
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.AIT_FLAMING_CODE)));
			listAitUnfolding
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.AIT_UNFOLDING)));
			listArticle.add(cursor.getString(cursor
					.getColumnIndex(PrepopulatedDBOpenHelper.AIT_ARTICLE)));

			listAitObservation
					.add(cursor.getString(cursor
							.getColumnIndex(PrepopulatedDBOpenHelper.AIT_OBSERVATION)));

			cursor.moveToNext();

		}
		cursor.close();

		aaInfration = new ArrayAdapter<String>(getActivity(),
				R.layout.custom_autocomplete, R.id.autoCompleteItem,
				listAitArray);

		autoCompleteInfration.setAdapter(aaInfration);

		autoCompleteInfration
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int pos, long id) {

						Routine.closeKeyboard(autoCompleteInfration, getActivity());

						aitData.setInfraction((String) parent.getItemAtPosition(pos));
						int realPosition = listAitArray.indexOf(aitData.getInfraction());

						aitData.setInfraction(listInfrationArray.get(realPosition));
						//etAutoEnquadra.setText(dadosAuto.getCodigoInfracao());

						aitData.setFramingCode(listAitFramingCode.get(realPosition));
						etFramingCode.setText(aitData.getFramingCode());

						aitData.setUnfolding(listAitUnfolding.get(realPosition));
						etUnfolding.setText(aitData.getUnfolding());

						aitData.setArticle(listArticle.get(realPosition));
						etArticle.setText(aitData.getArticle());

						aitData.setObservation(listAitObservation.get(realPosition));

						String obs = aitData.getObservation();

						etObservation.setText(replaceStringObs(obs));

						showComponents();

					}
				});

	}

	public String replaceStringObs(String obs){

		String idRRD = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
				.getRrdNumber();

		obs = obs.replace("ARAA-0000","ARAA-0816");
		obs = obs.replace("CNH","CNH nº " + aitData.getCnhPpd());
		obs = obs.replace("cnh","CNH nº ...");
		obs = obs.replace("RRD","RRD nº " + idRRD);

		return obs;
	}

	private void addListener() {

		etObservation.addTextChangedListener(new ChangeText(R.id.et_auto_observacao));
		etEquipmentBrand.addTextChangedListener(new ChangeText(R.id.et_auto_marca_equipamento));
		etSerialNumber.addTextChangedListener(new ChangeText(R.id.et_auto_numero_de_serie));
		etEquipmentModel.addTextChangedListener(new ChangeText(R.id.et_auto_modelo_equipamento));
		etMeasurementPerformed.addTextChangedListener(new ChangeText(R.id.et_auto_medicao_realizada));
		etRegulatedValue.addTextChangedListener(new ChangeText(R.id.et_auto_limite_regulamentado));
		etValueConsidered.addTextChangedListener(new ChangeText(R.id.et_auto_valor_medicao));
		etAlcoholTestNumber.addTextChangedListener(new ChangeText(R.id.et_auto_numero_amostra));
		etShipperName.addTextChangedListener(new ChangeText(R.id.et_auto_nome_embarcador));
		etCarrierName.addTextChangedListener(new ChangeText(R.id.et_auto_nome_transportador));
		etShipperCpf.addTextChangedListener(new ChangeText(R.id.et_cpf_embarcador));
		etShipperCnpj.addTextChangedListener(new ChangeText(R.id.et_cnpj_embarcador));
		etCarrierCpf.addTextChangedListener(new ChangeText(R.id.et_cfp_transportador));
		etCarrierCnpj.addTextChangedListener(new ChangeText(R.id.et_cnpj_transportador));

		rgProcedures.setOnCheckedChangeListener(this);
		rgrRetreat.setOnCheckedChangeListener(this);

		tvClearSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				autoCompleteInfration.setText(""); // clear your TextView
				etArticle.setText("");
				etFramingCode.setText("");
				etUnfolding.setText("");
				etObservation.setText("");
				tvAitObservation.setText("");

				Routine.openKeyboard(autoCompleteInfration, getActivity());

				hideComponents();
			}
		});

		tvClearObs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				tvAitObservation.setVisibility(View.VISIBLE);
				tvClearObs.setVisibility(View.GONE);
				tvAitObservation.setText(aitData.getObservation());
				etObservation.setText("");
				etObservation.requestFocus();

				Routine.openKeyboard(tvClearObs, getActivity());

			}
		});

		cbTca.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(cbTca.isChecked()){
					String idTCA = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
							.getTcaNumber();
					etTcaNumber.setText(idTCA);
					aitData.setTcaNumber(idTCA);
					tilAutoTca.setVisibility(View.VISIBLE);
                    cbEquipment.setVisibility(View.GONE);
                    cbCarrier.setVisibility(View.GONE);
				}else{
					etTcaNumber.setText("");
					aitData.setTcaNumber(null);
					tilAutoTca.setVisibility(View.GONE);
                    cbEquipment.setVisibility(View.VISIBLE);
                    cbCarrier.setVisibility(View.VISIBLE);
				}
			}

		});

		cbProcedures.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(cbProcedures.isChecked()){
					llProcedures.setVisibility(View.VISIBLE);
					Routine.closeKeyboard(llProcedures, getActivity());
                    aitData.setProcedures("");
                    aitData.setRetreat("");
				}else{
					llProcedures.setVisibility(View.GONE);
					rgProcedures.clearCheck();
					rgrRetreat.clearCheck();
                    aitData.setProcedures("");
                    aitData.setRetreat("");
				}
			}
		});

		cbEquipment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cbEquipment.isChecked()){
					llEquipment.setVisibility(View.VISIBLE);
					Routine.closeKeyboard(llEquipment, getActivity());
				}else{
					llEquipment.setVisibility(View.GONE);
					spinnerAitDescription.setSelection(0);
					clearAllFildsEquipament();
				}
			}
		});

		cbCarrier.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (cbCarrier.isChecked()) {
					llCarrier.setVisibility(View.VISIBLE);
					separator.setVisibility(View.GONE);
					Routine.closeKeyboard(llCarrier, getActivity());
				}else{
					llCarrier.setVisibility(View.GONE);
					separator.setVisibility(View.VISIBLE);
					etCarrierName.setText("");
					etCarrierCpf.setText("");
					etCarrierCnpj.setText("");
					etShipperName.setText("");
					etShipperCpf.setText("");
					etShipperCnpj.setText("");
					rgShipperIdentification.clearCheck();
					rgCarrierIdentification.clearCheck();
				}
			}
		});

		rbCpfShipper.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCpfShipper.isChecked()){
					tilCpfEmbarcador.setVisibility(view.VISIBLE);
					tilCnpjEmbarcador.setVisibility(view.GONE);
					etShipperCnpj.setText("");
					etShipperCpf.requestFocus();
					Routine.openKeyboard(etShipperCpf, getActivity());
				}
			}
		});

		rbCnpjShipper.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCnpjShipper.isChecked()){
					tilCpfEmbarcador.setVisibility(view.GONE);
					tilCnpjEmbarcador.setVisibility(view.VISIBLE);
					etShipperCpf.setText("");
					etShipperCnpj.requestFocus();
					Routine.openKeyboard(etShipperCnpj, getActivity());
				}
			}
		});

		rbCpfCarrier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCpfCarrier.isChecked()){
					tilCpfTransportador.setVisibility(view.VISIBLE);
					tilCnpjTransportador.setVisibility(view.GONE);
					etCarrierCnpj.setText("");
					etCarrierCpf.requestFocus();
					Routine.openKeyboard(etCarrierCpf, getActivity());
				}
			}
		});

		rbCnpjCarrier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCnpjCarrier.isChecked()){
					tilCpfTransportador.setVisibility(view.GONE);
					tilCnpjTransportador.setVisibility(view.VISIBLE);
					etCarrierCpf.setText("");
					etCarrierCnpj.requestFocus();
					Routine.openKeyboard(etCarrierCnpj, getActivity());
				}
			}
		});

		cbConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cbConfirm.isChecked()) {
					TvSaveData.setVisibility(view.VISIBLE);
					Routine.closeKeyboard(TvSaveData, getActivity());
				}else{
					TvSaveData.setVisibility(view.GONE);
				}
			}
		});

		TvSaveData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (checkInput()) {

					if (aitData.isStoreFullData()) {
						//Log.d("AutoExiste", "linha 567");
						if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).insertAidData(aitData))
							Routine.showAlert(getResources().getString(R.string.update_veiculo), getActivity());

						if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).updateAitDataConductor(aitData))
							Routine.showAlert(getResources().getString(R.string.update_condutor), getActivity());

						if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).updateAitDataAddress(aitData))
							Routine.showAlert(getResources().getString(R.string.update_endereco), getActivity());

						if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).updateAitDataInfraction(aitData))
							Routine.showAlert(getResources().getString(R.string.update_infracao), getActivity());

					}else {
						if (!DatabaseCreator.getAitDatabaseAdapter(getActivity()).updateAitDataInfraction(aitData))
							Routine.showAlert(getResources().getString(R.string.update_infracao), getActivity());
					}

					((AitActivity) getActivity()).setActiveTab(4);

				}

			}
		});

	}

	private boolean checkInput(){

		if (autoCompleteInfration.getText().toString().isEmpty()) {
			autoCompleteInfration.setError(getResources().getString(
					R.string.texto_inserir_infracao));
			autoCompleteInfration.requestFocus();
			return false;
		} else {
			return true;
		}

	}

	public void clearAllFildsEquipament(){
		etEquipmentBrand.setText("");
		etEquipmentModel.setText("");
		etSerialNumber.setText("");
		etMeasurementPerformed.setText("");
		etRegulatedValue.setText("");
		etValueConsidered.setText("");
		etAlcoholTestNumber.setText("");
		spinnerMeasuringUnit.setSelection(0);
		spinnerAitDescription.requestFocus();
	}

	private boolean checkNumberMedicaoNumber(String s) {

		try {
			double medicaoConsiderada = 0.00;
			double medicaoRealizada = Double.valueOf(s);

			if (medicaoRealizada < 0.05) {
				etValueConsidered.setText(String.valueOf("0,00"));
			} else if (medicaoRealizada >= 0.05
					&& medicaoRealizada <= 0.50) {
				medicaoConsiderada = medicaoRealizada - 0.032;
			} else if (medicaoRealizada > 0.50 && medicaoRealizada <= 2) {
				medicaoConsiderada = medicaoRealizada - (medicaoRealizada * 8) / 100;
			} else {
				medicaoConsiderada = medicaoRealizada - (medicaoRealizada * 30) / 100;
			}

			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.DOWN);
			String format = df.format(medicaoConsiderada);
			if(format.length() < 4){
				format = format + "0";
			}

			etValueConsidered.setText(String.valueOf(format) + " " + sMeasuringUnit);
			aitData.setValueConsidered(String.valueOf(format) + " " + sMeasuringUnit);

			return true;

		} catch (NumberFormatException e) {
			return false;
		}

	}

	private void getAutoObject() {
		aitData = ObjectAit.getAitData();

		if (aitData.isDataisNull()) {
			addListener();
		} else if (aitData.isStoreFullData()) {
			getRecomandedUpdate();
			addListener();
		} else {
			addListener();
			initializedSelectetItems();
		}

	}

	private void initializedSelectetItems() {

		aitData.setProcedures("");
		aitData.setRetreat("");

		//Log.d("initialized: 734: ", String.valueOf(dadosAuto.isStoreFullData()));

	}

	private void getRecomandedUpdate() {

		aitData.setProcedures("");
		aitData.setRetreat("");

		//Log.d("initialized: 743: ", String.valueOf(dadosAuto.isStoreFullData()));

		//etAutoEnquadra.setText(dadosAuto.getEnquadra());
		//etAutoDesdob.setText(dadosAuto.getDesdob());
		//etAutoArt.setText(dadosAuto.getAmparoLegal());
		//etAutoObservacao.setText(dadosAuto.getObservacao());
		//etAutoMarcaEquipamento.setText(dadosAuto.getMarcaDoEquipamento());
		//etAutoModeloEquipamento.setText(dadosAuto.getModeloDoEquipamento());
		//etAutoNumeroSerie.setText(dadosAuto.getNumeroDeSerie());
		//etAutoMedicaoRealizada.setText(dadosAuto.getMedicaoRealizada());
		//etAutoValor.setText(dadosAuto.getValorConsiderada());
		//etAutoNumeroDaAmostra.setText(dadosAuto.getNumeroAmostra());
		//spinnerAutoDescricao.setSelection(0);

	}

	@Override
	public void Update() {

	}

	@Override
	public void onCheckedChanged(RadioGroup v, int check_id) {

		switch (v.getId()) {
			case R.id.rg_recolhimento:
				if (check_id != -1) {
					aitData.setRetreat(((RadioButton) view
							.findViewById(rgrRetreat.getCheckedRadioButtonId()))
							.getText().toString());
				}
				break;
			case R.id.rg_procedimentos:
				if (check_id != -1) {
					aitData.setProcedures(((RadioButton) view
							.findViewById(rgProcedures
									.getCheckedRadioButtonId())).getText()
							.toString());
				}
				break;
		}
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
				case R.id.et_auto_observacao:
					aitData.setObservation(s.toString());
					break;
				case R.id.et_auto_marca_equipamento:
					aitData.setEquipmentBrand(s.toString());
					break;
				case R.id.et_auto_numero_de_serie:
					aitData.setSerialNumber(s.toString());
					break;
				case R.id.et_auto_modelo_equipamento:
					aitData.setEquipmentModel(s.toString());
					break;
				case R.id.et_auto_medicao_realizada:
					if (ifCalculate) {
						if (checkNumberMedicaoNumber(s.toString())) {
							aitData.setMeasurementPerformed(s.toString() + " " + sMeasuringUnit);
						}
					}
					aitData.setMeasurementPerformed(s.toString() + " " + sMeasuringUnit);
					break;
				/*case R.id.et_auto_limite_regulamentado:
					dadosAuto.setValorRegulamentado(s.toString() + " " + unidadeMedicao);
					Log.d("unidadeMedicao: ", unidadeMedicao);
					break;*/
				case R.id.et_auto_valor_medicao:
					aitData.setValueConsidered(s.toString() + " " + sMeasuringUnit);
				case R.id.et_auto_numero_amostra:
					aitData.setAlcoholTestNumber(s.toString());
					break;
				case R.id.et_auto_nome_transportador:
					aitData.setCarrierIdentification(s.toString());
					break;
				case R.id.et_auto_nome_embarcador:
					aitData.setCarrierIdentification(s.toString());
					break;
				case R.id.et_cfp_transportador:
					aitData.setCpfCarrier(s.toString());
					break;
				case R.id.et_cnpj_transportador:
					aitData.setCnpjCarrier(s.toString());
					break;
				case R.id.et_cpf_embarcador:
					aitData.setCpfShipper(s.toString());
					break;
				case R.id.et_cnpj_embarcador:
					aitData.setCnpShipper(s.toString());
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

			if (ifCalculate) {
				if (etMeasurementPerformed.getText().toString().length() == 4) {
					//Log.d("Limite", "alcançado");
				    etMeasurementPerformed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
				    etRegulatedValue.setEnabled(false);
					etValueConsidered.setEnabled(false);
					//etAutoNumeroDaAmostra.requestFocus();
				}
			}else{
				aitData.setRegulatedValue(etRegulatedValue.getText().toString() + " " + sMeasuringUnit);
				etRegulatedValue.setEnabled(true);
				etValueConsidered.setEnabled(true);
			}

			if (etShipperCpf.getText().toString().length() == 11) {
				//boolean retorno = CNPJ.isValidCPF(etCpfEmbarcador.getText().toString());
				if(!Cnpj.isValidCpf(etShipperCpf.getText().toString()))
					AnyAlertDialog.dialogShow(getActivity().getResources()
									.getString(R.string.cpf_embarcador_invalido), getActivity(),
							getActivity().getResources().getString(R.string.msg_erro));
				//Toast.makeText(getActivity(), "CPF embarcador inválido " + retorno, Toast.LENGTH_SHORT).show();
			}

			if (etShipperCnpj.getText().toString().length() == 14) {
				//boolean retorno = CNPJ.isValidCPF(etCpfEmbarcador.getText().toString());
				if(!Cnpj.isValidCnpj(etShipperCnpj.getText().toString()))
					AnyAlertDialog.dialogShow(getActivity().getResources()
									.getString(R.string.cnpj_embarcador_invalido), getActivity(),
							getActivity().getResources().getString(R.string.msg_erro));
				//Toast.makeText(getActivity(), "CPF embarcador inválido " + retorno, Toast.LENGTH_SHORT).show();
			}

			if (etCarrierCpf.getText().toString().length() == 11) {
				//boolean retorno = CNPJ.isValidCPF(etCpfEmbarcador.getText().toString());
				if(!Cnpj.isValidCpf(etCarrierCpf.getText().toString()))
					AnyAlertDialog.dialogShow(getActivity().getResources()
									.getString(R.string.cpf_tranportador_invalido), getActivity(),
							getActivity().getResources().getString(R.string.msg_erro));
				//Toast.makeText(getActivity(), "CPF embarcador inválido " + retorno, Toast.LENGTH_SHORT).show();
			}

			if (etCarrierCnpj.getText().toString().length() == 14) {
				//boolean retorno = CNPJ.isValidCPF(etCpfEmbarcador.getText().toString());
				if(!Cnpj.isValidCnpj(etCarrierCnpj.getText().toString()))
					AnyAlertDialog.dialogShow(getActivity().getResources()
									.getString(R.string.cnpj_tranportador_invalido), getActivity(),
							getActivity().getResources().getString(R.string.msg_erro));
				//Toast.makeText(getActivity(), "CPF embarcador inválido " + retorno, Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void onClick(View v) {

	}

}
