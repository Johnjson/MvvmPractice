package com.jhonjson.mvvmpractice.ui.main

import com.jhonjson.base_library.base.DataBindingConfig
import com.jhonjson.base_library.base.LazyVmFragment
import com.jhonjson.mvvmpractice.R

class MineFragment: LazyVmFragment() {
    override fun lazyInit() {

    }

    override fun getLayoutId()= R.layout.fragment_mine

    override fun getDataBindingConfig(): DataBindingConfig? {
        return null

    }
}