package com.androidcentral.app.fragments;

import android.content.Context;
import com.androidcentral.app.data.GalleryCategory;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.AsyncSupportLoader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.List;

class CategoryLoader
  extends AsyncSupportLoader<List<GalleryCategory>>
{
  private static final String GALLERY_URL = "http://m.androidcentral.com/mobile_app/wallpapers";
  
  public CategoryLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  public List<GalleryCategory> loadInBackground()
  {
    String str = NetUtils.get("http://m.androidcentral.com/mobile_app/wallpapers");
    if (str == null) {
      return null;
    }
    Gson localGson = new Gson();
    JsonParser localJsonParser = new JsonParser();
    try
    {
      JsonElement localJsonElement = localJsonParser.parse(str);
      (List)localGson.fromJson(localJsonElement.getAsJsonObject().get("categories"), new TypeToken() {}.getType());
    }
    catch (JsonSyntaxException localJsonSyntaxException) {}
    return null;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.CategoryLoader
 * JD-Core Version:    0.7.0.1
 */