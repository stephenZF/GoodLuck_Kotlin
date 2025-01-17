package com.goodluck.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.request.PublishTextCardRequest
import com.goodluck.event.PublishEvent
import com.goodluck.http.datasource.ArticleActionDataSource
import com.goodluck.utils.LogUtils
import com.goodluck.utils.MConfig
import org.greenrobot.eventbus.EventBus

class PublishCardViewModel : BaseViewModel() {
    val mPublishLiveData = MutableLiveData<Any?>()
    fun publishCard(request: PublishTextCardRequest) {
        defUI.showDialog.call()
//        if (!TextUtils.isEmpty(request.pic)) {
//            with(CosService()) {
//                setCosServiceResultListener(object : CosService.CosServiceResultListener {
//                    override fun onSuccess(path: String) {
//                        request.pic = path
//                        request(request)
//                    }
//
//                    override fun onFailure() {
//                    }
//
//                })
//                uploadImg(request.pic!!)
//            }
//            return
//        }
        request(request)

    }

    fun request(request: PublishTextCardRequest) {
        launchOnlyresult({
            LogUtils.e("------${request}")
            ArticleActionDataSource.publishTextCard(
                request.priv,
                request.title,
                request.category,
                request.original,
                request.from,
                request.content,
                request.type,
                request.originbookid,
                request.pic
            )
        }, {
            defUI.dismissDialog
            val userHomeData = MConfig.getLoginResult()
            val booklist = userHomeData.booklist
            if (booklist.isNullOrEmpty()) return@launchOnlyresult
            for (i in 0..booklist.size - 1) {
                if (it.originbook!!.bookid == booklist[i].bookid) {
                    booklist[i].cardcnt = booklist[i].cardcnt + 1
                    break
                }
            }
            userHomeData.booklist = booklist
            MConfig.setLoginResult(userHomeData)
            EventBus.getDefault().post(PublishEvent(PublishEvent.PUBLISH_SUCCESS))
            mPublishLiveData.value = null
        }, { defUI.dismissDialog }, { defUI.dismissDialog }, true)
    }
}