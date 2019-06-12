package com.kingcorp.tv_app.presentation.listener;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

public interface MainActivityListener {
    void onChannelClick(ChannelEntity currentChannel);
}
