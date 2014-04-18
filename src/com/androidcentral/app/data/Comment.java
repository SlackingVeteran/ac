package com.androidcentral.app.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Comment
{
  @SerializedName("avatar_url")
  public String avatarUrl;
  @SerializedName("cid")
  public String cid;
  @SerializedName("content")
  public String content;
  public int nestedLevel;
  @SerializedName("postdate")
  public String postdate;
  @SerializedName("replies")
  public List<Comment> replies;
  public CharSequence spannedContent;
  @SerializedName("uid")
  public String uid;
  @SerializedName("username")
  public String username;
  
  public String getAvatarUrl()
  {
    if (this.avatarUrl != null) {
      return this.avatarUrl;
    }
    return "http://cdn-forums.androidcentral.com/avatars/d_ac/avatar" + this.uid + "_1.gif";
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.Comment
 * JD-Core Version:    0.7.0.1
 */