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
    private OnSubscriptionSelectedListener mListener;
    private boolean mIsSubscriptionExist;

    public SkuAdapter(OnSubscriptionSelectedListener mListener, boolean isSubscriptionExist) {
        this.mListener = mListener;
        this.mIsSubscriptionExist = isSubscriptionExist;
    }

    public void updateData(List<Sku> skuList) {
        this.mSkuList = skuList;
        notifyDataSetChanged();
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

        SkuViewHolder(@NonNull View itemView) {
            super(itemView);
            subsText = itemView.findViewById(R.id.subs_text);
            subsBtn = itemView.findViewById(R.id.subs_btn);

        }

        void bind(Sku sku) {
            String text = sku.getTitle() + " / " + sku.getPrice();
            subsText.setText(text);

            if (sku.isSubscribed())
                subsBtn.setText(R.string.subs_btn_text_own);
            else {
                @StringRes int btnText = mIsSubscriptionExist ? R.string.subs_btn_text_change : R.string.subs_btn_text_buy;
                subsBtn.setText(btnText);
            }
            subsBtn.setOnClickListener(view -> mListener.onSubscriptionSelected(sku));
        }
    }

    public interface OnSubscriptionSelectedListener {
        void onSubscriptionSelected(Sku selectedSku);
    }
}
