package com.androidcentral.app.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.androidcentral.app.data.Article.CommentThread;

public class TMThreadDialogFragment
  extends DialogFragment
{
  public static final String ARG_THREADS = "threads";
  private ThreadSelectListener listener;
  private Article.CommentThread[] threads;
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.listener = ((ThreadSelectListener)paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.threads = ((Article.CommentThread[])getArguments().getParcelableArray("threads"));
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    final CommentThreadsAdapter localCommentThreadsAdapter = new CommentThreadsAdapter(getActivity(), this.threads);
    localBuilder.setAdapter(localCommentThreadsAdapter, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        TMThreadDialogFragment.this.listener.onThreadSelected((Article.CommentThread)localCommentThreadsAdapter.getItem(paramAnonymousInt));
      }
    });
    return localBuilder.create();
  }
  
  public static abstract interface ThreadSelectListener
  {
    public abstract void onThreadSelected(Article.CommentThread paramCommentThread);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.TMThreadDialogFragment
 * JD-Core Version:    0.7.0.1
 */