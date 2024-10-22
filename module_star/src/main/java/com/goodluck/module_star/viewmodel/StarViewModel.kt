package com.goodluck.module_star.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.constant.CARD_CATEGORY
import com.goodluck.entity.ArticleData
import com.goodluck.entity.TextCard
import com.goodluck.http.datasource.HomeDataSource
import kotlinx.coroutines.CoroutineScope

/**
 * Created by zf on 2020/3/4
 * 描述：
 */
class StarViewModel : BaseViewModel() {
    val mSubcribeTextCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeTextCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeTextCardsFailureLiveData = MutableLiveData<Any>()
    var mMoreExtra: String? = null
    var mLastCardId: String? = null
    fun getTextCardsByType(type: Int) {
        var block: suspend CoroutineScope.() -> ArticleData
        when (type) {

            CARD_CATEGORY.TYPE_ALL._value -> {
                block = { HomeDataSource.getAllStarTextCards(mLastCardId, mMoreExtra) }
            }
            CARD_CATEGORY.TYPE_ORIGIN._value -> {
                block = { HomeDataSource.getOriginStarTextCards(mLastCardId) }
            }
            else ->
                block = { HomeDataSource.getTextCardsByType(type, mLastCardId, mMoreExtra) }
        }
        launchOnlyresult(block, {
            if (TextUtils.isEmpty(mMoreExtra)) {
                mSubcribeTextCardsLiveData.value = it.textcardlist
            } else {
                mSubcribeTextCardsMoreLiveData.value = it.textcardlist
                mMoreExtra = it.moreextra
            }
        }, {
            defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
            mSubcribeTextCardsFailureLiveData.value = it.errorcode
        })
    }
}