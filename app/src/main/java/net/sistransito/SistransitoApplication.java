package net.sistransito;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

public class SistransitoApplication extends MultiDexApplication {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getAppContext() {
        return context;
    }

}
