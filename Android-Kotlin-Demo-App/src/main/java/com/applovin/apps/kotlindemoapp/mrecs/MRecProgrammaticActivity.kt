package com.applovin.apps.kotlindemoapp.mrecs


import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView

import com.applovin.adview.AppLovinAdView
import com.applovin.adview.AppLovinAdViewDisplayErrorCode
import com.applovin.adview.AppLovinAdViewEventListener
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdClickListener
import com.applovin.sdk.AppLovinAdDisplayListener
import com.applovin.sdk.AppLovinAdLoadListener
import com.applovin.sdk.AppLovinAdSize
import com.applovin.sdk.AppLovinSdkUtils

/**
 * Created by monica ong on 7/20/17.
 */

class MRecProgrammaticActivity : AdStatusActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mrec_programmatic)

        adStatusTextView = findViewById(R.id.status_label) as TextView

        // Create MRec
        val adView = AppLovinAdView(AppLovinAdSize.MREC, this)

        val rootView = findViewById(android.R.id.content) as ViewGroup
        rootView.addView(adView)

        val layoutParams = FrameLayout.LayoutParams(AppLovinSdkUtils.dpToPx(this, AppLovinAdSize.MREC.width), AppLovinSdkUtils.dpToPx(this, AppLovinAdSize.MREC.height))
        layoutParams.topMargin = AppLovinSdkUtils.dpToPx(this, 80)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL

        adView.setLayoutParams(layoutParams)

        // Set up load button
        val loadButton = findViewById(R.id.load_button) as Button

        loadButton.setOnClickListener { adView.loadNextAd() }

        //
        // Optional: Set listeners
        //
        adView.setAdLoadListener(object : AppLovinAdLoadListener {
            override fun adReceived(ad: AppLovinAd) {
                log("MRec loaded")
            }

            override fun failedToReceiveAd(errorCode: Int) {
                // Look at AppLovinErrorCodes.java for list of error codes
                log("MRec failed to load with error code " + errorCode)
            }
        })

        adView.setAdDisplayListener(object : AppLovinAdDisplayListener {
            override fun adDisplayed(ad: AppLovinAd) {
                log("MRec Displayed")
            }

            override fun adHidden(ad: AppLovinAd) {
                log("MRec Hidden")
            }
        })

        adView.setAdClickListener(AppLovinAdClickListener { log("MRec Clicked") })

        adView.setAdViewEventListener(object : AppLovinAdViewEventListener {
            override fun adOpenedFullscreen(ad: AppLovinAd, adView: AppLovinAdView) {
                log("MRec opened fullscreen")
            }

            override fun adClosedFullscreen(ad: AppLovinAd, adView: AppLovinAdView) {
                log("MRec closed fullscreen")
            }

            override fun adLeftApplication(ad: AppLovinAd, adView: AppLovinAdView) {
                log("MRec left application")
            }

            override fun adFailedToDisplay(ad: AppLovinAd, adView: AppLovinAdView, code: AppLovinAdViewDisplayErrorCode) {
                log("MRec failed to display with error code " + code)
            }
        })
    }
}
