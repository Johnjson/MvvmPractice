package com.jhonjson.base_library.base

import android.os.Bundle
/**
 * @date 2020-07-27
 * @author jhonjson
 * @describe 实现懒加载
 */
abstract class LazyVmFragment :BaseVmFragment() {

    private var isLoaded = false
    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}