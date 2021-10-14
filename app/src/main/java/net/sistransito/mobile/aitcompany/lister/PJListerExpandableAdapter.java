package net.sistransito.mobile.aitcompany.lister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.sistransito.mobile.aitcompany.AitCompanyActivity;
import net.sistransito.mobile.aitcompany.pjData;
import net.sistransito.mobile.database.AitPJDatabaseHelper;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.bluetoothprint.bluetooth.BluetoothPrinterListerner;
import net.sistransito.R;

public class PJListerExpandableAdapter extends CursorTreeAdapter {
    private LayoutInflater mInflator;
    private Context mycontext;
    private BluetoothPrinterListerner printListener;
    private String cpfCnpj;

    public PJListerExpandableAdapter(Cursor cursor, Context context) {
        super(cursor, context);
        mycontext = context;
        mInflator = LayoutInflater.from(context);
        printListener = (BluetoothPrinterListerner) context;
    }

    @Override
    protected void bindChildView(View arg0, Context arg1, Cursor arg2,
                                 boolean arg3) {
    }

    @Override
    protected void bindGroupView(View view, Context arg1, Cursor cursor,
                                 boolean arg3) {
        TextView logInfrator, logCpfCnpj, logArt;

        logInfrator = (TextView) view.findViewById(R.id.logpj_infrator);
        logCpfCnpj = (TextView) view.findViewById(R.id.logpj_cpf_cnpj);
        logArt = (TextView) view.findViewById(R.id.log_article);

        logInfrator.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.COMPANY_SOCIAL)));
        logCpfCnpj.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.CPF)));
        logArt.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.ARTICLE)));

        cpfCnpj = cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.CPF));

    }

    @Override
    protected View newChildView(Context arg0, Cursor arg1, boolean arg2,
                                ViewGroup arg3) {
        return null;
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor,
                                boolean isExpendable, ViewGroup parent) {
        View view = mInflator.inflate(R.layout.autopj_lista_listview_parent,
                null);
        TextView logInfrator, logCpfCnpj, logArt, logAit;

        logInfrator = (TextView) view.findViewById(R.id.logpj_infrator);
        logCpfCnpj = (TextView) view.findViewById(R.id.logpj_cpf_cnpj);
        logArt = (TextView) view.findViewById(R.id.logpj_art);
        logAit = (TextView) view.findViewById(R.id.logpj_ait);

        logInfrator.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.COMPANY_SOCIAL)));
        logCpfCnpj.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.CPF)));
        logArt.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.ARTICLE)));
        logAit.setText(cursor.getString(cursor
                .getColumnIndex(AitPJDatabaseHelper.AIT_NUMBER)));
        return view;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        int groupId = groupCursor.getInt(groupCursor
                .getColumnIndex(AitPJDatabaseHelper.COLUMN_ID));
        return (DatabaseCreator.getAitPJDatabaseAdapter(mycontext))
                .getAutoCursorFromID(groupId);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
        // final int gPosition = groupPosition;
        TextView tvAutosChildview;
        FrameLayout fragmentContainerAuto;
        Button btnPrintAuto, btnNewAuto;

        View mView = null;
        if (view != null) {
            mView = view;
        } else {
            mView = mInflator.inflate(R.layout.autopj_lista_listview_child,
                    parent, false);
        }

        fragmentContainerAuto = (FrameLayout) mView.findViewById(R.id.fragment_pj_container_list);

        tvAutosChildview = (TextView) mView.findViewById(R.id.tv_open_ait_data_childview);

        final pjData dadosAuto = new pjData();

        dadosAuto.setAutoDataFromCursor(getGroup(groupPosition));

		/*fragmentContainerAuto.setVisibility(view.VISIBLE);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.fragment_container_list, AutoPreviewFragment.newInstance()).commit();*/

        tvAutosChildview.setText(dadosAuto.getAutoListViewData(mycontext));

        btnPrintAuto = (Button) mView.findViewById(R.id.btpj_print_auto);
        btnNewAuto = (Button) mView.findViewById(R.id.btpj_new_auto);

        btnNewAuto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

				/*openDadosAuto((DatabaseCreator
						.getDatabaseAdapterAutoInfracao(mycontext))
						.getDataAutoFromPlate(cpfCnpj));*/

                dialogNewAuto("Deseja criar outro AIT para este CPF/CNPJ " + cpfCnpj + " ?", mycontext);

            }

        });

        btnPrintAuto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialogViaPrint(dadosAuto, mycontext);
            }

        });

        return mView;
    }

    public void dialogNewAuto(String mgs, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,
                AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Novo AIT");
        builder.setMessage(mgs);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openDadosAuto((DatabaseCreator
                        .getAitPJDatabaseAdapter(mycontext))
                        .getPJDataFromPJ(cpfCnpj));
            }
        });
        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void dialogViaPrint(final pjData dados, Context context){
        LayoutInflater linear = LayoutInflater.from(context);
        View view = linear.inflate(R.layout.print_layout_option, null);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mycontext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setView(view);

        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        Button btnViaAgente, btnViaCondutor;

        btnViaAgente = (Button) view.findViewById(R.id.btn_via_agente);
        btnViaCondutor = (Button) view.findViewById(R.id.btn_via_condutor);

        btnViaAgente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printListener.print(dados, null, "AGENTE");
                dialog.dismiss();
            }
        });

        btnViaCondutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printListener.print(dados, null, "CONDUTOR");
                dialog.dismiss();
            }
        });

    }

    private void openDadosAuto(pjData dadosAuto) {
        Intent intent = new Intent(mycontext, AitCompanyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(pjData.getIDAuto(), dadosAuto);
        intent.putExtras(bundle);
        mycontext.startActivity(intent);
        ((Activity) mycontext).finish();
    }

}
