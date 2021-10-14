package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import net.sistransito.mobile.tav.SubTitleTavConductorFragment;
import net.sistransito.mobile.tav.SubTitleTavGenerateFragment;
import net.sistransito.mobile.tav.SubTitleTavVehicleFragment;

public class TavSubTitleSectionsPagerAdapter extends FragmentPagerAdapter {

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		super.startUpdate(container);
	}

	public TavSubTitleSectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {

		case 0:
			return  SubTitleTavConductorFragment.newInstance();
		case 1:
			return  SubTitleTavVehicleFragment.newInstance();
		case 2:
			return  SubTitleTavGenerateFragment.newInstance();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}
}