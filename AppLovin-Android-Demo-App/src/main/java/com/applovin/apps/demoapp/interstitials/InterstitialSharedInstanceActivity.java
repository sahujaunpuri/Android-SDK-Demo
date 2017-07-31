package com.applovin.apps.demoapp.interstitials;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.apps.demoapp.AdStatusActivity;
import com.applovin.apps.demoapp.R;

/**
 * Created by thomasso on 10/5/15.
 */

public class InterstitialSharedInstanceActivity
        extends AdStatusActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_interstitial_simple );

        adStatusTextView = (TextView) findViewById( R.id.status_label );

        final Button showButton = (Button) findViewById( R.id.loadButton );
        showButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( AppLovinInterstitialAd.isAdReadyToDisplay( InterstitialSharedInstanceActivity.this ) )
                {
                    /*
                     NOTE: We recommend the use of placements (AFTER creating them in your dashboard):

                     AppLovinInterstitialAd.show(weakRef.get(), "SHARED_INSTANCE_SCREEN");

                     To learn more about placements, check out https://applovin.com/integration#androidPlacementsIntegration
                    */
                    AppLovinInterstitialAd.show( InterstitialSharedInstanceActivity.this );
                    log( "Interstitial Displayed" );
                }
                else
                {
                    // Ideally, the SDK preloads ads when you initialize it in your launch activity
                    // you can manually load an ad as demonstrated in InterstitialManualLoadingActivity
                    log( "Interstitial not ready for display.\nPlease check SDK key or internet connection." );
                }
            }
        } );
    }
}
