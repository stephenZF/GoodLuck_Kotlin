package com.goodluck.http.basic.exception

import com.goodluck.http.basic.config.HttpCode

/**
 * Created by zf on 2020/3/16
 * 描述：
 */
class ResultInvalidException:ResponseThrowable(HttpCode.CODE_RESULT_INVALID, "无效请求") {
}