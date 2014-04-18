package com.androidcentral.app;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcentral.app.data.UserProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import java.text.DateFormat;
import java.util.Date;

public class ViewProfileActivity
  extends BaseActivity
  implements LoaderManager.LoaderCallbacks<UserProfile>
{
  private String authorId;
  private ImageView avatarImage;
  private DisplayImageOptions displayOptions;
  private ImageLoader imageLoader;
  private TextView memberSinceText;
  private TextView postCountText;
  private TextView usernameText;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903054);
    this.authorId = getIntent().getStringExtra("post_author_id");
    this.avatarImage = ((ImageView)findViewById(2131099689));
    this.usernameText = ((TextView)findViewById(2131099690));
    this.postCountText = ((TextView)findViewById(2131099691));
    this.memberSinceText = ((TextView)findViewById(2131099692));
    this.imageLoader = ImageLoader.getInstance();
    this.displayOptions = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(5)).build();
    getLoaderManager().initLoader(0, null, this);
  }
  
  public Loader<UserProfile> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new UserProfileLoader(this, this.authorId);
  }
  
  public void onLoadFinished(Loader<UserProfile> paramLoader, UserProfile paramUserProfile)
  {
    if (paramUserProfile != null)
    {
      this.usernameText.setText(paramUserProfile.username);
      this.postCountText.setText(paramUserProfile.postCount);
      if ((paramUserProfile.avatarUrl != null) && (!paramUserProfile.avatarUrl.isEmpty())) {
        this.imageLoader.displayImage(paramUserProfile.avatarUrl, this.avatarImage, this.displayOptions);
      }
      for (;;)
      {
        long l = 1000L * Long.parseLong(paramUserProfile.regDate);
        this.memberSinceText.setText(DateFormat.getDateInstance().format(new Date(l)));
        return;
        this.avatarImage.setImageResource(2130837539);
      }
    }
    Toast.makeText(this, 2131165211, 0).show();
  }
  
  public void onLoaderReset(Loader<UserProfile> paramLoader) {}
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.ViewProfileActivity
 * JD-Core Version:    0.7.0.1
 */