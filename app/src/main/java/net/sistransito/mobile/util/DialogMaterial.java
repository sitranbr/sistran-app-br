package net.sistransito.mobile.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rey.material.app.BottomSheetDialog;

import net.sistransito.R;

/**
 * Created by GaziRimon on 8/22/2015.
 */
public class DialogMaterial {

    private static BottomSheetDialog bottomSheetDialog;
    private static TextView tvMgs;

    public static BottomSheetDialog getBottomSheet(String mgs, int color, Context cnt) {
        bottomSheetDialog = new BottomSheetDialog(cnt);
        View v = LayoutInflater.from(cnt).inflate(R.layout.view_bottomsheet, null);
        tvMgs = ((TextView) v.findViewById(R.id.tv_message));
        tvMgs.setText(mgs);
        tvMgs.setTextColor(color);
        bottomSheetDialog.contentView(v);
        return bottomSheetDialog;
    }
    public static void setMgs(String mgs) {
        tvMgs.setText(mgs);
    }

}