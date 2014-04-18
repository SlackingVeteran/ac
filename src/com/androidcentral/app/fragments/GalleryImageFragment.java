package com.androidcentral.app.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class GalleryImageFragment
  extends Fragment
{
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private String imageUrl;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.imageUrl = getArguments().getString("url");
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(500)).cacheOnDisc().build();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903072, paramViewGroup, false);
    final ProgressBar localProgressBar = (ProgressBar)localView.findViewById(2131099723);
    ImageView localImageView = (ImageView)localView.findViewById(2131099722);
    this.imageLoader.displayImage(this.imageUrl, localImageView, this.displayOptions, new SimpleImageLoadingListener()
    {
      public void onLoadingComplete(String paramAnonymousString, View paramAnonymousView, Bitmap paramAnonymousBitmap)
      {
        localProgressBar.setVisibility(8);
      }
      
      public void onLoadingFailed(String paramAnonymousString, View paramAnonymousView, FailReason paramAnonymousFailReason)
      {
        localProgressBar.setVisibility(8);
      }
      
      public void onLoadingStarted(String paramAnonymousString, View paramAnonymousView)
      {
        localProgressBar.setVisibility(0);
      }
    });
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.GalleryImageFragment
 * JD-Core Version:    0.7.0.1
 */