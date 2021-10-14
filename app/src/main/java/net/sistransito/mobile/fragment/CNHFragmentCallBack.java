package net.sistransito.mobile.fragment;


import net.sistransito.mobile.cnh.dados.DataFromCnh;

public interface CNHFragmentCallBack {

	void CallBack(DataFromCnh cnhFormat, boolean isOffline);

}
