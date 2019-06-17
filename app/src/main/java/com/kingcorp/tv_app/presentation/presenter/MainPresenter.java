package com.kingcorp.tv_app.presentation.presenter;


import com.kingcorp.tv_app.presentation.listeners.MainActivityListener;

public interface MainPresenter extends MainActivityListener {
    void loadChannels();

    boolean isSubscribed();

    MainPresenterImpl.UpdateListener getUpdateListener();
}
