package com.jthou.pro.crazy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jthou.pro.crazy.R;

import java.util.List;

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
class RightAdapter extends RecyclerView.Adapter<VerticalStockActivity.RightViewHolder> {

    private Context mContext;
    private List<Stock> mData;

    public RightAdapter(Context context, List<Stock> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public VerticalStockActivity.RightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VerticalStockActivity.RightViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_right, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalStockActivity.RightViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
