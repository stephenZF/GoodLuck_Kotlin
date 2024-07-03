package com.goodluck.module_mine.module

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.goodluck.base.BaseActivity
import com.goodluck.base.BaseViewModel
import com.goodluck.module_mine.R
import com.goodluck.module_mine.fragment.MineFragment


class MineTestActivity : BaseActivity<BaseViewModel, ViewDataBinding>() {
    override fun getLayoutId() = R.layout.mine_activity_mine_test

    override fun init(savedInstanceState: Bundle?) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.fl_content, MineFragment())
            .commitAllowingStateLoss()
    }

}
