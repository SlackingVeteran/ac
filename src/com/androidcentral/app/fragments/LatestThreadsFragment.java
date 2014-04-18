package com.androidcentral.app.fragments;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper.Param;
import java.util.ArrayList;
import java.util.List;

public class LatestThreadsFragment
  extends ScrollingThreadListFragment
{
  protected String getHeaderText()
  {
    return "SHOWING [" + this.forum.threads.size() + " of " + this.forum.totalThreadCount + "] THREADS";
  }
  
  protected ScrollingThreadListFragment.ThreadDownloadTask getThreadDownloader()
  {
    return new LatestThreadsDownloadTask();
  }
  
  protected ScrollingThreadListFragment.ThreadListAdapter getThreadListAdapter()
  {
    return new LatestThreadListAdapter();
  }
  
  public class LatestThreadListAdapter
    extends ScrollingThreadListFragment.ThreadListAdapter
  {
    public LatestThreadListAdapter()
    {
      super();
    }
    
    public void bindSubtitle(TextView paramTextView, Forum.ThreadInfo paramThreadInfo)
    {
      SpannableString localSpannableString = new SpannableString(paramThreadInfo.forumName + " | " + paramThreadInfo.authorName + " | ");
      localSpannableString.setSpan(new StyleSpan(2), 0, paramThreadInfo.forumName.length(), 0);
      paramTextView.setText(localSpannableString);
    }
  }
  
  public class LatestThreadsDownloadTask
    extends ScrollingThreadListFragment.ThreadDownloadTask
  {
    public LatestThreadsDownloadTask()
    {
      super();
    }
    
    public String getMethod()
    {
      return "get_latest_topic";
    }
    
    public List<MobiquoHelper.Param> getParams()
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(LatestThreadsFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(LatestThreadsFragment.this.endIndx)));
      return localArrayList;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.LatestThreadsFragment
 * JD-Core Version:    0.7.0.1
 */