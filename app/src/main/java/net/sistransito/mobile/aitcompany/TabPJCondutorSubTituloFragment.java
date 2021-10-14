package net.sistransito.mobile.aitcompany;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;

public class TabPJCondutorSubTituloFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ait_driver_fragment_subtitle, null, false);
        return view;
    }
    public static TabPJCondutorSubTituloFragment newInstance() {
        return new TabPJCondutorSubTituloFragment();
    }

}
