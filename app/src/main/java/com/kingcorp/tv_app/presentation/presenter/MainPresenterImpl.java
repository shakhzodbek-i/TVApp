package com.kingcorp.tv_app.presentation.presenter;

import com.kingcorp.tv_app.data.Constants;
import com.kingcorp.tv_app.data.SharedPreferencesHelper;
import com.kingcorp.tv_app.domain.entity.ChannelEntity;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;
import com.kingcorp.tv_app.presentation.view.MainView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {

    private final MainView mView;
    private final ChannelRepository mRepository;
    private final SharedPreferencesHelper mSharedPreferencesHelper;
    private final CompositeDisposable mCompositeDisposable;

    private String mRegion;
    private List<ChannelEntity> mChannelsList;

    public MainPresenterImpl(MainView mView, ChannelRepository repository, SharedPreferencesHelper sharedPreferencesHelper) {
        this.mView = mView;
        this.mRepository = repository;
        this.mSharedPreferencesHelper = sharedPreferencesHelper;
        this.mCompositeDisposable = new CompositeDisposable();

        mRegion = mSharedPreferencesHelper.getString(Constants.REGION_PREFERENCE_KEY);
        loadChannels();
    }

    @Override
    public void loadChannels() {

        mCompositeDisposable.add(
                mRepository.loadChannels(mRegion)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    mChannelsList = response;
                                    mView.showChannels(mChannelsList);
                                },
                                throwable -> {

                                }
                        )
        );


    }

    @Override
    public void onChannelClick(ChannelEntity currentChannel) {
        mView.openChannel(currentChannel);
    }
}
