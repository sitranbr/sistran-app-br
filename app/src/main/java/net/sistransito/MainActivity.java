package net.sistransito;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.rey.material.widget.ProgressView;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.login.ActivityLogin;
import net.sistransito.mobile.main.InstallTask;
import net.sistransito.mobile.main.MainUserActivity;

public class MainActivity extends AppCompatActivity {
    // second test
    private LinearLayout linearLayout;
    private InstallTask installTask;
    private ProgressView mProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializedView();
        startViewAnimation();
        getInstallTask();
    }

    private void initializedView() {
        mProgressBar = (ProgressView) findViewById(R.id.progressBar);
    }

    private void startViewAnimation() {
        linearLayout = (LinearLayout) findViewById(R.id.linear_layour);
        linearLayout.startAnimation(AppObject
                .getSlideAndScaleAnimition(this));
    }

    public void updateProgress(Integer integer) {
        mProgressBar.setProgress(integer);

    }

    @SuppressWarnings("deprecation")
    private void getInstallTask() {
        installTask = (InstallTask) this.getLastNonConfigurationInstance();
        if (installTask == null) {
            installTask = new InstallTask(MainActivity.this);
            installTask.execute();
        } else {
            mProgressBar.setProgress(100);
        }
    }

    public void installWorkComplete() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppObject.getTinyDB(MainActivity.this).getBoolean(AppConstants.isLogin, false)) {
                    mProgressBar.stop();
                    finish();
                    startActivity(new Intent(MainActivity.this, MainUserActivity.class));

                } else {
                    mProgressBar.stop();
                    finish();
                    startActivity(new Intent(MainActivity.this, ActivityLogin.class));
                }
            }
        }, 3000);
    }
}
