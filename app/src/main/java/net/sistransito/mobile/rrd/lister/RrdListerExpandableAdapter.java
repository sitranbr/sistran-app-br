package net.sistransito.mobile.rrd.lister;


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
import net.sistransito.mobile.database.AitDatabaseHelper;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.RrdDatabaseHelper;
import net.sistransito.mobile.bluetoothprint.bluetooth.BluetoothPrinterListerner;
import net.sistransito.mobile.rrd.RrdData;
import net.sistransito.R;

public class RrdListerExpandableAdapter extends CursorTreeAdapter {
	private LayoutInflater mInflator;
	private Context mycontext;
	private BluetoothPrinterListerner printListener;

	public RrdListerExpandableAdapter(Cursor cursor, Context context) {
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
		TextView tvLogPlate, tvLogDocument;
		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);
		tvLogDocument = (TextView) view.findViewById(R.id.log_documento);

		if(cursor.getString(cursor.getColumnIndex(RrdDatabaseHelper.RRD_TYPE)).equals("avulso")) {
			tvLogPlate.setText(cursor.getString(cursor
					.getColumnIndex(RrdDatabaseHelper.RRD_NUMBER)) + " - Avulso");
		}else{
			tvLogPlate.setText(cursor.getString(cursor
					.getColumnIndex(RrdDatabaseHelper.PLATE)));
		}
		tvLogDocument.setText("Documento: " + cursor.getString(cursor
				.getColumnIndex(RrdDatabaseHelper.DOCUMENT_TYPE)));

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
				.inflate(R.layout.rrd_listview_parent, null);
		TextView tvLogPlate, tvLogDocument;
		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);
		tvLogDocument = (TextView) view.findViewById(R.id.log_documento);

		tvLogPlate.setText(cursor.getString(cursor.getColumnIndex(RrdDatabaseHelper.RRD_NUMBER)));
		tvLogDocument.setText(cursor.getString(cursor
				.getColumnIndex(RrdDatabaseHelper.DOCUMENT_TYPE)));

		return view;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		int groupId = groupCursor.getInt(groupCursor
				.getColumnIndex(AitDatabaseHelper.COLUMN_ID));
		return (DatabaseCreator.getRrdDatabaseAdapter(mycontext))
				.getRrdCursorFromID(groupId);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		// final int gPosition = groupPosition;
		TextView verRrdChildview;
		Button btnRrdPrint;
		View mView = null;
		if (view != null) {
			mView = view;
		} else {
			mView = mInflator.inflate(R.layout.rrd_listview_child,
					parent, false);
		}
		verRrdChildview = (TextView) mView
				.findViewById(R.id.ver_rrd_childview);
		final RrdData RRDData = new RrdData();
		RRDData.setRRDDataFromCursor(getGroup(groupPosition));
		verRrdChildview.setText(RRDData.getRRDListViewData(mycontext));
		btnRrdPrint = (Button) mView.findViewById(R.id.btn_rrd_print);
		btnRrdPrint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AitData aitData = DatabaseCreator
						.getAitDatabaseAdapter(mycontext)
						.getDataFromAitNumber(
								RRDData.getAitNumber());

				printListener.print(RRDData, aitData);

			}
		});
		return mView;
	}
}
