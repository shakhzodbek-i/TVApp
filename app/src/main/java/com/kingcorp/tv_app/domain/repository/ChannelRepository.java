package com.kingcorp.tv_app.domain.repository;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

import java.util.List;

import io.reactivex.Single;

public interface ChannelRepository {
    Single<List<ChannelEntity>> loadChannels(String region);
}
