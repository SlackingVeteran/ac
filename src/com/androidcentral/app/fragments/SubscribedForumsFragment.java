package com.androidcentral.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.BaseActivity;
import com.androidcentral.app.ForumSearchActivity;
import com.androidcentral.app.ForumTopicActivity;
import com.androidcentral.app.data.ForumEntry;
import java.util.List;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;

public class SubscribedForumsFragment
  extends ListFragment
  implements LoaderManager.LoaderCallbacks<List<ForumEntry>>
{
  private SubscribedForumsAdapter adapter;
  private PullToRefreshAttacher attacher;
  private SubscribedForumsLoader loader;
  
  private void initEmptyView()
  {
    View localView = LayoutInflater.from(getActivity()).inflate(2130903056, null);
    ((TextView)localView.findViewById(2131099696)).setText("No forums to display");
    ((ViewGroup)getListView().getParent()).addView(localView);
    getListView().setEmptyView(localView);
  }
  
  private void setupPullToRefresh()
  {
    this.attacher = ((BaseActivity)getActivity()).pullToRefreshAttacher;
    this.attacher.addRefreshableView(getListView(), new PullToRefreshAttacher.OnRefreshListener()
    {
      public void onRefreshStarted(View paramAnonymousView)
      {
        SubscribedForumsFragment.this.loader.onContentChanged();
      }
    });
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    initEmptyView();
    this.adapter = new SubscribedForumsAdapter(getActivity());
    setListAdapter(this.adapter);
    setListShown(false);
    setupPullToRefresh();
    this.loader = ((SubscribedForumsLoader)getLoaderManager().initLoader(0, null, this));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }
  
  public Loader<List<ForumEntry>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new SubscribedForumsLoader(getActivity());
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623948, paramMenu);
    paramMenuInflater.inflate(2131623947, paramMenu);
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    ForumEntry localForumEntry = (ForumEntry)paramListView.getItemAtPosition(paramInt);
    Intent localIntent = new Intent(getActivity(), ForumTopicActivity.class);
    localIntent.putExtra("forum_id", localForumEntry.id);
    localIntent.putExtra("name", localForumEntry.name);
    startActivity(localIntent);
  }
  
  public void onLoadFinished(Loader<List<ForumEntry>> paramLoader, List<ForumEntry> paramList)
  {
    if (paramList != null)
    {
      this.adapter.clear();
      this.adapter.addAll(paramList);
    }
    for (;;)
    {
      setListShown(true);
      this.attacher.setRefreshComplete();
      return;
      Toast.makeText(getActivity(), 2131165211, 0).show();
    }
  }
  
  public void onLoaderReset(Loader<List<ForumEntry>> paramLoader)
  {
    this.adapter.clear();
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
    this.loader.onContentChanged();
    return true;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SubscribedForumsFragment
 * JD-Core Version:    0.7.0.1
 */