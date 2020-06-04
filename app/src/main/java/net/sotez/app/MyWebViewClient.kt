package net.sotez.app

import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(val pageLoader: PageLoader? = null) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        when (url) {
            "https://wa.me/8801630244144" -> {
                pageLoader?.pageLoaded(ConstantURL.whatsapp, url)
            }
            "https://facebook.com/sotezBD" -> {
                pageLoader?.pageLoaded(ConstantURL.facebook, url)
            }
            else -> {
                view.loadUrl(url)
                pageLoader?.pageLoaded(ConstantURL.insidePage, url)
            }
        }
        return true
    }

//    override fun onLoadResource(view: WebView?, url: String?) {
//        if (url != null && url == "https://wa.me/8801630244144") {
//            pageLoader?.pageLoaded(ConstantURL.whatsapp, url)
//        }
//    }


}

interface PageLoader {
    fun pageLoaded(constantURL: ConstantURL, url: String)
}

enum class ConstantURL {
    youtube, twitter, facebook, whatsapp, insidePage
}