package com.kingcorp.tv_app.di;

import com.kingcorp.tv_app.data.api.ChannelApi;
import com.kingcorp.tv_app.data.repository.ChannelRepositoryImpl;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class MainActivityModule {

    @Provides
    @MainActivityScope
    ChannelApi provideChannelApi(){
        return new Retrofit.Builder()
                .baseUrl("http://wwiptv.ru")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ChannelApi.class);
    }

    @Provides
    @MainActivityScope
    ChannelRepository provideChannelRepository(ChannelApi channelApi){
        return new ChannelRepositoryImpl(channelApi);
    }

}
