package com.kingcorp.tv_app.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Channels implements Parcelable
{
    @JsonProperty("channels")
    public List<Channel> channels = null;
    public final static Parcelable.Creator<Channels> CREATOR = new Creator<Channels>() {

        public Channels createFromParcel(Parcel in) {
            return new Channels(in);
        }

        public Channels[] newArray(int size) {
            return (new Channels[size]);
        }

    }
            ;

    protected Channels(Parcel in) {
        in.readList(this.channels, (Channel.class.getClassLoader()));
    }

    public Channels() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(channels);
    }

    public int describeContents() {
        return 0;
    }

}
