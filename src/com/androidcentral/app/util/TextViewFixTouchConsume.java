package com.androidcentral.app.util;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextViewFixTouchConsume
  extends TextView
{
  boolean dontConsumeNonUrlClicks = true;
  boolean linkHit;
  
  public TextViewFixTouchConsume(Context paramContext)
  {
    super(paramContext);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.linkHit = false;
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (this.dontConsumeNonUrlClicks) {
      bool = this.linkHit;
    }
    return bool;
  }
  
  public static class LocalLinkMovementMethod
    extends LinkMovementMethod
  {
    static LocalLinkMovementMethod sInstance;
    
    public static LocalLinkMovementMethod getInstance()
    {
      if (sInstance == null) {
        sInstance = new LocalLinkMovementMethod();
      }
      return sInstance;
    }
    
    public boolean onTouchEvent(TextView paramTextView, Spannable paramSpannable, MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      if ((i == 1) || (i == 0))
      {
        int j = (int)paramMotionEvent.getX();
        int k = (int)paramMotionEvent.getY();
        int m = j - paramTextView.getTotalPaddingLeft();
        int n = k - paramTextView.getTotalPaddingTop();
        int i1 = m + paramTextView.getScrollX();
        int i2 = n + paramTextView.getScrollY();
        Layout localLayout = paramTextView.getLayout();
        int i3 = localLayout.getOffsetForHorizontal(localLayout.getLineForVertical(i2), i1);
        ClickableSpan[] arrayOfClickableSpan = (ClickableSpan[])paramSpannable.getSpans(i3, i3, ClickableSpan.class);
        if (arrayOfClickableSpan.length != 0)
        {
          if (i == 1) {}
          for (;;)
          {
            try
            {
              arrayOfClickableSpan[0].onClick(paramTextView);
              if ((paramTextView instanceof TextViewFixTouchConsume)) {
                ((TextViewFixTouchConsume)paramTextView).linkHit = true;
              }
              return true;
            }
            catch (Exception localException)
            {
              return true;
            }
            if (i == 0) {
              Selection.setSelection(paramSpannable, paramSpannable.getSpanStart(arrayOfClickableSpan[0]), paramSpannable.getSpanEnd(arrayOfClickableSpan[0]));
            }
          }
        }
        Selection.removeSelection(paramSpannable);
        Touch.onTouchEvent(paramTextView, paramSpannable, paramMotionEvent);
        return false;
      }
      return Touch.onTouchEvent(paramTextView, paramSpannable, paramMotionEvent);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.TextViewFixTouchConsume
 * JD-Core Version:    0.7.0.1
 */