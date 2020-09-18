package com.jhonjson.mvvmpractice

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.jhonjson.base_library.base.BaseVmActivity
import com.jhonjson.base_library.utils.DialogUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.util.concurrent.TimeUnit

/**
 * @date 2020-07-31
 * @author jhonjson
 * @describe 闪屏页
 */
class SplashActivity : BaseVmActivity(), PermissionCallbacks {
    private var disposable: Disposable? = null
    private val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    companion object {
        private const val WRITE_EXTERNAL_STORAGE = 100
    }

    override fun init(savedInstanceState: Bundle?) {
        requestPermission()
    }

    /**
     * 申请权限
     */
    private fun requestPermission() {
        //已申请
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        } else {
            RequestLocationAndCallPermission()
        }
    }

    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    private fun RequestLocationAndCallPermission() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //数组中权限都已申请
        if (EasyPermissions.hasPermissions(this, *perms)) {
            startIntent()
        } else {
            EasyPermissions.requestPermissions(this, "请求内存权限", WRITE_EXTERNAL_STORAGE, *perms)
        }
    }

    /**
     * 开始倒计时跳转
     */
    private fun startIntent() {
        //开启服务
        disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
            .subscribe {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startIntent()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}