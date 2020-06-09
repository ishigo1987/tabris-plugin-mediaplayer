package ru.fax1ty.tabris.audioplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.PowerManager

class AudioPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private var player: MediaPlayer? = null

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_PAUSE -> {
                player!!.pause()
                Utils.setPosition(applicationContext, player!!.currentPosition)
                Utils.setIsPlaying(applicationContext, false)
            }
            ACTION_PLAY -> {
                player!!.start()
            }
            ACTION_SEEK -> {
                Utils.setPosition(applicationContext, intent.extras!!.getInt("position", 0))
                player!!.seekTo(Utils.getPosition(applicationContext))
            }
            ACTION_SET_LOOP -> {
                Utils.setLoop(applicationContext, intent.extras!!.getBoolean("loop", false))
                player!!.isLooping = Utils.getLoop(applicationContext)
            }
            else -> {
                player = MediaPlayer()
                        .apply {
                            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                        }
                Utils.setURL(applicationContext, if (intent.extras != null) intent.extras!!.getString("url", "") else Utils.getURL(applicationContext)!!)
                Utils.setLoop(applicationContext, if (intent.extras != null) intent.extras!!.getBoolean("loop", false) else Utils.getLoop(applicationContext))
                Utils.setAutoPlay(applicationContext, if (intent.extras != null) intent.extras!!.getBoolean("autoPlay", false) else Utils.getAutoPlay(applicationContext))
                player!!.setDataSource(Utils.getURL(applicationContext))
                player!!.isLooping = Utils.getLoop(applicationContext)
                player!!.prepareAsync()
                player!!.setOnPreparedListener(this)
                player!!.setOnErrorListener(this)
                player!!.setOnCompletionListener(this)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.setPosition(applicationContext, player!!.currentPosition)
        player!!.stop()
        player!!.release()
    }

    override fun onLowMemory() {
        Utils.setPosition(applicationContext, player!!.currentPosition)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (Utils.getAutoPlay(applicationContext)) {
            player!!.start()
            Utils.setIsPlaying(applicationContext, true)
        }
        sendBroadcast(Intent(UPDATE).apply { putExtra("event", "ready") })
    }

    companion object {
        val ACTION_CREATE = this::class.java.`package`?.name + ".CREATE"
        val ACTION_PLAY = this::class.java.`package`?.name + ".PLAY"
        val ACTION_PAUSE = this::class.java.`package`?.name + ".PAUSE"
        val ACTION_SEEK = this::class.java.`package`?.name + ".SEEK"
        val ACTION_SET_LOOP = this::class.java.`package`?.name + ".SET_LOOP"
        val UPDATE = this::class.java.`package`?.name + ".UPDATE"
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        sendBroadcast(Intent(UPDATE).apply {
            putExtra("event", "error")
            putExtra("code", what)
        })
        return true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        sendBroadcast(Intent(UPDATE).apply {
            putExtra("event", "complete")
        })
    }


}