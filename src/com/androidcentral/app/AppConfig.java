package com.androidcentral.app;

import java.util.HashSet;
import java.util.Set;

public class AppConfig
{
  public static final String API_BASE_URL = "http://m.androidcentral.com/";
  public static final String API_VERSION = "3";
  public static final String APP_FRIENDLY_NAME = "Android Central";
  public static final String CDN_BASE_URL = "http://cdn-forums.androidcentral.com/";
  public static final Set<String> COOKIES = new HashSet();
  public static final String COOKIE_DOMAIN_URL = "http://androidcentral.com";
  public static final String DOMAIN_LOGGED_IN = "androidcentral";
  public static final String DRUPAL_AVATAR_BASE_URL = "http://cdn-forums.androidcentral.com/avatars/d_ac/avatar";
  public static final String DRUPAL_COMMENT_SERVER = "m.androidcentral.com";
  public static final String FEEDBACK_EMAIL = "apps@mobilenations.com";
  public static final String FLURRY_KEY = "8RXZVCW874D7NPWHJPYG";
  public static final String MOBIQUO_URL = "http://forums.androidcentral.com/mobiquo/mobiquo.php";
  public static final String PACKAGE = "com.androidcentral.app";
  public static final String PASSPORT_BASE_URL = "https://passport.mobilenations.com";
  public static final String PLATFORM_UID_TAG = "d_ac";
  public static final String PREFS_NAME = "AndroidCentralPrefs";
  public static final String PROFILE_URL = "https://passport.mobilenations.com/profile-ac";
  public static final String QUANTCAST_KEY = "0neif4ce6zusin26-wffdqrg9cfqru9wq";
  public static final String SHOP_PHONE_NO = "+18884686158";
  public static final String SHOP_URL = "http://shopandroid.com/";
  public static final String SIGN_IN_URL = "https://passport.mobilenations.com/login-ac";
  public static final String SIGN_UP_URL = "https://passport.mobilenations.com/register-ac";
  public static final String SITE_BASE_URL = "http://www.androidcentral.com/";
  public static final String SITE_FORUM_URL = "http://forums.androidcentral.com/";
  public static final String TIP_EMAIL = "actips@androidcentral.com";
  public static final String USERID_COOKIE_NAME = "acuserid";
  public static final String VIDEO_FEED_URL = "https://gdata.youtube.com/feeds/api/videos?author=AndroidCentral&orderby=published&v=2&alt=jsonc";
  public static final String YT_DEV_KEY = "AIzaSyBvXbF2AyYwZBwdLORms-cq7CHmTk2iGRY";
  
  static
  {
    COOKIES.add("PassportSession");
    COOKIES.add("PassportLocalSession");
    COOKIES.add("PassportSessionDate");
    COOKIES.add("acuserid");
    COOKIES.add("acpassword");
    COOKIES.add("acsessionhash");
    COOKIES.add("SESS9928782eb8d98596f1d025e6d7d60fed");
    COOKIES.add("SESSf659a72bed167194167ec3641e600b60");
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.AppConfig
 * JD-Core Version:    0.7.0.1
 */