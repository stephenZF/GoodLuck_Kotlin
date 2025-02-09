package com.goodluck.module_mine.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.goodluck.base.BaseActivity
import com.goodluck.base.BaseFragment
import com.goodluck.base.BaseViewModel
import com.goodluck.event.PublishEvent
import com.goodluck.module_mine.R
import com.goodluck.module_mine.databinding.MineActivityPublishCardBinding
import com.goodluck.module_mine.fragment.PublishCardFragment
import com.goodluck.module_mine.fragment.PublishMusicFragment
import com.goodluck.widget.CommonPage2Adapter
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.kotlin.skin
import com.qmuiteam.qmui.widget.tab.QMUITab
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.qmuiteam.qmui.widget.tab.TabMediator
import kotlinx.android.synthetic.main.mine_activity_publish_card.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = ex.PUBLISH_CARD_PAGE)
class PublishCardActivity : BaseActivity<BaseViewModel, MineActivityPublishCardBinding>() {
    private val mAdapter by lazy {
        val fragments = arrayListOf<BaseFragment<*, *>>().apply {
            add(PublishCardFragment.getInstance())
            add(PublishMusicFragment.getInstance())
        }
        CommonPage2Adapter(this, fragments)
    }

    override fun getLayoutId() = R.layout.mine_activity_publish_card

    override fun init(savedInstanceState: Bundle?) {
        vp_content.adapter = mAdapter
        val fragment = QMUITabSegment(mContext).apply {
            val tabBuilder = tabBuilder()
            addTab(
                tabBuilder.build(mContext)
            )
            addTab(tabBuilder.build(mContext))
            setIndicator(
                QMUITabIndicator(
                    resources.getDimensionPixelOffset(R.dimen.dp_10),
                    resources.getDimensionPixelSize(com.qmuiteam.qmui.R.dimen.qmui_tab_segment_indicator_height),
                    false, false
                )
            )
            mode = QMUITabSegment.MODE_SCROLLABLE
            setItemSpaceInScrollMode(resources.getDimensionPixelOffset(R.dimen.dp_26))
        }
        mTopBar.setCenterView(fragment)
        TabMediator(mContext,
            fragment
            , vp_content, true, object : TabMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: QMUITab, position: Int) {
                    tab.text = if (position == 0) "图文" else "音乐"
                }
            }).attach()
        mTopBar.addLeftBackImageButton().onClick { finish() }
        with(mTopBar.addRightTextButton("下一步", R.id.action_next)) {
            skin {
                it.textColor(R.attr.app_skin_text_selected_color)
            }
            onClick {
                mAdapter.mFragments.forEach {
                    if (it is PublishCardFragment) {
                        it.onNextAction()
                    }
                }
            }
        }
    }

    override fun isNeedEventBus() = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPublishEvent(event: PublishEvent) {
        if (event.mAction == PublishEvent.PUBLISH_SUCCESS) {
            finish()
        }
    }
}
