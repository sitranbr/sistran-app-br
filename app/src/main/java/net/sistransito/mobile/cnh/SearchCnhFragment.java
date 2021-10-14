package net.sistransito.mobile.cnh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.cnh.dados.CnhHttpResultAsyncTask;
import net.sistransito.mobile.cnh.dados.CnhViewFormat;
import net.sistransito.mobile.cnh.dados.DataFromCnh;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.fragment.CallBackCnh;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.R;

public class SearchCnhFragment extends Fragment implements
		View.OnClickListener {
	private View view;
    DataFromCnh fromCNH;
	private CheckBox cbOfflineSearch;
	private Button btnSearch;
	private LinearLayout llResultParent, llRadio, llInput;
	private TextView tvShowResult;
	private EditText etRegister, etIdentity, etCnhCpf, etName, etMothersName, etDate;
	private RadioButton rbRegister, rbIdentity, rbCnhCpf, rbCnhName;
	private String sSearch, sEmptyField;
	private CnhHttpResultAsyncTask httpResultAnysTask;
	private String typeSearch;

	public static SearchCnhFragment newInstance() {
		return new SearchCnhFragment();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cnh_search_fragment, null, false);
		initializedView();
		return view;
	}

	private void initializedView() {
		cbOfflineSearch = (CheckBox) view
				.findViewById(R.id.cb_cnh_offline);
		rbRegister = (RadioButton) view.findViewById(R.id.rb_cnh_registro);
		rbIdentity = (RadioButton) view.findViewById(R.id.rb_cnh_identidade);
		rbCnhCpf = (RadioButton) view.findViewById(R.id.rb_cnh_cpf);
		rbCnhName = (RadioButton) view.findViewById(R.id.rb_cnh_nome);
		btnSearch = (Button) view.findViewById(R.id.btn_consultar_cnh);
		btnSearch.setOnClickListener(this);
		llResultParent = (LinearLayout) view
				.findViewById(R.id.ll_result_parent);
        llRadio = (LinearLayout) view
                .findViewById(R.id.ll_radio);
        llInput = (LinearLayout) view
                .findViewById(R.id.ll_input);
		tvShowResult = (TextView) view.findViewById(R.id.tv_show_result);
		tvShowResult.setOnClickListener(this);
		etRegister = (EditText) view.findViewById(R.id.et_registro_input);
		etIdentity = (EditText) view.findViewById(R.id.et_cnh_input);
		etCnhCpf = (EditText) view.findViewById(R.id.et_cpf_input);
		etName = (EditText) view.findViewById(R.id.et_nome_input);
		etMothersName = (EditText) view.findViewById(R.id.et_mae_input);
		etDate = (EditText) view.findViewById(R.id.et_data_input);

		etRegister.addTextChangedListener(new ChangeText(
				R.id.et_registro_input));
		etIdentity.addTextChangedListener(new ChangeText(
				R.id.et_cnh_input));
		etCnhCpf.addTextChangedListener(new ChangeText(
				R.id.et_cpf_input));
		etName.addTextChangedListener(new ChangeText(
				R.id.et_nome_input));
		etMothersName.addTextChangedListener(new ChangeText(
				R.id.et_mae_input));
		etDate.addTextChangedListener(new ChangeText(
				R.id.et_data_input));

		etDate.setOnClickListener(this);

		etRegister.setEnabled(true);
		etIdentity.setEnabled(false);
		etCnhCpf.setEnabled(false);
		etName.setEnabled(false);
		etMothersName.setEnabled(false);
		etDate.setEnabled(false);

		fromCNH = new DataFromCnh();

		cbOfflineSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etCnhCpf.requestFocus();
			}
		});

		if (rbIdentity.isChecked()){
			sEmptyField = etIdentity.getText().toString();
			typeSearch = "rg";
		}

		rbRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbRegister.isChecked()){
					etRegister.setVisibility(View.VISIBLE);
					etIdentity.setVisibility(View.GONE);
					etCnhCpf.setVisibility(View.GONE);
					etName.setVisibility(View.GONE);
					etMothersName.setVisibility(View.GONE);
					etDate.setVisibility(View.GONE);
					etIdentity.setEnabled(true);
					clearAllFields();

					sEmptyField = etIdentity.getText().toString();
					typeSearch = "cnh";
				}
			}
		});

		rbIdentity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbIdentity.isChecked()){
					etRegister.setVisibility(View.GONE);
					etIdentity.setVisibility(View.VISIBLE);
					etCnhCpf.setVisibility(View.GONE);
					etName.setVisibility(View.GONE);
					etMothersName.setVisibility(View.GONE);
					etDate.setVisibility(View.GONE);
                    etIdentity.setEnabled(true);
					clearAllFields();

					sEmptyField = etIdentity.getText().toString();
					typeSearch = "rg";
				}
			}
		});

		rbCnhCpf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCnhCpf.isChecked()){
					etRegister.setVisibility(View.GONE);
					etIdentity.setVisibility(View.GONE);
					etCnhCpf.setVisibility(View.VISIBLE);
					etName.setVisibility(View.GONE);
					etMothersName.setVisibility(View.GONE);
					etDate.setVisibility(View.GONE);
                    etCnhCpf.setEnabled(true);
					clearAllFields();

					sEmptyField = etCnhCpf.getText().toString();
					typeSearch = "cpf";
				}
			}
		});

		rbCnhName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rbCnhName.isChecked()){
					etRegister.setVisibility(View.GONE);
					etIdentity.setVisibility(View.GONE);
					etCnhCpf.setVisibility(View.GONE);
					etName.setVisibility(View.VISIBLE);
					etMothersName.setVisibility(View.VISIBLE);
					etDate.setVisibility(View.VISIBLE);
                    etName.setEnabled(true);
                    etMothersName.setEnabled(true);
                    etDate.setEnabled(true);
					clearAllFields();

					sEmptyField = etMothersName.getText().toString();
					typeSearch = "nome";
				}
			}
		});

		removeResultView();

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
					case R.id.et_registro_input:
						fromCNH.setRegister(s.toString());
						break;
					case R.id.et_cnh_input:
						fromCNH.setIdentity(s.toString());
						break;
					case R.id.et_cpf_input:
						fromCNH.setCpf(s.toString());
						break;
					case R.id.et_nome_input:
						fromCNH.setName(s.toString());
						break;
					case R.id.et_mae_input:
						fromCNH.setMothersName(s.toString());
						break;
					case R.id.et_data_input:
						fromCNH.setBirthDate(s.toString());
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_show_result:
			removeResultView();
			enableSearchButton();
			break;
		case R.id.btn_consultar_cnh:
			setQuerySearch();

			if (isCnhSearch()) {
				getCnhResult();
				//Log.d("Latitude", gps.getLocation().getLatitude() + " ");
				//Log.d("Longitude", gps.getLocation().getLongitude() + " ");
			}
			break;
		}
	}

	public boolean isCnhSearch() {

		if (true){

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

	private boolean checkInput() {
		if (rbRegister.isChecked() && etRegister.getText().toString().isEmpty()) {
			etRegister.setError(getResources().getString(
					R.string.dados_da_cnh));
			etIdentity.requestFocus();
			return false;
		} else if (rbIdentity.isChecked() && etIdentity.getText().toString().isEmpty()) {
			etIdentity.setError(getResources().getString(
					R.string.dados_da_cnh));
			etIdentity.requestFocus();
			return false;
		} else if (rbCnhCpf.isChecked()&& etCnhCpf.getText().toString().isEmpty()) {
			etCnhCpf.setError(getResources().getString(
					R.string.cpf_condutor_cnh_ppd));
			etCnhCpf.requestFocus();
			return false;
		} else if (rbCnhName.isChecked() && etName.getText().toString().isEmpty()) {
			etName.setError(getResources().getString(
					R.string.nome_condutor_cnh_ppd));
			etName.requestFocus();
			return false;
		} else if (rbCnhName.isChecked() && etMothersName.getText().toString().isEmpty()) {
			etMothersName.setError(getResources().getString(
					R.string.nome_mae_cnh_ppd));
			etMothersName.requestFocus();
			return false;
		} else if (rbCnhName.isChecked() && etDate.getText().toString().isEmpty()) {
			etDate.setError(getResources().getString(
					R.string.data_condutor_cnh_ppd));
			etDate.requestFocus();
			return false;
		} else {
			return true;
		}
	}

	private boolean isOfflineSearch() {
		return cbOfflineSearch.isChecked();
	}

	private boolean isInternetConnected() {
		return NetworkConnection.isNetworkAvailable(getActivity());
	}

	private void getCnhResult() {

		httpResultAnysTask = new CnhHttpResultAsyncTask(
				new CallBackCnh() {
					@Override
					public void callBack(DataFromCnh fromCnh) {
						resultCallBack(fromCnh);
					}
				}, getActivity(), isOfflineSearch(), setQuerySearch(), null, typeSearch);//gps.getLocation());
		httpResultAnysTask.execute("");
	}

	public void resultCallBack(DataFromCnh fromCNH) {
		//this.fromCNH = null;
		//     gps.stopUsingGPS();

			if (fromCNH != null) {

				this.fromCNH = fromCNH;
				CnhViewFormat cnhViewFormat = new CnhViewFormat(
						fromCNH, getActivity());
				addResultView();

				tvShowResult.setText(cnhViewFormat.getResultViewData(),
						BufferType.SPANNABLE);

				cnhViewFormat.setWarning();
				addResultView();
				disableSearchButton();

			} else {

				tvShowResult.setText(getResources().getString(
						R.string.nehum_resultado_retornado));
				addResultView();
				clearAllFields();

			}

	}

	public DataFromCnh setQuerySearch(){

		if(rbIdentity.isChecked()) {
			fromCNH.getIdentity();
		}else if(rbCnhCpf.isChecked()) {
			fromCNH.getCpf();
		}else if(rbCnhName.isChecked()) {
			fromCNH.getName();
			fromCNH.getMothersName();
			fromCNH.getBirthDate();
		}

		return fromCNH;

	}

	private void removeResultView() {
		if (tvShowResult.getParent() != null)
			llResultParent.removeView(tvShowResult);

	}

	private void addResultView() {
		if (tvShowResult.getParent() == null)
			llResultParent.addView(tvShowResult);

	}

	private void disableSearchButton() {

        llRadio.setVisibility(View.GONE);
        llInput.setVisibility(View.GONE);

		clearAllFields();
	}

	private void enableSearchButton() {
		llRadio.setVisibility(View.VISIBLE);
        llInput.setVisibility(View.VISIBLE);
	}

	private void clearAllFields(){
		etIdentity.setText("");
		etCnhCpf.setText("");
		etName.setText("");
		etMothersName.setText("");
		etDate.setText("");
	}

}
