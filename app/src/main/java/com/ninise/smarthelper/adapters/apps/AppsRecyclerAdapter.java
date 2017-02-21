package com.ninise.smarthelper.adapters.apps;

import android.content.pm.ResolveInfo;
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

public class AppsRecyclerAdapter extends RecyclerView.Adapter<AppsRecyclerViewHolder> {

    private List<ResolveInfo> mDataSet = new ArrayList<>();
    private IRecyclerItemClickListener<ResolveInfo> mClickListener = (model, pos) -> {};

    public AppsRecyclerAdapter(List<ResolveInfo> list, IRecyclerItemClickListener<ResolveInfo> listener) {
        mDataSet.addAll(list);
        mClickListener = listener;
    }

    @Override
    public AppsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppsRecyclerViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(AppsRecyclerViewHolder holder, int position) {
        holder.bind(mDataSet.get(position), position, mClickListener);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
