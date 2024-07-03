package com.goodluck.http.basic.callback

/**
 * Created by zf on 2020/2/26
 * 描述：
 */
interface RequestCallback<T> {
    fun onSuccess(t: T)
}