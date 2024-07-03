package com.goodluck.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.goodluck.base.BaseActivity
import com.goodluck.base.BaseViewModel
import com.goodluck.entity.UserData
import com.goodluck.entity.UserHomeData
import com.goodluck.module_mine.R
import com.goodluck.module_mine.databinding.MineActivityUserHomeBinding
import com.goodluck.module_mine.fragment.UserHomeFragment
import com.qmuiteam.qmui.kotlin.onClick
import ex.USER_HOME_PAGE

@Route(path = USER_HOME_PAGE)
class UserHomeActivity : BaseActivity<BaseViewModel, MineActivityUserHomeBinding>() {
    override fun getLayoutId() = R.layout.mine_activity_user_home

    companion object {
        fun navTo(context: Context, userData: UserData?) {
            val intent = Intent(context, UserHomeActivity::class.java)
            intent.putExtra(ex.USER_DATA, userData)
            context.startActivity(intent)
        }

        fun navTo(context: Context, uid: String?) {
            val intent = Intent(context, UserHomeActivity::class.java)
            intent.putExtra(ex.UID, uid)
            context.startActivity(intent)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.addLeftBackImageButton().onClick { finish() }
        val userData: UserHomeData? = intent.getParcelableExtra(ex.USER_DATA)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_user_home,
                if (userData == null) UserHomeFragment.getInstance(intent.getStringExtra(ex.UID)) else UserHomeFragment.getInstance(
                    userData
                )
            )
            .commit()
    }
}
