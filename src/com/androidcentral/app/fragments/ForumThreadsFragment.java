package com.androidcentral.app.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper.Param;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForumThreadsFragment
  extends ScrollingThreadListFragment
{
  private int forumId;
  private String forumName;
  private String threadType;
  
  protected String getHeaderText()
  {
    return this.forumName.toUpperCase(Locale.getDefault()) + " [" + this.forum.threads.size() + " of " + this.forum.totalThreadCount + "]";
  }
  
  protected ScrollingThreadListFragment.ThreadDownloadTask getThreadDownloader()
  {
    return new ForumThreadsDownloadTask();
  }
  
  protected ScrollingThreadListFragment.ThreadListAdapter getThreadListAdapter()
  {
    return new ForumThreadListAdapter();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.forumId = getArguments().getInt("forum_id");
    this.forumName = getArguments().getString("name");
    this.threadType = getArguments().getString("thread_type");
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
  
  public class ForumThreadsDownloadTask
    extends ScrollingThreadListFragment.ThreadDownloadTask
  {
    public ForumThreadsDownloadTask()
    {
      super();
    }
    
    public String getMethod()
    {
      return "get_topic";
    }
    
    public List<MobiquoHelper.Param> getParams()
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumThreadsFragment.this.forumId)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(ForumThreadsFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(ForumThreadsFragment.this.endIndx)));
      localArrayList.add(new MobiquoHelper.Param("string", ForumThreadsFragment.this.threadType));
      return localArrayList;
    }
    
    protected void onPostExecute(Forum paramForum)
    {
      super.onPostExecute(paramForum);
      ForumThreadsFragment.this.getActivity().invalidateOptionsMenu();
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ForumThreadsFragment
 * JD-Core Version:    0.7.0.1
 */