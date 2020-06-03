package net.sotez.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.pb
import kotlinx.android.synthetic.main.activity_notification.webview

class NotificationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        webview.loadUrl("https://www.sotez.net/notifications.html")
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = MyWebViewClient()

        showProgressBar()
    }

    private fun showProgressBar() {
        Handler().postDelayed({
            pb.visibility = View.GONE
        }, 5000)
    }

}