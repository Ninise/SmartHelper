package com.ninise.smarthelper.view.draws;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.adapters.GenericRecyclerAdapter;
import com.ninise.smarthelper.base.BaseFragment;
import com.ninise.smarthelper.db.RealmWorker;
import com.ninise.smarthelper.model.UserCaptureModel;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class DrawsFragment extends BaseFragment {

    @BindView(R.id.drawsToolbar) Toolbar mToolbar;
    @BindView(R.id.drawsRecyclerView) RecyclerView mRecyclerView;

    private IRecyclerItemClickListener<UserCaptureModel> itemClickListener = (model, pos) -> {

    };

    public static DrawsFragment newInstance() {
        DrawsFragment fragment = new DrawsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draws_fragment, container, false);

        ButterKnife.bind(this, view);

        tuneToolbar(mToolbar, R.drawable.ic_action_keyboard_arrow_left, R.string.draws, v -> getActivity().onBackPressed());
        tuneRecyclerView(mRecyclerView);

        List<UserCaptureModel> models = RealmWorker.getInstance().<UserCaptureModel>readAllItems();

        GenericRecyclerAdapter<UserCaptureModel> adapter =
                new GenericRecyclerAdapter<>(models, itemClickListener, GenericRecyclerAdapter.DRAWS);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
