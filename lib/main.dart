import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(

      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String timer = "Start Service";


  void startServiceInPlatform() async{

    if(Platform.isAndroid){

      var methodChannel = MethodChannel("com.example.flutter_app_bg_services");
      String data  = await methodChannel.invokeMethod("startService");
      debugPrint("M Channel: "+data);

    }

  }

  @override
  Widget build(BuildContext context) {

    return Container(
      color: Colors.white,
      child: Center(
        child:Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            RaisedButton(
              child: Text(timer),
              onPressed: (){
                startServiceInPlatform();
              },
            ),
          ],
        ),
      ),
    );
  }
}
