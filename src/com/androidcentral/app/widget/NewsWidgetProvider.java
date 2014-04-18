package com.androidcentral.app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;
import com.androidcentral.app.ArticlePagerActivity;
import com.androidcentral.app.util.UiUtils;

public class NewsWidgetProvider
  extends AppWidgetProvider
{
  public static final String REFRESH_ACTION = "com.androidcentral.app.widget.REFRESH_ACTION";
  private static final String TAG = "NewsWidgetProvider";
  
  public static RemoteViews getRemoteViews(Context paramContext, int paramInt, boolean paramBoolean)
  {
    Intent localIntent1 = new Intent(paramContext, NewsWidgetService.class);
    localIntent1.putExtra("appWidgetId", paramInt);
    localIntent1.setData(Uri.parse(localIntent1.toUri(1)));
    String str = paramContext.getPackageName();
    int i;
    RemoteViews localRemoteViews;
    int j;
    if (UiUtils.isNightTheme(paramContext))
    {
      i = 2130903088;
      localRemoteViews = new RemoteViews(str, i);
      if (!paramBoolean) {
        break label206;
      }
      j = 4;
      label69:
      localRemoteViews.setViewVisibility(2131099759, j);
      if (!paramBoolean) {
        break label212;
      }
    }
    label206:
    label212:
    for (int k = 0;; k = 4)
    {
      localRemoteViews.setViewVisibility(2131099760, k);
      localRemoteViews.setRemoteAdapter(2131099761, localIntent1);
      Intent localIntent2 = new Intent(paramContext, ArticlePagerActivity.class);
      TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(paramContext);
      localTaskStackBuilder.addParentStack(ArticlePagerActivity.class);
      localTaskStackBuilder.addNextIntent(localIntent2);
      localRemoteViews.setPendingIntentTemplate(2131099761, localTaskStackBuilder.getPendingIntent(0, 134217728));
      Intent localIntent3 = new Intent(paramContext, NewsWidgetProvider.class);
      localIntent3.setAction("com.androidcentral.app.widget.REFRESH_ACTION");
      localIntent3.putExtra("appWidgetId", paramInt);
      localRemoteViews.setOnClickPendingIntent(2131099759, PendingIntent.getBroadcast(paramContext, 0, localIntent3, 134217728));
      return localRemoteViews;
      i = 2130903089;
      break;
      j = 0;
      break label69;
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.d("NewsWidgetProvider", "onReceive called");
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
    if (paramIntent.getAction().equals("com.androidcentral.app.widget.REFRESH_ACTION")) {
      localAppWidgetManager.notifyAppWidgetViewDataChanged(paramIntent.getIntExtra("appWidgetId", 0), 2131099761);
    }
    super.onReceive(paramContext, paramIntent);
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    Log.d("NewsWidgetProvider", "widget provider onUpdate called");
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length)
      {
        super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
        return;
      }
      paramAppWidgetManager.updateAppWidget(paramArrayOfInt[i], getRemoteViews(paramContext, paramArrayOfInt[i], false));
      paramAppWidgetManager.notifyAppWidgetViewDataChanged(paramArrayOfInt[i], 2131099761);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.widget.NewsWidgetProvider
 * JD-Core Version:    0.7.0.1
 */