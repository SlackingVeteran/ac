package com.androidcentral.app.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Article
{
  private static final Pattern GALLERY_PATTERN = Pattern.compile("\\<img alt\\=\\\"(.+?)\\\" src=\\\"(.+?)\\\"", 34);
  @SerializedName("app_id")
  public String appId;
  @SerializedName("audio")
  public String audio;
  @SerializedName("author")
  public String author;
  @SerializedName("comment_count")
  public int commentCount;
  @SerializedName("comment_server")
  public String commentServer;
  @SerializedName("comment_threads")
  public CommentThread[] commentThreads;
  @SerializedName("content")
  public String content;
  public transient String heroImage;
  @SerializedName("images")
  public String[] images;
  @SerializedName("internalLink")
  public String internalLink;
  @SerializedName("modified_date")
  public long modifiedDate;
  @SerializedName("nid")
  public long nid;
  @SerializedName("permaLink")
  public String permaLink;
  @SerializedName("published_date")
  public long publishedDate;
  public transient boolean readStatus;
  @SerializedName("section")
  public String[] sections;
  @SerializedName("sticky")
  public int sticky;
  @SerializedName("title")
  public String title;
  @SerializedName("type")
  public String type;
  
  private static void rewriteImageUrls(ArrayList<GalleryImage> paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      GalleryImage localGalleryImage = (GalleryImage)localIterator.next();
      localGalleryImage.setImageUrl(localGalleryImage.getImageUrl().replace("/thumbnail/", "/xlarge/"));
    }
  }
  
  public boolean allowComments()
  {
    return !this.author.equals("ShopAndroid.com");
  }
  
  public CommentThread findCommentThread(String paramString)
  {
    CommentThread[] arrayOfCommentThread = this.commentThreads;
    int i = arrayOfCommentThread.length;
    for (int j = 0;; j++)
    {
      CommentThread localCommentThread;
      if (j >= i) {
        localCommentThread = null;
      }
      do
      {
        return localCommentThread;
        localCommentThread = arrayOfCommentThread[j];
      } while (localCommentThread.title.equals(paramString));
    }
  }
  
  public ContentValues getArticlesCVs()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("nid", Long.valueOf(this.nid));
    localContentValues.put("publishedDate", Long.valueOf(this.publishedDate));
    localContentValues.put("modifiedDate", Long.valueOf(this.modifiedDate));
    localContentValues.put("sticky", Integer.valueOf(this.sticky));
    localContentValues.put("commentCount", Integer.valueOf(this.commentCount));
    localContentValues.put("author", this.author);
    localContentValues.put("title", this.title);
    localContentValues.put("internalLink", this.internalLink);
    localContentValues.put("permaLink", this.permaLink);
    localContentValues.put("heroImage", getHeroImage());
    localContentValues.put("readStatus", Integer.valueOf(0));
    localContentValues.put("type", this.type);
    String str1;
    if (this.audio != null)
    {
      str1 = this.audio;
      localContentValues.put("audio", str1);
      if (this.appId == null) {
        break label189;
      }
    }
    label189:
    for (String str2 = this.appId;; str2 = "")
    {
      localContentValues.put("appId", str2);
      return localContentValues;
      str1 = "";
      break;
    }
  }
  
  public ContentValues getContentCV()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("nid_rel", Long.valueOf(this.nid));
    localContentValues.put("content", this.content);
    return localContentValues;
  }
  
  public String getDisplayableContent()
  {
    if (!hasMediaGallery()) {
      return this.content;
    }
    int i = this.content.indexOf("<div class=\"media-gallery\">");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.content.substring(0, i));
    ArrayList localArrayList = getMediaGalleryImages();
    localStringBuilder.append("<a class='gallery' href='_fullscreen'>");
    localStringBuilder.append("<span></span>");
    localStringBuilder.append("<img src=\"" + ((GalleryImage)localArrayList.get(0)).getImageUrl() + "\" />");
    localStringBuilder.append("</a>");
    localStringBuilder.append(this.content.substring(i));
    return localStringBuilder.toString();
  }
  
  public String getHeroImage()
  {
    if ((this.images != null) && (this.images.length > 0)) {
      return this.images[0];
    }
    return "";
  }
  
  public ArrayList<GalleryImage> getMediaGalleryImages()
  {
    int i = this.content.indexOf("<div class=\"media-gallery\">");
    int j = this.content.indexOf("</div>", i);
    ArrayList localArrayList = new ArrayList();
    Matcher localMatcher = GALLERY_PATTERN.matcher(this.content).region(i, j);
    for (;;)
    {
      if (!localMatcher.find())
      {
        rewriteImageUrls(localArrayList);
        return localArrayList;
      }
      localArrayList.add(new GalleryImage(localMatcher.group(2), localMatcher.group(1)));
    }
  }
  
  public String getPublishedAgo()
  {
    long l = System.currentTimeMillis() / 1000L - this.publishedDate;
    if (l < 3600L) {
      return Math.round((float)l / 60.0F) + "m";
    }
    if (l < 86400L) {
      return Math.round((float)l / 3600.0F) + "h";
    }
    return Math.round((float)l / 86400.0F) + "d";
  }
  
  public ContentValues[] getSectionsCVs()
  {
    ContentValues[] arrayOfContentValues = new ContentValues[this.sections.length];
    for (int i = 0;; i++)
    {
      if (i >= this.sections.length) {
        return arrayOfContentValues;
      }
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("nid_rel", Long.valueOf(this.nid));
      localContentValues.put("section", this.sections[i]);
      arrayOfContentValues[i] = localContentValues;
    }
  }
  
  public boolean hasMediaGallery()
  {
    return this.content.contains("<div class=\"media-gallery\">");
  }
  
  public boolean isPodcast()
  {
    return this.type.equals("podcast");
  }
  
  public boolean isTalkMobile()
  {
    return this.type.equals("talk_mobile");
  }
  
  public String toString()
  {
    return this.title;
  }
  
  public static class CommentThread
    implements Parcelable
  {
    public static final Parcelable.Creator<CommentThread> CREATOR = new Parcelable.Creator()
    {
      public Article.CommentThread createFromParcel(Parcel paramAnonymousParcel)
      {
        return new Article.CommentThread(paramAnonymousParcel, null);
      }
      
      public Article.CommentThread[] newArray(int paramAnonymousInt)
      {
        return new Article.CommentThread[paramAnonymousInt];
      }
    };
    @SerializedName("comment_count")
    public int commentCount;
    @SerializedName("nid")
    public long nid;
    @SerializedName("title")
    public String title;
    
    private CommentThread(Parcel paramParcel)
    {
      this.title = paramParcel.readString();
      this.commentCount = paramParcel.readInt();
      this.nid = paramParcel.readLong();
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public String toString()
    {
      return this.title;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.title);
      paramParcel.writeInt(this.commentCount);
      paramParcel.writeLong(this.nid);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.Article
 * JD-Core Version:    0.7.0.1
 */