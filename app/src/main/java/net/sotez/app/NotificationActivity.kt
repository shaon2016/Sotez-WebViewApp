package net.sotez.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.pb
import kotlinx.android.synthetic.main.activity_notification.webview
import java.lang.Exception

class NotificationActivity : AppCompatActivity() , PageLoader {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        if (Util.isNetConnected(this)) {
            initWeb()
        } else {
            noInternetConnectionMessage()
        }
    }


    private fun initWeb() {
        webview.loadUrl("https://www.sotez.net/notifications.html")
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = MyWebViewClient(this)

        webview.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        showProgressBar()
    }

    private fun showProgressBar() {
        pb.visibility = View.VISIBLE
        Handler().postDelayed({
            pb.visibility = View.GONE
        }, 4000)
    }

    private fun noInternetConnectionMessage() {
        tvInternetMsg.visibility = View.VISIBLE
        webview.visibility = View.GONE
        pb.visibility = View.GONE

        btnReload.visibility = View.VISIBLE

        btnReload.setOnClickListener {
            if (Util.isNetConnected(this)) {
                tvInternetMsg.visibility = View.GONE
                webview.visibility = View.VISIBLE
                initWeb()

                btnReload.visibility = View.GONE
            } else {
                Toast.makeText(
                    this, "Turn on your internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun pageLoaded(constantURL: ConstantURL, url: String) {
        if (Util.isNetConnected(this))

            when (constantURL) {
                ConstantURL.whatsapp -> {
                    val packageName = "com.whatsapp"
                    openOtherApp(packageName, url)
                }
                ConstantURL.youtube -> TODO()
                ConstantURL.twitter -> TODO()
                ConstantURL.facebook -> {
                    val packageName = "com.facebook.katana"
                    openOtherApp(packageName, "fb://facewebmodal/f?href=$url")
                }
                ConstantURL.insidePage -> {
                    showProgressBar()
                }
            }
        else noInternetConnectionMessage()
    }

    private fun openOtherApp(packageName: String, url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.setPackage(packageName)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}