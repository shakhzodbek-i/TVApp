package com.kingcorp.tv_app.data.repository;

import android.support.annotation.NonNull;

import com.kingcorp.tv_app.data.api.ChannelApi;
import com.kingcorp.tv_app.domain.entity.Channels;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;

import io.reactivex.Observable;


public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelApi mChannelApi;

    public ChannelRepositoryImpl(@NonNull ChannelApi channelApi) {
        this.mChannelApi = channelApi;
    }

    @Override
    public Observable<Channels> loadChannels(String region) {
        return mChannelApi.loadChannelsList(region);
    }

}
