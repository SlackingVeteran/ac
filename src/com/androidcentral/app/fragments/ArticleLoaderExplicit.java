package com.androidcentral.app.fragments;

import android.content.Context;
import android.util.Log;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.net.ArticleUpdateHandler;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.AsyncSupportLoader;
import com.androidcentral.app.util.PreferenceHelper;

class ArticleLoaderExplicit
  extends AsyncSupportLoader<Article>
{
  private static final String TAG = "ArticleLoader";
  private String contentUrl;
  private ArticleUpdateHandler handler;
  private long nid;
  private NewsDataSource source;
  
  public ArticleLoaderExplicit(Context paramContext, long paramLong)
  {
    super(paramContext);
    this.nid = paramLong;
    this.handler = new ArticleUpdateHandler(paramContext);
    this.source = NewsDataSource.getInstance(paramContext);
    this.contentUrl = ("http://m.androidcentral.com/node/" + paramLong + "/json");
    this.contentUrl += "?version=3";
    this.contentUrl = (this.contentUrl + "&debug=" + PreferenceHelper.getInstance(paramContext).getDebugFlag());
  }
  
  public Article loadInBackground()
  {
    Article localArticle = this.source.getArticleContent(this.nid);
    if (localArticle.isTalkMobile())
    {
      String str2 = NetUtils.get(this.contentUrl);
      return this.handler.extractArticle(str2);
    }
    if (localArticle.content == null)
    {
      Log.d("ArticleLoader", "Article not in DB, fetching remotely");
      String str1 = NetUtils.get(this.contentUrl);
      this.handler.updateArticleContents(str1, false);
      localArticle = this.source.getArticleContent(this.nid);
    }
    return localArticle;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ArticleLoaderExplicit
 * JD-Core Version:    0.7.0.1
 */