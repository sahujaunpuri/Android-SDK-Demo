package com.applovin.apps.kotlindemoapp.banners

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.applovin.adview.AppLovinAdView
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdDisplayListener
import com.applovin.sdk.AppLovinAdLoadListener
import com.applovin.sdk.AppLovinAdSize
import kotlinx.android.synthetic.main.activity_banner_programmatic.*

class BannerProgrammaticActivity : AdStatusActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_programmatic)

        adStatusTextView = status_label

        val adView = AppLovinAdView(AppLovinAdSize.BANNER, this)

        load_button.setOnClickListener { adView.loadNextAd() }

        //
        // Optional: Set listeners
        //
        adView.setAdLoadListener(object : AppLovinAdLoadListener
                                 {
                                     override fun adReceived(ad: AppLovinAd)
                                     {
                                         log("Banner loaded")
                                     }

                                     override fun failedToReceiveAd(errorCode: Int)
                                     {
                                         // Look at AppLovinErrorCodes.java for list of error codes
                                         log("Banner failed to load with error code " + errorCode)
                                     }
                                 })

        adView.setAdDisplayListener(object : AppLovinAdDisplayListener
                                    {
                                        override fun adDisplayed(ad: AppLovinAd)
                                        {
                                            log("Banner Displayed")
                                        }

                                        override fun adHidden(ad: AppLovinAd)
                                        {
                                            log("Banner Hidden")
                                        }
                                    })

        adView.setAdClickListener { log("Banner Clicked") }

        // Add programmatically created banner into our container
        banner_container.addView(adView, android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER))

        // Load an ad!
        adView.loadNextAd()
    }
}
