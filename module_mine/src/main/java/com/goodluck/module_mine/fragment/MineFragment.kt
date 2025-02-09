package com.goodluck.module_mine.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.goodluck.base.BaseFragment
import com.goodluck.base.BaseViewModel
import com.goodluck.manager.QDSkinManager
import com.goodluck.module_mine.R
import com.goodluck.module_mine.databinding.MineFragmentMineBinding
import com.goodluck.utils.MConfig
import com.qmuiteam.qmui.kotlin.onClick
import ex.MODULE_MINE_MAIN

/**
 * Created by zf on 2020/3/19
 * 描述：
 */
@Route(path = MODULE_MINE_MAIN)
class MineFragment : BaseFragment<BaseViewModel, MineFragmentMineBinding>() {
    override fun layoutId() = R.layout.mine_fragment_mine

    override fun initView(savedInstanceState: Bundle?) {

        mBinding!!.topbar.setBottomDividerAlpha(0)
        childFragmentManager.beginTransaction()
            .replace(R.id.fl_user_home, UserHomeFragment.getInstance(MConfig.getLoginResult()))
            .commit()
        mBinding!!.ibtnLightMode.onClick {
            if (QDSkinManager.getCurrentSkin() == QDSkinManager.SKIN_WHITE) {
                QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
            } else {
                QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
            }

        }
    }
}