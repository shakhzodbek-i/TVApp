package com.kingcorp.tv_app.domain.entity;

import com.android.billingclient.api.SkuDetails;

public class Sku {

    private String sku, title, price, description;
    private boolean isSubscribed = false;

    public Sku(SkuDetails details) {
        this.sku = details.getSku();
        this.title = details.getTitle();
        this.price = details.getPrice();
        this.description = details.getDescription();
    }

    public String getSku() {
        return sku;
    }


    public String getTitle() {
        return title;
    }


    public String getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
