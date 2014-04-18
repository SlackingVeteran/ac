package com.androidcentral.app.net;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.androidcentral.app.data.Article;
import com.androidcentral.app.data.NewsDataSource;
import com.androidcentral.app.util.PreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ArticleUpdateHandler
{
  private static final int FETCH_MAX = 20;
  private static final int NUM_ARTICLES = 100;
  private static final String TAG = "ArticleUpdateHandler";
  private Context context;
  private SQLiteDatabase db;
  private NewsDataSource newsDataSource;
  
  public ArticleUpdateHandler(Context paramContext)
  {
    this.newsDataSource = NewsDataSource.getInstance(paramContext);
    this.db = this.newsDataSource.getDatabase();
    this.context = paramContext;
  }
  
  public Article extractArticle(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Gson localGson = new Gson();
    JsonParser localJsonParser = new JsonParser();
    try
    {
      JsonElement localJsonElement = localJsonParser.parse(paramString);
      return (Article)localGson.fromJson(localJsonElement.getAsJsonObject().get("node"), Article.class);
    }
    catch (JsonSyntaxException localJsonSyntaxException) {}
    return null;
  }
  
  public void updateArticleContents(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {}
    Article localArticle;
    do
    {
      return;
      localArticle = extractArticle(paramString);
    } while (localArticle == null);
    this.db.beginTransaction();
    if (paramBoolean) {}
    for (;;)
    {
      int i;
      int j;
      try
      {
        this.db.insert("articles", null, localArticle.getArticlesCVs());
        arrayOfContentValues = localArticle.getSectionsCVs();
        i = arrayOfContentValues.length;
        j = 0;
      }
      finally
      {
        ContentValues[] arrayOfContentValues;
        ContentValues localContentValues;
        this.db.endTransaction();
      }
      this.db.replace("articlesContent", null, localArticle.getContentCV());
      this.db.setTransactionSuccessful();
      this.db.endTransaction();
      return;
      localContentValues = arrayOfContentValues[j];
      this.db.replace("articlesSection", null, localContentValues);
      j++;
      if (j < i) {}
    }
  }
  
  /* Error */
  public List<Article> updateArticleIndex(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 46	com/google/gson/Gson
    //   9: dup
    //   10: invokespecial 47	com/google/gson/Gson:<init>	()V
    //   13: astore_2
    //   14: new 49	com/google/gson/JsonParser
    //   17: dup
    //   18: invokespecial 50	com/google/gson/JsonParser:<init>	()V
    //   21: astore_3
    //   22: aload_3
    //   23: aload_1
    //   24: invokevirtual 54	com/google/gson/JsonParser:parse	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
    //   27: astore 5
    //   29: aload 5
    //   31: invokevirtual 60	com/google/gson/JsonElement:getAsJsonObject	()Lcom/google/gson/JsonObject;
    //   34: ldc 84
    //   36: invokevirtual 67	com/google/gson/JsonObject:get	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
    //   39: astore 6
    //   41: aload 5
    //   43: invokevirtual 60	com/google/gson/JsonElement:getAsJsonObject	()Lcom/google/gson/JsonObject;
    //   46: ldc 116
    //   48: invokevirtual 67	com/google/gson/JsonObject:get	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
    //   51: astore 7
    //   53: new 118	com/androidcentral/app/net/ArticleUpdateHandler$1
    //   56: dup
    //   57: aload_0
    //   58: invokespecial 121	com/androidcentral/app/net/ArticleUpdateHandler$1:<init>	(Lcom/androidcentral/app/net/ArticleUpdateHandler;)V
    //   61: astore 8
    //   63: aload 8
    //   65: invokevirtual 125	com/androidcentral/app/net/ArticleUpdateHandler$1:getType	()Ljava/lang/reflect/Type;
    //   68: astore 9
    //   70: aload_2
    //   71: aload 6
    //   73: aload 9
    //   75: invokevirtual 128	com/google/gson/Gson:fromJson	(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;)Ljava/lang/Object;
    //   78: checkcast 130	java/util/List
    //   81: astore 10
    //   83: aload_2
    //   84: aload 7
    //   86: aload 9
    //   88: invokevirtual 128	com/google/gson/Gson:fromJson	(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;)Ljava/lang/Object;
    //   91: checkcast 130	java/util/List
    //   94: astore 11
    //   96: new 132	java/util/ArrayList
    //   99: dup
    //   100: bipush 100
    //   102: invokespecial 135	java/util/ArrayList:<init>	(I)V
    //   105: astore 12
    //   107: new 132	java/util/ArrayList
    //   110: dup
    //   111: bipush 100
    //   113: invokespecial 135	java/util/ArrayList:<init>	(I)V
    //   116: astore 13
    //   118: new 132	java/util/ArrayList
    //   121: dup
    //   122: bipush 100
    //   124: invokespecial 135	java/util/ArrayList:<init>	(I)V
    //   127: astore 14
    //   129: new 132	java/util/ArrayList
    //   132: dup
    //   133: bipush 100
    //   135: invokespecial 135	java/util/ArrayList:<init>	(I)V
    //   138: astore 15
    //   140: aload_0
    //   141: getfield 32	com/androidcentral/app/net/ArticleUpdateHandler:newsDataSource	Lcom/androidcentral/app/data/NewsDataSource;
    //   144: invokevirtual 139	com/androidcentral/app/data/NewsDataSource:getArticlesModified	()Ljava/util/Map;
    //   147: astore 16
    //   149: aload 10
    //   151: invokeinterface 143 1 0
    //   156: astore 17
    //   158: aload 17
    //   160: invokeinterface 149 1 0
    //   165: ifne +126 -> 291
    //   168: aload 11
    //   170: ifnull +22 -> 192
    //   173: aload 11
    //   175: invokeinterface 143 1 0
    //   180: astore 42
    //   182: aload 42
    //   184: invokeinterface 149 1 0
    //   189: ifne +247 -> 436
    //   192: aload_0
    //   193: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   196: invokevirtual 82	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   199: aload 11
    //   201: ifnull +12 -> 213
    //   204: aload_0
    //   205: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   208: ldc 151
    //   210: invokevirtual 155	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   213: aload 12
    //   215: invokeinterface 143 1 0
    //   220: astore 25
    //   222: aload 25
    //   224: invokeinterface 149 1 0
    //   229: ifne +238 -> 467
    //   232: aload 13
    //   234: invokeinterface 143 1 0
    //   239: astore 29
    //   241: aload 29
    //   243: invokeinterface 149 1 0
    //   248: ifne +262 -> 510
    //   251: aload 14
    //   253: invokeinterface 143 1 0
    //   258: astore 35
    //   260: aload 35
    //   262: invokeinterface 149 1 0
    //   267: ifne +322 -> 589
    //   270: aload_0
    //   271: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   274: invokevirtual 107	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   277: aload_0
    //   278: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   281: invokevirtual 110	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   284: aload 15
    //   286: areturn
    //   287: astore 4
    //   289: aconst_null
    //   290: areturn
    //   291: aload 17
    //   293: invokeinterface 159 1 0
    //   298: checkcast 69	com/androidcentral/app/data/Article
    //   301: astore 18
    //   303: aload 18
    //   305: iconst_0
    //   306: putfield 162	com/androidcentral/app/data/Article:sticky	I
    //   309: aload 16
    //   311: aload 18
    //   313: getfield 166	com/androidcentral/app/data/Article:nid	J
    //   316: invokestatic 172	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   319: invokeinterface 178 2 0
    //   324: ifeq +96 -> 420
    //   327: aload 13
    //   329: aload 18
    //   331: invokeinterface 181 2 0
    //   336: pop
    //   337: aload 16
    //   339: aload 18
    //   341: getfield 166	com/androidcentral/app/data/Article:nid	J
    //   344: invokestatic 172	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   347: invokeinterface 184 2 0
    //   352: checkcast 168	java/lang/Long
    //   355: invokevirtual 188	java/lang/Long:longValue	()J
    //   358: aload 18
    //   360: getfield 191	com/androidcentral/app/data/Article:modifiedDate	J
    //   363: lcmp
    //   364: ifge +50 -> 414
    //   367: iconst_1
    //   368: istore 20
    //   370: iload 20
    //   372: ifeq -214 -> 158
    //   375: aload 18
    //   377: invokevirtual 194	com/androidcentral/app/data/Article:isTalkMobile	()Z
    //   380: ifne +13 -> 393
    //   383: aload 15
    //   385: aload 18
    //   387: invokeinterface 181 2 0
    //   392: pop
    //   393: aload 18
    //   395: getfield 198	com/androidcentral/app/data/Article:sections	[Ljava/lang/String;
    //   398: ifnull -240 -> 158
    //   401: aload 14
    //   403: aload 18
    //   405: invokeinterface 181 2 0
    //   410: pop
    //   411: goto -253 -> 158
    //   414: iconst_0
    //   415: istore 20
    //   417: goto -47 -> 370
    //   420: aload 12
    //   422: aload 18
    //   424: invokeinterface 181 2 0
    //   429: pop
    //   430: iconst_1
    //   431: istore 20
    //   433: goto -63 -> 370
    //   436: aload 42
    //   438: invokeinterface 159 1 0
    //   443: checkcast 69	com/androidcentral/app/data/Article
    //   446: astore 43
    //   448: aload 43
    //   450: iconst_1
    //   451: putfield 162	com/androidcentral/app/data/Article:sticky	I
    //   454: aload 13
    //   456: aload 43
    //   458: invokeinterface 181 2 0
    //   463: pop
    //   464: goto -282 -> 182
    //   467: aload 25
    //   469: invokeinterface 159 1 0
    //   474: checkcast 69	com/androidcentral/app/data/Article
    //   477: astore 26
    //   479: aload_0
    //   480: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   483: ldc 84
    //   485: aconst_null
    //   486: aload 26
    //   488: invokevirtual 88	com/androidcentral/app/data/Article:getArticlesCVs	()Landroid/content/ContentValues;
    //   491: invokevirtual 92	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   494: pop2
    //   495: goto -273 -> 222
    //   498: astore 24
    //   500: aload_0
    //   501: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   504: invokevirtual 110	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   507: aload 24
    //   509: athrow
    //   510: aload 29
    //   512: invokeinterface 159 1 0
    //   517: checkcast 69	com/androidcentral/app/data/Article
    //   520: astore 30
    //   522: aload 30
    //   524: invokevirtual 88	com/androidcentral/app/data/Article:getArticlesCVs	()Landroid/content/ContentValues;
    //   527: astore 31
    //   529: aload 31
    //   531: ldc 200
    //   533: invokevirtual 205	android/content/ContentValues:remove	(Ljava/lang/String;)V
    //   536: aload 11
    //   538: ifnonnull +10 -> 548
    //   541: aload 31
    //   543: ldc 206
    //   545: invokevirtual 205	android/content/ContentValues:remove	(Ljava/lang/String;)V
    //   548: aload_0
    //   549: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   552: astore 32
    //   554: iconst_1
    //   555: anewarray 208	java/lang/String
    //   558: astore 33
    //   560: aload 33
    //   562: iconst_0
    //   563: aload 30
    //   565: getfield 166	com/androidcentral/app/data/Article:nid	J
    //   568: invokestatic 211	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   571: aastore
    //   572: aload 32
    //   574: ldc 84
    //   576: aload 31
    //   578: ldc 213
    //   580: aload 33
    //   582: invokevirtual 217	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   585: pop
    //   586: goto -345 -> 241
    //   589: aload 35
    //   591: invokeinterface 159 1 0
    //   596: checkcast 69	com/androidcentral/app/data/Article
    //   599: invokevirtual 96	com/androidcentral/app/data/Article:getSectionsCVs	()[Landroid/content/ContentValues;
    //   602: astore 36
    //   604: aload 36
    //   606: arraylength
    //   607: istore 37
    //   609: iconst_0
    //   610: istore 38
    //   612: iload 38
    //   614: iload 37
    //   616: if_icmpge -356 -> 260
    //   619: aload 36
    //   621: iload 38
    //   623: aaload
    //   624: astore 39
    //   626: aload_0
    //   627: getfield 38	com/androidcentral/app/net/ArticleUpdateHandler:db	Landroid/database/sqlite/SQLiteDatabase;
    //   630: ldc 112
    //   632: aconst_null
    //   633: aload 39
    //   635: invokevirtual 104	android/database/sqlite/SQLiteDatabase:replace	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   638: pop2
    //   639: iinc 38 1
    //   642: goto -30 -> 612
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	645	0	this	ArticleUpdateHandler
    //   0	645	1	paramString	String
    //   13	71	2	localGson	Gson
    //   21	2	3	localJsonParser	JsonParser
    //   287	1	4	localJsonSyntaxException	JsonSyntaxException
    //   27	15	5	localJsonElement1	JsonElement
    //   39	33	6	localJsonElement2	JsonElement
    //   51	34	7	localJsonElement3	JsonElement
    //   61	3	8	local1	1
    //   68	19	9	localType	java.lang.reflect.Type
    //   81	69	10	localList1	List
    //   94	443	11	localList2	List
    //   105	316	12	localArrayList1	java.util.ArrayList
    //   116	339	13	localArrayList2	java.util.ArrayList
    //   127	275	14	localArrayList3	java.util.ArrayList
    //   138	246	15	localArrayList4	java.util.ArrayList
    //   147	191	16	localMap	java.util.Map
    //   156	136	17	localIterator1	java.util.Iterator
    //   301	122	18	localArticle1	Article
    //   368	64	20	i	int
    //   498	10	24	localObject	Object
    //   220	248	25	localIterator2	java.util.Iterator
    //   477	10	26	localArticle2	Article
    //   239	272	29	localIterator3	java.util.Iterator
    //   520	44	30	localArticle3	Article
    //   527	50	31	localContentValues1	ContentValues
    //   552	21	32	localSQLiteDatabase	SQLiteDatabase
    //   558	23	33	arrayOfString	String[]
    //   258	332	35	localIterator4	java.util.Iterator
    //   602	18	36	arrayOfContentValues	ContentValues[]
    //   607	10	37	j	int
    //   610	30	38	k	int
    //   624	10	39	localContentValues2	ContentValues
    //   180	257	42	localIterator5	java.util.Iterator
    //   446	11	43	localArticle4	Article
    // Exception table:
    //   from	to	target	type
    //   22	29	287	com/google/gson/JsonSyntaxException
    //   204	213	498	finally
    //   213	222	498	finally
    //   222	241	498	finally
    //   241	260	498	finally
    //   260	277	498	finally
    //   467	495	498	finally
    //   510	536	498	finally
    //   541	548	498	finally
    //   548	586	498	finally
    //   589	609	498	finally
    //   619	639	498	finally
  }
  
  public void updateArticles(List<Article> paramList)
  {
    int i = Math.min(20, paramList.size());
    Log.d("ArticleUpdateHandler", "Fetching the content for " + i + " articles");
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      long l = ((Article)paramList.get(j)).nid;
      Log.d("ArticleUpdateHandler", "Downloading content for article nid = " + l);
      updateArticleContents(NetUtils.get(new StringBuilder(String.valueOf(new StringBuilder("http://m.androidcentral.com/node/").append(l).append("/json").toString())).append("?version=3").toString() + "&debug=" + PreferenceHelper.getInstance(this.context).getDebugFlag()), false);
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.net.ArticleUpdateHandler
 * JD-Core Version:    0.7.0.1
 */