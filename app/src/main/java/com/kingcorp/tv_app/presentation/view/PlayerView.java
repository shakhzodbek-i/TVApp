package com.kingcorp.tv_app.presentation.view;

import android.media.AudioManager;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

public interface PlayerView {
    void setPlayButtonIcon(boolean isPaused);

    void setMuteButtonIcon(boolean isMute);

    void showProgressBar();

    void hideProgressBar();

    void showNoInternetConnection(ChannelEntity currentChannel);

    void setChannelMetadata(ChannelEntity entity);

    AudioManager getAudioManager();
}
