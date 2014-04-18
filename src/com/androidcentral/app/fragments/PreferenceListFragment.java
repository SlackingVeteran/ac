package com.androidcentral.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PreferenceListFragment
  extends ListFragment
{
  private static final int FIRST_REQUEST_CODE = 100;
  private static final int MSG_BIND_PREFERENCES = 0;
  private static final String TAG = "PreferenceListFragment";
  private ListView lv;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      }
      PreferenceListFragment.this.bindPreferences();
    }
  };
  private PreferenceManager mPreferenceManager;
  private int xmlId;
  
  private void bindPreferences()
  {
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    if (localPreferenceScreen != null) {
      localPreferenceScreen.bind(this.lv);
    }
  }
  
  private PreferenceManager onCreatePreferenceManager()
  {
    try
    {
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Activity.class;
      arrayOfClass[1] = Integer.TYPE;
      Constructor localConstructor = PreferenceManager.class.getDeclaredConstructor(arrayOfClass);
      localConstructor.setAccessible(true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = getActivity();
      arrayOfObject[1] = Integer.valueOf(100);
      PreferenceManager localPreferenceManager = (PreferenceManager)localConstructor.newInstance(arrayOfObject);
      return localPreferenceManager;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
    return null;
  }
  
  private void postBindPreferences()
  {
    if (this.mHandler.hasMessages(0)) {
      return;
    }
    this.mHandler.obtainMessage(0).sendToTarget();
  }
  
  public void addPreferencesFromIntent(Intent paramIntent)
  {
    throw new RuntimeException("too lazy to include this bs");
  }
  
  public void addPreferencesFromResource(int paramInt)
  {
    try
    {
      Class[] arrayOfClass = new Class[3];
      arrayOfClass[0] = Context.class;
      arrayOfClass[1] = Integer.TYPE;
      arrayOfClass[2] = PreferenceScreen.class;
      Method localMethod = PreferenceManager.class.getDeclaredMethod("inflateFromResource", arrayOfClass);
      localMethod.setAccessible(true);
      PreferenceManager localPreferenceManager = this.mPreferenceManager;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = getActivity();
      arrayOfObject[1] = Integer.valueOf(paramInt);
      arrayOfObject[2] = getPreferenceScreen();
      setPreferenceScreen((PreferenceScreen)localMethod.invoke(localPreferenceManager, arrayOfObject));
      return;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
  }
  
  public Preference findPreference(CharSequence paramCharSequence)
  {
    if (this.mPreferenceManager == null) {
      return null;
    }
    return this.mPreferenceManager.findPreference(paramCharSequence);
  }
  
  public PreferenceManager getPreferenceManager()
  {
    return this.mPreferenceManager;
  }
  
  public PreferenceScreen getPreferenceScreen()
  {
    try
    {
      Method localMethod = PreferenceManager.class.getDeclaredMethod("getPreferenceScreen", new Class[0]);
      localMethod.setAccessible(true);
      PreferenceScreen localPreferenceScreen = (PreferenceScreen)localMethod.invoke(this.mPreferenceManager, new Object[0]);
      return localPreferenceScreen;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
    return null;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    try
    {
      Class[] arrayOfClass = new Class[3];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = Integer.TYPE;
      arrayOfClass[2] = Intent.class;
      Method localMethod = PreferenceManager.class.getDeclaredMethod("dispatchActivityResult", arrayOfClass);
      localMethod.setAccessible(true);
      PreferenceManager localPreferenceManager = this.mPreferenceManager;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      arrayOfObject[2] = paramIntent;
      localMethod.invoke(localPreferenceManager, arrayOfObject);
      return;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null) {
      this.xmlId = paramBundle.getInt("xml");
    }
    this.mPreferenceManager = onCreatePreferenceManager();
    this.lv = ((ListView)LayoutInflater.from(getActivity()).inflate(2130903077, null));
    this.lv.setScrollBarStyle(0);
    postBindPreferences();
    ((OnPreferenceAttachedListener)getActivity()).onPreferenceAttached(getPreferenceScreen(), this.xmlId);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    postBindPreferences();
    return this.lv;
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.lv = null;
    try
    {
      Method localMethod = PreferenceManager.class.getDeclaredMethod("dispatchActivityDestroy", new Class[0]);
      localMethod.setAccessible(true);
      localMethod.invoke(this.mPreferenceManager, new Object[0]);
      return;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    ViewParent localViewParent = this.lv.getParent();
    if (localViewParent != null) {
      ((ViewGroup)localViewParent).removeView(this.lv);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("xml", this.xmlId);
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onStop()
  {
    super.onStop();
    try
    {
      Method localMethod = PreferenceManager.class.getDeclaredMethod("dispatchActivityStop", new Class[0]);
      localMethod.setAccessible(true);
      localMethod.invoke(this.mPreferenceManager, new Object[0]);
      return;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
  }
  
  public void setPreferenceScreen(PreferenceScreen paramPreferenceScreen)
  {
    try
    {
      Method localMethod = PreferenceManager.class.getDeclaredMethod("setPreferences", new Class[] { PreferenceScreen.class });
      localMethod.setAccessible(true);
      if ((((Boolean)localMethod.invoke(this.mPreferenceManager, new Object[] { paramPreferenceScreen })).booleanValue()) && (paramPreferenceScreen != null)) {
        postBindPreferences();
      }
      return;
    }
    catch (Exception localException)
    {
      Log.e("PreferenceListFragment", Log.getStackTraceString(localException));
    }
  }
  
  public static abstract interface OnPreferenceAttachedListener
  {
    public abstract void onPreferenceAttached(PreferenceScreen paramPreferenceScreen, int paramInt);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.PreferenceListFragment
 * JD-Core Version:    0.7.0.1
 */