package net.sistransito.mobile.aitcompany;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.viewpagerindicator.UnderlinePageIndicator;

import net.sistransito.mobile.adapter.TabPJStartSectionsPagerAdapter;
import net.sistransito.mobile.adapter.TabPJSubTitleSectionsPagerAdapter;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.fragment.AnyAlertDialog;
import net.sistransito.mobile.fragment.UpdateFragment;
import net.sistransito.mobile.util.Routine;
import net.sistransito.mobile.viewpager.AnySwipeableViewPager;
import net.sistransito.mobile.viewpager.DepthPageTransformer;
import net.sistransito.mobile.viewpager.FixedSpeedScroller;
import net.sistransito.mobile.viewpager.ZoomOutPageTransformer;
import net.sistransito.R;

import java.lang.reflect.Field;

public class AitCompanyActivity extends AppCompatActivity implements OnClickListener {
    private AnySwipeableViewPager pager;
    private AnySwipeableViewPager subTituloDaPagina;
    private Field mScroller;
    private FixedSpeedScroller scroller;
    private UnderlinePageIndicator indicator;
    private ImageView imBtnTabCondutor, imBtnTabEnd, imBtnTabInfracao, imBtnBack;
    private TabPJStartSectionsPagerAdapter adapter;
    private pjData dadosAuto;

    private int tabAtual;

    String tabCondutor;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.autopj_main);

        getObjectAuto();
        initializedView();
        setMainPager();
        setSubTitlePager();
        setTitleIndicator();

        if (dadosAuto.isStorePJFullData()) {
            setPagerPosition(2);
        }

    }

    private void getObjectAuto() {
        Bundle bundle = getIntent().getExtras();
        ObjectAutoPJ.setDadosAuto((pjData) bundle.getSerializable(pjData.getIDAuto()));
        dadosAuto = ObjectAutoPJ.getDadosAuto();
    }

    private void setTitleIndicator() {
        indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setFades(false);

        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int positon) {

                if (positon == 3) {
                    UpdateFragment fragment = (UpdateFragment) adapter
                            .instantiateItem(pager, positon);
                    fragment.Update();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void setSubTitlePager() {

        subTituloDaPagina = (AnySwipeableViewPager) findViewById(R.id.auto_title_pager);
        subTituloDaPagina.setSwipeable(false);
        subTituloDaPagina.setAdapter(new TabPJSubTitleSectionsPagerAdapter(
                getSupportFragmentManager()));
        subTituloDaPagina.setPageMargin(20);
        subTituloDaPagina.setPageTransformer(true,
                new ZoomOutPageTransformer());
        try {

            mScroller.set(subTituloDaPagina, scroller);

        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

    }

    private void setMainPager() {

        adapter = new TabPJStartSectionsPagerAdapter(
                getSupportFragmentManager());
        pager = (AnySwipeableViewPager) findViewById(R.id.auto_start_pager);
        pager.setSwipeable(false);
        pager.setPageMargin(20);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setAdapter(adapter);

        try {
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new FixedSpeedScroller(this);
            scroller.setFixedDuration(2000);
            mScroller.set(pager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

    }

    public void setCancelar(Context mycontext, String motivo){
        if (!DatabaseCreator.getAitPJDatabaseAdapter(mycontext).setCancelarAuto(dadosAuto, motivo))
            Routine.showAlert(getResources().getString(R.string.update_erro), mycontext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pj_btn_tab_condutor:
                setPagerPosition(0);
                break;
            case R.id.img_pj_btn_tab_end:
                setPagerPosition(1);
                break;
            case R.id.img_pj_btn_tab_infracao:
                setPagerPosition(2);
                break;
            case R.id.im_pj_btn_back:
                AnyAlertDialog.dialogView(this, this.getResources().getString(R.string.titulo_cancelar), null);
                break;
        }
    }

    private void setPagerPosition(int position) {

        if (dadosAuto.isStorePJFullData() && position == 0) {
            return;
        }
        Log.d("isStore ? ", String.valueOf(dadosAuto.isStorePJFullData()));
        setCurrentTabSelectedIteams(position);
        subTituloDaPagina.setCurrentItem(position);
        pager.setCurrentItem(position);
    }

    private void setCurrentTabSelectedIteams(int positon) {
        switch (positon) {
            case 0:
                imBtnTabCondutor.setSelected(true);
                imBtnTabEnd.setSelected(false);
                imBtnTabInfracao.setSelected(false);
                break;
            case 1:
                imBtnTabCondutor.setSelected(false);
                imBtnTabEnd.setSelected(true);
                imBtnTabInfracao.setSelected(false);
                break;
            case 2:
                imBtnTabCondutor.setSelected(false);
                imBtnTabEnd.setSelected(false);
                imBtnTabInfracao.setSelected(true);
                break;
        }
    }

    private void initializedView() {
        imBtnTabCondutor = (ImageView) findViewById(R.id.img_pj_btn_tab_condutor);
        imBtnTabEnd = (ImageView) findViewById(R.id.img_pj_btn_tab_end);
        imBtnTabInfracao = (ImageView) findViewById(R.id.img_pj_btn_tab_infracao);

        imBtnTabCondutor.setOnClickListener(this);
        imBtnTabEnd.setOnClickListener(this);
        imBtnTabInfracao.setOnClickListener(this);

        imBtnBack = (ImageView) findViewById(R.id.im_pj_btn_back);
        imBtnBack.setOnClickListener(this);
    }

    public void setTabAtual(int tabAtual) { setPagerPosition(tabAtual); }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return !(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0);
    }

}
