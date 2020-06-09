package ru.fax1ty.tabris.audioplayer

import com.eclipsesource.tabris.android.ActivityScope
import com.eclipsesource.tabris.android.BooleanProperty

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class IsPlayingProperty(private val scope: ActivityScope) : BooleanProperty<AudioPlayer>("isPlaying") {
    override fun set(audioPlayer: AudioPlayer, isPlaying: Boolean?) {
        require(isPlaying != null) { "The 'isPlaying' property has to be a boolean value." }
        if (isPlaying) audioPlayer.play()
        else audioPlayer.pause()
    }

    override fun get(audioPlayer: AudioPlayer): Any? {
        return Utils.getIsPlaying(scope.activity.applicationContext)
    }
}