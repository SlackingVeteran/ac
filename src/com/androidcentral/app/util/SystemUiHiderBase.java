package com.androidcentral.app.util;

import android.app.Activity;
import android.view.View;
import android.view.Window;

public class SystemUiHiderBase
  extends SystemUiHider
{
  private boolean mVisible = true;
  
  protected SystemUiHiderBase(Activity paramActivity, View paramView, int paramInt)
  {
    super(paramActivity, paramView, paramInt);
  }
  
  public void hide()
  {
    if ((0x2 & this.mFlags) != 0) {
      this.mActivity.getWindow().setFlags(1024, 1024);
    }
    this.mOnVisibilityChangeListener.onVisibilityChange(false);
    this.mVisible = false;
  }
  
  public boolean isVisible()
  {
    return this.mVisible;
  }
  
  public void setup()
  {
    if ((0x1 & this.mFlags) == 0) {
      this.mActivity.getWindow().setFlags(768, 768);
    }
  }
  
  public void show()
  {
    if ((0x2 & this.mFlags) != 0) {
      this.mActivity.getWindow().setFlags(0, 1024);
    }
    this.mOnVisibilityChangeListener.onVisibilityChange(true);
    this.mVisible = true;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.SystemUiHiderBase
 * JD-Core Version:    0.7.0.1
 */