package com.kingcorp.tv_app.data.repository;

import android.support.annotation.NonNull;

import com.kingcorp.tv_app.data.api.ChannelApi;
import com.kingcorp.tv_app.domain.converters.ChannelsResponseConverter;
import com.kingcorp.tv_app.domain.entity.ChannelEntity;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelRepositoryImpl implements ChannelRepository {

    private final ChannelApi mChannelApi;
    private final ChannelsResponseConverter mConverter;
    private List<ChannelEntity> channels;
    private String resp;

    public ChannelRepositoryImpl(@NonNull ChannelApi channelApi,
                                 @NonNull ChannelsResponseConverter converter) {
        this.mChannelApi = channelApi;
        this.mConverter = converter;
    }

    @Override
    public void loadChannels() {
        mChannelApi.downloadChannelList()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
