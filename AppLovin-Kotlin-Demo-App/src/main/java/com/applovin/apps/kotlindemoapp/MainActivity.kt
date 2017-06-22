package com.applovin.apps.kotlindemoapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.applovin.apps.kotlindemoapp.banners.BannerDemoMenuActivity
import com.applovin.apps.kotlindemoapp.eventtracking.EventTrackingActivity
import com.applovin.apps.kotlindemoapp.interstitials.InterstitialDemoMenuActivity
import com.applovin.apps.kotlindemoapp.nativeads.NativeAdDemoMenuActivity
import com.applovin.apps.kotlindemoapp.rewarded.RewardedVideosActivity
import com.applovin.sdk.AppLovinSdk
import kotlinx.android.synthetic.main.activity_list.*


class MainActivity : DemoMenuActivity()
{
    private val KEY_SHARED_PREFERENCES_NAMESPACE = "com.applovin.apps.demo.shared_preferences"
    private val KEY_PROMPTED_CONFIG_FLAGS = "com.applovin.apps.demo.shared_preferences.prompted_config_flags"

    private lateinit var muteToggleMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // Initializing our SDK at launch is very important as it'll start preloading ads in the background.
        AppLovinSdk.initializeSdk(applicationContext)
        AppLovinSdk.getInstance(applicationContext).settings.isTestAdsEnabled = true

        // Warn user if SDK key is invalid
        val isLegitSdkKey = checkSdkKey()

        // Prompt to add config flags if SDK key is legit
        if (isLegitSdkKey)
        {
            maybePromptConfigFlags()
        }
    }

    override fun setupListViewFooter()
    {
        var appVersion = ""
        try
        {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            appVersion = pInfo.versionName
        }
        catch (e: PackageManager.NameNotFoundException)
        {
            e.printStackTrace()
        }

        val versionName = Build.VERSION_CODES::class.java.fields[android.os.Build.VERSION.SDK_INT].name
        val apiLevel = Build.VERSION.SDK_INT

        val footer = TextView(applicationContext)
        footer.setTextColor(Color.GRAY)
        footer.setPadding(0, 20, 0, 0)
        footer.gravity = Gravity.CENTER
        footer.textSize = 18f
        footer.text = "\nApp Version: $appVersion\nSDK Version: ${AppLovinSdk.VERSION}\nOS Version: $versionName (API Level $apiLevel)\n"

        list_view.addFooterView(footer)
        list_view.setFooterDividersEnabled(false)
    }

    private fun makeContactIntent(): Intent
    {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "text/plain"
        intent.data = Uri.parse("mailto:" + "support@applovin.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android SDK support")
        intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n---\nSDK Version: ${AppLovinSdk.VERSION}")
        return Intent.createChooser(intent, "Send Email")
    }

    override fun getListViewContents(): Array<DemoMenuItem>
    {
        return arrayOf(
                DemoMenuItem("Interstitials", "Full screen ads. Graphic or video", Intent(this, InterstitialDemoMenuActivity::class.java)),
                DemoMenuItem("Rewarded Videos (Incentivized Ads)", "Reward your users for watching these on-demand videos", Intent(this, RewardedVideosActivity::class.java)),
                DemoMenuItem("Native Ads", "In-content ads that blend in seamlessly", Intent(this, NativeAdDemoMenuActivity::class.java)),
                DemoMenuItem("Banners", "320x50 Classic banner ads", Intent(this, BannerDemoMenuActivity::class.java)),
                DemoMenuItem("MRecs", "Please reference banners demo"),
                DemoMenuItem("Event Tracking", "Track in-app events for your users", Intent(this, EventTrackingActivity::class.java)),
                DemoMenuItem("Resources", "https://support.applovin.com/support/home", Intent(Intent.ACTION_VIEW, Uri.parse("https://support.applovin.com/support/home"))),
                DemoMenuItem("Contact", "support@applovin.com", makeContactIntent())
        )
    }

    private fun checkSdkKey(): Boolean
    {
        val sdkKey = AppLovinSdk.getInstance(applicationContext).sdkKey
        if ("YOUR_SDK_KEY".equals(sdkKey, ignoreCase = true))
        {
            AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("Please update your sdk key in the manifest file.")
                    .setCancelable(false)
                    .setNeutralButton("OK", null)
                    .show()

            return false
        }

        return true
    }

    private fun maybePromptConfigFlags()
    {
        val sharedPrefs = getSharedPreferences(KEY_SHARED_PREFERENCES_NAMESPACE, Context.MODE_PRIVATE)
        if (!sharedPrefs.getBoolean(KEY_PROMPTED_CONFIG_FLAGS, false))
        {
            AlertDialog.Builder(this)
                    .setTitle("IF you are using Android SDK 6.4.0 or above")
                    .setMessage("In your manifest file, please set the \"android:configChanges\" attribute for com.applovin.adview.AppLovinInterstitialActivity to be \"orientation|screenSize\"")
                    .setCancelable(false)
                    .setNeutralButton("OK", null)
                    .show()

            sharedPrefs.edit().putBoolean(KEY_PROMPTED_CONFIG_FLAGS, true).apply()
        }
    }

    // Mute Toggling

    /**
     * Toggling the sdk mute setting will affect whether your video ads begin in a muted state or not.
     */
    private fun toggleMute()
    {
        val sdk = AppLovinSdk.getInstance(applicationContext)
        sdk.settings.isMuted = !sdk.settings.isMuted
        muteToggleMenuItem.icon = getMuteIconForCurrentSdkMuteSetting()
    }

    private fun getMuteIconForCurrentSdkMuteSetting(): Drawable
    {
        val sdk = AppLovinSdk.getInstance(applicationContext)
        val drawableId = if (sdk.settings.isMuted) R.drawable.mute else R.drawable.unmute

        if (Build.VERSION.SDK_INT >= 22)
        {
            return resources.getDrawable(drawableId, theme)
        }
        else
        {
            @Suppress("DEPRECATION")
            return resources.getDrawable(drawableId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        muteToggleMenuItem = menu.findItem(R.id.action_toggle_mute).apply {
            icon = getMuteIconForCurrentSdkMuteSetting()
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.action_toggle_mute)
        {
            toggleMute()
        }

        return true
    }
}
