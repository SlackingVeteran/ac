package com.androidcentral.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.androidcentral.app.data.ForumComment;
import com.androidcentral.app.util.TextViewFixTouchConsume;
import com.androidcentral.app.util.TextViewFixTouchConsume.LocalLinkMovementMethod;
import com.androidcentral.app.util.UiUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ocpsoft.prettytime.PrettyTime;

class CommentListAdapter
  extends ArrayAdapter<ForumComment>
{
  private static final Pattern IMG_PATTERN = Pattern.compile("\\[img[l|r]?\\](.+?)\\[/img[l|r]?\\]", 34);
  private static final Pattern QUOTE_PATTERN = Pattern.compile("\\[quote.*?\\](.+?)\\[/quote\\]", 34);
  private static final Pattern URL_PATTERN_1 = Pattern.compile("\\[url=\"?(.+?)\"?\\](.+?)\\[/url\\]", 34);
  private static final Pattern URL_PATTERN_2 = Pattern.compile("\\[url\\](.+?)\\[/url\\]", 34);
  private final int FONT_SIZE;
  private final int ICON_LIKE;
  private final int ICON_THANKS;
  private final int QUOTE_BORDER_COLOR;
  private final int QUOTE_BORDER_WIDTH;
  private final int QUOTE_TEXT_COLOR;
  private final int TEXT_COLOR;
  private final int TEXT_LINK_COLOR;
  private DisplayImageOptions avatarDisplayOptions;
  private DisplayImageOptions forumImageDisplayOptions;
  private ImageLoader imageLoader;
  private LayoutInflater inflater;
  private long lastPostId;
  private PrettyTime prettyTime;
  
  public CommentListAdapter(Context paramContext)
  {
    super(paramContext, 0);
    this.inflater = LayoutInflater.from(paramContext);
    this.prettyTime = new PrettyTime();
    this.imageLoader = ImageLoader.getInstance();
    this.lastPostId = -1L;
    this.avatarDisplayOptions = new DisplayImageOptions.Builder().showStubImage(2130837529).displayer(new RoundedBitmapDisplayer(5)).cacheOnDisc().cacheInMemory().build();
    this.forumImageDisplayOptions = new DisplayImageOptions.Builder().showStubImage(2130837556).cacheOnDisc().build();
    if (UiUtils.isNightTheme(paramContext))
    {
      this.QUOTE_TEXT_COLOR = paramContext.getResources().getColor(2131296289);
      this.TEXT_LINK_COLOR = paramContext.getResources().getColor(2131296287);
      this.TEXT_COLOR = paramContext.getResources().getColor(17170443);
      this.ICON_LIKE = 2130837552;
    }
    for (this.ICON_THANKS = 2130837554;; this.ICON_THANKS = 2130837555)
    {
      this.QUOTE_BORDER_WIDTH = UiUtils.dpToPx(paramContext, 3);
      this.QUOTE_BORDER_COLOR = paramContext.getResources().getColor(2131296282);
      this.FONT_SIZE = getFontSize();
      return;
      this.QUOTE_TEXT_COLOR = paramContext.getResources().getColor(2131296284);
      this.TEXT_LINK_COLOR = paramContext.getResources().getColor(2131296286);
      this.TEXT_COLOR = paramContext.getResources().getColor(17170444);
      this.ICON_LIKE = 2130837553;
    }
  }
  
  private void attachComment(String paramString, ViewGroup paramViewGroup)
  {
    Matcher localMatcher = QUOTE_PATTERN.matcher(paramString);
    for (int i = 0;; i = localMatcher.end())
    {
      if (!localMatcher.find())
      {
        if (i < paramString.length()) {
          attachForumContent(paramString.substring(i), paramViewGroup);
        }
        return;
      }
      if (localMatcher.start() > i) {
        attachForumContent(paramString.substring(i, localMatcher.start()), paramViewGroup);
      }
      paramViewGroup.addView(getQuoteView(localMatcher.group(1)));
    }
  }
  
  private void attachForumContent(String paramString, ViewGroup paramViewGroup)
  {
    Matcher localMatcher = IMG_PATTERN.matcher(paramString);
    for (int i = 0;; i = localMatcher.end())
    {
      if (!localMatcher.find())
      {
        if (i < paramString.length()) {
          paramViewGroup.addView(getTextView(paramString.substring(i)));
        }
        return;
      }
      if (localMatcher.start() > i) {
        paramViewGroup.addView(getTextView(paramString.substring(i, localMatcher.start())));
      }
      paramViewGroup.addView(getImageView(localMatcher.group(1)));
    }
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
  
  private SpannableStringBuilder getFormattedText(String paramString)
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramString);
    parseUrlType1(localSpannableStringBuilder);
    parseUrlType2(localSpannableStringBuilder);
    return localSpannableStringBuilder;
  }
  
  private ImageView getImageView(String paramString)
  {
    ImageView localImageView = new ImageView(getContext());
    localImageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.imageLoader.displayImage(paramString, localImageView, this.forumImageDisplayOptions);
    return localImageView;
  }
  
  private View getQuoteView(String paramString)
  {
    LinearLayout localLinearLayout = new LinearLayout(getContext());
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    localLinearLayout.setPadding(10, 0, 0, 0);
    ImageView localImageView = new ImageView(getContext());
    localImageView.setLayoutParams(new ViewGroup.LayoutParams(this.QUOTE_BORDER_WIDTH, -1));
    localImageView.setPadding(10, 0, 0, 0);
    localImageView.setBackgroundColor(this.QUOTE_BORDER_COLOR);
    localLinearLayout.addView(localImageView);
    TextViewFixTouchConsume localTextViewFixTouchConsume = new TextViewFixTouchConsume(getContext());
    localTextViewFixTouchConsume.setTextSize(this.FONT_SIZE);
    localTextViewFixTouchConsume.setPadding(10, 0, 0, 0);
    localTextViewFixTouchConsume.setTextColor(this.QUOTE_TEXT_COLOR);
    localTextViewFixTouchConsume.setText(getFormattedText(paramString));
    localTextViewFixTouchConsume.setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
    localLinearLayout.addView(localTextViewFixTouchConsume);
    return localLinearLayout;
  }
  
  private TextView getTextView(String paramString)
  {
    TextViewFixTouchConsume localTextViewFixTouchConsume = new TextViewFixTouchConsume(getContext());
    localTextViewFixTouchConsume.setText(getFormattedText(paramString));
    localTextViewFixTouchConsume.setTextSize(this.FONT_SIZE);
    localTextViewFixTouchConsume.setTextColor(this.TEXT_COLOR);
    localTextViewFixTouchConsume.setLinkTextColor(this.TEXT_LINK_COLOR);
    localTextViewFixTouchConsume.setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
    return localTextViewFixTouchConsume;
  }
  
  private void parseUrlType1(SpannableStringBuilder paramSpannableStringBuilder)
  {
    Matcher localMatcher = URL_PATTERN_1.matcher(paramSpannableStringBuilder);
    for (;;)
    {
      if (!localMatcher.find()) {
        return;
      }
      String str1 = localMatcher.group(1);
      String str2 = localMatcher.group(2);
      paramSpannableStringBuilder.replace(localMatcher.start(), localMatcher.end(), str2);
      paramSpannableStringBuilder.setSpan(new URLSpan(str1), localMatcher.start(), localMatcher.start() + str2.length(), 0);
      localMatcher.reset(paramSpannableStringBuilder);
    }
  }
  
  private void parseUrlType2(SpannableStringBuilder paramSpannableStringBuilder)
  {
    Matcher localMatcher = URL_PATTERN_2.matcher(paramSpannableStringBuilder);
    for (;;)
    {
      if (!localMatcher.find()) {
        return;
      }
      String str = localMatcher.group(1);
      paramSpannableStringBuilder.replace(localMatcher.start(), localMatcher.end(), str);
      paramSpannableStringBuilder.setSpan(new URLSpan(str), localMatcher.start(), localMatcher.start() + str.length(), 0);
      localMatcher.reset(paramSpannableStringBuilder);
    }
  }
  
  public String getLastPostId()
  {
    return String.valueOf(this.lastPostId);
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolder localViewHolder;
    ForumComment localForumComment;
    if (paramView == null)
    {
      paramView = this.inflater.inflate(2130903062, paramViewGroup, false);
      localViewHolder = new ViewHolder(null);
      localViewHolder.authorView = ((TextView)paramView.findViewById(2131099706));
      localViewHolder.whenView = ((TextView)paramView.findViewById(2131099707));
      localViewHolder.contentView = ((LinearLayout)paramView.findViewById(2131099708));
      localViewHolder.avatarView = ((ImageView)paramView.findViewById(2131099705));
      localViewHolder.likesView = ((TextView)paramView.findViewById(2131099703));
      localViewHolder.thanksView = ((TextView)paramView.findViewById(2131099704));
      localViewHolder.likesView.setCompoundDrawablesWithIntrinsicBounds(this.ICON_LIKE, 0, 0, 0);
      localViewHolder.likesView.setCompoundDrawablePadding(5);
      localViewHolder.thanksView.setCompoundDrawablesWithIntrinsicBounds(this.ICON_THANKS, 0, 0, 0);
      localViewHolder.thanksView.setCompoundDrawablePadding(5);
      paramView.setTag(localViewHolder);
      localForumComment = (ForumComment)getItem(paramInt);
      long l = Long.parseLong(localForumComment.postId);
      if (l > this.lastPostId) {
        this.lastPostId = l;
      }
      localViewHolder.authorView.setText(localForumComment.authorName);
      localViewHolder.whenView.setText(this.prettyTime.format(new Date(1000L * Long.parseLong(localForumComment.timestamp))));
      this.imageLoader.displayImage(localForumComment.avatarUrl, localViewHolder.avatarView, this.avatarDisplayOptions);
      if (localForumComment.likeCount <= 0) {
        break label377;
      }
      localViewHolder.likesView.setVisibility(0);
      localViewHolder.likesView.setText(String.valueOf(localForumComment.likeCount));
      label308:
      if (localForumComment.thankCount <= 0) {
        break label389;
      }
      localViewHolder.thanksView.setVisibility(0);
      localViewHolder.thanksView.setText(String.valueOf(localForumComment.thankCount));
    }
    for (;;)
    {
      localViewHolder.contentView.removeAllViews();
      attachComment(localForumComment.getContentWithAttachments(), localViewHolder.contentView);
      return paramView;
      localViewHolder = (ViewHolder)paramView.getTag();
      break;
      label377:
      localViewHolder.likesView.setVisibility(4);
      break label308;
      label389:
      localViewHolder.thanksView.setVisibility(4);
    }
  }
  
  private static class ViewHolder
  {
    public TextView authorView;
    public ImageView avatarView;
    public LinearLayout contentView;
    public TextView likesView;
    public TextView thanksView;
    public TextView whenView;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.CommentListAdapter
 * JD-Core Version:    0.7.0.1
 */