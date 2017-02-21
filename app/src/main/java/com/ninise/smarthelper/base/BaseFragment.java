package com.ninise.smarthelper.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.utils.DividerItemDecoration;

/**
 * @author Nikitin Nikita
 */

public abstract class BaseFragment extends Fragment {

    protected void tuneToolbar(Toolbar toolbar, @DrawableRes int navIcon, @StringRes int title, View.OnClickListener listener) {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        if (navIcon != 0) {
            toolbar.setNavigationIcon(navIcon);
        }

        if (title != 0) {
            toolbar.setTitle(getString(title));
        }

        if (listener != null) {
            toolbar.setNavigationOnClickListener(listener);
        }
    }

    protected void tuneRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration itemDecoration = DividerItemDecoration.newBuilder()
                .setContext(getActivity())
                .setDivider(getResources().getDrawable(R.drawable.divider_chat_list))
                .setMargin(5)
                .build();

        recyclerView.addItemDecoration(itemDecoration);
    }

}
