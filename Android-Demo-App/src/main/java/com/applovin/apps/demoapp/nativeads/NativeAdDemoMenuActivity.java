package com.applovin.apps.demoapp.nativeads;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;

import com.applovin.apps.demoapp.DemoMenuActivity;
import com.applovin.apps.demoapp.DemoMenuItem;

public class NativeAdDemoMenuActivity
        extends DemoMenuActivity
{

    @Override
    protected void setupListViewFooter()
    {
        TextView footer = new TextView( getApplicationContext() );
        footer.setPadding( 0, 20, 0, 0 );
        footer.setGravity( Gravity.CENTER );
        footer.setTextSize( 18 );
        footer.setTextColor( Color.RED );
        footer.setText( Html.fromHtml( "You must turn <b>ON</b> \" Native Ads\" in your AppLovin dashboard" ) );

        listView.addFooterView( footer );
        listView.setFooterDividersEnabled( false );
    }

    @Override
    protected DemoMenuItem[] getListViewContents()
    {
        DemoMenuItem[] result = {
                new DemoMenuItem( "Single ad", "Programmatically load an ad using our open-source carousel view", new Intent( this, NativeAdCarouselUIActivity.class ) ),
                new DemoMenuItem( "Multiple ads", "Simple native ads in a RecyclerView", new Intent( this, NativeAdRecyclerViewActivity.class ) )
        };
        return result;
    }
}
