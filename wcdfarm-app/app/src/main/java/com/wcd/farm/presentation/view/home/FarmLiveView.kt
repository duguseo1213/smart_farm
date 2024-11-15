package com.wcd.farm.presentation.view.home

import android.net.Uri
import android.util.Log
import android.view.SurfaceView
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.rtmp.RtmpDataSource
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.interfaces.IMedia
import org.videolan.libvlc.util.VLCVideoLayout

//const val rtmpURL = "rtmp://210.99.70.120/live/cctv048.stream"
//const val hlsURL = "http://210.99.70.120:1935/live/cctv048.stream/playlist.m3u8"
const val rtmpURL = "rtmp://k11c104.p.ssafy.io/live/test.stream"
@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerUI(modifier: Modifier) {

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*val mContext = LocalContext.current

        val rtmpDataSourceFactory = RtmpDataSource.Factory()
        val mediaSource = DefaultMediaSourceFactory(mContext)
            .setDataSourceFactory(rtmpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(rtmpURL))

        val mExoPlayer = remember {
            ExoPlayer.Builder(mContext).build().apply {


                setMediaSource(mediaSource)

                *//*val dataSourceFactory = DefaultDataSourceFactory(mContext, "user-agent")
                val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(hlsURL)))

                setMediaSource(mediaSource)*//*

                playWhenReady = true
                prepare()
            }
        }

        mExoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)

                when (state) {
                    Player.STATE_READY -> {
                        Log.e("ExoPlayer", "RTMP stream is ready!")
                    }
                    Player.STATE_BUFFERING -> {
                        Log.e("ExoPlayer", "Buffering...")
                    }
                    Player.STATE_ENDED -> {
                        Log.e("ExoPlayer", "RTMP stream ended.")
                    }
                    Player.STATE_IDLE -> {
                        Log.e("ExoPlayer", "Player is idle.")
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                // 에러가 발생했을 때
                Log.e("ExoPlayer", "Error: ${error.message}")
                Log.e("TEST", "Error: $error")
            }
        })*/


        VLCPlayer(modifier = Modifier, videoUrl = rtmpURL, subtitleUrl = null)

        /*AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = mExoPlayer
            }
        })*/
    }
}

@Composable
fun VLCPlayer(
    modifier: Modifier,
    videoUrl: String,
    subtitleUrl: String?,
) {
    val localContext = LocalContext.current
    val libVLC = remember(key1 = videoUrl, key2 = subtitleUrl) { LibVLC(localContext) }
    val media = remember(key1 = videoUrl, key2 = subtitleUrl) { Media(libVLC, Uri.parse(videoUrl)) }
    val mediaPlayer = remember(key1 = videoUrl, key2 = subtitleUrl) {
        MediaPlayer(libVLC).apply {
            setMedia(media)
        }
    }

    if (!subtitleUrl.isNullOrBlank()) {
        LaunchedEffect(key1 = subtitleUrl) {
            media.addSlave(
                IMedia.Slave(
                    IMedia.Slave.Type.Subtitle,
                    4,
                    Uri.parse(subtitleUrl).toString(),
                )
            )
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            VLCVideoLayout(context).apply {
                mediaPlayer.attachViews(this, null, false, false)
                mediaPlayer.play()
                mediaPlayer.videoScale = MediaPlayer.ScaleType.SURFACE_FILL
            }
        },
        update = {
            //mediaPlayer.play()
        },
    )
}