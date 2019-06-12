package com.kingcorp.tv_app.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelEntity implements Parcelable {
    @JsonProperty("id") int id;
    @JsonProperty("name") String name;
    @JsonProperty("icon") String url;
    @JsonProperty("link") String img;

    protected ChannelEntity(Parcel in) {
        name = in.readString();
        url = in.readString();
        img = in.readString();
    }

    public static final Creator<ChannelEntity> CREATOR = new Creator<ChannelEntity>() {
        @Override
        public ChannelEntity createFromParcel(Parcel in) {
            return new ChannelEntity(in);
        }

        @Override
        public ChannelEntity[] newArray(int size) {
            return new ChannelEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(img);
    }
}
