package com.kingcorp.tv_app.presentation.presenter;

import android.view.View;

import com.kingcorp.tv_app.domain.repository.ChannelRepository;
import com.kingcorp.tv_app.presentation.view.MainView;

public class MainPresenterImpl implements MainPresenter {

    private MainView mView;

    private ChannelRepository mRepository;

    public MainPresenterImpl(MainView mView, ChannelRepository repository) {
        this.mView = mView;
        this.mRepository = repository;

        loadChannels();
    }

    @Override
    public void loadChannels() {
        mRepository.loadChannels();
    }

    @Override
    public void onClick(View view) {

    }
}
