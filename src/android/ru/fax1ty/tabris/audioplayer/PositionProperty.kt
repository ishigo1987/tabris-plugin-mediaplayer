package ru.fax1ty.tabris.audioplayer

import com.eclipsesource.tabris.android.ActivityScope
import com.eclipsesource.tabris.android.IntProperty

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class PositionProperty(private val scope: ActivityScope) : IntProperty<AudioPlayer>("position") {
    override fun set(audioPlayer: AudioPlayer, position: Int?) {
        require(position != null) { "The 'position' property has to be a int value." }
        audioPlayer.seekTo(position)
    }

    override fun get(audioPlayer: AudioPlayer): Any? {
        return Utils.getPosition(scope.activity.applicationContext)
    }
}