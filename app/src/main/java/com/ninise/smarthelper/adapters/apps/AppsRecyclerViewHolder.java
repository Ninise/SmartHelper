package com.ninise.smarthelper.adapters.apps;

import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninise.smarthelper.R2;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;
import com.ninise.smarthelper.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Nikitin Nikita
 */

public class AppsRecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R2.id.appsItemView) View mView;
    @BindView(R2.id.appsItemImageView) ImageView mImageView;
    @BindView(R2.id.appsItemTextView) TextView mTextView;

    public AppsRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(ResolveInfo ri, int pos, IRecyclerItemClickListener<ResolveInfo> listener) {
        Utils.getInstance().parseResolveInfoIcon(ri).into(mImageView);
        mTextView.setText(Utils.getInstance().parseResolveInfoLabel(ri));
        mView.setOnClickListener(v -> listener.onClick(ri, pos));
    }
}
