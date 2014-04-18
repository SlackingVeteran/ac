package com.androidcentral.app;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.data.Comment;
import com.androidcentral.app.fragments.CommentOptionsFragment;
import com.androidcentral.app.fragments.CommentOptionsFragment.CommentDialogListener;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.SessionManager;
import com.androidcentral.app.util.AsyncLoader;
import com.androidcentral.app.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

public class ArticleCommentsActivity
  extends DrawerActivity
  implements LoaderManager.LoaderCallbacks<List<Comment>>, AdapterView.OnItemClickListener, CommentOptionsFragment.CommentDialogListener
{
  public static final String EXTRA_HERO_IMG = "heroImage";
  public static final String EXTRA_NID = "nid";
  public static final String EXTRA_NUM_COMMENTS = "commentCount";
  public static final String EXTRA_SERVER = "server";
  public static final String EXTRA_TITLE = "title";
  public static final String EXTRA_TM13 = "isTalkMobile";
  private static final int POST_COMMENT_REQUEST;
  private CommentsAdapter adapter;
  private int commentCount;
  private String commentServer;
  private ListView commentsList;
  private View emptyView;
  private String heroImage;
  private CommentsLoader loader;
  private long nid;
  private String title;
  
  private void initFooter()
  {
    View localView = new View(this);
    localView.setLayoutParams(new AbsListView.LayoutParams(-1, UiUtils.dpToPx(this, 10)));
    this.commentsList.addFooterView(localView);
  }
  
  private void initHeader()
  {
    View localView = LayoutInflater.from(this).inflate(2130903055, null);
    ((TextView)localView.findViewById(2131099694)).setText(this.title);
    ImageView localImageView = (ImageView)localView.findViewById(2131099693);
    ImageLoader.getInstance().displayImage(this.heroImage, localImageView);
    this.commentsList.addHeaderView(localView, null, false);
    updateHeaderCommentCount(this.commentCount);
  }
  
  private void postNewComment(String paramString1, boolean paramBoolean, String paramString2)
  {
    if (this.sessionManager.isLoggedIn())
    {
      Intent localIntent = new Intent(this, NewCommentActivity.class);
      localIntent.putExtra("nid", this.nid);
      localIntent.putExtra("reply_cid", paramString1);
      localIntent.putExtra("server", this.commentServer);
      localIntent.putExtra("edit_mode", paramBoolean);
      localIntent.putExtra("comment", paramString2);
      startActivityForResult(localIntent, 0);
      return;
    }
    UiUtils.showLoginDialog(this);
  }
  
  private void refresh()
  {
    this.pullToRefreshAttacher.setRefreshing(true);
    this.loader.onContentChanged();
  }
  
  private void setupPullToRefresh()
  {
    this.pullToRefreshAttacher.addRefreshableView(this.commentsList, new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        ArticleCommentsActivity.this.refresh();
      }
    });
  }
  
  private void updateHeaderCommentCount(int paramInt)
  {
    ((TextView)this.commentsList.findViewById(2131099695)).setText(String.valueOf(paramInt) + " COMMENTS");
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 0) && (paramInt2 == -1)) {
      refresh();
    }
  }
  
  public void onCommentOptionSelected(int paramInt1, int paramInt2)
  {
    Comment localComment = (Comment)this.commentsList.getItemAtPosition(paramInt1);
    switch (paramInt2)
    {
    default: 
      return;
    case 0: 
      postNewComment(localComment.cid, false, null);
      return;
    }
    postNewComment(localComment.cid, true, localComment.content);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903040);
    Intent localIntent = getIntent();
    this.nid = localIntent.getLongExtra("nid", 0L);
    this.title = localIntent.getStringExtra("title");
    this.commentCount = localIntent.getIntExtra("commentCount", 0);
    this.heroImage = localIntent.getStringExtra("heroImage");
    this.commentServer = localIntent.getStringExtra("server");
    this.commentsList = ((ListView)findViewById(2131099661));
    this.emptyView = findViewById(16908292);
    this.emptyView.setVisibility(0);
    initHeader();
    initFooter();
    this.adapter = new CommentsAdapter(this);
    this.commentsList.setAdapter(this.adapter);
    this.commentsList.setOnItemClickListener(this);
    setupPullToRefresh();
    this.loader = ((CommentsLoader)getLoaderManager().initLoader(0, null, this));
  }
  
  public Loader<List<Comment>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new CommentsLoader(this, this.nid, this.commentServer);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623936, paramMenu);
    return true;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Comment localComment = (Comment)this.commentsList.getItemAtPosition(paramInt);
    CommentOptionsFragment.newInstance(paramInt, localComment.uid, localComment.postdate).show(getSupportFragmentManager(), "CommentOptionsFragment");
  }
  
  public void onLoadFinished(Loader<List<Comment>> paramLoader, List<Comment> paramList)
  {
    if (paramList != null)
    {
      this.adapter.clear();
      this.adapter.addAll(paramList);
      updateHeaderCommentCount(paramList.size());
    }
    for (;;)
    {
      this.emptyView.setVisibility(8);
      this.pullToRefreshAttacher.setRefreshComplete();
      return;
      Toast.makeText(this, 2131165211, 0).show();
    }
  }
  
  public void onLoaderReset(Loader<List<Comment>> paramLoader)
  {
    this.adapter.clear();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099763: 
      refresh();
      return true;
    }
    postNewComment(null, false, null);
    return true;
  }
  
  public static class CommentsLoader
    extends AsyncLoader<List<Comment>>
  {
    private long nid;
    private String server;
    
    public CommentsLoader(Context paramContext, long paramLong, String paramString)
    {
      super();
      this.nid = paramLong;
      this.server = paramString;
    }
    
    private List<Comment> flattenComments(List<Comment> paramList)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return localArrayList;
        }
        Comment localComment = (Comment)localIterator.next();
        localComment.nestedLevel = 0;
        localArrayList.add(localComment);
        flattenReplies(localComment, 1, localArrayList);
      }
    }
    
    private void flattenReplies(Comment paramComment, int paramInt, List<Comment> paramList)
    {
      if ((paramComment.replies == null) || (paramComment.replies.isEmpty())) {}
      for (;;)
      {
        return;
        Iterator localIterator = paramComment.replies.iterator();
        while (localIterator.hasNext())
        {
          Comment localComment = (Comment)localIterator.next();
          localComment.nestedLevel = paramInt;
          paramList.add(localComment);
          flattenReplies(localComment, paramInt + 1, paramList);
        }
      }
    }
    
    public List<Comment> loadInBackground()
    {
      String str = NetUtils.get("http://" + this.server + "/node/" + this.nid + "/comments/json");
      if (str == null) {
        return null;
      }
      Gson localGson = new Gson();
      JsonParser localJsonParser = new JsonParser();
      try
      {
        JsonElement localJsonElement = localJsonParser.parse(str);
        flattenComments((List)localGson.fromJson(localJsonElement.getAsJsonObject().get("comments"), new TypeToken() {}.getType()));
      }
      catch (JsonSyntaxException localJsonSyntaxException) {}
      return null;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ArticleCommentsActivity
 * JD-Core Version:    0.7.0.1
 */