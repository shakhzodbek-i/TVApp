package com.kingcorp.tv_app.data.api;

import com.kingcorp.tv_app.domain.entity.Channels;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChannelApi {
    @POST("/api.php")
    Call<Channels> loadChannelsList(@Query("lang") String lang);
}
