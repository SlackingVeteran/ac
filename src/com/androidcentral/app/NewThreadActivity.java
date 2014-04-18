package com.androidcentral.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.XmlRpcUtils;
import com.androidcentral.app.net.XmlRpcUtils.ResultResponse;
import java.util.ArrayList;
import java.util.List;

public class NewThreadActivity
  extends BaseActivity
{
  public static final String EXTRA_FORUM_ID = "forum_id";
  private EditText contentField;
  private int forumId;
  private PostThreadTask postThreadTask;
  private EditText titleField;
  
  private void postThread()
  {
    String str1 = this.titleField.getText().toString().trim();
    String str2 = this.contentField.getText().toString().trim();
    if ((str1.isEmpty()) || (str2.isEmpty())) {
      Toast.makeText(this, "Please enter both a title and post text", 0).show();
    }
    while ((this.postThreadTask != null) && (this.postThreadTask.getStatus() == AsyncTask.Status.RUNNING)) {
      return;
    }
    this.postThreadTask = new PostThreadTask(str1, getCompletePostText(str2));
    this.postThreadTask.execute(new Void[0]);
  }
  
  public String getCompletePostText(String paramString)
  {
    String str = this.settings.getString("pref_forums_signature", "");
    if (!str.isEmpty()) {
      paramString = new StringBuilder(String.valueOf(paramString)).append("\n\n").toString() + str;
    }
    return paramString;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903053);
    this.titleField = ((EditText)findViewById(2131099687));
    this.contentField = ((EditText)findViewById(2131099688));
    this.forumId = getIntent().getIntExtra("forum_id", -1);
    getWindow().setSoftInputMode(4);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623945, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    postThread();
    return true;
  }
  
  class PostThreadTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private String content;
    private String title;
    
    public PostThreadTask(String paramString1, String paramString2)
    {
      this.title = XmlRpcUtils.getBase64Encoded(paramString1);
      this.content = XmlRpcUtils.getBase64Encoded(paramString2);
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(3);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(NewThreadActivity.this.forumId)));
      localArrayList.add(new MobiquoHelper.Param("base64", this.title));
      localArrayList.add(new MobiquoHelper.Param("base64", this.content));
      return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("new_topic", localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success) {}
      for (String str = "Thread posted";; str = "Could not post thread: " + paramResultResponse.error)
      {
        Toast.makeText(NewThreadActivity.this, str, 0).show();
        if (paramResultResponse.success)
        {
          NewThreadActivity.this.setResult(-1);
          NewThreadActivity.this.finish();
        }
        return;
      }
    }
    
    protected void onPreExecute()
    {
      Toast.makeText(NewThreadActivity.this, "Posting thread...", 0).show();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.NewThreadActivity
 * JD-Core Version:    0.7.0.1
 */