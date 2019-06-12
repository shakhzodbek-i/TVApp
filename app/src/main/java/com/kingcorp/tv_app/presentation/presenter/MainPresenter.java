package com.kingcorp.tv_app.presentation.presenter;

import com.kingcorp.tv_app.presentation.listener.MainActivityListener;

public interface MainPresenter extends MainActivityListener {
    void loadChannels();
}
