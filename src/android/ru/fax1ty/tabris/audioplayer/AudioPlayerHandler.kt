package ru.fax1ty.tabris.audioplayer

import android.content.Context
import android.net.wifi.WifiManager
import com.eclipsesource.tabris.android.ActivityScope
import com.eclipsesource.tabris.android.ObjectHandler
import com.eclipsesource.tabris.android.Property
import com.eclipsesource.tabris.android.internal.ktx.getBooleanOrNull
import com.eclipsesource.v8.V8Object

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class AudioPlayerHandler(private val scope: ActivityScope) : ObjectHandler<AudioPlayer> {

    private lateinit var wifiManager: WifiManager
    private val wifiLock: WifiManager.WifiLock =
            wifiManager.createWifiLock(WifiManager.WIFI_STATE_ENABLED, "mylock")

    override val type = "ru.fax1ty.tabris.audioplayer.AudioPlayer"

    override fun create(id: String, properties: V8Object) = AudioPlayer(scope.activity, scope,
            properties.getString("url"),
            properties.getBooleanOrNull("loop") ?: false,
            properties.getBooleanOrNull("autoPlay") ?: false
    ).also {
        it.create()
        wifiManager = scope.activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiLock.acquire()
    }

    override val properties: List<Property<AudioPlayer, *>> = listOf(
            PositionProperty(scope),
            LoopProperty(scope),
            IsPlayingProperty(scope)
    )

    override fun call(audioPlayer: AudioPlayer, method: String, properties: V8Object): Any? = when (method) {
        "play" -> audioPlayer.play()
        "pause" -> audioPlayer.pause()
        "seekTo" -> audioPlayer.seekTo(properties.getInteger("position"))

        else -> null
    }

    override fun destroy(audioPlayer: AudioPlayer) {
        super.destroy(audioPlayer)
        audioPlayer.stop()
        wifiLock.release()
    }

}