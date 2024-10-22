package com.goodluck.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goodluck.adapter.InstrestAdapter
import com.goodluck.base.BaseCardViewHolder
import com.goodluck.base.R
import com.goodluck.entity.TextCard

class InstrestItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {
    override fun convert(card: TextCard) {
        val rvContent = getView<RecyclerView>(R.id.rv_content)
        val layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        layoutManager.isSmoothScrollbarEnabled = false;
        rvContent.layoutManager = layoutManager
        val adapter = InstrestAdapter(card.subcardlist)
        val footer = LayoutInflater.from(mContext).inflate(R.layout.star_rv_instrest_item_footer, rvContent, false)
        adapter.setFooterView(footer)
        rvContent.adapter = adapter
    }
}