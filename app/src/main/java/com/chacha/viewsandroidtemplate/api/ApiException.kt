package com.chacha.viewsandroidtemplate.api

class ApiException(val statusCode: Int = 0, val statusMessage: String) : Throwable(statusMessage)