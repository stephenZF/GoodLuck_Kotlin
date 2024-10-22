package com.goodluck.module_home.view.delegate

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.goodluck.constant.CARD_CATEGORY
import com.goodluck.entity.TextCard
import com.goodluck.module_home.databinding.HomeDetailTextViewBinding
import com.goodluck.module_home.view.fragment.TextCardDetailFragment

/**
 * Created by zf on 2020/3/20
 * 描述：
 */
class TextProxy(binding: HomeDetailTextViewBinding, context: TextCardDetailFragment) :
    BaseProxy<HomeDetailTextViewBinding>(binding, context) {

    override fun initView() {
        val card = mBinding.textCard!!
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView
        image =
            if (imgShow == TextCard.TYPE_IMAGE_SHOW_RECTANGLE || imgShow == TextCard.TYPE_IMAGE_SHOW_SQUARE) {
                mBinding.nsvCommonView.visibility = View.VISIBLE
                mBinding.ivHeader
            } else if (imgShow == TextCard.TYPE_IMAGE_SHOW_CIRCLE) {
                mBinding.nsvCommonView.visibility = View.VISIBLE
                mBinding.ivHeader2
            } else {
                mBinding.clImgTxtView.visibility = View.VISIBLE
                mBinding.flImgShadow.visibility = View.VISIBLE
                mBinding.ivHeader3
            }
        if (!TextUtils.isEmpty(card.picpath)) {
            image.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)
        } else {
            mBinding.clImgTxtView.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(card.showtime)) {
            val date = card.showtime!!.split("-", " ")
            mBinding.tabView.tvDate.text = "${date[1]}-${date[2]}"
        }
        var category = ""
        if (card.original == 1)
            category += "#原创 "
        when (card.category) {
            CARD_CATEGORY.TYPE_TEXT._value ->
                mBinding.tvCategory.text = category + "#文字"
            CARD_CATEGORY.TYPE_POETRY._value -> mBinding.tvCategory.text = category + "#诗"
            CARD_CATEGORY.TYPE_FILM._value -> mBinding.tvCategory.text = category + "#电影"
            CARD_CATEGORY.TYPE_RECORD._value -> mBinding.tvCategory.text = category + "#语录"
            CARD_CATEGORY.TYPE_WORD._value -> mBinding.tvCategory.text = category + "#歌词"
        }
        mBinding.tabView.proxy = this
        mBinding.bottomAction.proxy = this
        checkLiked(card.textcardid!!)
        setupBottomAction(mBinding.bottomAction)
    }

}