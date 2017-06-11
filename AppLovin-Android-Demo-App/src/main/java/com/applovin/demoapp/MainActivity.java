package com.applovin.demoapp;

import com.applovin.apps.demoapp.R;
import com.applovin.demoapp.eventtracking.EventTrackingActivity;
import com.applovin.demoapp.rewarded.RewardedVideosActivity;
import com.applovin.sdk.AppLovinSdk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by thomasso on 10/5/15.
 */
public class MainActivity
        extends BaseActivity
{
    private static final String KEY_SHARED_PREFERENCES_NAMESPACE = "com.applovin.apps.demo.shared_preferences";
    private static final String KEY_PROMPTED_CONFIG_FLAGS        = "com.applovin.apps.demo.shared_preferences.prompted_config_flags";

    private static final int    POSITION_INTERSTITIALS           = 0;
    private static final int    POSITION_INCENTIVIZED            = 1;
    private static final int    POSITION_NATIVE                  = 2;
    private static final int    POSITION_BANNER                  = 3;
    private static final int    POSITION_EVENT                   = 5;
    private static final int    POSITION_RESOURCES               = 6;
    private static final int    POSITION_CONTACT                 = 7;

    private ListView            listView;
    private ListItem[]          items                            = {
            new ListItem( "Interstitials", "Full screen ads. Graphic or video" ),
            new ListItem( "Rewarded Videos (Incentivized Ads)", "Reward your users for watching these on-demand videos" ),
            new ListItem( "Native Ads", "In-content ads that blend in seamlessly" ),
            new ListItem( "Banners", "320x50 Classic banner ads" ),
            new ListItem( "MRecs", "Please reference banners demo" ),
            new ListItem( "Event Tracking", "Track in-app events for your users" ),
            new ListItem( "Resources", "https://support.applovin.com/support/home" ),
            new ListItem( "Contact", "support@applovin.com" )
    };

    private MenuItem            muteToggleMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //
        // Initializing our SDK at launch is very important as it'll start preloading ads in the background.
        //
        AppLovinSdk.initializeSdk( getApplicationContext() );

        // Warn user if SDK key is invalid
        boolean isLegitSdkKey = checkSdkKey();

        // Prompt to add config flags if SDK key is legit
        if ( isLegitSdkKey )
        {
            maybePromptConfigFlags();
        }

        // Initialize list view
        listView = (ListView) findViewById( R.id.listView );
        setupListViewFooter();

        ArrayAdapter<ListItem> listAdapter = new ArrayAdapter<ListItem>( this, android.R.layout.simple_list_item_2, items ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View row = convertView;
                if ( row == null )
                {
                    LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    row = inflater.inflate( android.R.layout.simple_list_item_2, parent, false );
                }

                ListItem item = items[position];

                TextView title = (TextView) row.findViewById( android.R.id.text1 );
                title.setText( item.getTitle() );
                TextView subtitle = (TextView) row.findViewById( android.R.id.text2 );
                subtitle.setText( item.getSubtitle() );

                return row;
            }
        };
        listView.setAdapter( listAdapter );


        // If ListActivity -> @Override public void onListItemClick( . . . .
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if ( position == POSITION_INTERSTITIALS )
                {
                    Intent intent = new Intent( MainActivity.this, InterstitialListViewActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_INCENTIVIZED )
                {
                    Intent intent = new Intent( MainActivity.this, RewardedVideosActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_NATIVE )
                {
                    Intent intent = new Intent( MainActivity.this, NativeAdListViewActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_BANNER )
                {
                    Intent intent = new Intent( MainActivity.this, BannerListViewActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_EVENT )
                {
                    Intent intent = new Intent( MainActivity.this, EventTrackingActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_RESOURCES )
                {
                    openSupportWebpage();
                }
                else if ( position == POSITION_CONTACT )
                {
                    openContactSupport();
                }
            }
        };
        listView.setOnItemClickListener( itemClickListener );
    }

    private boolean checkSdkKey()
    {
        final String sdkKey = AppLovinSdk.getInstance( getApplicationContext() ).getSdkKey();
        if ( "YOUR_SDK_KEY".equalsIgnoreCase( sdkKey ) )
        {
            new AlertDialog.Builder( this )
                    .setTitle( "ERROR" )
                    .setMessage( "Please update your sdk key in the manifest file." )
                    .setCancelable( false )
                    .setNeutralButton( "OK", null )
                    .show();

            return false;
        }

        return true;
    }

    private void maybePromptConfigFlags()
    {
        final SharedPreferences sharedPrefs = getSharedPreferences( KEY_SHARED_PREFERENCES_NAMESPACE, Context.MODE_PRIVATE );
        if ( !sharedPrefs.getBoolean( KEY_PROMPTED_CONFIG_FLAGS, false ) )
        {
            new AlertDialog.Builder( this )
                    .setTitle( "IF you are using Android SDK 6.4.0 or above" )
                    .setMessage( "In your manifest file, please set the \"android:configChanges\" attribute for com.applovin.adview.AppLovinInterstitialActivity to be \"orientation|screenSize\"" )
                    .setCancelable( false )
                    .setNeutralButton( "OK", null )
                    .show();

            sharedPrefs.edit().putBoolean( KEY_PROMPTED_CONFIG_FLAGS, true ).apply();
        }
    }

    // Mute Toggling

    /**
     * Toggling the sdk mute setting will affect whether your video ads begin in a muted state or not.
     */
    private void toggleMute()
    {
        AppLovinSdk sdk = AppLovinSdk.getInstance( getApplicationContext() );
        sdk.getSettings().setMuted( !sdk.getSettings().isMuted() );
        muteToggleMenuItem.setIcon( getMuteIconForCurrentSdkMuteSetting() );
    }

    private Drawable getMuteIconForCurrentSdkMuteSetting()
    {
        AppLovinSdk sdk = AppLovinSdk.getInstance( getApplicationContext() );
        int drawableId = sdk.getSettings().isMuted() ? R.drawable.mute : R.drawable.unmute;

        if ( Build.VERSION.SDK_INT >= 22 )
        {
            return getResources().getDrawable( drawableId, getTheme() );
        }
        else
        {
            return getResources().getDrawable( drawableId );
        }
    }

    public void setupListViewFooter()
    {
        String appVersion = "";
        try
        {
            PackageInfo pInfo = getPackageManager().getPackageInfo( getPackageName(), 0 );
            appVersion = pInfo.versionName;
        }
        catch ( PackageManager.NameNotFoundException e )
        {
            e.printStackTrace();
        }

        final String versionName = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        final int apiLevel = Build.VERSION.SDK_INT;

        TextView footer = new TextView( getApplicationContext() );
        footer.setTextColor( Color.GRAY );
        footer.setPadding( 0, 20, 0, 0 );
        footer.setGravity( Gravity.CENTER );
        footer.setTextSize( 18 );
        footer.setText( "\nApp Version: " + appVersion +
                "\nSDK Version: " + AppLovinSdk.VERSION +
                "\nOS Version: " + versionName + "(API " + apiLevel + ")" );

        listView.addFooterView( footer );
        listView.setFooterDividersEnabled( false );
    }

    public void openSupportWebpage()
    {
        final String url = "https://support.applovin.com/support/home";
        startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( url ) ) );
    }

    public void openContactSupport()
    {
        Intent intent = new Intent( Intent.ACTION_SENDTO );
        intent.setType( "text/plain" );
        intent.setData( Uri.parse( "mailto:" + "support@applovin.com" ) );
        intent.putExtra( Intent.EXTRA_SUBJECT, "Android SDK support" );
        intent.putExtra( Intent.EXTRA_TEXT, "\n\n\n---\nSDK Version: " + AppLovinSdk.VERSION );
        startActivity( Intent.createChooser( intent, "Send Email" ) );
    }

    // Options Menu Functions

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        muteToggleMenuItem = menu.findItem( R.id.action_toggle_mute );
        muteToggleMenuItem.setIcon( getMuteIconForCurrentSdkMuteSetting() );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if ( item.getItemId() == R.id.action_toggle_mute )
        {
            toggleMute();
        }

        return true;
    }

    private class ListItem
    {
        private final String title;
        private final String subtitle;

        ListItem(final String title, final String subtitle)
        {
            this.title = title;
            this.subtitle = subtitle;
        }

        public String getTitle()
        {
            return title;
        }

        public String getSubtitle()
        {
            return subtitle;
        }
    }
}
