package com.example.untitled2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.abc.app/rating"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "rateOnGooglePlay") {
                if (call.hasArgument("packageName")) {
                    val packageName = call.argument<String>("packageName")
                    if (packageName != null && packageName.isNotEmpty()) {
                        print("NPD Android: $packageName")
                        val res = rateOnGooglePlay(packageName)
                        result.success(res)
                    } else {
                        result.error("400", "Package name error", "Package name is empty")
                    }
                }
            } else {
                print("Something")
            }
        }

    }

    private fun rateOnGooglePlay(packageName: String) : Boolean {
        return try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            true
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
            false
        }
    }
}
