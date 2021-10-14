package net.sistransito.mobile.rrd.lister;

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
import net.sistransito.mobile.bluetoothprint.PrintBitmap.RrdPrintBitmap;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.BasePrintBitmap;
import net.sistransito.mobile.rrd.RrdData;
import net.sistransito.ui.BasePrintActivity;
import net.sistransito.R;

public class RrdLister extends BasePrintActivity implements OnClickListener {
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rrd_lister_main);
        initializedView();
    }

    @Override
    protected void onPrintData(Object mainData, Object aitData) {
        BasePrintBitmap rrdPrintBitmap = new RrdPrintBitmap(this,
                (RrdData) mainData, (AitData) aitData);
        startPrint(rrdPrintBitmap);
    }

    @Override
    protected void onPrintData(Object mainData, Object aitData, String copy) {

    }

    private void initializedView() {
        ImageButton imBtnBack = (ImageButton) findViewById(R.id.im_btn_back);
        imBtnBack.setOnClickListener(this);

        if (checkCursor()) {
            addResultView();
        } else {
            addNoResultView();
        }
    }

    private boolean checkCursor() {
        cursor = (DatabaseCreator.getRrdDatabaseAdapter(this)).getRrdCursor();
        return cursor.getCount() > 0;
    }

    private void addNoResultView() {
        RelativeLayout rrdListerLayout = (RelativeLayout) findViewById(R.id.rrd_lister_layout);
        cursor.close();
        TextView tvMessage = new TextView(this);
        tvMessage.setText(getResources().getString(
                R.string.nehum_resultado_retornado));
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setTextAppearance(this, android.R.style.TextAppearance_Large);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        tvMessage.setLayoutParams(params);
        tvMessage.setTextColor(getResources().getColor(R.color.line_color));
        if (tvMessage.getParent() == null) {
            rrdListerLayout.addView(tvMessage);
        }
    }

    private void addResultView() {
        RrdListerExpandableAdapter expandableAdapter = new RrdListerExpandableAdapter(cursor,
                RrdLister.this);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view_rrd);
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
