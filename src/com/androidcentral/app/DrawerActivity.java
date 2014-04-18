package com.androidcentral.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.androidcentral.app.fragments.AccountsFragment;
import com.androidcentral.app.fragments.ForumListFragment;
import com.androidcentral.app.fragments.GalleryFragment;
import com.androidcentral.app.fragments.NewsSectionFragment;
import com.androidcentral.app.fragments.SettingsFragment;
import com.androidcentral.app.fragments.ShopFragment;
import com.androidcentral.app.fragments.VideosFragment;
import com.androidcentral.app.util.UiUtils;

public abstract class DrawerActivity
  extends BaseActivity
{
  protected MenuListAdapter drawerAdapter;
  protected DrawerLayout drawerLayout;
  protected ListView drawerList;
  protected ActionBarDrawerToggle drawerToggle;
  
  private int getNavToggleResource()
  {
    if (UiUtils.isNightTheme(this)) {
      return 2130837547;
    }
    return 2130837548;
  }
  
  private void sendTipEmail()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("plain/text");
    localIntent.putExtra("android.intent.extra.EMAIL", new String[] { "actips@androidcentral.com" });
    localIntent.putExtra("android.intent.extra.SUBJECT", "Story Tip!");
    startActivity(localIntent);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.drawerToggle.onConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle, int paramInt)
  {
    super.onCreate(paramBundle);
    setContentView(paramInt);
    this.drawerLayout = ((DrawerLayout)findViewById(2131099660));
    this.drawerList = ((ListView)findViewById(2131099662));
    this.drawerLayout.setDrawerShadow(2130837531, 8388611);
    this.drawerAdapter = new MenuListAdapter(this);
    this.drawerList.setAdapter(this.drawerAdapter);
    this.drawerList.setOnItemClickListener(new DrawerItemClickListener(null));
    this.drawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, getNavToggleResource(), 2131165252, 2131165253);
    this.drawerLayout.setDrawerListener(this.drawerToggle);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    this.drawerToggle.onOptionsItemSelected(paramMenuItem);
    return true;
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    this.drawerToggle.syncState();
  }
  
  public void swapToPosition(int paramInt)
  {
    Intent localIntent = new Intent(this, HomeActivity.class);
    localIntent.putExtra("target_pos", paramInt);
    localIntent.setFlags(603979776);
    startActivity(localIntent);
  }
  
  public static class DrawerItem
  {
    public Bundle args;
    public Class<? extends Fragment> clss;
    public int iconRes;
    public String tag;
    
    public DrawerItem(String paramString, int paramInt, Class<? extends Fragment> paramClass)
    {
      this(paramString, paramInt, paramClass, null);
    }
    
    public DrawerItem(String paramString, int paramInt, Class<? extends Fragment> paramClass, Bundle paramBundle)
    {
      this.tag = paramString;
      this.iconRes = paramInt;
      this.clss = paramClass;
      this.args = paramBundle;
    }
  }
  
  private class DrawerItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private DrawerItemClickListener() {}
    
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if (((DrawerActivity.DrawerItem)DrawerActivity.this.drawerAdapter.getItem(paramInt)).clss != null) {
        DrawerActivity.this.swapToPosition(paramInt);
      }
      for (;;)
      {
        DrawerActivity.this.drawerLayout.closeDrawer(DrawerActivity.this.drawerList);
        return;
        DrawerActivity.this.sendTipEmail();
      }
    }
  }
  
  public class MenuListAdapter
    extends ArrayAdapter<DrawerActivity.DrawerItem>
  {
    private LayoutInflater inflater;
    
    public MenuListAdapter(Context paramContext)
    {
      super(0);
      this.inflater = LayoutInflater.from(paramContext);
      String[] arrayOfString = DrawerActivity.this.getResources().getStringArray(2131558403);
      add(new DrawerActivity.DrawerItem(arrayOfString[0], 2130837563, NewsSectionFragment.class));
      add(new DrawerActivity.DrawerItem(arrayOfString[1], 2130837561, ForumListFragment.class));
      add(new DrawerActivity.DrawerItem(arrayOfString[2], 2130837568, VideosFragment.class));
      Bundle localBundle = new Bundle();
      localBundle.putString("section_name", "podcast");
      localBundle.putString("section_title", "PODCASTS");
      add(new DrawerActivity.DrawerItem(arrayOfString[3], 2130837564, NewsSectionFragment.class, localBundle));
      add(new DrawerActivity.DrawerItem(arrayOfString[4], 2130837562, GalleryFragment.class));
      add(new DrawerActivity.DrawerItem(arrayOfString[5], 2130837566, ShopFragment.class));
      add(new DrawerActivity.DrawerItem(arrayOfString[6], 2130837567, null));
      add(new DrawerActivity.DrawerItem(arrayOfString[7], 2130837560, AccountsFragment.class));
      add(new DrawerActivity.DrawerItem(arrayOfString[8], 2130837565, SettingsFragment.class));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = this.inflater.inflate(2130903058, paramViewGroup, false);
      }
      DrawerActivity.DrawerItem localDrawerItem = (DrawerActivity.DrawerItem)getItem(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(16908308);
      localTextView.setText(localDrawerItem.tag);
      localTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawerItem.iconRes, 0, 0, 0);
      localTextView.setCompoundDrawablePadding(15);
      return paramView;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.DrawerActivity
 * JD-Core Version:    0.7.0.1
 */