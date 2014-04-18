package com.androidcentral.app.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedList;
import java.util.List;

public class ForumSearchHistory
{
  private static final String PREFS_KEY = "forum_search_history";
  private static final Gson gson = new Gson();
  private SharedPreferences.Editor editor;
  private List<String> history;
  private SharedPreferences prefs;
  
  public ForumSearchHistory(Context paramContext)
  {
    this.prefs = paramContext.getSharedPreferences("AndroidCentralPrefs", 0);
    this.editor = this.prefs.edit();
    if (this.prefs.contains("forum_search_history"))
    {
      this.history = ((List)gson.fromJson(this.prefs.getString("forum_search_history", ""), new TypeToken() {}.getType()));
      return;
    }
    this.history = new LinkedList();
  }
  
  public void addSearch(String paramString)
  {
    if (this.history.contains(paramString)) {
      this.history.remove(paramString);
    }
    this.history.add(0, paramString);
    this.editor.putString("forum_search_history", gson.toJson(this.history));
    this.editor.apply();
  }
  
  public void clear()
  {
    this.history.clear();
    this.editor.remove("forum_search_history");
    this.editor.apply();
  }
  
  public List<String> getSearchHistory()
  {
    return this.history;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumSearchHistory
 * JD-Core Version:    0.7.0.1
 */