package com.androidcentral.app;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.data.GalleryImage;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.SystemUiHider;
import com.androidcentral.app.util.SystemUiHider.OnVisibilityChangeListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity
  extends BaseActivity
{
  private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
  public static final String EXTRA_CATEGORY = "category";
  public static final String EXTRA_IMAGES = "images";
  private static final String TAG = "GalleryActivity";
  private GalleryPagerAdapter adapter;
  private View contentView;
  private View controlsView;
  private TextView galleryPageInfo;
  private ViewPager galleryPager;
  View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
  {
    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      GalleryActivity.this.delayedHide(3000);
      return false;
    }
  };
  Handler mHideHandler = new Handler();
  Runnable mHideRunnable = new Runnable()
  {
    public void run()
    {
      GalleryActivity.this.mSystemUiHider.hide();
      GalleryActivity.this.getActionBar().hide();
    }
  };
  private SystemUiHider mSystemUiHider;
  BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Toast.makeText(GalleryActivity.this, "Wallpaper set", 0).show();
    }
  };
  private TextView wallpaperContentText;
  private WallpaperDownloadTask wallpaperDownloader;
  
  private void delayedHide(int paramInt)
  {
    this.mHideHandler.removeCallbacks(this.mHideRunnable);
    this.mHideHandler.postDelayed(this.mHideRunnable, paramInt);
  }
  
  private void downloadImage()
  {
    GalleryImage localGalleryImage = this.adapter.getDataAtPosition(this.galleryPager.getCurrentItem());
    DownloadManager localDownloadManager = (DownloadManager)getSystemService("download");
    Uri localUri = Uri.parse(localGalleryImage.getImageUrl());
    DownloadManager.Request localRequest = new DownloadManager.Request(localUri);
    localRequest.allowScanningByMediaScanner();
    localRequest.setNotificationVisibility(1);
    localRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, localUri.getLastPathSegment());
    localDownloadManager.enqueue(localRequest);
    Toast.makeText(this, 2131165226, 0).show();
  }
  
  private void refreshControls(int paramInt)
  {
    GalleryImage localGalleryImage = this.adapter.getDataAtPosition(paramInt);
    View localView;
    if (localGalleryImage != null)
    {
      this.wallpaperContentText.setText(localGalleryImage.getContent());
      this.galleryPageInfo.setText(paramInt + 1 + " of " + this.adapter.getCount());
      this.controlsView.setTranslationY(0.0F);
      localView = this.controlsView;
      if (!this.mSystemUiHider.isVisible()) {
        break label95;
      }
    }
    label95:
    for (int i = 0;; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  private void setWallpaper()
  {
    GalleryImage localGalleryImage = this.adapter.getDataAtPosition(this.galleryPager.getCurrentItem());
    if (localGalleryImage == null) {
      return;
    }
    File localFile;
    WallpaperManager localWallpaperManager;
    int i;
    int j;
    try
    {
      localFile = DiscCacheUtil.findInCache(localGalleryImage.getImageUrl(), ImageLoader.getInstance().getDiscCache());
      localWallpaperManager = WallpaperManager.getInstance(this);
      i = localWallpaperManager.getDesiredMinimumHeight();
      j = localWallpaperManager.getDesiredMinimumWidth();
      if ((j <= 0) || (i <= 0))
      {
        localWallpaperManager.setStream(new FileInputStream(localFile));
        return;
      }
    }
    catch (Exception localException)
    {
      Log.e("GalleryActivity", Log.getStackTraceString(localException));
      return;
    }
    Bitmap localBitmap1 = BitmapFactory.decodeFile(localFile.getAbsolutePath());
    int k = localBitmap1.getHeight();
    int m = localBitmap1.getWidth();
    Bitmap localBitmap2 = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap2);
    float f1 = j / m;
    float f2 = i / k;
    Matrix localMatrix = new Matrix();
    localMatrix.preScale(f1, f2);
    localCanvas.drawBitmap(localBitmap1, localMatrix, new Paint(2));
    localWallpaperManager.setBitmap(localBitmap2);
  }
  
  private void viewInNative()
  {
    GalleryImage localGalleryImage = this.adapter.getDataAtPosition(this.galleryPager.getCurrentItem());
    if (localGalleryImage != null)
    {
      File localFile = DiscCacheUtil.findInCache(localGalleryImage.getImageUrl(), ImageLoader.getInstance().getDiscCache());
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setDataAndType(Uri.parse("file://" + localFile.getAbsolutePath()), "image/*");
      startActivity(localIntent);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903050);
    this.controlsView = findViewById(2131099682);
    this.contentView = findViewById(2131099681);
    this.wallpaperContentText = ((TextView)findViewById(2131099683));
    this.galleryPageInfo = ((TextView)findViewById(2131099684));
    this.galleryPager = ((ViewPager)this.contentView);
    this.adapter = new GalleryPagerAdapter(getSupportFragmentManager());
    this.galleryPager.setAdapter(this.adapter);
    this.galleryPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
    {
      public void onPageSelected(int paramAnonymousInt)
      {
        GalleryActivity.this.refreshControls(paramAnonymousInt);
      }
    });
    this.galleryPager.setOnTouchListener(new View.OnTouchListener()
    {
      private float newX = 0.0F;
      private float oldX = 0.0F;
      private float sens = 5.0F;
      
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        }
        for (;;)
        {
          return false;
          this.oldX = paramAnonymousMotionEvent.getX();
          return true;
          this.newX = paramAnonymousMotionEvent.getX();
          if (Math.abs(this.oldX - this.newX) < this.sens)
          {
            if (GalleryActivity.this.getActionBar().isShowing()) {
              GalleryActivity.this.getActionBar().hide();
            }
            for (;;)
            {
              GalleryActivity.this.mSystemUiHider.toggle();
              return true;
              GalleryActivity.this.getActionBar().show();
            }
          }
          this.oldX = 0.0F;
          this.newX = 0.0F;
        }
      }
    });
    this.mSystemUiHider = SystemUiHider.getInstance(this, this.contentView, 1);
    this.mSystemUiHider.setup();
    this.mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener()
    {
      int mShortAnimTime;
      
      public void onVisibilityChange(boolean paramAnonymousBoolean)
      {
        int i = GalleryActivity.this.controlsView.getHeight();
        if (this.mShortAnimTime == 0) {
          this.mShortAnimTime = GalleryActivity.this.getResources().getInteger(17694720);
        }
        GalleryActivity.this.controlsView.setVisibility(0);
        ViewPropertyAnimator localViewPropertyAnimator = GalleryActivity.this.controlsView.animate();
        if (paramAnonymousBoolean) {
          i = 0;
        }
        localViewPropertyAnimator.translationY(i).setDuration(this.mShortAnimTime);
        if (paramAnonymousBoolean) {
          GalleryActivity.this.delayedHide(3000);
        }
      }
    });
    Object localObject = getIntent().getStringExtra("category");
    if (localObject != null)
    {
      try
      {
        localObject = URLEncoder.encode(getIntent().getStringExtra("category"), "UTF-8");
        String str = ((String)localObject).replace("+", "%20");
        localObject = str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          Log.e("GalleryActivity", Log.getStackTraceString(localUnsupportedEncodingException));
        }
      }
      this.wallpaperDownloader = new WallpaperDownloadTask();
      this.wallpaperDownloader.execute(new String[] { localObject });
      return;
    }
    ArrayList localArrayList = getIntent().getParcelableArrayListExtra("images");
    this.adapter.setData(localArrayList);
    refreshControls(0);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131623942, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099778: 
      setWallpaper();
      return true;
    case 2131099779: 
      downloadImage();
      return true;
    }
    viewInNative();
    return true;
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    delayedHide(1000);
  }
  
  protected void onStart()
  {
    super.onStart();
    registerReceiver(this.receiver, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"));
  }
  
  protected void onStop()
  {
    super.onStop();
    unregisterReceiver(this.receiver);
    if (this.wallpaperDownloader != null) {
      this.wallpaperDownloader.cancel(true);
    }
  }
  
  public class WallpaperDownloadTask
    extends AsyncTask<String, Void, List<GalleryImage>>
  {
    private static final String GALLERY_URL = "http://m.androidcentral.com/mobile_app/wallpapers/";
    
    public WallpaperDownloadTask() {}
    
    protected List<GalleryImage> doInBackground(String... paramVarArgs)
    {
      String str = NetUtils.get("http://m.androidcentral.com/mobile_app/wallpapers/" + paramVarArgs[0] + "?sort=downloaded");
      if (str == null) {
        return null;
      }
      Gson localGson = new Gson();
      JsonParser localJsonParser = new JsonParser();
      try
      {
        JsonElement localJsonElement = localJsonParser.parse(str);
        (List)localGson.fromJson(localJsonElement.getAsJsonObject().get("wallpapers"), new TypeToken() {}.getType());
      }
      catch (JsonSyntaxException localJsonSyntaxException) {}
      return null;
    }
    
    protected void onPostExecute(List<GalleryImage> paramList)
    {
      if (paramList != null)
      {
        GalleryActivity.this.adapter.setData(paramList);
        GalleryActivity.this.galleryPager.setCurrentItem(0);
        GalleryActivity.this.refreshControls(0);
        return;
      }
      Toast.makeText(GalleryActivity.this, 2131165211, 0).show();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.GalleryActivity
 * JD-Core Version:    0.7.0.1
 */