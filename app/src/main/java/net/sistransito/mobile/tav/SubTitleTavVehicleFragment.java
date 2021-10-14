package net.sistransito.mobile.tav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;

public class SubTitleTavVehicleFragment extends Fragment {
	private View view;

	public static SubTitleTavVehicleFragment newInstance() {
		return new SubTitleTavVehicleFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tav_vehicle_fragment_subtitle, null,
				false);
		return view;
	}

}
