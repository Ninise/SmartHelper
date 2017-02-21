package com.ninise.smarthelper.view.apps;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.R2;
import com.ninise.smarthelper.adapters.apps.AppsRecyclerAdapter;
import com.ninise.smarthelper.base.BaseFragment;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Nikitin Nikita
 */

public class AppsFragment extends BaseFragment implements IAppsView {

    private final String TAG = AppsFragment.class.getSimpleName();

    @BindView(R2.id.appsToolbar) Toolbar mToolbar;
    @BindView(R2.id.appsRecyclerView) RecyclerView mRecyclerView;

    private IAppsPresenter mPresenter;
    private IRecyclerItemClickListener<ResolveInfo> mListener = ((model, pos) -> {

    });

    public static AppsFragment newInstance() {
        AppsFragment fragment = new AppsFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AppsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apps_fragment, container, false);

        ButterKnife.bind(this, view);

        tuneToolbar(mToolbar, R.drawable.ic_action_keyboard_arrow_left,
                R.string.apps, view1 -> getActivity().onBackPressed());

        mPresenter.onCreateAdapter(mListener);
        tuneRecyclerView(mRecyclerView);

        return view;
    }

    @Override
    public void onSetRecyclerAdapter(AppsRecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
