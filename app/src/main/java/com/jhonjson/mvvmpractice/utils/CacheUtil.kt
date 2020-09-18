package com.jhonjson.mvvmpractice.utils

import com.jhonjson.base_library.utils.PsUtils
import com.jhonjson.mvvmpractice.constants.Constants

/**
 * des 缓存工具类
 * @date 2020/7/9
 * @author jhonjson
 */
class CacheUtil {

    companion object {
        /**
         * 登录状态
         */
        fun isLogin(): Boolean {
            return PsUtils.getBoolean(Constants.LOGIN, false)
        }

        /**
         * 退出登录，重置用户状态
         */
        fun resetUser() {
            //发送退出登录消息
//            EventBus.getDefault().post(LogoutEvent())
            //更新登陆状态
            PsUtils.setBoolean(Constants.LOGIN, false)
            //移除用户信息
            PsUtils.removeKey(Constants.USER_INFO)
        }
    }
}