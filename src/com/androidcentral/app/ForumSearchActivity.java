package com.androidcentral.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ForumSearchActivity
  extends DrawerActivity
{
  private SearchHistoryAdapter adapter;
  
  private void showResults(String paramString)
  {
    Intent localIntent = new Intent(this, ForumSearchResultsActivity.class);
    localIntent.putExtra("search_string", paramString);
    startActivity(localIntent);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903045);
    ListView localListView = (ListView)findViewById(2131099668);
    this.adapter = new SearchHistoryAdapter(this);
    localListView.setAdapter(this.adapter);
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        String str = (String)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
        ForumSearchActivity.this.adapter.add(str);
        ForumSearchActivity.this.showResults(str);
      }
    });
    ((EditText)findViewById(2131099667)).setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool = false;
        String str;
        if (paramAnonymousInt == 3)
        {
          str = paramAnonymousTextView.getText().toString().trim();
          if (!str.isEmpty()) {
            break label49;
          }
          Toast.makeText(ForumSearchActivity.this, 2131165248, 0).show();
        }
        for (;;)
        {
          bool = true;
          return bool;
          label49:
          if (str.length() < 3)
          {
            Toast.makeText(ForumSearchActivity.this, 2131165249, 0).show();
          }
          else
          {
            ForumSearchActivity.this.adapter.add(str);
            ForumSearchActivity.this.showResults(str);
          }
        }
      }
    });
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623939, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    this.adapter.clear();
    return true;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumSearchActivity
 * JD-Core Version:    0.7.0.1
 */