package com.androidcentral.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.androidcentral.app.fragments.PostListFragment;
import com.androidcentral.app.fragments.SearchThreadsFragment;
import com.viewpagerindicator.TabPageIndicator;

public class ForumSearchResultsActivity
  extends DrawerActivity
{
  public static final String EXTRA_SEARCH_STRING = "search_string";
  private SectionPagerAdapter adapter;
  private TabPageIndicator indicator;
  private ViewPager resultsSectionPager;
  private String searchString;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903046);
    this.searchString = getIntent().getStringExtra("search_string");
    this.resultsSectionPager = ((ViewPager)findViewById(2131099670));
    this.adapter = new SectionPagerAdapter(getSupportFragmentManager());
    this.resultsSectionPager.setAdapter(this.adapter);
    this.indicator = ((TabPageIndicator)findViewById(2131099669));
    this.indicator.setViewPager(this.resultsSectionPager);
  }
  
  class SectionPagerAdapter
    extends FragmentPagerAdapter
  {
    public SectionPagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    public int getCount()
    {
      return 2;
    }
    
    public Fragment getItem(int paramInt)
    {
      if (paramInt == 0)
      {
        SearchThreadsFragment localSearchThreadsFragment = new SearchThreadsFragment();
        Bundle localBundle1 = new Bundle();
        localBundle1.putString("search_string", ForumSearchResultsActivity.this.searchString);
        localSearchThreadsFragment.setArguments(localBundle1);
        return localSearchThreadsFragment;
      }
      PostListFragment localPostListFragment = new PostListFragment();
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("search_string", ForumSearchResultsActivity.this.searchString);
      localPostListFragment.setArguments(localBundle2);
      return localPostListFragment;
    }
    
    public CharSequence getPageTitle(int paramInt)
    {
      return ForumSearchResultsActivity.this.getResources().getStringArray(2131558404)[paramInt];
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumSearchResultsActivity
 * JD-Core Version:    0.7.0.1
 */