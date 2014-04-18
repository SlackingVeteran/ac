package com.androidcentral.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.androidcentral.app.fragments.ForumListFragment;
import com.androidcentral.app.fragments.LatestThreadsFragment;
import com.androidcentral.app.fragments.ParticipatedThreadsFragment;
import com.androidcentral.app.fragments.SubscribedForumsFragment;
import com.androidcentral.app.fragments.SubscribedThreadsFragment;
import com.androidcentral.app.net.SessionManager;

class ForumSectionAdapter
  extends ArrayAdapter<ForumSection>
{
  private LayoutInflater inflater;
  
  public ForumSectionAdapter(Context paramContext)
  {
    super(paramContext, 0);
    add(new ForumSection("BY CATEGORY", 2130837540));
    add(new ForumSection("BY NAME", 2130837544));
    add(new ForumSection("LATEST POSTS", 2130837543));
    add(new ForumSection("FAVORITE FORUMS", 2130837541));
    add(new ForumSection("FAVORITE THREADS", 2130837542));
    add(new ForumSection("PARTICIPATED THREADS", 2130837545));
    this.inflater = LayoutInflater.from(paramContext);
  }
  
  public Fragment createFragment(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 0: 
      return ForumListFragment.newInstance("", -1);
    case 1: 
      return ForumListFragment.newInstance("name", 0);
    case 2: 
      return new LatestThreadsFragment();
    case 3: 
      return new SubscribedForumsFragment();
    case 4: 
      return new SubscribedThreadsFragment();
    }
    return new ParticipatedThreadsFragment();
  }
  
  public int getCount()
  {
    if (SessionManager.getInstance().isLoggedIn()) {
      return 6;
    }
    return 3;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.inflater.inflate(2130903080, paramViewGroup, false);
    }
    ForumSection localForumSection = (ForumSection)getItem(paramInt);
    TextView localTextView = (TextView)paramView.findViewById(2131099744);
    localTextView.setText(localForumSection.sectionTitle);
    localTextView.setCompoundDrawablesWithIntrinsicBounds(localForumSection.iconRes, 0, 0, 0);
    localTextView.setCompoundDrawablePadding(15);
    return paramView;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.inflater.inflate(2130903080, paramViewGroup, false);
    }
    ForumSection localForumSection = (ForumSection)getItem(paramInt);
    ((TextView)paramView.findViewById(2131099744)).setText(localForumSection.sectionTitle);
    return paramView;
  }
  
  static class ForumSection
  {
    public int iconRes;
    public String sectionTitle;
    
    public ForumSection(String paramString, int paramInt)
    {
      this.sectionTitle = paramString;
      this.iconRes = paramInt;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ForumSectionAdapter
 * JD-Core Version:    0.7.0.1
 */