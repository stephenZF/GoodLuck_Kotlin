package com.goodluck.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.UserData
import com.goodluck.http.datasource.UserDataSource

class UserFollowersViewModel : BaseViewModel() {
    val mUsersDataLiveData = MutableLiveData<MutableList<UserData>>()
    val mUsersDataMoreLiveData = MutableLiveData<MutableList<UserData>>()
    val mUsersDataFailLiveData = MutableLiveData<Any?>()
    lateinit var mUid: String
    var mLastDateTime: String? = null
    var mIndex: Int? = null
    fun getFollowers() {
        launchOnlyresult({
            UserDataSource.getFollowers(mUid, mLastDateTime, mIndex)
        }, {
            if (mLastDateTime == null) {
                mUsersDataLiveData.value = it.userlist
            } else {
                mUsersDataMoreLiveData.value = it.userlist
            }
            if (!it.userlist.isNullOrEmpty())
                mLastDateTime = it.userlist!![it.userlist!!.size - 1].followdatetime
        }, {
            defUI.toastEvent.postValue(it.errormsg)
            mUsersDataFailLiveData.value = null
        })
    }
}