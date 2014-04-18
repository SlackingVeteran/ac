package com.androidcentral.app.fragments;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class ParticipatedThreadsFragment
  extends ScrollingThreadListFragment
{
  protected String getHeaderText()
  {
    return "SHOWING [" + this.forum.threads.size() + " of " + this.forum.totalThreadCount + "] THREADS";
  }
  
  protected ScrollingThreadListFragment.ThreadDownloadTask getThreadDownloader()
  {
    return new ParticipatedThreadsDownloadTask();
  }
  
  protected ScrollingThreadListFragment.ThreadListAdapter getThreadListAdapter()
  {
    return new ParticipatedThreadListAdapter();
  }
  
  public class ParticipatedThreadListAdapter
    extends ScrollingThreadListFragment.ThreadListAdapter
  {
    public ParticipatedThreadListAdapter()
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
  
  public class ParticipatedThreadsDownloadTask
    extends ScrollingThreadListFragment.ThreadDownloadTask
  {
    public ParticipatedThreadsDownloadTask()
    {
      super();
    }
    
    public String getMethod()
    {
      return "get_participated_topic";
    }
    
    public List<MobiquoHelper.Param> getParams()
    {
      ArrayList localArrayList = new ArrayList(5);
      String str = SessionManager.getInstance().getUserId();
      localArrayList.add(new MobiquoHelper.Param("base64", ""));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(ParticipatedThreadsFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(ParticipatedThreadsFragment.this.endIndx)));
      localArrayList.add(new MobiquoHelper.Param("string", ""));
      localArrayList.add(new MobiquoHelper.Param("string", str));
      return localArrayList;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ParticipatedThreadsFragment
 * JD-Core Version:    0.7.0.1
 */