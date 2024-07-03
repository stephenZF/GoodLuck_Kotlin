package com.goodluck.http.basic.callback

import com.goodluck.http.basic.exception.BaseException

/**
 * Created by zf on 2020/2/26
 * 描述：
 */
interface RequestMultiplyCallback<T> : RequestCallback<T> {
    fun onFail(e: BaseException)
}