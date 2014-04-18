package com.androidcentral.app.fragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.ArticleCommentsActivity;
import com.androidcentral.app.GalleryActivity;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.Article.CommentThread;
import com.androidcentral.app.media.MediaPlayerService;
import com.androidcentral.app.media.MediaPlayerService.MediaPlayerBinder;
import com.androidcentral.app.media.MediaPlayerServiceClient;
import com.androidcentral.app.media.MediaUtils;
import com.androidcentral.app.media.ServiceTools;
import com.androidcentral.app.media.StatefulMediaPlayer;
import com.androidcentral.app.util.SimpleTemplate;
import com.androidcentral.app.util.UiUtils;
import com.androidcentral.app.util.YouTubeHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.ocpsoft.prettytime.PrettyTime;

public class ArticleViewFragment
  extends Fragment
  implements LoaderManager.LoaderCallbacks<Article>, MediaPlayerServiceClient, SeekBar.OnSeekBarChangeListener
{
  public static final String ARG_NID = "nid";
  public static final String ARG_PERMALINK = "permalink";
  public static final String TAG = "ArticleViewFragment";
  private Article article;
  private boolean bound;
  private Button downloadButton;
  private MediaPlayerService mediaService;
  private long nid;
  private String permaLink;
  private Button playButton;
  private TextView playbackDurationText;
  private TextView playbackPositionText;
  private Handler progressHandler = new Handler();
  private SeekBar seekBar;
  private ServiceConnection serviceConn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      MediaPlayerService.MediaPlayerBinder localMediaPlayerBinder = (MediaPlayerService.MediaPlayerBinder)paramAnonymousIBinder;
      ArticleViewFragment.this.mediaService = localMediaPlayerBinder.getService();
      ArticleViewFragment.this.bound = true;
      ArticleViewFragment.this.showMediaControls();
      ArticleViewFragment.this.updateProgressBar();
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      ArticleViewFragment.this.bound = false;
    }
  };
  private SharedPreferences settings;
  private Runnable updateTimeTask = new Runnable()
  {
    public void run()
    {
      StatefulMediaPlayer localStatefulMediaPlayer = ArticleViewFragment.this.mediaService.getMediaPlayer();
      if ((localStatefulMediaPlayer.isEmpty()) || (localStatefulMediaPlayer.isCreated()) || (localStatefulMediaPlayer.isPrepared()) || (!localStatefulMediaPlayer.getAudioUrl().equals(ArticleViewFragment.this.article.audio))) {
        ArticleViewFragment.this.seekBar.setEnabled(false);
      }
      for (;;)
      {
        ArticleViewFragment.this.progressHandler.postDelayed(this, 100L);
        return;
        long l1 = localStatefulMediaPlayer.getDuration();
        long l2 = localStatefulMediaPlayer.getCurrentPosition();
        ArticleViewFragment.this.playbackPositionText.setText(MediaUtils.milliSecondsToTimer(l2));
        ArticleViewFragment.this.playbackDurationText.setText(MediaUtils.milliSecondsToTimer(l1));
        float f = 100.0F * ((float)l2 / (float)l1);
        ArticleViewFragment.this.seekBar.setEnabled(true);
        ArticleViewFragment.this.seekBar.setProgress((int)f);
      }
    }
  };
  private WebSettings webSettings;
  private WebView webView;
  
  private String buildDocument(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    SimpleTemplate localSimpleTemplate = new SimpleTemplate(getActivity(), "article_template");
    HashMap localHashMap = new HashMap(5);
    localHashMap.put("TITLE", paramString2);
    localHashMap.put("AUTHOR", paramString3);
    localHashMap.put("PUBLISHED", paramString4);
    localHashMap.put("CONTENT", paramString1);
    localHashMap.put("CSS_FILENAME", getCssFilename());
    return localSimpleTemplate.applyMapping(localHashMap);
  }
  
  private void downloadClicked()
  {
    DownloadManager localDownloadManager = (DownloadManager)getActivity().getSystemService("download");
    Uri localUri = Uri.parse(this.article.audio);
    DownloadManager.Request localRequest = new DownloadManager.Request(localUri);
    localRequest.allowScanningByMediaScanner();
    localRequest.setNotificationVisibility(1);
    localRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_PODCASTS, localUri.getLastPathSegment());
    localDownloadManager.enqueue(localRequest);
    Toast.makeText(getActivity(), 2131165225, 0).show();
    this.downloadButton.setEnabled(false);
  }
  
  private String getCssFilename()
  {
    StringBuilder localStringBuilder = new StringBuilder("file:///android_asset/");
    if (UiUtils.isNightTheme(getActivity())) {}
    for (String str = "articles_dark.css";; str = "articles_light.css") {
      return str;
    }
  }
  
  private Intent getDefaultShareIntent()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    localIntent.putExtra("android.intent.extra.SUBJECT", this.article.title);
    localIntent.putExtra("android.intent.extra.TEXT", this.article.title + " " + getArticleUrl());
    return localIntent;
  }
  
  private int getTextZoom()
  {
    int i = 110;
    String str = this.settings.getString("pref_font_size", "");
    if (str.equals("Tiny")) {
      i = 80;
    }
    do
    {
      do
      {
        return i;
        if (str.equals("Small")) {
          return 100;
        }
      } while (str.equals("Medium"));
      if (str.equals("Large")) {
        return 130;
      }
    } while (!str.equals("Huge"));
    return 150;
  }
  
  private void launchCommentsActivity(long paramLong, String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean)
  {
    Intent localIntent = new Intent(getActivity(), ArticleCommentsActivity.class);
    localIntent.putExtra("nid", paramLong);
    localIntent.putExtra("title", paramString1);
    localIntent.putExtra("commentCount", paramInt);
    localIntent.putExtra("heroImage", paramString2);
    localIntent.putExtra("server", paramString3);
    localIntent.putExtra("isTalkMobile", paramBoolean);
    startActivity(localIntent);
  }
  
  private void launchGallery()
  {
    if (this.article == null) {
      return;
    }
    ArrayList localArrayList = this.article.getMediaGalleryImages();
    Intent localIntent = new Intent(getActivity(), GalleryActivity.class);
    localIntent.putParcelableArrayListExtra("images", localArrayList);
    startActivity(localIntent);
  }
  
  public static ArticleViewFragment newInstance(long paramLong)
  {
    ArticleViewFragment localArticleViewFragment = new ArticleViewFragment();
    Bundle localBundle = new Bundle();
    localBundle.putLong("nid", paramLong);
    localArticleViewFragment.setArguments(localBundle);
    return localArticleViewFragment;
  }
  
  public static ArticleViewFragment newInstance(String paramString)
  {
    ArticleViewFragment localArticleViewFragment = new ArticleViewFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("permalink", paramString);
    localArticleViewFragment.setArguments(localBundle);
    return localArticleViewFragment;
  }
  
  private void playClicked()
  {
    StatefulMediaPlayer localStatefulMediaPlayer = this.mediaService.getMediaPlayer();
    String str = "http://www.androidcentral.com/" + this.article.permaLink;
    this.mediaService.setClient(this, str);
    if (localStatefulMediaPlayer.isStarted())
    {
      if (localStatefulMediaPlayer.getAudioUrl().equals(this.article.audio))
      {
        this.mediaService.pauseMediaPlayer();
        this.playButton.setText(2131165221);
        return;
      }
      this.mediaService.stopMediaPlayer();
      this.mediaService.initializePlayer(this.article.audio, this.article.title);
      this.playButton.setText(2131165220);
      return;
    }
    if ((localStatefulMediaPlayer.isPaused()) || (localStatefulMediaPlayer.isPrepared())) {
      if (localStatefulMediaPlayer.getAudioUrl().equals(this.article.audio)) {
        this.mediaService.startMediaPlayer();
      }
    }
    for (;;)
    {
      Toast.makeText(getActivity(), 2131165222, 0).show();
      this.playButton.setText(2131165220);
      return;
      this.mediaService.stopMediaPlayer();
      this.mediaService.initializePlayer(this.article.audio, this.article.title);
      continue;
      this.mediaService.initializePlayer(this.article.audio, this.article.title);
    }
  }
  
  private void showComments()
  {
    if (this.article == null) {
      return;
    }
    if (this.article.isTalkMobile())
    {
      TMThreadDialogFragment localTMThreadDialogFragment = new TMThreadDialogFragment();
      Bundle localBundle = new Bundle();
      localBundle.putParcelableArray("threads", this.article.commentThreads);
      localTMThreadDialogFragment.setArguments(localBundle);
      localTMThreadDialogFragment.show(getChildFragmentManager(), "TMThreadDialogFragment");
      return;
    }
    launchCommentsActivity(this.article.nid, this.article.title, this.article.commentCount, this.article.heroImage, "m.androidcentral.com", false);
  }
  
  private void showMediaControls()
  {
    ((ViewStub)getView().findViewById(2131099712)).inflate();
    this.seekBar = ((SeekBar)getView().findViewById(2131099738));
    this.seekBar.setOnSeekBarChangeListener(this);
    this.seekBar.setProgress(0);
    this.seekBar.setMax(100);
    this.playbackPositionText = ((TextView)getView().findViewById(2131099739));
    this.playbackDurationText = ((TextView)getView().findViewById(2131099740));
    this.playButton = ((Button)getView().findViewById(2131099736));
    this.playButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ArticleViewFragment.this.playClicked();
      }
    });
    this.downloadButton = ((Button)getView().findViewById(2131099737));
    this.downloadButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ArticleViewFragment.this.downloadClicked();
      }
    });
    StatefulMediaPlayer localStatefulMediaPlayer = this.mediaService.getMediaPlayer();
    if ((localStatefulMediaPlayer.isStarted()) && (localStatefulMediaPlayer.getAudioUrl().equals(this.article.audio))) {
      this.playButton.setText(2131165220);
    }
  }
  
  private void updateProgressBar()
  {
    this.progressHandler.postDelayed(this.updateTimeTask, 100L);
  }
  
  public void bindToMediaService()
  {
    Intent localIntent = new Intent(getActivity(), MediaPlayerService.class);
    if (ServiceTools.isServiceRunning(getActivity(), MediaPlayerService.class.getName()))
    {
      getActivity().bindService(localIntent, this.serviceConn, 1);
      return;
    }
    getActivity().startService(localIntent);
    getActivity().bindService(localIntent, this.serviceConn, 1);
  }
  
  public String getArticleUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder("http://www.androidcentral.com/");
    if (this.article != null) {}
    for (String str = this.article.permaLink;; str = "") {
      return str;
    }
  }
  
  public void launchCommentsActivity(Article.CommentThread paramCommentThread)
  {
    Intent localIntent = new Intent(getActivity(), ArticleCommentsActivity.class);
    localIntent.putExtra("nid", paramCommentThread.nid);
    localIntent.putExtra("title", paramCommentThread.title);
    localIntent.putExtra("commentCount", paramCommentThread.commentCount);
    localIntent.putExtra("heroImage", this.article.getHeroImage());
    localIntent.putExtra("server", this.article.commentServer);
    localIntent.putExtra("isTalkMobile", true);
    startActivity(localIntent);
  }
  
  public void launchInBrowser(String paramString)
  {
    Uri localUri = Uri.parse(paramString);
    Intent localIntent1 = new Intent("android.intent.action.VIEW", localUri);
    Iterator localIterator = getActivity().getPackageManager().queryIntentActivities(localIntent1, 0).iterator();
    String str1;
    String str2;
    do
    {
      if (!localIterator.hasNext()) {
        return;
      }
      ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
      str1 = localResolveInfo.activityInfo.name;
      str2 = localResolveInfo.activityInfo.packageName;
    } while (str1.contains("ArticlePagerActivity"));
    Intent localIntent2 = new Intent();
    localIntent2.setComponent(new ComponentName(str2, str1));
    localIntent2.setData(localUri);
    startActivity(localIntent2);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.settings = getActivity().getSharedPreferences("AndroidCentralPrefs", 0);
    this.webView = ((WebView)getView().findViewById(2131099714));
    this.webView.setWebViewClient(new ArticleWebViewClient(null));
    if (UiUtils.isNightTheme(getActivity())) {
      this.webView.setBackgroundColor(getResources().getColor(2131296293));
    }
    this.webSettings = this.webView.getSettings();
    this.webSettings.setTextZoom(getTextZoom());
    this.webSettings.setJavaScriptEnabled(true);
    getLoaderManager().initLoader(0, null, this);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (getArguments().containsKey("nid"))
    {
      this.nid = getArguments().getLong("nid");
      this.permaLink = null;
    }
    for (;;)
    {
      setHasOptionsMenu(true);
      return;
      this.permaLink = getArguments().getString("permalink");
      this.nid = -1L;
    }
  }
  
  public Loader<Article> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (this.nid != -1L) {
      return new ArticleLoaderExplicit(getActivity(), this.nid);
    }
    return new ArticleLoaderImplicit(getActivity(), this.permaLink);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenuInflater.inflate(2131623937, paramMenu);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903066, paramViewGroup, false);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.bound)
    {
      getActivity().unbindService(this.serviceConn);
      this.progressHandler.removeCallbacks(this.updateTimeTask);
      this.bound = false;
    }
  }
  
  public void onError()
  {
    Toast.makeText(getActivity(), 2131165211, 0).show();
    this.playButton.setEnabled(true);
    this.playButton.setText(2131165221);
  }
  
  public void onInitializePlayerStart(String paramString)
  {
    this.playButton.setText(paramString);
    this.playButton.setEnabled(false);
  }
  
  public void onInitializePlayerSuccess()
  {
    this.playButton.setText(2131165220);
    this.playButton.setEnabled(true);
  }
  
  public void onLoadFinished(Loader<Article> paramLoader, Article paramArticle)
  {
    if (paramArticle == null)
    {
      if (this.nid != -1L)
      {
        Toast.makeText(getActivity(), 2131165211, 0).show();
        return;
      }
      launchInBrowser("http://www.androidcentral.com/" + this.permaLink);
      getActivity().finish();
      return;
    }
    this.article = paramArticle;
    if (paramArticle.isTalkMobile())
    {
      this.webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
      this.webView.loadUrl("http://m.androidcentral.com/" + paramArticle.permaLink + "?embedded=1");
    }
    for (;;)
    {
      getActivity().invalidateOptionsMenu();
      if (!paramArticle.isPodcast()) {
        break;
      }
      bindToMediaService();
      return;
      String str = buildDocument(paramArticle.getDisplayableContent(), paramArticle.title, paramArticle.author, new PrettyTime().format(new Date(1000L * paramArticle.publishedDate)));
      this.webView.loadDataWithBaseURL("http://m.androidcentral.com/", str, "text/html", "utf-8", null);
    }
  }
  
  public void onLoaderReset(Loader<Article> paramLoader) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    case 2131099766: 
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099767: 
      launchInBrowser(getArticleUrl());
      return true;
    case 2131099768: 
      ((ClipboardManager)getActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Article URL", getArticleUrl()));
      return true;
    case 2131099765: 
      showComments();
      return true;
    }
    launchGallery();
    return true;
  }
  
  public void onPlaybackComplete()
  {
    this.playButton.setEnabled(false);
  }
  
  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (this.article != null)
    {
      ((ShareActionProvider)paramMenu.findItem(2131099766).getActionProvider()).setShareIntent(getDefaultShareIntent());
      if (!this.article.hasMediaGallery()) {
        paramMenu.removeItem(2131099764);
      }
      if (!this.article.allowComments()) {
        paramMenu.removeItem(2131099765);
      }
    }
  }
  
  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean) {}
  
  public void onStartTrackingTouch(SeekBar paramSeekBar)
  {
    this.progressHandler.removeCallbacks(this.updateTimeTask);
  }
  
  public void onStopTrackingTouch(SeekBar paramSeekBar)
  {
    this.progressHandler.removeCallbacks(this.updateTimeTask);
    StatefulMediaPlayer localStatefulMediaPlayer = this.mediaService.getMediaPlayer();
    int i = localStatefulMediaPlayer.getDuration();
    localStatefulMediaPlayer.seekTo(MediaUtils.progressToTimer(paramSeekBar.getProgress(), i));
    updateProgressBar();
  }
  
  private class ArticleWebViewClient
    extends WebViewClient
  {
    private ArticleWebViewClient() {}
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      String str = YouTubeHelper.getVideoId(paramString);
      if (str != null)
      {
        boolean bool = ArticleViewFragment.this.settings.getBoolean("pref_video_fullscreen", false);
        YouTubeHelper.launchPlayer(ArticleViewFragment.this.getActivity(), str, bool);
        return true;
      }
      if (paramString.equals("http://m.androidcentral.com/_fullscreen"))
      {
        ArticleViewFragment.this.launchGallery();
        return true;
      }
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      ArticleViewFragment.this.startActivity(localIntent);
      return true;
    }
  }
  
  public class WebAppInterface
  {
    Context context;
    
    WebAppInterface(Context paramContext)
    {
      this.context = paramContext;
    }
    
    @JavascriptInterface
    public void showComments(String paramString1, String paramString2)
    {
      Article.CommentThread localCommentThread = ArticleViewFragment.this.article.findCommentThread(paramString1);
      if (localCommentThread != null) {
        ArticleViewFragment.this.launchCommentsActivity(localCommentThread);
      }
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ArticleViewFragment
 * JD-Core Version:    0.7.0.1
 */