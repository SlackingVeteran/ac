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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.androidcentral.app.BaseActivity;
import com.androidcentral.app.ForumThreadActivity;
import com.androidcentral.app.data.ForumPost;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.XmlRpcUtils;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

public class PostListFragment
  extends ListFragment
  implements AbsListView.OnScrollListener
{
  public static final String ARG_SEARCH_STRING = "search_string";
  private static final int POSTS_PER_PAGE = 20;
  private CommentListAdapter adapter;
  private PullToRefreshAttacher attacher;
  private GetPostsTask downloader;
  private int endIndx;
  private ProgressBar footerView;
  private TextView headerView;
  private ListView lv;
  private String searchString;
  private int startIndx;
  private int totalPostCount;
  
  private void addListFooter()
  {
    this.footerView = ((ProgressBar)LayoutInflater.from(getActivity()).inflate(2130903061, null));
    this.lv.addFooterView(this.footerView);
  }
  
  private void addListHeader()
  {
    this.headerView = ((TextView)LayoutInflater.from(getActivity()).inflate(2130903084, null));
    this.lv.addHeaderView(this.headerView, null, false);
  }
  
  private void fetchMorePosts()
  {
    this.startIndx = (20 + this.startIndx);
    this.endIndx = (20 + this.endIndx);
    this.downloader = new GetPostsTask();
    this.downloader.execute(new Void[0]);
  }
  
  private void refresh()
  {
    this.startIndx = 0;
    this.endIndx = 19;
    this.totalPostCount = 0;
    setListShown(false);
    this.adapter.clear();
    this.downloader = new GetPostsTask();
    this.downloader.execute(new Void[0]);
  }
  
  private void setupPullToRefresh()
  {
    this.attacher = ((BaseActivity)getActivity()).pullToRefreshAttacher;
    this.attacher.addRefreshableView(getListView(), new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        PostListFragment.this.refresh();
      }
    });
  }
  
  private void updateListFooter()
  {
    if (this.adapter.getCount() >= this.totalPostCount) {
      this.lv.removeFooterView(this.footerView);
    }
  }
  
  private void updateListHeader()
  {
    this.headerView.setText("SHOWING [" + this.adapter.getCount() + " of " + this.totalPostCount + "] RESULT(S) FOR \"" + this.searchString + "\"");
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.searchString = getArguments().getString("search_string");
    this.lv = getListView();
    addListHeader();
    addListFooter();
    this.adapter = new CommentListAdapter(getActivity());
    setListAdapter(this.adapter);
    this.lv.setOnScrollListener(this);
    this.lv.setDividerHeight(0);
    this.lv.setSelector(17170445);
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
    paramMenuInflater.inflate(2131623947, paramMenu);
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    ForumPost localForumPost = (ForumPost)paramListView.getItemAtPosition(paramInt);
    Intent localIntent = new Intent(getActivity(), ForumThreadActivity.class);
    localIntent.putExtra("thread_id", localForumPost.threadId);
    localIntent.putExtra("thread_title", localForumPost.threadTitle);
    localIntent.putExtra("forum_id", localForumPost.forumId);
    localIntent.putExtra("post_id", localForumPost.postId);
    startActivity(localIntent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
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
      if ((i != 0) && (this.adapter.getCount() < this.totalPostCount) && (this.downloader.getStatus() != AsyncTask.Status.RUNNING)) {
        fetchMorePosts();
      }
      return;
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  public void onStop()
  {
    super.onStop();
    if (this.downloader != null) {
      this.downloader.cancel(true);
    }
  }
  
  class GetPostsTask
    extends AsyncTask<Void, Void, List<ForumPost>>
  {
    private static final String TAG = "GetPostsTask";
    
    GetPostsTask() {}
    
    private List<ForumPost> parseResponse(String paramString)
    {
      if (paramString == null)
      {
        localObject1 = null;
        return localObject1;
      }
      Object localObject1 = new ArrayList();
      for (;;)
      {
        XmlPullParser localXmlPullParser;
        Object localObject2;
        try
        {
          localXmlPullParser = Xml.newPullParser();
          localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
          localXmlPullParser.setInput(new StringReader(paramString));
          localXmlPullParser.nextTag();
          localObject2 = null;
          if (localXmlPullParser.next() == 1)
          {
            if (localObject2 == null) {
              break;
            }
            ((List)localObject1).add(localObject2);
            return localObject1;
          }
        }
        catch (Exception localException)
        {
          Log.e("GetPostsTask", Log.getStackTraceString(localException));
          return null;
        }
        if ((localXmlPullParser.getEventType() == 2) && (localXmlPullParser.getName().equals("name")))
        {
          String str = XmlRpcUtils.readText(localXmlPullParser);
          if (str.equals("total_post_num"))
          {
            PostListFragment.this.totalPostCount = XmlRpcUtils.getIntValue(localXmlPullParser);
          }
          else if (str.equals("forum_id"))
          {
            if (localObject2 != null) {
              ((List)localObject1).add(localObject2);
            }
            localObject2 = new ForumPost();
            ((ForumPost)localObject2).forumId = XmlRpcUtils.getStringValue(localXmlPullParser);
          }
          else if (str.equals("forum_name"))
          {
            ((ForumPost)localObject2).forumName = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str.equals("topic_id"))
          {
            ((ForumPost)localObject2).threadId = XmlRpcUtils.getStringValue(localXmlPullParser);
          }
          else if (str.equals("post_id"))
          {
            ((ForumPost)localObject2).postId = XmlRpcUtils.getStringValue(localXmlPullParser);
          }
          else if (str.equals("topic_title"))
          {
            ((ForumPost)localObject2).threadTitle = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str.equals("post_author_name"))
          {
            ((ForumPost)localObject2).authorName = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
          else if (str.equals("short_content"))
          {
            ((ForumPost)localObject2).shortContent = XmlRpcUtils.getBase64Value(localXmlPullParser);
          }
        }
      }
    }
    
    protected List<ForumPost> doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(3);
      localArrayList.add(new MobiquoHelper.Param("base64", XmlRpcUtils.getBase64Encoded(PostListFragment.this.searchString)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(PostListFragment.this.startIndx)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(PostListFragment.this.endIndx)));
      return parseResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("search_post", localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(List<ForumPost> paramList)
    {
      if (paramList != null)
      {
        if (paramList.isEmpty()) {
          PostListFragment.this.totalPostCount = PostListFragment.this.adapter.getCount();
        }
        PostListFragment.this.adapter.addAll(paramList);
        PostListFragment.this.updateListHeader();
        PostListFragment.this.updateListFooter();
      }
      PostListFragment.this.setListShown(true);
      PostListFragment.this.attacher.setRefreshComplete();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.PostListFragment
 * JD-Core Version:    0.7.0.1
 */