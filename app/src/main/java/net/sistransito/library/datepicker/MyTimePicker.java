package net.sistransito.library.datepicker;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

public class MyTimePicker extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {
	TimeListener dateTimeListener;
	private int view_id;
	public static final String MY_TIME_PICKER_ID = "my_time_picker_id";

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		dateTimeListener = (TimeListener) getTargetFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		view_id = getArguments().getInt(MY_TIME_PICKER_ID);
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		String minutos = ""; String horas = "";
		if (minute < 10) { minutos = "0" + minute;
		} else { minutos = String.valueOf(minute); }
		if (hourOfDay < 10) { horas = "0" + hourOfDay;
		} else { horas = String.valueOf(hourOfDay); }

		dateTimeListener.time(horas + ":" + minutos, view_id);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		dateTimeListener.time(null, view_id);
	}

}
