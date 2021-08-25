package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var player: MediaPlayer
    lateinit var  seekBar: SeekBar
    lateinit var  btnPlay: ImageView
    var currentSongNumber: Int =1

    lateinit var tvTotalDuration: TextView
    lateinit var tvPassedDuration: TextView
    lateinit var tvRemainingDuration: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnPlay = findViewById(R.id.btnPlay)
        val btnPrev: ImageView = findViewById(R.id.btnPrev)
        val btnNext: ImageView = findViewById(R.id.btnNext)
        seekBar = findViewById(R.id.seekBar)

        tvTotalDuration = findViewById(R.id.tvTotalDuration)
        tvPassedDuration = findViewById(R.id.tvPassedDuration)
        tvRemainingDuration = findViewById(R.id.tvRemainingDuration)


        player = MediaPlayer.create(this, R.raw.songa)
        seekBar.max = player.duration / 1000




        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object: Runnable{
            override fun run() {

                val totalDuration=player.duration/1000
                val remainingDuration = (player.duration - player.currentPosition)/1000
                val passedDuration = totalDuration - remainingDuration

                seekBar.progress = passedDuration

                tvTotalDuration.text = ""+(totalDuration/60)+":"+(totalDuration%60)
                tvPassedDuration.text = ""+(passedDuration/60)+":"+(passedDuration%60)
                tvRemainingDuration.text = ""+(remainingDuration/60)+":"+(remainingDuration%60)

                mainHandler.postDelayed(this, 1000)
            }
        })

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

