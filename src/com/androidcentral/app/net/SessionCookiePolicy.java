package com.androidcentral.app.net;

import com.androidcentral.app.AppConfig;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Set;

public class SessionCookiePolicy
  implements CookiePolicy
{
  public boolean shouldAccept(URI paramURI, HttpCookie paramHttpCookie)
  {
    return AppConfig.COOKIES.contains(paramHttpCookie.getName());
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.SessionCookiePolicy
 * JD-Core Version:    0.7.0.1
 */