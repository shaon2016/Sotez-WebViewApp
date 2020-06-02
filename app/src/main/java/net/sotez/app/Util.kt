package net.sotez.app

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

object Util {

    fun isNetConnected(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission")
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}