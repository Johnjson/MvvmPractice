package com.jhonjson.base_library.http

/**
 * @date 2020-07-27
 * @author jhonjson
 * @describe 封装业务错误信息
 */
class ApiException (val errorMessage: String, val errorCode: Int) :
    Throwable()