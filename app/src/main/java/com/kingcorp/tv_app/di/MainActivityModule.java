package com.kingcorp.tv_app.di;

import com.kingcorp.tv_app.data.api.ChannelApi;
import com.kingcorp.tv_app.data.repository.ChannelRepositoryImpl;
import com.kingcorp.tv_app.domain.converters.ChannelsResponseConverter;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainActivityModule {

    @Provides
    @MainActivityScope
    ChannelApi provideChannelApi(){
        return new Retrofit.Builder()
                .baseUrl("http://iptv.denms.ru/") //TODO: CHANGE BASE URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChannelApi.class);
    }

    @Provides
    @MainActivityScope
    ChannelsResponseConverter provideChannelsResponseConverter(){
        return new ChannelsResponseConverter();
    }

    @Provides
    @MainActivityScope
    ChannelRepository provideChannelRepository(ChannelApi channelApi,
                                               ChannelsResponseConverter converter){
        return new ChannelRepositoryImpl(channelApi, converter);
    }

}
