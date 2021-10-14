package net.sistransito.mobile.setting;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;

import com.rey.material.widget.CheckBox;

import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.SettingDatabaseAdapter;
import net.sistransito.mobile.database.SetttingDatabaseHelper;
import net.sistransito.mobile.database.sync.SyncDataInformation;
import net.sistransito.mobile.network.NetworkConnection;
import net.sistransito.mobile.util.Routine;
import net.sistransito.R;

public class SettingFragment extends Fragment implements
        OnCheckedChangeListener, OnClickListener {

    private ImageButton im_btn_back;
    private CheckBox checkBoxVibrar, checkBoxRington, checkBoxAutoBackup;
    private SettingDatabaseAdapter database;
    private TextView tvTitle;
    private EditText etSettingPrinter, etSettingState;
    private Button btnSync, btnUpdate;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_main, null, false);
        database = DatabaseCreator.getSettingDatabaseAdapter(getActivity());
        initializedView();
        return view;
    }
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    private void initializedView() {
        etSettingState = (EditText) view.findViewById(R.id.et_setting_uf);
        etSettingPrinter = (EditText) view.findViewById(R.id.et_setting_printer);
        checkBoxAutoBackup = (CheckBox) view.findViewById(R.id.checkBoxAutoBackup);
        checkBoxRington = (CheckBox) view.findViewById(R.id.checkBoxRington);
        checkBoxVibrar = (CheckBox) view.findViewById(R.id.checkBoxVibarte);
        btnSync = (Button) view.findViewById(R.id.btn_sync);
        btnUpdate = (Button) view.findViewById(R.id.btn_update);


        Cursor cursor = (DatabaseCreator
                .getSettingDatabaseAdapter(getActivity()))
                .getSettingCursor();

        etSettingState.setText(cursor.getString(cursor
                .getColumnIndex(SetttingDatabaseHelper.SETTING_UF)));
        etSettingPrinter.setText(cursor.getString(cursor
                .getColumnIndex(SetttingDatabaseHelper.SETTING_PRINTER)));

        btnSync.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        if (database.getAutobackup()) {
            checkBoxAutoBackup.setChecked(true);
        } else {
            checkBoxAutoBackup.setChecked(false);
        }
        if (database.getRingtone()) {
            checkBoxRington.setChecked(true);
        } else {
            checkBoxRington.setChecked(false);
        }
        if (database.getVibrator()) {
            checkBoxVibrar.setChecked(true);
            Log.d("Vibrar", database.getVibrator() + " ");

        } else {
            checkBoxVibrar.setChecked(false);
            Log.d("Não vibrar", database.getVibrator() + " ");
        }
        checkBoxVibrar.setOnCheckedChangeListener(this);
        checkBoxRington.setOnCheckedChangeListener(this);
        checkBoxAutoBackup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.checkBoxAutoBackup:
                if (isChecked) {
                    database.setAutobackup(true);
                } else {
                    database.setAutobackup(false);
                }
                break;
            case R.id.checkBoxRington:
                if (isChecked) {
                    database.setRingtone(true);
                } else {
                    database.setRingtone(false);
                }
                break;
            case R.id.checkBoxVibarte:
                Log.d("vibrar", database.getVibrator() + "");
                if (isChecked) {
                    database.setVibrator(true);
                } else {
                    database.setVibrator(false);
                }
                Log.d("não vibra", database.getVibrator() + "");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                etSettingState.setText(etSettingState.getText().toString());
                etSettingPrinter.setText(etSettingPrinter.getText().toString());
                if (database.setUpdatePrinterUf(etSettingState.getText().toString(),
                        etSettingPrinter.getText().toString())) {
                    Routine.showAlert(getResources().getString(R.string.update_sucess), getActivity());
                } else {
                    Routine.showAlert(getResources().getString(R.string.update_erro), getActivity());
                }
                break;
            case R.id.btn_sync:
                if (NetworkConnection.isNetworkAvailable(getActivity())) {
                    SyncDataInformation information = new SyncDataInformation(getActivity());
                    information.execute("");
                } else {
                    Routine.showAlert(getResources().getString(R.string.sem_conexao), getActivity());
                }

                break;

        }

    }

}
