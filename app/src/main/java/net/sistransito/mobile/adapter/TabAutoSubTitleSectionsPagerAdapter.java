package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import net.sistransito.mobile.ait.TabConductorSubTitleFragment;
import net.sistransito.mobile.ait.TabAddressSubTitleFragment;
import net.sistransito.mobile.ait.TabGenerationSubTitleFragment;
import net.sistransito.mobile.ait.TabInfrationSubTitleFragment;
import net.sistransito.mobile.ait.TabVehicleSubTitleFragment;

public class TabAutoSubTitleSectionsPagerAdapter extends FragmentPagerAdapter {

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		super.startUpdate(container);
	}

	public TabAutoSubTitleSectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
  		case 0:
  			return TabVehicleSubTitleFragment.newInstance();
  		case 1:
  			return TabConductorSubTitleFragment.newInstance();
  		case 2:
  			return TabAddressSubTitleFragment.newInstance();
  		case 3:
  			return TabInfrationSubTitleFragment.newInstance();
  		case 4:
  			return TabGenerationSubTitleFragment.newInstance();
  		}
  		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}
}