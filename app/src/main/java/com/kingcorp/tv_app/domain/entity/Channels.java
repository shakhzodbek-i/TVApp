package com.kingcorp.tv_app.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Channels implements Parcelable
{
    @JsonProperty("channels")
    public List<Channel> channels = null;

    public Channels() {
    }

    protected Channels(Parcel in) {
        channels = in.createTypedArrayList(Channel.CREATOR);
    }

    public static final Creator<Channels> CREATOR = new Creator<Channels>() {
        @Override
        public Channels createFromParcel(Parcel in) {
            return new Channels(in);
        }

        @Override
        public Channels[] newArray(int size) {
            return new Channels[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(channels);
    }
}
