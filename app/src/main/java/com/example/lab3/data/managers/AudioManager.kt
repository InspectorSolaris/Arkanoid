package com.example.lab3.data.managers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

object AudioManager {

    private var playlist = ArrayList<MediaPlayer>()
    private var audioList = ArrayList<MediaPlayer>()
    private var currentTrack = -1

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()

    fun initData(
        context: Context,
        playlistPaths: List<String>,
        audioPaths: List<String>
    ) {
        stopAll()
        releaseAll()

        playlist.clear()
        audioList.clear()
        currentTrack = -1

        audioPaths.forEach {
            initAudio(
                context,
                it
            )
        }

        initPlaylist(
            context,
            playlistPaths
        )
    }

    fun stopAll() {
        playlist.forEach { it.stop() }
        audioList.forEach { it.stop() }
    }

    fun releaseAll() {
        playlist.forEach { it.release() }
        audioList.forEach { it.release() }
    }

    fun startPlaylist() {
        if (currentTrack == -1 && playlist.isNotEmpty()) {
            currentTrack = 0
            playlist[currentTrack].start()
        }
    }

    fun pausePlaylist() {
        if (currentTrack != -1 && playlist.isNotEmpty()) {
            playlist[currentTrack].pause()
        }
    }

    fun resumePlaylist() {
        if (currentTrack != -1 && playlist.isNotEmpty()) {
            playlist[currentTrack].start()
        }
    }

    fun startAudio(
        id: Int
    ) {
        if (id in audioList.indices) {
            audioList[id].start()
        }
    }

    private fun initAudio(
        context: Context,
        path: String
    ) {
        audioList.add(
            MediaPlayer().also {
                it.setDataSource(context, Uri.parse(path))
                it.setAudioAttributes(audioAttributes)
                it.prepare()
            }
        )
    }

    private fun initPlaylist(
        context: Context,
        paths: List<String>
    ) {
        paths.forEach { path ->
            playlist.add(
                MediaPlayer().also {
                    it.setDataSource(context, Uri.parse(path))
                    it.setAudioAttributes(audioAttributes)
                    it.prepare()

                    it.setOnCompletionListener {
                        currentTrack =
                            (playlist.indexOfFirst { mp -> mp == it } + 1) % playlist.size

                        playlist[currentTrack].start()
                    }
                }
            )
        }
    }

}