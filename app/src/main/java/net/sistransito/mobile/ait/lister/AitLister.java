package net.sistransito.mobile.ait.lister;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.AitPrintBitmap;
import net.sistransito.ui.BasePrintActivity;
import net.sistransito.R;

public class AitLister extends BasePrintActivity implements OnClickListener {
    private AutoListerExpandableAdapter expandableAdapter;
    private ExpandableListView expandableListView;
    private ImageButton imBtnBack;
    private TextView tvMessage;
    private RelativeLayout aitListerLayout;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ait_list_main);
        initializedView();
    }

    @Override
    protected void onPrintData(Object mainData, Object aitData) {
        AitPrintBitmap printBitmap = new AitPrintBitmap(this, (AitData) mainData);
        startPrint(printBitmap);
    }

    @Override
    protected void onPrintData(Object mainData, Object aitData, String copy) {
        //Log.d("aqui", "onPrintData");
        AitPrintBitmap printBitmap = new AitPrintBitmap(this, (AitData) mainData, copy);
        startPrint(printBitmap);
    }

    private void initializedView() {
        imBtnBack = (ImageButton) findViewById(R.id.im_btn_back);
        imBtnBack.setOnClickListener(this);

        if (checkCursor()) {
            addResultView();
        } else {
            addNoResultView();
        }
    }

    private boolean checkCursor() {
        cursor = (DatabaseCreator.getAitDatabaseAdapter(this))
                .getAitActiveCursor();
        return cursor.getCount() > 0;
    }

    private void addNoResultView() {
        aitListerLayout = (RelativeLayout) findViewById(R.id.ait_lister_layout);
        cursor.close();
        tvMessage = new TextView(this);
        tvMessage.setText(getResources().getString(
                R.string.nehum_resultado_retornado));
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setTextAppearance(this, android.R.style.TextAppearance_Large);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        tvMessage.setLayoutParams(params);
        tvMessage.setTextColor(getResources().getColor(R.color.line_color));
        if (tvMessage.getParent() == null) {
            aitListerLayout.addView(tvMessage);
        }
    }

    private void addResultView() {
        expandableAdapter = new AutoListerExpandableAdapter(cursor,
                AitLister.this);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewPlate);
        expandableListView.setAdapter(expandableAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_btn_back:
                finish();
                break;
        }
    }
}