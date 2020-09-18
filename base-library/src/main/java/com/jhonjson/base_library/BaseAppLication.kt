package com.jhonjson.base_library

import android.app.Application
import android.content.Context


/**
 * @date 2020/7/27
 * @author jhonjson
 */
open class BaseAppLication : Application() {

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        private lateinit var baseApplication: BaseAppLication

        fun getContext(): Context {
            return baseApplication
        }
    }
}