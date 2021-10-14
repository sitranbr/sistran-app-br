package net.sistransito.mobile.tav.lister;


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
import net.sistransito.mobile.database.TavDatabaseHelper;
import net.sistransito.mobile.bluetoothprint.bluetooth.BluetoothPrinterListerner;
import net.sistransito.mobile.tav.TavData;
import net.sistransito.R;

public class TAVListerExpandableAdapter extends CursorTreeAdapter {
	private LayoutInflater mInflator;
	private Context mycontext;
	private BluetoothPrinterListerner printListener;

	public TAVListerExpandableAdapter(Cursor cursor, Context context) {
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
		TextView tvLogPlate, tvLogOwnerName, etLogAitNumber;

		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);

		tvLogOwnerName = (TextView) view
				.findViewById(R.id.log_nome_do_proprietario);
		etLogAitNumber = (TextView) view
				.findViewById(R.id.log_number_do_auto);

		tvLogPlate.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.PLATE)));

		tvLogOwnerName.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.OWNER_NAME)));

		etLogAitNumber.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.AIT_NUMBER)));

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
				.inflate(R.layout.tav_lister_listview_parent, null);
		TextView tvLogPlate, tvLogOwnerName, tvLogAitNumber;

		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);

		tvLogOwnerName = (TextView) view
				.findViewById(R.id.log_nome_do_proprietario);
		tvLogAitNumber = (TextView) view
				.findViewById(R.id.log_number_do_auto);

		tvLogPlate.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.PLATE)));

		tvLogOwnerName.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.OWNER_NAME)));

		tvLogAitNumber.setText(cursor.getString(cursor
				.getColumnIndex(TavDatabaseHelper.AIT_NUMBER)));
		return view;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		int groupId = groupCursor.getInt(groupCursor
				.getColumnIndex(TavDatabaseHelper.COLUMN_ID));
		return (DatabaseCreator.getTavDatabaseAdapter(mycontext))
				.getTAVCursorFromID(groupId);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		// final int gPosition = groupPosition;
		TextView tvTavChildview;
		Button btnTavPrint;
		View mView = null;
		if (view != null) {
			mView = view;
		} else {
			mView = mInflator.inflate(R.layout.tav_lister_listview_child,
					parent, false);
		}
		tvTavChildview = (TextView) mView
				.findViewById(R.id.ver_tav_childview);
		final TavData tavData = new TavData();

		tavData.setTAVDataFromCursor(getGroup(groupPosition));

		tvTavChildview.setText(tavData.getTCAListViewData(mycontext));

		btnTavPrint = (Button) mView.findViewById(R.id.ver_tav_print);
		btnTavPrint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				AitData aitData = DatabaseCreator
						.getAitDatabaseAdapter(mycontext)
						.getDataFromAitNumber(
								tavData.getAitNumber());

				printListener.print(tavData, aitData);

			}
		});
		return mView;
	}
}
