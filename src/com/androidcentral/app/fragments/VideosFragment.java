package com.androidcentral.app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.androidcentral.app.util.YouTubeHelper;
import java.util.List;

public class VideosFragment
  extends ListFragment
  implements LoaderManager.LoaderCallbacks<List<Video>>
{
  private VideosAdapter adapter;
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.adapter = new VideosAdapter(getActivity());
    setListAdapter(this.adapter);
    setListShown(false);
    getListView().setDivider(null);
    getListView().setDividerHeight(0);
    getLoaderManager().initLoader(0, null, this);
  }
  
  public Loader<List<Video>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new VideoLoader(getActivity());
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Video localVideo = (Video)paramListView.getItemAtPosition(paramInt);
    boolean bool = getActivity().getSharedPreferences("AndroidCentralPrefs", 0).getBoolean("pref_video_fullscreen", false);
    YouTubeHelper.launchPlayer(getActivity(), localVideo.id, bool);
  }
  
  public void onLoadFinished(Loader<List<Video>> paramLoader, List<Video> paramList)
  {
    if (paramList != null)
    {
      this.adapter.clear();
      this.adapter.addAll(paramList);
    }
    for (;;)
    {
      setListShown(true);
      return;
      Toast.makeText(getActivity(), 2131165211, 0).show();
    }
  }
  
  public void onLoaderReset(Loader<List<Video>> paramLoader)
  {
    this.adapter.clear();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.VideosFragment
 * JD-Core Version:    0.7.0.1
 */