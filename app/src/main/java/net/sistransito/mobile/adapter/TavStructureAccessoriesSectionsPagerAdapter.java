package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.sistransito.mobile.tav.TavAccessoriesFragment;
import net.sistransito.mobile.tav.TavStructureFragment;

public class TavStructureAccessoriesSectionsPagerAdapter extends
		FragmentPagerAdapter {

	public TavStructureAccessoriesSectionsPagerAdapter(FragmentManager fm) {
		super(fm);

	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new TavStructureFragment();
		case 1:
			return new TavAccessoriesFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}