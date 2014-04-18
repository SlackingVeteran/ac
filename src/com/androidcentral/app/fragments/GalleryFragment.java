package com.androidcentral.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.GalleryActivity;
import com.androidcentral.app.data.GalleryCategory;
import com.androidcentral.app.data.GalleryImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GalleryFragment
  extends Fragment
  implements LoaderManager.LoaderCallbacks<List<GalleryCategory>>
{
  private LinearLayout categoriesContainer;
  private DisplayImageOptions displayOptions;
  private View emptyView;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  
  private List<GalleryCategory> filterCategories(List<GalleryCategory> paramList)
  {
    HashSet localHashSet = new HashSet(Arrays.asList(new String[] { "Babes (may be NSFW)", "Celebrities", "Dudes" }));
    ArrayList localArrayList = new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      GalleryCategory localGalleryCategory = (GalleryCategory)localIterator.next();
      if (!localHashSet.contains(localGalleryCategory.name)) {
        localArrayList.add(localGalleryCategory);
      }
    }
  }
  
  private void populateCategories(List<GalleryCategory> paramList)
  {
    int i = (int)Math.ceil(paramList.size() / 2.0D);
    Random localRandom = new Random();
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(GalleryFragment.this.getActivity(), GalleryActivity.class);
        localIntent.putExtra("category", (String)paramAnonymousView.getTag());
        GalleryFragment.this.startActivity(localIntent);
      }
    };
    this.categoriesContainer.removeAllViews();
    int j = 0;
    if (j >= i) {
      return;
    }
    View localView = this.inflater.inflate(2130903071, this.categoriesContainer, false);
    GalleryCategory localGalleryCategory1 = (GalleryCategory)paramList.get(j * 2);
    ImageView localImageView1 = (ImageView)localView.findViewById(2131099718);
    TextView localTextView1 = (TextView)localView.findViewById(2131099719);
    this.imageLoader.displayImage(localGalleryCategory1.wallpapers[localRandom.nextInt(4)].getScaleImage(0), localImageView1, this.displayOptions);
    localTextView1.setText(localGalleryCategory1.name);
    localImageView1.setTag(localGalleryCategory1.name);
    localImageView1.setOnClickListener(local1);
    ImageView localImageView2 = (ImageView)localView.findViewById(2131099720);
    TextView localTextView2 = (TextView)localView.findViewById(2131099721);
    if ((j < i - 1) || (paramList.size() % 2 == 0))
    {
      GalleryCategory localGalleryCategory2 = (GalleryCategory)paramList.get(1 + j * 2);
      this.imageLoader.displayImage(localGalleryCategory2.wallpapers[localRandom.nextInt(4)].getScaleImage(0), localImageView2, this.displayOptions);
      localTextView2.setText(localGalleryCategory2.name);
      localImageView2.setTag(localGalleryCategory2.name);
      localImageView2.setOnClickListener(local1);
    }
    for (;;)
    {
      this.categoriesContainer.addView(localView);
      j++;
      break;
      localTextView2.setVisibility(8);
    }
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.inflater = LayoutInflater.from(getActivity());
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().displayer(new FadeInBitmapDisplayer(500)).cacheOnDisc().build();
    getLoaderManager().initLoader(0, null, this);
  }
  
  public Loader<List<GalleryCategory>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new CategoryLoader(getActivity());
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903067, paramViewGroup, false);
    this.categoriesContainer = ((LinearLayout)localView.findViewById(2131099715));
    this.emptyView = localView.findViewById(16908292);
    return localView;
  }
  
  public void onLoadFinished(Loader<List<GalleryCategory>> paramLoader, List<GalleryCategory> paramList)
  {
    this.emptyView.setVisibility(8);
    if (paramList != null)
    {
      populateCategories(filterCategories(paramList));
      return;
    }
    Toast.makeText(getActivity(), 2131165211, 0).show();
  }
  
  public void onLoaderReset(Loader<List<GalleryCategory>> paramLoader) {}
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.GalleryFragment
 * JD-Core Version:    0.7.0.1
 */