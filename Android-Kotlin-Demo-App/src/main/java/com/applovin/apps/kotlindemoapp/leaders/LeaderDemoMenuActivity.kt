package com.applovin.apps.kotlindemoapp.leaders

import android.content.Intent
import com.applovin.apps.kotlindemoapp.DemoMenuActivity
import com.applovin.apps.kotlindemoapp.DemoMenuItem

class LeaderDemoMenuActivity : DemoMenuActivity()
{
    override fun getListViewContents(): Array<DemoMenuItem> = arrayOf(
            DemoMenuItem("Programmatic", "Programmatically create an instance of it", Intent(this, LeaderProgrammaticActivity::class.java)),
            DemoMenuItem("Layout Editor", "Create a Leader from the layout editor", Intent(this, LeaderLayoutEditorActivity::class.java))
    )
}
