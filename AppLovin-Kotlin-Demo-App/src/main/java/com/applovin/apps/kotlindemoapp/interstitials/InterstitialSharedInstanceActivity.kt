package com.applovin.apps.kotlindemoapp.interstitials

import android.os.Bundle
import com.applovin.adview.AppLovinInterstitialAd
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import kotlinx.android.synthetic.main.activity_interstitial_simple.*
import java.lang.ref.WeakReference

class InterstitialSharedInstanceActivity : AdStatusActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_simple)

        adStatusTextView = status_label

        val weakRef = WeakReference(this)

        loadButton.setOnClickListener {
            if (AppLovinInterstitialAd.isAdReadyToDisplay(weakRef.get()))
            {
                // If you want to set the ad load, ad display, ad click, or video playback callback listeners, use one of the other methods to show

                /*
                     NOTE: We recommend the use of placements (AFTER creating them in your dashboard):

                     AppLovinInterstitialAd.show(weakRef.get(), "SHARED_INSTANCE_SCREEN");

                     To learn more about placements, check out https://applovin.com/integration#androidPlacementsIntegration
                    */
                AppLovinInterstitialAd.show(weakRef.get())
                log("Interstitial Displayed")
            }
            else
            {
                // Ideally, the SDK preloads ads when you initialize it in your launch activity
                // you can manually load an ad as demonstrated in InterstitialManualLoadingActivity
                log("Interstitial not ready for display.\nPlease check SDK key or internet connection.")
            }
        }
    }
}
