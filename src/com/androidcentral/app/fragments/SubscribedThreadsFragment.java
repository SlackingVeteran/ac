package com.androidcentral.app.fragments;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper.Param;
import java.util.ArrayList;
import java.util.List;

public class SubscribedThreadsFragment
  extends ScrollingThreadListFragment
{
  protected String getHeaderText()
  {
    return "SHOWING [" + this.forum.threads.size() + " of " + this.forum.totalThreadCount + "] THREADS";
  }
  
  protected ScrollingThreadListFragment.ThreadDownloadTask getThreadDownloader()
  {
    return new SubscribedThreadsDownloadTask();
  }
  
  protected ScrollingThreadListFragment.ThreadListAdapter getThreadListAdapter()
  {
    return new SubscribedThreadListAdapter();
  }
  
  public class SubscribedThreadListAdapter
    extends ScrollingThreadListFragment.ThreadListAdapter
  {
    public SubscribedThreadListAdapter()
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
  
  public class SubscribedThreadsDownloadTask
    extends ScrollingThreadListFragment.ThreadDownloadTask
  {
    public SubscribedThreadsDownloadTask()
    {
      super();
    }
    
    public String getMethod()
    {
      return "get_subscribed_topic";
    }
    
    public List<MobiquoHelper.Param> getParams()
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(SubscribedThreadsFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(SubscribedThreadsFragment.this.endIndx)));
      return localArrayList;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SubscribedThreadsFragment
 * JD-Core Version:    0.7.0.1
 */