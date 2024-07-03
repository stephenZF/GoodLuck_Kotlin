package com.goodluck.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.goodluck.base.R
import com.goodluck.entity.UserData

/**
 * Created by zf on 2020/3/13
 * 描述：
 */
class UserItemAdapter :
    BaseQuickAdapter<UserData, BaseViewHolder>(R.layout.star_rv_user_item) {
    override fun convert(holder: BaseViewHolder, item: UserData) {
        holder.setText(R.id.tv_nickname, item.username)
        Glide.with(context)
            .load(item.smallavatar)
            .into(holder.getView(R.id.iv_avatar))
    }
}