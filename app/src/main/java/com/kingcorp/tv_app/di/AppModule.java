package com.kingcorp.tv_app.di;

import android.content.Context;

import com.kingcorp.tv_app.App;
import com.kingcorp.tv_app.data.utility.SharedPreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context providerContext(App application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferencesHelper provideSharedPreferencesHelper(App app) {
        return new SharedPreferencesHelper(app);
    }
}
