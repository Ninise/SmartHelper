package com.ninise.smarthelper.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.StringRes;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * @author Nikitin Nikita
 */

public class Utils {

    private final String TAG = Utils.class.getSimpleName();

    private static Utils mInstance = null;

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }

        return mInstance;
    }

    public List<ResolveInfo> getApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities(mainIntent, 0);

        return pkgAppsList;
    }

    public String string(@StringRes int id) {
        return mContext.getResources().getString(id);
    }

    public DrawableRequestBuilder<String> parseResolveInfoIcon(ResolveInfo ri) {
        return Glide.with(mContext).load("").placeholder(ri.loadIcon(mContext.getPackageManager())).diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public String parseResolveInfoLabel(ResolveInfo ri) {
        return String.valueOf(ri.loadLabel(mContext.getPackageManager()));
    }
}
