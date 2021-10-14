package net.sistransito.mobile.tca;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;


public class SubTitleTcaConductorFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tca_condutor_fragment_subtitulo, null, false);
		return view;
	}
	public static SubTitleTcaConductorFragment newInstance() {
		return new SubTitleTcaConductorFragment();
	}

}
