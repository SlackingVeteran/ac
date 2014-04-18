package com.androidcentral.app.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.androidcentral.app.data.ForumEntry;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

class ForumListAdapter
  extends ArrayAdapter<ForumEntry>
  implements SectionIndexer
{
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  private String sectionString;
  
  public ForumListAdapter(Context paramContext)
  {
    super(paramContext, 0);
    this.inflater = LayoutInflater.from(paramContext);
    this.sectionString = "";
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().cacheOnDisc().cacheInMemory().build();
  }
  
  public void addAll(Collection<? extends ForumEntry> paramCollection)
  {
    super.addAll(paramCollection);
    Iterator localIterator = paramCollection.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      char c = ((ForumEntry)localIterator.next()).name.trim().toUpperCase(Locale.getDefault()).charAt(0);
      if (this.sectionString.indexOf(c) == -1) {
        this.sectionString += c;
      }
    }
  }
  
  public int getPositionForSection(int paramInt)
  {
    for (int i = 0;; i++)
    {
      if (i >= getCount()) {
        i = 0;
      }
      while (((ForumEntry)getItem(i)).name.toUpperCase(Locale.getDefault()).charAt(0) == this.sectionString.charAt(paramInt)) {
        return i;
      }
    }
  }
  
  public int getSectionForPosition(int paramInt)
  {
    return 0;
  }
  
  public Object[] getSections()
  {
    String[] arrayOfString = new String[this.sectionString.length()];
    for (int i = 0;; i++)
    {
      if (i >= this.sectionString.length()) {
        return arrayOfString;
      }
      arrayOfString[i] = this.sectionString.charAt(i);
    }
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = this.inflater.inflate(2130903064, paramViewGroup, false);
    TextView localTextView = (TextView)localView.findViewById(2131099710);
    ForumEntry localForumEntry = (ForumEntry)getItem(paramInt);
    localTextView.setText(localForumEntry.name);
    ImageView localImageView = (ImageView)localView.findViewById(2131099709);
    if (localForumEntry.subOnly)
    {
      localImageView.setImageResource(2130837551);
      return localView;
    }
    if (localForumEntry.url != null)
    {
      localImageView.setImageResource(2130837550);
      return localView;
    }
    this.imageLoader.displayImage(localForumEntry.getLogoUrl(), localImageView, this.displayOptions);
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ForumListAdapter
 * JD-Core Version:    0.7.0.1
 */