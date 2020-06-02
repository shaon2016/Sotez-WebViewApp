package net.sotez.app

import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        view.loadUrl(url)
        return true
    }


}