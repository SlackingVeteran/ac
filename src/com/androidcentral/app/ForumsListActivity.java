package com.androidcentral.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.androidcentral.app.fragments.ForumListFragment;

public class ForumsListActivity
  extends DrawerActivity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903049);
    if (paramBundle == null)
    {
      ForumListFragment localForumListFragment = new ForumListFragment();
      localForumListFragment.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction().add(2131099680, localForumListFragment).commit();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumsListActivity
 * JD-Core Version:    0.7.0.1
 */