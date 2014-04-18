package com.androidcentral.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.Article.CommentThread;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.fragments.ArticleViewFragment;
import com.androidcentral.app.fragments.TMThreadDialogFragment.ThreadSelectListener;
import com.androidcentral.app.util.UiUtils;
import com.espian.showcaseview.OnShowcaseEventListener;
import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseView.ConfigOptions;
import com.espian.showcaseview.targets.PointTarget;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;

public class ArticlePagerActivity
  extends DrawerActivity
  implements TMThreadDialogFragment.ThreadSelectListener
{
  public static final String EXTRA_NID = "nid";
  public static final String EXTRA_SECTION_NAME = "sectionName";
  public static final String EXTRA_SECTION_TITLE = "sectionTitle";
  public static final String TAG = "ArticlePagerActivity";
  private ArticlePagerAdapter articleAdapter;
  private ViewPager articlePager;
  private NewsDataSource dataSource;
  private Set<Long> visitedArticles;
  
  private void evaluateLaunchIntent()
  {
    Uri localUri = getIntent().getData();
    String str;
    if (localUri == null)
    {
      this.articleAdapter.nid = getIntent().getLongExtra("nid", 0L);
      str = getIntent().getStringExtra("sectionTitle");
      this.articleAdapter.enablePaging(getIntent().getStringExtra("sectionName"));
      this.dataSource.markAsRead(this.articleAdapter.nid);
      this.visitedArticles.add(Long.valueOf(this.articleAdapter.nid));
      updateReturnResult();
    }
    for (;;)
    {
      setTitle(str);
      updateTitleAppearance();
      return;
      this.articleAdapter.permaLink = localUri.getPath().substring(1);
      str = "ALL";
    }
  }
  
  private long[] getVisitedArray()
  {
    return ArrayUtils.toPrimitive((Long[])this.visitedArticles.toArray(new Long[this.visitedArticles.size()]));
  }
  
  private void showSwipeShowcase()
  {
    SharedPreferences localSharedPreferences = getSharedPreferences("AndroidCentralPrefs", 0);
    if (!localSharedPreferences.getBoolean("article_swipe_showcase", false))
    {
      this.articlePager.setAlpha(0.05F);
      ShowcaseView.ConfigOptions localConfigOptions = new ShowcaseView.ConfigOptions();
      localConfigOptions.hideOnClickOutside = true;
      Point localPoint = UiUtils.getDisplayPixels(this);
      ShowcaseView localShowcaseView = ShowcaseView.insertShowcaseView(new PointTarget(300 + localPoint.x, 300 + localPoint.y), this, "Did you know?", "You can now swipe left and right to navigate between articles.", localConfigOptions);
      localShowcaseView.setOnShowcaseEventListener(new OnShowcaseEventListener()
      {
        public void onShowcaseViewDidHide(ShowcaseView paramAnonymousShowcaseView)
        {
          ArticlePagerActivity.this.articlePager.setAlpha(1.0F);
        }
        
        public void onShowcaseViewHide(ShowcaseView paramAnonymousShowcaseView) {}
        
        public void onShowcaseViewShow(ShowcaseView paramAnonymousShowcaseView) {}
      });
      localShowcaseView.animateGesture(localPoint.x, localPoint.y / 2, 0.0F, localPoint.y / 2);
      localSharedPreferences.edit().putBoolean("article_swipe_showcase", true).commit();
    }
  }
  
  private void updateReturnResult()
  {
    if (getIntent().getData() == null)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("visited_articles", getVisitedArray());
      setResult(-1, localIntent);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle, 2130903041);
    this.dataSource = NewsDataSource.getInstance(this);
    if (paramBundle != null)
    {
      long[] arrayOfLong = paramBundle.getLongArray("visited_articles");
      this.visitedArticles = new HashSet();
      Collections.addAll(this.visitedArticles, ArrayUtils.toObject(arrayOfLong));
    }
    for (;;)
    {
      this.articlePager = ((ViewPager)findViewById(2131099663));
      this.articlePager.setOffscreenPageLimit(2);
      this.articlePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
      {
        public void onPageScrollStateChanged(int paramAnonymousInt) {}
        
        public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {}
        
        public void onPageSelected(int paramAnonymousInt)
        {
          long l = ArticlePagerActivity.this.articleAdapter.getDataItemAtPosition(paramAnonymousInt);
          ArticlePagerActivity.this.dataSource.markAsRead(l);
          ArticlePagerActivity.this.visitedArticles.add(Long.valueOf(l));
          ArticlePagerActivity.this.updateReturnResult();
        }
      });
      showSwipeShowcase();
      this.articleAdapter = new ArticlePagerAdapter(getSupportFragmentManager());
      evaluateLaunchIntent();
      this.articlePager.setAdapter(this.articleAdapter);
      this.articlePager.setCurrentItem(this.articleAdapter.getStartIndex());
      return;
      this.visitedArticles = new HashSet();
    }
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putLongArray("visited_articles", getVisitedArray());
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onThreadSelected(Article.CommentThread paramCommentThread)
  {
    ((ArticleViewFragment)this.articleAdapter.instantiateItem(this.articlePager, this.articlePager.getCurrentItem())).launchCommentsActivity(paramCommentThread);
  }
  
  class ArticlePagerAdapter
    extends FragmentStatePagerAdapter
  {
    private List<Article> articles;
    private long nid = -1L;
    private String permaLink = null;
    private int startIndx = -1;
    
    public ArticlePagerAdapter(FragmentManager paramFragmentManager)
    {
      super();
    }
    
    private void enablePaging(String paramString)
    {
      this.articles = NewsDataSource.getInstance(ArticlePagerActivity.this).getArticles(paramString, 100);
      for (int i = 0;; i++)
      {
        if (i >= this.articles.size()) {
          return;
        }
        if (((Article)this.articles.get(i)).nid == this.nid)
        {
          this.startIndx = i;
          return;
        }
      }
    }
    
    public int getCount()
    {
      if (this.startIndx != -1) {
        return this.articles.size();
      }
      return 1;
    }
    
    public long getDataItemAtPosition(int paramInt)
    {
      return ((Article)this.articles.get(paramInt)).nid;
    }
    
    public Fragment getItem(int paramInt)
    {
      if (this.nid != -1L) {
        return ArticleViewFragment.newInstance(((Article)this.articles.get(paramInt)).nid);
      }
      return ArticleViewFragment.newInstance(this.permaLink);
    }
    
    public int getStartIndex()
    {
      if (this.startIndx != -1) {
        return this.startIndx;
      }
      return 0;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ArticlePagerActivity
 * JD-Core Version:    0.7.0.1
 */