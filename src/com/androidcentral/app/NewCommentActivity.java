package com.androidcentral.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.androidcentral.app.net.NetUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NewCommentActivity
  extends BaseActivity
{
  public static final String EXTRA_CID = "reply_cid";
  public static final String EXTRA_COMMENT = "comment";
  public static final String EXTRA_EDIT_MODE = "edit_mode";
  public static final String EXTRA_NID = "nid";
  public static final String EXTRA_SERVER = "server";
  private String cid;
  private String comment;
  private EditText commentField;
  private CommentTask commentTask;
  private boolean editMode;
  private long nid;
  private String server;
  
  private void deleteComment()
  {
    new AlertDialog.Builder(this).setTitle(2131165260).setMessage(2131165261).setPositiveButton(17039370, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        NewCommentActivity.this.commentTask = new NewCommentActivity.DeleteCommentTask(NewCommentActivity.this);
        NewCommentActivity.this.commentTask.execute(new Void[0]);
      }
    }).setNegativeButton(17039360, null).create().show();
  }
  
  private void postComment()
  {
    String str = this.commentField.getText().toString();
    if (str.trim().isEmpty())
    {
      Toast.makeText(this, "Please enter a comment", 0).show();
      return;
    }
    if (this.editMode) {}
    for (this.commentTask = new EditCommentTask(str);; this.commentTask = new PostCommentTask(getCompletePostText(str)))
    {
      this.commentTask.execute(new Void[0]);
      return;
    }
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
    setContentView(2130903052);
    this.nid = getIntent().getLongExtra("nid", 0L);
    this.cid = getIntent().getStringExtra("reply_cid");
    this.server = getIntent().getStringExtra("server");
    this.editMode = getIntent().getBooleanExtra("edit_mode", false);
    this.comment = getIntent().getStringExtra("comment");
    this.commentField = ((EditText)findViewById(2131099686));
    getWindow().setSoftInputMode(5);
    if (this.editMode)
    {
      this.commentField.append(this.comment);
      setTitle(2131165258);
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623943, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099782: 
      postComment();
      return true;
    }
    deleteComment();
    return true;
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (!this.editMode) {
      paramMenu.removeItem(2131099781);
    }
    return true;
  }
  
  protected void onStop()
  {
    super.onStop();
    if (this.commentTask != null) {
      this.commentTask.cancel(true);
    }
  }
  
  abstract class CommentTask
    extends AsyncTask<Void, Void, Boolean>
  {
    private String errorMessage = "";
    public String failMsg;
    public String preMsg;
    public String successMsg;
    
    CommentTask() {}
    
    protected boolean handleResponse(String paramString)
    {
      if (paramString == null)
      {
        this.errorMessage = "Please try again";
        return false;
      }
      JsonParser localJsonParser = new JsonParser();
      JsonObject localJsonObject;
      try
      {
        localJsonObject = localJsonParser.parse(paramString).getAsJsonObject();
        if (localJsonObject.get("code").getAsInt() == 0) {
          return true;
        }
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        this.errorMessage = "Please try again";
        return false;
      }
      this.errorMessage = localJsonObject.get("msg").getAsString();
      return false;
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue()) {}
      for (String str = this.successMsg;; str = this.failMsg + ": " + this.errorMessage)
      {
        Toast.makeText(NewCommentActivity.this, str, 0).show();
        if (paramBoolean.booleanValue())
        {
          NewCommentActivity.this.setResult(-1);
          NewCommentActivity.this.finish();
        }
        return;
      }
    }
    
    protected void onPreExecute()
    {
      Toast.makeText(NewCommentActivity.this, this.preMsg, 0).show();
    }
  }
  
  class DeleteCommentTask
    extends NewCommentActivity.CommentTask
  {
    public DeleteCommentTask()
    {
      super();
      this.preMsg = "Deleting comment...";
      this.successMsg = "Comment deleted";
      this.failMsg = "Could not delete comment";
    }
    
    protected Boolean doInBackground(Void... paramVarArgs)
    {
      return Boolean.valueOf(handleResponse(NetUtils.post("http://" + NewCommentActivity.this.server + "/mobile_app/comment/delete", "cid=" + NewCommentActivity.this.cid, null)));
    }
  }
  
  class EditCommentTask
    extends NewCommentActivity.CommentTask
  {
    private static final String TAG = "EditCommentTask";
    private String comment;
    
    public EditCommentTask(String paramString)
    {
      super();
      this.comment = paramString;
      this.preMsg = "Updating comment...";
      this.successMsg = "Comment updated";
      this.failMsg = "Could not update comment";
    }
    
    protected Boolean doInBackground(Void... paramVarArgs)
    {
      Object localObject = "";
      try
      {
        String str = "cid=" + NewCommentActivity.this.cid + "&comment=" + URLEncoder.encode(this.comment, "UTF-8");
        localObject = str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          Log.e("EditCommentTask", Log.getStackTraceString(localUnsupportedEncodingException));
        }
      }
      return Boolean.valueOf(handleResponse(NetUtils.post("http://" + NewCommentActivity.this.server + "/mobile_app/comment/edit", (String)localObject, null)));
    }
  }
  
  class PostCommentTask
    extends NewCommentActivity.CommentTask
  {
    private static final String TAG = "PostCommentTask";
    private String comment;
    
    public PostCommentTask(String paramString)
    {
      super();
      this.comment = paramString;
      this.preMsg = "Posting comment...";
      this.successMsg = "Comment posted";
      this.failMsg = "Could not post comment";
    }
    
    protected Boolean doInBackground(Void... paramVarArgs)
    {
      Object localObject = "";
      try
      {
        localObject = "nid=" + NewCommentActivity.this.nid + "&comment=" + URLEncoder.encode(this.comment, "UTF-8");
        if (NewCommentActivity.this.cid != null)
        {
          String str = localObject + "&reply_cid=" + NewCommentActivity.this.cid;
          localObject = str;
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          Log.e("PostCommentTask", Log.getStackTraceString(localUnsupportedEncodingException));
        }
      }
      return Boolean.valueOf(handleResponse(NetUtils.post("http://" + NewCommentActivity.this.server + "/mobile_app/comment/add", (String)localObject, null)));
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.NewCommentActivity
 * JD-Core Version:    0.7.0.1
 */