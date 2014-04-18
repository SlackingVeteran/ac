package com.androidcentral.app.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.TextView;
import com.androidcentral.app.ArticlePagerActivity;
import com.androidcentral.app.BaseActivity;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.net.NewsDataService;
import com.commonsware.cwac.loaderex.acl.SQLiteCursorLoader;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

public class NewsSectionFragment
  extends ListFragment
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  public static final String ARG_SECTION_NAME = "section_name";
  public static final String ARG_SECTION_TITLE = "section_title";
  private static final int ARTICLE_LOADER_ID = 0;
  private static final int FEATURED_LOADER_ID = 1;
  private static final int SHOW_ARTICLE_REQUEST = 0;
  public static final String TAG = "NewsSectionFragment";
  private NewsListAdapter adapter;
  private PullToRefreshAttacher attacher;
  BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Log.d("NewsSectionFragment", "(BroadcastReceiver) DB updated - refreshing display");
      NewsSectionFragment.this.loader.onContentChanged();
      NewsSectionFragment.this.attacher.setRefreshComplete();
      if (NewsSectionFragment.this.featuredLoader != null) {
        NewsSectionFragment.this.featuredLoader.onContentChanged();
      }
    }
  };
  private FeaturedPagerAdapter featuredAdapter;
  private CirclePageIndicator featuredIndicator;
  private SQLiteCursorLoader featuredLoader;
  private ViewPager featuredPager;
  private SQLiteCursorLoader loader;
  private NewsDataSource newsDataSource;
  private String sectionName;
  private String sectionTitle;
  
  private void addFeaturedHeader()
  {
    ListView localListView = getListView();
    ViewGroup localViewGroup = (ViewGroup)LayoutInflater.from(getActivity()).inflate(2130903060, localListView, false);
    this.featuredPager = ((ViewPager)localViewGroup.findViewById(2131099701));
    this.featuredAdapter = new FeaturedPagerAdapter(getChildFragmentManager());
    this.featuredPager.setAdapter(this.featuredAdapter);
    this.featuredPager.setOnTouchListener(new View.OnTouchListener()
    {
      private static final int SWIPE_SENS = 100;
      private static final int TOUCH_SENS = 5;
      private float x = 0.0F;
      private float y = 0.0F;
      
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        paramAnonymousView.getParent().requestDisallowInterceptTouchEvent(true);
        switch (paramAnonymousMotionEvent.getAction())
        {
        }
        for (;;)
        {
          return false;
          this.x = paramAnonymousMotionEvent.getX();
          this.y = paramAnonymousMotionEvent.getY();
          continue;
          if (Math.abs(this.x - paramAnonymousMotionEvent.getX()) < 5.0F)
          {
            FeaturedPagerAdapter.FeaturedItem localFeaturedItem = NewsSectionFragment.this.featuredAdapter.getDataAtPosition(NewsSectionFragment.this.featuredPager.getCurrentItem());
            NewsSectionFragment.this.launchArticleView(localFeaturedItem.nid);
            return true;
            if (paramAnonymousMotionEvent.getY() - this.y > 100.0F) {
              paramAnonymousView.getParent().requestDisallowInterceptTouchEvent(false);
            }
          }
        }
      }
    });
    this.featuredIndicator = ((CirclePageIndicator)localViewGroup.findViewById(2131099702));
    this.featuredIndicator.setViewPager(this.featuredPager);
    localListView.addHeaderView(localViewGroup);
  }
  
  private boolean isRefreshNeeded()
  {
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("AndroidCentralPrefs", 0);
    long l1 = localSharedPreferences.getLong("last_refresh_" + this.sectionName, 0L);
    long l2 = Long.parseLong(localSharedPreferences.getString("pref_sync_interval", "900000"));
    boolean bool1 = l2 < 0L;
    boolean bool2 = false;
    if (bool1)
    {
      boolean bool3 = System.currentTimeMillis() - l1 < l2;
      bool2 = false;
      if (bool3) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private void launchArticleView(long paramLong)
  {
    Intent localIntent = new Intent(getActivity(), ArticlePagerActivity.class);
    localIntent.putExtra("nid", paramLong);
    localIntent.putExtra("sectionTitle", this.sectionTitle);
    localIntent.putExtra("sectionName", this.sectionName);
    startActivityForResult(localIntent, 0);
  }
  
  public static NewsSectionFragment newInstance(String paramString1, String paramString2)
  {
    NewsSectionFragment localNewsSectionFragment = new NewsSectionFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("section_name", paramString1);
    localBundle.putString("section_title", paramString2);
    localNewsSectionFragment.setArguments(localBundle);
    return localNewsSectionFragment;
  }
  
  private void refresh()
  {
    this.attacher.setRefreshing(true);
    Intent localIntent = new Intent(getActivity(), NewsDataService.class);
    localIntent.putExtra("section", this.sectionName);
    getActivity().startService(localIntent);
  }
  
  private void setupPullToRefresh()
  {
    this.attacher = ((BaseActivity)getActivity()).pullToRefreshAttacher;
    ((PullToRefreshLayout)getView().findViewById(2131099717)).setPullToRefreshAttacher(this.attacher, new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        NewsSectionFragment.this.refresh();
      }
    });
  }
  
  private boolean showFeatured()
  {
    return this.sectionName.equals("all");
  }
  
  public String getSectionName()
  {
    return this.sectionName;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.newsDataSource = NewsDataSource.getInstance(getActivity());
    if (showFeatured()) {
      addFeaturedHeader();
    }
    this.adapter = new NewsListAdapter(getActivity());
    setListAdapter(this.adapter);
    setupPullToRefresh();
    this.loader = ((SQLiteCursorLoader)getLoaderManager().initLoader(0, null, this));
    if (showFeatured()) {
      this.featuredLoader = ((SQLiteCursorLoader)getLoaderManager().initLoader(1, null, this));
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    long[] arrayOfLong;
    List localList;
    if ((paramInt2 == -1) && (paramInt1 == 0))
    {
      arrayOfLong = paramIntent.getLongArrayExtra("visited_articles");
      Arrays.sort(arrayOfLong);
      localList = this.adapter.getArticles();
      if (localList != null) {}
    }
    else
    {
      return;
    }
    Iterator localIterator = localList.iterator();
    label47:
    ListView localListView;
    int i;
    int j;
    label81:
    Article localArticle2;
    if (!localIterator.hasNext())
    {
      localListView = getListView();
      i = localListView.getFirstVisiblePosition();
      j = i;
      int k = localListView.getLastVisiblePosition();
      if (j <= k)
      {
        localArticle2 = (Article)localListView.getItemAtPosition(j);
        if (localArticle2 != null) {
          break label145;
        }
      }
    }
    for (;;)
    {
      j++;
      break label81;
      break;
      Article localArticle1 = (Article)localIterator.next();
      if (Arrays.binarySearch(arrayOfLong, localArticle1.nid) < 0) {
        break label47;
      }
      localArticle1.readStatus = true;
      break label47;
      label145:
      if (Arrays.binarySearch(arrayOfLong, localArticle2.nid) >= 0) {
        ((TextView)localListView.getChildAt(j - i).findViewById(2131099732)).setTypeface(null, 0);
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
    this.sectionName = getArguments().getString("section_name");
    this.sectionTitle = getArguments().getString("section_title");
  }
  
  public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 0) {
      return new SQLiteCursorLoader(getActivity(), this.newsDataSource.getDatabaseHelper(), this.newsDataSource.getArticleQuery(this.sectionName), null);
    }
    return new SQLiteCursorLoader(getActivity(), this.newsDataSource.getDatabaseHelper(), this.newsDataSource.getFeaturedQuery(), null);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623946, paramMenu);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903069, paramViewGroup, false);
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    launchArticleView(((Article)paramListView.getItemAtPosition(paramInt)).nid);
  }
  
  public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    if (paramLoader.getId() == 0)
    {
      this.adapter.changeCursor(paramCursor);
      return;
    }
    this.featuredAdapter.changeCursor(paramCursor);
    this.featuredIndicator.notifyDataSetChanged();
  }
  
  public void onLoaderReset(Loader<Cursor> paramLoader)
  {
    if (paramLoader.getId() == 0)
    {
      this.adapter.changeCursor(null);
      return;
    }
    this.featuredAdapter.changeCursor(null);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099784: 
      this.newsDataSource.markAllAsRead(this.sectionName);
      this.loader.onContentChanged();
      return true;
    }
    refresh();
    return true;
  }
  
  public void onStart()
  {
    super.onStart();
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.broadcastReceiver, new IntentFilter("db_updated_" + this.sectionName));
    if (isRefreshNeeded()) {
      refresh();
    }
  }
  
  public void onStop()
  {
    super.onStop();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.broadcastReceiver);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.NewsSectionFragment
 * JD-Core Version:    0.7.0.1
 */