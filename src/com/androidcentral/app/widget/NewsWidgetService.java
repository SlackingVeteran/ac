package com.androidcentral.app.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class NewsWidgetService
  extends RemoteViewsService
{
  public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent paramIntent)
  {
    return new NewsRemoteViewsFactory(getApplicationContext(), paramIntent);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.widget.NewsWidgetService
 * JD-Core Version:    0.7.0.1
 */