package net.sistransito.mobile.main;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.ait.AitActivity;
import net.sistransito.mobile.ait.AitData;
import net.sistransito.mobile.ait.table.InfrationTable;
import net.sistransito.mobile.aitcompany.AitCompanyActivity;
import net.sistransito.mobile.aitcompany.pjData;
import net.sistransito.mobile.cnh.SearchCnhFragment;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.mobile.database.sync.ImageFragment;
import net.sistransito.mobile.equipments.AitEquipmentEntryJsonTask;
import net.sistransito.mobile.http.WebClient;
import net.sistransito.mobile.login.LoginData;
import net.sistransito.mobile.number.CheckNumber;
import net.sistransito.mobile.plate.search.ConsultPlateFragment;
import net.sistransito.mobile.plate.lister.LogListFragment;
import net.sistransito.mobile.profile.ProfileFragment;
import net.sistransito.mobile.rrd.RrdActivity;
import net.sistransito.mobile.rrd.RrdData;
import net.sistransito.mobile.setting.SettingFragment;
import net.sistransito.mobile.tav.TavActivity;
import net.sistransito.mobile.tav.TavData;
import net.sistransito.mobile.tca.TcaActivity;
import net.sistransito.mobile.tca.TcaData;
import net.sistransito.mobile.util.DialogMaterial;
import net.sistransito.mobile.util.User;
import net.sistransito.R;

public class MainUserActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {
    //save our header or resul
    private Context context;
    private SQLiteDatabase database;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private SharedPreferences preferences;
    //profile
    private IProfile profile;
    private User user;
    private LoginData loginData;

    private DialogMaterial dialogMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setImageLoder();
        startMenu(savedInstanceState);
        checkFirstTimeAppSetup();
        getPermission();

        /*try {
            Log.d("Password in cash: ", dataLogin.getPassword() + " IDlogin: " + dataLogin.getIDLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }*/

    private void setImageLoder() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(500)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                // default
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(defaultOptions)
                // default
                .build();
        ImageLoader.getInstance().init(config);

    }

    private void startMenu(Bundle savedInstanceState) {
        user = AppObject.getTinyDB(this).getObject(AppConstants.user, User.class);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        this.setSupportActionBar(toolbar);

        profile = new ProfileDrawerItem().withName(user.getEmployeeName()).withEmail(user.getCompanyName());
        //create account
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState).addProfiles(profile)
                .build();

        ImageLoader.getInstance().loadImage(user.getProfileImage(),
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        profile = new ProfileDrawerItem().withName(user.getEmployeeName()).withEmail(user.getCompanyName()).withIcon(loadedImage);
                        headerResult.clear();
                        headerResult.addProfile(profile, 0);
                    }
                });

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this).withActionBarDrawerToggleAnimated(true)
                .withToolbar(toolbar).withAccountHeader(headerResult)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        KeyboardUtil.hideKeyboard(MainUserActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withFireOnInitialOnClick(true)
                .withSavedInstance(savedInstanceState)
                .build();

        //    result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName(R.string.acesso_ao_sistransito).withIcon(R.mipmap.ic_i_car_p));
        result.addItem(new PrimaryDrawerItem().withName(R.string.texto_titulo_cpf).withIcon(R.mipmap.ic_i_id_p));
        result.addItem(new PrimaryDrawerItem().withName(R.string.opcoes_log).withIcon(R.mipmap.ic_log_n));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName(R.string.tabela_infracoes).withIcon(R.mipmap.ic_infracao));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName(R.string.setting).withIcon(R.mipmap.ic_settting_nav));
        result.addItem(new PrimaryDrawerItem().withName(R.string.profile).withIcon(R.mipmap.ic_profile));
        result.addItem(new PrimaryDrawerItem().withName(R.string.ajuda_sobre_app).withIcon(R.mipmap.ic_help_nav));
        result.addItem(new DividerDrawerItem());
        result.addItem(new PrimaryDrawerItem().withName(R.string.logout).withIcon(R.mipmap.ic_logout));
        result.setOnDrawerItemClickListener(MainUserActivity.this);
        result.setSelection(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_overflow_autuar:
                AitData data = new AitData();
                data.setDataisNull(true);
                Intent intent = new Intent(this, AitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AitData.getIDAuto(), data);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_overflow_autuar_pj:
                pjData datapj = new pjData();
                datapj.setDataisPJNull(true);
                Intent intentPJ = new Intent(this, AitCompanyActivity.class);
                Bundle bundlePJ = new Bundle();
                bundlePJ.putSerializable(pjData.getIDAuto(), datapj);
                intentPJ.putExtras(bundlePJ);
                startActivity(intentPJ);
                break;
            case R.id.btn_overflow_rrd_avulso:
                RrdData dados = new RrdData();
                dados.setRrdType(getResources().getString(R.string.avulso));
                Intent intents = new Intent(this, RrdActivity.class);
                Bundle bundles = new Bundle();
                bundles.putSerializable(RrdData.getRRDId(), dados);
                intents.putExtras(bundles);
                startActivity(intents);
                break;
            case R.id.btn_overflow_tav_avulso:
                TavData dadaTav = new TavData();
                dadaTav.setTavType(getResources().getString(R.string.avulso));
                Intent intentTav = new Intent(this, TavActivity.class);
                Bundle bundleTav = new Bundle();
                bundleTav.putSerializable(TavData.getTavId(), dadaTav);
                intentTav.putExtras(bundleTav);
                startActivity(intentTav);
                break;
            case R.id.btn_overflow_tca_avulso:
                TcaData dadaTca = new TcaData();
                dadaTca.setTcaType(getResources().getString(R.string.avulso));
                Intent intentTca = new Intent(this, TcaActivity.class);
                Bundle bundleTca = new Bundle();
                bundleTca.putSerializable(TcaData.getTcaId(), dadaTca);
                intentTca.putExtras(bundleTca);
                startActivity(intentTca);
                break;
            case R.id.btn_overflow_autos_restantes:
                DialogMaterial.getBottomSheet(DatabaseCreator
                        .getBalanceDatabaseAdapter(this)
                        .getShowView(), Color.BLACK, this).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

        if (drawerItem != null && drawerItem instanceof Nameable) {
            int title = ((Nameable) drawerItem).getNameRes();
            getSupportActionBar().setTitle(title);
            switch (title) {
                case R.string.acesso_ao_sistransito:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ConsultPlateFragment.newInstance()).commit();
                    break;
                case R.string.texto_titulo_cpf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SearchCnhFragment.newInstance()).commit();
                    break;
                case R.string.opcoes_log:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LogListFragment.newInstance()).commit();
                    break;
                case R.string.tabela_infracoes:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, InfrationTable.newInstance()).commit();
                    break;
                case R.string.logout:
                    AppObject.getTinyDB(this).putBoolean(AppConstants.isLogin, false);
                    finish();
                    break;
                case R.string.setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SettingFragment.newInstance()).commit();
                    break;
                case R.string.ajuda_sobre_app:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AjudaFragment.newInstance()).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ImageFragment.newInstance()).commit();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, VolleyFragment.newInstance()).commit();
                    break;
                case R.string.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ProfileFragment.newInstance()).commit();
                    break;
            }
        }

        return false;
    }

    private void checkFirstTimeAppSetup() {
        if (!AppObject.getTinyDB(this).getBoolean("is_first_time", false)) {
            (new FirstInstall()).execute();
            AppObject.getTinyDB(this).putBoolean("is_first_time", true);
        }
    }

    private class FirstInstall extends AsyncTask<String, String, String> {
        ProgressDialog pd = new ProgressDialog(MainUserActivity.this, getResources().getString(R.string.processing));

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
            pd.setCancelable(false);
        }

        protected String doInBackground(String... params) {
            try {

                AitEquipmentEntryJsonTask
                        entryJsonTask = new AitEquipmentEntryJsonTask(AppObject.getHttpClient()
                        .executeHttpGet(WebClient.URL_EQUIPMENTS), MainUserActivity.this);

                entryJsonTask.prepareEntry();

            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNumber checkNumber = new CheckNumber(MainUserActivity.this);
            checkNumber.check();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
        }
    }

    private void getPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }

        }

    }

    public void OpenItemMenu(){

        AitData data = new AitData();
        data.setDataisNull(true);
        Intent intent = new Intent(this, AitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AitData.getIDAuto(), data);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
