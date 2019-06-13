package com.kingcorp.tv_app.data.api;

import com.kingcorp.tv_app.domain.entity.Channels;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChannelApi {
    @POST("/api.php")
    Observable<Channels> loadChannelsList(@Query("lang") String lang);
}
