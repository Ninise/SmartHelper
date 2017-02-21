package com.ninise.smarthelper.db;


import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * @author Nikitin Nikita
 */

public class RealmWorker<T extends DefaultRealmObject> implements RealmAccessor<T> {

    private static RealmWorker mInstance = null;

    private Realm mRealm;
    private Class<T> mClass;
    private final String QUERY_KEY = "QUERY_KEY";

    private RealmWorker() {}

    public static RealmWorker getInstance() {
        if (mInstance == null) {
            mInstance = new RealmWorker();
        }

        return mInstance;
    }

    public void init(Realm realm, Class<T> clazz) {
        mRealm = realm;
        mClass = clazz;
    }

    public void deinit() {
        mRealm.close();
        mRealm = null;
        mClass = null;
    }

    @Override
    public void createItem(T item) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(item);
        mRealm.commitTransaction();
    }

    @Override
    public T readItem(String name) {
        T item;

        if (query(name).findAll().size() == 0) {
            item = null;
        } else {
            item = mRealm.copyFromRealm(query(name).findFirst());
        }

        return item;
    }

    @Override
    public void updateItem(T item) {
        mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(item));
    }

    @Override
    public void deleteItem(T item) {
        mRealm.beginTransaction();
        query(item.getKey()).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public List<T> readAllItems() {
        if (mRealm.isEmpty()) return new LinkedList<T>();
        return mRealm.copyFromRealm(where().findAll());
    }

    @Override
    public void clear() {
        mRealm.delete(mClass);
    }

    private RealmQuery<T> query(String name) {
        return where().contains(QUERY_KEY, name);
    }

    private RealmQuery<T> where() {
        return mRealm.where(mClass);
    }
}
