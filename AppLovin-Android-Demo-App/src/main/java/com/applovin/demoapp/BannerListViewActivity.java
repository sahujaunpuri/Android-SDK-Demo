package com.applovin.demoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applovin.apps.demoapp.R;
import com.applovin.demoapp.banners.LayoutEditorBannerActivity;
import com.applovin.demoapp.banners.ProgrammaticBannerActivity;

public class BannerListViewActivity
        extends BaseActivity
{
    private static final int POSITION_PROGRAMMATIC  = 0;
    private static final int POSITION_LAYOUT_EDITOR = 1;

    private ListItem[] items = {
            new ListItem( "Programmatic", "Programmatically creating an instance of it" ),
            new ListItem( "Layout Editor", "Create a banner from the layout editor" ),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_interstitial_list_view );

        ListView listView = (ListView) findViewById( R.id.listView );
        ArrayAdapter<ListItem> listAdapter = new ArrayAdapter<ListItem>( this, android.R.layout.simple_list_item_2, items )
        {
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

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                if ( position == POSITION_PROGRAMMATIC )
                {
                    Intent intent = new Intent( BannerListViewActivity.this, ProgrammaticBannerActivity.class );
                    startActivity( intent );
                }
                else if ( position == POSITION_LAYOUT_EDITOR )
                {
                    Intent intent = new Intent( BannerListViewActivity.this, LayoutEditorBannerActivity.class );
                    startActivity( intent );
                }
            }
        };
        listView.setOnItemClickListener( itemClickListener );
    }
}
