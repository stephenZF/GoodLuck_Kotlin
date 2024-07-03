package com.goodluck.http.basic.exception

import com.goodluck.http.basic.config.HttpCode

/**
 * Created by zf on 2020/3/16
 * 描述：
 */
class ForbiddenException : ResponseThrowable(HttpCode.CODE_PARAMETER_INVALID, "404错误")