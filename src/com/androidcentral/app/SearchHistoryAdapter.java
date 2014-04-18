package com.androidcentral.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.androidcentral.app.data.ForumSearchHistory;
import java.util.List;

class SearchHistoryAdapter
  extends ArrayAdapter<String>
{
  private ForumSearchHistory history;
  
  public SearchHistoryAdapter(Context paramContext)
  {
    super(paramContext, 17367043);
    this.history = new ForumSearchHistory(paramContext);
  }
  
  public void add(String paramString)
  {
    this.history.addSearch(paramString);
    notifyDataSetChanged();
  }
  
  public void clear()
  {
    this.history.clear();
    notifyDataSetChanged();
  }
  
  public int getCount()
  {
    return this.history.getSearchHistory().size();
  }
  
  public String getItem(int paramInt)
  {
    return (String)this.history.getSearchHistory().get(paramInt);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.SearchHistoryAdapter
 * JD-Core Version:    0.7.0.1
 */