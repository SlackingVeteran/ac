package com.androidcentral.app.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.androidcentral.app.net.NetUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTemplate
{
  private static final String TAG = "SimpleTemplate";
  private Context context;
  private String template;
  
  public SimpleTemplate(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.template = getTemplate(paramString);
  }
  
  private String getTemplate(String paramString)
  {
    AssetManager localAssetManager = this.context.getAssets();
    InputStream localInputStream = null;
    try
    {
      localInputStream = localAssetManager.open(paramString);
      String str2 = NetUtils.readStream(localInputStream);
      String str1 = str2;
      if (localInputStream != null) {}
      return str1;
    }
    catch (IOException localIOException2)
    {
      do
      {
        localIOException2 = localIOException2;
        Log.e("SimpleTemplate", Log.getStackTraceString(localIOException2));
        str1 = null;
      } while (localInputStream == null);
      try
      {
        localInputStream.close();
        return null;
      }
      catch (IOException localIOException3)
      {
        Log.e("SimpleTemplate", Log.getStackTraceString(localIOException3));
        return null;
      }
    }
    finally
    {
      if (localInputStream != null) {}
      try
      {
        localInputStream.close();
        throw localObject;
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          Log.e("SimpleTemplate", Log.getStackTraceString(localIOException1));
        }
      }
    }
  }
  
  public String applyMapping(Map<String, String> paramMap)
  {
    if (this.template == null) {
      return null;
    }
    Matcher localMatcher = Pattern.compile("@\\{\\{(.+?)\\}\\}").matcher(this.template);
    StringBuffer localStringBuffer = new StringBuffer();
    for (;;)
    {
      if (!localMatcher.find())
      {
        localMatcher.appendTail(localStringBuffer);
        return localStringBuffer.toString();
      }
      String str = localMatcher.group(1);
      if (paramMap.containsKey(str)) {
        localMatcher.appendReplacement(localStringBuffer, Matcher.quoteReplacement((String)paramMap.get(str)));
      }
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.SimpleTemplate
 * JD-Core Version:    0.7.0.1
 */