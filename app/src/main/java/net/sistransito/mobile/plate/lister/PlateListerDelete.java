package net.sistransito.mobile.plate.lister;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.SearchPlateDatabaseAdapter;
import net.sistransito.R;

import java.util.ArrayList;

public class PlateListerDelete extends AppCompatActivity implements OnClickListener,
		OnCheckedChangeListener {
	private PlateListerDeleteExpendableAdapter logExpendableAdapter;
	private ExpandableListView expandableListView;
	private CheckBox cbCheckAll;
	private Button btnDelete;
	private TextView tvMessage;
	private RelativeLayout plateListerLayout;
	private ProgressDialog pDialog = null;
	private SearchPlateDatabaseAdapter searchPlateDatabaseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plate_list_delete_main);
		expandableListView = ((ExpandableListView) findViewById(R.id.remove_expandableListView));
		cbCheckAll = (CheckBox) findViewById(R.id.cb_check_all);
		cbCheckAll.setOnCheckedChangeListener(this);
		btnDelete = (Button) findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(this);
		addAdapter();
	}

	private void addAdapter() {
		searchPlateDatabaseAdapter = DatabaseCreator
				.getSearchPlateDatabaseAdapter(this);
		if (checkView()) {
			setAdapter();
		} else {
			setMessage();
		}

	}

	private void setMessage() {

		plateListerLayout = (RelativeLayout) findViewById(R.id.placa_lister_layout_remove);
		if (expandableListView.getParent() != null) {
			plateListerLayout.removeView(expandableListView);
		}
		tvMessage = new TextView(this);
		tvMessage.setText(getResources().getString(R.string.nehum_resultado_retornado));
		tvMessage.setGravity(Gravity.CENTER);
		tvMessage.setTextAppearance(this, android.R.style.TextAppearance_Large);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		tvMessage.setLayoutParams(params);
		tvMessage.setTextColor(getResources().getColor(R.color.line_color));
		if (tvMessage.getParent() == null) {
			plateListerLayout.addView(tvMessage);
		}
		btnDelete.setEnabled(false);
		cbCheckAll.setEnabled(false);

	}

	private void setAdapter() {
		logExpendableAdapter = new PlateListerDeleteExpendableAdapter(
				searchPlateDatabaseAdapter.getPlateCursor(), this);
		expandableListView.setAdapter(logExpendableAdapter);
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false; // This way the expander cannot be
								// collapsed
			}
		});
		btnDelete.setEnabled(true);
		cbCheckAll.setEnabled(true);

	}

	private boolean checkView() {
		return searchPlateDatabaseAdapter.getPlateCursor().getCount() > 0;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_btn_back:
			finish();
			startActivity(new Intent(PlateListerDelete.this, PlateLister.class));
			break;
		case R.id.btn_delete:
			cbCheckAll.setOnCheckedChangeListener(null);
			(new DeleteData()).execute("");
			cbCheckAll.setOnCheckedChangeListener(this);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.cb_check_all) {
			if (isChecked) {

				if (checkView()) {
					PlateListerDeleteExpendableAdapter.chnAgeState = true;
					for (int i = 0; i < PlateListerDeleteExpendableAdapter.selectedStatus.length; i++) {
						PlateListerDeleteExpendableAdapter.selectedStatus[i] = true;
					}
					logExpendableAdapter.notifyDataSetChanged();
				}

			} else {
				for (int i = 0; i < PlateListerDeleteExpendableAdapter.selectedStatus.length; i++) {
					PlateListerDeleteExpendableAdapter.selectedStatus[i] = false;
				}
				logExpendableAdapter.notifyDataSetChanged();
			}
		}
	}

	class DeleteData extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = null;
			pDialog = new ProgressDialog(PlateListerDelete.this,
					AlertDialog.THEME_HOLO_LIGHT);
			pDialog.setMax(100);
			pDialog.setCancelable(false);
			pDialog.setMessage(getResources().getString(R.string.deletando));
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<String> list = new ArrayList<String>();

			for (int i = 0; i < PlateListerDeleteExpendableAdapter.idNumber.length; i++) {
				if (!((PlateListerDeleteExpendableAdapter.idNumber[i]).equals(""))) {
					list.add(PlateListerDeleteExpendableAdapter.idNumber[i]);
				}
			}
			if (list.size() > 0) {

				String deleteIdFields[] = new String[list.size()];
				deleteIdFields = list.toArray(deleteIdFields);

				for (int i = 0; i < deleteIdFields.length; i++) {
					if ((DatabaseCreator
							.getSearchPlateDatabaseAdapter(PlateListerDelete.this))
							.deleteDataFromIdField(deleteIdFields[i])) {

					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			// pDialog.setProgress(Integer.parseInt(values[0]));
		}

		@Override
		protected void onPostExecute(String result) {
			if ((pDialog.isShowing()) || (pDialog != null)) {
				pDialog.dismiss();
				pDialog = null;
			}
			cbCheckAll.setChecked(false);
			logExpendableAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
			addAdapter();
		}
	}
}
