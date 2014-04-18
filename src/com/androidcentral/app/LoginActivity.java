package com.androidcentral.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.androidcentral.app.fragments.AccountsFragment;

public class LoginActivity
  extends BaseActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903051);
    if (paramBundle == null)
    {
      AccountsFragment localAccountsFragment = new AccountsFragment();
      localAccountsFragment.setArguments(getIntent().getExtras());
      getSupportFragmentManager().beginTransaction().add(2131099685, localAccountsFragment).commit();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.LoginActivity
 * JD-Core Version:    0.7.0.1
 */