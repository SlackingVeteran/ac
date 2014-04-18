package com.androidcentral.app;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import com.androidcentral.app.data.ForumDataSource;
import com.androidcentral.app.net.SessionCookiePolicy;
import com.androidcentral.app.net.SessionCookieStore;
import com.androidcentral.app.net.SessionManager;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.net.CookieHandler;
import java.net.CookieManager;

public class App
  extends Application
{
  private static final int DISC_CACHE_SIZE = 104857600;
  private static final String TAG = "App";
  
  public void onCreate()
  {
    super.onCreate();
    DisplayImageOptions localDisplayImageOptions = new DisplayImageOptions.Builder().showStubImage(2130837578).displayer(new FadeInBitmapDisplayer(500)).cacheInMemory().cacheOnDisc().build();
    ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(localDisplayImageOptions).discCache(new TotalSizeLimitedDiscCache(StorageUtils.getCacheDirectory(this), 104857600)).build();
    ImageLoader.getInstance().init(localImageLoaderConfiguration);
    PreferenceManager.setDefaultValues(this, "AndroidCentralPrefs", 0, 2131034113, true);
    CookieHandler.setDefault(new CookieManager(new SessionCookieStore(this), new SessionCookiePolicy()));
    SessionManager.getInstance().sessionCheck();
    ForumDataSource.getInstance(this).prune();
    if (Looper.getMainLooper().equals(Looper.myLooper()))
    {
      Log.d("App", "Initializing AsyncTask InternalHandler on UI thread");
      new AsyncTask()
      {
        protected Void doInBackground(Void... paramAnonymousVarArgs)
        {
          return null;
        }
      };
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.App
 * JD-Core Version:    0.7.0.1
 */