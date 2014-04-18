package com.androidcentral.app.fragments;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import com.androidcentral.app.data.ForumEntry;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.net.XmlRpcUtils;
import com.androidcentral.app.util.AsyncSupportLoader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

class SubscribedForumsLoader
  extends AsyncSupportLoader<List<ForumEntry>>
{
  private static final String TAG = "SubscribedForumsLoader";
  
  public SubscribedForumsLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  private List<ForumEntry> parseResponse(String paramString)
  {
    if (paramString == null)
    {
      localObject1 = null;
      return localObject1;
    }
    Object localObject1 = new ArrayList();
    for (;;)
    {
      XmlPullParser localXmlPullParser;
      Object localObject2;
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        localXmlPullParser.setInput(new StringReader(paramString));
        localXmlPullParser.nextTag();
        localObject2 = null;
        if (localXmlPullParser.next() == 1)
        {
          if (localObject2 == null) {
            break;
          }
          ((List)localObject1).add(localObject2);
          return localObject1;
        }
      }
      catch (Exception localException)
      {
        Log.e("SubscribedForumsLoader", Log.getStackTraceString(localException));
        return null;
      }
      if ((localXmlPullParser.getEventType() == 2) && (localXmlPullParser.getName().equals("name")))
      {
        String str = XmlRpcUtils.readText(localXmlPullParser);
        if (str.equals("forum_id"))
        {
          if (localObject2 != null) {
            ((List)localObject1).add(localObject2);
          }
          localObject2 = new ForumEntry();
          ((ForumEntry)localObject2).id = Integer.parseInt(XmlRpcUtils.getStringValue(localXmlPullParser));
        }
        else if (str.equals("forum_name"))
        {
          ((ForumEntry)localObject2).name = XmlRpcUtils.getBase64Value(localXmlPullParser);
        }
      }
    }
  }
  
  public List<ForumEntry> loadInBackground()
  {
    return parseResponse(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("get_subscribed_forum", null), "text/xml"));
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.SubscribedForumsLoader
 * JD-Core Version:    0.7.0.1
 */