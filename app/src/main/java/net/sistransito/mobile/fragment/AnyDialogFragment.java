package net.sistransito.mobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.R;

public class AnyDialogFragment extends DialogFragment {
	private int mgsId;
	private int titleId;
	private AnyDialogListener dialogListener;
	private boolean isNetConnected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isNetConnected = NetworkConnection.isNetworkAvailable(getActivity());
		dialogListener = (AnyDialogListener) getTargetFragment();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		dialogListener.onDialogTaskWork(false);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String mgs = "";
		mgsId = getArguments().getInt(AppConstants.DIALOG_MGS_ID);
		titleId = getArguments().getInt(AppConstants.DIALOG_TITLE_ID);

		if (!isNetConnected) {
			mgs = getResources().getString(mgsId) + AppConstants.NEW_LINE
					+ AppConstants.NEW_LINE
					+ getResources().getString(R.string.sem_conexao);
		} else {
			mgs = getResources().getString(mgsId);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(titleId);
		builder.setMessage(mgs);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialogInterface, int arg1) {
				dialogListener.onDialogTaskWork(true && isNetConnected);
				dialogInterface.dismiss();

			}
		});
		builder.setNegativeButton(android.R.string.cancel,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialogInterface,
							int arg1) {
						dialogListener.onDialogTaskWork(false);
						dialogInterface.dismiss();
					}
				});
		return builder.create();
	}

	@Override
	public View getView() {
		return super.getView();
	}
}
