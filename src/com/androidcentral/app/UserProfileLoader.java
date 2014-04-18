package com.androidcentral.app;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import com.androidcentral.app.data.UserProfile;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.MobiquoHelper.Param;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.XmlRpcUtils;
import com.androidcentral.app.util.AsyncLoader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

class UserProfileLoader
  extends AsyncLoader<UserProfile>
{
  private static final String TAG = "UserProfileLoader";
  private String authorId;
  
  public UserProfileLoader(Context paramContext, String paramString)
  {
    super(paramContext);
    this.authorId = paramString;
  }
  
  private UserProfile parseResponse(String paramString)
  {
    if (paramString == null)
    {
      localUserProfile = null;
      return localUserProfile;
    }
    UserProfile localUserProfile = new UserProfile();
    for (;;)
    {
      XmlPullParser localXmlPullParser;
      String str;
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        localXmlPullParser.setInput(new StringReader(paramString));
        localXmlPullParser.nextTag();
        if (localXmlPullParser.next() == 1) {
          break;
        }
        if ((localXmlPullParser.getEventType() != 2) || (!localXmlPullParser.getName().equals("name"))) {
          continue;
        }
        str = XmlRpcUtils.readText(localXmlPullParser);
        if (str.equals("user_name"))
        {
          localUserProfile.username = XmlRpcUtils.getBase64Value(localXmlPullParser);
          continue;
        }
        if (!str.equals("timestamp_reg")) {
          break label155;
        }
      }
      catch (Exception localException)
      {
        Log.e("UserProfileLoader", Log.getStackTraceString(localException));
        return null;
      }
      localUserProfile.regDate = XmlRpcUtils.getStringValue(localXmlPullParser);
      continue;
      label155:
      if (str.equals("post_count")) {
        localUserProfile.postCount = XmlRpcUtils.getTypeValue(localXmlPullParser, "int");
      } else if (str.equals("icon_url")) {
        localUserProfile.avatarUrl = XmlRpcUtils.getStringValue(localXmlPullParser);
      }
    }
  }
  
  public UserProfile loadInBackground()
  {
    ArrayList localArrayList = new ArrayList(2);
    localArrayList.add(new MobiquoHelper.Param("base64", ""));
    localArrayList.add(new MobiquoHelper.Param("string", this.authorId));
    return parseResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("get_user_info", localArrayList), "text/xml"));
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.UserProfileLoader
 * JD-Core Version:    0.7.0.1
 */