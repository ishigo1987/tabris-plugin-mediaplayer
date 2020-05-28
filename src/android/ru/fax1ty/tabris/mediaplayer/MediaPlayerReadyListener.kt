package ru.fax1ty.tabris.mediaplayer

import com.eclipsesource.tabris.android.Scope

class MediaPlayerReadyListener(private val mediaPlayer: MediaPlayer, private val scope: Scope)
    : android.media.MediaPlayer.OnPreparedListener {

    override fun onPrepared(p0: android.media.MediaPlayer?) {
        scope.remoteObject(mediaPlayer)?.notify("ready")
    }

}