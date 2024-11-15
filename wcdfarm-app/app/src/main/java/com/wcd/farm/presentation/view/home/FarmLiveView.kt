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
import androidx.compose.runtime.DisposableEffect
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

const val rtmpURL = "rtmp://k11c104.p.ssafy.io/live/test.stream"

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

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
            libVLC.release()
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

        },
    )
}