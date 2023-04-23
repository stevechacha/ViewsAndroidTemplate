package com.chacha.viewsandroidtemplate.util

sealed class Environment(val url: String) {
    object Main : Environment("https://api-uat.equitygroupholdings.com/noncustomer/")
}