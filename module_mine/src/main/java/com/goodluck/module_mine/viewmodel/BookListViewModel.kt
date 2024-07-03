package com.goodluck.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.TextCard
import com.goodluck.http.datasource.UserDataSource

/**
 * Created by zf on 2020/4/1
 * 描述：
 */
class BookListViewModel : BaseViewModel() {
    val mCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mCardsFailLiveData = MutableLiveData<Any?>()
    var mUid: String? = null
    lateinit var mBookId: String
    var mLastDateTime: String? = null
    var mInvent: Int? = null
    fun getTextCardByUser() {
        launchOnlyresult({
            UserDataSource.getTextCardByUser(mUid!!, mLastDateTime, mInvent)
        }, {
            if (mLastDateTime == null) {
                mCardsLiveData.value = it.textcardlist
            } else {
                mCardsMoreLiveData.value = it.textcardlist
            }
            if (!it.textcardlist.isNullOrEmpty()) {
                mLastDateTime = it.textcardlist!![it.textcardlist!!.size - 1].datetime
            }
        }, {
            defUI.toastEvent.postValue(it.errormsg)
            mCardsFailLiveData.value = null
        })
    }

    fun getTextCardByBook() {
        launchOnlyresult({
            UserDataSource.getTextCardByBook(mBookId, mLastDateTime)
        }, {
            if (mLastDateTime == null) {
                mCardsLiveData.value = it.textcardlist
            } else {
                mCardsMoreLiveData.value = it.textcardlist
            }
            if (!it.textcardlist.isNullOrEmpty()) {
                mLastDateTime = it.textcardlist!![it.textcardlist!!.size - 1].datetime
            }
        }, {
            defUI.toastEvent.postValue(it.errormsg)
            mCardsFailLiveData.value = null
        })
    }
}