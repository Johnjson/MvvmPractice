package com.jhonjson.base_library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhonjson.base_library.http.ApiException

/**
 * @date 2020-07-27
 * @author jhonjson
 * @describe 基础vm
 */
open class BaseViewModel : ViewModel() {

    /**
     * 错误信息liveData
     */
    val errorLiveData = MutableLiveData<ApiException>()

    /**
     * 无更多数据
     */
    val footLiveDate = MutableLiveData<Any>()

    /**
     * 无数据
     */
    val emptyLiveDate = MutableLiveData<Any>()
}