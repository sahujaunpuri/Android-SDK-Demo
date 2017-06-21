package com.applovin.apps.kotlindemoapp.banners

import android.os.Bundle
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdDisplayListener
import com.applovin.sdk.AppLovinAdLoadListener
import kotlinx.android.synthetic.main.activity_banner_layout_editor.*

class BannerLayoutEditorActivity : AdStatusActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_layout_editor)

        adStatusTextView = status_label

        load_button.setOnClickListener { ad_view.loadNextAd() }

        //
        // Optional: Set listeners
        //
        ad_view.setAdLoadListener(object : AppLovinAdLoadListener {
            override fun adReceived(ad: AppLovinAd) {
                log("Banner loaded")
            }
            override fun failedToReceiveAd(errorCode: Int) {
                // Look at AppLovinErrorCodes.java for list of error codes
                log("Banner failed to load with error code " + errorCode)
            }
        })

        ad_view.setAdDisplayListener(object : AppLovinAdDisplayListener {
            override fun adDisplayed(ad: AppLovinAd) {
                log("Banner Displayed")
            }
            override fun adHidden(ad: AppLovinAd) {
                log("Banner Hidden")
            }
        })

        ad_view.setAdClickListener { log("Banner Clicked") }

        // Load an ad!
        ad_view.loadNextAd()

        //
        // Please note that the AppLovinAdView CAN AUTOMATICALLY invoke loadNextAd() upon inflation from layout
        // To do so, add the following attributes to the com.applovin.adview.AppLovinAdView element:
        //
        // xmlns:demo="http://schemas.applovin.com/android/1.0"
        // demo:loadAdOnCreate="true"
        //
    }
}
