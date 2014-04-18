package com.androidcentral.app.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class URLDrawable
  extends BitmapDrawable
{
  protected Drawable drawable;
  
  public URLDrawable(Resources paramResources, Bitmap paramBitmap)
  {
    super(paramResources, paramBitmap);
    this.drawable = new BitmapDrawable(paramResources, paramBitmap);
    this.drawable.setBounds(0, 0, this.drawable.getIntrinsicWidth(), this.drawable.getIntrinsicHeight());
    setBounds(0, 0, this.drawable.getIntrinsicWidth(), this.drawable.getIntrinsicHeight());
  }
  
  public void draw(Canvas paramCanvas)
  {
    this.drawable.draw(paramCanvas);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.URLDrawable
 * JD-Core Version:    0.7.0.1
 */