package com.kingcorp.tv_app.presentation.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.data.billing.BillingManager;
import com.kingcorp.tv_app.data.billing.BillingProvider;
import com.kingcorp.tv_app.data.utility.Constants;
import com.kingcorp.tv_app.data.utility.SharedPreferencesHelper;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;
import com.kingcorp.tv_app.presentation.adapters.ChannelsAdapter;
import com.kingcorp.tv_app.presentation.adapters.RegionSpinnerAdapter;
import com.kingcorp.tv_app.presentation.presenter.MainPresenter;
import com.kingcorp.tv_app.presentation.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.kingcorp.tv_app.data.billing.BillingManager.BILLING_MANAGER_NOT_INITIALIZED;

public class MainActivity extends AppCompatActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener, BillingProvider {

    private static final String DIALOG_TAG = "dialog";

    private RecyclerView mChannelsRecyclerView;
    private MainPresenter mPresenter;
    private ChannelsAdapter mAdapter;
    private ProgressBar mProgressBar;
    private AdView mAdViewBanner;
    private Spinner mRegionSpinner;
    private InterstitialAd mInterstitialAd;

    private BillingManager mBillingManager;
    private BillingDialog mBillingDialog;
    private boolean mIsAdShow;
    private String mCurrentSubs;

    @Inject
    ChannelRepository mRepository;

    @Inject
    SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        setContentView(R.layout.activity_main);

        mIsAdShow = mSharedPreferencesHelper.getBoolean(Constants.AD_STATE_KEY, true);
        mCurrentSubs = mSharedPreferencesHelper.getString(Constants.CURRENT_SUBS_KEY);
        initView();

        mPresenter = new MainPresenterImpl(this, mRepository, mSharedPreferencesHelper);

        if (savedInstanceState != null) {
            mBillingDialog = (BillingDialog) getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        }

        mBillingManager = new BillingManager(this, mPresenter.getUpdateListener());

    }

    @Override
    public void showChannels(List<Channel> channels) {

        mAdapter = new ChannelsAdapter(channels, mPresenter);

        mChannelsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void openChannel(Channel channel, ArrayList<Channel> channelList) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putParcelableArrayListExtra(Constants.CHANNEL_LIST_EXTRA_KEY, channelList);
        intent.putExtra(Constants.CHANNEL_INDEX_EXTRA_KEY, channelList.indexOf(channel));

        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mChannelsRecyclerView = findViewById(R.id.tvList);

        mProgressBar = findViewById(R.id.main_progress);

        mChannelsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mRegionSpinner = findViewById(R.id.region_spinner);

        RegionSpinnerAdapter adapter = new RegionSpinnerAdapter(
                this,
                R.layout.item_region_spinner_main,
                getResources().getStringArray(R.array.locations)
        );

        mRegionSpinner.setAdapter(adapter);

        mRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.onRegionChanged(mRegionSpinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        initAd();
    }

    private void initAd() {
        if (mIsAdShow) {
            mAdViewBanner = findViewById(R.id.adView);
            mAdViewBanner.setVisibility(View.VISIBLE);
            mAdViewBanner.loadAd(new AdRequest.Builder().build());
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.admob_inter_unit_id));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_off_ads:
                onAdOffButtonPressed();
                break;
            case R.id.nav_rate:
                Uri appUri = Uri.parse(Constants.APP_PLAY_MARKET_URL);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, appUri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        (Build.VERSION.SDK_INT >= 21
                                ? Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                                : Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET) |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                break;
            case R.id.nav_policy:

                break;
            case R.id.nav_about:

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onAdOffButtonPressed() {

        if (mBillingDialog == null) {
            Bundle args = new Bundle();
            args.putString(Constants.CURRENT_SUBS_KEY, mCurrentSubs);

            mBillingDialog = new BillingDialog();
            mBillingDialog.setArguments(args);
        }

        if (!isBillingDialogShown()) {
            mBillingDialog.show(getSupportFragmentManager(), DIALOG_TAG);

            if (mBillingManager != null
                    && mBillingManager.getBillingClientResponseCode()
                    > BILLING_MANAGER_NOT_INITIALIZED) {
                mBillingDialog.onManagerReady(this);
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showAd(){
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("AD_TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    public void onBillingManagerSetupFinished() {
        if (mBillingDialog != null) {
            mBillingDialog.onManagerReady(this);
        }
    }

    @Override
    public BillingManager getBillingManager() {
        return mBillingManager;
    }

    @Override
    public boolean isSubscribed() {
        return mPresenter.isSubscribed();
    }

    @Override
    public boolean isAdExist() {
        return mIsAdShow;
    }

    public boolean isBillingDialogShown() {
        return mBillingDialog != null && mBillingDialog.isVisible();
    }

    @Override
    public void refreshAdsState(boolean adsState) {
        if (adsState && mAdViewBanner != null && mInterstitialAd != null) {
            mAdViewBanner.setVisibility(View.GONE);
            mInterstitialAd = null;
            mIsAdShow = false;
            recreate();
        }
    }
}
