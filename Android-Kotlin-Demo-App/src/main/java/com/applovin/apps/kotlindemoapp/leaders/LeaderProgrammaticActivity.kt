package com.applovin.apps.kotlindemoapp.leaders

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.applovin.adview.AppLovinAdView
import com.applovin.adview.AppLovinAdViewDisplayErrorCode
import com.applovin.adview.AppLovinAdViewEventListener
import com.applovin.apps.kotlindemoapp.AdStatusActivity
import com.applovin.apps.kotlindemoapp.R
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdDisplayListener
import com.applovin.sdk.AppLovinAdLoadListener
import com.applovin.sdk.AppLovinAdSize
import kotlinx.android.synthetic.main.activity_leader_programmatic.*

class LeaderProgrammaticActivity : AdStatusActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_programmatic)

        adStatusTextView = status_label

        val adView = AppLovinAdView(AppLovinAdSize.LEADER, this)

        load_button.setOnClickListener { adView.loadNextAd() }

        //
        // Optional: Set listeners
        //
        adView.setAdLoadListener(object : AppLovinAdLoadListener
                                 {
                                     override fun adReceived(ad: AppLovinAd)
                                     {
                                         log("Leader loaded")
                                     }

                                     override fun failedToReceiveAd(errorCode: Int)
                                     {
                                         // Look at AppLovinErrorCodes.java for list of error codes
                                         log("Leader failed to load with error code $errorCode")
                                     }
                                 })

        adView.setAdDisplayListener(object : AppLovinAdDisplayListener
                                    {
                                        override fun adDisplayed(ad: AppLovinAd)
                                        {
                                            log("Leader Displayed")
                                        }

                                        override fun adHidden(ad: AppLovinAd)
                                        {
                                            log("Leader Hidden")
                                        }
                                    })

        adView.setAdClickListener { log("Banner Clicked") }

        adView.setAdViewEventListener(object : AppLovinAdViewEventListener
                                      {
                                          override fun adOpenedFullscreen(ad: AppLovinAd, adView: AppLovinAdView)
                                          {
                                              log("Leader opened fullscreen")
                                          }

                                          override fun adClosedFullscreen(ad: AppLovinAd, adView: AppLovinAdView)
                                          {
                                              log("Leader closed fullscreen")
                                          }

                                          override fun adLeftApplication(ad: AppLovinAd, adView: AppLovinAdView)
                                          {
                                              log("Leader left application")
                                          }

                                          override fun adFailedToDisplay(ad: AppLovinAd, adView: AppLovinAdView, code: AppLovinAdViewDisplayErrorCode)
                                          {
                                              log("Leader failed to display with error code $code")
                                          }
                                      })

        // Add programmatically created banner into our container
        leader_container.addView(adView, android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER))

        // Load an ad!
        adView.loadNextAd()
    }
}