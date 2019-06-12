package com.kingcorp.tv_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelEntity {
    @JsonProperty("id") int id;
    @JsonProperty("name") String name;
    @JsonProperty("icon") String url;
    @JsonProperty("link") String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
