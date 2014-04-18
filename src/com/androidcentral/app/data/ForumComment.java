package com.androidcentral.app.data;

import android.text.Html;
import android.text.Spanned;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForumComment
{
  public List<String> attachments = new ArrayList();
  public String authorId;
  public String authorName;
  public String avatarUrl;
  public boolean canEdit;
  public String content;
  public int likeCount;
  public String postId;
  public int thankCount;
  public String timestamp;
  
  public String getContentWithAttachments()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.content);
    localStringBuilder.append("\n");
    Iterator localIterator = this.attachments.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localStringBuilder.toString();
      }
      String str = (String)localIterator.next();
      localStringBuilder.append("[img]");
      localStringBuilder.append(str);
      localStringBuilder.append("[/img]");
    }
  }
  
  public void setContent(String paramString)
  {
    this.content = Html.fromHtml(paramString).toString().trim();
  }
  
  public String toString()
  {
    return this.content;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumComment
 * JD-Core Version:    0.7.0.1
 */