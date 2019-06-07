package com.kingcorp.tv_app.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

public interface ChannelApi {
    @POST("?toall")
    @Streaming
    Call<ResponseBody> downloadChannelList();
}
