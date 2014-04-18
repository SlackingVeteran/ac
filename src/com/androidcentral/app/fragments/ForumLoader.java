package com.androidcentral.app.fragments;

import android.content.Context;
import com.androidcentral.app.data.ForumData;
import com.androidcentral.app.net.ForumIndexUpdateHandler;
import com.androidcentral.app.net.MobiquoHelper;
import com.androidcentral.app.net.NetUtils;
import com.androidcentral.app.util.AsyncSupportLoader;

class ForumLoader
  extends AsyncSupportLoader<ForumData>
{
  public ForumLoader(Context paramContext)
  {
    super(paramContext);
  }
  
  public ForumData loadInBackground()
  {
    ForumData localForumData = ForumData.getInstance();
    if (!localForumData.isForumsListLoaded()) {
      ForumIndexUpdateHandler.updateIndex(NetUtils.post("http://forums.androidcentral.com/mobiquo/mobiquo.php", MobiquoHelper.buildPostMethod("get_forum", null), "text/xml"));
    }
    return localForumData;
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.fragments.ForumLoader
 * JD-Core Version:    0.7.0.1
 */