package com.kingcorp.tv_app.presentation.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.data.Constants;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.presentation.presenter.PlayerPresenter;
import com.kingcorp.tv_app.presentation.presenter.PlayerPresenterImpl;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements PlayerView{

    private SurfaceView mSurfaceView;
    private ProgressBar mProgressBar;
    private ImageView mChannelIcon;
    private TextView mChannelName;

    private ConstraintLayout mControlPanel;
    private FrameLayout mVideoContainer;
    private ImageView mNextBtn;
    private ImageView mPrevBtn;
    private ImageView mPlayBtn;
    private ImageView mMuteBtn;

    private SurfaceHolder mSurfaceHolder;
    private PlayerPresenter mPresenter;
    private ArrayList<Channel> mChannelsList;
    private int mCurrentChannelIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mChannelsList = getIntent().getParcelableArrayListExtra(Constants.CHANNEL_LIST_EXTRA_KEY);
        mCurrentChannelIndex = getIntent().getIntExtra(Constants.CHANNEL_INDEX_EXTRA_KEY, -1);

        initViews();

        mPresenter = new PlayerPresenterImpl(this, mSurfaceHolder, mChannelsList, mCurrentChannelIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.restart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.releaseMediaPlayer();
        mSurfaceHolder = null;
    }

    private void initViews(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSurfaceView = findViewById(R.id.player_surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mProgressBar = findViewById(R.id.progress_bar);
        mChannelIcon = findViewById(R.id.channel_img);
        mChannelName = findViewById(R.id.channel_name);
        mControlPanel = findViewById(R.id.control_panel);
        mVideoContainer = findViewById(R.id.video_container);
        mNextBtn = findViewById(R.id.next_btn);
        mPrevBtn = findViewById(R.id.prev_btn);
        mPlayBtn = findViewById(R.id.play_btn);
        mMuteBtn = findViewById(R.id.mute_btn);

        mNextBtn.setOnClickListener(view -> {
            mPresenter.onControlButtonClick(view);
        });
        mPrevBtn.setOnClickListener(view -> {
            mPresenter.onControlButtonClick(view);
        });
        mPlayBtn.setOnClickListener(view -> {
            mPresenter.onControlButtonClick(view);
        });
        mMuteBtn.setOnClickListener(view -> {
            mPresenter.onControlButtonClick(view);
        });

        mVideoContainer.setOnClickListener(view -> mPresenter.onSurfaceClick(mControlPanel));

    }

    @Override
    public void setPlayButtonIcon(boolean isPaused) {
        if (isPaused)
            mPlayBtn.setImageResource(R.drawable.ic_play_btn);
        else
            mPlayBtn.setImageResource(R.drawable.ic_pause_btn);
    }

    @Override
    public void setMuteButtonIcon(boolean isMute) {
        if (isMute)
            mMuteBtn.setImageResource(R.drawable.ic_unmute_btn);
        else
            mMuteBtn.setImageResource(R.drawable.ic_mute_btn);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetConnection(Channel currentChannel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder
                .setTitle("No Internet Connection.")
                .setMessage("Application cannot connect to server, please check Internet connection and try again!")
                .setPositiveButton("Try again!", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    mPresenter.changeChannel(currentChannel.getLink());
                });
        dialogBuilder.show();
    }

    @Override
    public void setChannelMetadata(Channel entity) {
        Glide
                .with(this)
                .load(Uri.parse(entity.getIcon()))
                .placeholder(R.mipmap.ic_launcher_foreground)
                .into(mChannelIcon);

        mChannelName.setText(entity.getName());
    }

    @Override
    public AudioManager getAudioManager() {
        return (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }
}