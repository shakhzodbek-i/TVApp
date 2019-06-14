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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.data.Constants;
import com.kingcorp.tv_app.data.SharedPreferencesHelper;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;
import com.kingcorp.tv_app.presentation.adapters.ChannelsAdapter;
import com.kingcorp.tv_app.presentation.presenter.MainPresenter;
import com.kingcorp.tv_app.presentation.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mChannelsRecyclerView;
    private MainPresenter mPresenter;
    private ChannelsAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Inject
    ChannelRepository mRepository;

    @Inject
    SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        setContentView(R.layout.activity_main);

        initNavigationView();

        mChannelsRecyclerView = findViewById(R.id.tvList);

        mProgressBar = findViewById(R.id.main_progress);

        mChannelsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mPresenter = new MainPresenterImpl(this, mRepository, mSharedPreferencesHelper);
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

    private void initNavigationView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                break;
            case R.id.nav_rate:
                // TODO: Add app URI
                Uri appUri = null;
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


}
