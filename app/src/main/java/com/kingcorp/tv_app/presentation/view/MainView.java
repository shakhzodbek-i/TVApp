package com.kingcorp.tv_app.presentation.view;

import com.kingcorp.tv_app.domain.entity.Channel;

import java.util.ArrayList;
import java.util.List;

public interface MainView {
    void showChannels(List<Channel> channels);

    void openChannel(Channel channel, ArrayList<Channel> channels);

    void showProgressBar();

    void hideProgressBar();

    void showMessage(String msg);

    void onBillingManagerSetupFinished();

    void refreshAdsState(boolean adsState);

    void showNoInternetConnection();
}
