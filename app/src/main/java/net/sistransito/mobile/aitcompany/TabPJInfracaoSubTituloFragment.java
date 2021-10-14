package net.sistransito.mobile.aitcompany;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;

public class TabPJInfracaoSubTituloFragment extends Fragment {
    private View view;

    public static TabPJInfracaoSubTituloFragment newInstance() {
        return new TabPJInfracaoSubTituloFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autopj_infracao_fragment_subtitulo, null, false);
        return view;
    }

}
