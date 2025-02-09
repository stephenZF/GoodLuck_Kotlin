package ex

import android.app.Application
import android.content.Context
import android.os.Parcel
import com.alibaba.android.arouter.launcher.ARouter
import com.goodluck.manager.QDQQFaceManager
import com.goodluck.manager.QDSkinManager
import com.goodluck.widget.YiYanFooter
import com.goodluck.widget.YiYanHeader
import com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.tencent.cos.xml.model.bucket.PutBucketRequest
import com.tencent.mmkv.MMKV


/**
 * Created by zf on 2020/2/26
 * 描述：
 */

lateinit var sApplication: Application

fun initApp(context: Context) {
    initApp(context.applicationContext as Application)
}

fun initApp(app: Application) {
    sApplication = app
    MMKV.initialize(app)
    ARouter.openLog()
    ARouter.openDebug()
    ARouter.init(app)
    QDSkinManager.install(app)
//    initCos()
    QMUIQQFaceCompiler.setDefaultQQFaceManager(QDQQFaceManager.getInstance())
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
        override fun createRefreshHeader(context: Context, layout: RefreshLayout) = YiYanHeader(app)
    })
    SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
        override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
            return YiYanFooter(context)
        }
    })

}

fun initCos() {
    val putBucketRequest = PutBucketRequest(CosService.BUCKET)
//发送请求
//    CosService.getCosXmlService().putBucketAsync(putBucketRequest, object : CosXmlResultListener() {
//        override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult?) {
//        }
//
//        override fun onFail(
//            request: CosXmlRequest?,
//            exception: CosXmlClientException?,
//            serviceException: CosXmlServiceException?
//        ) {
//        }
//    })
}


inline fun <reified t> Parcel.readMutableList(): MutableList<t> {
    @Suppress("unchecked_cast")
    return readArrayList(t::class.java.classLoader) as MutableList<t>
}

const val SINA_APP_KEY = "1274666040"
//const val SINA_REDIRECT_URL = "http://open.weibo.com/apps/1274666040/privilege/oauth"
const val SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
