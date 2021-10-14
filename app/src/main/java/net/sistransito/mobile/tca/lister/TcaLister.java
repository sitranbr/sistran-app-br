package net.sistransito.mobile.tca.lister;

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
import net.sistransito.mobile.bluetoothprint.PrintBitmap.TcaPrintBitmap;
import net.sistransito.mobile.bluetoothprint.PrintBitmap.base.BasePrintBitmap;
import net.sistransito.mobile.tca.TcaData;
import net.sistransito.ui.BasePrintActivity;
import net.sistransito.R;

public class TcaLister extends BasePrintActivity implements OnClickListener {
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tca_lister_main);
        initializedView();
    }

    @Override
    protected void onPrintData(Object mainData, Object aitData) {
        BasePrintBitmap tcaPrintBitmap = new TcaPrintBitmap(this, (TcaData) mainData, (AitData) aitData);
        startPrint(tcaPrintBitmap);
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
        cursor = (DatabaseCreator.getTcaDatabaseAdapter(this)).getTCACursor();
        return cursor.getCount() > 0;
    }

    private void addNoResultView() {
        RelativeLayout tcaListerLayout = (RelativeLayout) findViewById(R.id.tca_lister_layout);
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
            tcaListerLayout.addView(tvMessage);
        }
    }

    private void addResultView() {
        TcaListerExpandableAdapter expandableAdapter = new TcaListerExpandableAdapter(cursor,
                TcaLister.this);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewTca);
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
