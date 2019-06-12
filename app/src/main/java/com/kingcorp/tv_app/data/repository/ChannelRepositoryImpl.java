package com.kingcorp.tv_app.data.repository;

import android.support.annotation.NonNull;

import com.kingcorp.tv_app.data.api.ChannelApi;
import com.kingcorp.tv_app.domain.entity.ChannelEntity;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;

import java.util.List;

import io.reactivex.Single;

public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelApi mChannelApi;
    private List<ChannelEntity> mChannels;

    public ChannelRepositoryImpl(@NonNull ChannelApi channelApi) {
        this.mChannelApi = channelApi;
    }

    @Override
    public Single<List<ChannelEntity>> loadChannels(String region) {
        return mChannelApi.loadChannelsList(region).map(this::setChannels);
    }

    private List<ChannelEntity> setChannels(List<ChannelEntity> channels){
        this.mChannels = channels;

        return mChannels;
    }
}
