package com.androidcentral.app.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.BaseActivity;
import com.androidcentral.app.ForumSearchActivity;
import com.androidcentral.app.ForumThreadActivity;
import com.androidcentral.app.data.Forum;
import com.androidcentral.app.data.Forum.ThreadInfo;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.SessionManager;
import com.androidcentral.app.net.XmlRpcUtils;
import java.io.StringReader;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

public abstract class ScrollingThreadListFragment
  extends ListFragment
  implements AbsListView.OnScrollListener
{
  private static final int THREADS_PER_PAGE = 20;
  private ThreadListAdapter adapter;
  private PullToRefreshAttacher attacher;
  protected volatile int endIndx;
  private ProgressBar footerView;
  public Forum forum;
  private TextView headerView;
  private SessionManager sessionManager;
  protected volatile int startIndx;
  private ThreadDownloadTask threadDownloader;
  
  private void addListFooter()
  {
    this.footerView = ((ProgressBar)LayoutInflater.from(getActivity()).inflate(2130903061, null));
    getListView().addFooterView(this.footerView);
  }
  
  private void addListHeader()
  {
    this.headerView = ((TextView)LayoutInflater.from(getActivity()).inflate(2130903084, null));
    getListView().addHeaderView(this.headerView, null, false);
  }
  
  private void fetchThreads()
  {
    this.threadDownloader = getThreadDownloader();
    this.threadDownloader.execute(new Void[0]);
  }
  
  private void initEmptyView()
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130903056, null);
    ((TextView)localView.findViewById(2131099696)).setText("No threads to display");
    ((ViewGroup)getListView().getParent()).addView(localView);
    getListView().setEmptyView(localView);
  }
  
  private void initListView()
  {
    ListView localListView = getListView();
    localListView.setDivider(null);
    localListView.setDividerHeight(0);
    localListView.setSelector(17170445);
  }
  
  private void setupPullToRefresh()
  {
    this.attacher = ((BaseActivity)getActivity()).pullToRefreshAttacher;
    this.attacher.addRefreshableView(getListView(), new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        ScrollingThreadListFragment.this.refresh();
      }
    });
  }
  
  private void updateListFooter()
  {
    if (this.forum.threads.size() >= this.forum.totalThreadCount) {
      getListView().removeFooterView(this.footerView);
    }
  }
  
  private void updateListHeader()
  {
    this.headerView.setText(getHeaderText());
  }
  
  protected abstract String getHeaderText();
  
  protected abstract ThreadDownloadTask getThreadDownloader();
  
  protected abstract ThreadListAdapter getThreadListAdapter();
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.sessionManager = SessionManager.getInstance();
    initListView();
    initEmptyView();
    addListFooter();
    addListHeader();
    this.adapter = getThreadListAdapter();
    setListAdapter(this.adapter);
    getListView().setOnScrollListener(this);
    setupPullToRefresh();
    refresh();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623948, paramMenu);
    paramMenuInflater.inflate(2131623947, paramMenu);
  }
  
  public void onDetach()
  {
    super.onDetach();
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Forum.ThreadInfo localThreadInfo = (Forum.ThreadInfo)paramListView.getItemAtPosition(paramInt);
    Intent localIntent = new Intent(getActivity(), ForumThreadActivity.class);
    localIntent.putExtra("thread_id", localThreadInfo.id);
    localIntent.putExtra("thread_title", localThreadInfo.title);
    localIntent.putExtra("forum_id", localThreadInfo.forumId);
    startActivity(localIntent);
    if (this.sessionManager.isLoggedIn())
    {
      localThreadInfo.unread = false;
      this.adapter.notifyDataSetChanged();
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    case 2131099784: 
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099785: 
      startActivity(new Intent(getActivity(), ForumSearchActivity.class));
      return true;
    }
    this.attacher.setRefreshing(true);
    refresh();
    return true;
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 + paramInt2 >= paramInt3) {}
    for (int i = 1;; i = 0)
    {
      if ((i != 0) && (this.forum != null) && (paramInt3 < this.forum.totalThreadCount) && (this.threadDownloader.getStatus() != AsyncTask.Status.RUNNING)) {
        fetchThreads();
      }
      return;
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  public void onStop()
  {
    super.onStop();
    this.threadDownloader.cancel(true);
  }
  
  public void refresh()
  {
    this.startIndx = -20;
    this.endIndx = -1;
    this.forum = null;
    this.adapter.clear();
    setListShown(false);
    fetchThreads();
  }
  
  public abstract class ThreadDownloadTask
    extends AsyncTask<Void, Void, Forum>
  {
    private static final String TAG = "ThreadDownloadTask";
    
    public ThreadDownloadTask() {}
    
    private Forum parseResponse(String paramString)
    {
      if (paramString == null)
      {
        localForum = null;
        return localForum;
      }
      String str1 = null;
      String str2 = null;
      Forum localForum = new Forum();
      for (;;)
      {
        XmlPullParser localXmlPullParser;
        Object localObject;
        try
        {
          localXmlPullParser = Xml.newPullParser();
          localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
          localXmlPullParser.setInput(new StringReader(paramString));
          localXmlPullParser.nextTag();
          localObject = null;
          if (localXmlPullParser.next() == 1)
          {
            if (localObject == null) {
              break;
            }
            localForum.threads.add(localObject);
            return localForum;
          }
        }
        catch (Exception localException)
        {
          Log.e("ThreadDownloadTask", Log.getStackTraceString(localException));
          return null;
        }
        if ((localXmlPullParser.getEventType() == 2) && (localXmlPullParser.getName().equals("name")))
        {
          String str3 = XmlRpcUtils.readText(localXmlPullParser);
          if (str3.equals("total_topic_num"))
          {
            localForum.totalThreadCount = XmlRpcUtils.getIntValue(localXmlPullParser);
          }
          else if (str3.equals("is_subscribed"))
          {
            localForum.isSubscribed = XmlRpcUtils.getBooleanValue(localXmlPullParser);
          }
          else if (str3.equals("forum_id"))
          {
            str1 = XmlRpcUtils.getStringValue(localXmlPullParser);
          }
          else if (str3.equals("forum_name"))
          {
            str2 = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str3.equals("topic_id"))
          {
            if (localObject != null) {
              localForum.threads.add(localObject);
            }
            localObject = new Forum.ThreadInfo();
            ((Forum.ThreadInfo)localObject).id = XmlRpcUtils.getStringValue(localXmlPullParser);
            ((Forum.ThreadInfo)localObject).forumId = str1;
            ((Forum.ThreadInfo)localObject).forumName = str2;
          }
          else if (str3.equals("topic_title"))
          {
            ((Forum.ThreadInfo)localObject).title = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str3.equals("topic_author_name"))
          {
            ((Forum.ThreadInfo)localObject).authorName = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str3.equals("reply_number"))
          {
            ((Forum.ThreadInfo)localObject).numReplies = XmlRpcUtils.getIntValue(localXmlPullParser);
          }
          else if (str3.equals("post_author_name"))
          {
            ((Forum.ThreadInfo)localObject).authorName = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str3.equals("new_post"))
          {
            ((Forum.ThreadInfo)localObject).unread = XmlRpcUtils.getBooleanValue(localXmlPullParser);
          }
        }
      }
    }
    
    protected Forum doInBackground(Void... paramVarArgs)
    {
      return parseResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod(getMethod(), getParams()), "text/xml"));
    }
    
    public abstract String getMethod();
    
    public abstract List<MobiquoHelper.Param> getParams();
    
    protected void onPostExecute(Forum paramForum)
    {
      if (paramForum != null) {
        if (ScrollingThreadListFragment.this.forum == null)
        {
          ScrollingThreadListFragment.this.forum = paramForum;
          if (ScrollingThreadListFragment.this.forum.threads.size() < 19) {
            ScrollingThreadListFragment.this.forum.totalThreadCount = ScrollingThreadListFragment.this.forum.threads.size();
          }
          ScrollingThreadListFragment.this.adapter.addAll(paramForum.threads);
          ScrollingThreadListFragment.this.updateListFooter();
          ScrollingThreadListFragment.this.updateListHeader();
          ScrollingThreadListFragment.this.setListShown(true);
        }
      }
      for (;;)
      {
        ScrollingThreadListFragment.this.attacher.setRefreshComplete();
        return;
        if (paramForum.threads.isEmpty())
        {
          ScrollingThreadListFragment.this.forum.totalThreadCount = ScrollingThreadListFragment.this.forum.threads.size();
          break;
        }
        ScrollingThreadListFragment.this.forum.threads.addAll(paramForum.threads);
        break;
        Toast.makeText(ScrollingThreadListFragment.this.getActivity(), 2131165211, 0).show();
      }
    }
    
    protected void onPreExecute()
    {
      ScrollingThreadListFragment localScrollingThreadListFragment1 = ScrollingThreadListFragment.this;
      localScrollingThreadListFragment1.startIndx = (20 + localScrollingThreadListFragment1.startIndx);
      ScrollingThreadListFragment localScrollingThreadListFragment2 = ScrollingThreadListFragment.this;
      localScrollingThreadListFragment2.endIndx = (20 + localScrollingThreadListFragment2.endIndx);
    }
  }
  
  public abstract class ThreadListAdapter
    extends ArrayAdapter<Forum.ThreadInfo>
  {
    private LayoutInflater inflater = LayoutInflater.from(getContext());
    
    public ThreadListAdapter()
    {
      super(0);
    }
    
    public abstract void bindSubtitle(TextView paramTextView, Forum.ThreadInfo paramThreadInfo);
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ScrollingThreadListFragment.ViewHolder localViewHolder;
      Forum.ThreadInfo localThreadInfo;
      int i;
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903082, paramViewGroup, false);
        localViewHolder = new ScrollingThreadListFragment.ViewHolder(null);
        localViewHolder.titleView = ((TextView)paramView.findViewById(2131099746));
        localViewHolder.subtitleView = ((TextView)paramView.findViewById(2131099747));
        localViewHolder.replyCountView = ((TextView)paramView.findViewById(2131099748));
        paramView.setTag(localViewHolder);
        localThreadInfo = (Forum.ThreadInfo)getItem(paramInt);
        if (ScrollingThreadListFragment.this.sessionManager.isLoggedIn())
        {
          TextView localTextView = localViewHolder.titleView;
          if (!localThreadInfo.unread) {
            break label208;
          }
          i = 1;
          label115:
          localTextView.setTypeface(null, i);
        }
        localViewHolder.titleView.setText(localThreadInfo.title);
        if (localThreadInfo.numReplies < 1000) {
          break label214;
        }
      }
      label208:
      label214:
      for (String str1 = "1k+";; str1 = String.valueOf(localThreadInfo.numReplies))
      {
        String str2 = str1 + " REPLIES";
        localViewHolder.replyCountView.setText(str2);
        bindSubtitle(localViewHolder.subtitleView, localThreadInfo);
        return paramView;
        localViewHolder = (ScrollingThreadListFragment.ViewHolder)paramView.getTag();
        break;
        i = 0;
        break label115;
      }
    }
  }
  
  private static class ViewHolder
  {
    public TextView replyCountView;
    public TextView subtitleView;
    public TextView titleView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ScrollingThreadListFragment
 * JD-Core Version:    0.7.0.1
 */