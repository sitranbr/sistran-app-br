package net.sistransito.mobile.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class SectionsPagerAdapterLogin extends FragmentPagerAdapter {

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public void startUpdate(ViewGroup container) {
		super.startUpdate(container);
	}

	public SectionsPagerAdapterLogin(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new LoginFragment();
		case 1:
			return new FragmentLoginRegister();
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return null;
	}

}