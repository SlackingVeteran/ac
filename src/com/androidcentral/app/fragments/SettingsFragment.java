package com.androidcentral.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.androidcentral.app.util.StorageHelper;

public class SettingsFragment
  extends PreferenceListFragment
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private static final String TAG = "SettingsFragment";
  int debugToggleCount = 0;
  
  private SharedPreferences getPrefs()
  {
    return getPreferenceScreen().getSharedPreferences();
  }
  
  private String getVersionName()
  {
    FragmentActivity localFragmentActivity = getActivity();
    try
    {
      String str = localFragmentActivity.getPackageManager().getPackageInfo(localFragmentActivity.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.e("SettingsFragment", Log.getStackTraceString(localNameNotFoundException));
    }
    return "";
  }
  
  private void launchFeedbackEmail()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("plain/text");
    localIntent.putExtra("android.intent.extra.EMAIL", new String[] { "apps@mobilenations.com" });
    localIntent.putExtra("android.intent.extra.SUBJECT", "Android Central Application Feedback");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("App Version: ");
    localStringBuilder.append(getVersionName());
    localStringBuilder.append("\n");
    localStringBuilder.append("Device Info: ");
    localStringBuilder.append(Build.MODEL);
    localStringBuilder.append("/");
    localStringBuilder.append(Build.VERSION.RELEASE);
    localStringBuilder.append("\n");
    localStringBuilder.append("\n");
    localStringBuilder.append("Please enter your feedback below.\nThank you!\n");
    localStringBuilder.append("----------------------");
    localStringBuilder.append("\n\n");
    localIntent.putExtra("android.intent.extra.TEXT", localStringBuilder.toString());
    startActivity(localIntent);
  }
  
  private void showDebugToggle(boolean paramBoolean)
  {
    PreferenceCategory localPreferenceCategory = (PreferenceCategory)findPreference("about_category");
    CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference)localPreferenceCategory.findPreference("pref_debug");
    if (localCheckBoxPreference == null)
    {
      localCheckBoxPreference = new CheckBoxPreference(getActivity());
      localCheckBoxPreference.setKey("pref_debug");
      localCheckBoxPreference.setTitle(2131165264);
      localCheckBoxPreference.setSummary("Super secret!");
      localPreferenceCategory.addPreference(localCheckBoxPreference);
    }
    if (paramBoolean) {
      localCheckBoxPreference.setChecked(true);
    }
    getPrefs().edit().putBoolean("show_debug_toggle", true).apply();
  }
  
  private void updateSummary(String paramString)
  {
    SharedPreferences localSharedPreferences = getPrefs();
    Preference localPreference = findPreference(paramString);
    if (paramString.equals("pref_forums_signature")) {
      localPreference.setSummary(localSharedPreferences.getString(paramString, ""));
    }
    do
    {
      return;
      if (paramString.equals("versionPreference"))
      {
        localPreference.setSummary(getVersionName());
        return;
      }
      if (paramString.equals("pref_sync_interval"))
      {
        long l = Long.parseLong(localSharedPreferences.getString(paramString, getResources().getString(2131165265))) / 60000L;
        if (l == 0L)
        {
          localPreference.setSummary("Manual");
          return;
        }
        localPreference.setSummary("Every " + l + " mins (only while app is open)");
        return;
      }
      if (paramString.equals("pref_open_behavior"))
      {
        if (localSharedPreferences.getString(paramString, getResources().getString(2131165267)).equals("unread")) {}
        for (String str = "Go to " + "first unread post";; str = "Go to " + "first post")
        {
          localPreference.setSummary(str);
          return;
        }
      }
      if (paramString.equals("pref_font_size"))
      {
        localPreference.setSummary(localSharedPreferences.getString(paramString, ""));
        return;
      }
      if (paramString.equals("pref_screen_animation"))
      {
        localPreference.setSummary(localSharedPreferences.getString(paramString, ""));
        return;
      }
    } while (!paramString.equals("clearCache"));
    StorageHelper localStorageHelper = new StorageHelper(getActivity());
    localPreference.setSummary("Cache Size: " + localStorageHelper.getCacheSize());
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    getPreferenceManager().setSharedPreferencesName("AndroidCentralPrefs");
    addPreferencesFromResource(2131034113);
    if (getPrefs().getBoolean("show_debug_toggle", false)) {
      showDebugToggle(false);
    }
    findPreference("emailFeedbackSettings").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingsFragment.this.launchFeedbackEmail();
        return true;
      }
    });
    findPreference("clearCache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Toast.makeText(SettingsFragment.this.getActivity(), "Cache Cleared!", 0).show();
        new StorageHelper(SettingsFragment.this.getActivity()).clearCache();
        SettingsFragment.this.updateSummary("clearCache");
        SharedPreferences.Editor localEditor = SettingsFragment.this.getPrefs().edit();
        localEditor.putLong("last_refresh_all", 0L);
        localEditor.putLong("last_refresh_reviews", 0L);
        localEditor.putLong("last_refresh_editorial", 0L);
        localEditor.putLong("last_refresh_help", 0L);
        localEditor.putLong("last_refresh_apps", 0L);
        localEditor.putLong("last_refresh_accessories", 0L);
        localEditor.putLong("last_refresh_talkmobile", 0L);
        localEditor.apply();
        return true;
      }
    });
    findPreference("versionPreference").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingsFragment localSettingsFragment = SettingsFragment.this;
        int i = 1 + localSettingsFragment.debugToggleCount;
        localSettingsFragment.debugToggleCount = i;
        if (i >= 8)
        {
          Toast.makeText(SettingsFragment.this.getActivity(), "Debug Mode Enabled", 0).show();
          SettingsFragment.this.debugToggleCount = 0;
          SettingsFragment.this.showDebugToggle(true);
        }
        return true;
      }
    });
    updateSummary("versionPreference");
    updateSummary("pref_forums_signature");
    updateSummary("pref_sync_interval");
    updateSummary("pref_open_behavior");
    updateSummary("pref_font_size");
    updateSummary("pref_screen_animation");
    updateSummary("clearCache");
  }
  
  public void onPause()
  {
    super.onPause();
    getPrefs().unregisterOnSharedPreferenceChangeListener(this);
  }
  
  public void onResume()
  {
    super.onResume();
    getPrefs().registerOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if ((paramString.equals("pref_forums_signature")) || (paramString.equals("pref_sync_interval")) || (paramString.equals("pref_open_behavior")) || (paramString.equals("pref_font_size"))) {
      updateSummary(paramString);
    }
    do
    {
      return;
      if (paramString.equals("pref_night_mode"))
      {
        getActivity().recreate();
        return;
      }
    } while (!paramString.equals("pref_screen_animation"));
    updateSummary("pref_screen_animation");
    getActivity().recreate();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SettingsFragment
 * JD-Core Version:    0.7.0.1
 */