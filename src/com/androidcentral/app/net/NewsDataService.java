package com.androidcentral.app.net;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.androidcentral.app.util.PreferenceHelper;
import java.util.List;

public class NewsDataService
  extends IntentService
{
  public static final String EXTRA_SECTION = "section";
  public static final String TAG = "NewsDataService";
  
  public NewsDataService()
  {
    super("NewsDataService");
  }
  
  private void updateLastRefreshed(String paramString)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences("AndroidCentralPrefs", 0).edit();
    localEditor.putLong("last_refresh_" + paramString, System.currentTimeMillis());
    localEditor.commit();
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    final String str1 = paramIntent.getStringExtra("section");
    String str2 = "http://m.androidcentral.com/mobile_app/feed/json" + "?debug=" + PreferenceHelper.getInstance(this).getDebugFlag();
    if (!str1.equals("all")) {
      str2 = str2 + "&section=" + str1;
    }
    final ArticleUpdateHandler localArticleUpdateHandler = new ArticleUpdateHandler(this);
    Log.d("NewsDataService", "Updating article index: " + str1);
    final List localList = localArticleUpdateHandler.updateArticleIndex(NetUtils.get(str2));
    updateLastRefreshed(str1);
    Intent localIntent = new Intent("db_updated_" + str1);
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    if ((localList != null) && (!localList.isEmpty())) {
      new Thread(new Runnable()
      {
        public void run()
        {
          Log.d("NewsDataService", "Updating article contents: " + str1);
          localArticleUpdateHandler.updateArticles(localList);
        }
      }).start();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.NewsDataService
 * JD-Core Version:    0.7.0.1
 */