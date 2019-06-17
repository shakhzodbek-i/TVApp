package com.kingcorp.tv_app;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.kingcorp.tv_app.di.DaggerAppComponent;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(getString(R.string.appmetrica_api_key)).build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);

        MobileAds.initialize(this.getApplicationContext(), getString(R.string.admob_application_id));

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
