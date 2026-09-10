package com.fsociety.tictactoe

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager(context: Context) {

    private val soundPool: SoundPool

    private val clickSound: Int
    private val winSound: Int
    private val drawSound: Int

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        clickSound = soundPool.load(context, R.raw.click, 1)
        winSound = soundPool.load(context, R.raw.win, 1)
        drawSound = soundPool.load(context, R.raw.draw, 1)
    }

    fun playClick() {
        soundPool.play(
            clickSound,
            1f,
            1f,
            1,
            0,
            1f
        )
    }

    fun playWin() {
        soundPool.play(
            winSound,
            1f,
            1f,
            1,
            0,
            1f
        )
    }

    fun playDraw() {
        soundPool.play(
            drawSound,
            1f,
            1f,
            1,
            0,
            1f
        )
    }

    fun release() {
        soundPool.release()
    }
}