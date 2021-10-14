package net.sistransito.library.datepicker;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class MyDatePicker extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	DateListener dateTimeListener;
	private int view_id;
	public static final String MY_DATE_PICKER_ID = "my_date_picker_id";

	@Override
	public void onCancel(DialogInterface dialog) {
		dateTimeListener.date(null, view_id);
		super.onCancel(dialog);
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		dateTimeListener = (DateListener) getTargetFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		view_id = getArguments().getInt(MY_DATE_PICKER_ID);
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		int mes = month + 1;
		String dia = ""; String meses = "";
		if (day < 10) { dia = "0" + day;
		} else { dia = String.valueOf(day); }
		if (mes < 10) { meses = "0" + mes;
		} else { meses = String.valueOf(mes); }
		dateTimeListener.date(dia + "/" + meses + "/" + year, view_id);
	}
}
