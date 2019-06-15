package com.kingcorp.tv_app.presentation.presenter;

import android.support.annotation.NonNull;

import com.kingcorp.tv_app.data.Constants;
import com.kingcorp.tv_app.data.SharedPreferencesHelper;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.domain.entity.Channels;
import com.kingcorp.tv_app.domain.repository.ChannelRepository;
import com.kingcorp.tv_app.presentation.view.MainView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements MainPresenter {

    private final MainView mView;
    private final ChannelRepository mRepository;
    private final SharedPreferencesHelper mSharedPreferencesHelper;

    private String mRegion;
    private List<Channel> mChannelsList;

    public MainPresenterImpl(MainView mView, ChannelRepository repository, SharedPreferencesHelper sharedPreferencesHelper) {
        this.mView = mView;
        this.mRepository = repository;
        this.mSharedPreferencesHelper = sharedPreferencesHelper;
        loadChannels();
    }

    @Override
    public void loadChannels() {
        mView.showProgressBar();

        mRegion = mSharedPreferencesHelper.getString(Constants.REGION_PREFERENCE_KEY) == null
                ? "ru"
                : mSharedPreferencesHelper.getString(Constants.REGION_PREFERENCE_KEY);

        // TODO: DELETE AFTER TEST
        if (!mRegion.equals("ru")) {
            mView.showMessage("Selected region: " + mRegion);
            return;
        }

        mRepository.loadChannels(mRegion)
                .enqueue(new Callback<Channels>() {
                    @Override
                    public void onResponse(@NonNull Call<Channels> call, @NonNull Response<Channels> response) {
                        if (response.body() != null) {
                            mChannelsList = response.body().channels;
                        }
                        mView.showChannels(mChannelsList);
                        mView.hideProgressBar();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Channels> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onChannelClick(Channel currentChannel) {
        mView.openChannel(currentChannel, new ArrayList<>(mChannelsList));
    }

    @Override
    public void onRegionChanged(int selectedItemId) {

        switch (selectedItemId) {
            case 0:
                mRegion = "ru";
                break;
            case 1:
                mRegion = "by";
                break;
            case 2:
                mRegion = "kz";
                break;
            case 3:
                mRegion = "ua";
                break;
            case 4:
                mRegion = "uz";
                break;
            default:
                mRegion = "ru";
        }

        mSharedPreferencesHelper.saveString(mRegion, Constants.REGION_PREFERENCE_KEY);

        loadChannels();
    }


}
