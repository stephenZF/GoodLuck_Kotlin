package com.goodluck.adapter.holder

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.goodluck.base.BaseCardViewHolder
import com.goodluck.base.R
import com.goodluck.constant.CARD_CATEGORY
import com.goodluck.entity.TextCard
import com.goodluck.manager.AssetsManager

class CommonItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {

    override fun convert(card: TextCard) {
        setGone(R.id.text_container, false)
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView
        image =
            if (imgShow == TextCard.TYPE_IMAGE_SHOW_RECTANGLE || imgShow == TextCard.TYPE_IMAGE_SHOW_SQUARE) {
                setVisible(R.id.fl_header, true)
                getView(R.id.iv_header)
            } else {
                setVisible(R.id.fl_header, true)
                getView(R.id.iv_header2)
            }
        if (!TextUtils.isEmpty(card.picpath)) {
            image.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)
        } else {
            setGone(R.id.fl_header, true)
        }

        Glide.with(mContext)
            .load(card.creator?.smallavatar)
            .into(getView(R.id.iv_avatar))
        setText(
            R.id.tv_nickname, card.creator?.username
        ).setText(
            R.id.tv_book,
            if (card.creator != null && card.originbook != null && card.originbook.bookname != null) "[${card.originbook!!.bookname}]" else ""
        ).setText(
            R.id.tv_date,
            if (!TextUtils.isEmpty(card.datetime)) {
                val date = card.datetime!!.split("-", " ")
                val split = date[3].split(":")
                "${split[0]}:${split[1]}"
            } else ""
        )
            .setText(R.id.tv_text_title, if (!TextUtils.isEmpty(card.title)) card.title else "")
            .setText(R.id.tv_content, card.content)
            .setText(R.id.tv_text_from, if (!TextUtils.isEmpty(card.from)) "- ${card.from} -" else "")
            .setText(R.id.tv_collection, card.collectcnt.toString())
            .setText(R.id.tv_comment, card.replycnt.toString())
            .setText(R.id.tv_like, (card.commentcnt - card.replycnt).toString())
            .setText(
                R.id.tv_category, when (card.category) {
                    CARD_CATEGORY.TYPE_TEXT._value -> "#文字"
                    CARD_CATEGORY.TYPE_RECORD._value -> "#语录"
                    CARD_CATEGORY.TYPE_POETRY._value -> "#诗"
                    CARD_CATEGORY.TYPE_FILM._value -> "#电影"
                    CARD_CATEGORY.TYPE_WORD._value -> "#歌词"
                    else -> ""
                }
            )
        setTypeFace(
            R.id.tv_text_title, R.id.tv_content, R.id.tv_text_from, typeface = AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
        setTypeFace(
            R.id.tv_category, typeface = AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT1)
        )
        setGravity(
            R.id.tv_content,
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        )
    }
}