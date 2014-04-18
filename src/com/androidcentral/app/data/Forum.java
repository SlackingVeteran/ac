package com.androidcentral.app.data;

import java.util.ArrayList;
import java.util.List;

public class Forum
{
  public boolean isSubscribed = false;
  public int newThreadCount = 0;
  public List<ThreadInfo> threads = new ArrayList();
  public int totalThreadCount = 0;
  
  public static class ThreadInfo
  {
    public String authorName;
    public String forumId;
    public String forumName;
    public String id;
    public int numReplies;
    public String title;
    public boolean unread;
    
    public String toString()
    {
      return this.title;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.Forum
 * JD-Core Version:    0.7.0.1
 */