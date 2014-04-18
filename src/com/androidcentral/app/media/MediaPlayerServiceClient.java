package com.androidcentral.app.media;

public abstract interface MediaPlayerServiceClient
{
  public abstract void onError();
  
  public abstract void onInitializePlayerStart(String paramString);
  
  public abstract void onInitializePlayerSuccess();
  
  public abstract void onPlaybackComplete();
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.media.MediaPlayerServiceClient
 * JD-Core Version:    0.7.0.1
 */