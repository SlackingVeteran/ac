package com.androidcentral.app;

import android.content.Intent;
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

public class ForumReportActivity
  extends BaseActivity
{
  private String postId;
  private ReportPostTask reportPostTask;
  private EditText reportReasonText;
  
  private void reportPost()
  {
    String str = this.reportReasonText.getText().toString().trim();
    if ((this.reportPostTask == null) || (this.reportPostTask.getStatus() != AsyncTask.Status.RUNNING))
    {
      this.reportPostTask = new ReportPostTask(str);
      this.reportPostTask.execute(new Void[0]);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    this.postId = getIntent().getStringExtra("post_id");
    this.reportReasonText = ((EditText)findViewById(2131099666));
    getWindow().setSoftInputMode(4);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623938, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    reportPost();
    return true;
  }
  
  class ReportPostTask
    extends AsyncTask<Void, Void, XmlRpcUtils.ResultResponse>
  {
    private String reason;
    
    public ReportPostTask(String paramString)
    {
      this.reason = XmlRpcUtils.getBase64Encoded(paramString);
    }
    
    protected XmlRpcUtils.ResultResponse doInBackground(Void... paramVarArgs)
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(new MobiquoHelper.Param("string", ForumReportActivity.this.postId));
      localArrayList.add(new MobiquoHelper.Param("base64", this.reason));
      return XmlRpcUtils.parseResultResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("report_post", localArrayList), "text/xml"));
    }
    
    protected void onPostExecute(XmlRpcUtils.ResultResponse paramResultResponse)
    {
      if (paramResultResponse.success) {}
      for (String str = "Post reported";; str = "Could not report post: " + paramResultResponse.error)
      {
        Toast.makeText(ForumReportActivity.this, str, 0).show();
        if (paramResultResponse.success)
        {
          ForumReportActivity.this.setResult(-1);
          ForumReportActivity.this.finish();
        }
        return;
      }
    }
    
    protected void onPreExecute()
    {
      Toast.makeText(ForumReportActivity.this, "Reporting post...", 0).show();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumReportActivity
 * JD-Core Version:    0.7.0.1
 */