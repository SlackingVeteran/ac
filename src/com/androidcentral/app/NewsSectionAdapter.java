package com.androidcentral.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class NewsSectionAdapter
  extends ArrayAdapter<NewsSection>
{
  private LayoutInflater inflater;
  
  public NewsSectionAdapter(Context paramContext)
  {
    super(paramContext, 0);
    this.inflater = LayoutInflater.from(paramContext);
    add(new NewsSection("all", "ALL", 2130837575));
    add(new NewsSection("reviews", "REVIEWS", 2130837576));
    add(new NewsSection("editorial", "EDITORIALS", 2130837573));
    add(new NewsSection("help", "HELP", 2130837574));
    add(new NewsSection("apps", "APPS", 2130837572));
    add(new NewsSection("accessories", "ACCESSORIES", 2130837571));
    add(new NewsSection("talkmobile", "TALK MOBILE", 2130837577));
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.inflater.inflate(2130903080, paramViewGroup, false);
    }
    NewsSection localNewsSection = (NewsSection)getItem(paramInt);
    TextView localTextView = (TextView)paramView.findViewById(2131099744);
    localTextView.setText(localNewsSection.sectionTitle);
    localTextView.setCompoundDrawablesWithIntrinsicBounds(localNewsSection.iconRes, 0, 0, 0);
    localTextView.setCompoundDrawablePadding(15);
    return paramView;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.inflater.inflate(2130903080, paramViewGroup, false);
    }
    NewsSection localNewsSection = (NewsSection)getItem(paramInt);
    ((TextView)paramView.findViewById(2131099744)).setText(localNewsSection.sectionTitle);
    return paramView;
  }
  
  static class NewsSection
  {
    public int iconRes;
    public String sectionName;
    public String sectionTitle;
    
    public NewsSection(String paramString1, String paramString2, int paramInt)
    {
      this.sectionName = paramString1;
      this.sectionTitle = paramString2;
      this.iconRes = paramInt;
    }
    
    public String toString()
    {
      return this.sectionTitle;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.NewsSectionAdapter
 * JD-Core Version:    0.7.0.1
 */