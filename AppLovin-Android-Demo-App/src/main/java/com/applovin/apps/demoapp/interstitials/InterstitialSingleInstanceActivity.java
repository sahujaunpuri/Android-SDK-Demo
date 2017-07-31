package com.applovin.apps.demoapp.interstitials;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.apps.demoapp.AdStatusActivity;
import com.applovin.apps.demoapp.R;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;

/**
 * Created by thomasso on 10/5/15.
 */

public class InterstitialSingleInstanceActivity
        extends AdStatusActivity
        implements AppLovinAdLoadListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener
{
    private AppLovinInterstitialAdDialog interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_interstitial_simple );

        adStatusTextView = (TextView) findViewById( R.id.status_label );

        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( this ), this );

        final Button showButton = (Button) findViewById( R.id.loadButton );
        showButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //
                // Optional: Set ad load, ad display, ad click, and ad video playback callback listeners
                //
                interstitialAd.setAdLoadListener( InterstitialSingleInstanceActivity.this );
                interstitialAd.setAdDisplayListener( InterstitialSingleInstanceActivity.this );
                interstitialAd.setAdClickListener( InterstitialSingleInstanceActivity.this );
                interstitialAd.setAdVideoPlaybackListener( InterstitialSingleInstanceActivity.this ); // This will only ever be used if you have video ads enabled.

                /**
                 NOTE: We recommend the use of placements (AFTER creating them in your dashboard):

                 interstitialAd.showAndRender(currentAd, "INTER_LOADING_SCREEN");

                 To learn more about placements, check out https://applovin.com/integration#androidPlacementsIntegration
                 */
                interstitialAd.show();
            }
        } );
    }

    //
    // Ad Load Listener
    //
    @Override
    public void adReceived(AppLovinAd appLovinAd)
    {
        log( "Interstitial loaded" );
    }

    @Override
    public void failedToReceiveAd(int errorCode)
    {
        // Look at AppLovinErrorCodes.java for list of error codes
        if ( errorCode == AppLovinErrorCodes.NO_FILL )
        {
            log( "No-fill: No ads are currently available for this device/country" );
        }
        else
        {
            log( "Interstitial failed to load with error code " + errorCode );
        }
    }

    //
    // Ad Display Listener
    //
    @Override
    public void adDisplayed(AppLovinAd appLovinAd)
    {
        log( "Interstitial Displayed" );
    }

    @Override
    public void adHidden(AppLovinAd appLovinAd)
    {
        log( "Interstitial Hidden" );
    }

    //
    // Ad Click Listener
    //
    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        log( "Interstitial Clicked" );
    }

    //
    // Ad Video Playback Listener
    //
    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd)
    {
        log( "Video Started" );
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
    {
        log( "Video Ended" );
    }
}
