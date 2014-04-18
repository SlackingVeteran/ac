package com.androidcentral.app.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidcentral.app.data.Article.CommentThread;

class CommentThreadsAdapter
  extends ArrayAdapter<Article.CommentThread>
{
  private LayoutInflater inflater;
  
  public CommentThreadsAdapter(Context paramContext, Article.CommentThread[] paramArrayOfCommentThread)
  {
    super(paramContext, 0, paramArrayOfCommentThread);
    this.inflater = LayoutInflater.from(paramContext);
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.inflater.inflate(2130903083, paramViewGroup, false);
    }
    Article.CommentThread localCommentThread = (Article.CommentThread)getItem(paramInt);
    ((TextView)paramView.findViewById(2131099750)).setText("Q" + (paramInt + 1) + ":");
    ((TextView)paramView.findViewById(2131099751)).setText(localCommentThread.title);
    ((TextView)paramView.findViewById(2131099752)).setText(String.valueOf(localCommentThread.commentCount));
    return paramView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.CommentThreadsAdapter
 * JD-Core Version:    0.7.0.1
 */