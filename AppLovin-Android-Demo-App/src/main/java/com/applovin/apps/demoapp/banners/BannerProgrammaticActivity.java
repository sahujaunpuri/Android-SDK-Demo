package com.applovin.apps.demoapp.banners;

import com.applovin.adview.AppLovinAdView;
import com.applovin.apps.demoapp.R;
import com.applovin.apps.demoapp.AdStatusActivity;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by thomasso on 3/6/17.
 */

public final class BannerProgrammaticActivity
        extends AdStatusActivity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_banner_programmatic );

        adStatusTextView = (TextView) findViewById( R.id.status_label );

        final AppLovinAdView adView = new AppLovinAdView( AppLovinAdSize.BANNER, this );

        Button loadButton = (Button) findViewById( R.id.load_button );

        loadButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                adView.loadNextAd();
            }
        } );

        //
        // Optional: Set listeners
        //
        adView.setAdLoadListener( new AppLovinAdLoadListener() {
            @Override
            public void adReceived(final AppLovinAd ad)
            {
                log( "Banner loaded" );
            }

            @Override
            public void failedToReceiveAd(final int errorCode)
            {
                // Look at AppLovinErrorCodes.java for list of error codes
                log( "Banner failed to load with error code " + errorCode );
            }
        } );

        adView.setAdDisplayListener( new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(final AppLovinAd ad)
            {
                log( "Banner Displayed" );
            }

            @Override
            public void adHidden(final AppLovinAd ad)
            {
                log( "Banner Hidden" );
            }
        } );

        adView.setAdClickListener( new AppLovinAdClickListener() {
            @Override
            public void adClicked(final AppLovinAd ad)
            {
                log( "Banner Clicked" );
            }
        } );

        // Add programmatically created banner into our container
        final LinearLayout bannerContainer = (LinearLayout) findViewById( R.id.banner_container );
        bannerContainer.addView( adView, new android.widget.FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER ) );

        // Load an ad!
        adView.loadNextAd();
    }
}
