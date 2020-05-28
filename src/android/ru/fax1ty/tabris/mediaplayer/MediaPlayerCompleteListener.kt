package ru.fax1ty.tabris.mediaplayer

import com.eclipsesource.tabris.android.Scope

class MediaPlayerCompleteListener(private val mediaPlayer: MediaPlayer, private val scope: Scope)
    : android.media.MediaPlayer.OnCompletionListener {

    override fun onCompletion(p0: android.media.MediaPlayer?) {
        scope.remoteObject(mediaPlayer)?.notify("complete")
    }
}