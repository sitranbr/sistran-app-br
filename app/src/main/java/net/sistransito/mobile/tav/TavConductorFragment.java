package net.sistransito.mobile.tav;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyDialogFragment;
import net.sistransito.mobile.fragment.AnyDialogListener;
import net.sistransito.mobile.number.NumberAnysListerner;
import net.sistransito.mobile.number.NumberHttpResultAnysTask;
import net.sistransito.R;

public class TavConductorFragment extends Fragment implements
		AnyDialogListener {
	private View view;

	private TavData data;
	private TextView etAitNumber;
	private EditText etOwnerName, etCpfCnpj,
			etRenavamNumber, etChassiNumber;
	private Bundle bundle;
	private AnyDialogFragment diaglogFragmentForFragment;

	public static TavConductorFragment newInstance() {
		return new TavConductorFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tav_driver_fragment, null, false);
		initializedView();
		getAitObject();
		checkAitNumber();
		return view;
	}

	private void checkAitNumber() {
		String value = (DatabaseCreator.getNumberDatabaseAdapter(getActivity()))
				.geTavNumber();
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
			data.setTavNumber(value);

		}
	}

	private void addListener() {
		etAitNumber.addTextChangedListener(new ChangeText(
				R.id.et_tav_ait));
		etOwnerName.addTextChangedListener(new ChangeText(
				R.id.et_tav_proprietario));

		etCpfCnpj.addTextChangedListener(new ChangeText(R.id.et_tav_cpf_cnpj));

		etRenavamNumber.addTextChangedListener(new ChangeText(
				R.id.et_numero_tav_renavam));

		etChassiNumber.addTextChangedListener(new ChangeText(
				R.id.et_numero_tav_chassi));

	}

	private void getAitObject() {
		data = TavObject.getTAVObject();
		getRecomandedUpdate();

	}

	private void getRecomandedUpdate() {
      etAitNumber.setText(data.getAitNumber());
	}

	private void initializedView() {
    
		etAitNumber = (TextView) view
				.findViewById(R.id.et_tav_ait);
		etOwnerName = (EditText) view
				.findViewById(R.id.et_tav_proprietario);

		etCpfCnpj = (EditText) view.findViewById(R.id.et_tav_cpf_cnpj);

		etRenavamNumber = (EditText) view
				.findViewById(R.id.et_numero_tav_renavam);

		etChassiNumber = (EditText) view
				.findViewById(R.id.et_numero_tav_chassi);

		addListener();

	}

	private class ChangeText implements TextWatcher {
		private int id;

		public ChangeText(int id) {
			this.id = id;
		}

		@Override
		public void afterTextChanged(Editable editable) {
			String s = editable.toString();
			switch (id) {
			case R.id.et_tav_ait:
				data.setAitNumber(s);
				break;
			case R.id.et_tav_cpf_cnpj:
				data.setCpfCnpj(s);
				break;

			case R.id.et_numero_tav_renavam:
				data.setRenavamNumber(s);
				break;

			case R.id.et_numero_tav_chassi:
				data.setChassisNumber(s);
				break;

			case R.id.et_tav_proprietario:
				data.setOwnerName(s);
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
			}, getActivity(), AppConstants.TAV_NUMBER)).execute("");

		} else {
			getActivity().finish();
		}
	}
}