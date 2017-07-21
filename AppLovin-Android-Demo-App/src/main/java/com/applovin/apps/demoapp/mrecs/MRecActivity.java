package com.applovin.apps.demoapp.mrecs;


import com.applovin.adview.AppLovinAdView;
import com.applovin.apps.demoapp.AdStatusActivity;
import com.applovin.apps.demoapp.R;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by monica ong on 7/20/17.
 */

public final class MRecActivity
        extends AdStatusActivity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mrec );

        adStatusTextView = (TextView) findViewById( R.id.status_label );

        // Create MRec
        final AppLovinAdView adView = new AppLovinAdView( AppLovinAdSize.MREC, this );

        final ViewGroup rootView = (ViewGroup) findViewById( android.R.id.content );
        rootView.addView( adView );

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams( com.applovin.sdk.AppLovinSdkUtils.dpToPx( this, AppLovinAdSize.MREC.getWidth() ), com.applovin.sdk.AppLovinSdkUtils.dpToPx( this, AppLovinAdSize.MREC.getHeight() ) );
        layoutParams.topMargin = (int) (80 * Resources.getSystem().getDisplayMetrics().density);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        adView.setLayoutParams( layoutParams );

        // Set up load button
        final Button loadButton = (Button) findViewById( R.id.load_button );

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
                log( "MRec loaded" );
            }

            @Override
            public void failedToReceiveAd(final int errorCode)
            {
                // Look at AppLovinErrorCodes.java for list of error codes
                log( "MRec failed to load with error code " + errorCode );
            }
        } );

        adView.setAdDisplayListener( new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(final AppLovinAd ad)
            {
                log( "MRec Displayed" );
            }

            @Override
            public void adHidden(final AppLovinAd ad)
            {
                log( "MRec Hidden" );
            }
        } );

        adView.setAdClickListener( new AppLovinAdClickListener() {
            @Override
            public void adClicked(final AppLovinAd ad)
            {
                log( "MRec Clicked" );
            }
        } );

        //
        // Please note that the AppLovinAdView CAN AUTOMATICALLY invoke loadNextAd() upon inflation from layout
        // To do so, add the following attributes to the com.applovin.adview.AppLovinAdView element:
        //
        // xmlns:demo="http://schemas.applovin.com/android/1.0"
        // demo:loadAdOnCreate="true"
        //
    }
}
