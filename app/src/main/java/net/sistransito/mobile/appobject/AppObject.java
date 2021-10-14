package net.sistransito.mobile.appobject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import net.sistransito.SistransitoApplication;
import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.ait.lister.AitLister;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.TinyDB;
import net.sistransito.mobile.http.CustomHttpClient;
import net.sistransito.mobile.plate.lister.PlateLister;
import net.sistransito.mobile.rrd.lister.RrdLister;
import net.sistransito.mobile.tav.lister.TAVLister;
import net.sistransito.mobile.tca.lister.TcaLister;
import net.sistransito.R;

public class AppObject {
    private static Vibrator vibrator;
    private static MediaPlayer mPlayer = null;
    private static CustomHttpClient customHttpClient;
    private static Animation slideAndScaleAnimation = null;
    private static Typeface tfNormal, tfCurrent, tfTitle;
    private static TinyDB tinyDB;

    public static Intent getPlateListIntent(Context context) {
        if (plateListIntent == null)
            plateListIntent = new Intent(context, PlateLister.class);
        return plateListIntent;
    }

    public static Intent getAitListerIntent(Context context) {
        if (aitListerIntent == null)
            aitListerIntent = new Intent(context, AitLister.class);

        return aitListerIntent;
    }

    public static  Intent getRrdListerIntent(Context context) {
        if (rrdListerIntent == null)
            rrdListerIntent = new Intent(context, RrdLister.class);
        return rrdListerIntent;
    }

    public  static Intent getTcaListerIntent(Context context) {
        if (tcaListerIntent == null)
            tcaListerIntent = new Intent(context, TcaLister.class);
        return tcaListerIntent;
    }

    public static Intent getTavListerIntent(Context context) {
        if (tavListerIntent == null)
            tavListerIntent = new Intent(context, TAVLister.class);
        return tavListerIntent;
    }

    private static Intent plateListIntent, aitListerIntent, rrdListerIntent, tcaListerIntent, tavListerIntent;


    public static TinyDB getTinyDB(Context c) {
        if (tinyDB == null)
            tinyDB = new TinyDB(c);
        return tinyDB;
    }

    public static void newInstance(Context context) {
        getfont(context);
        slideAndScaleAnimation = AnimationUtils.loadAnimation(context,
                R.anim.slide_and_scale);

        customHttpClient = new CustomHttpClient();
        mPlayer = MediaPlayer.create(context, R.raw.right_answer);
        vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        tinyDB = new TinyDB(context);

        plateListIntent = new Intent(SistransitoApplication.getAppContext(), PlateLister.class);
        aitListerIntent = new Intent(SistransitoApplication.getAppContext(), AitLister.class);
        rrdListerIntent = new Intent(SistransitoApplication.getAppContext(), RrdLister.class);
        tcaListerIntent = new Intent(SistransitoApplication.getAppContext(), TcaLister.class);
        tavListerIntent = new Intent(SistransitoApplication.getAppContext(), TAVLister.class);
    }

    public static Typeface getTf_normal(Context context) {
        if (tfNormal == null)
            tfNormal = Typeface.createFromAsset(context.getAssets(),
                    "font/MuseoSans-300.ttf");
        return tfNormal;
    }

    public static Typeface getCurrentFont(Context context) {
        if (tfCurrent == null) {
            getfont(context);
        }
        return tfCurrent;
    }

    public static void getfont(Context context) {

        String font = (DatabaseCreator.getSettingDatabaseAdapter(context))
                .getFont();

        if ((font.equals(AppConstants.FONT_1))) {
            tfCurrent = Typeface.createFromAsset(context.getAssets(),
                    "font/Roboto-Regular.ttf");
        } else if ((font.equals(AppConstants.FONT_2))) {
            tfCurrent = Typeface.createFromAsset(context.getAssets(),
                    "font/Roboto-Regular.ttf");
        } else if ((font.equals(AppConstants.FONT_3))) {
            tfCurrent = Typeface.createFromAsset(context.getAssets(),
                    "font/Roboto-Regular.ttf");
        } else {
            tfCurrent = Typeface.DEFAULT;
        }
    }

    public static Typeface getTitlefont(Context context) {

        tfTitle = Typeface.createFromAsset(context.getAssets(),
                "font/Roboto-Regular.ttf");
        return tfTitle;

    }

    public static void setfont(Context context, String font) {
        (DatabaseCreator.getSettingDatabaseAdapter(context)).setFont(font);
        getfont(context);
    }

    public static Animation getSlideAndScaleAnimition(Context context) {
        if (slideAndScaleAnimation == null) {
            slideAndScaleAnimation = AnimationUtils.loadAnimation(context,
                    R.anim.slide_and_scale);
            return slideAndScaleAnimation;
        }
        return slideAndScaleAnimation;

    }

    public static CustomHttpClient getHttpClient() {
        if (customHttpClient == null) {
            customHttpClient = new CustomHttpClient();
        }
        return customHttpClient;
    }

    public static void startWarning(Context context) {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(context, R.raw.right_answer);
        }
        mPlayer.start();
    }

    public static void startVibrate(Context context) {
        vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
    }
}
