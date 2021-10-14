package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.sistransito.mobile.ait.TabAitAddressFragment;
import net.sistransito.mobile.ait.TabAitInfrationFragment;
import net.sistransito.mobile.ait.TabAitConductorFragment;
import net.sistransito.mobile.ait.TabAitGenerateFragment;
import net.sistransito.mobile.ait.TabAitVehicleFragment;

public class TabAutoStartSectionsPagerAdapter extends FragmentPagerAdapter {

	public TabAutoStartSectionsPagerAdapter(FragmentManager fm) {
		super(fm);

	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return TabAitVehicleFragment.newInstance();
		case 1:
			return TabAitConductorFragment.newInstance();
		case 2:
			return TabAitAddressFragment.newInstance();
		case 3:
			return TabAitInfrationFragment.newInstance();
		case 4:
			return TabAitGenerateFragment.newInstance();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}