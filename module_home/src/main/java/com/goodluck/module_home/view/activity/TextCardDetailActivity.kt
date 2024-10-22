package com.goodluck.module_home.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.goodluck.base.BaseActivity
import com.goodluck.constant.CARD_CATEGORY
import com.goodluck.entity.TextCard
import com.goodluck.event.CardActionEvent
import com.goodluck.module_home.R
import com.goodluck.module_home.adapter.TextCardDetailAdapter
import com.goodluck.module_home.view.fragment.TextCardDetailFragment
import com.goodluck.module_home.view.viewmodel.TextCardDetailViewModel
import com.goodluck.utils.LogUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import ex.PAGE_HOME
import ex.PAGE_STAR
import kotlinx.android.synthetic.main.home_activity_text_card_detail.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = ex.TEXT_CARD_DETAIL)
class TextCardDetailActivity : BaseActivity<TextCardDetailViewModel, ViewDataBinding>(),
    OnLoadMoreListener {

    private lateinit var mTextCards: ArrayList<TextCard>
    private var mFromPage = PAGE_HOME
    private var mPosition = 0
    private lateinit var mFragments: ArrayList<TextCardDetailFragment>
    private var mType: Int? = null
    private lateinit var mAdapter: TextCardDetailAdapter

    companion object {
        fun navTo(context: Context, textcard: TextCard) {
            ARouter.getInstance().inject(this)
            val intent = Intent(context, TextCardDetailActivity::class.java)
            intent.putExtra(ex.TEXT_CARD, textcard)
            context.startActivity(intent)
        }
    }

    override fun hasTitleAction() = false
    override fun getLayoutId() = R.layout.home_activity_text_card_detail
    override fun init(savedInstanceState: Bundle?) {
        //滚动到差不多一半时，向外通知加载更多，并将数据更新到详情页
        mPosition = intent.getIntExtra(ex.POSITION, 0)
        mFromPage = intent.getIntExtra(ex.FROM_PAGE, PAGE_HOME)
        mType = intent.getIntExtra(ex.TYPE, CARD_CATEGORY.TYPE_ALL._value)
        mTextCards = intent.getParcelableArrayListExtra(ex.TEXT_CARDS)
        mFragments = arrayListOf()
        mTextCards.forEach {
            val fragment = TextCardDetailFragment.getInstance(
                it,
                mPosition
            )
            mFragments.add(fragment)
        }
        mAdapter = TextCardDetailAdapter(this, mFragments)
        vp_content.adapter = mAdapter
        srl_refresh.setEnableRefresh(false)
        if (mTextCards.size == 1) {
            srl_refresh.setEnableLoadMore(false)
        } else {
            srl_refresh.setOnLoadMoreListener(this)
        }
        vp_content.setCurrentItem(mPosition, false)
        vp_content.offscreenPageLimit = 3
        vp_content.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mPosition = position
                LogUtils.e("-----$position")
            }
        })
//        mViewModel.mTextCardsLiveData.observe(this, Observer { handleArticles(it) })
        mViewModel.mTextCardsMoreLiveData.observe(this, Observer { handleMoreTextCards(it) })
        mViewModel.mTextCardsFailureLiveData.observe(this, Observer { handleTextCardsFailure() })
    }

    private fun handleTextCardsFailure() {
        srl_refresh.closeHeaderOrFooter()
    }

    private fun handleMoreTextCards(it: MutableList<TextCard>?) {
        srl_refresh.closeHeaderOrFooter()
        mTextCards.addAll(it!!)
        it.forEach {
            val fragment = TextCardDetailFragment.getInstance(
                it,
                mPosition
            )
            mFragments.add(fragment)
        }
        vp_content.adapter!!.notifyDataSetChanged()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        when (mFromPage) {
            PAGE_HOME -> mViewModel.getSubcribeArticles(mTextCards[mTextCards.size - 1].feedid)
            PAGE_STAR -> mViewModel.getTextCardsByType(mType!!)
        }
    }

    override fun isNeedEventBus() = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeleteCardEvent(event: CardActionEvent) {
        if (event.mAction == CardActionEvent.CARD_DELETE_SUCCESS) {
            val position = event.getIntExtra(ex.POSITION, -1)
            mAdapter.removeFragment(position)
            vp_content.adapter = mAdapter
        }
    }

}
