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
import com.kingcorp.tv_app.domain.entity.ChannelEntity;

import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder> {

    private List<ChannelEntity> mChannels;
    private View.OnClickListener mListener;

    public ChannelsAdapter(List<ChannelEntity> channels, View.OnClickListener listener) {
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
        ChannelEntity channel = mChannels.get(i);
        viewHolder.bind(channel.getName(), channel.getUrl(), mListener);
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

        void bind(String name, String imgUrl, View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
            channelName.setText(name);

            Glide.with(itemView)
                    .load(imgUrl)
                    .into(channelImg);
        }
    }
}
