package com.androidcentral.app.net;

import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlRpcUtils
{
  private static final String TAG = "XmlRpcUtils";
  public static final String TYPE_BASE64 = "base64";
  public static final String TYPE_BOOLEAN = "boolean";
  public static final String TYPE_INT = "int";
  public static final String TYPE_STRING = "string";
  
  public static String getBase64Encoded(String paramString)
  {
    return Base64.encodeToString(paramString.getBytes(), 0);
  }
  
  public static String getBase64Value(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    return new String(Base64.decode(getTypeValue(paramXmlPullParser, "base64"), 0));
  }
  
  public static boolean getBooleanValue(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    return getTypeValue(paramXmlPullParser, "boolean").equals("1");
  }
  
  public static int getIntValue(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    return Integer.parseInt(getTypeValue(paramXmlPullParser, "int"));
  }
  
  public static String getStringValue(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    return getTypeValue(paramXmlPullParser, "string");
  }
  
  public static String getTypeValue(XmlPullParser paramXmlPullParser, String paramString)
    throws IOException, XmlPullParserException
  {
    do
    {
      if (paramXmlPullParser.next() == 1) {
        return null;
      }
    } while ((paramXmlPullParser.getEventType() != 2) || (!paramXmlPullParser.getName().equals(paramString)));
    return readText(paramXmlPullParser);
  }
  
  public static ResultResponse parseResultResponse(String paramString)
  {
    ResultResponse localResultResponse = new ResultResponse();
    if (paramString == null) {}
    for (;;)
    {
      return localResultResponse;
      try
      {
        XmlPullParser localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        localXmlPullParser.setInput(new StringReader(paramString));
        localXmlPullParser.nextTag();
        String str;
        label124:
        do
        {
          do
          {
            do
            {
              if (localXmlPullParser.next() == 1) {
                break;
              }
            } while ((localXmlPullParser.getEventType() != 2) || (!localXmlPullParser.getName().equals("name")));
            str = readText(localXmlPullParser);
            if (!str.equals("result")) {
              break label124;
            }
            localResultResponse.success = getBooleanValue(localXmlPullParser);
          } while (!localResultResponse.success);
          return localResultResponse;
        } while (!str.equals("result_text"));
        localResultResponse.error = getBase64Value(localXmlPullParser);
        return localResultResponse;
      }
      catch (Exception localException)
      {
        Log.e("XmlRpcUtils", Log.getStackTraceString(localException));
      }
    }
    return localResultResponse;
  }
  
  public static String readText(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    String str = "";
    if (paramXmlPullParser.next() == 4)
    {
      str = paramXmlPullParser.getText();
      paramXmlPullParser.nextTag();
    }
    return str;
  }
  
  public static class ResultResponse
  {
    public String error = "";
    public boolean success = false;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.XmlRpcUtils
 * JD-Core Version:    0.7.0.1
 */