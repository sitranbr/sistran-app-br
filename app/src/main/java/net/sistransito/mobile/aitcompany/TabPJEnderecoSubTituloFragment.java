package net.sistransito.mobile.aitcompany;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sistransito.R;

public class TabPJEnderecoSubTituloFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autopj_endereco_fragment_subtitulo, null, false);
        return view;
    }
    public static TabPJEnderecoSubTituloFragment newInstance() {
        return new TabPJEnderecoSubTituloFragment();
    }

}
