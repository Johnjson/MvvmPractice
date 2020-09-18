package com.jhonjson.mvvmpractice.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.jhonjson.base_library.base.BaseVmFragment
import com.jhonjson.base_library.base.DataBindingConfig
import com.jhonjson.base_library.common.initFragment
import com.jhonjson.mvvmpractice.MainViewModel
import com.jhonjson.mvvmpractice.BR
import com.jhonjson.mvvmpractice.R
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * @date 2020-07-31
 * @author jhonjson
 * @describe 主页面
 */
class MainFragment:BaseVmFragment() {

    private var mainVM: MainViewModel? = null
    private val fragmentList = arrayListOf<Fragment>()

    /**
     * 首页
     */
    private val homeFragment by lazy { HomeFragment() }

    /**
     * 广场
     */
    private val squareFragment by lazy { SquareFragment() }

    /**
     * 我的
     */
    private val mineFragment by lazy { MineFragment() }

    override fun initViewModel() {
        mainVM = getActivityViewModel(MainViewModel::class.java)
    }

    init {
        fragmentList.apply {
            add(homeFragment)
            add(squareFragment)
            add(mineFragment)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
         //初始化viewpager2
        vpHome.initFragment(this, fragmentList).run {
            //全部缓存,避免切换回重新加载
            offscreenPageLimit = fragmentList.size
        }
        //取消viewPager2滑动
        vpHome.isUserInputEnabled = false
        vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btnNav.menu.getItem(position).isChecked = true
            }
        })
        //初始化底部导航栏
        btnNav.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> vpHome.setCurrentItem(0, false)
                    R.id.menu_square -> vpHome.setCurrentItem(1, false)
                    R.id.menu_mine -> vpHome.setCurrentItem(2, false)
                }
                // 这里注意返回true,否则点击失效
                true
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_main

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_main, mainVM)
            .addBindingParam(BR.vm, mainVM)
    }

}