package com.kingcorp.tv_app.domain.repository;

import com.kingcorp.tv_app.domain.entity.Channels;

import retrofit2.Call;

public interface ChannelRepository {
    Call<Channels> loadChannels(String region);
}
