package com.ninise.smarthelper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikitin Nikita
 */

public class GenericRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private List<T> mDataSet = new ArrayList<>();
    private IRecyclerItemClickListener<T> mClickListener = (model, pos) -> {};

    private int type;

    public static final int DRAWS = 0x4;
    public static final int APPS = 0x8;

    public GenericRecyclerAdapter(List<T> list, IRecyclerItemClickListener<T> listener, int type) {
        mDataSet.addAll(list);
        mClickListener = listener;
        this.type = type;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;

        switch (type) {
            case DRAWS :
                baseViewHolder = new DrawsRecyclerViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item, parent, false));
                break;
            case APPS :
                baseViewHolder = new AppsRecyclerViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item, parent, false));
                break;
        }

        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.bind(mDataSet.get(position), position, mClickListener);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
