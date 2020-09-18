package com.jhonjson.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jhonjson.base_library.base.BaseVmActivity
import com.jhonjson.mvvmpractice.ui.main.MainFragment

class MainActivity : BaseVmActivity() {

    private var mainVM: MainViewModel? = null

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initViewModel() {
        mainVM = getActivityViewModel(MainViewModel::class.java)
    }

    override fun getLayoutId() = R.layout.activity_main


    override fun onBackPressed() {
        //获取hostFragment
        val mMainNavFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.host_fragment)
        //获取当前所在的fragment
        val fragment =
            mMainNavFragment?.childFragmentManager?.primaryNavigationFragment
        //如果当前处于根fragment即HostFragment
        if (fragment is MainFragment) {
            //Activity退出但不销毁
            moveTaskToBack(false)
        }else{
            super.onBackPressed()
        }
    }
}