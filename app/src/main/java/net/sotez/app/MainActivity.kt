package net.sotez.app

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity(), PageLoader {
    val REQUEST_SELECT_FILE = 100
    var uploadMessage: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Util.isNetConnected(this)) {
            initWeb()
        } else {
            noInternetConnectionMessage()
        }
    }

    private fun initWeb() {
        webview.loadUrl("https://www.sotez.net")

        webview.settings.javaScriptEnabled = true

        webview.webViewClient = MyWebViewClient(this)

        webview.webChromeClient = MyWebChromeClient()

        webview.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        floating.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        showProgressBar()
    }

    private fun noInternetConnectionMessage() {
        tvInternetMsg.visibility = View.VISIBLE
        floating.visibility = View.GONE
        webview.visibility = View.GONE
        pb.visibility = View.GONE

        btnReload.visibility = View.VISIBLE

        btnReload.setOnClickListener {
            if (Util.isNetConnected(this)) {
                tvInternetMsg.visibility = View.GONE
                floating.visibility = View.VISIBLE
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

    private fun showProgressBar() {
        pb.visibility = View.VISIBLE
        Handler().postDelayed({
            pb.visibility = View.GONE
        }, 4000)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null) return;
            uploadMessage?.onReceiveValue(
                WebChromeClient.FileChooserParams.parseResult(
                    resultCode,
                    data
                )
            );
            uploadMessage = null;
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

     inner class MyWebChromeClient : WebChromeClient() {

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            // make sure there is no existing message
            if (uploadMessage != null) {
                uploadMessage?.onReceiveValue(null)
                uploadMessage = null
            }
            uploadMessage = filePathCallback
            val intent = fileChooserParams.createIntent()
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE)
            } catch (e: ActivityNotFoundException) {
                uploadMessage = null
                Toast.makeText(this@MainActivity, "Cannot open file chooser", Toast.LENGTH_LONG)
                    .show()
                return false
            }
            return true
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


