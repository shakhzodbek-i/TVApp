package com.kingcorp.tv_app.domain.repository;

import com.kingcorp.tv_app.domain.entity.Channels;

import io.reactivex.Observable;

public interface ChannelRepository {
    Observable<Channels> loadChannels(String region);
}
