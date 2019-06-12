package com.kingcorp.tv_app.data.api;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChannelApi {
    @GET("api.php?lang={lang}")
    Single<List<ChannelEntity>> loadChannelsList(@Path("lang") String lang);
}
