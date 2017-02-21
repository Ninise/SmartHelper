package com.ninise.smarthelper.db;

import java.util.List;

/**
 * @author Nikita Nikita
 */

public interface RealmAccessor<T> {

    T readItem(String name);
    List<T> readAllItems();
    void createItem(T item);
    void updateItem(T item);
    void deleteItem(T item);
    void clear();

}
