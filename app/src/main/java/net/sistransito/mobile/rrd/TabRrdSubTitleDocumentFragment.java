package net.sistransito.mobile.rrd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;

public class TabRrdSubTitleDocumentFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.rrd_document_fragment_subtitle, null, false);
		return view;
	}

	public static TabRrdSubTitleDocumentFragment newInstance() {
		return new TabRrdSubTitleDocumentFragment();
	}

}
