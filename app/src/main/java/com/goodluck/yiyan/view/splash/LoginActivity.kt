package com.goodluck.yiyan.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.goodluck.base.BaseActivity
import com.goodluck.entity.UserHomeData
import com.goodluck.utils.LogUtils
import com.goodluck.yiyan.MainActivity
import com.goodluck.yiyan.databinding.ActivityLoginBinding
import com.goodluck.yiyan.viewmodel.LoginViewModel
import com.qmuiteam.qmui.kotlin.onClick
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import ex.MODULE_LOGIN
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Route(path = MODULE_LOGIN)
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun getLayoutId(): Int = com.goodluck.yiyan.R.layout.activity_login
    private var mSsoHandler: SsoHandler? = null
    override fun init(savedInstanceState: Bundle?) {
        mSsoHandler = SsoHandler(this)
        tv_sina.onClick {
            mSsoHandler!!.authorize(SelfSinaAuthListener())
        }
        mViewModel.mLoginLiveData.observe(this, Observer { handleLogin(it) })
    }

    private fun handleLogin(it: UserHomeData?) {
        if (it != null) {
            navTo(mContext, MainActivity::class.java)
        }
    }

    inner class SelfSinaAuthListener : WbAuthListener {

        override fun onSuccess(token: Oauth2AccessToken?) {
            GlobalScope.launch(Dispatchers.Main) {
                if (token == null) return@launch
                if (token.isSessionValid) {
                    LogUtils.e("-------${token.token}")
//                    MMKVUtil.setValue(TOKEN,token.token)
                    mViewModel.loginFromOtherPlatform(token.uid, token.token)
                }
            }
        }

        override fun onFailure(p0: WbConnectErrorMessage?) {
        }

        override fun cancel() {
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mSsoHandler != null) {
            mSsoHandler!!.authorizeCallBack(requestCode, resultCode, data)
        }
    }

    override fun hasTitleAction(): Boolean {
        return false
    }
}
