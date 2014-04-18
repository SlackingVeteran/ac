package com.androidcentral.app.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidcentral.app.data.ForumEntry;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

class SubscribedForumsAdapter
  extends ArrayAdapter<ForumEntry>
{
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  
  public SubscribedForumsAdapter(Context paramContext)
  {
    super(paramContext, 0);
    this.inflater = LayoutInflater.from(paramContext);
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().cacheOnDisc().cacheInMemory().build();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = this.inflater.inflate(2130903064, paramViewGroup, false);
    ForumEntry localForumEntry = (ForumEntry)getItem(paramInt);
    ((TextView)localView.findViewById(2131099710)).setText(localForumEntry.name);
    ImageView localImageView = (ImageView)localView.findViewById(2131099709);
    this.imageLoader.displayImage(localForumEntry.getLogoUrl(), localImageView, this.displayOptions);
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SubscribedForumsAdapter
 * JD-Core Version:    0.7.0.1
 */