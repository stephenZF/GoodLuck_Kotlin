package com.goodluck.http.interceptor

import com.goodluck.utils.MConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by zf on 2020/2/26
 * 描述：
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()
        newBuilder.addHeader("Cookie", MConfig.getCookie());
        newBuilder.addHeader("v", "3.35");
        return chain.proceed(newBuilder.build())
    }
}