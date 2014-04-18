package com.androidcentral.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDataSource
{
  private static NewsDataSource instance = null;
  private SQLiteDatabase db;
  private DatabaseHelper dbHelper;
  
  private NewsDataSource(Context paramContext)
  {
    this.dbHelper = new DatabaseHelper(paramContext);
    this.db = this.dbHelper.getWritableDatabase();
  }
  
  public static NewsDataSource getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new NewsDataSource(paramContext.getApplicationContext());
    }
    return instance;
  }
  
  public Article articleFromCursor(Cursor paramCursor, int paramInt, boolean paramBoolean)
  {
    int i = 1;
    int j = paramCursor.getCount();
    Article localArticle = null;
    if (paramInt < j)
    {
      paramCursor.moveToPosition(paramInt);
      localArticle = new Article();
      localArticle.nid = paramCursor.getLong(paramCursor.getColumnIndex("nid"));
      localArticle.permaLink = paramCursor.getString(paramCursor.getColumnIndex("permaLink"));
      localArticle.title = paramCursor.getString(paramCursor.getColumnIndex("title"));
      localArticle.author = paramCursor.getString(paramCursor.getColumnIndex("author"));
      localArticle.type = paramCursor.getString(paramCursor.getColumnIndex("type"));
      localArticle.audio = paramCursor.getString(paramCursor.getColumnIndex("audio"));
      localArticle.publishedDate = paramCursor.getLong(paramCursor.getColumnIndex("publishedDate"));
      localArticle.commentCount = paramCursor.getInt(paramCursor.getColumnIndex("commentCount"));
      localArticle.heroImage = paramCursor.getString(paramCursor.getColumnIndex("heroImage"));
      localArticle.appId = paramCursor.getString(paramCursor.getColumnIndex("appId"));
      if (paramCursor.getInt(paramCursor.getColumnIndex("readStatus")) != i) {
        break label279;
      }
    }
    for (;;)
    {
      localArticle.readStatus = i;
      if (paramBoolean) {
        localArticle.content = paramCursor.getString(paramCursor.getColumnIndex("content"));
      }
      return localArticle;
      label279:
      i = 0;
    }
  }
  
  public Article getArticleContent(long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM articles LEFT OUTER JOIN articlesContent ON (articlesContent.nid_rel = articles.nid) WHERE articles.nid = ?", arrayOfString);
    Article localArticle = articleFromCursor(localCursor, 0, true);
    localCursor.close();
    return localArticle;
  }
  
  public Article getArticleContent(String paramString)
  {
    Cursor localCursor = this.db.rawQuery("SELECT * FROM articles LEFT OUTER JOIN articlesContent ON (articlesContent.nid_rel = articles.nid) WHERE articles.permaLink = ?", new String[] { paramString });
    Article localArticle = articleFromCursor(localCursor, 0, true);
    localCursor.close();
    return localArticle;
  }
  
  public String getArticleQuery(String paramString)
  {
    if (paramString.equals("all")) {
      return "SELECT rowid _id,* FROM articles ORDER BY publishedDate DESC LIMIT 0,100";
    }
    if (paramString.equals("talkmobile")) {
      return "SELECT nid _id,* FROM articles WHERE articles.type = 'talk_mobile' ORDER BY publishedDate DESC LIMIT 0,100";
    }
    if (paramString.equals("podcast")) {
      return "SELECT nid _id,* FROM articles WHERE articles.type = 'podcast' ORDER BY publishedDate DESC LIMIT 0,100";
    }
    return "SELECT nid _id,* FROM articles, articlesSection WHERE articles.nid = articlesSection.nid_rel AND articlesSection.section = '" + paramString + "' " + "ORDER BY publishedDate DESC " + "LIMIT 0,100";
  }
  
  public List<Article> getArticles(String paramString, int paramInt)
  {
    Cursor localCursor = this.db.rawQuery(getArticleQuery(paramString), null);
    ArrayList localArrayList = new ArrayList(paramInt);
    for (int i = 0;; i++)
    {
      if (i >= paramInt)
      {
        localCursor.close();
        return localArrayList;
      }
      localArrayList.add(articleFromCursor(localCursor, i, false));
    }
  }
  
  public Map<Long, Long> getArticlesModified()
  {
    Cursor localCursor = this.db.rawQuery("SELECT nid, modifiedDate FROM articles", null);
    HashMap localHashMap = new HashMap(localCursor.getCount());
    localCursor.moveToFirst();
    for (;;)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return localHashMap;
      }
      localHashMap.put(Long.valueOf(localCursor.getLong(0)), Long.valueOf(localCursor.getLong(1)));
      localCursor.moveToNext();
    }
  }
  
  public SQLiteDatabase getDatabase()
  {
    return this.db;
  }
  
  public DatabaseHelper getDatabaseHelper()
  {
    return this.dbHelper;
  }
  
  public String getFeaturedQuery()
  {
    return "SELECT rowid _id,* FROM articles WHERE articles.sticky = 1 ORDER BY publishedDate DESC";
  }
  
  public void markAllAsRead(String paramString)
  {
    if (paramString.equals("all"))
    {
      this.db.execSQL("UPDATE articles SET readStatus = 1");
      return;
    }
    if (paramString.equals("talkmobile"))
    {
      this.db.execSQL("UPDATE articles SET readStatus = 1 WHERE type = 'talk_mobile'");
      return;
    }
    if (paramString.equals("podcast"))
    {
      this.db.execSQL("UPDATE articles SET readStatus = 1 WHERE type = 'podcast'");
      return;
    }
    this.db.execSQL("UPDATE articles SET readStatus = 1 WHERE articles.nid IN (SELECT nid_rel FROM articlesSection WHERE articlesSection.section = \"" + paramString + "\")");
  }
  
  public void markAsRead(long paramLong)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("readStatus", Boolean.valueOf(true));
    SQLiteDatabase localSQLiteDatabase = this.db;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    localSQLiteDatabase.update("articles", localContentValues, "nid=?", arrayOfString);
  }
  
  public void resetDatabase()
  {
    this.dbHelper.resetDatabase();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.NewsDataSource
 * JD-Core Version:    0.7.0.1
 */