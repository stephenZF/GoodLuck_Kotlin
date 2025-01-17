package com.goodluck.module_home.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.goodluck.adapter.HomePagerAdapter
import com.goodluck.base.BaseFragment
import com.goodluck.entity.TextCard
import com.goodluck.manager.QDSkinManager
import com.goodluck.module_home.R
import com.goodluck.module_home.databinding.HomeFragmentHomeBinding
import com.goodluck.module_home.view.viewmodel.HomeViewModel
import com.goodluck.utils.LogUtils
import com.goodluck.widget.YiYanHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.MODULE_HOME_MAIN
import kotlinx.android.synthetic.main.home_fragment_home.*


/**
 * Created by zf on 2020/2/29
 * 描述：
 */
@Route(path = MODULE_HOME_MAIN)
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentHomeBinding>(), OnRefreshLoadMoreListener {
    override fun layoutId(): Int = R.layout.home_fragment_home


    private lateinit var mHomeAdapter: HomePagerAdapter
    private var mFeedId: String? = ""
    override fun initView(savedInstanceState: Bundle?) {
        LogUtils.e("---------initview"+QDSkinManager.getCurrentSkin())
        topbar.setTitle("订阅")
        topbar.addRightImageButton(R.drawable.icons8_time_machine_66_3x, R.id.clock_funciton)
        mHomeAdapter = HomePagerAdapter()
        srl_refresh.setRefreshHeader(YiYanHeader(mContext!!))
        srl_refresh.setOnRefreshLoadMoreListener(this)
        mViewModel.mSubcribeArticlesLiveData.observe(this, Observer { handleArticles(it) })
        mViewModel.mSubcribeArticlesMoreLiveData.observe(this, Observer { handleMoreArticles(it) })
        mViewModel.mSubcribeArticlesFailureLiveData.observe(this, Observer { handleArticlesFailure() })
        mBinding!!.viewpagerLayout.vpContent.setCurrentItem(
            0,
            false
        )//触发onPageScrolled，防止初始化时transformPage 所有position都为0
    }

    override fun getBackgroundAttr(): Int {
        return R.attr.app_skin_common_background_1
    }
    private fun handleArticlesFailure() {
        srl_refresh.closeHeaderOrFooter()
    }

    private fun handleMoreArticles(cards: MutableList<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        if (cards.isEmpty()) {
            srl_refresh.finishLoadMoreWithNoMoreData()
        } else {
            mHomeAdapter.addDatas(cards)
            mFeedId = cards[cards.size - 1].feedid!!
        }
    }

    private fun handleArticles(cards: MutableList<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        mHomeAdapter.setDatas(cards)
        mBinding!!.viewpagerLayout.vpContent.adapter = mHomeAdapter
    }

    override fun lazyLoadData() {
        mViewModel.getSubcribeArticles(mFeedId)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        lazyLoadData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mBinding!!.viewpagerLayout.vpContent.postDelayed({
            lazyLoadData()
        }, 300)
    }

}