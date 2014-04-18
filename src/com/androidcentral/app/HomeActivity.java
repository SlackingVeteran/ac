package com.androidcentral.app;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import com.androidcentral.app.fragments.NewsSectionFragment;
import com.androidcentral.app.fragments.PreferenceListFragment.OnPreferenceAttachedListener;
import com.androidcentral.app.util.UiUtils;
import com.espian.showcaseview.OnShowcaseEventListener;
import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseView.ConfigOptions;
import com.espian.showcaseview.targets.PointTarget;
import java.util.Locale;

public class HomeActivity
  extends DrawerActivity
  implements PreferenceListFragment.OnPreferenceAttachedListener
{
  private static final String CHECKED_POS = "checked_position";
  private static final String DROPDOWN_SELECTED = "dropdown_selected_indx";
  
  private boolean isForumsFragment(int paramInt)
  {
    return paramInt == 1;
  }
  
  private boolean isNewsFragment(int paramInt)
  {
    return paramInt == 0;
  }
  
  private void setupActionBar(int paramInt)
  {
    final int i = this.drawerList.getCheckedItemPosition();
    if (isNewsFragment(i))
    {
      final NewsSectionAdapter localNewsSectionAdapter = new NewsSectionAdapter(this);
      getActionBar().setNavigationMode(1);
      getActionBar().setListNavigationCallbacks(localNewsSectionAdapter, new ActionBar.OnNavigationListener()
      {
        public boolean onNavigationItemSelected(int paramAnonymousInt, long paramAnonymousLong)
        {
          NewsSectionAdapter.NewsSection localNewsSection = (NewsSectionAdapter.NewsSection)localNewsSectionAdapter.getItem(paramAnonymousInt);
          NewsSectionFragment localNewsSectionFragment = NewsSectionFragment.newInstance(localNewsSection.sectionName, localNewsSection.sectionTitle);
          HomeActivity.this.replaceFragment(localNewsSectionFragment, i + "-" + paramAnonymousInt);
          return true;
        }
      });
      getActionBar().setSelectedNavigationItem(paramInt);
      return;
    }
    if (isForumsFragment(i))
    {
      final ForumSectionAdapter localForumSectionAdapter = new ForumSectionAdapter(this);
      getActionBar().setNavigationMode(1);
      getActionBar().setListNavigationCallbacks(localForumSectionAdapter, new ActionBar.OnNavigationListener()
      {
        public boolean onNavigationItemSelected(int paramAnonymousInt, long paramAnonymousLong)
        {
          Fragment localFragment = localForumSectionAdapter.createFragment(paramAnonymousInt);
          HomeActivity.this.replaceFragment(localFragment, i + "-" + paramAnonymousInt);
          return true;
        }
      });
      getActionBar().setSelectedNavigationItem(paramInt);
      return;
    }
    getActionBar().setNavigationMode(0);
  }
  
  private void showTutorials()
  {
    SharedPreferences localSharedPreferences = getSharedPreferences("AndroidCentralPrefs", 0);
    if (!localSharedPreferences.getBoolean("home_tutorials_shown", false))
    {
      final View localView = getWindow().getDecorView().findViewById(16908290);
      localView.setAlpha(0.05F);
      ShowcaseView.ConfigOptions localConfigOptions = new ShowcaseView.ConfigOptions();
      localConfigOptions.hideOnClickOutside = true;
      Point localPoint = UiUtils.getDisplayPixels(this);
      ShowcaseView localShowcaseView = ShowcaseView.insertShowcaseView(new PointTarget(300 + localPoint.x, 300 + localPoint.y), this, "Did you know?", "You can swipe down anywhere on most screens to refresh the content!", localConfigOptions);
      localShowcaseView.setOnShowcaseEventListener(new OnShowcaseEventListener()
      {
        public void onShowcaseViewDidHide(ShowcaseView paramAnonymousShowcaseView)
        {
          localView.setAlpha(1.0F);
        }
        
        public void onShowcaseViewHide(ShowcaseView paramAnonymousShowcaseView) {}
        
        public void onShowcaseViewShow(ShowcaseView paramAnonymousShowcaseView) {}
      });
      localShowcaseView.animateGesture(localPoint.x / 2, 100.0F, localPoint.x / 2, localPoint.y);
      localSharedPreferences.edit().putBoolean("home_tutorials_shown", true).commit();
    }
  }
  
  private void updateTitle()
  {
    int i = this.drawerList.getCheckedItemPosition();
    DrawerActivity.DrawerItem localDrawerItem = (DrawerActivity.DrawerItem)this.drawerAdapter.getItem(i);
    if ((i == 0) || (i == 1)) {
      setTitle("");
    }
    for (;;)
    {
      updateTitleAppearance();
      return;
      setTitle(localDrawerItem.tag.toUpperCase(Locale.getDefault()));
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    switch (this.drawerList.getCheckedItemPosition())
    {
    default: 
      swapToPosition(0);
      return;
    }
    super.onBackPressed();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903042);
    showTutorials();
    if (paramBundle == null)
    {
      this.drawerList.setItemChecked(0, true);
      setupActionBar(0);
    }
    for (;;)
    {
      updateTitle();
      return;
      this.drawerList.setItemChecked(paramBundle.getInt("checked_position"), true);
      setupActionBar(paramBundle.getInt("dropdown_selected_indx", 0));
    }
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    swapToPosition(paramIntent.getIntExtra("target_pos", 0));
  }
  
  public void onPreferenceAttached(PreferenceScreen paramPreferenceScreen, int paramInt) {}
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("checked_position", this.drawerList.getCheckedItemPosition());
    if (getActionBar().getNavigationMode() == 1) {
      paramBundle.putInt("dropdown_selected_indx", getActionBar().getSelectedNavigationIndex());
    }
  }
  
  public void replaceFragment(Fragment paramFragment, String paramString)
  {
    if (getSupportFragmentManager().findFragmentByTag(paramString) == null) {
      getSupportFragmentManager().beginTransaction().replace(2131099664, paramFragment, paramString).commit();
    }
  }
  
  public void swapToPosition(int paramInt)
  {
    this.drawerList.setItemChecked(paramInt, true);
    if ((!isNewsFragment(paramInt)) && (!isForumsFragment(paramInt)))
    {
      DrawerActivity.DrawerItem localDrawerItem = (DrawerActivity.DrawerItem)this.drawerAdapter.getItem(paramInt);
      replaceFragment(Fragment.instantiate(this, localDrawerItem.clss.getName(), localDrawerItem.args), String.valueOf(paramInt));
    }
    setupActionBar(0);
    updateTitle();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.HomeActivity
 * JD-Core Version:    0.7.0.1
 */