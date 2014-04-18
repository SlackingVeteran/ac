package com.androidcentral.app.util;

public class TextUtils
{
  public static CharSequence trimTrailingWhitespace(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {
      return "";
    }
    int i = paramCharSequence.length();
    do
    {
      i--;
    } while ((i >= 0) && (Character.isWhitespace(paramCharSequence.charAt(i))));
    return paramCharSequence.subSequence(0, i + 1);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.util.TextUtils
 * JD-Core Version:    0.7.0.1
 */