package com.jthou.pro.crazy;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.List;

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
public class LeftAdapter extends RecyclerView.Adapter<VerticalStockActivity.LeftViewHolder> {

    private Context mContext;
    private List<String> mData;

    public LeftAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public VerticalStockActivity.LeftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(45));
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        return new VerticalStockActivity.LeftViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalStockActivity.LeftViewHolder holder, int position) {
        if (!(holder.itemView instanceof TextView)) {
            return;
        }
        TextView textView = (TextView) holder.itemView;
        textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
