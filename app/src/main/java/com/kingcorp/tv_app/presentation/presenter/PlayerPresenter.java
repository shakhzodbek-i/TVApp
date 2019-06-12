package com.kingcorp.tv_app.presentation.presenter;

import com.kingcorp.tv_app.presentation.listeners.PlayerListener;

public interface PlayerPresenter extends PlayerListener {
    void playAndPauseMediaPlayer();

    void changeChannel(String url);

    void setMute(boolean isMute);

    void loadMediaPlayer();

    void releaseMediaPlayer();

    void restart();

    void stop();
}
