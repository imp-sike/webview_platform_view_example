import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class WebView extends StatefulWidget {
  final bool setJavascriptEnabled;
  final ValueNotifier<String> urlController;

  const WebView(
      {Key? key,
      required this.setJavascriptEnabled,
      required this.urlController})
      : super(key: key);

  @override
  State<WebView> createState() => _WebViewState();
}

class _WebViewState extends State<WebView> {
  // viewType and webviewMessageChannel code to identified in the android side codes
  final String viewType = 'com.sugar/custom_webView';
  final String webviewMessageChannel = 'com.sugar/custom_webView_message';
  late MethodChannel methodChannel;

  @override
  void initState() {
    methodChannel = MethodChannel(webviewMessageChannel);
    widget.urlController.addListener(() async {
      // call android code everytime url changes
      var message = {"newUrl": widget.urlController.value};
      await methodChannel.invokeMethod("changeUrl", message);
    });
    super.initState();
  }

  @override
  void dispose() {
    widget.urlController.removeListener(() {});
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // prepare the initial message for sending to the Platform WebView
    final Map<String, dynamic> creationParams = <String, dynamic>{};
    creationParams["initialUrl"] = widget.urlController.value;
    creationParams["javascriptEnabled"] = widget.setJavascriptEnabled;

    // return the platform View
    return AndroidView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
