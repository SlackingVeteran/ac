package com.androidcentral.app.fragments;

import android.content.Context;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.AsyncSupportLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class VideoLoader
  extends AsyncSupportLoader<List<Video>>
{
  public VideoLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  public List<Video> loadInBackground()
  {
    String str1 = NetUtils.get("https://gdata.youtube.com/feeds/api/videos?author=AndroidCentral&orderby=published&v=2&alt=jsonc");
    Object localObject = null;
    if (str1 == null) {}
    for (;;)
    {
      return localObject;
      JsonParser localJsonParser = new JsonParser();
      try
      {
        JsonElement localJsonElement = localJsonParser.parse(str1);
        JsonArray localJsonArray = localJsonElement.getAsJsonObject().get("data").getAsJsonObject().get("items").getAsJsonArray();
        localObject = new ArrayList(localJsonArray.size());
        Iterator localIterator = localJsonArray.iterator();
        while (localIterator.hasNext())
        {
          JsonObject localJsonObject = ((JsonElement)localIterator.next()).getAsJsonObject();
          if (localJsonObject.get("duration").getAsInt() != 0)
          {
            String str2 = localJsonObject.get("id").getAsString();
            String str3 = localJsonObject.get("title").getAsString();
            String str4 = localJsonObject.get("thumbnail").getAsJsonObject().get("hqDefault").getAsString();
            ((List)localObject).add(new Video(str2, str3, localJsonObject.get("player").getAsJsonObject().get("default").getAsString(), str4));
          }
        }
        return null;
      }
      catch (JsonSyntaxException localJsonSyntaxException) {}
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.VideoLoader
 * JD-Core Version:    0.7.0.1
 */