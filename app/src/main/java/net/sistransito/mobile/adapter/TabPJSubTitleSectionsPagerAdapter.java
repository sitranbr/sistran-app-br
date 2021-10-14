package net.sistransito.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import net.sistransito.mobile.aitcompany.TabPJCondutorSubTituloFragment;
import net.sistransito.mobile.aitcompany.TabPJEnderecoSubTituloFragment;
import net.sistransito.mobile.aitcompany.TabPJInfracaoSubTituloFragment;

public class TabPJSubTitleSectionsPagerAdapter extends FragmentPagerAdapter

    {

        @Override
        public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

        @Override
        public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

	public TabPJSubTitleSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

        @Override
        public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return TabPJCondutorSubTituloFragment.newInstance();
            case 1:
                return TabPJEnderecoSubTituloFragment.newInstance();
            case 2:
                return TabPJInfracaoSubTituloFragment.newInstance();
        }
        return null;
    }

        @Override
        public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
