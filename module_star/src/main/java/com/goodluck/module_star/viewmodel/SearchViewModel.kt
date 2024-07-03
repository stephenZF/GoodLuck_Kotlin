package com.goodluck.module_star.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.TextCard
import com.goodluck.entity.UserData
import com.goodluck.http.datasource.HomeDataSource
import org.json.JSONObject

class SearchViewModel : BaseViewModel() {
    val mSearchTextCardLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSearchUsersLiveData = MutableLiveData<MutableList<UserData>>()
    var mTextIndex: Int? = null
    var mUserIndex: Int? = null
    var mMoreExtra: String? = null
    fun searchTextCards(content: String) {
        launchOnlyresult({
            HomeDataSource.searchTextCards(content, mTextIndex, mMoreExtra)
        }, {
            mSearchTextCardLiveData.value = it.textcardlist
            mMoreExtra = it.moreextra
            val jsonObject = JSONObject(mMoreExtra)
            mTextIndex = jsonObject.getInt("offset")
        }, {
            defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
        }, {}, true)
    }

    fun searchUsers(content: String) {
        launchOnlyresult({
            HomeDataSource.searchUsers(content, mUserIndex)
        }, {
            mSearchUsersLiveData.value = it.userlist
        }, { defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}") }, {})
    }
}