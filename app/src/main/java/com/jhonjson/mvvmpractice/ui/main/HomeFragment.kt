package com.jhonjson.mvvmpractice.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jhonjson.base_library.base.DataBindingConfig
import com.jhonjson.base_library.base.LazyVmFragment
import com.jhonjson.base_library.common.loadUrl
import com.jhonjson.base_library.common.smartConfig
import com.jhonjson.base_library.common.smartDismiss
import com.jhonjson.mvvmpractice.BR
import com.jhonjson.mvvmpractice.R
import com.jhonjson.mvvmpractice.common.ArticleAdapter
import com.jhonjson.mvvmpractice.common.OnChildItemClickListener
import com.jhonjson.mvvmpractice.ui.main.baen.BannerBean
import com.jhonjson.mvvmpractice.ui.main.vm.HomeVM
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : LazyVmFragment(), BGABanner.Adapter<ImageView?, String?>
    , BGABanner.Delegate<ImageView?, String?>, OnChildItemClickListener {
    private var homeVm: HomeVM? = null
    private val adapter by lazy { ArticleAdapter(mutableListOf()) }
    private var bannerList: MutableList<BannerBean>? = null
    private val head by lazy {
        LayoutInflater.from(mActivity).inflate(R.layout.banner_head, null)
    }
    private val banner by lazy {
        head.findViewById(R.id.banner) as BGABanner
    }

    override fun initViewModel() {
        homeVm = getActivityViewModel(HomeVM::class.java)
    }

    override fun observe() {
        //文章列表
        homeVm?.articleList?.observe(this, Observer {
            smartDismiss(smartRefresh)
            adapter.setNewData(it)
        })
        //banner
        homeVm?.banner?.observe(this, Observer {
            bannerList = it
            initBanner()
        })
        //收藏
        homeVm?.collectLiveData?.observe(this, Observer {
            adapter.collectNotifyById(it)
        })
        //取消收藏
        homeVm?.unCollectLiveData?.observe(this, Observer {
            adapter.unCollectNotifyById(it)
        })
        //请求错误
        homeVm?.errorLiveData?.observe(this, Observer {
            smartDismiss(smartRefresh)
        })
    }
    override fun lazyInit() {
        initView()
        loadData()
    }

    override fun initView() {
        //关闭更新动画
        (rvHomeList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        smartRefresh.setOnRefreshListener {
            homeVm?.getArticleList(true)
        }
        //上拉加载
        smartRefresh.setOnLoadMoreListener {
            homeVm?.getArticleList(false)
        }
        smartConfig(smartRefresh)
        adapter.apply {
            setOnChildItemClickListener(this@HomeFragment)
            //将banner添加至recyclerView
            addHeaderView(head)
            rvHomeList.adapter = this
        }
    }

    override fun loadData() {
        //自动刷新
        smartRefresh.autoRefresh()
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        banner.apply {
            setAutoPlayAble(true)
            val views: MutableList<View> = ArrayList()
            bannerList?.forEach { _ ->
                views.add(ImageView(mActivity).apply {
                    setBackgroundResource(R.drawable.ripple_bg)
                })
            }
            setAdapter(this@HomeFragment)
            setDelegate(this@HomeFragment)
            setData(views)
        }
    }

    override fun fillBannerItem(
        banner: BGABanner?,
        itemView: ImageView?,
        model: String?,
        position: Int
    ) {
        itemView?.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            loadUrl(mActivity, bannerList?.get(position)?.imagePath!!)
        }
    }

    override fun onBannerItemClick(
        banner: BGABanner?,
        itemView: ImageView?,
        model: String?,
        position: Int
    ) {
        nav().navigate(R.id.action_main_fragment_to_web_fragment
            , Bundle().apply {
                bannerList?.get(position)?.let {
                    putString("loadUrl",it.url)
                    putString("title",it.title)
                    putInt("id",it.id)
                }
            })
    }

    override fun getLayoutId()= R.layout.fragment_home

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_home, homeVm)
            .addBindingParam(BR.vm, homeVm)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            //item
//            R.id.root->{
//                nav().navigate(R.id.action_main_fragment_to_web_fragment
//                    ,this@HomeFragment.adapter.getBundle(position))
//            }
//            //收藏
//            R.id.ivCollect->{
//                if (CacheUtil.isLogin()){
//                    this@HomeFragment.adapter.data[position].apply {
//                        //已收藏取消收藏
//                        if (collect){
//                            homeVm?.unCollect(id)
//                        }else{
//                            homeVm?.collect(id)
//                        }
//                    }
//                }else{
//                    nav().navigate(R.id.action_main_fragment_to_login_fragment)
//                }
//            }
        }
    }
}