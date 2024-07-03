package com.goodluck.http.datasource

import com.goodluck.http.basic.RetrofitManager
import com.goodluck.http.service.ApiService
open class BaseDataSource {
     companion object{
          const val APP_VERSION = "3.35"
     }
     val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
}