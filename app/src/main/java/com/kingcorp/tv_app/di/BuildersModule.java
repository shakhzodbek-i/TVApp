package com.kingcorp.tv_app.di;

import com.kingcorp.tv_app.presentation.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Привязывает все субкомпоненты внутри приложения
 */
@Module
public abstract class BuildersModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();


}
