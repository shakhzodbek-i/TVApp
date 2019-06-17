package com.kingcorp.tv_app.presentation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.SkuDetails;
import com.kingcorp.tv_app.R;
import com.kingcorp.tv_app.data.billing.BillingConstants;
import com.kingcorp.tv_app.data.billing.BillingProvider;
import com.kingcorp.tv_app.data.utility.Constants;
import com.kingcorp.tv_app.domain.entity.Sku;
import com.kingcorp.tv_app.presentation.adapters.SkuAdapter;

import java.util.ArrayList;
import java.util.List;

public class BillingDialog extends DialogFragment implements SkuAdapter.OnSubscriptionSelectedListener {

    private RecyclerView mRecyclerView;
    private SkuAdapter mAdapter;
    private BillingProvider mBillingProvider;
    private String mCurrentSubs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentSubs = getArguments().getString(Constants.CURRENT_SUBS_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.billing_dialog, container, false);
        mRecyclerView = root.findViewById(R.id.sku_recycler);

        if (mBillingProvider != null) {
            querySkuDetails();
        }
        return root;
    }

    private void querySkuDetails() {
        boolean isCurrentSubsExist = mCurrentSubs == null;

        mAdapter = new SkuAdapter( this, isCurrentSubsExist);

        if (getActivity() != null && !getActivity().isFinishing()) {
            final List<Sku> dataList = new ArrayList<>();
            List<String> subscriptionsSkus = BillingConstants.getSkuList();
            addSkuRows(dataList, subscriptionsSkus);
        }
    }

    public void onManagerReady(BillingProvider billingProvider) {
        mBillingProvider = billingProvider;
        if (mRecyclerView != null) {
            querySkuDetails();
        }
    }

    private void addSkuRows(List<Sku> dataList, List<String> skusList) {
        mBillingProvider.getBillingManager()
                .querySkuDetailsAsync(BillingClient.SkuType.SUBS, skusList,
                        (responseCode, skuDetailsList) -> {
                            if (responseCode != BillingClient.BillingResponse.OK) {
                                Log.w("BILLING DIALOG", "Unsuccessful query for type: " + BillingClient.SkuType.SUBS
                                        + ". Error code: " + responseCode);
                            } else if (skuDetailsList != null && skuDetailsList.size() > 0) {

                                for (SkuDetails details : skuDetailsList) {
                                    Sku sku = new Sku(details);
                                    sku.setSubscribed(details.getSku().equals(mCurrentSubs));
                                    dataList.add(sku);
                                }

                                if (dataList.size() != 0) {
                                    if (mRecyclerView.getAdapter() == null) {
                                        mRecyclerView.setAdapter(mAdapter);
                                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    }
                                    mAdapter.updateData(dataList);
                                }

                            }
                        });
    }

    @Override
    public void onSubscriptionSelected(Sku data) {
        if (data != null) {
            if (mBillingProvider.isSubscribed()) {
                ArrayList<String> currentSubscriptionSku = new ArrayList<>();
                currentSubscriptionSku.add(mCurrentSubs);
                mBillingProvider.getBillingManager().initiatePurchaseFlow(data.getSku(),
                        currentSubscriptionSku, BillingClient.SkuType.SUBS);
            } else {
                mBillingProvider.getBillingManager().initiatePurchaseFlow(data.getSku(),
                        BillingClient.SkuType.SUBS);
            }
        }
    }
}
