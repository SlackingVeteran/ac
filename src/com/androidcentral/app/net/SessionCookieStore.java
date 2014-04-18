package com.androidcentral.app.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.androidcentral.app.AppConfig;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SessionCookieStore
  implements CookieStore
{
  private static final String COOKIE_PREFIX = "cookie_";
  private static final String TAG = "CookieStore";
  private SharedPreferences prefs;
  
  public SessionCookieStore(Context paramContext)
  {
    this.prefs = paramContext.getApplicationContext().getSharedPreferences("AndroidCentralPrefs", 0);
  }
  
  public void add(URI paramURI, HttpCookie paramHttpCookie)
  {
    String str1 = paramHttpCookie.getName().trim();
    String str2 = paramHttpCookie.getValue().trim();
    StringBuilder localStringBuilder;
    if ((!str2.isEmpty()) && (!str2.equals(getCookieValue(str1))))
    {
      localStringBuilder = new StringBuilder("Storing the cookie ").append(str1).append(":").append(str2).append(" from ");
      if (paramURI == null) {
        break label140;
      }
    }
    label140:
    for (String str3 = paramURI.toString();; str3 = "")
    {
      Log.d("CookieStore", str3);
      SharedPreferences.Editor localEditor = this.prefs.edit();
      localEditor.putString("cookie_" + str1, str2);
      localEditor.commit();
      return;
    }
  }
  
  public void extractCookies(String paramString)
  {
    if (paramString == null) {}
    for (;;)
    {
      return;
      String[] arrayOfString = paramString.split(";");
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        String str1 = arrayOfString[j].trim();
        int k = str1.indexOf('=');
        if (k != -1)
        {
          String str2 = str1.substring(0, k);
          String str3 = str1.substring(k + 1);
          if (AppConfig.COOKIES.contains(str2)) {
            add(null, new HttpCookie(str2, str3));
          }
        }
      }
    }
  }
  
  public List<HttpCookie> get(URI paramURI)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = AppConfig.COOKIES.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      String str = (String)localIterator.next();
      if (this.prefs.contains("cookie_" + str))
      {
        HttpCookie localHttpCookie = new HttpCookie(str, this.prefs.getString("cookie_" + str, ""));
        localHttpCookie.setVersion(0);
        localArrayList.add(localHttpCookie);
      }
    }
  }
  
  public String getCookieValue(String paramString)
  {
    return this.prefs.getString("cookie_" + paramString, "");
  }
  
  public List<HttpCookie> getCookies()
  {
    throw new UnsupportedOperationException("getCookies() not implemented");
  }
  
  public List<URI> getURIs()
  {
    throw new UnsupportedOperationException("getURIs() not implemented");
  }
  
  public boolean remove(URI paramURI, HttpCookie paramHttpCookie)
  {
    throw new UnsupportedOperationException("remove() not implemented");
  }
  
  public boolean removeAll()
  {
    throw new UnsupportedOperationException("removeAll() not implemented");
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.SessionCookieStore
 * JD-Core Version:    0.7.0.1
 */