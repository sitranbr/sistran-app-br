package net.sistransito.mobile.plate.lister;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.PlateSearchDatabaseHelper;
import net.sistransito.R;

public class PlateListerExpandableAdapter extends CursorTreeAdapter {
	private LayoutInflater mInflator;
	private Context mycontext;

	public PlateListerExpandableAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		mycontext = context;
		mInflator = LayoutInflater.from(context);
	}

	@Override
	protected void bindChildView(View arg0, Context arg1, Cursor arg2,
			boolean arg3) {
	}

	@Override
	protected void bindGroupView(View view, Context arg1, Cursor cursor,
			boolean arg3) {
		TextView logPlate, logTime, logLicenseStatus;
		logPlate = (TextView) view.findViewById(R.id.log_plate);
		logLicenseStatus = (TextView) view
				.findViewById(R.id.log_license_status);
		logTime = (TextView) view.findViewById(R.id.log_time);
		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.PLATE)));
		logLicenseStatus
				.setText(cursor.getString(cursor
						.getColumnIndex(PlateSearchDatabaseHelper.LICENSING_STATUS)));
		logTime.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.DATE)));

	}

	@Override
	protected View newChildView(Context arg0, Cursor arg1, boolean arg2,
			ViewGroup arg3) {
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpendable, ViewGroup parent) {
		View view = mInflator.inflate(R.layout.plate_list_listview_parent,
				null);

		TextView logPlate, logTime, logLicenseStatus;

		logPlate = (TextView) view.findViewById(R.id.log_plate);
		logLicenseStatus = (TextView) view.findViewById(R.id.log_license_status);
		logTime = (TextView) view.findViewById(R.id.log_time);

		logPlate.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.PLATE)));
		logLicenseStatus
				.setText(cursor.getString(cursor
						.getColumnIndex(PlateSearchDatabaseHelper.LICENSING_STATUS)));
		logTime.setText(cursor.getString(cursor
				.getColumnIndex(PlateSearchDatabaseHelper.DATE)));
		return view;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		int groupId = groupCursor.getInt(groupCursor
				.getColumnIndex(PlateSearchDatabaseHelper.COLUMN_ID));
		return (DatabaseCreator.getSearchPlateDatabaseAdapter(mycontext))
				.getPlateFromIDCursor(groupId);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		final int G_POSITION = groupPosition;
		final String PLATE = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.PLATE));

		final String STATE = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.STATE));

		final String COUNTRY = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.COUNTRY));

		final String CHASSI = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.CHASSIS));

		final String COLOR = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.COLOR));

		final String BRAND = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.BRAND));

		final String MODEL = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.MODEL));

		final String SPECIES = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.TYPE));

		final String CATEGORY = getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.CATEGORY));

		View mView = null;
		if (view != null) {
			mView = view;
		} else {
			mView = mInflator.inflate(R.layout.plate_list_listview_child,
					parent, false);
		}

		TextView tvMarca, tvModel, tvColor, tvType, tvCategory, tvLicenseYear, tvLicenseDate, tvIpva, tvInsurance, tvIntraction, tvRestrictions;

		Button btnCreateNewAit, btnCreateAit;

		btnCreateNewAit = (Button) mView
				.findViewById(R.id.btn_plate_criar_novo_auto);
		btnCreateAit = (Button) mView.findViewById(R.id.btn_plate_criar_auto);

		if ((DatabaseCreator.getAitDatabaseAdapter(mycontext))
				.isSamePlateExist(PLATE)) {
			btnCreateNewAit.setEnabled(true);
			btnCreateAit.setEnabled(false);
		} else {
			btnCreateNewAit.setEnabled(false);
			btnCreateAit.setEnabled(true);
		}

		btnCreateNewAit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openDadosAuto((DatabaseCreator
						.getAitDatabaseAdapter(mycontext))
						.getAitDataFromPlate(PLATE));
			}

		});

		btnCreateAit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AitData aitData = new AitData();
				aitData.setPlate(PLATE);
				aitData.setStateVehicle(STATE.toUpperCase());
				aitData.setCountry(COUNTRY);
				aitData.setChassi(CHASSI);
				aitData.setVehicleBrand(BRAND);
				aitData.setVehicleModel(MODEL);
				aitData.setVehycleColor(COLOR);
				aitData.setVehicleSpecies(SPECIES.toUpperCase());
				aitData.setVehicleCategory(CATEGORY.toUpperCase());
				aitData.setStoreFullData(false);
				openDadosAuto(aitData);
			}
		});

		tvMarca = (TextView) mView.findViewById(R.id.log_marca);
		tvModel = (TextView) mView.findViewById(R.id.log_model);
		tvColor = (TextView) mView.findViewById(R.id.log_color);
		tvType = (TextView) mView.findViewById(R.id.log_type);
		tvCategory = (TextView) mView.findViewById(R.id.log_category);
		tvLicenseYear = (TextView) mView.findViewById(R.id.log_license_year);
		tvLicenseDate = (TextView) mView.findViewById(R.id.log_license_date);
		tvIpva = (TextView) mView.findViewById(R.id.log_ipva);
		tvInsurance = (TextView) mView.findViewById(R.id.log_insurance);
		tvIntraction = (TextView) mView.findViewById(R.id.log_infraction);
		tvRestrictions = (TextView) mView.findViewById(R.id.log_restriction);

		tvMarca.setText(mycontext.getResources().getString(
				R.string.marca_format)
		        + getGroup(G_POSITION)
				.getString(
						getGroup(G_POSITION)
								.getColumnIndex(
										PlateSearchDatabaseHelper.BRAND)));
		tvModel.setText(mycontext.getResources().getString(
				R.string.modelo_format)
				+ MODEL);

		tvColor.setText(mycontext.getResources().getString(R.string.cor_format)
				+ COLOR);

		tvType.setText(mycontext.getResources().getString(R.string.tipo_format)
				+ getGroup(G_POSITION).getString(
						getGroup(G_POSITION).getColumnIndex(
								PlateSearchDatabaseHelper.TYPE)));

		tvCategory.setText(mycontext.getResources().getString(R.string.categoria_format)
				+ getGroup(G_POSITION).getString(
				getGroup(G_POSITION).getColumnIndex(
						PlateSearchDatabaseHelper.CATEGORY)));

		tvLicenseYear
				.setText(mycontext.getResources().getString(
						R.string.ano_licenciamento_format)
						+ getGroup(G_POSITION)
								.getString(
										getGroup(G_POSITION)
												.getColumnIndex(
														PlateSearchDatabaseHelper.LICENSING_YEAR)));

		tvLicenseDate
				.setText(mycontext.getResources().getString(
						R.string.data_licenciamento_format)
						+ getGroup(G_POSITION)
								.getString(
										getGroup(G_POSITION)
												.getColumnIndex(
														PlateSearchDatabaseHelper.LICENSING_DATE)));
		tvIpva.setText(mycontext.getResources().getString(R.string.ipva_format)
				+ getGroup(G_POSITION).getString(
						getGroup(G_POSITION).getColumnIndex(
								PlateSearchDatabaseHelper.IPVA)));

		tvInsurance.setText(mycontext.getResources().getString(
				R.string.seguro_format)
				+ getGroup(G_POSITION).getString(
						getGroup(G_POSITION).getColumnIndex(
								PlateSearchDatabaseHelper.INSURANCE)));

		tvIntraction.setText(mycontext.getResources().getString(
				R.string.multas_format)
				+ getGroup(G_POSITION).getString(
						getGroup(G_POSITION).getColumnIndex(
								PlateSearchDatabaseHelper.INFRATIONS)));

		tvRestrictions.setText(mycontext.getResources().getString(
				R.string.restricoes_format)
				+ getGroup(G_POSITION).getString(
						getGroup(G_POSITION).getColumnIndex(
								PlateSearchDatabaseHelper.RESTRICTIONS)));

		return mView;
	}

	private void openDadosAuto(AitData data) {
		Intent intent = new Intent(mycontext, AitActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(AitData.getIDAuto(), data);
		intent.putExtras(bundle);
		mycontext.startActivity(intent);
		((Activity) mycontext).finish();
	}
}
