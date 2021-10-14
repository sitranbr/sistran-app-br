package net.sistransito.mobile.tav;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.tav.lister.TAVLister;
import net.sistransito.R;

import java.util.Arrays;
import java.util.List;

public class TavGenerateFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private View view;
	private TavData tavData;

	private RadioGroup rgFuelGauge1,
			rgFuelGauge2;
	private EditText etOdometer, etCompanyName,
			etWinchDriverName, etTavObservation;

	private Button btnTavCheck, btnTavGeneration;

	private Spinner spRemovedVia;
	private CheckBox cbTav12, cbTav14, cbTavEmpty, cbTavBroken,
			cbTavFull, cbTav34;

	private List<String> listRemovedVia;
	private AnyArrayAdapter<String> aaaRemovedVia;

	private LinearLayout linearLayoutParent, linearLayoutChild;
	public static TavGenerateFragment newInstance() {
		return new TavGenerateFragment();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tav_geral_fragment, null, false);
		initializedView();
		getAitObject();
		return view;
	}

	private void addListener() {
		etOdometer.addTextChangedListener(new ChangeText(R.id.et_odometo));

		etCompanyName.addTextChangedListener(new ChangeText(
				R.id.et_nome_da_empresa));

		etWinchDriverName.addTextChangedListener(new ChangeText(
				R.id.et_nome_do_condutor_do_guincho));

		etTavObservation.addTextChangedListener(new ChangeText(
				R.id.et_tav_observacao));
		btnTavGeneration.setOnClickListener(this);
		btnTavCheck.setOnClickListener(this);

		spRemovedVia
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {

						String s = (String) parent.getItemAtPosition(pos);
						tavData.setRemovedVia(s);

						if (s.compareTo(listRemovedVia
								.get(listRemovedVia.size() - 1)) == 0) {

							addView();

						} else {
							removeView();
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		rgFuelGauge1.setOnCheckedChangeListener(this);
		rgFuelGauge2.setOnCheckedChangeListener(this);
	}

	private void getAitObject() {
		tavData = TavObject.getTAVObject();

	}

	private void initializedView() {
		etOdometer = (EditText) view.findViewById(R.id.et_odometo);
		etCompanyName = (EditText) view
				.findViewById(R.id.et_nome_da_empresa);
		etWinchDriverName = (EditText) view
				.findViewById(R.id.et_nome_do_condutor_do_guincho);
		etTavObservation = (EditText) view
				.findViewById(R.id.et_tav_observacao);

		btnTavGeneration = (Button) view.findViewById(R.id.btn_tav_gerar);
		btnTavCheck = (Button) view.findViewById(R.id.btn_tav_conferir);

		spRemovedVia = (Spinner) view
				.findViewById(R.id.sp_remocao_atraves_de);

		listRemovedVia = Arrays.asList(getResources().getStringArray(
				R.array.vehicle_removed_by));

		aaaRemovedVia = new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				listRemovedVia);

		spRemovedVia.setAdapter(aaaRemovedVia);

		linearLayoutParent = (LinearLayout) view
				.findViewById(R.id.linearLayout_parent);
		linearLayoutChild = (LinearLayout) view
				.findViewById(R.id.linearLayout_child);

		rgFuelGauge1 = (RadioGroup) view
				.findViewById(R.id.rg_marcador_de_conbutivel_1);
		rgFuelGauge2 = (RadioGroup) view
				.findViewById(R.id.rg_marcador_de_conbutivel_2);

		addListener();

	}

	public void getFuelGauge() {
		String s = null;
		if (cbTavBroken.isChecked())
			s += AppConstants.COMMA + cbTavBroken.getText();
		if (cbTavEmpty.isChecked())
			s += AppConstants.COMMA + cbTavEmpty.getText();
		if (cbTav14.isChecked())
			s += AppConstants.COMMA + cbTav14.getText();
		if (cbTav12.isChecked())
			s += AppConstants.COMMA + cbTav12.getText();
		if (cbTav34.isChecked())
			s += AppConstants.COMMA + cbTav34.getText();
		if (cbTavFull.isChecked())
			s += AppConstants.COMMA + cbTavFull.getText();
		if (s != null) {
			s = s.substring(AppConstants.COMMA.length());
			tavData.setFuelGauge(s);
		}

	}

	private class ChangeText implements TextWatcher {
		private int id;

		public ChangeText(int id) {
			this.id = id;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			String s = editable.toString().trim();
			switch (id) {

			case R.id.et_odometo:
				tavData.setOdometer(s);
				break;

			case R.id.et_nome_da_empresa:
				tavData.setCompanyName(s);
				break;

			case R.id.et_nome_do_condutor_do_guincho:
				tavData.setWinchDriverName(s);
				break;

			case R.id.et_tav_observacao:
				tavData.setObservation(s);
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
		case R.id.btn_tav_gerar:
			(DatabaseCreator.getTavDatabaseAdapter(getActivity()))
					.setData(tavData);
			getActivity().startActivity(
					new Intent(getActivity(), TAVLister.class));

			getActivity().finish();

			break;

		case R.id.btn_tav_conferir:

			AnyAlertDialog.dialogShow(tavData.getTAVListerView(getActivity()),
					getActivity(), getResources()
							.getString(R.string.listar_tav));

			break;
		}

	}

	private void removeView() {
		if (linearLayoutChild.getParent() != null) {
			linearLayoutParent.removeView(linearLayoutChild);
		}

	}

	private void addView() {
		if (linearLayoutChild.getParent() == null) {
			linearLayoutParent.addView(linearLayoutChild);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup v, int check_id) {

		switch (v.getId()) {
		case R.id.rg_marcador_de_conbutivel_1:
			rgFuelGauge2.setOnCheckedChangeListener(null);
			rgFuelGauge2.clearCheck();
			tavData.setFuelGauge(((RadioButton) (view
					.findViewById(rgFuelGauge1
							.getCheckedRadioButtonId()))).getText().toString());
			rgFuelGauge2.setOnCheckedChangeListener(this);

			break;

		case R.id.rg_marcador_de_conbutivel_2:
			rgFuelGauge1.setOnCheckedChangeListener(null);
			rgFuelGauge1.clearCheck();
			tavData.setFuelGauge(((RadioButton) (view
					.findViewById(rgFuelGauge2
							.getCheckedRadioButtonId()))).getText().toString());
			rgFuelGauge1.setOnCheckedChangeListener(this);

			break;

		}

	}

}
