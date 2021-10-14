/**
 * Load sqlite chipper library
 * initialized all database object
 * initialized all single tone object
 */

package net.sistransito.mobile.main;

import android.os.AsyncTask;

import net.sistransito.MainActivity;
import net.sistransito.SistransitoApplication;
import net.sistransito.mobile.appobject.AppObject;
import net.sqlcipher.database.SQLiteDatabase;

public class InstallTask extends AsyncTask<Void, Integer, Void> {

    private MainActivity mActivity;

    public InstallTask(MainActivity main) {
        attach(main);
    }

    void attach(MainActivity activity) {
        this.mActivity = activity;

    }

    @Override
    protected Void doInBackground(Void... arg0) {

        publishProgress(0);

        try {
            SQLiteDatabase.loadLibs(mActivity);
            publishProgress(80);
            AppObject.newInstance(SistransitoApplication.getAppContext());
        } catch (Exception e) {

        }

        publishProgress(100);
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (mActivity == null) {
        } else {
            mActivity.updateProgress(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (mActivity == null) {
        } else {
            mActivity.installWorkComplete();
        }

    }

    void detach() {
        mActivity = null;
    }
}