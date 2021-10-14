package net.sistransito.mobile.tca;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.viewpagerindicator.UnderlinePageIndicator;

import net.sistransito.mobile.adapter.TcaStartSectionsPagerAdapter;
import net.sistransito.mobile.adapter.TcaSubTitleSectionsPagerAdapter;
import net.sistransito.mobile.viewpager.AnySwipeableViewPager;
import net.sistransito.mobile.viewpager.DepthPageTransformer;
import net.sistransito.mobile.viewpager.FixedSpeedScroller;
import net.sistransito.mobile.viewpager.ZoomOutPageTransformer;
import net.sistransito.R;

import java.lang.reflect.Field;

public class TcaActivity extends FragmentActivity implements
		OnClickListener {
	private AnySwipeableViewPager pager;
	private AnySwipeableViewPager subTitlePager;
	private Field mScroller;
	private FixedSpeedScroller scroller;
	private UnderlinePageIndicator indicator;
	private ImageView imBtnTabConductor, imBtnTabQuiz,
			imBtnBack;
	private TcaStartSectionsPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.tca_main);
		getTCAObject();
		initializedView();
		setMainPager();
		setSubTitlePager();
		setTitleIndicator();

	}

	private void getTCAObject() {
		Bundle bundleFrom = getIntent().getExtras();
		TcaObject.setTCAObject((TcaData) bundleFrom.getSerializable(TcaData
				.getTcaId()));
	}

	private void setTitleIndicator() {
		indicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		indicator.setFades(false);

		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int positon) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		setCurrentTabSelectedIteams(pager.getCurrentItem());

	}

	private void setSubTitlePager() {
		subTitlePager = (AnySwipeableViewPager) findViewById(R.id.tca_title_pager);
		subTitlePager.setSwipeable(false);
		subTitlePager.setAdapter(new TcaSubTitleSectionsPagerAdapter(
				getSupportFragmentManager()));
		subTitlePager.setPageMargin(20);
		subTitlePager.setPageTransformer(true, new ZoomOutPageTransformer());

		try {

			mScroller.set(subTitlePager, scroller);

		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

	}

	private void setMainPager() {
		adapter = new TcaStartSectionsPagerAdapter(getSupportFragmentManager());
		pager = (AnySwipeableViewPager) findViewById(R.id.tca_start_pager);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_btn_tab_conductor:
			setPagerPosition(0);
			break;
		case R.id.im_btn_tab_questionario:
			setPagerPosition(1);

			break;
		case R.id.im_btn_back:
			finish();
		}
	}

	private void setPagerPosition(int position) {
		setCurrentTabSelectedIteams(position);
		subTitlePager.setCurrentItem(position);
		pager.setCurrentItem(position);
	}

	private void setCurrentTabSelectedIteams(int positon) {
		switch (positon) {
		case 0:
			imBtnTabConductor.setSelected(true);
			imBtnTabQuiz.setSelected(false);
			break;

		case 1:
			imBtnTabConductor.setSelected(false);
			imBtnTabQuiz.setSelected(true);
			break;

		}
	}

	private void initializedView() {
		imBtnTabQuiz = (ImageView) findViewById(R.id.im_btn_tab_questionario);
		imBtnTabConductor = (ImageView) findViewById(R.id.im_btn_tab_conductor);

		imBtnTabQuiz.setOnClickListener(this);
		imBtnTabConductor.setOnClickListener(this);

		imBtnBack = (ImageView) findViewById(R.id.im_btn_back);
		imBtnBack.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        return !(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0);
    }
}
