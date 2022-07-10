package com.example.webview_platform_view_example

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private lateinit var methodChannel: MethodChannel
    private var nv: NativeWebViewFactory = NativeWebViewFactory()

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {

        // regester the factory instance for controlling the platform view
        flutterEngine
                .platformViewsController
                .registry
                .registerViewFactory("com.sugar/custom_webView", nv)

        // method channel for getting the events currently changeUrl event
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.sugar/custom_webView_message")

        methodChannel.setMethodCallHandler { call, result ->
            if (call.method == "changeUrl") {
                val arguments = call.arguments as Map<*, *>
                var key = arguments["newUrl"]
                var flag = nv.changeUrl(key.toString())
                result.success(flag)
            }
        }

    }
}
