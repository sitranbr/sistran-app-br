package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.sistransito.mobile.tav.TavConductorFragment;
import net.sistransito.mobile.tav.TavGenerateFragment;
import net.sistransito.mobile.tav.TavVehicleFragment;

public class TavStartSectionsPagerAdapter extends FragmentPagerAdapter {

    public TavStartSectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return TavConductorFragment.newInstance();
            case 1:
                return TavVehicleFragment.newInstance();
            case 2:
                return TavGenerateFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}