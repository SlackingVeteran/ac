package com.androidcentral.app.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.androidcentral.app.HomeActivity;
import com.androidcentral.app.data.ForumData;
import com.androidcentral.app.net.SessionManager;

public class AccountsFragment
  extends Fragment
{
  private boolean defaultShowLogin;
  private boolean finished;
  private ProgressDialog progressDialog;
  private SessionManager sessionManager;
  private WebView webView;
  
  private void exit()
  {
    FragmentActivity localFragmentActivity = getActivity();
    try
    {
      ((HomeActivity)localFragmentActivity).swapToPosition(0);
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      localFragmentActivity.finish();
    }
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.sessionManager = SessionManager.getInstance();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    CookieSyncManager.createInstance(getActivity());
    this.finished = false;
    if (getArguments() != null) {}
    for (boolean bool = getArguments().getBoolean("login_or_signup");; bool = true)
    {
      this.defaultShowLogin = bool;
      return;
    }
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903065, paramViewGroup, false);
    this.webView = ((WebView)localView.findViewById(2131099711));
    this.webView.setHorizontalScrollBarEnabled(false);
    this.webView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return paramAnonymousMotionEvent.getAction() == 2;
      }
    });
    this.webView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        if ((AccountsFragment.this.progressDialog != null) && (AccountsFragment.this.progressDialog.isShowing())) {
          AccountsFragment.this.progressDialog.dismiss();
        }
      }
      
      public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        if (paramAnonymousString.contains("androidcentral"))
        {
          CookieSyncManager.getInstance().sync();
          AccountsFragment.this.sessionManager.copyWebViewCookies();
          ForumData.getInstance().clear();
          if (!AccountsFragment.this.finished)
          {
            AccountsFragment.this.finished = true;
            AccountsFragment.this.sessionManager.sessionCheck();
            AccountsFragment.this.exit();
          }
        }
      }
    });
    this.webView.clearFormData();
    WebSettings localWebSettings = this.webView.getSettings();
    localWebSettings.setUserAgentString(localWebSettings.getUserAgentString() + " MNAPP(AC1)");
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setSavePassword(false);
    localWebSettings.setSaveFormData(false);
    localWebSettings.setUseWideViewPort(true);
    if (this.sessionManager.isLoggedIn())
    {
      this.webView.loadUrl("https://passport.mobilenations.com/profile-ac");
      return localView;
    }
    WebView localWebView = this.webView;
    if (this.defaultShowLogin) {}
    for (String str = "https://passport.mobilenations.com/login-ac";; str = "https://passport.mobilenations.com/register-ac")
    {
      localWebView.loadUrl(str);
      return localView;
    }
  }
  
  public void onPause()
  {
    super.onPause();
    if (this.progressDialog != null) {
      this.progressDialog.dismiss();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.AccountsFragment
 * JD-Core Version:    0.7.0.1
 */