package com.applovin.apps.demoapp;

/**
 * Created by thomasso on 10/5/15.
 */
public class DemoMenuItem
{
    private final String title;
    private final String subtitle;

    public DemoMenuItem(final String title, final String subtitle)
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