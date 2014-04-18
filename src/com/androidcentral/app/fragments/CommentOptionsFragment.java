package com.androidcentral.app.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class CommentOptionsFragment
  extends DialogFragment
{
  public static final String ARG_POSITION = "position";
  public static final String ARG_POSTDATE = "postdate";
  public static final String ARG_UID = "uid";
  private CommentDialogListener listener;
  private int position;
  private String postdate;
  private String uid;
  
  public static CommentOptionsFragment newInstance(int paramInt, String paramString1, String paramString2)
  {
    CommentOptionsFragment localCommentOptionsFragment = new CommentOptionsFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("position", paramInt);
    localBundle.putString("uid", paramString1);
    localBundle.putString("postdate", paramString2);
    localCommentOptionsFragment.setArguments(localBundle);
    return localCommentOptionsFragment;
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.position = getArguments().getInt("position");
    this.uid = getArguments().getString("uid");
    this.postdate = getArguments().getString("postdate");
    this.listener = ((CommentDialogListener)paramActivity);
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    localBuilder.setTitle(2131165219).setAdapter(new CommentOptionsAdapter(getActivity(), this.uid, this.postdate), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        CommentOptionsFragment.this.listener.onCommentOptionSelected(CommentOptionsFragment.this.position, paramAnonymousInt);
      }
    });
    return localBuilder.create();
  }
  
  public static abstract interface CommentDialogListener
  {
    public abstract void onCommentOptionSelected(int paramInt1, int paramInt2);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.CommentOptionsFragment
 * JD-Core Version:    0.7.0.1
 */