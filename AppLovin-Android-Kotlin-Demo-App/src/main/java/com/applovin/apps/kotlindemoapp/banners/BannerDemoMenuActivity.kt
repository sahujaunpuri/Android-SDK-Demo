package com.applovin.apps.kotlindemoapp.banners

import android.content.Intent
import com.applovin.apps.kotlindemoapp.DemoMenuActivity
import com.applovin.apps.kotlindemoapp.DemoMenuItem

class BannerDemoMenuActivity : DemoMenuActivity()
{
    override fun getListViewContents(): Array<DemoMenuItem> = arrayOf(
            DemoMenuItem("Programmatic", "Programmatically creating an instance of it", Intent(this, BannerProgrammaticActivity::class.java)),
            DemoMenuItem("Layout Editor", "Create a banner from the layout editor", Intent(this, BannerLayoutEditorActivity::class.java)),
            DemoMenuItem("Zone Integration", "Create different user experiences of the same ad type", Intent(this, BannerZoneActivity::class.java))
    )
}
