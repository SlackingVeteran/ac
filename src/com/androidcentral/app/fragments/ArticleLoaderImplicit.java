package com.androidcentral.app.fragments;

import android.content.Context;
import android.util.Log;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.net.ArticleUpdateHandler;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.AsyncSupportLoader;
import com.androidcentral.app.util.PreferenceHelper;

class ArticleLoaderImplicit
  extends AsyncSupportLoader<Article>
{
  private static final String TAG = "ArticleLoader";
  private String decodeUrl;
  private ArticleUpdateHandler handler;
  private String permaLink;
  private NewsDataSource source;
  
  public ArticleLoaderImplicit(Context paramContext, String paramString)
  {
    super(paramContext);
    this.permaLink = paramString;
    this.handler = new ArticleUpdateHandler(paramContext);
    this.source = NewsDataSource.getInstance(getContext());
    this.decodeUrl = "http://m.androidcentral.com/mobile_app/decode_node_json/";
    this.decodeUrl += paramString;
    this.decodeUrl += "?version=3";
    this.decodeUrl = (this.decodeUrl + "&debug=" + PreferenceHelper.getInstance(paramContext).getDebugFlag());
  }
  
  public Article loadInBackground()
  {
    Article localArticle;
    if (this.permaLink.startsWith("talk-mobile"))
    {
      String str2 = NetUtils.get(this.decodeUrl);
      localArticle = this.handler.extractArticle(str2);
    }
    do
    {
      return localArticle;
      localArticle = this.source.getArticleContent(this.permaLink);
    } while ((localArticle != null) && (localArticle.content != null));
    Log.d("ArticleLoader", "Article not in DB, fetching remotely");
    String str1 = NetUtils.get(this.decodeUrl);
    ArticleUpdateHandler localArticleUpdateHandler = this.handler;
    if (localArticle == null) {}
    for (boolean bool = true;; bool = false)
    {
      localArticleUpdateHandler.updateArticleContents(str1, bool);
      return this.source.getArticleContent(this.permaLink);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ArticleLoaderImplicit
 * JD-Core Version:    0.7.0.1
 */