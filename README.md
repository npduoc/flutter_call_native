# Rate on Google Play by callin Android native

## Getting Started
### class MainActivity: FlutterActivity() {
      private val CHANNEL = "com.abc.app/rating"

      override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
          super.configureFlutterEngine(flutterEngine)
          MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                  call, result ->
              if (call.method == "rateOnGooglePlay") {
                  if (call.hasArgument("packageName")) {
                      val packageName = call.argument<String>("packageName")
                      if (packageName != null && packageName.isNotEmpty()) {
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
  ### }

