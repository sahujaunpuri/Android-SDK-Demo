package com.applovin.apps.demoapp.interstitials;

import android.content.Intent;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class InterstitialDemoMenuActivity
        extends DemoMenuActivity
{
    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Single instance", "Programmatically creating an instance of it", new Intent( this, InterstitialSingleInstanceActivity.class ) ),
                new DemoMenuItem( "Shared instance", "Use the shared instance", new Intent( this, InterstitialSharedInstanceActivity.class ) ),
                new DemoMenuItem( "Manually loading ad", "Use this for greater control over the ad load process", new Intent( this, InterstitialManualLoadingActivity.class ) )
        };
        return result;
    }
}
