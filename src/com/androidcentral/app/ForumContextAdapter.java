package com.androidcentral.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.androidcentral.app.data.ForumComment;
import java.util.ArrayList;
import java.util.List;

class ForumContextAdapter
  extends BaseAdapter
{
  private ForumComment comment;
  private LayoutInflater inflater;
  private List<DialogOption> options;
  
  public ForumContextAdapter(Context paramContext, ForumComment paramForumComment)
  {
    this.comment = paramForumComment;
    this.inflater = LayoutInflater.from(paramContext);
    generateOptions();
  }
  
  private void generateOptions()
  {
    this.options = new ArrayList();
    this.options.add(new DialogOption(2131165236, 2130837535));
    this.options.add(new DialogOption(2131165237, 2130837536));
    if (this.comment.canEdit) {
      this.options.add(new DialogOption(2131165238, 2130837532));
    }
    this.options.add(new DialogOption(2131165239, 2130837538));
    this.options.add(new DialogOption(2131165240, 2130837533));
    this.options.add(new DialogOption(2131165241, 2130837534));
    this.options.add(new DialogOption(2131165242, 2130837537));
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
 * Qualified Name:     com.androidcentral.app.ForumContextAdapter
 * JD-Core Version:    0.7.0.1
 */