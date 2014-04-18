package com.androidcentral.app.fragments;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.ForumSearchActivity;
import com.androidcentral.app.ForumTopicActivity;
import com.androidcentral.app.ForumsListActivity;
import com.androidcentral.app.data.ForumData;
import com.androidcentral.app.data.ForumEntry;
import java.util.Locale;

public class ForumListFragment
  extends ListFragment
  implements LoaderManager.LoaderCallbacks<ForumData>
{
  private static final String ARG_PARENT_ID = "parentId";
  private static final String ARG_PARENT_NAME = "parentName";
  private static final String ARG_STYLE = "style";
  private ForumListAdapter adapter;
  private int parentId;
  private String parentName;
  public String style;
  
  private void addListHeader()
  {
    ListView localListView = getListView();
    TextView localTextView = (TextView)LayoutInflater.from(getActivity()).inflate(2130903081, null);
    localTextView.setText(this.parentName.toUpperCase(Locale.getDefault()));
    localListView.addHeaderView(localTextView, null, false);
  }
  
  private void launchBrowser(String paramString)
  {
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
  }
  
  public static ForumListFragment newInstance(String paramString, int paramInt)
  {
    ForumListFragment localForumListFragment = new ForumListFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("style", paramString);
    localBundle.putInt("parentId", paramInt);
    localForumListFragment.setArguments(localBundle);
    return localForumListFragment;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.parentId = getArguments().getInt("parentId");
    this.style = getArguments().getString("style");
    this.parentName = getArguments().getString("parentName");
    if (this.style.equals("name"))
    {
      getListView().setFastScrollEnabled(true);
      getListView().setFastScrollAlwaysVisible(true);
    }
    for (;;)
    {
      this.adapter = new ForumListAdapter(getActivity());
      setListAdapter(this.adapter);
      setListShown(false);
      getLoaderManager().initLoader(0, null, this);
      return;
      if (this.parentId != -1) {
        addListHeader();
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }
  
  public Loader<ForumData> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new ForumLoader(getActivity());
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623948, paramMenu);
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    ForumEntry localForumEntry = (ForumEntry)paramListView.getItemAtPosition(paramInt);
    if (localForumEntry.url != null)
    {
      launchBrowser(localForumEntry.url);
      return;
    }
    if (localForumEntry.subOnly)
    {
      Intent localIntent1 = new Intent(getActivity(), ForumsListActivity.class);
      localIntent1.putExtra("parentName", localForumEntry.name);
      localIntent1.putExtra("parentId", localForumEntry.id);
      localIntent1.putExtra("style", "");
      startActivity(localIntent1);
      return;
    }
    Intent localIntent2 = new Intent(getActivity(), ForumTopicActivity.class);
    localIntent2.putExtra("forum_id", localForumEntry.id);
    localIntent2.putExtra("name", localForumEntry.name);
    startActivity(localIntent2);
  }
  
  public void onLoadFinished(Loader<ForumData> paramLoader, ForumData paramForumData)
  {
    if (paramForumData.isForumsListLoaded())
    {
      this.adapter.clear();
      if (this.style.equals("name")) {
        this.adapter.addAll(paramForumData.getSortedForumsList());
      }
    }
    for (;;)
    {
      setListShown(true);
      return;
      this.adapter.addAll(paramForumData.getForumsList(this.parentId));
      continue;
      Toast.makeText(getActivity(), 2131165211, 0).show();
    }
  }
  
  public void onLoaderReset(Loader<ForumData> paramLoader)
  {
    this.adapter.clear();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    startActivity(new Intent(getActivity(), ForumSearchActivity.class));
    return true;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ForumListFragment
 * JD-Core Version:    0.7.0.1
 */