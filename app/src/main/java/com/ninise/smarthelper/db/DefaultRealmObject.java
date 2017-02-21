package com.ninise.smarthelper.db;

import io.realm.RealmModel;

/**
 * @author Nikitin Nikita
 */

public interface DefaultRealmObject extends RealmModel {

    String getTitle();

    String getKey();

    String getName();

}
