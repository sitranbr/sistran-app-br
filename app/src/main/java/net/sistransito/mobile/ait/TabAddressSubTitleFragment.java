package net.sistransito.mobile.ait;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;


public class TabAddressSubTitleFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tab_address_sub_title_fragment, null, false);
		return view;
	}
	public static TabAddressSubTitleFragment newInstance() {
		return new TabAddressSubTitleFragment();
	}

}
