import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyCustomApp(),
    );
  }
}

class MyCustomApp extends StatefulWidget {
  const MyCustomApp({Key? key}) : super(key: key);

  @override
  State<MyCustomApp> createState() => _MyCustomAppState();
}

class _MyCustomAppState extends State<MyCustomApp> {
  static const platform = MethodChannel('com.abc.app/rating'); //Unique
  var ratingState = false;

  Future<void> _rateOnGooglePlay(String packageName) async {
    try {
      final bool result = await platform.invokeMethod('rateOnGooglePlay', {'packageName' : packageName});
      print('NPD: rate on google play = $result');
      if (result) {
        setState(() {
          ratingState = result;
        });
      }
    } catch (e) {
      print('NPD: rate on google play = $e');
    }
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.amber,
        onPressed: () {
          _rateOnGooglePlay('com.phucduoc.engsmart');
        },
        child: const Icon(Icons.share, color: Colors.white,),
      ),
    );
  }
}

