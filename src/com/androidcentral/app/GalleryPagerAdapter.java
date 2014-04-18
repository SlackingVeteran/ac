package com.androidcentral.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidcentral.app.data.GalleryImage;
import com.androidcentral.app.fragments.GalleryImageFragment;
import java.util.List;

class GalleryPagerAdapter
  extends FragmentStatePagerAdapter
{
  private List<GalleryImage> images;
  
  public GalleryPagerAdapter(FragmentManager paramFragmentManager)
  {
    super(paramFragmentManager);
  }
  
  public int getCount()
  {
    if (this.images != null) {
      return this.images.size();
    }
    return 1;
  }
  
  public GalleryImage getDataAtPosition(int paramInt)
  {
    if (this.images != null) {
      return (GalleryImage)this.images.get(paramInt);
    }
    return null;
  }
  
  public Fragment getItem(int paramInt)
  {
    if (this.images != null)
    {
      String str = ((GalleryImage)this.images.get(paramInt)).getImageUrl();
      GalleryImageFragment localGalleryImageFragment = new GalleryImageFragment();
      Bundle localBundle = new Bundle();
      localBundle.putString("url", str);
      localGalleryImageFragment.setArguments(localBundle);
      return localGalleryImageFragment;
    }
    return new LoadingFragment();
  }
  
  public int getItemPosition(Object paramObject)
  {
    return -2;
  }
  
  public void setData(List<GalleryImage> paramList)
  {
    this.images = paramList;
    notifyDataSetChanged();
  }
  
  public static class LoadingFragment
    extends Fragment
  {
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      return paramLayoutInflater.inflate(2130903070, paramViewGroup, false);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.GalleryPagerAdapter
 * JD-Core Version:    0.7.0.1
 */