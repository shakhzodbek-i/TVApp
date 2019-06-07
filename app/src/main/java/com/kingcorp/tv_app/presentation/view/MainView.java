package com.kingcorp.tv_app.presentation.view;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

import java.util.List;

public interface MainView {
    void showChannels(List<ChannelEntity> channels);

    void openChannel(ChannelEntity channel);
}
