package net.sistransito.mobile.tav;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.viewpagerindicator.UnderlinePageIndicator;

import net.sistransito.mobile.adapter.TabAutoSubTitleSectionsPagerAdapter;
import net.sistransito.mobile.adapter.TavStartSectionsPagerAdapter;
import net.sistransito.mobile.viewpager.AnySwipeableViewPager;
import net.sistransito.mobile.viewpager.DepthPageTransformer;
import net.sistransito.mobile.viewpager.FixedSpeedScroller;
import net.sistransito.mobile.viewpager.ZoomOutPageTransformer;
import net.sistransito.R;

import java.lang.reflect.Field;

public class TavActivity extends AppCompatActivity implements
		OnClickListener {
	private AnySwipeableViewPager pager;
	private AnySwipeableViewPager subTitlePager;
	private Field mScroller;
	private FixedSpeedScroller scroller;
	private UnderlinePageIndicator indicator;
	private ImageView imBtnTabConductor, imBtnTabVehicle,
			imBtnTabGeneration, imBtnBack;
	private TavStartSectionsPagerAdapter adapter;
	//private TAVData tavData;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.tav_main);
		getAitObject();
		initializedView();
		setMainPager();
		setSubTitlePager();
		setTitleIndicator();

	}

	private void getAitObject() {
		Bundle bundleFrom = getIntent().getExtras();
		TavObject.setTAVObject((TavData) bundleFrom
				.getSerializable(TavData.getTavId()));
		//tavData = TAVObject.getTAVObject();
		
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
		subTitlePager = (AnySwipeableViewPager) findViewById(R.id.tav_title_pager);
		subTitlePager.setSwipeable(false);
		subTitlePager.setAdapter(new TabAutoSubTitleSectionsPagerAdapter(
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
		adapter = new TavStartSectionsPagerAdapter(
				getSupportFragmentManager());
		pager = (AnySwipeableViewPager) findViewById(R.id.tav_start_pager);
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
		case R.id.im_btn_tav_condutor:
			setPagerPosition(0);
			break;
		case R.id.im_btn_tav_veiculo:
			setPagerPosition(1);
			break;
		case R.id.im_btn_tav_gerar:
			setPagerPosition(2);
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
			imBtnTabVehicle.setSelected(false);
			imBtnTabGeneration.setSelected(false);

			break;

		case 1:
			imBtnTabConductor.setSelected(false);
			imBtnTabVehicle.setSelected(true);
			imBtnTabGeneration.setSelected(false);

			break;
		case 2:
			imBtnTabConductor.setSelected(false);
			imBtnTabVehicle.setSelected(false);
			imBtnTabGeneration.setSelected(true);
			break;
		}
	}

	private void initializedView() {
		imBtnTabVehicle = (ImageView) findViewById(R.id.im_btn_tav_veiculo);
		imBtnTabConductor = (ImageView) findViewById(R.id.im_btn_tav_condutor);
		imBtnTabGeneration = (ImageView) findViewById(R.id.im_btn_tav_gerar);
		imBtnTabVehicle.setOnClickListener(this);
		imBtnTabConductor.setOnClickListener(this);
		imBtnTabGeneration.setOnClickListener(this);
		imBtnBack = (ImageView) findViewById(R.id.im_btn_back);
		imBtnBack.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        return !(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() >= 0);

    }
}
