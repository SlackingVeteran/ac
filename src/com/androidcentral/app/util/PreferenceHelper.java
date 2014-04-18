package com.androidcentral.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper
{
  private static PreferenceHelper instance = null;
  protected SharedPreferences prefs;
  
  private PreferenceHelper(Context paramContext)
  {
    this.prefs = paramContext.getSharedPreferences("AndroidCentralPrefs", 0);
  }
  
  public static PreferenceHelper getInstance(Context paramContext)
  {
    if (instance == null) {
      instance = new PreferenceHelper(paramContext.getApplicationContext());
    }
    return instance;
  }
  
  public String getDebugFlag()
  {
    if (this.prefs.getBoolean("pref_debug", false)) {
      return "1";
    }
    return "0";
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.PreferenceHelper
 * JD-Core Version:    0.7.0.1
 */