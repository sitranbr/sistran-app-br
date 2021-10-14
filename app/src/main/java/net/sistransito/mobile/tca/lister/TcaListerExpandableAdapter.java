package net.sistransito.mobile.tca.lister;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.TcaDatabaseHelper;
import net.sistransito.mobile.bluetoothprint.bluetooth.BluetoothPrinterListerner;
import net.sistransito.mobile.tca.TcaData;
import net.sistransito.R;

public class TcaListerExpandableAdapter extends CursorTreeAdapter {
	private LayoutInflater mInflator;
	private Context mycontext;
	private BluetoothPrinterListerner printListener;

	public TcaListerExpandableAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		mycontext = context;
		mInflator = LayoutInflater.from(context);
		printListener = (BluetoothPrinterListerner)context;
	}

	@Override
	protected void bindChildView(View arg0, Context arg1, Cursor arg2,
			boolean arg3) {
	}

	@Override
	protected void bindGroupView(View view, Context arg1, Cursor cursor,
			boolean arg3) {
		TextView logPlate, logName, logCnhPpd;

		logPlate = (TextView) view.findViewById(R.id.log_placa);

		logName = (TextView) view.findViewById(R.id.log_nome);
		logCnhPpd = (TextView) view.findViewById(R.id.log_chn_ppd);

		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.PLACA)));

		logName.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.NOME_DO_CONDUTOR)));

		logCnhPpd.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.CNH_PPD)));

	}

	@Override
	protected View newChildView(Context arg0, Cursor arg1, boolean arg2,
			ViewGroup arg3) {
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpendable, ViewGroup parent) {
		View view = mInflator
				.inflate(R.layout.tca_lister_listview_parent, null);
		TextView logPlate, logName, logCnhPpd;

		logPlate = (TextView) view.findViewById(R.id.log_placa);

		logName = (TextView) view.findViewById(R.id.log_nome);
		logCnhPpd = (TextView) view.findViewById(R.id.log_chn_ppd);

		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.PLACA)));

		logName.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.NOME_DO_CONDUTOR)));

		logCnhPpd.setText(cursor.getString(cursor
				.getColumnIndex(TcaDatabaseHelper.CNH_PPD)));

		return view;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		int groupId = groupCursor.getInt(groupCursor
				.getColumnIndex(TcaDatabaseHelper.COLUMN_ID));
		return (DatabaseCreator.getTcaDatabaseAdapter(mycontext))
				.getTCACursorFromID(groupId);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		// final int gPosition = groupPosition;
		TextView viewTcaChildview;
		Button btnTcaPrint;
		View mView = null;
		if (view != null) {
			mView = view;
		} else {
			mView = mInflator.inflate(R.layout.tca_lister_listview_child,
					parent, false);
		}
		viewTcaChildview = (TextView) mView
				.findViewById(R.id.ver_tca_childview);
		final TcaData TCAData = new TcaData();
		TCAData.setTCADataFromCursor(getGroup(groupPosition));
		viewTcaChildview.setText(TCAData.getTcaListViewData(mycontext));
		btnTcaPrint = (Button) mView.findViewById(R.id.ver_tca_print);
		btnTcaPrint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				AitData aitData = DatabaseCreator
						.getAitDatabaseAdapter(mycontext)
						.getDataFromAitNumber(TCAData.getAitNumber());

				printListener.print(TCAData, aitData);

			}
		});
		return mView;
	}
}
