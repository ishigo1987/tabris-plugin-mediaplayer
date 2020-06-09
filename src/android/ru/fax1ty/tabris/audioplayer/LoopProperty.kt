package ru.fax1ty.tabris.audioplayer

import com.eclipsesource.tabris.android.ActivityScope
import com.eclipsesource.tabris.android.BooleanProperty

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class LoopProperty(private val scope: ActivityScope) : BooleanProperty<AudioPlayer>("position") {
    override fun set(audioPlayer: AudioPlayer, loop: Boolean?) {
        require(loop != null) { "The 'loop' property has to be a boolean value." }
        audioPlayer.setLoop(loop)
    }

    override fun get(audioPlayer: AudioPlayer): Any? {
        return Utils.getLoop(scope.activity.applicationContext)
    }
}