package com.androidcentral.app.net;

import android.util.Log;
import android.util.Xml;
import com.androidcentral.app.data.ForumData;
import com.androidcentral.app.data.ForumEntry;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public class ForumIndexUpdateHandler
{
  private static final String TAG = "ForumIndexUpdateHandler";
  
  public static void updateIndex(String paramString)
  {
    if (paramString == null) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        localXmlPullParser.setInput(new StringReader(paramString));
        localXmlPullParser.nextTag();
        localObject = null;
      }
      catch (Exception localException2)
      {
        XmlPullParser localXmlPullParser;
        Object localObject;
        String str;
        ForumEntry localForumEntry2;
        continue;
        continue;
      }
      try
      {
        if (localXmlPullParser.next() == 1)
        {
          if (localObject == null) {
            continue;
          }
          localArrayList.add(localObject);
          ForumData.getInstance().clear();
          ForumData.getInstance().addAll(localArrayList);
          return;
        }
        if ((localXmlPullParser.getEventType() != 2) || (!localXmlPullParser.getName().equals("name"))) {
          continue;
        }
        str = XmlRpcUtils.readText(localXmlPullParser);
        if (str.equals("forum_id"))
        {
          if (localObject != null) {
            localArrayList.add(localObject);
          }
          ForumEntry localForumEntry1 = new ForumEntry();
          localForumEntry1.id = Integer.parseInt(XmlRpcUtils.getStringValue(localXmlPullParser));
          localObject = localForumEntry1;
          continue;
        }
        if (str.equals("forum_name"))
        {
          localObject.name = XmlRpcUtils.getBase64Value(localXmlPullParser);
          continue;
        }
      }
      catch (Exception localException1)
      {
        Log.e("ForumIndexUpdateHandler", Log.getStackTraceString(localException1));
        continue;
        if (str.equals("parent_id"))
        {
          localObject.parentId = Integer.parseInt(XmlRpcUtils.getStringValue(localXmlPullParser));
          continue;
        }
        if (str.equals("can_subscribe"))
        {
          localObject.canSubscribe = XmlRpcUtils.getBooleanValue(localXmlPullParser);
          continue;
        }
        if (str.equals("sub_only"))
        {
          localObject.subOnly = XmlRpcUtils.getBooleanValue(localXmlPullParser);
          continue;
        }
        if (str.equals("url"))
        {
          localObject.url = XmlRpcUtils.getStringValue(localXmlPullParser);
          continue;
        }
      }
      if ((str.equals("child")) && (!localObject.subOnly))
      {
        localObject.subOnly = true;
        localForumEntry2 = new ForumEntry();
        localForumEntry2.id = localObject.id;
        localForumEntry2.parentId = localObject.id;
        localForumEntry2.name = localObject.name;
        localForumEntry2.canSubscribe = true;
        localForumEntry2.subOnly = false;
        localForumEntry2.url = localObject.url;
        localArrayList.add(localForumEntry2);
      }
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.ForumIndexUpdateHandler
 * JD-Core Version:    0.7.0.1
 */