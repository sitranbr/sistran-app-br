package net.sistransito.mobile.tav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import net.sistransito.mobile.adapter.TavStructureAccessoriesSectionsPagerAdapter;
import net.sistransito.mobile.viewpager.AnySwipeableViewPager;
import net.sistransito.mobile.viewpager.DepthPageTransformer;
import net.sistransito.mobile.viewpager.FixedSpeedScroller;
import net.sistransito.R;

import java.lang.reflect.Field;

public class TavVehicleFragment extends Fragment implements
		OnClickListener {
	private View view;
	private AnySwipeableViewPager pager;
	private TavStructureAccessoriesSectionsPagerAdapter pagerAdapter;

	private Button btnTavStructure, btnTavAccessories;
	private Field mScroller;
	private FixedSpeedScroller scroller;

	public static TavVehicleFragment newInstance() {
		return new TavVehicleFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.tav_vehicle_main_fragment, null, false);

		initializedView();
		return view;
	}

	private void initializedView() {
		setviewPager();
		btnTavAccessories = (Button) view
				.findViewById(R.id.btn_tav_acessorios);
		btnTavStructure = (Button) view.findViewById(R.id.btn_tav_estrutura);
		addListener();

	}

	private void setviewPager() {
		pagerAdapter = new TavStructureAccessoriesSectionsPagerAdapter(
				getChildFragmentManager());
		pager = (AnySwipeableViewPager) view.findViewById(R.id.vp_tav_pager);
		pager.setSwipeable(false);
		pager.setPageMargin(20);
		pager.setPageTransformer(true, new DepthPageTransformer());
		pager.setOffscreenPageLimit(pagerAdapter.getCount());
		pager.setAdapter(pagerAdapter);

		try {
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			scroller = new FixedSpeedScroller(getActivity());
			scroller.setFixedDuration(2000);
			mScroller.set(pager, scroller);

		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}

	}

	private void addListener() {
		btnTavAccessories.setOnClickListener(this);
		btnTavStructure.setOnClickListener(this);
		btnTavStructure.performClick();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tav_acessorios:
			btnTavAccessories.setEnabled(false);
			btnTavStructure.setEnabled(true);
			pager.setCurrentItem(1);
			break;

		case R.id.btn_tav_estrutura:
			btnTavAccessories.setEnabled(true);
			btnTavStructure.setEnabled(false);
			pager.setCurrentItem(0);
			break;
		}
	}

}
