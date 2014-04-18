package com.androidcentral.app.net;

import java.net.CookieHandler;

public class SessionManager
{
  private static final String SESSION_CHECK_URL = "https://passport.mobilenations.com/process/SessionCheck.php";
  private static final String TAG = "SessionManager";
  private static SessionManager instance;
  private String drupalId;
  private boolean loggedIn = false;
  private String username;
  
  public static SessionManager getInstance()
  {
    if (instance == null) {
      instance = new SessionManager();
    }
    return instance;
  }
  
  public void copyWebViewCookies()
  {
    android.webkit.CookieManager localCookieManager = android.webkit.CookieManager.getInstance();
    SessionCookieStore localSessionCookieStore = (SessionCookieStore)((java.net.CookieManager)CookieHandler.getDefault()).getCookieStore();
    localSessionCookieStore.extractCookies(localCookieManager.getCookie("https://passport.mobilenations.com"));
    localSessionCookieStore.extractCookies(localCookieManager.getCookie("http://androidcentral.com"));
  }
  
  public String getDrupalId()
  {
    return this.drupalId;
  }
  
  public String getUserId()
  {
    return ((SessionCookieStore)((java.net.CookieManager)CookieHandler.getDefault()).getCookieStore()).getCookieValue("acuserid");
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public boolean isLoggedIn()
  {
    return this.loggedIn;
  }
  
  public void sessionCheck()
  {
    new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: ldc 26
        //   2: invokestatic 32	com/androidcentral/app/net/NetUtils:get	(Ljava/lang/String;)Ljava/lang/String;
        //   5: astore_1
        //   6: aload_1
        //   7: ifnonnull +4 -> 11
        //   10: return
        //   11: ldc 34
        //   13: new 36	java/lang/StringBuilder
        //   16: dup
        //   17: ldc 38
        //   19: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   22: aload_1
        //   23: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   26: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   29: invokestatic 55	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   32: pop
        //   33: new 57	com/google/gson/JsonParser
        //   36: dup
        //   37: invokespecial 58	com/google/gson/JsonParser:<init>	()V
        //   40: astore_3
        //   41: aload_3
        //   42: aload_1
        //   43: invokevirtual 62	com/google/gson/JsonParser:parse	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   46: invokevirtual 68	com/google/gson/JsonElement:getAsJsonObject	()Lcom/google/gson/JsonObject;
        //   49: astore 5
        //   51: aload_0
        //   52: getfield 17	com/androidcentral/app/net/SessionManager$1:this$0	Lcom/androidcentral/app/net/SessionManager;
        //   55: aload 5
        //   57: ldc 70
        //   59: invokevirtual 74	com/google/gson/JsonObject:get	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   62: invokevirtual 78	com/google/gson/JsonElement:getAsBoolean	()Z
        //   65: invokestatic 82	com/androidcentral/app/net/SessionManager:access$0	(Lcom/androidcentral/app/net/SessionManager;Z)V
        //   68: aload_0
        //   69: getfield 17	com/androidcentral/app/net/SessionManager$1:this$0	Lcom/androidcentral/app/net/SessionManager;
        //   72: invokestatic 86	com/androidcentral/app/net/SessionManager:access$1	(Lcom/androidcentral/app/net/SessionManager;)Z
        //   75: ifeq -65 -> 10
        //   78: aload_0
        //   79: getfield 17	com/androidcentral/app/net/SessionManager$1:this$0	Lcom/androidcentral/app/net/SessionManager;
        //   82: aload 5
        //   84: ldc 88
        //   86: invokevirtual 74	com/google/gson/JsonObject:get	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   89: invokevirtual 91	com/google/gson/JsonElement:getAsString	()Ljava/lang/String;
        //   92: invokestatic 95	com/androidcentral/app/net/SessionManager:access$2	(Lcom/androidcentral/app/net/SessionManager;Ljava/lang/String;)V
        //   95: aload 5
        //   97: ldc 97
        //   99: invokevirtual 101	com/google/gson/JsonObject:has	(Ljava/lang/String;)Z
        //   102: ifeq +67 -> 169
        //   105: aload 5
        //   107: ldc 97
        //   109: invokevirtual 104	com/google/gson/JsonObject:getAsJsonObject	(Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   112: astore 14
        //   114: aload 14
        //   116: ldc 106
        //   118: invokevirtual 101	com/google/gson/JsonObject:has	(Ljava/lang/String;)Z
        //   121: ifeq +48 -> 169
        //   124: aload_0
        //   125: getfield 17	com/androidcentral/app/net/SessionManager$1:this$0	Lcom/androidcentral/app/net/SessionManager;
        //   128: aload 14
        //   130: ldc 106
        //   132: invokevirtual 74	com/google/gson/JsonObject:get	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   135: invokevirtual 91	com/google/gson/JsonElement:getAsString	()Ljava/lang/String;
        //   138: invokestatic 109	com/androidcentral/app/net/SessionManager:access$3	(Lcom/androidcentral/app/net/SessionManager;Ljava/lang/String;)V
        //   141: ldc 34
        //   143: new 36	java/lang/StringBuilder
        //   146: dup
        //   147: ldc 111
        //   149: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   152: aload_0
        //   153: getfield 17	com/androidcentral/app/net/SessionManager$1:this$0	Lcom/androidcentral/app/net/SessionManager;
        //   156: invokestatic 115	com/androidcentral/app/net/SessionManager:access$4	(Lcom/androidcentral/app/net/SessionManager;)Ljava/lang/String;
        //   159: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   162: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   165: invokestatic 55	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   168: pop
        //   169: aconst_null
        //   170: astore 6
        //   172: aconst_null
        //   173: astore 7
        //   175: new 117	java/net/URL
        //   178: dup
        //   179: ldc 119
        //   181: invokespecial 120	java/net/URL:<init>	(Ljava/lang/String;)V
        //   184: invokevirtual 124	java/net/URL:openConnection	()Ljava/net/URLConnection;
        //   187: checkcast 126	java/net/HttpURLConnection
        //   190: astore 6
        //   192: aload 6
        //   194: invokevirtual 130	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
        //   197: pop
        //   198: aload 6
        //   200: invokevirtual 134	java/net/HttpURLConnection:getURL	()Ljava/net/URL;
        //   203: astore 12
        //   205: aconst_null
        //   206: astore 7
        //   208: aload 12
        //   210: ifnull +19 -> 229
        //   213: aload 12
        //   215: invokevirtual 124	java/net/URL:openConnection	()Ljava/net/URLConnection;
        //   218: checkcast 126	java/net/HttpURLConnection
        //   221: astore 7
        //   223: aload 7
        //   225: invokevirtual 130	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
        //   228: pop
        //   229: aload 6
        //   231: ifnull +8 -> 239
        //   234: aload 6
        //   236: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   239: aload 7
        //   241: ifnull -231 -> 10
        //   244: aload 7
        //   246: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   249: return
        //   250: astore 4
        //   252: return
        //   253: astore 9
        //   255: ldc 34
        //   257: aload 9
        //   259: invokestatic 141	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
        //   262: invokestatic 144	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   265: pop
        //   266: aload 6
        //   268: ifnull +8 -> 276
        //   271: aload 6
        //   273: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   276: aload 7
        //   278: ifnull -268 -> 10
        //   281: aload 7
        //   283: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   286: return
        //   287: astore 8
        //   289: aload 6
        //   291: ifnull +8 -> 299
        //   294: aload 6
        //   296: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   299: aload 7
        //   301: ifnull +8 -> 309
        //   304: aload 7
        //   306: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
        //   309: aload 8
        //   311: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	312	0	this	1
        //   5	38	1	str	String
        //   40	2	3	localJsonParser	com.google.gson.JsonParser
        //   250	1	4	localJsonSyntaxException	com.google.gson.JsonSyntaxException
        //   49	57	5	localJsonObject1	com.google.gson.JsonObject
        //   170	125	6	localHttpURLConnection1	java.net.HttpURLConnection
        //   173	132	7	localHttpURLConnection2	java.net.HttpURLConnection
        //   287	23	8	localObject	Object
        //   253	5	9	localIOException	java.io.IOException
        //   203	11	12	localURL	java.net.URL
        //   112	17	14	localJsonObject2	com.google.gson.JsonObject
        // Exception table:
        //   from	to	target	type
        //   41	51	250	com/google/gson/JsonSyntaxException
        //   175	205	253	java/io/IOException
        //   213	229	253	java/io/IOException
        //   175	205	287	finally
        //   213	229	287	finally
        //   255	266	287	finally
      }
    }).start();
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.SessionManager
 * JD-Core Version:    0.7.0.1
 */