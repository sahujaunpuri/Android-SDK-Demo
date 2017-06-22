package com.applovin.apps.kotlindemoapp.interstitials

import android.content.Intent
import com.applovin.apps.kotlindemoapp.DemoMenuActivity
import com.applovin.apps.kotlindemoapp.DemoMenuItem

class InterstitialDemoMenuActivity : DemoMenuActivity()
{
    override fun getListViewContents(): Array<DemoMenuItem> = arrayOf(
            DemoMenuItem("Single instance", "Programmatically creating an instance of it", Intent(this, InterstitialSingleInstanceActivity::class.java)),
            DemoMenuItem("Shared instance", "Use the shared instance", Intent(this, InterstitialSharedInstanceActivity::class.java)),
            DemoMenuItem("Manually loading ad", "Use this for greater control over the ad load process", Intent(this, InterstitialManualLoadingActivity::class.java))
    )
}
