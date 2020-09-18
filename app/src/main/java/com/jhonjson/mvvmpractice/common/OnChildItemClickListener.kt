package com.jhonjson.mvvmpractice.common

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter

interface OnChildItemClickListener {
    fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position:Int )
}