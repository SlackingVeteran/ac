package com.androidcentral.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ForumDataSource
{
  private static final String TAG = "ForumDataSource";
  private static ForumDataSource instance;
  private SQLiteDatabase db;
  private ForumDatabaseHelper dbHelper;
  
  private ForumDataSource(Context paramContext)
  {
    this.dbHelper = new ForumDatabaseHelper(paramContext);
    this.db = this.dbHelper.getWritableDatabase();
  }
  
  public static ForumDataSource getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new ForumDataSource(paramContext);
    }
    return instance;
  }
  
  public long getLastPost(String paramString)
  {
    Cursor localCursor = this.db.query("threads", new String[] { "last_post_id" }, "thread_id = ?", new String[] { paramString }, null, null, null);
    long l = -1L;
    if ((localCursor != null) && (localCursor.getCount() > 0))
    {
      localCursor.moveToFirst();
      l = localCursor.getLong(0);
    }
    return l;
  }
  
  public void prune()
  {
    long l = System.currentTimeMillis() - 1209600000L;
    SQLiteDatabase localSQLiteDatabase = this.db;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(l);
    int i = localSQLiteDatabase.delete("threads", "timestamp < ?", arrayOfString);
    Log.d("ForumDataSource", "deleted " + i + " records from threads table");
  }
  
  public void setLastPost(long paramLong1, long paramLong2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("thread_id", Long.valueOf(paramLong1));
    localContentValues.put("last_post_id", Long.valueOf(paramLong2));
    localContentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    this.db.replace("threads", null, localContentValues);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumDataSource
 * JD-Core Version:    0.7.0.1
 */