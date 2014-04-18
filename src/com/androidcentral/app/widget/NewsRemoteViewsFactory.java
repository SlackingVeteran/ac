package com.androidcentral.app.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.net.ArticleUpdateHandler;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.PreferenceHelper;
import com.androidcentral.app.util.UiUtils;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class NewsRemoteViewsFactory
  implements RemoteViewsService.RemoteViewsFactory
{
  private static final String TAG = "NewsRemoteViewsFactory";
  private int appWidgetId;
  private List<Article> articles;
  private Context context;
  private NewsDataSource dataSource;
  private ImageLoader imageLoader;
  private MemoryCacheAware<String, Bitmap> memCache;
  BitmapFactory.Options options;
  
  public NewsRemoteViewsFactory(Context paramContext, Intent paramIntent)
  {
    this.context = paramContext;
    this.imageLoader = ImageLoader.getInstance();
    this.memCache = this.imageLoader.getMemoryCache();
    this.options = new BitmapFactory.Options();
    this.options.inSampleSize = 2;
    this.appWidgetId = paramIntent.getIntExtra("appWidgetId", 0);
  }
  
  public int getCount()
  {
    return 10;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public RemoteViews getLoadingView()
  {
    return null;
  }
  
  public RemoteViews getViewAt(int paramInt)
  {
    String str1 = this.context.getPackageName();
    int i;
    RemoteViews localRemoteViews;
    Article localArticle;
    if (UiUtils.isNightTheme(this.context))
    {
      i = 2130903086;
      localRemoteViews = new RemoteViews(str1, i);
      if (this.articles == null) {
        this.articles = this.dataSource.getArticles("all", 10);
      }
      localArticle = (Article)this.articles.get(paramInt);
      if (localArticle.commentCount < 1000) {
        break label247;
      }
    }
    label247:
    for (String str2 = "1k+";; str2 = String.valueOf(localArticle.commentCount))
    {
      localRemoteViews.setTextViewText(2131099732, localArticle.title);
      localRemoteViews.setTextViewText(2131099733, localArticle.author + " | ");
      localRemoteViews.setTextViewText(2131099734, str2);
      localRemoteViews.setTextViewText(2131099735, " | " + localArticle.getPublishedAgo());
      loadImage(localRemoteViews, localArticle.heroImage);
      Bundle localBundle = new Bundle();
      localBundle.putLong("nid", localArticle.nid);
      localBundle.putString("sectionTitle", "ALL");
      localBundle.putString("sectionName", "all");
      Intent localIntent = new Intent();
      localIntent.putExtras(localBundle);
      localRemoteViews.setOnClickFillInIntent(2131099730, localIntent);
      return localRemoteViews;
      i = 2130903087;
      break;
    }
  }
  
  public int getViewTypeCount()
  {
    return 1;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public void loadImage(RemoteViews paramRemoteViews, String paramString)
  {
    Bitmap localBitmap = (Bitmap)this.memCache.get("__" + paramString);
    if (localBitmap == null) {
      localHttpURLConnection = null;
    }
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localBitmap = BitmapFactory.decodeStream(localHttpURLConnection.getInputStream(), null, this.options);
      if (localBitmap != null) {
        this.memCache.put("__" + paramString, localBitmap);
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
      }
    }
    finally
    {
      if (localHttpURLConnection == null) {
        break label159;
      }
      localHttpURLConnection.disconnect();
    }
    if (localBitmap == null) {
      localBitmap = BitmapFactory.decodeResource(this.context.getResources(), 2130837578);
    }
    paramRemoteViews.setImageViewBitmap(2131099731, localBitmap);
  }
  
  public void onCreate()
  {
    Log.d("NewsRemoteViewsFactory", "onCreate");
    this.dataSource = NewsDataSource.getInstance(this.context);
  }
  
  public void onDataSetChanged()
  {
    Log.d("NewsRemoteViewsFactory", "onDataSetChanged");
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(this.context);
    localAppWidgetManager.updateAppWidget(this.appWidgetId, NewsWidgetProvider.getRemoteViews(this.context, this.appWidgetId, true));
    ArticleUpdateHandler localArticleUpdateHandler = new ArticleUpdateHandler(this.context);
    Log.d("NewsRemoteViewsFactory", "Updating article index");
    List localList = localArticleUpdateHandler.updateArticleIndex(NetUtils.get("http://m.androidcentral.com/mobile_app/feed/json" + "?debug=" + PreferenceHelper.getInstance(this.context).getDebugFlag()));
    if (localList != null)
    {
      Log.d("NewsRemoteViewsFactory", "Updating article contents");
      localArticleUpdateHandler.updateArticles(localList);
    }
    this.articles = this.dataSource.getArticles("all", 10);
    localAppWidgetManager.updateAppWidget(this.appWidgetId, NewsWidgetProvider.getRemoteViews(this.context, this.appWidgetId, false));
  }
  
  public void onDestroy()
  {
    Log.d("NewsRemoteViewsFactory", "onDestroy");
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.widget.NewsRemoteViewsFactory
 * JD-Core Version:    0.7.0.1
 */