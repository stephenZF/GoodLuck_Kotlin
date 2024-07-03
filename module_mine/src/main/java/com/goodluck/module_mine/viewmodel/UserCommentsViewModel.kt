package com.goodluck.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.CommentData
import com.goodluck.http.datasource.UserDataSource

/**
 * Created by zf on 2020/4/2
 * 描述：
 */
class UserCommentsViewModel : BaseViewModel() {
    lateinit var mUid: String
    val mMl: Int? = null
    val mMc: Int? = null
    var mLastCommendId: String? = null
    val mCommentsLiveData = MutableLiveData<MutableList<CommentData>>()
    val mCommentsMoreLiveData = MutableLiveData<MutableList<CommentData>>()
    val mCommentsFailLiveData = MutableLiveData<Any?>()
    fun getCommentByUser() {
        launchOnlyresult({
            UserDataSource.getCommentByUser(mUid, mMl, mMc, mLastCommendId)
        }, {
            if (mLastCommendId == null) {
                mCommentsLiveData.value = it.commentlist
            } else {
                mCommentsMoreLiveData.value = it.commentlist
            }
            if (!it.commentlist.isNullOrEmpty())
                mLastCommendId = it.commentlist!![it.commentlist!!.size - 1].commentid
        }, {
            mCommentsFailLiveData.value = null
        })
    }
}