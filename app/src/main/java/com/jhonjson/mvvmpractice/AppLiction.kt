package com.jhonjson.mvvmpractice

import android.content.Context
import androidx.multidex.MultiDex
import com.jhonjson.base_library.BaseAppLication
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter

class AppLiction : BaseAppLication() {

    override fun onCreate() {
        super.onCreate()
        initSmartHead()
        MultiDex.install(this);
    }

    /**
     * 初始化加载刷新ui
     */
    private fun initSmartHead() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, _: RefreshLayout? ->
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, _: RefreshLayout? ->
            BallPulseFooter(context)
        }
    }
}