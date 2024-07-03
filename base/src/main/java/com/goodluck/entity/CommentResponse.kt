package com.goodluck.entity

class CommentResponse(
    val commentlist: MutableList<CommentData>? = null,
    val count: Int = 0,
    val hotlist: MutableList<CommentData>? = null,
    val sz: Int = 0
)