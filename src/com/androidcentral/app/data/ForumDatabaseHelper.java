package com.androidcentral.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ForumDatabaseHelper
  extends SQLiteOpenHelper
{
  private static final String DB_NAME = "db_forum";
  private static final int DB_VERSION = 1;
  private static final String TAG = "ForumDatabaseHelper";
  
  public ForumDatabaseHelper(Context paramContext)
  {
    super(paramContext, "db_forum", null, 1);
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    Log.d("ForumDatabaseHelper", "Creating forum database tables");
    paramSQLiteDatabase.execSQL("CREATE TABLE threads (thread_id INTEGER PRIMARY KEY NOT NULL UNIQUE, last_post_id INTEGER, timestamp INTEGER)");
    paramSQLiteDatabase.execSQL("CREATE INDEX threadIndex ON threads (thread_id)");
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.d("ForumDatabaseHelper", "Upgrading forum database from " + paramInt1 + " to " + paramInt2);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumDatabaseHelper
 * JD-Core Version:    0.7.0.1
 */