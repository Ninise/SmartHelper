package com.ninise.smarthelper;

import android.app.Application;

import com.ninise.smarthelper.utils.Utils;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.getInstance().init(getApplicationContext());
    }
}
