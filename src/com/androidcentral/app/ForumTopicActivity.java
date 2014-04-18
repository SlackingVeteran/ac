package com.androidcentral.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.fragments.ForumThreadsFragment;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.SessionManager;
import com.androidcentral.app.net.XmlRpcUtils;
import com.androidcentral.app.net.XmlRpcUtils.ResultResponse;
import com.androidcentral.app.util.UiUtils;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class ForumTopicActivity
  extends DrawerActivity
{
  public static final String EXTRA_FORUM_ID = "forum_id";
  public static final String EXTRA_FORUM_NAME = "name";
  private static final int NEW_THREAD_REQUEST;
  private int forumId;
  private String forumName;
  private TabPageIndicator indicator;
  private SectionsPagerAdapter pagerAdapter;
  private ViewPager topicSectionPager;
  
  private void launchNewThreadActivity()
  {
    Intent localIntent = new Intent(this, NewThreadActivity.class);
    localIntent.putExtras(getIntent());
    startActivityForResult(localIntent, 0);
  }
  
  private void updateSubscription()
  {
    Forum localForum = getCurrentFragment().forum;
    if (localForum != null) {
      if (!localForum.isSubscribed) {
        break label39;
      }
    }
    label39:
    for (boolean bool = false;; bool = true)
    {
      new ForumSubscribeTask(bool).execute(new Void[0]);
      return;
    }
  }
  
  public ForumThreadsFragment getCurrentFragment()
  {
    return (ForumThreadsFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:2131099679:0");
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 0) && (paramInt2 == -1))
    {
      this.pullToRefreshAttacher.setRefreshing(true);
      getCurrentFragment().refresh();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903048);
    this.forumId = getIntent().getIntExtra("forum_id", -1);
    this.forumName = getIntent().getStringExtra("name");
    this.topicSectionPager = ((ViewPager)findViewById(2131099679));
    this.pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    this.topicSectionPager.setAdapter(this.pagerAdapter);
    this.indicator = ((TabPageIndicator)findViewById(2131099678));
    this.indicator.setViewPager(this.topicSectionPager);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623941, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099776: 
      if (this.sessionManager.isLoggedIn())
      {
        launchNewThreadActivity();
        return true;
      }
      UiUtils.showLoginDialog(this);
      return true;
    }
    if (this.sessionManager.isLoggedIn())
    {
      updateSubscription();
      return true;
    }
    UiUtils.showLoginDialog(this);
    return true;
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    ForumThreadsFragment localForumThreadsFragment = getCurrentFragment();
    MenuItem localMenuItem = paramMenu.findItem(2131099777);
    if (localForumThreadsFragment.forum == null) {
      localMenuItem.setIcon(2130837513);
    }
    for (;;)
    {
      return true;
      if (localForumThreadsFragment.forum.isSubscribed)
      {
        localMenuItem.setIcon(2130837515);
        localMenuItem.setTitle(2131165204);
      }
      else
      {
        localMenuItem.setIcon(2130837514);
        localMenuItem.setTitle(2131165203);
      }
    }
  }
  
  public class ForumSubscribeTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private boolean subscribe;
    
    public ForumSubscribeTask(boolean paramBoolean)
    {
      this.subscribe = paramBoolean;
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumTopicActivity.this.forumId)));
      if (this.subscribe) {}
      for (String str = "subscribe_forum";; str = "unsubscribe_forum") {
        return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod(str, localArrayList), "text/xml"));
      }
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success)
      {
        if (this.subscribe) {}
        for (str = "Subscribed to " + ForumTopicActivity.this.forumName;; str = "Unsubscribed from " + ForumTopicActivity.this.forumName)
        {
          ForumTopicActivity.this.getCurrentFragment().forum.isSubscribed = this.subscribe;
          ForumTopicActivity.this.invalidateOptionsMenu();
          Toast.makeText(ForumTopicActivity.this, str, 0).show();
          return;
        }
      }
      if (this.subscribe) {}
      for (String str = "Could not subscribe to " + ForumTopicActivity.this.forumName;; str = "Could not unsubscribe from " + ForumTopicActivity.this.forumName + ": " + paramResultResponse.error) {
        break;
      }
    }
  }
  
  public class SectionsPagerAdapter
    extends FragmentPagerAdapter
  {
    public SectionsPagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    public int getCount()
    {
      return 2;
    }
    
    public Fragment getItem(int paramInt)
    {
      ForumThreadsFragment localForumThreadsFragment = new ForumThreadsFragment();
      Bundle localBundle = ForumTopicActivity.this.getIntent().getExtras();
      if (paramInt == 0) {}
      for (String str = "";; str = "TOP")
      {
        localBundle.putString("thread_type", str);
        localForumThreadsFragment.setArguments(localBundle);
        return localForumThreadsFragment;
      }
    }
    
    public CharSequence getPageTitle(int paramInt)
    {
      return ForumTopicActivity.this.getResources().getStringArray(2131558405)[paramInt];
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumTopicActivity
 * JD-Core Version:    0.7.0.1
 */