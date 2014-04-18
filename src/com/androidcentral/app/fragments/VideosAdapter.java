package com.androidcentral.app.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

class VideosAdapter
  extends ArrayAdapter<Video>
{
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  
  public VideosAdapter(Context paramContext)
  {
    super(paramContext, 2130903085);
    this.inflater = LayoutInflater.from(paramContext);
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(500)).cacheInMemory().cacheOnDisc().build();
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = this.inflater.inflate(2130903085, paramViewGroup, false);
    Video localVideo = (Video)getItem(paramInt);
    ((TextView)localView.findViewById(2131099755)).setText(localVideo.title);
    ImageView localImageView = (ImageView)localView.findViewById(2131099754);
    this.imageLoader.displayImage(localVideo.thumbUrl, localImageView, this.displayOptions);
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.VideosAdapter
 * JD-Core Version:    0.7.0.1
 */