package com.kingcorp.tv_app.presentation.presenter;

import android.support.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.kingcorp.tv_app.data.billing.BillingConstants;
import com.kingcorp.tv_app.data.billing.BillingManager;
import com.kingcorp.tv_app.data.utility.Constants;
import com.kingcorp.tv_app.data.utility.SharedPreferencesHelper;
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

    private boolean mMonthly;
    private boolean mThreeMonth;
    private boolean mSixMonth;
    private boolean mYearly;

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


    public class UpdateListener implements BillingManager.BillingUpdatesListener {
        @Override
        public void onBillingClientSetupFinished() {
            mView.onBillingManagerSetupFinished();
        }

        @Override
        public void onConsumeFinished(String token, @BillingClient.BillingResponse int result) {
        }

        @Override
        public void onPurchasesUpdated(List<Purchase> purchaseList) {
            mMonthly = false;
            mThreeMonth = false;
            mSixMonth = false;
            mYearly = false;

            for (Purchase purchase : purchaseList) {
                switch (purchase.getSku()) {
                    case BillingConstants.SKU_MONTHLY:
                        mMonthly = true;
                        break;
                    case BillingConstants.SKU_THREE_MONTH:
                        mThreeMonth = true;
                        break;
                    case BillingConstants.SKU_SIX_MONTH:
                        mSixMonth = true;
                        break;
                    case BillingConstants.SKU_YEARLY:
                        mYearly = true;
                        break;
                }
            }

            if(mMonthly || mThreeMonth || mSixMonth || mYearly)
                mSharedPreferencesHelper.saveBoolean(false, Constants.AD_STATE_KEY);
            else
                mSharedPreferencesHelper.saveBoolean(true, Constants.AD_STATE_KEY);

            mView.refreshAdsState(mMonthly || mThreeMonth || mSixMonth || mYearly);
        }
    }

    @Override
    public boolean isSubscribed() {
        return mMonthly || mThreeMonth || mSixMonth || mYearly;
    }

    @Override
    public UpdateListener getUpdateListener() {
        return new UpdateListener();
    }
}
