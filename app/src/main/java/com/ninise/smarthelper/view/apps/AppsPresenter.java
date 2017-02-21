package com.ninise.smarthelper.view.apps;

import android.content.pm.ResolveInfo;

import com.ninise.smarthelper.adapters.apps.AppsRecyclerAdapter;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;
import com.ninise.smarthelper.utils.Utils;

/**
 * @author Nikitin Nikita
 */

public class AppsPresenter implements IAppsPresenter {

    private IAppsView mView;

    public AppsPresenter(IAppsView view) {
        mView = view;
    }

    @Override
    public void onCreateAdapter(IRecyclerItemClickListener<ResolveInfo> listener) {
        mView.onSetRecyclerAdapter(new AppsRecyclerAdapter(Utils.getInstance().getApps(), listener));
    }

}
