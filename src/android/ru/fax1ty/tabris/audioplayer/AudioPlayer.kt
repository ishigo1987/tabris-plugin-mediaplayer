package ru.fax1ty.tabris.audioplayer

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.eclipsesource.tabris.android.Scope


class AudioPlayer(private val activity: Activity, private val scope: Scope, private val url: String, private val loop: Boolean, private val autoPlay: Boolean) {
    private var intent: Intent? = null

    fun create() {
        intent = Intent(AudioPlayerService.ACTION_CREATE).apply {
            putExtra("url", url)
            putExtra("loop", loop)
            putExtra("autoPlay", autoPlay)
        }
        activity.startService(intent)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent!!.extras!!.getString("event")) {
                    "ready" -> scope.remoteObject(this)?.notify("ready")
                    "error" -> scope.remoteObject(this)?.notify("error", mapOf("code" to intent.extras!!.getInt("code")))
                    "complete" -> scope.remoteObject(this)?.notify("complete")
                }
            }
        }
        activity.registerReceiver(receiver, IntentFilter().apply { addAction(AudioPlayerService.UPDATE) })
    }

    fun play() {
        intent = Intent(AudioPlayerService.ACTION_PLAY)
        activity.startService(intent)
    }

    fun stop() {
        activity.stopService(intent)
    }

    fun pause() {
        intent = Intent(AudioPlayerService.ACTION_PAUSE)
        activity.startService(intent)
    }

    fun seekTo(position: Int) {
        intent = Intent(AudioPlayerService.ACTION_SEEK).apply { putExtra("position", position) }
        activity.startService(intent)
    }

    fun setLoop(loop: Boolean) {
        intent = Intent(AudioPlayerService.ACTION_SET_LOOP).apply { putExtra("loop", loop) }
        activity.startService(intent)
    }
}