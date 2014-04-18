package com.androidcentral.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.data.ForumComment;
import com.androidcentral.app.data.ForumDataSource;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.SessionManager;
import com.androidcentral.app.net.XmlRpcUtils;
import com.androidcentral.app.net.XmlRpcUtils.ResultResponse;
import com.androidcentral.app.util.UiUtils;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

public class ForumThreadActivity
  extends DrawerActivity
  implements AdapterView.OnItemClickListener
{
  private static final int COMMENTS_PER_PAGE = 25;
  private static final int EDIT_POST_REQUEST = 1;
  public static final String EXTRA_FORUM_ID = "forum_id";
  public static final String EXTRA_POST_ID = "post_id";
  public static final String EXTRA_THREAD_ID = "thread_id";
  public static final String EXTRA_THREAD_TITLE = "thread_title";
  private static final int POST_REPLY_REQUEST = 0;
  private static final String TAG = "ForumThreadActivity";
  private ForumThreadTask commentDownloader;
  private CommentListAdapter commentListAdapter;
  private List<ForumComment> comments;
  private ListView commentsList;
  private int currentPage;
  private View emptyView;
  private Button firstButton;
  private ForumDataSource forumDataSource;
  private String forumId;
  private Button gotoButton;
  private boolean isSubscribed;
  private Button lastButton;
  private long lastPostId;
  private Button nextButton;
  private int numPages;
  private View paginationSection;
  private int position;
  private Button prevButton;
  private String threadId;
  private String threadTitle;
  private int totalCommentCount;
  
  private void addListHeader()
  {
    TextView localTextView = (TextView)LayoutInflater.from(this).inflate(2130903084, null);
    localTextView.setText(this.threadTitle.toUpperCase(Locale.getDefault()));
    this.commentsList.addHeaderView(localTextView, null, false);
  }
  
  private String[] buildDialogSelections()
  {
    String[] arrayOfString = new String[this.numPages];
    for (int i = 1;; i++)
    {
      if (i > this.numPages) {
        return arrayOfString;
      }
      arrayOfString[(i - 1)] = ("Page " + i);
    }
  }
  
  private void displayPageDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(2131165212).setItems(buildDialogSelections(), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ForumThreadActivity.this.jumpToPage(paramAnonymousInt);
      }
    });
    localBuilder.create().show();
  }
  
  private long getLastPostId()
  {
    long l = -1L;
    if (getIntent().getStringExtra("post_id") != null) {
      l = Long.parseLong(getIntent().getStringExtra("post_id"));
    }
    String str;
    do
    {
      return l;
      str = this.settings.getString("pref_open_behavior", getResources().getString(2131165267));
    } while ((!this.sessionManager.isLoggedIn()) || (!str.equals("unread")));
    return this.forumDataSource.getLastPost(this.threadId);
  }
  
  private void jumpToPage(int paramInt)
  {
    if ((paramInt == this.currentPage) || (paramInt < 0) || (paramInt >= this.numPages)) {}
    while ((this.commentDownloader != null) && (this.commentDownloader.getStatus() == AsyncTask.Status.RUNNING)) {
      return;
    }
    this.currentPage = paramInt;
    this.commentDownloader = new ForumThreadTask(false);
    this.commentDownloader.execute(new Void[0]);
  }
  
  private void loadCommentsFirstRun()
  {
    if (this.lastPostId != -1L)
    {
      new ThreadByUnreadTask().execute(new Void[0]);
      return;
    }
    refresh(false);
  }
  
  private List<ForumComment> parseResponse(String paramString)
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
      int i;
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        localXmlPullParser.setInput(new StringReader(paramString));
        localXmlPullParser.nextTag();
        localObject2 = null;
        i = 0;
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
        Log.e("ForumThreadActivity", Log.getStackTraceString(localException));
        return null;
      }
      if ((localXmlPullParser.getEventType() == 2) && (localXmlPullParser.getName().equals("name")))
      {
        String str = XmlRpcUtils.readText(localXmlPullParser);
        if (str.equals("total_post_num"))
        {
          this.totalCommentCount = XmlRpcUtils.getIntValue(localXmlPullParser);
        }
        else if (str.equals("is_subscribed"))
        {
          this.isSubscribed = XmlRpcUtils.getBooleanValue(localXmlPullParser);
        }
        else if (str.equals("position"))
        {
          this.position = XmlRpcUtils.getIntValue(localXmlPullParser);
        }
        else if (str.equals("post_id"))
        {
          if (localObject2 != null) {
            ((List)localObject1).add(localObject2);
          }
          localObject2 = new ForumComment();
          ((ForumComment)localObject2).postId = XmlRpcUtils.getStringValue(localXmlPullParser);
          i = 0;
        }
        else if (str.equals("post_content"))
        {
          ((ForumComment)localObject2).setContent(XmlRpcUtils.getBase64Value(localXmlPullParser));
        }
        else if (str.equals("post_author_id"))
        {
          ((ForumComment)localObject2).authorId = XmlRpcUtils.getStringValue(localXmlPullParser);
        }
        else if (str.equals("post_author_name"))
        {
          ((ForumComment)localObject2).authorName = XmlRpcUtils.getBase64Value(localXmlPullParser);
        }
        else if (str.equals("timestamp"))
        {
          ((ForumComment)localObject2).timestamp = XmlRpcUtils.getStringValue(localXmlPullParser);
        }
        else if (str.equals("can_edit"))
        {
          ((ForumComment)localObject2).canEdit = XmlRpcUtils.getBooleanValue(localXmlPullParser);
        }
        else if (str.equals("icon_url"))
        {
          ((ForumComment)localObject2).avatarUrl = XmlRpcUtils.getStringValue(localXmlPullParser);
        }
        else if (str.equals("like_count"))
        {
          if (localObject2 != null) {
            ((ForumComment)localObject2).likeCount = XmlRpcUtils.getIntValue(localXmlPullParser);
          }
        }
        else if (str.equals("thanks_info"))
        {
          i = 1;
        }
        else if ((str.equals("userid")) && (i != 0))
        {
          ((ForumComment)localObject2).thankCount = (1 + ((ForumComment)localObject2).thankCount);
        }
        else if (str.equals("url"))
        {
          ((ForumComment)localObject2).attachments.add(XmlRpcUtils.getStringValue(localXmlPullParser));
        }
      }
    }
  }
  
  private void postNewReply(String paramString, int paramInt)
  {
    if (this.sessionManager.isLoggedIn())
    {
      Intent localIntent = new Intent(this, ForumReplyActivity.class);
      localIntent.putExtra("mode", paramInt);
      localIntent.putExtra("thread_id", this.threadId);
      localIntent.putExtra("forum_id", this.forumId);
      localIntent.putExtra("post_id", paramString);
      if (paramInt == 2) {}
      for (int i = 1;; i = 0)
      {
        startActivityForResult(localIntent, i);
        return;
      }
    }
    UiUtils.showLoginDialog(this);
  }
  
  private void refresh(boolean paramBoolean)
  {
    this.commentDownloader = new ForumThreadTask(paramBoolean);
    this.commentDownloader.execute(new Void[0]);
  }
  
  private void reportPost(String paramString)
  {
    if (this.sessionManager.isLoggedIn())
    {
      Intent localIntent = new Intent(this, ForumReportActivity.class);
      localIntent.putExtra("post_id", paramString);
      startActivity(localIntent);
      return;
    }
    UiUtils.showLoginDialog(this);
  }
  
  private void setupPullToRefresh()
  {
    this.pullToRefreshAttacher.addRefreshableView(this.commentsList, new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        ForumThreadActivity.this.refresh(false);
      }
    });
  }
  
  private void updateNavigation()
  {
    int i = 1;
    if (this.paginationSection == null)
    {
      this.paginationSection = findViewById(2131099671);
      this.firstButton = ((Button)findViewById(2131099672));
      this.prevButton = ((Button)findViewById(2131099673));
      this.gotoButton = ((Button)findViewById(2131099674));
      this.nextButton = ((Button)findViewById(2131099675));
      this.lastButton = ((Button)findViewById(2131099676));
    }
    this.numPages = ((int)Math.ceil(this.totalCommentCount / 25.0D));
    if (this.numPages > i)
    {
      Button localButton1 = this.firstButton;
      label150:
      label213:
      Button localButton4;
      if (this.currentPage > 0)
      {
        int k = i;
        localButton1.setEnabled(k);
        Button localButton2 = this.prevButton;
        if (this.currentPage <= 0) {
          break label259;
        }
        int n = i;
        localButton2.setEnabled(n);
        this.gotoButton.setText("Page " + (1 + this.currentPage) + "...");
        Button localButton3 = this.nextButton;
        if (1 + this.currentPage >= this.numPages) {
          break label265;
        }
        int i2 = i;
        localButton3.setEnabled(i2);
        localButton4 = this.lastButton;
        if (1 + this.currentPage >= this.numPages) {
          break label271;
        }
      }
      for (;;)
      {
        localButton4.setEnabled(i);
        this.paginationSection.setVisibility(0);
        return;
        int m = 0;
        break;
        label259:
        int i1 = 0;
        break label150;
        label265:
        int i3 = 0;
        break label213;
        label271:
        int j = 0;
      }
    }
    this.paginationSection.setVisibility(8);
  }
  
  private void updateSubscription()
  {
    if (this.sessionManager.isLoggedIn())
    {
      if (this.isSubscribed) {}
      for (boolean bool = false;; bool = true)
      {
        new ThreadSubscribeTask(bool).execute(new Void[0]);
        return;
      }
    }
    UiUtils.showLoginDialog(this);
  }
  
  public String getThreadUrl()
  {
    return "http://forums.androidcentral.com/showthread.php?t=" + this.threadId;
  }
  
  public void goToFirst(View paramView)
  {
    jumpToPage(0);
  }
  
  public void goToLast(View paramView)
  {
    jumpToPage(-1 + this.numPages);
  }
  
  public void goToNext(View paramView)
  {
    jumpToPage(1 + this.currentPage);
  }
  
  public void goToPage(View paramView)
  {
    displayPageDialog();
  }
  
  public void goToPrev(View paramView)
  {
    jumpToPage(-1 + this.currentPage);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == -1)
      {
        if (this.currentPage != -1 + this.numPages) {
          break label36;
        }
        this.pullToRefreshAttacher.setRefreshing(true);
        refresh(false);
      }
    }
    label36:
    while ((paramInt1 != 1) || (paramInt2 != -1))
    {
      return;
      goToLast(new View(this));
      return;
    }
    refresh(true);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903047);
    this.commentsList = ((ListView)findViewById(2131099677));
    this.emptyView = findViewById(16908292);
    this.threadTitle = getIntent().getStringExtra("thread_title");
    this.threadId = getIntent().getStringExtra("thread_id");
    this.forumId = getIntent().getStringExtra("forum_id");
    this.forumDataSource = ForumDataSource.getInstance(this);
    this.lastPostId = getLastPostId();
    if (paramBundle == null)
    {
      this.numPages = 0;
      this.totalCommentCount = 0;
      this.currentPage = 0;
    }
    for (this.isSubscribed = false;; this.isSubscribed = paramBundle.getBoolean("is_subscribed"))
    {
      addListHeader();
      updateNavigation();
      this.commentListAdapter = new CommentListAdapter(this);
      this.commentsList.setAdapter(this.commentListAdapter);
      this.commentsList.setOnItemClickListener(this);
      setupPullToRefresh();
      this.comments = ((List)getLastCustomNonConfigurationInstance());
      if (this.comments != null) {
        break;
      }
      loadCommentsFirstRun();
      return;
      this.numPages = paramBundle.getInt("num_pages");
      this.totalCommentCount = paramBundle.getInt("total_comment_count");
      this.currentPage = paramBundle.getInt("current_page");
    }
    this.commentListAdapter.addAll(this.comments);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623940, paramMenu);
    return true;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    final ForumComment localForumComment = (ForumComment)this.commentsList.getItemAtPosition(paramInt);
    final ForumContextAdapter localForumContextAdapter = new ForumContextAdapter(this, localForumComment);
    localBuilder.setTitle(2131165219);
    localBuilder.setAdapter(localForumContextAdapter, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        switch (((ForumContextAdapter.DialogOption)localForumContextAdapter.getItem(paramAnonymousInt)).textResId)
        {
        default: 
          return;
        case 2131165236: 
          ForumThreadActivity.this.postNewReply(null, 0);
          return;
        case 2131165237: 
          ForumThreadActivity.this.postNewReply(localForumComment.postId, 1);
          return;
        case 2131165238: 
          ForumThreadActivity.this.postNewReply(localForumComment.postId, 2);
          return;
        case 2131165239: 
          if (ForumThreadActivity.this.sessionManager.isLoggedIn())
          {
            new ForumThreadActivity.SimpleForumPostTask(ForumThreadActivity.this, "thank_post", localForumComment).execute(new Void[0]);
            return;
          }
          UiUtils.showLoginDialog(ForumThreadActivity.this);
          return;
        case 2131165240: 
          if (ForumThreadActivity.this.sessionManager.isLoggedIn())
          {
            new ForumThreadActivity.SimpleForumPostTask(ForumThreadActivity.this, "like_post", localForumComment).execute(new Void[0]);
            return;
          }
          UiUtils.showLoginDialog(ForumThreadActivity.this);
          return;
        case 2131165241: 
          if (ForumThreadActivity.this.sessionManager.isLoggedIn())
          {
            Intent localIntent = new Intent(ForumThreadActivity.this, ViewProfileActivity.class);
            localIntent.putExtra("post_author_id", localForumComment.authorId);
            ForumThreadActivity.this.startActivity(localIntent);
            return;
          }
          UiUtils.showLoginDialog(ForumThreadActivity.this);
          return;
        }
        ForumThreadActivity.this.reportPost(localForumComment.postId);
      }
    });
    localBuilder.create().show();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099771: 
      postNewReply(null, 0);
      return true;
    case 2131099773: 
      this.pullToRefreshAttacher.setRefreshing(true);
      refresh(false);
      return true;
    case 2131099772: 
      updateSubscription();
      return true;
    case 2131099774: 
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getThreadUrl())));
      return true;
    }
    ((ClipboardManager)getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Forums Thread", getThreadUrl()));
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    this.forumDataSource.setLastPost(Long.parseLong(this.threadId), Long.parseLong(this.commentListAdapter.getLastPostId()));
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    MenuItem localMenuItem = paramMenu.findItem(2131099772);
    if (this.isSubscribed)
    {
      localMenuItem.setIcon(2130837515);
      localMenuItem.setTitle(2131165204);
    }
    for (;;)
    {
      return true;
      localMenuItem.setIcon(2130837514);
      localMenuItem.setTitle(2131165203);
    }
  }
  
  public Object onRetainCustomNonConfigurationInstance()
  {
    return this.comments;
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("current_page", this.currentPage);
    paramBundle.putInt("num_pages", this.numPages);
    paramBundle.putInt("total_comment_count", this.totalCommentCount);
    paramBundle.putBoolean("is_subscribed", this.isSubscribed);
  }
  
  protected void onStop()
  {
    super.onStop();
    if (this.commentDownloader != null) {
      this.commentDownloader.cancel(true);
    }
  }
  
  class ForumThreadTask
    extends AsyncTask<Void, Void, List<ForumComment>>
  {
    private boolean restorePosition;
    private Parcelable state;
    
    public ForumThreadTask(boolean paramBoolean)
    {
      this.restorePosition = paramBoolean;
    }
    
    protected List<ForumComment> doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList();
      int i = 25 * ForumThreadActivity.this.currentPage;
      int j = -1 + (i + 25);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumThreadActivity.this.threadId)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(i)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(j)));
      localArrayList.add(new MobiquoHelper.Param("boolean", "1"));
      String str = NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("get_thread", localArrayList), "text/xml");
      return ForumThreadActivity.this.parseResponse(str);
    }
    
    protected void onPostExecute(List<ForumComment> paramList)
    {
      if (paramList != null)
      {
        ForumThreadActivity.this.comments = paramList;
        ForumThreadActivity.this.commentListAdapter.clear();
        ForumThreadActivity.this.commentListAdapter.addAll(paramList);
        ForumThreadActivity.this.updateNavigation();
      }
      if (this.restorePosition) {
        ForumThreadActivity.this.commentsList.onRestoreInstanceState(this.state);
      }
      ForumThreadActivity.this.emptyView.setVisibility(8);
      ForumThreadActivity.this.pullToRefreshAttacher.setRefreshComplete();
    }
    
    protected void onPreExecute()
    {
      if (this.restorePosition) {
        this.state = ForumThreadActivity.this.commentsList.onSaveInstanceState();
      }
      ForumThreadActivity.this.commentListAdapter.clear();
      ForumThreadActivity.this.emptyView.setVisibility(0);
    }
  }
  
  class SimpleForumPostTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private ForumComment comment;
    private String method;
    
    public SimpleForumPostTask(String paramString, ForumComment paramForumComment)
    {
      this.method = paramString;
      this.comment = paramForumComment;
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(new MobiquoHelper.Param("string", this.comment.postId));
      return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod(this.method, localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success)
      {
        if (!this.method.equals("like_post")) {
          break label45;
        }
        ForumComment localForumComment2 = this.comment;
        localForumComment2.likeCount = (1 + localForumComment2.likeCount);
      }
      for (;;)
      {
        ForumThreadActivity.this.commentListAdapter.notifyDataSetChanged();
        return;
        label45:
        ForumComment localForumComment1 = this.comment;
        localForumComment1.thankCount = (1 + localForumComment1.thankCount);
      }
    }
  }
  
  class ThreadByUnreadTask
    extends AsyncTask<Void, Void, List<ForumComment>>
  {
    ThreadByUnreadTask() {}
    
    protected List<ForumComment> doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(3);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumThreadActivity.this.lastPostId)));
      localArrayList.add(new MobiquoHelper.Param("int", String.valueOf(25)));
      localArrayList.add(new MobiquoHelper.Param("boolean", "1"));
      String str = NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("get_thread_by_post", localArrayList), "text/xml");
      return ForumThreadActivity.this.parseResponse(str);
    }
    
    protected void onPostExecute(List<ForumComment> paramList)
    {
      if (paramList != null)
      {
        ForumThreadActivity.this.comments = paramList;
        ForumThreadActivity.this.commentListAdapter.addAll(paramList);
        if (ForumThreadActivity.this.position > 0)
        {
          ForumThreadActivity.this.currentPage = (-1 + (int)Math.ceil(ForumThreadActivity.this.position / 25.0D));
          ForumThreadActivity.this.commentsList.clearFocus();
          ForumThreadActivity.this.commentsList.post(new Runnable()
          {
            public void run()
            {
              ForumThreadActivity.this.commentsList.requestFocusFromTouch();
              ForumThreadActivity.this.commentsList.setSelection((-1 + ForumThreadActivity.this.position) % 25);
              ForumThreadActivity.this.commentsList.requestFocus();
            }
          });
        }
        ForumThreadActivity.this.updateNavigation();
      }
      ForumThreadActivity.this.emptyView.setVisibility(8);
    }
    
    protected void onPreExecute()
    {
      ForumThreadActivity.this.commentListAdapter.clear();
      ForumThreadActivity.this.emptyView.setVisibility(0);
    }
  }
  
  public class ThreadSubscribeTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private boolean subscribe;
    
    public ThreadSubscribeTask(boolean paramBoolean)
    {
      this.subscribe = paramBoolean;
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumThreadActivity.this.threadId)));
      if (this.subscribe) {}
      for (String str = "subscribe_topic";; str = "unsubscribe_topic") {
        return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod(str, localArrayList), "text/xml"));
      }
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success)
      {
        if (this.subscribe) {}
        for (str = "Subscribed to \"" + ForumThreadActivity.this.threadTitle + "\"";; str = "Unsubscribed from \"" + ForumThreadActivity.this.threadTitle + "\"")
        {
          ForumThreadActivity.this.isSubscribed = this.subscribe;
          ForumThreadActivity.this.invalidateOptionsMenu();
          Toast.makeText(ForumThreadActivity.this, str, 0).show();
          return;
        }
      }
      if (this.subscribe) {}
      for (String str = "Could not subscribe to \"" + ForumThreadActivity.this.threadTitle + "\"";; str = "Could not unsubscribe from \"" + ForumThreadActivity.this.threadTitle + "\": " + paramResultResponse.error) {
        break;
      }
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumThreadActivity
 * JD-Core Version:    0.7.0.1
 */