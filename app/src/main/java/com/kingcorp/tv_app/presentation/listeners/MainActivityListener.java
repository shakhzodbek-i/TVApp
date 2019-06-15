package com.kingcorp.tv_app.presentation.listeners;

import com.kingcorp.tv_app.domain.entity.Channel;

public interface MainActivityListener {
    void onChannelClick(Channel currentChannel);

    void onRegionChanged(int selectedItemId);
}
