package net.sistransito.mobile.plate.lister;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.R;

public class PlateLister extends AppCompatActivity implements OnClickListener {
	private PlateListerExpandableAdapter expandableAdapter;
	private ExpandableListView expandableListView;

	private ImageButton imBtnDelete, imBtnBack;
	public static Boolean CallSecondActivity = false;
	private TextView tvMessage;
	private RelativeLayout plateListerLayout;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plate_list_main);
		initializedView();
	}

	private void initializedView() {
		imBtnBack = (ImageButton) findViewById(R.id.im_btn_back);
		imBtnBack.setOnClickListener(this);
		imBtnDelete = (ImageButton) findViewById(R.id.im_btn_delete);
		imBtnDelete.setOnClickListener(this);
		if (checkCursor()) {
			addResultView();
		} else {
			addNoResultView();
		}
	}

	private boolean checkCursor() {
		cursor = (DatabaseCreator.getSearchPlateDatabaseAdapter(PlateLister.this))
				.getPlateCursor();
		return cursor.getCount() > 0;
	}

	private void addNoResultView() {
		plateListerLayout = (RelativeLayout) findViewById(R.id.placa_lister_layout);
		imBtnDelete.setEnabled(false);
		cursor.close();
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
	}

	private void addResultView() {
		expandableAdapter = new PlateListerExpandableAdapter(cursor,
				PlateLister.this);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewPlate);
		expandableListView.setAdapter(expandableAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_btn_back:
			finish();
			break;
		case R.id.im_btn_delete:
			finish();
			startActivity(new Intent(PlateLister.this, PlateListerDelete.class));
			break;

		}
	}

}
