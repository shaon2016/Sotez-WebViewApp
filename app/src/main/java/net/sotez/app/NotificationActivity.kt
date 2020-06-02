package net.sotez.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification.pb
import kotlinx.android.synthetic.main.activity_notification.swiperefresh
import kotlinx.android.synthetic.main.activity_notification.webview

class NotificationActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        swiperefresh.setOnRefreshListener(this)


        webview.loadUrl("https://www.sotez.net/notifications.html")
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = MyWebViewClient()

        showProgressBar()
    }

    private fun showProgressBar() {
        Handler().postDelayed({
            pb.visibility = View.GONE
            swiperefresh.isRefreshing = false
        }, 5000)
    }

    override fun onRefresh() {
        webview.reload()
        showProgressBar()
    }
}