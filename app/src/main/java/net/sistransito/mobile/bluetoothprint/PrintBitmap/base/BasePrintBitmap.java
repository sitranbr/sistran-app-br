package net.sistransito.mobile.bluetoothprint.PrintBitmap.base;

import android.content.Context;
import android.graphics.Bitmap;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.util.User;

/**
 * Created by Sandro A Costa on 13/09/2016.
 */
public abstract class BasePrintBitmap {
    protected Context context;
    protected User user;

    public BasePrintBitmap(Context context) {
        this.context = context;
        user = AppObject.getTinyDB(context).getObject(AppConstants.user, User.class);
    }

    public abstract Bitmap getBitmap();
}
