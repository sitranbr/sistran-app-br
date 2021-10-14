package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.sistransito.mobile.aitcompany.TabPJEnderecoFragment;
import net.sistransito.mobile.aitcompany.TabPJInfracaoFragment;
import net.sistransito.mobile.aitcompany.TabPJCondutorFragment;

public class TabPJStartSectionsPagerAdapter extends FragmentPagerAdapter {

    public TabPJStartSectionsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return TabPJCondutorFragment.newInstance();
            case 1:
                return TabPJEnderecoFragment.newInstance();
            case 2:
                return TabPJInfracaoFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}