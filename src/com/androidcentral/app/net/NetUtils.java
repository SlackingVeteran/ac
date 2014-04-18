package com.androidcentral.app.net;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class NetUtils
{
  private static final String TAG = "NetUtils";
  
  public static String get(String paramString)
  {
    HttpURLConnection localHttpURLConnection = null;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      String str = readStream(localHttpURLConnection.getInputStream());
      return str;
    }
    catch (IOException localIOException)
    {
      Log.e("NetUtils", Log.getStackTraceString(localIOException));
      return null;
    }
    finally
    {
      if (localHttpURLConnection != null) {
        localHttpURLConnection.disconnect();
      }
    }
  }
  
  public static String post(String paramString1, String paramString2, String paramString3)
  {
    HttpURLConnection localHttpURLConnection = null;
    int i = 0;
    for (;;)
    {
      Object localObject2;
      if (i >= 3)
      {
        Log.d("NetUtils", "Connection retries failed. Giving up...");
        localObject2 = null;
        return localObject2;
      }
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(paramString1).openConnection();
        localHttpURLConnection.setDoOutput(true);
        localHttpURLConnection.setFixedLengthStreamingMode(paramString2.length());
        if (paramString3 != null)
        {
          localHttpURLConnection.setRequestProperty("Content-Type", paramString3);
          localHttpURLConnection.setRequestProperty("Accept", "*/*");
        }
        PrintWriter localPrintWriter = new PrintWriter(localHttpURLConnection.getOutputStream());
        localPrintWriter.write(paramString2);
        localPrintWriter.close();
        String str = readStream(localHttpURLConnection.getInputStream());
        localObject2 = str;
        return localObject2;
      }
      catch (IOException localIOException)
      {
        Log.e("NetUtils", Log.getStackTraceString(localIOException));
        Log.d("NetUtils", "Connection failed. Retrying again...");
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
        i++;
      }
      finally
      {
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
      }
    }
  }
  
  public static String readStream(InputStream paramInputStream)
  {
    try
    {
      String str = new Scanner(paramInputStream).useDelimiter("\\A").next();
      return str;
    }
    catch (NoSuchElementException localNoSuchElementException) {}
    return null;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.NetUtils
 * JD-Core Version:    0.7.0.1
 */