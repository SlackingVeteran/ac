package com.androidcentral.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class FeaturedArticleFragment
  extends Fragment
{
  public static final String ARG_BANNER_URL = "banner_url";
  public static final String ARG_COMMENT_COUNT = "comment_count";
  public static final String ARG_TITLE = "title";
  private String bannerUrl;
  private int commentCount;
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private String title;
  
  public static FeaturedArticleFragment newInstance(String paramString1, String paramString2, int paramInt)
  {
    FeaturedArticleFragment localFeaturedArticleFragment = new FeaturedArticleFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("banner_url", paramString1);
    localBundle.putString("title", paramString2);
    localBundle.putInt("comment_count", paramInt);
    localFeaturedArticleFragment.setArguments(localBundle);
    return localFeaturedArticleFragment;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.bannerUrl = getArguments().getString("banner_url");
    this.title = getArguments().getString("title");
    this.commentCount = getArguments().getInt("comment_count");
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(500)).cacheOnDisc().cacheInMemory().build();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903059, paramViewGroup, false);
    ImageView localImageView = (ImageView)localView.findViewById(2131099697);
    this.imageLoader.displayImage(this.bannerUrl, localImageView, this.displayOptions);
    ((TextView)localView.findViewById(2131099698)).setText(this.title);
    ((TextView)localView.findViewById(2131099699)).setText(String.valueOf(this.commentCount));
    return localView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.FeaturedArticleFragment
 * JD-Core Version:    0.7.0.1
 */