package com.applovin.apps.kotlindemoapp.nativeads.carouselui.cards

import android.content.Context
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import com.applovin.apps.kotlindemoapp.nativeads.carouselui.AppLovinCarouselView
import com.applovin.apps.kotlindemoapp.nativeads.carouselui.AppLovinCarouselViewSettings
import com.applovin.apps.kotlindemoapp.nativeads.carouselui.support.AppLovinSdkViewPagerAdapter
import com.applovin.apps.kotlindemoapp.nativeads.carouselui.support.SdkCenteredViewPager
import com.applovin.sdk.AppLovinSdk
import java.lang.ref.WeakReference

/**
 * This adapter manages instances of InlineCarouselCardView which are supplied to the view pager contained in AppLovinCarouselView.
 */
class InlineCarouselAdapter(private val context: Context, private val sdk: AppLovinSdk, private val parentView: AppLovinCarouselView) : AppLovinSdkViewPagerAdapter()
{
    private val existingCards: SparseArray<WeakReference<InlineCarouselCardView>> = SparseArray()

    override fun getView(newPosition: Int, pager: SdkCenteredViewPager): View
    {
        sdk.logger.d(TAG, "Adapter is creating a card for position " + newPosition)

        val slots = parentView.getNativeAds()
        if (slots != null && newPosition < slots.size)
        {
            val card = InlineCarouselCardView(context)
            card.sdk = sdk
            card.ad = slots[newPosition]
            card.cardState = parentView.getCardState(newPosition)
            card.setUpView()

            val params = SdkCenteredViewPager.LayoutParams()
            params.width = SdkCenteredViewPager.LayoutParams.MATCH_PARENT
            params.height = SdkCenteredViewPager.LayoutParams.MATCH_PARENT
            params.gravity = Gravity.CENTER

            card.layoutParams = params

            existingCards.append(newPosition, WeakReference(card))

            return card
        }
        else
        {
            sdk.logger.e(TAG, "Unable to render widget slot: Requested position does not exist.")
            return View(context)
        }
    }

    override fun getCount(): Int
    {
        val slots = parentView.getNativeAds()

        val count = slots.size
        if (count <= 1)
        {
            sdk.logger.e(TAG, "Asked to render a view pager but only one slot is available!")
            return 0
        }
        else
        {
            return slots.size
        }
    }

    fun getExistingCard(key: Int): WeakReference<InlineCarouselCardView>?
    {
        return existingCards.get(key)
    }

    override fun getPageWidth(position: Int): Float
    {
        return AppLovinCarouselViewSettings.VIEW_PAGER_CARD_WIDTH
    }

    fun destroyCards()
    {
        sdk.logger.d(TAG, "Destroying all owned cards")
        (0..existingCards.size() - 1)
                .mapNotNull { existingCards.get(it) }
                .map { it.get() }
                .forEach { it?.destroy() }
    }

    companion object
    {
        private val TAG = "InlineCarouselAdapter"
    }
}
