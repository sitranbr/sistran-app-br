package net.sistransito.mobile.rrd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.rrd.lister.RrdLister;
import net.sistransito.R;

import java.util.Arrays;
import java.util.List;

public class TabRrdInformationFragment extends Fragment {
	private View view;
	private EditText etReasonForCollecting;
	private Spinner spinnerDays;
	private RrdData rrdData;
	private List<String> listQuantity;
	private AnyArrayAdapter<String> aaaQuantity;

	private Button btnRrdCheck, btnRrdPrint;

	public static TabRrdInformationFragment newInstance() {
		return new TabRrdInformationFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.rrd_information_fragment, null, false);
		initializedView();
		getAitObject();
		return view;
	}

	private void addListener() {

		Log.d("tipoRRD", rrdData.getRrdType());

	}

	private void getAitObject() {
		rrdData = RrdObject.getRRDOject();
		getRecomandedUpdate();
		addListener();
		initializedSelectetItems();
	}

	private void initializedSelectetItems() {
		etReasonForCollecting.setText("");
		spinnerDays.setSelection(4);
	}

	private void initializedView() {

		btnRrdCheck = (Button) view.findViewById(R.id.btn_rrd_conferir);
		btnRrdPrint = (Button) view.findViewById(R.id.btn_rrd_imprimir);

		etReasonForCollecting = (EditText) view
				.findViewById(R.id.et_rrd_motivo_recolhimento);
		etReasonForCollecting.addTextChangedListener(new ChangeText(
				R.id.et_rrd_motivo_recolhimento));

		listQuantity = Arrays.asList(getResources().getStringArray(
				R.array.array_quantity));
		aaaQuantity = new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				listQuantity);

		spinnerDays = (Spinner) view
				.findViewById(R.id.spinner_rrd_dias_regularizacao);
		spinnerDays.setAdapter(aaaQuantity);

		spinnerDays
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						rrdData.setDaysForRegularization((String) parent
								.getItemAtPosition(pos));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});




		btnRrdPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				(DatabaseCreator.getRrdDatabaseAdapter(getActivity()))
						.insertRrdInformation(rrdData);
				
				startActivity(new Intent(getActivity(), RrdLister.class));
				getActivity().finish();
			}
		});

		btnRrdCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AnyAlertDialog.dialogShow(rrdData.getRRDListViewData(getActivity()),
						getActivity(), getResources()
								.getString(R.string.listar_rrd));
			}
		});

	}

	private void getRecomandedUpdate() {

	}

	private class ChangeText implements TextWatcher {
		private int id;

		public ChangeText(int id) {
			this.id = id;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (id == R.id.et_rrd_motivo_recolhimento) {
				rrdData.setReasonCollected((s.toString()).trim());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

	}
}