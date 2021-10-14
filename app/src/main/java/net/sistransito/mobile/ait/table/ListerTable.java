package net.sistransito.mobile.ait.table;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.sistransito.mobile.database.DatabaseCreator;

import net.sistransito.R;

public class ListerTable extends AppCompatActivity implements View.OnClickListener {
    private ListerExpandableAdapterTable expandableAdapter;
    private ExpandableListView expandableListView;

    private ImageButton im_btn_delete, im_btn_back;
    public static Boolean CallSecondActivity = false;
    private TextView tvMessage;
    private RelativeLayout placa_lister_layout;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_table_fragment);
        initializedView();
    }

    private void initializedView() {
        im_btn_back = (ImageButton) findViewById(R.id.im_btn_back);
        im_btn_back.setOnClickListener(this);
        im_btn_delete = (ImageButton) findViewById(R.id.im_btn_delete);
        im_btn_delete.setOnClickListener(this);
        if (checkCursor()) {
            addResultView();
        } else {
            addNoResultView();
        }
    }

    private boolean checkCursor() {
        cursor = (DatabaseCreator.getPrepopulatedDBOpenHelper(ListerTable.this))
                .getInfrationCursor();
        return cursor.getCount() > 0;
    }

    private void addNoResultView() {
        placa_lister_layout = (RelativeLayout) findViewById(R.id.tabela_lister_layout);
        im_btn_delete.setEnabled(false);
        cursor.close();
        tvMessage = new TextView(this);
        tvMessage.setText(getResources().getString(R.string.nehum_resultado_retornado));
        tvMessage.setGravity(Gravity.CENTER);
        tvMessage.setTextAppearance(this, android.R.style.TextAppearance_Large);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        tvMessage.setLayoutParams(params);
        tvMessage.setTextColor(getResources().getColor(R.color.line_color));
        if (tvMessage.getParent() == null) {
            placa_lister_layout.addView(tvMessage);
        }
    }

    private void addResultView() {
        expandableAdapter = new ListerExpandableAdapterTable(cursor,
                ListerTable.this);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_listview_tabela);
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
                //startActivity(new Intent(TabelaLister.this, PlacaListerDelete.class));
                break;

        }
    }

}
