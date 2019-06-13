package com.kingcorp.tv_app.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.domain.entity.Channel;
import com.kingcorp.tv_app.presentation.listener.MainActivityListener;

import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder> {

    private List<Channel> mChannels;
    private MainActivityListener mListener;

    public ChannelsAdapter(List<Channel> channels, MainActivityListener listener) {
        this.mChannels = channels;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_tv_channel, viewGroup, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder viewHolder, int i) {
        Channel channel = mChannels.get(i);
        viewHolder.bind(channel, mListener);
    }

    @Override
    public int getItemCount() {
        return mChannels.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{
        private ImageView channelImg;
        private TextView channelName;

        ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            channelName = itemView.findViewById(R.id.channel_name);
            channelImg = itemView.findViewById(R.id.channel_img);
        }

        void bind(Channel channel, MainActivityListener listener) {
            itemView.setOnClickListener(view -> listener.onChannelClick(channel));
            channelName.setText(channel.getName());

            Glide.with(itemView)
                    .load(channel.getLink())
                    .into(channelImg);
        }
    }
}
