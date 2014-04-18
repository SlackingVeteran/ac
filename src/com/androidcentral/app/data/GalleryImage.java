package com.androidcentral.app.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.Spanned;
import com.google.gson.annotations.SerializedName;

public class GalleryImage
  implements Parcelable
{
  public static final Parcelable.Creator<GalleryImage> CREATOR = new Parcelable.Creator()
  {
    public GalleryImage createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GalleryImage(paramAnonymousParcel);
    }
    
    public GalleryImage[] newArray(int paramAnonymousInt)
    {
      return new GalleryImage[paramAnonymousInt];
    }
  };
  public static final String SCALE_PRESET = "w300h600";
  @SerializedName("content")
  public String description;
  @SerializedName("images")
  public String[] imageUrls;
  
  public GalleryImage(Parcel paramParcel)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramParcel.readString();
    this.imageUrls = arrayOfString;
    this.description = paramParcel.readString();
  }
  
  public GalleryImage(String paramString1, String paramString2)
  {
    this.imageUrls = new String[] { paramString1 };
    this.description = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getContent()
  {
    return Html.fromHtml(this.description).toString().trim();
  }
  
  public String getImageUrl()
  {
    return this.imageUrls[0];
  }
  
  public String getScaleImage(int paramInt)
  {
    return this.imageUrls[0].replace("/files/", "/files/imagecache/w300h600/");
  }
  
  public void setImageUrl(String paramString)
  {
    this.imageUrls[0] = paramString;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.imageUrls[0]);
    paramParcel.writeString(this.description);
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.data.GalleryImage
 * JD-Core Version:    0.7.0.1
 */