package com.example.webview_platform_view_example

import android.content.Context
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

// self explanatory codes
class NativeWebView(context: Context, id: Int, creationParams: Map<String?, Any?>?) : PlatformView {

    private val webView: WebView = WebView(context)

    override fun getView(): View {
        return webView
    }

    override fun dispose() {}

    fun changeUrl(key: String?) {
        webView.loadUrl(key!!)
    }

    init {
        webView.webViewClient = WebViewClient()
        webView.loadUrl(creationParams!!["initialUrl"].toString())
        webView.settings.javaScriptEnabled = creationParams!!["javascriptEnabled"] as Boolean
    }
}

class NativeWebViewFactory : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    lateinit var nativeWebView: NativeWebView
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String?, Any?>?
        nativeWebView = NativeWebView(context!!, viewId, creationParams)
        return nativeWebView
    }

    fun changeUrl(key: String?): Boolean? {
        nativeWebView.changeUrl(key)
        return true
    }
}