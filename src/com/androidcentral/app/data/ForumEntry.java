package com.androidcentral.app.data;

import java.util.List;

public class ForumEntry
  implements Comparable<ForumEntry>
{
  public boolean canSubscribe;
  public List<ForumEntry> children;
  public int id;
  public String name;
  public int parentId;
  public boolean subOnly;
  public String url;
  
  public int compareTo(ForumEntry paramForumEntry)
  {
    return this.name.compareToIgnoreCase(paramForumEntry.name);
  }
  
  public String getLogoUrl()
  {
    return "http://forums.androidcentral.com/images/forum_icons_fids/" + this.id + ".png";
  }
  
  public String toString()
  {
    return this.name;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.ForumEntry
 * JD-Core Version:    0.7.0.1
 */