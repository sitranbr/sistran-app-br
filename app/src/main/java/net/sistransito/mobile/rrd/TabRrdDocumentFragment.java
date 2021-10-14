package net.sistransito.mobile.rrd;

import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.sistransito.library.datepicker.DateListener;
import net.sistransito.library.datepicker.MyDatePicker;
import net.sistransito.library.datepicker.MyTimePicker;
import net.sistransito.library.datepicker.TimeListener;
import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PrepopulatedDBOpenHelper;
import net.sistransito.mobile.database.SetttingDatabaseHelper;
import net.sistransito.mobile.fragment.AnyDialogFragment;
import net.sistransito.mobile.fragment.AnyDialogListener;
import net.sistransito.mobile.number.NumberAnysListerner;
import net.sistransito.mobile.number.NumberHttpResultAnysTask;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabRrdDocumentFragment extends Fragment implements
		AnyDialogListener, OnClickListener, DateListener, TimeListener  {
	private View view;
	private AutoCompleteTextView autoCompleteCity;
    private TextView tvAitNumber, tvRrdTitle;
	private EditText etDriverName,
			etCrlvNumber, etPlate, etState, etRegisterNumber, etRegisterState;
	private Spinner spinnerDocument;
	private RrdData rrdData;
	private List<String> listDocument;
	private AnyArrayAdapter<String> aaaAdapter;
	private AnyDialogFragment anyDialogFragment;
	private Bundle bundle;
	private Button btnDate, btnTime, btnValidity;

	private List<String> cityArray, codeArray, stateArray;
	private ArrayAdapter<String> aaCity;

	private LinearLayout llValidityRegister, llPlateState, llDateAndTime;

	public static TabRrdDocumentFragment newInstance() {
		return new TabRrdDocumentFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.rrd_document_fragment, null, false);
		initializedView();
		getAitObject();
		checkNumeroAit();
		return view;
	}

	private void initializedView() {


		btnDate = (Button) view.findViewById(R.id.btn_rrd_data);
		btnTime = (Button) view.findViewById(R.id.btn_rrd_hora);

		autoCompleteCity = (AutoCompleteTextView) view
				.findViewById(R.id.actv_rrd_municipio);

		tvRrdTitle = (TextView) view.findViewById(R.id.tv_rrd_titulo);
		llPlateState = (LinearLayout) view
				.findViewById(R.id.layout_hide_placa_uf);
		llValidityRegister = (LinearLayout) view
				.findViewById(R.id.layout_hide_registro_validade);

		llDateAndTime = (LinearLayout) view
				.findViewById(R.id.ll_rrd_data_hora);

		tvAitNumber = (TextView) view.findViewById(R.id.tv_rrd_numero_auto);

		etDriverName = (EditText) view
				.findViewById(R.id.et_rrd_nome_do_condutor);

		//etRrdNomeCondutor.setEnabled(false);

		etCrlvNumber = (EditText) view.findViewById(R.id.et_rrd_numero_crlv);
		etPlate = (EditText) view.findViewById(R.id.et_rrd_placa);

		etState = (EditText) view.findViewById(R.id.et_rrd_uf);

		etRegisterNumber = (EditText) view
				.findViewById(R.id.et_rrd_numero_registro);

		etRegisterState = (EditText) view
				.findViewById(R.id.et_rrd_uf_registro);

		btnValidity = (Button) view.findViewById(R.id.btn_rrd_validade);

		listDocument = Arrays.asList(getResources().getStringArray(
				R.array.rrd_document));

		aaaAdapter = new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				listDocument);

		spinnerDocument = (Spinner) view
				.findViewById(R.id.spinner_rrd_documento);
		spinnerDocument.setAdapter(aaaAdapter);

		llPlateState.removeView(llPlateState);

		spinnerDocument
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
											   View view, int pos, long id) {
						rrdData.setDocumentType((String) parent
								.getItemAtPosition(pos));

						if (pos == -1) {
                            hideLayoutPlateView();;
                            hideLayoutRegisterView();
						} else if (pos == 0) {
                            showLayoutPlacaView();
                            hideLayoutRegisterView();
                        } else {
                            showLayoutRegistroView();
                            hideLayoutPlateView();;
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

	}

	private void addListener() {

		etDriverName.addTextChangedListener(new ChangeText(R.id.et_rrd_nome_do_condutor));
		etCrlvNumber.addTextChangedListener(new ChangeText(R.id.et_rrd_numero_crlv));
		etState.addTextChangedListener(new ChangeText(R.id.et_rrd_uf));
		etRegisterNumber.addTextChangedListener(new ChangeText(R.id.et_rrd_numero_registro));
		etRegisterState.addTextChangedListener(new ChangeText(R.id.et_rrd_uf_registro));

		btnDate.addTextChangedListener(new ChangeText(R.id.btn_rrd_data));
		btnTime.addTextChangedListener(new ChangeText(R.id.btn_rrd_hora));

		btnDate.setOnClickListener(this);
		btnTime.setOnClickListener(this);
		btnValidity.setOnClickListener(this);

		hideLayoutRegisterView();

	}

	public void setMunicipioAutoComplete() {

		cityArray = new ArrayList<String>();
		codeArray = new ArrayList<String>();
		stateArray = new ArrayList<String>();
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
			cityArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.CITY_NAME)));
			stateArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.STATE)));
			codeArray.add(myCursor.getString(myCursor.getColumnIndex(PrepopulatedDBOpenHelper.CITY_CODE)));
		} while (myCursor.moveToNext());
		myCursor.close();

		aaCity = new ArrayAdapter<String>(getActivity(), R.layout.custom_autocomplete, R.id.autoCompleteItem, cityArray);
		autoCompleteCity.setAdapter(aaCity);

		autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int pos, long arg3) {

				rrdData.setCityCollected((String) parent.getItemAtPosition(pos));
				int real_position = cityArray.indexOf(parent.getItemAtPosition(pos));

				rrdData.setStateCollected(stateArray.get(real_position));

				Routine.closeKeyboard(autoCompleteCity, getActivity());

			}
		});
	}

	private void checkNumeroAit() {
		String value = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
				.getRrdNumber();
		if (value == null) {
			anyDialogFragment = new AnyDialogFragment();
			anyDialogFragment.setTargetFragment(this, 0);
			bundle = new Bundle();
			bundle.putInt(AppConstants.DIALOG_TITLE_ID, R.string.titulo_tela_sincronizacao);
			bundle.putInt(AppConstants.DIALOG_MGS_ID, R.string.mgs_sincronizacao);
			anyDialogFragment.setArguments(bundle);
			anyDialogFragment.setCancelable(false);
			anyDialogFragment
					.show(getChildFragmentManager(), "dialog");
		} else {
			rrdData.setRrdNumber(value);

		}
	}

	private void getAitObject() {
		rrdData = RrdObject.getRRDOject();
		getRecomandedUpdate();
		addListener();
		initializedSelectetItems();
	}

	private void initializedSelectetItems() {
		// spinnerRrdDocumento.setSelection(0);
		// etRrdNomeCondutor.setText("");
		// etRrdNumeroCrlv.setText("");
		// etRrdUf.setText("");
	}



	private void getRecomandedUpdate() {

		if(rrdData.getRrdType().equals("avulso")) {
			tvAitNumber.setVisibility(View.GONE);
			llDateAndTime.setVisibility(View.VISIBLE);
			autoCompleteCity.setVisibility(View.VISIBLE);
			setMunicipioAutoComplete();

		}else {
			tvAitNumber.setText("Referente ao AIT: " + rrdData.getAitNumber());
			etPlate.setText(rrdData.getPlate());
			etPlate.setEnabled(false);
			etDriverName.setText(rrdData.getDriverName());
			etState.setText(rrdData.getPlateState());
			etRegisterNumber.setText(rrdData.getRegistrationNumber());
			etRegisterState.setText(rrdData.getRegistrationState());
			llDateAndTime.setVisibility(View.GONE);
			autoCompleteCity.setVisibility(View.GONE);
			etState.setEnabled(false);
		}

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
                    case R.id.et_rrd_nome_do_condutor:
                        rrdData.setDriverName(s);
                        break;
                    case R.id.et_rrd_numero_crlv:
                        rrdData.setCrlvNumber(s);
                        break;
                    case R.id.et_rrd_uf:
                        rrdData.setPlateState(s);
                        break;
                    case R.id.et_rrd_numero_registro:
                        rrdData.setRegistrationNumber(s);
                        break;
                    case R.id.et_rrd_uf_registro:
                        rrdData.setRegistrationState(s);
                        break;
                    case R.id.btn_rrd_data:
                        rrdData.setDateCollected(s);
                        break;
                    case R.id.btn_rrd_hora:
                        rrdData.setTimeCollected(s);
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
						checkNumeroAit();
					}

				}
			}, getActivity(), AppConstants.RRD_NUMBER)).execute("");

		} else {
			getActivity().finish();
		}

	}

	private void showLayoutPlacaView() {

		//if (layoutPlacaUf.getParent() == null) {
			//layoutPlacaUf.addView(layoutPlacaUf);
			llPlateState.setVisibility(View.VISIBLE);
		//}

		hideLayoutRegisterView();

	}

	private void showLayoutRegistroView() {

	    llValidityRegister.setVisibility(View.VISIBLE);
        btnValidity.setVisibility(View.VISIBLE);
	    btnValidity.setText(R.string.rrd_validade);
		hideLayoutPlateView();

	}

	private void hideLayoutPlateView() {
	    etCrlvNumber.setText("");
	    llPlateState.setVisibility(View.GONE);
	}

	private void hideLayoutRegisterView() {
        llValidityRegister.setVisibility(View.GONE);
        btnValidity.setVisibility(View.GONE);
        btnValidity.setText(R.string.rrd_validade);
        rrdData.setValidity("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_rrd_validade:
				MyDatePicker datePicker = new MyDatePicker();
				bundle = new Bundle();
				bundle.putInt(MyDatePicker.MY_DATE_PICKER_ID, R.id.btn_rrd_validade);
				datePicker.setArguments(bundle);
				datePicker.setTargetFragment(this, 0);
				datePicker.show(getActivity().getSupportFragmentManager(), "date");
				break;
			case R.id.btn_rrd_data:
				MyDatePicker rrdDate = new MyDatePicker();
				bundle = new Bundle();
				bundle.putInt(rrdDate.MY_DATE_PICKER_ID,
						R.id.btn_rrd_data);
				rrdDate.setArguments(bundle);
				rrdDate.setTargetFragment(this, 0);
				rrdDate.show(getActivity().getSupportFragmentManager(), "date2");
				break;

			case R.id.btn_rrd_hora:
				MyTimePicker picker = new MyTimePicker();
				bundle = new Bundle();
				bundle.putInt(MyTimePicker.MY_TIME_PICKER_ID,
						R.id.btn_rrd_hora);
				picker.setArguments(bundle);
				picker.setTargetFragment(this, 0);
				picker.show(getActivity().getSupportFragmentManager(), "time");
				break;
		}
	}

	@Override
	public void date(String date, int view_id) {
		if (view_id == R.id.btn_rrd_validade) {
			btnValidity.setText(date);
			rrdData.setValidity(date);
		}
		if (view_id == R.id.btn_rrd_data) {
			btnDate.setText(date);
			rrdData.setDateCollected(date);
		}
	}

	@Override
	public void time(String time, int view_id) {
		if (view_id == R.id.btn_rrd_hora) {
			btnTime.setText(time);
			rrdData.setTimeCollected(time);
		}
	}

}