package com.androidcentral.app.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.androidcentral.app.LoginActivity;

public class UiUtils
{
  public static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = paramOptions.outHeight;
    int j = paramOptions.outWidth;
    int k = 1;
    if ((i > paramInt2) || (j > paramInt1)) {}
    for (;;)
    {
      if ((i / k <= paramInt2) && (j / k <= paramInt1)) {
        return k;
      }
      k *= 2;
    }
  }
  
  public static int dpToPx(Context paramContext, int paramInt)
  {
    return (int)(0.5F + paramContext.getResources().getDisplayMetrics().density * paramInt);
  }
  
  public static Point getDisplayPixels(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    return new Point(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
  }
  
  public static String getThemeAnimation(Context paramContext)
  {
    return paramContext.getSharedPreferences("AndroidCentralPrefs", 0).getString("pref_screen_animation", "");
  }
  
  public static boolean isNightTheme(Context paramContext)
  {
    return paramContext.getSharedPreferences("AndroidCentralPrefs", 0).getBoolean("pref_night_mode", false);
  }
  
  public static void setTheme(Context paramContext)
  {
    String str = getThemeAnimation(paramContext);
    int i = 2131230744;
    if (isNightTheme(paramContext)) {
      if (str.equals("None")) {
        i = 2131230736;
      }
    }
    for (;;)
    {
      paramContext.setTheme(i);
      return;
      if (str.equals("Fade"))
      {
        i = 2131230737;
      }
      else if (str.equals("Zoom"))
      {
        i = 2131230747;
        continue;
        if (str.equals("None")) {
          i = 2131230734;
        } else if (str.equals("Fade")) {
          i = 2131230735;
        } else if (str.equals("Zoom")) {
          i = 2131230744;
        }
      }
    }
  }
  
  public static void showLoginDialog(Context paramContext)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle(2131165205).setItems(2131558402, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent(UiUtils.this, LoginActivity.class);
        if (paramAnonymousInt == 0) {}
        for (boolean bool = true;; bool = false)
        {
          localIntent.putExtra("login_or_signup", bool);
          UiUtils.this.startActivity(localIntent);
          return;
        }
      }
    });
    localBuilder.create().show();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.UiUtils
 * JD-Core Version:    0.7.0.1
 */