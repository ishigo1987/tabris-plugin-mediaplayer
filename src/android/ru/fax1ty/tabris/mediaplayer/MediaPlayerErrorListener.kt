package ru.fax1ty.tabris.mediaplayer

import com.eclipsesource.tabris.android.Scope

class MediaPlayerErrorListener(private val mediaPlayer: MediaPlayer, private val scope: Scope)
    : android.media.MediaPlayer.OnErrorListener {

    override fun onError(p0: android.media.MediaPlayer?, p1: Int, p2: Int): Boolean {
        scope.remoteObject(mediaPlayer)?.notify("error", mapOf("error" to listOf(p1, p2)))
        return true
    }

}