package com.androidcentral.app.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ForumData
{
  private static ForumData instance = null;
  private List<ForumEntry> forumsList = new ArrayList();
  
  public static ForumData getInstance()
  {
    if (instance == null) {
      instance = new ForumData();
    }
    return instance;
  }
  
  public void addAll(List<ForumEntry> paramList)
  {
    this.forumsList.addAll(paramList);
  }
  
  public void clear()
  {
    this.forumsList.clear();
  }
  
  public List<ForumEntry> getForumsList(int paramInt)
  {
    ArrayList localArrayList = new ArrayList(this.forumsList.size());
    Iterator localIterator = this.forumsList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      ForumEntry localForumEntry = (ForumEntry)localIterator.next();
      if (localForumEntry.parentId == paramInt) {
        localArrayList.add(localForumEntry);
      }
    }
  }
  
  public List<ForumEntry> getSortedForumsList()
  {
    ArrayList localArrayList = new ArrayList(this.forumsList);
    Collections.sort(localArrayList);
    return localArrayList;
  }
  
  public boolean isForumsListLoaded()
  {
    return !this.forumsList.isEmpty();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumData
 * JD-Core Version:    0.7.0.1
 */