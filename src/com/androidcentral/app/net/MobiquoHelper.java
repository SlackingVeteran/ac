package com.androidcentral.app.net;

import java.util.Iterator;
import java.util.List;

public class MobiquoHelper
{
  public static String buildPostMethod(String paramString, List<Param> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("<methodCall><methodName>");
    localStringBuilder.append(paramString);
    localStringBuilder.append("</methodName>");
    Iterator localIterator;
    if (paramList != null)
    {
      localStringBuilder.append("<params>");
      localIterator = paramList.iterator();
      if (!localIterator.hasNext()) {
        localStringBuilder.append("</params>");
      }
    }
    else
    {
      localStringBuilder.append("</methodCall>");
      return localStringBuilder.toString();
    }
    Param localParam = (Param)localIterator.next();
    localStringBuilder.append("<param><value>");
    localStringBuilder.append("<");
    localStringBuilder.append(localParam.type);
    if (localParam.value.isEmpty()) {
      localStringBuilder.append("/>");
    }
    for (;;)
    {
      localStringBuilder.append("</value></param>");
      break;
      localStringBuilder.append(">");
      localStringBuilder.append(localParam.value);
      localStringBuilder.append("</");
      localStringBuilder.append(localParam.type);
      localStringBuilder.append(">");
    }
  }
  
  public static class Param
  {
    public String type;
    public String value;
    
    public Param(String paramString1, String paramString2)
    {
      this.type = paramString1;
      this.value = paramString2;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.MobiquoHelper
 * JD-Core Version:    0.7.0.1
 */