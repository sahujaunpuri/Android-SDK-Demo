package com.applovin.apps.kotlindemoapp.interstitials

import android.os.Bundle
import com.applovin.adview.AppLovinInterstitialAd
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import com.applovin.sdk.*
import kotlinx.android.synthetic.main.activity_interstitial_simple.*
import java.lang.ref.WeakReference

class InterstitialSingleInstanceActivity : AdStatusActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_simple)

        adStatusTextView = status_label

        val weakRef = WeakReference(this)
        val sdk = AppLovinSdk.getInstance(applicationContext)

        loadButton.setOnClickListener {
            if (AppLovinInterstitialAd.isAdReadyToDisplay(weakRef.get())) {
                val interstitialAdDialog = AppLovinInterstitialAd.create(sdk, weakRef.get())

                //
                // Optional: Set ad load, ad display, ad click, and ad video playback callback listeners
                //
                interstitialAdDialog.setAdLoadListener(object : AppLovinAdLoadListener {
                    override fun adReceived(appLovinAd: AppLovinAd) {
                        log("Interstitial loaded")
                    }

                    override fun failedToReceiveAd(errorCode: Int) {

                        // Look at AppLovinErrorCodes.java for list of error codes

                        if (errorCode == AppLovinErrorCodes.NO_FILL) {
                            log("No-fill: No ads are currently available for this device/country")
                        } else {
                            log("Interstitial failed to load with error code " + errorCode)
                        }
                    }
                })

                interstitialAdDialog.setAdDisplayListener(object : AppLovinAdDisplayListener {
                    override fun adDisplayed(appLovinAd: AppLovinAd) {
                        log("Interstitial Displayed")
                    }

                    override fun adHidden(appLovinAd: AppLovinAd) {
                        log("Interstitial Hidden")
                    }
                })

                interstitialAdDialog.setAdClickListener({ log("Interstitial Clicked") })

                // This will only ever be used if you have video ads enabled.
                interstitialAdDialog.setAdVideoPlaybackListener(object : AppLovinAdVideoPlaybackListener {
                    override fun videoPlaybackBegan(appLovinAd: AppLovinAd) {
                        log("Video Started")
                    }

                    override fun videoPlaybackEnded(appLovinAd: AppLovinAd, percentViewed: Double, wasFullyViewed: Boolean) {
                        log("Video Ended")
                    }
                })

                /*
                     NOTE: We recommend the use of placements (AFTER creating them in your dashboard):

                     interstitialAdDialog.showAndRender(currentAd, "MANUAL_LOADING_SCREEN");

                     To learn more about placements, check out https://applovin.com/integration#androidPlacementsIntegration
                    */
                interstitialAdDialog.show()
            } else {
                // Ideally, the SDK preloads ads when you initialize it in your launch activity
                // you can manually load an ad as demonstrated in InterstitialManualLoadingActivity
                log("Interstitial not ready for display.\nPlease check SDK key or internet connection.")
            }
        }
    }
}
