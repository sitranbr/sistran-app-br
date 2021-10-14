package net.sistransito.mobile.ait.lister;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.AitDatabaseHelper;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.bluetoothprint.bluetooth.BluetoothPrinterListerner;
import net.sistransito.mobile.rrd.RrdActivity;
import net.sistransito.mobile.rrd.RrdData;
import net.sistransito.mobile.tav.TavActivity;
import net.sistransito.mobile.tav.TavData;
import net.sistransito.mobile.tca.TcaActivity;
import net.sistransito.mobile.tca.TcaData;
import net.sistransito.R;

public class AutoListerExpandableAdapter extends CursorTreeAdapter {
	private final LayoutInflater mInflator;
	private final Context mycontext;
	private final BluetoothPrinterListerner printListener;
	private String vehiclePlate;

	public AutoListerExpandableAdapter(Cursor cursor, Context context) {
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
		TextView logPlate, logModel, logArticle, logAit;

		logPlate = view.findViewById(R.id.log_plate);
		logModel = view.findViewById(R.id.log_model);
		logArticle = view.findViewById(R.id.log_article);
		logAit = view.findViewById(R.id.log_ait);

		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.PLATE)));
		logModel.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.VEHICLE_MODEL)));
		logArticle.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.ARTICLE)));
		logAit.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.AIT_NUMBER)));

		vehiclePlate = cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.PLATE));

		/*TimeAndIme dozeHoras = new TimeAndIme(mycontext);

		Log.d("Hora depois das doze: ", String.valueOf(dozeHoras.addHalfDay()));

		String horaInicio = "02:10:30";
		String horaFim = "10:10:30";

		Log.d("Hora subtraida: ", String.valueOf(dozeHoras.subtraiHoras(horaInicio, horaFim)));

		Coordenadas location = new Coordenadas();

		Log.d("Localização: ", String.valueOf(location));*/

	}

	@Override
	protected View newChildView(Context arg0, Cursor arg1, boolean arg2,
			ViewGroup arg3) {
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpendable, ViewGroup parent) {
		View view = mInflator.inflate(R.layout.ait_listview_parent,
				null);
		TextView logPlate, logModel, logArt, logAit;
		ImageView imgSinc;

		logPlate = view.findViewById(R.id.log_plate);
		logModel = view.findViewById(R.id.log_model);
		logArt = view.findViewById(R.id.log_article);
		logAit = view.findViewById(R.id.log_ait);
		imgSinc = view.findViewById(R.id.img_sinc);

		String sync = (cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.SYNC_STATUS)));

		String plateCar = (cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.PLATE)));

		//DatabaseCreator.getDatabaseAdapterAit(context).autoUpdateSyncStatus("TL00006683");

		try {
			if(sync.equals("1")) {
				imgSinc.setImageDrawable(context.getDrawable(R.drawable.sync_icon));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.PLATE)));
		logModel.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.VEHICLE_MODEL)));
		logArt.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.ARTICLE)));
		logAit.setText(cursor.getString(cursor
				.getColumnIndex(AitDatabaseHelper.AIT_NUMBER)));

		return view;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		int groupId = groupCursor.getInt(groupCursor
				.getColumnIndex(AitDatabaseHelper.COLUMN_ID));
		return (DatabaseCreator.getAitDatabaseAdapter(mycontext))
				.getAitCursorFromID(groupId);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		// final int gPosition = groupPosition;
		TextView tvAitChildView;
		FrameLayout fragmentContainerAit;
		Button btnOpenTav, btnOpenTca, btnOpenRrd, btnPrintAuto, btnNewAuto;

		View mView;
		if (view != null) {
			mView = view;
		} else {
			mView = mInflator.inflate(R.layout.ait_listview_child,
					parent, false);
		}

		fragmentContainerAit = mView.findViewById(R.id.fragment_container_list);

		tvAitChildView = mView.findViewById(R.id.tv_open_ait_data_childview);

		final AitData aitData = new AitData();

		aitData.setAitDataFromCursor(getGroup(groupPosition));

		/*fragmentContainerAuto.setVisibility(view.VISIBLE);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.fragment_container_list, AutoPreviewFragment.newInstance()).commit();*/

		tvAitChildView.setText(aitData.getAutoListViewData(mycontext));

		btnOpenTav = mView.findViewById(R.id.bt_open_autos_tav);
		btnOpenTca = mView.findViewById(R.id.bt_open_autos_tca);
		btnOpenRrd = mView.findViewById(R.id.bt_open_auto_rrd);
		btnPrintAuto = mView.findViewById(R.id.bt_print_auto);
		btnNewAuto = mView.findViewById(R.id.bt_new_auto);

		btnNewAuto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				/*openDadosAuto((DatabaseCreator
						.getDatabaseAdapterAutoInfracao(mycontext))
						.getDataAutoFromPlate(carPlate));*/

				dialogNewAuto("Deseja criar outro AIT para este veículo de placa " + vehiclePlate + " ?", mycontext);

			}

		});

		btnOpenTav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TavData data = new TavData();
				data.setPlate(aitData.getPlate());
				data.setAitNumber(aitData.getAitNumber());
				openTAV(data);

			}
		});

		btnOpenTca.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TcaData data = new TcaData();
				data.setDriverName(aitData.getConductorName());
				data.setCnhPpd(aitData.getCnhPpd());
				data.setCpf(aitData.getCnhState());
				data.setPlate(aitData.getPlate());
				data.setPlateState(aitData.getState());
				data.setBrandModel(aitData.getVehicleModel());
				data.setAitNumber(aitData.getAitNumber());
				openTCA(data);

			}
		});

		btnOpenRrd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				RrdData rrdData = new RrdData();
				rrdData.setAitNumber(aitData.getAitNumber());
				rrdData.setPlate(aitData.getPlate());
				rrdData.setDriverName(aitData.getConductorName());
				rrdData.setPlateState(aitData.getState());
				rrdData.setRegistrationNumber(aitData.getCnhPpd());
				rrdData.setRegistrationState(aitData.getCnhState());
				rrdData.setDateCollected(aitData.getAitDate());
				rrdData.setTimeCollected(aitData.getAitTime());
				rrdData.setCityCollected(aitData.getCity());
				rrdData.setStateCollected(aitData.getState());
				openRRD(rrdData);
			}
		});

		btnPrintAuto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogViaPrint(aitData, mycontext);
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
						.getAitDatabaseAdapter(mycontext))
						.getAitDataFromPlate(vehiclePlate));
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

	public void dialogViaPrint(final AitData dados, Context context){
		LayoutInflater linear = LayoutInflater.from(context);
		View view = linear.inflate(R.layout.print_layout_option, null);
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mycontext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		alertDialog.setView(view);

		final AlertDialog dialog = alertDialog.create();
		dialog.show();

		Button btnViaAgente, btnViaCondutor;

		btnViaAgente = view.findViewById(R.id.btn_via_agente);
		btnViaCondutor = view.findViewById(R.id.btn_via_condutor);

		btnViaAgente.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				printListener.print(dados, null, "AGENTE");
				dialog.dismiss();
			}
		});

		btnViaCondutor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				printListener.print(dados, null, "CONDUTOR");
				dialog.dismiss();
			}
		});

	}

	private void openRRD(RrdData data) {
		Intent intent = new Intent(mycontext, RrdActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(RrdData.getRRDId(), data);
		intent.putExtras(bundle);
		mycontext.startActivity(intent);
		((Activity) mycontext).finish();
	}

	private void openTCA(TcaData data) {
		Intent intent = new Intent(mycontext, TcaActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(TcaData.getTcaId(), data);
		intent.putExtras(bundle);
		mycontext.startActivity(intent);
		((Activity) mycontext).finish();
	}

	private void openTAV(TavData data) {
		Intent intent = new Intent(mycontext, TavActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(TavData.getTavId(), data);
		intent.putExtras(bundle);
		mycontext.startActivity(intent);
		((Activity) mycontext).finish();
	}

	private void openDadosAuto(AitData aitData) {
		Intent intent = new Intent(mycontext, AitActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(AitData.getIDAuto(), aitData);
		intent.putExtras(bundle);
		mycontext.startActivity(intent);
		((Activity) mycontext).finish();
	}

}
