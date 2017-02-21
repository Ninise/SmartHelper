package com.ninise.smarthelper.utils;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public interface IRecyclerItemClickListener<T> {
    void onClick(T model, int pos);
}
