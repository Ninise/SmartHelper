package com.ninise.smarthelper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ninise.smarthelper.utils.IRecyclerItemClickListener;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T t, int position, IRecyclerItemClickListener<T> listener);

}
