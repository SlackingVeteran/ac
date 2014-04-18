package com.androidcentral.app;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import com.androidcentral.app.net.SessionManager;
import com.androidcentral.app.util.UiUtils;
import com.flurry.android.FlurryAgent;
import com.quantcast.measurement.service.QuantcastClient;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.DefaultHeaderTransformer;

public abstract class BaseActivity
  extends FragmentActivity
{
  public PullToRefreshAttacher pullToRefreshAttacher;
  protected SessionManager sessionManager;
  protected SharedPreferences settings;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    UiUtils.setTheme(this);
    this.sessionManager = SessionManager.getInstance();
    this.settings = getSharedPreferences("AndroidCentralPrefs", 0);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    updateTitleAppearance();
    this.pullToRefreshAttacher = PullToRefreshAttacher.get(this);
    PullToRefreshAttacher.DefaultHeaderTransformer localDefaultHeaderTransformer = (PullToRefreshAttacher.DefaultHeaderTransformer)this.pullToRefreshAttacher.getHeaderTransformer();
    localDefaultHeaderTransformer.setPullText("Swipe down to refresh");
    localDefaultHeaderTransformer.setRefreshingText("Refreshing...");
    QuantcastClient.beginSessionWithApiKeyAndWithUserId(this, "0neif4ce6zusin26-wffdqrg9cfqru9wq", null);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    QuantcastClient.endSession(this);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    finish();
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    QuantcastClient.pauseSession();
  }
  
  protected void onResume()
  {
    super.onResume();
    QuantcastClient.resumeSession();
  }
  
  protected void onStart()
  {
    super.onStart();
    FlurryAgent.onStartSession(this, "8RXZVCW874D7NPWHJPYG");
  }
  
  protected void onStop()
  {
    super.onStop();
    FlurryAgent.onEndSession(this);
  }
  
  protected void updateTitleAppearance()
  {
    int i = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
    TextView localTextView = (TextView)getWindow().findViewById(i);
    localTextView.setTextColor(getResources().getColor(2131296288));
    localTextView.setTypeface(null, 1);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.BaseActivity
 * JD-Core Version:    0.7.0.1
 */