package com.androidcentral.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidcentral.app.data.Comment;
import com.androidcentral.app.util.InlineImageGetter;
import com.androidcentral.app.util.TextUtils;
import com.androidcentral.app.util.TextViewFixTouchConsume;
import com.androidcentral.app.util.TextViewFixTouchConsume.LocalLinkMovementMethod;
import com.androidcentral.app.util.UiUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

class CommentsAdapter
  extends ArrayAdapter<Comment>
{
  private static final int NEST_LEVEL_CAP = 5;
  private int BASE_PADDING;
  private int FONT_SIZE;
  private int PX_PER_NEST;
  private int TEXT_COLOR;
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  private PrettyTime prettyTime;
  
  public CommentsAdapter(Context paramContext)
  {
    super(paramContext, 2130903074);
    this.inflater = LayoutInflater.from(paramContext);
    this.prettyTime = new PrettyTime();
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().showStubImage(2130837529).displayer(new RoundedBitmapDisplayer(5)).cacheOnDisc().cacheInMemory().build();
    this.FONT_SIZE = getFontSize();
    this.BASE_PADDING = UiUtils.dpToPx(paramContext, 8);
    this.PX_PER_NEST = UiUtils.dpToPx(paramContext, 15);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(new int[] { 2130772014 });
    this.TEXT_COLOR = localTypedArray.getColor(0, -16777216);
    localTypedArray.recycle();
  }
  
  private int getFontSize()
  {
    int i = 18;
    String str = getContext().getSharedPreferences("AndroidCentralPrefs", 0).getString("pref_font_size", "");
    if (str.equals("Tiny")) {
      i = 14;
    }
    do
    {
      do
      {
        return i;
        if (str.equals("Small")) {
          return 16;
        }
      } while (str.equals("Medium"));
      if (str.equals("Large")) {
        return 20;
      }
    } while (!str.equals("Huge"));
    return 22;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    String str;
    int k;
    label255:
    int m;
    label377:
    int n;
    if (paramView == null)
    {
      paramView = this.inflater.inflate(2130903074, paramViewGroup, false);
      localViewHolder = new ViewHolder(null);
      localViewHolder.authorView = ((TextView)paramView.findViewById(2131099728));
      localViewHolder.whenView = ((TextView)paramView.findViewById(2131099729));
      localViewHolder.avatarView = ((ImageView)paramView.findViewById(2131099726));
      localViewHolder.borderView = ((ImageView)paramView.findViewById(2131099724));
      ViewGroup localViewGroup = (ViewGroup)paramView.findViewById(2131099727);
      TextViewFixTouchConsume localTextViewFixTouchConsume = new TextViewFixTouchConsume(getContext());
      localViewGroup.addView(localTextViewFixTouchConsume);
      localViewHolder.contentView = localTextViewFixTouchConsume;
      localViewHolder.contentView.setTextColor(this.TEXT_COLOR);
      localViewHolder.contentView.setTextSize(this.FONT_SIZE);
      localViewHolder.contentView.setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
      paramView.setTag(localViewHolder);
      Comment localComment = (Comment)getItem(paramInt);
      if (localComment.spannedContent == null)
      {
        localComment.spannedContent = Html.fromHtml(localComment.content, new InlineImageGetter(localViewHolder.contentView, getContext()), null);
        localComment.spannedContent = TextUtils.trimTrailingWhitespace(localComment.spannedContent);
      }
      int i = Math.min(localComment.nestedLevel, 5);
      int j = Math.max(0, -5 + localComment.nestedLevel);
      str = "";
      k = 0;
      if (k < j) {
        break label451;
      }
      localViewHolder.authorView.setText(str + localComment.username);
      localViewHolder.whenView.setText(this.prettyTime.format(new Date(1000L * Long.parseLong(localComment.postdate))));
      localViewHolder.contentView.setText(localComment.spannedContent);
      this.imageLoader.displayImage(localComment.getAvatarUrl(), localViewHolder.avatarView, this.displayOptions);
      ImageView localImageView = localViewHolder.borderView;
      if (i <= 0) {
        break label480;
      }
      m = 8;
      localImageView.setVisibility(m);
      n = this.BASE_PADDING + i * this.PX_PER_NEST;
      if (i <= 0) {
        break label486;
      }
    }
    label451:
    label480:
    label486:
    for (int i1 = 9;; i1 = 0)
    {
      int i2 = n + i1;
      int i3 = this.BASE_PADDING;
      int i4 = this.BASE_PADDING;
      paramView.setPadding(i2, i3, i4, 0);
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
      break;
      str = str + "> ";
      k++;
      break label255;
      m = 0;
      break label377;
    }
  }
  
  private static class ViewHolder
  {
    public TextView authorView;
    public ImageView avatarView;
    public ImageView borderView;
    public TextView contentView;
    public TextView whenView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.CommentsAdapter
 * JD-Core Version:    0.7.0.1
 */