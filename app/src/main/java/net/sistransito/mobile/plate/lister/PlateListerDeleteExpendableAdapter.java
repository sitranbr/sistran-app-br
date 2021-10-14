package net.sistransito.mobile.plate.lister;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.sistransito.mobile.database.PlateSearchDatabaseHelper;
import net.sistransito.R;

public class PlateListerDeleteExpendableAdapter extends CursorTreeAdapter {
	LayoutInflater mInflator;
	Context mycontext;
	public static String idNumber[];
	public static boolean chnAgeState = false;
	public static boolean[] selectedStatus;

	public PlateListerDeleteExpendableAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		mycontext = context;
		mInflator = LayoutInflater.from(context);
		idNumber = new String[cursor.getCount()];
		selectedStatus = new boolean[cursor.getCount()];
		for (int i = 0; i < cursor.getCount(); i++) {
			selectedStatus[i] = false;
			idNumber[i] = "";
		}

	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {

	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {

		ImageView imageView;

		TextView tvLogPlate, tvLogTime, tvLogLicenseStatus;
		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);
		tvLogLicenseStatus = (TextView) view
				.findViewById(R.id.log_license_status);
		tvLogTime = (TextView) view.findViewById(R.id.log_time);
		tvLogPlate.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.PLATE)));
		tvLogLicenseStatus
				.setText(cursor.getString(cursor
						.getColumnIndex(PlateSearchDatabaseHelper.LICENSING_STATUS)));
		tvLogTime.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.DATE)));
		imageView = (ImageView) view.findViewById(R.id.list_image);
		imageView.setImageResource(R.drawable.ic_unchecked);
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {

		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {

		View view = mInflator.inflate(
				R.layout.plate_delete_listview_parent, null);
		ImageView imageView;

		TextView tvLogPlate, tvLogTime, tvLogLicenseStatus;
		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);
		tvLogLicenseStatus = (TextView) view
				.findViewById(R.id.log_license_status);
		tvLogTime = (TextView) view.findViewById(R.id.log_time);
		tvLogPlate.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.PLATE)));
		tvLogLicenseStatus
				.setText(cursor.getString(cursor
						.getColumnIndex(PlateSearchDatabaseHelper.LICENSING_STATUS)));
		tvLogTime.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.DATE)));
		imageView = (ImageView) view.findViewById(R.id.list_image);
		imageView.setImageResource(R.drawable.ic_unchecked);
		return view;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View view,
			ViewGroup parent) {
		final int position = groupPosition;

		view = mInflator.inflate(R.layout.plate_delete_listview_parent,
				null);

		TextView tvLogPlate, tvLogTime, tvLogLicenseStatus;
		tvLogPlate = (TextView) view.findViewById(R.id.log_plate);
		tvLogLicenseStatus = (TextView) view
				.findViewById(R.id.log_license_status);
		tvLogTime = (TextView) view.findViewById(R.id.log_time);
	
		tvLogPlate.setText(getGroup(groupPosition).getString(
				getGroup(groupPosition).getColumnIndex(
						PlateSearchDatabaseHelper.PLATE)));
		tvLogLicenseStatus
				.setText(getGroup(groupPosition)
						.getString(
								getGroup(groupPosition)
										.getColumnIndex(
												PlateSearchDatabaseHelper.LICENSING_STATUS)));
		tvLogTime.setText(getGroup(groupPosition).getString(
				getGroup(groupPosition).getColumnIndex(
						PlateSearchDatabaseHelper.DATE)));

		final ImageView imageView = (ImageView) view
				.findViewById(R.id.list_image);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedStatus[position]) {
					selectedStatus[position] = false;
					imageView.setImageResource(R.drawable.ic_unchecked);
					idNumber[position] = "";
				} else {
					selectedStatus[position] = true;
					imageView.setImageResource(R.drawable.ic_checked);
					idNumber[position] = (getGroup(position)
							.getString(getGroup(position).getColumnIndex(
									PlateSearchDatabaseHelper.COLUMN_ID)))
							.toString();

				}

			}
		});

		if (selectedStatus[position]) {

			imageView.setImageResource(R.drawable.ic_checked);
			idNumber[position] = (getGroup(position).getString(getGroup(
					position).getColumnIndex(
					PlateSearchDatabaseHelper.COLUMN_ID))).toString();

		} else {
			imageView.setImageResource(R.drawable.ic_unchecked);
			idNumber[position] = "";

		}
		if (chnAgeState == true) {
			for (int i = 0; i < idNumber.length; i++) {
				if (selectedStatus[i]) {
					idNumber[i] = (getGroup(i)
							.getString(getGroup(i).getColumnIndex(
									PlateSearchDatabaseHelper.COLUMN_ID)))
							.toString();

				} else {
					idNumber[i] = "";
				}
			}

			chnAgeState = false;
		}

		return view;

	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		return null;
	}
}