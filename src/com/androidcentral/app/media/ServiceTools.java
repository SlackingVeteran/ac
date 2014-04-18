package com.androidcentral.app.media;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import java.util.Iterator;
import java.util.List;

public class ServiceTools
{
  public static boolean isServiceRunning(Context paramContext, String paramString)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
    do
    {
      if (!localIterator.hasNext()) {
        return false;
      }
    } while (!((ActivityManager.RunningServiceInfo)localIterator.next()).service.getClassName().equals(paramString));
    return true;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.media.ServiceTools
 * JD-Core Version:    0.7.0.1
 */