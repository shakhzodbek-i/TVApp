package com.kingcorp.tv_app.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.domain.entity.Sku;

import java.util.List;

public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.SkuViewHolder> {
    private List<Sku> mSkuList;
    private OnSubscriptionListener mListener;
    private boolean mIsSubscriptionExist;

    public SkuAdapter(OnSubscriptionListener mListener, boolean isSubscriptionExist, List<Sku> skuList) {
        this.mSkuList = skuList;
        this.mListener = mListener;
        this.mIsSubscriptionExist = isSubscriptionExist;
    }

    @NonNull
    @Override
    public SkuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sku_row, viewGroup, false);
        return new SkuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkuViewHolder skuViewHolder, int i) {
        Sku sku = mSkuList.get(i);
        skuViewHolder.bind(sku);
    }

    @Override
    public int getItemCount() {
        return mSkuList.size();
    }

    class SkuViewHolder extends RecyclerView.ViewHolder {

        TextView subsText;
        Button subsBtn;
        TextView subsPrice;
        TextView subsCancel;

        SkuViewHolder(@NonNull View itemView) {
            super(itemView);
            subsText = itemView.findViewById(R.id.subs_text);
            subsBtn = itemView.findViewById(R.id.subs_btn);
            subsPrice = itemView.findViewById(R.id.subs_price);
            subsCancel = itemView.findViewById(R.id.subs_cancel);
        }

        void bind(Sku sku) {
            subsText.setText(sku.getTitle());
            subsPrice.setText(sku.getPrice());

            if (sku.isSubscribed()) {
                subsBtn.setText(R.string.subs_btn_text_own);
                subsCancel.setVisibility(View.VISIBLE);
            }
            else {
                @StringRes int btnText = mIsSubscriptionExist ? R.string.subs_btn_text_change : R.string.subs_btn_text_buy;
                subsBtn.setText(btnText);
            }
            subsBtn.setOnClickListener(view -> mListener.onSubscriptionBuy(sku));
            subsCancel.setOnClickListener(view -> mListener.onSubscriptionCancel(sku));
        }
    }

    public interface OnSubscriptionListener {
        void onSubscriptionBuy(Sku selectedSku);

        void onSubscriptionCancel(Sku selectedSku);
    }
}
