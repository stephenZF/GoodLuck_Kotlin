package com.goodluck.module_mine.fragment

import android.os.Bundle
import com.goodluck.base.BaseFragment
import com.goodluck.module_mine.R
import com.goodluck.module_mine.viewmodel.PublishCardViewModel

class PublishMusicFragment : BaseFragment<PublishCardViewModel, com.goodluck.module_mine.databinding.MineFragmentPublishMusicBinding>() {

    companion object {
        fun getInstance(): PublishMusicFragment {
            val fragment = PublishMusicFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutId() = R.layout.mine_fragment_publish_music

    override fun initView(savedInstanceState: Bundle?) {
    }

}