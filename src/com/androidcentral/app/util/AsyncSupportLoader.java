package com.androidcentral.app.util;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncSupportLoader<D>
  extends AsyncTaskLoader<D>
{
  private D data;
  
  public AsyncSupportLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  public void deliverResult(D paramD)
  {
    if (isReset()) {
      return;
    }
    this.data = paramD;
    super.deliverResult(paramD);
  }
  
  protected void onReset()
  {
    super.onReset();
    onStopLoading();
    this.data = null;
  }
  
  protected void onStartLoading()
  {
    if (this.data != null) {
      deliverResult(this.data);
    }
    if ((takeContentChanged()) || (this.data == null)) {
      forceLoad();
    }
  }
  
  protected void onStopLoading()
  {
    cancelLoad();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.AsyncSupportLoader
 * JD-Core Version:    0.7.0.1
 */