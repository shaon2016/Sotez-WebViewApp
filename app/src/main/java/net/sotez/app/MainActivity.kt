package net.sotez.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swiperefresh.setOnRefreshListener(this)

        if (Util.isNetConnected(this)) {

            webview.loadUrl("https://www.sotez.net")

            webview.settings.javaScriptEnabled = true

            webview.webViewClient = MyWebViewClient()

            floating.setOnClickListener {
                startActivity(Intent(this, NotificationActivity::class.java))
            }

            showProgressBar()


        } else {
            tvInternetMsg.visibility = View.VISIBLE
            webview.visibility = View.GONE
            floating.visibility = View.GONE
            pb.visibility = View.GONE
        }

    }

    private fun showProgressBar() {
        Handler().postDelayed({
            pb.visibility = View.GONE
            swiperefresh.isRefreshing = false
        }, 5000)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    override fun onRefresh() {
        webview.reload()
        showProgressBar()
    }
}