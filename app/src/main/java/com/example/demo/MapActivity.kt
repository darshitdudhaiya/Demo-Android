package com.example.demo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.*
import com.mapquest.android.searchahead.SearchAhead


class MapActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val searchAhead = SearchAhead.getInstance()
        searchAhead.initialize(this, "LF4fUlD2aB4HzjcTNQyx6xB8ezzxzzWc")

        webView = findViewById(R.id.webView)

        // Enable JavaScript in the WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Set a WebViewClient to handle page navigation
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/mapquest_map.html")

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d(
                    "WebView Console",
                    "${consoleMessage.message()} -- From line ${consoleMessage.lineNumber()} of ${consoleMessage.sourceId()}"
                )




                return true
            }
        })

    }
}