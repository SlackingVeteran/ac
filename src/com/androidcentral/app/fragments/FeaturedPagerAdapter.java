package com.androidcentral.app.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

class FeaturedPagerAdapter
  extends FragmentStatePagerAdapter
{
  private Cursor cursor;
  private List<FeaturedItem> featuredItems;
  
  public FeaturedPagerAdapter(FragmentManager paramFragmentManager)
  {
    super(paramFragmentManager);
  }
  
  public void changeCursor(Cursor paramCursor)
  {
    if (paramCursor != this.cursor) {
      if (paramCursor != null)
      {
        this.featuredItems = new ArrayList(paramCursor.getCount());
        paramCursor.moveToFirst();
      }
    }
    for (;;)
    {
      if (paramCursor.isAfterLast())
      {
        notifyDataSetChanged();
        if (this.cursor != null) {
          this.cursor.close();
        }
        this.cursor = paramCursor;
        return;
      }
      FeaturedItem localFeaturedItem = new FeaturedItem();
      localFeaturedItem.nid = paramCursor.getLong(paramCursor.getColumnIndex("nid"));
      localFeaturedItem.heroImage = paramCursor.getString(paramCursor.getColumnIndex("heroImage"));
      localFeaturedItem.title = paramCursor.getString(paramCursor.getColumnIndex("title"));
      localFeaturedItem.commentCount = paramCursor.getInt(paramCursor.getColumnIndex("commentCount"));
      this.featuredItems.add(localFeaturedItem);
      paramCursor.moveToNext();
    }
  }
  
  public int getCount()
  {
    if (this.featuredItems != null) {
      return this.featuredItems.size();
    }
    return 1;
  }
  
  public FeaturedItem getDataAtPosition(int paramInt)
  {
    return (FeaturedItem)this.featuredItems.get(paramInt);
  }
  
  public Fragment getItem(int paramInt)
  {
    if (this.featuredItems != null)
    {
      FeaturedItem localFeaturedItem = (FeaturedItem)this.featuredItems.get(paramInt);
      return FeaturedArticleFragment.newInstance(localFeaturedItem.heroImage, localFeaturedItem.title, localFeaturedItem.commentCount);
    }
    DummySectionFragment localDummySectionFragment = new DummySectionFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("section_text", "");
    localDummySectionFragment.setArguments(localBundle);
    return localDummySectionFragment;
  }
  
  public int getItemPosition(Object paramObject)
  {
    return -2;
  }
  
  public static class FeaturedItem
  {
    public int commentCount;
    public String heroImage;
    public long nid;
    public String title;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.FeaturedPagerAdapter
 * JD-Core Version:    0.7.0.1
 */