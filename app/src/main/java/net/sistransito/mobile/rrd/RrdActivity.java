package net.sistransito.mobile.rrd;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.viewpagerindicator.UnderlinePageIndicator;

import net.sistransito.mobile.adapter.RrdStartSectionsPagerAdapter;
import net.sistransito.mobile.adapter.RrdSubTitleSectionsPagerAdapter;
import net.sistransito.mobile.viewpager.AnySwipeableViewPager;
import net.sistransito.mobile.viewpager.DepthPageTransformer;
import net.sistransito.mobile.viewpager.FixedSpeedScroller;
import net.sistransito.mobile.viewpager.ZoomOutPageTransformer;
import net.sistransito.R;

import java.lang.reflect.Field;

public class RrdActivity extends AppCompatActivity implements
		OnClickListener {
	private AnySwipeableViewPager pager;
	private AnySwipeableViewPager subTitlePager;
	private Field mScroller;
	private FixedSpeedScroller scroller;
	private UnderlinePageIndicator indicator;
	private ImageView imBtnTabDocument, imBtnTabInformations,
			imBtnBack;
	private RrdStartSectionsPagerAdapter startSectionsPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.rrd_list_main);
		getRrdObject();
		initializedView();
		setMainPager();
		setSubTitlePager();
		setTitleIndicator();

	}

	private void getRrdObject() {
		Bundle bundleFrom = getIntent().getExtras();
		RrdObject.setRRDObject((RrdData) bundleFrom.getSerializable(RrdData
				.getRRDId()));
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
		subTitlePager = (AnySwipeableViewPager) findViewById(R.id.rrd_title_pager);
		subTitlePager.setSwipeable(false);
		subTitlePager.setAdapter(new RrdSubTitleSectionsPagerAdapter(
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
		startSectionsPagerAdapter = new RrdStartSectionsPagerAdapter(
				getSupportFragmentManager());
		pager = (AnySwipeableViewPager) findViewById(R.id.rrd_start_pager);
		pager.setSwipeable(false);
		pager.setPageMargin(20);
		pager.setPageTransformer(true, new DepthPageTransformer());
		pager.setOffscreenPageLimit(startSectionsPagerAdapter.getCount());
		pager.setAdapter(startSectionsPagerAdapter);

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
		case R.id.im_btn_tab_documento:
			setPagerPosition(0);
			break;
		case R.id.im_btn_tab_informacoes:
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
			imBtnTabDocument.setSelected(true);
			imBtnTabInformations.setSelected(false);

			break;

		case 1:
			imBtnTabDocument.setSelected(false);
			imBtnTabInformations.setSelected(true);

			break;

		}
	}

	private void initializedView() {
		imBtnTabInformations = (ImageView) findViewById(R.id.im_btn_tab_informacoes);
		imBtnTabDocument = (ImageView) findViewById(R.id.im_btn_tab_documento);

		imBtnTabInformations.setOnClickListener(this);
		imBtnTabDocument.setOnClickListener(this);

		imBtnBack = (ImageView) findViewById(R.id.im_btn_back);
		imBtnBack.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        return !(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0);
    }
}
