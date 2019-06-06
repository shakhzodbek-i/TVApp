package com.kingcorp.tv_app.di;

import dagger.Module;

/**
 * Привязывает все субкомпоненты внутри приложения
 */
@Module
public abstract class BuildersModule {

    /*
        Шаблон добавления субкомпонентов
        @ActivityScope
        @ContributesAndroidInjector(module = ActivityModule.class)
        abstract Activity bindActivity();
     */

}
