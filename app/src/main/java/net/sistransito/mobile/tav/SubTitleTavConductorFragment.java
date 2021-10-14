package net.sistransito.mobile.tav;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;


public class SubTitleTavConductorFragment extends Fragment {
	private View view;

	public static SubTitleTavConductorFragment newInstance() {
		return new SubTitleTavConductorFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tav_conductor_fragment_subtitulo, null, false);
		return view;
	}

}
