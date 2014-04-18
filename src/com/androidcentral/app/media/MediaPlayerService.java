package com.androidcentral.app.media;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import com.androidcentral.app.ArticlePagerActivity;

public class MediaPlayerService
  extends Service
  implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener
{
  private final Binder mBinder = new MediaPlayerBinder();
  private MediaPlayerServiceClient mClient;
  private StatefulMediaPlayer mediaPlayer = new StatefulMediaPlayer();
  private String podcastTitle;
  private String returnUrl;
  
  public StatefulMediaPlayer getMediaPlayer()
  {
    return this.mediaPlayer;
  }
  
  public void initializePlayer(String paramString1, String paramString2)
  {
    this.podcastTitle = paramString2;
    this.mClient.onInitializePlayerStart("Buffering...");
    this.mediaPlayer = new StatefulMediaPlayer(paramString1);
    this.mediaPlayer.setOnCompletionListener(this);
    this.mediaPlayer.setOnPreparedListener(this);
    this.mediaPlayer.prepareAsync();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    this.mClient.onPlaybackComplete();
    stopMediaPlayer();
    stopSelf();
  }
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    this.mediaPlayer.reset();
    this.mClient.onError();
    return true;
  }
  
  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mClient.onInitializePlayerSuccess();
    startMediaPlayer();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 1;
  }
  
  public void pauseMediaPlayer()
  {
    this.mediaPlayer.pause();
    stopForeground(true);
  }
  
  public void resetMediaPlaer()
  {
    stopForeground(true);
    this.mediaPlayer.reset();
  }
  
  public void setClient(MediaPlayerServiceClient paramMediaPlayerServiceClient, String paramString)
  {
    this.mClient = paramMediaPlayerServiceClient;
    this.returnUrl = paramString;
  }
  
  public void startMediaPlayer()
  {
    Intent localIntent = new Intent(this, ArticlePagerActivity.class);
    localIntent.setData(Uri.parse(this.returnUrl));
    TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
    localTaskStackBuilder.addParentStack(ArticlePagerActivity.class);
    localTaskStackBuilder.addNextIntent(localIntent);
    PendingIntent localPendingIntent = localTaskStackBuilder.getPendingIntent(0, 134217728);
    startForeground(1, new NotificationCompat.Builder(this).setSmallIcon(2130837579).setContentTitle("Android Central Podcast").setContentText(this.podcastTitle).setContentIntent(localPendingIntent).build());
    this.mediaPlayer.start();
  }
  
  public void stopMediaPlayer()
  {
    stopForeground(true);
    this.mediaPlayer.stop();
    this.mediaPlayer.release();
  }
  
  public class MediaPlayerBinder
    extends Binder
  {
    public MediaPlayerBinder() {}
    
    public MediaPlayerService getService()
    {
      return MediaPlayerService.this;
    }
  }
}


/* Location:           C:\bin\dex2jar-0.0.9.15\ac-dex2jar.jar
 * Qualified Name:     com.androidcentral.app.media.MediaPlayerService
 * JD-Core Version:    0.7.0.1
 */