AppLovin Android Demo
============

This is our open source demo application for Android. You may re-use any code in your own project(s). If you have any questions, feel free to drop us a line at support@applovin.com.

# Instructions #

  1. Add your SDK key into your `AndroidManifest.xml` file, inside the `<application>` tag:

```
<meta-data
    android:name="applovin.sdk.key"
    android:value="YOUR_SDK_KEY_HERE" />
```
  2. Do a Gradle sync and run the app! The project will automatically import the latest version of our SDK via [JCenter](https://bintray.com/applovin/Android/sdk_android). If you prefer to import it manually, our SDK may be downloaded from our [docs](https://www.applovin.com/integration#androidIntegration) as well.
