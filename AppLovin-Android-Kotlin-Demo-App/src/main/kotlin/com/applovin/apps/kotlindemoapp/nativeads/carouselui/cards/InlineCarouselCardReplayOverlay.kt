package com.applovin.apps.kotlindemoapp.nativeads.carouselui.cards

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.applovin.apps.kotlindemoapp.R


/**
 * Created by mszaro on 4/21/15.
 */
class InlineCarouselCardReplayOverlay : LinearLayout
{
    var replayClickListener: View.OnClickListener? = null
    var learnMoreClickListener: View.OnClickListener? = null

    private var replayLayout: LinearLayout? = null
    private var learnMoreLayout: LinearLayout? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setUpView()
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.applovin_card_replay_overlay, this, true)

        bindViews()
        initializeView()
    }

    private fun bindViews()
    {
        replayLayout = findViewById<View>(R.id.applovin_card_overlay_replay_layout) as LinearLayout
        learnMoreLayout = findViewById<View>(R.id.applovin_card_overlay_learn_more_layout) as LinearLayout
    }

    private fun initializeView()
    {
        replayLayout!!.setOnClickListener(replayClickListener)
        learnMoreLayout!!.setOnClickListener(learnMoreClickListener)
    }
}
