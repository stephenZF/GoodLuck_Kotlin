package com.goodluck.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.UserHomeData
import com.goodluck.http.datasource.UserDataSource

/**
 * Created by zf on 2020/3/31
 * 描述：
 */
class UserHomeViewModel : BaseViewModel() {
    val mUserHomeDataLiveData by lazy { MutableLiveData<UserHomeData>() }
    val mBookSorderDataLiveData by lazy { MutableLiveData<Any?>() }
    fun getUserInfo(uid: String) {
        launchOnlyresult({
            UserDataSource.getUserinfoAndBooklist(uid)
        }, {
            mUserHomeDataLiveData.value = it
        })
    }

    fun updateBooksSorder(booksorder: String) {
        launchOnlyresult({
            UserDataSource.updateBooksSorder(booksorder)
        }, {
            mBookSorderDataLiveData.value = it
        })
    }
}