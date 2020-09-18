package com.jhonjson.mvvmpractice.ui.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jhonjson.base_library.base.BaseViewModel
import com.jhonjson.mvvmpractice.bean.ArticleBean
import com.jhonjson.mvvmpractice.ui.main.baen.BannerBean
import com.jhonjson.mvvmpractice.ui.main.repo.HomeRepo

/**
 * des 首页
 * @date 2020/9/17
 * @author jhonjson
 */
class HomeVM : BaseViewModel() {

    private val repo by lazy { HomeRepo(viewModelScope,errorLiveData) }
    /**
     * 文章列表
     */
    val articleList = MutableLiveData<MutableList<ArticleBean.DatasBean>>()

    /**
     * banner
     */
    val banner = MutableLiveData<MutableList<BannerBean>>()

    /**
     * 收藏
     */
    val collectLiveData = MutableLiveData<Int>()

    /**
     * 取消收藏
     */
    val unCollectLiveData = MutableLiveData<Int>()

    /**
     * 获取首页文章列表， 包括banner
     */
    fun getArticleList(isRefresh:Boolean) {
        repo.getArticleList(isRefresh,articleList,banner)
    }

    fun collect(id:Int){
        repo.collect(id,collectLiveData)
    }

    /**
     * 取消收藏
     */
    fun unCollect(id:Int){
        repo.unCollect(id,unCollectLiveData)
    }
}