package com.kingcorp.tv_app.presentation.presenter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;

import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.presentation.view.PlayerView;

import java.io.IOException;
import java.util.List;

public class PlayerPresenterImpl
        implements PlayerPresenter, SurfaceHolder.Callback {

    private Channel mCurrentChannel;
    private int mCurrentChannelIndex;
    private List<Channel> mChannelsList;
    private MediaPlayer mPlayer;
    private SurfaceHolder mHolder;
    private PlayerView mView;
    private boolean mIsMute = false;

//    private final String testUrl = "http://persik.tv/stream/9557/401338/46.m3u8";
//    private final String testNextUrl = "http://video-4-206.rutube.ru/stream/10113616/546602986e6a424d74d594876ddb3f04/tracks-v1a1/mono.m3u8";

    public PlayerPresenterImpl(PlayerView view, SurfaceHolder holder, List<Channel> channelsList, int currentChannelIndex) {
        this.mView = view;
        this.mHolder = holder;
        this.mChannelsList = channelsList;
        this.mCurrentChannelIndex = currentChannelIndex;
        this.mCurrentChannel = mChannelsList.get(mCurrentChannelIndex);
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        loadMediaPlayer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void playAndPauseMediaPlayer() {
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
            mView.setPlayButtonIcon(false);
        }
        else {
            mPlayer.pause();
            mView.setPlayButtonIcon(true);
        }

    }

    @Override
    public void changeChannel(String url) {
        mView.showProgressBar();
        try {
            mPlayer.reset();
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMute(boolean isMute) {
        AudioManager audioManager = mView.getAudioManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (isMute) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            }
            else {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            }
        } else {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, isMute);
        }

        mIsMute = isMute;
        mView.setMuteButtonIcon(mIsMute);
    }

    @Override
    public void loadMediaPlayer() {
        if (mPlayer == null) {
            mView.showProgressBar();
            mPlayer = new MediaPlayer();
            mPlayer.setDisplay(mHolder);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setScreenOnWhilePlaying(true);
            try {
                mPlayer.setDataSource(mCurrentChannel.getLink());
                mPlayer.setOnPreparedListener(mediaPlayer -> {
                    mediaPlayer.start();
                    mView.hideProgressBar();
                });
                mPlayer.setOnInfoListener((mediaPlayer, what, extra) -> {
                    if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        mView.showProgressBar();
                    } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        mView.hideProgressBar();
                    }
                    return true;
                });
                mPlayer.setOnErrorListener((mediaPlayer, what, extra) -> {
                    if (extra == MediaPlayer.MEDIA_ERROR_IO || extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                        mediaPlayer.pause();
                        mView.showNoInternetConnection(mCurrentChannel);
                    } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                        changeChannel(mCurrentChannel.getLink());
                    }
                    return true;
                });
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void releaseMediaPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onControlButtonClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.next_btn:
                if ((mCurrentChannelIndex+1) < mChannelsList.size()){
                    mCurrentChannel = mChannelsList.get(++mCurrentChannelIndex);
                    changeChannel(mCurrentChannel.getLink());
                    mView.setChannelMetadata(mCurrentChannel);
                }
                break;
            case R.id.prev_btn:
                if ((mCurrentChannelIndex-1) >= 0){
                    mCurrentChannel = mChannelsList.get(--mCurrentChannelIndex);
                    changeChannel(mCurrentChannel.getLink());
                    mView.setChannelMetadata(mCurrentChannel);
                }
                break;
            case R.id.play_btn:
                playAndPauseMediaPlayer();
                break;
            case R.id.mute_btn:
                setMute(!mIsMute);
                break;
        }
    }

    @Override
    public void onSurfaceClick(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            controlPanelTimerHandler(view);
        }
    }

    private void controlPanelTimerHandler(View view){
        new Handler().postDelayed(() -> {
            view.setVisibility(View.GONE);
        }, 5000);
    }

    @Override
    public void restart(){
        if (mPlayer != null && !mPlayer.isPlaying()) {
            changeChannel(mCurrentChannel.getLink());
        }
    }

    @Override
    public void stop(){
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }
}
