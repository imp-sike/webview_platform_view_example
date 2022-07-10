import 'package:flutter/material.dart';
import 'package:webview_platform_view_example/webview.dart';

// Entry point to the application
void main() => runApp(
      const MaterialApp(
        home: BaseApp(),
      ),
    );

class BaseApp extends StatelessWidget {
  const BaseApp({super.key});

  @override
  Widget build(BuildContext context) {
    // just used ValueNotifier, can also create custom WebView Controller
    ValueNotifier<String> urlController =
        ValueNotifier("https://docs.flutter.dev");

    return Scaffold(
      appBar: AppBar(
        title: const Text("Hello World"),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          urlController.value = "https://google.com";
        },
      ),
      body:
          // custom WebView implementation
          WebView(
        setJavascriptEnabled: true,
        urlController: urlController,
      ),
    );
  }
}
