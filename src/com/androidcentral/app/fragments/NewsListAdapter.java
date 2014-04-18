package com.androidcentral.app.fragments;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

class NewsListAdapter
  extends BaseAdapter
{
  private List<Article> articles;
  private Cursor cursor;
  private NewsDataSource dataSource;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  
  public NewsListAdapter(Context paramContext)
  {
    this.dataSource = NewsDataSource.getInstance(paramContext);
    this.inflater = LayoutInflater.from(paramContext);
    this.imageLoader = ImageLoader.getInstance();
  }
  
  public void changeCursor(Cursor paramCursor)
  {
    if (paramCursor != this.cursor) {
      if (paramCursor != null) {
        this.articles = new ArrayList(paramCursor.getCount());
      }
    }
    for (int i = 0;; i++)
    {
      if (i >= paramCursor.getCount())
      {
        notifyDataSetChanged();
        if (this.cursor != null) {
          this.cursor.close();
        }
        this.cursor = paramCursor;
        return;
      }
      this.articles.add(this.dataSource.articleFromCursor(paramCursor, i, false));
    }
  }
  
  public List<Article> getArticles()
  {
    return this.articles;
  }
  
  public int getCount()
  {
    if (this.articles != null) {
      return this.articles.size();
    }
    return 0;
  }
  
  public Object getItem(int paramInt)
  {
    return this.articles.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    Article localArticle;
    TextView localTextView;
    if (paramView == null)
    {
      localViewHolder = new ViewHolder(null);
      paramView = this.inflater.inflate(2130903075, paramViewGroup, false);
      localViewHolder.titleView = ((TextView)paramView.findViewById(2131099732));
      localViewHolder.authorView = ((TextView)paramView.findViewById(2131099733));
      localViewHolder.commentView = ((TextView)paramView.findViewById(2131099734));
      localViewHolder.dateView = ((TextView)paramView.findViewById(2131099735));
      localViewHolder.heroImageView = ((ImageView)paramView.findViewById(2131099731));
      paramView.setTag(localViewHolder);
      localArticle = (Article)getItem(paramInt);
      localTextView = localViewHolder.titleView;
      if (!localArticle.readStatus) {
        break label246;
      }
    }
    label246:
    for (int i = 0;; i = 1)
    {
      localTextView.setTypeface(null, i);
      localViewHolder.titleView.setText(localArticle.title);
      this.imageLoader.displayImage(localArticle.heroImage, localViewHolder.heroImageView);
      if (!localArticle.author.equals("ShopAndroid.com")) {
        break label252;
      }
      localViewHolder.authorView.setText(localArticle.author);
      localViewHolder.commentView.setText("");
      localViewHolder.dateView.setText(" | " + localArticle.getPublishedAgo());
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
      break;
    }
    label252:
    if (localArticle.commentCount >= 1000) {}
    for (String str1 = "1k+";; str1 = String.valueOf(localArticle.commentCount))
    {
      String str2 = str1 + " COMMENTS";
      localViewHolder.authorView.setText(localArticle.author + " | ");
      localViewHolder.commentView.setText(str2);
      break;
    }
  }
  
  private static class ViewHolder
  {
    public TextView authorView;
    public TextView commentView;
    public TextView dateView;
    public ImageView heroImageView;
    public TextView titleView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.NewsListAdapter
 * JD-Core Version:    0.7.0.1
 */