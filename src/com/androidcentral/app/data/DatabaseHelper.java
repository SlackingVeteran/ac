package com.androidcentral.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper
  extends SQLiteOpenHelper
{
  private static final String DB_NAME = "db";
  private static final int DB_VERSION = 3;
  private static final String TAG = "DatabaseHelper";
  
  public DatabaseHelper(Context paramContext)
  {
    super(paramContext, "db", null, 3);
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    Log.d("DatabaseHelper", "Creating database tables");
    paramSQLiteDatabase.execSQL("CREATE TABLE articles (nid INTEGER PRIMARY KEY NOT NULL UNIQUE, publishedDate INTEGER, modifiedDate INTEGER, sticky BOOL, commentCount INTEGER, author TEXT, title TEXT, internalLink TEXT, permaLink TEXT, heroImage TEXT, readStatus BOOL, type TEXT, audio TEXT, appId TEXT)");
    paramSQLiteDatabase.execSQL("CREATE TABLE articlesSection (nid_rel INTEGER NOT NULL, section TEXT)");
    paramSQLiteDatabase.execSQL("CREATE TABLE articlesContent (nid_rel INTEGER PRIMARY KEY NOT NULL UNIQUE, content TEXT)");
    paramSQLiteDatabase.execSQL("CREATE INDEX articleIndex ON articles (nid ASC, publishedDate ASC, sticky ASC, readStatus ASC)");
    paramSQLiteDatabase.execSQL("CREATE UNIQUE INDEX articlesSectionIndex ON articlesSection (nid_rel ASC, section ASC)");
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.d("DatabaseHelper", "Upgrading database from " + paramInt1 + " to " + paramInt2);
    switch (paramInt1)
    {
    default: 
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articles");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articlesSection");
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articlesContent");
      onCreate(paramSQLiteDatabase);
      return;
    }
    paramSQLiteDatabase.execSQL("ALTER TABLE articles ADD COLUMN appId TEXT");
  }
  
  public void resetDatabase()
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    localSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articles");
    localSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articlesSection");
    localSQLiteDatabase.execSQL("DROP TABLE IF EXISTS articlesContent");
    onCreate(localSQLiteDatabase);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.DatabaseHelper
 * JD-Core Version:    0.7.0.1
 */