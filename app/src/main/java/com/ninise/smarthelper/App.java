package com.ninise.smarthelper;

import android.app.Application;

import com.ninise.smarthelper.utils.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.getInstance().init(getApplicationContext());
        Realm.init(getApplicationContext());

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
