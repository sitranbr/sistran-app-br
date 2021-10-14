package net.sistransito.mobile.tav;

import android.content.Context;

import net.sistransito.R;

import java.util.Arrays;
import java.util.List;

public class FilterName {

	private List<String> listFullName;
	private List<String> listShortName;

	public FilterName(Context context) {

		listFullName = Arrays.asList(context.getResources().getStringArray(
				R.array.filter_full_name));

		listShortName = Arrays.asList(context.getResources().getStringArray(
				R.array.filter_short_name));

	}

	public String filter(String filterData) {
		int value = listFullName.indexOf(filterData);
		if (value != -1) {
			return listShortName.get(value);
		} else
			return filterData;
	}
}
