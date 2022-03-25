package com.star.myapplication

import androidx.multidex.MultiDexApplication
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        //cache flutter
        val flutterEngine = FlutterEngine(this)
        flutterEngine?.navigationChannel?.setInitialRoute("english_words")
        flutterEngine?.dartExecutor?.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("flutter_engine", flutterEngine)
    }
}