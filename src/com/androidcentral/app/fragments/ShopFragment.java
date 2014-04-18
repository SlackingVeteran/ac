package com.androidcentral.app.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShopFragment
  extends Fragment
{
  private ProgressDialog progressDialog;
  private WebView webView;
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.progressDialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenuInflater.inflate(2131623949, paramMenu);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.webView = new WebView(getActivity());
    this.webView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
        if ((ShopFragment.this.progressDialog != null) && (ShopFragment.this.progressDialog.isShowing())) {
          ShopFragment.this.progressDialog.dismiss();
        }
      }
      
      public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
      }
    });
    this.webView.setOnKeyListener(new View.OnKeyListener()
    {
      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if (paramAnonymousKeyEvent.getAction() == 0) {
          switch (paramAnonymousInt)
          {
          }
        }
        do
        {
          return false;
        } while (!ShopFragment.this.webView.canGoBack());
        ShopFragment.this.webView.goBack();
        return true;
      }
    });
    WebSettings localWebSettings = this.webView.getSettings();
    localWebSettings.setUserAgentString("MNAPP(AC1)");
    localWebSettings.setJavaScriptEnabled(true);
    if (paramBundle == null) {
      this.webView.loadUrl("http://shopandroid.com/");
    }
    for (;;)
    {
      return this.webView;
      this.webView.loadUrl(paramBundle.getString("current_url"));
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099786: 
      this.webView.loadUrl("http://shopandroid.com/cart.htm");
      return true;
    case 2131099787: 
      this.webView.loadUrl("http://shopandroid.com/content/customercare/index.htm");
      return true;
    case 2131099788: 
      this.webView.loadUrl("http://shopandroid.com/content/customercare/page-status.htm");
      return true;
    }
    Intent localIntent = new Intent("android.intent.action.DIAL");
    localIntent.setData(Uri.parse("tel:+18884686158"));
    startActivity(localIntent);
    return true;
  }
  
  public void onPause()
  {
    super.onPause();
    if (this.progressDialog != null) {
      this.progressDialog.dismiss();
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.webView != null) {
      paramBundle.putString("current_url", this.webView.getUrl());
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ShopFragment
 * JD-Core Version:    0.7.0.1
 */