package net.sotez.app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.pb
import kotlinx.android.synthetic.main.activity_notification.webview
import java.lang.Exception

class NotificationActivity : AppCompatActivity() , PageLoader {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        webview.loadUrl("https://www.sotez.net/notifications.html")
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = MyWebViewClient(this)

        showProgressBar()
    }

    private fun showProgressBar() {
        Handler().postDelayed({
            pb.visibility = View.GONE
        }, 3000)
    }

    override fun pageLoaded(constantURL: ConstantURL, url: String) {
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
                pb.visibility = View.VISIBLE
                showProgressBar()
            }
        }
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