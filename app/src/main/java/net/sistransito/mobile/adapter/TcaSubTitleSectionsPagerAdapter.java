package net.sistransito.mobile.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import net.sistransito.mobile.tca.SubTitleTcaConductorFragment;
import net.sistransito.mobile.tca.SubTitleTcaQuizFragment;

public class TcaSubTitleSectionsPagerAdapter extends FragmentPagerAdapter {

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		super.startUpdate(container);
	}

	public TcaSubTitleSectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {

		case 0:
			return SubTitleTcaConductorFragment.newInstance();
		case 1:
			return SubTitleTcaQuizFragment.newInstance();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}