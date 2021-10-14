package net.sistransito.mobile.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import net.sistransito.R;

public class ProfileFragment extends Fragment {
	private ImageButton imBtnBack;
	private TextView tvTitle;
	private View view;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =inflater.inflate(R.layout.profile, null,false);
		return  view;
	}

	public static ProfileFragment newInstance() {
		return new ProfileFragment();
	}

	private void actionbarSetup() {
		tvTitle = (TextView)view.findViewById(R.id.tv_title);
		tvTitle.setText(getResources().getString(R.string.profile));
		imBtnBack = (ImageButton)view. findViewById(R.id.im_btn_back);

	}

}
