package com.kingcorp.tv_app.presentation.view;

import android.media.AudioManager;

import com.kingcorp.tv_app.domain.entity.Channel;

public interface PlayerView {
    void setPlayButtonIcon(boolean isPaused);

    void setMuteButtonIcon(boolean isMute);

    void showProgressBar();

    void hideProgressBar();

    void showNoInternetConnection(Channel currentChannel);

    void setChannelMetadata(Channel entity);

    AudioManager getAudioManager();

    void showAd();

    boolean isAdOn();
}
