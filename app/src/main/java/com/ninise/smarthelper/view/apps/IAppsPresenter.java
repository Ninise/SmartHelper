package com.ninise.smarthelper.view.apps;

import android.content.pm.ResolveInfo;

import com.ninise.smarthelper.utils.IRecyclerItemClickListener;

/**
 * @author Nikitin Nikita
 */

public interface IAppsPresenter {

    void onCreateAdapter(IRecyclerItemClickListener<ResolveInfo> listener);

}
