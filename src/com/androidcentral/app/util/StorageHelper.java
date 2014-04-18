package com.androidcentral.app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;
import com.androidcentral.app.data.NewsDataSource;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;

public class StorageHelper
{
  private static final String TAG = "StorageHelper";
  private Context context;
  private ImageLoader imageLoader;
  private NewsDataSource newsDataSource;
  
  public StorageHelper(Context paramContext)
  {
    this.context = paramContext;
    this.imageLoader = ImageLoader.getInstance();
    this.newsDataSource = NewsDataSource.getInstance(paramContext);
  }
  
  private long dirSize(File paramFile)
  {
    if ((paramFile.exists()) && (paramFile.isDirectory()))
    {
      long l = 0L;
      File[] arrayOfFile = paramFile.listFiles();
      int i = 0;
      if (i >= arrayOfFile.length) {
        return l;
      }
      if (arrayOfFile[i].isDirectory()) {}
      for (l += dirSize(arrayOfFile[i]);; l += arrayOfFile[i].length())
      {
        i++;
        break;
      }
    }
    return 0L;
  }
  
  public void clearCache()
  {
    this.imageLoader.clearMemoryCache();
    this.imageLoader.clearDiscCache();
    new WebView(this.context).clearCache(true);
    this.newsDataSource.resetDatabase();
  }
  
  public String getCacheSize()
  {
    float f = (float)(dirSize(StorageUtils.getCacheDirectory(this.context)) + new File(this.newsDataSource.getDatabase().getPath()).length()) / 1048576.0F;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(f);
    return String.format("%.2fMB", arrayOfObject);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.StorageHelper
 * JD-Core Version:    0.7.0.1
 */