package com.androidcentral.app.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeHelper
{
  private static final Pattern YT_ID_PATTERN = Pattern.compile("^.*(youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=|\\&v=)([^#\\&\\?]*).*", 34);
  
  public static String getVideoId(String paramString)
  {
    Matcher localMatcher = YT_ID_PATTERN.matcher(paramString);
    if (localMatcher.find()) {
      return localMatcher.group(2);
    }
    return null;
  }
  
  public static void launchPlayer(Context paramContext, String paramString, boolean paramBoolean)
  {
    Activity localActivity = (Activity)paramContext;
    if (paramBoolean) {}
    for (boolean bool = false;; bool = true)
    {
      Intent localIntent = YouTubeStandalonePlayer.createVideoIntent(localActivity, "AIzaSyBvXbF2AyYwZBwdLORms-cq7CHmTk2iGRY", paramString, 0, true, bool);
      try
      {
        paramContext.startActivity(localIntent);
        return;
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + paramString)));
      }
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.YouTubeHelper
 * JD-Core Version:    0.7.0.1
 */