package com.androidcentral.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;
import java.net.HttpURLConnection;
import java.net.URL;

public class InlineImageGetter
  implements Html.ImageGetter
{
  private TextView container;
  private Context context;
  private URLDrawable urlDrawable;
  
  public InlineImageGetter(TextView paramTextView, Context paramContext)
  {
    this.container = paramTextView;
    this.context = paramContext;
  }
  
  public Drawable getDrawable(String paramString)
  {
    Bitmap localBitmap = BitmapFactory.decodeResource(this.context.getResources(), 2130837556);
    this.urlDrawable = new URLDrawable(this.context.getResources(), localBitmap);
    new ImageGetterAsyncTask().execute(new String[] { paramString });
    return this.urlDrawable;
  }
  
  public class ImageGetterAsyncTask
    extends AsyncTask<String, Void, Drawable>
  {
    private static final String TAG = "ImageGetterAsyncTask";
    
    public ImageGetterAsyncTask() {}
    
    protected Drawable doInBackground(String... paramVarArgs)
    {
      HttpURLConnection localHttpURLConnection = null;
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(paramVarArgs[0]).openConnection();
        Bitmap localBitmap = BitmapFactory.decodeStream(localHttpURLConnection.getInputStream());
        localBitmap.setDensity(0);
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(InlineImageGetter.this.context.getResources(), localBitmap);
        localBitmapDrawable.setBounds(0, 0, 0 + localBitmapDrawable.getIntrinsicWidth(), 0 + localBitmapDrawable.getIntrinsicHeight());
        return localBitmapDrawable;
      }
      catch (Exception localException)
      {
        Log.e("ImageGetterAsyncTask", Log.getStackTraceString(localException));
        return null;
      }
      finally
      {
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
      }
    }
    
    protected void onPostExecute(Drawable paramDrawable)
    {
      if (paramDrawable == null) {
        return;
      }
      InlineImageGetter.this.urlDrawable.setBounds(0, 0, 0 + paramDrawable.getIntrinsicWidth(), 0 + paramDrawable.getIntrinsicHeight());
      InlineImageGetter.this.urlDrawable.drawable = paramDrawable;
      InlineImageGetter.this.container.setText(InlineImageGetter.this.container.getText());
      InlineImageGetter.this.container.invalidate();
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.InlineImageGetter
 * JD-Core Version:    0.7.0.1
 */