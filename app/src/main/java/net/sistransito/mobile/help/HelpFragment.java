package net.sistransito.mobile.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import net.sistransito.R;

public class HelpFragment extends Fragment {
	private ImageButton im_btn_back;
	private TextView tv_title;
	private View view;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.help_fragment, null,false);
		return  view;
	}
	public static HelpFragment newInstance() {
		return new HelpFragment();
	}


	private void actionbarSetup() {
		tv_title = (TextView)view.findViewById(R.id.tv_title);
		tv_title.setText(getResources().getString(R.string.ajuda_sobre_app));
		im_btn_back = (ImageButton)view. findViewById(R.id.im_btn_back);

	}

}
