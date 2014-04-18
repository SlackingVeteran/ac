package com.androidcentral.app.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.androidcentral.app.net.SessionManager;
import java.util.ArrayList;
import java.util.List;

class CommentOptionsAdapter
  extends BaseAdapter
{
  private static final long FIFTEEN_M_IN_MS = 900000L;
  private LayoutInflater inflater;
  private List<DialogOption> options;
  private SessionManager sessionManager = SessionManager.getInstance();
  
  public CommentOptionsAdapter(Context paramContext, String paramString1, String paramString2)
  {
    this.inflater = LayoutInflater.from(paramContext);
    generateOptions(paramString1, paramString2);
  }
  
  private boolean enableEdit(String paramString1, String paramString2)
  {
    return (this.sessionManager.isLoggedIn()) && (this.sessionManager.getDrupalId() != null) && (paramString1.equals(this.sessionManager.getDrupalId())) && (System.currentTimeMillis() - 1000L * Long.parseLong(paramString2) < 900000L);
  }
  
  private void generateOptions(String paramString1, String paramString2)
  {
    this.options = new ArrayList();
    this.options.add(new DialogOption(2131165257, 2130837535));
    if (enableEdit(paramString1, paramString2)) {
      this.options.add(new DialogOption(2131165258, 2130837532));
    }
  }
  
  public int getCount()
  {
    return this.options.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.options.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    TextView localTextView = (TextView)this.inflater.inflate(2130903063, paramViewGroup, false);
    DialogOption localDialogOption = (DialogOption)this.options.get(paramInt);
    localTextView.setText(localDialogOption.textResId);
    localTextView.setCompoundDrawablesWithIntrinsicBounds(localDialogOption.iconResId, 0, 0, 0);
    localTextView.setCompoundDrawablePadding(10);
    return localTextView;
  }
  
  static class DialogOption
  {
    public int iconResId;
    public int textResId;
    
    public DialogOption(int paramInt1, int paramInt2)
    {
      this.textResId = paramInt1;
      this.iconResId = paramInt2;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.CommentOptionsAdapter
 * JD-Core Version:    0.7.0.1
 */