package com.goodluck.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodluck.event.Message
import com.goodluck.event.SingleLiveEvent
import com.goodluck.http.basic.config.HttpCode
import com.goodluck.http.basic.exception.ExceptionHandle
import com.goodluck.http.basic.exception.ResponseThrowable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by zf on 2020/2/27
 * 描述：
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    val defUI: UIChange by lazy { UIChange() }
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }

    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    private suspend fun <T> executeResponse(res: T, success: suspend CoroutineScope.(T) -> Unit) {
        coroutineScope {
            if (res != null)
                success(res)
            else throw ResponseThrowable(-1, "")
        }
    }


    fun <T> launchOnlyresult(
        block: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
        },
        complete: () -> Unit = {},
        isShowDialog: Boolean = false
    ) {
        if (isShowDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                {
                    withContext(Dispatchers.IO) {
                        block()
                    }
                },
                { res ->
                    executeResponse(res) { success(it) }
                },
                {
                    error(it)
                },
                {
                    defUI.dismissDialog.call()
                    complete()
                }
            )
        }
    }

    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> T,
        success: suspend CoroutineScope.(T) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                val ex = ExceptionHandle.handleException(e)
                if (ex.errorcode == HttpCode.CODE_ACCOUNT_INVALID) {
                    val message = Message()
                    message.code = ex.errorcode
                    message.msg = ex.errormsg
                    defUI.toastEvent.postValue("${ex.errorcode}:${ex.errormsg}")
                    defUI.msgEvent.postValue(message)
                } else {
                    error(ex)
                }
            } finally {
                complete()
            }
        }
    }


    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }
}