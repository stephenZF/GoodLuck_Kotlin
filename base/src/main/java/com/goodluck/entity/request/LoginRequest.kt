package com.goodluck.entity.request

/**
 * Created by zf on 2020/3/17
 * 描述：
 */
class LoginRequest(
    val platuid: String,
    val username: String,
    val smallavatar: String,
    val largeavatar: String,
    val gender: String,
    val ac: String,
    val actype: String
)