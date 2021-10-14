package net.sistransito.mobile.plate.lister;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.R;

public class LogListFragment extends Fragment implements OnClickListener {
    private View view;
    private Button btnListPlate, btnListAit, btnListRrd,
            btnListTca, btnListTav;

    public static LogListFragment newInstance() {
        return new LogListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.historic_list_fragment, null, false);
        initializedView();
        return view;
    }

    private void initializedView() {
        btnListPlate = (Button) view.findViewById(R.id.btn_listar_placas);
        btnListPlate.setOnClickListener(this);
        btnListAit = (Button) view.findViewById(R.id.btn_listar_autos);
        btnListAit.setOnClickListener(this);
        btnListRrd = (Button) view.findViewById(R.id.btn_listar_rrd);
        btnListRrd.setOnClickListener(this);
        btnListTca = (Button) view.findViewById(R.id.btn_listar_tca);
        btnListTca.setOnClickListener(this);
        btnListTav = (Button) view.findViewById(R.id.btn_listar_tav);
        btnListTav.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listar_placas:
                startActivity(AppObject.getPlateListIntent(getActivity()));
                break;
            case R.id.btn_listar_autos:
                startActivity(AppObject.getAitListerIntent(getActivity()));
                break;

            case R.id.btn_listar_rrd:
                startActivity(AppObject.getRrdListerIntent(getActivity()));
                break;

            case R.id.btn_listar_tca:
                startActivity(AppObject.getTcaListerIntent(getActivity()));
                break;
            case R.id.btn_listar_tav:
                startActivity(AppObject.getTavListerIntent(getActivity()));
                break;
        }
    }
}
