package com.androidcentral.app.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.XmlRpcUtils;
import java.util.ArrayList;
import java.util.List;

public class SearchThreadsFragment
  extends ScrollingThreadListFragment
{
  public static final String ARG_SEARCH_STRING = "search_string";
  private String searchString;
  
  protected String getHeaderText()
  {
    return "SHOWING [" + this.forum.threads.size() + " of " + this.forum.totalThreadCount + "] RESULT(S) FOR \"" + this.searchString + "\"";
  }
  
  protected ScrollingThreadListFragment.ThreadDownloadTask getThreadDownloader()
  {
    return new SearchThreadsDownloadTask();
  }
  
  protected ScrollingThreadListFragment.ThreadListAdapter getThreadListAdapter()
  {
    return new ForumThreadListAdapter();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.searchString = getArguments().getString("search_string");
  }
  
  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (paramMenu.findItem(2131099785) != null) {
      paramMenu.removeItem(2131099785);
    }
  }
  
  public class ForumThreadListAdapter
    extends ScrollingThreadListFragment.ThreadListAdapter
  {
    public ForumThreadListAdapter()
    {
      super();
    }
    
    public void bindSubtitle(TextView paramTextView, Forum.ThreadInfo paramThreadInfo)
    {
      paramTextView.setText(paramThreadInfo.authorName + " | ");
    }
  }
  
  public class SearchThreadsDownloadTask
    extends ScrollingThreadListFragment.ThreadDownloadTask
  {
    public SearchThreadsDownloadTask()
    {
      super();
    }
    
    public String getMethod()
    {
      return "search_topic";
    }
    
    public List<MobiquoHelper.Param> getParams()
    {
      ArrayList localArrayList = new ArrayList(3);
      localArrayList.add(new MobiquoHelper.Param("base64", XmlRpcUtils.getBase64Encoded(SearchThreadsFragment.this.searchString)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(SearchThreadsFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(SearchThreadsFragment.this.endIndx)));
      return localArrayList;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SearchThreadsFragment
 * JD-Core Version:    0.7.0.1
 */