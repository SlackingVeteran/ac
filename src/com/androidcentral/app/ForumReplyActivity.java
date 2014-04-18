package com.androidcentral.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Xml;
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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public class ForumReplyActivity
  extends BaseActivity
{
  public static final int MODE_EDIT = 2;
  public static final int MODE_REPLY = 0;
  public static final int MODE_REPLY_WITH_QUOTE = 1;
  private String forumId;
  private int mode;
  private String postId;
  private PostReplyTask postReplyTask;
  private EditText replyField;
  private SaveChangesTask saveChangesTask;
  private String threadId;
  
  private void postReply()
  {
    String str = this.replyField.getText().toString().trim();
    if (str.isEmpty()) {
      Toast.makeText(this, "Please enter a reply", 0).show();
    }
    while ((this.postReplyTask != null) && (this.postReplyTask.getStatus() == AsyncTask.Status.RUNNING)) {
      return;
    }
    this.postReplyTask = new PostReplyTask(getCompletePostText(str));
    this.postReplyTask.execute(new Void[0]);
  }
  
  private void saveChanges()
  {
    String str = this.replyField.getText().toString().trim();
    if (str.isEmpty()) {
      Toast.makeText(this, "Please enter a reply", 0).show();
    }
    while ((this.saveChangesTask != null) && (this.saveChangesTask.getStatus() == AsyncTask.Status.RUNNING)) {
      return;
    }
    this.saveChangesTask = new SaveChangesTask(str);
    this.saveChangesTask.execute(new Void[0]);
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
    setContentView(2130903043);
    this.replyField = ((EditText)findViewById(2131099665));
    this.mode = getIntent().getIntExtra("mode", 0);
    if (this.mode == 2)
    {
      setTitle(2131165238);
      updateTitleAppearance();
    }
    this.threadId = getIntent().getStringExtra("thread_id");
    this.forumId = getIntent().getStringExtra("forum_id");
    this.postId = getIntent().getStringExtra("post_id");
    if (((this.mode == 1) || (this.mode == 2)) && (paramBundle == null)) {
      new GetPostTask().execute(new Void[0]);
    }
    getWindow().setSoftInputMode(4);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623944, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    if ((this.mode == 0) || (this.mode == 1))
    {
      postReply();
      return true;
    }
    saveChanges();
    return true;
  }
  
  class GetPostTask
    extends AsyncTask<Void, Void, String>
  {
    private static final String TAG = "GetPostTask";
    private String method;
    private ProgressDialog progressDialog;
    
    public GetPostTask()
    {
      if (ForumReplyActivity.this.mode == 1) {}
      for (String str = "get_quote_post";; str = "get_raw_post")
      {
        this.method = str;
        return;
      }
    }
    
    private String parseResponse(String paramString)
    {
      if (paramString == null) {}
      for (;;)
      {
        return null;
        try
        {
          XmlPullParser localXmlPullParser = Xml.newPullParser();
          localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
          localXmlPullParser.setInput(new StringReader(paramString));
          localXmlPullParser.nextTag();
          do
          {
            if (localXmlPullParser.next() == 1) {
              break;
            }
          } while ((localXmlPullParser.getEventType() != 2) || (!localXmlPullParser.getName().equals("name")) || (!XmlRpcUtils.readText(localXmlPullParser).equals("post_content")));
          String str = XmlRpcUtils.getBase64Value(localXmlPullParser);
          return str;
        }
        catch (Exception localException)
        {
          Log.e("GetPostTask", Log.getStackTraceString(localException));
        }
      }
      return null;
    }
    
    protected String doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(new MobiquoHelper.Param("string", ForumReplyActivity.this.postId));
      return parseResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod(this.method, localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(String paramString)
    {
      EditText localEditText = ForumReplyActivity.this.replyField;
      if (paramString != null) {}
      for (;;)
      {
        localEditText.append(paramString);
        this.progressDialog.dismiss();
        return;
        paramString = "";
      }
    }
    
    protected void onPreExecute()
    {
      this.progressDialog = ProgressDialog.show(ForumReplyActivity.this, "", "Loading Post...", true);
    }
  }
  
  class PostReplyTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private String reply;
    
    public PostReplyTask(String paramString)
    {
      this.reply = XmlRpcUtils.getBase64Encoded(paramString);
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumReplyActivity.this.forumId)));
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumReplyActivity.this.threadId)));
      localArrayList.add(new MobiquoHelper.Param("base64", ""));
      localArrayList.add(new MobiquoHelper.Param("base64", this.reply));
      return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("reply_post", localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success) {}
      for (String str = "Reply posted";; str = "Could not post reply: " + paramResultResponse.error)
      {
        Toast.makeText(ForumReplyActivity.this, str, 0).show();
        if (paramResultResponse.success)
        {
          ForumReplyActivity.this.setResult(-1);
          ForumReplyActivity.this.finish();
        }
        return;
      }
    }
    
    protected void onPreExecute()
    {
      Toast.makeText(ForumReplyActivity.this, "Posting reply...", 0).show();
    }
  }
  
  class SaveChangesTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private String postContents;
    
    public SaveChangesTask(String paramString)
    {
      this.postContents = XmlRpcUtils.getBase64Encoded(paramString);
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(3);
      localArrayList.add(new MobiquoHelper.Param("string", String.valueOf(ForumReplyActivity.this.postId)));
      localArrayList.add(new MobiquoHelper.Param("base64", ""));
      localArrayList.add(new MobiquoHelper.Param("base64", this.postContents));
      return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("save_raw_post", localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success) {}
      for (String str = "Changes saved";; str = "Could not save changes: " + paramResultResponse.error)
      {
        Toast.makeText(ForumReplyActivity.this, str, 0).show();
        if (paramResultResponse.success)
        {
          ForumReplyActivity.this.setResult(-1);
          ForumReplyActivity.this.finish();
        }
        return;
      }
    }
    
    protected void onPreExecute()
    {
      Toast.makeText(ForumReplyActivity.this, "Saving changes...", 0).show();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumReplyActivity
 * JD-Core Version:    0.7.0.1
 */