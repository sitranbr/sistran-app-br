package net.sistransito.mobile.ait;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;


public class TabVehicleSubTitleFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.ait_vehicle_fragment_subtitle, null, false);
		return view;
	}
	public static TabVehicleSubTitleFragment newInstance() {
		return new TabVehicleSubTitleFragment();
	}

}
