package com.wcd.farm.presentation.view.home

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun VLCPlayer(
    modifier: Modifier,
    streamKey: String,
    subtitleUrl: String?,
) {
    val localContext = LocalContext.current
    val videoUrl = "rtmp://k11c104.p.ssafy.io/live/$streamKey"

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