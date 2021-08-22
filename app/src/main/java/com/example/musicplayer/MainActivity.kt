package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var player: MediaPlayer
    lateinit var  seekBar: SeekBar
    lateinit var  btnPlay: ImageView
    var currentSongNumber: Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.btnPlay)
        val btnPrev: ImageView = findViewById(R.id.btnPrev)
        val btnNext: ImageView = findViewById(R.id.btnNext)
        seekBar = findViewById(R.id.seekBar)

        player = MediaPlayer.create(this, R.raw.songa)
        seekBar.max = player.duration / 1000

        btnPlay.setOnClickListener {
            if(player.isPlaying)
            {
                player.pause()
                btnPlay.setImageResource(R.drawable.ico_play)
            }
            else {
                setPlayer(currentSongNumber)
                btnPlay.setImageResource(R.drawable.ico_pause)
            }
        }

        btnPrev.setOnClickListener {
            if(player.isPlaying)
            {
                player.pause()
                player.release()
            }

            if(currentSongNumber>=1)
                currentSongNumber--

            setPlayer(currentSongNumber)
        }

        btnNext.setOnClickListener {
            if(player.isPlaying)
            {
                player.pause()
                player.release()
            }


            if(currentSongNumber<=3)
                currentSongNumber++

            setPlayer(currentSongNumber)
        }



        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                if(player.isPlaying)
                {
                    player.seekTo(progress*1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })


    }

    private fun setPlayer(songNumber: Int) {
        if(songNumber<1)
        {
            Toast.makeText(this, "End of playlist", Toast.LENGTH_LONG).show()
            player = MediaPlayer.create(this, R.raw.songa)
            currentSongNumber=1
            btnPlay.setImageResource(R.drawable.ico_play)
            return
        }
        if(songNumber>3)
        {
            Toast.makeText(this, "End of playlist", Toast.LENGTH_LONG).show()
            player = MediaPlayer.create(this, R.raw.songc)
            currentSongNumber=3
            btnPlay.setImageResource(R.drawable.ico_play)
            return
        }

        if(songNumber==1) {
            player = MediaPlayer.create(this, R.raw.songa)
        }
        else if(songNumber==2)
        {
            player = MediaPlayer.create(this, R.raw.songb)
        }
        else if(songNumber==3)
        {
            player = MediaPlayer.create(this, R.raw.songc)
        }
        seekBar.max = player.duration / 1000

        if(player.isPlaying)
        {
            player.pause()
            btnPlay.setImageResource(R.drawable.ico_play)
        }
        else {
            player.start()
            btnPlay.setImageResource(R.drawable.ico_pause)
        }
    }
}

