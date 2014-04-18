package com.androidcentral.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidcentral.app.data.ForumPost;

class CommentListAdapter
  extends ArrayAdapter<ForumPost>
{
  private final int FONT_SIZE;
  private LayoutInflater inflater;
  
  public CommentListAdapter(Context paramContext)
  {
    super(paramContext, 0);
    this.inflater = LayoutInflater.from(paramContext);
    this.FONT_SIZE = getFontSize();
  }
  
  private int getFontSize()
  {
    int i = 18;
    String str = getContext().getSharedPreferences("AndroidCentralPrefs", 0).getString("pref_font_size", "");
    if (str.equals("Tiny")) {
      i = 14;
    }
    do
    {
      do
      {
        return i;
        if (str.equals("Small")) {
          return 16;
        }
      } while (str.equals("Medium"));
      if (str.equals("Large")) {
        return 20;
      }
    } while (!str.equals("Huge"));
    return 22;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = this.inflater.inflate(2130903078, paramViewGroup, false);
    ForumPost localForumPost = (ForumPost)getItem(paramInt);
    ((TextView)localView.findViewById(2131099741)).setText(localForumPost.threadTitle);
    TextView localTextView1 = (TextView)localView.findViewById(2131099742);
    localTextView1.setTextSize(this.FONT_SIZE);
    localTextView1.setText(localForumPost.shortContent);
    TextView localTextView2 = (TextView)localView.findViewById(2131099743);
    SpannableString localSpannableString = new SpannableString(localForumPost.forumName + " | " + localForumPost.authorName);
    localSpannableString.setSpan(new StyleSpan(2), 0, localForumPost.forumName.length(), 0);
    localTextView2.setText(localSpannableString);
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.CommentListAdapter
 * JD-Core Version:    0.7.0.1
 */