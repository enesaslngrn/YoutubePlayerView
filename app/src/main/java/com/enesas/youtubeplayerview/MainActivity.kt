package com.enesas.youtubeplayerview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.enesas.youtubeplayerview.databinding.ActivityMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var youTubePlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = "MzIEonb9Fiw"
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg" // Thumbnail böyle alınıyor youtube'da.

        Picasso.get().load(thumbnailUrl).into(binding.imageViewThumbnail)

        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                this@MainActivity.youTubePlayer = youTubePlayer
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Log.e("YoutubePlayer", "Error: $error")
            }
        })

        binding.btnPlayVideo.setOnClickListener {
            binding.imageViewThumbnail.visibility = View.GONE
            binding.btnPlayVideo.visibility = View.GONE
            binding.youtubePlayerView.visibility = View.VISIBLE
            youTubePlayer?.loadVideo(videoId, 0F)
        }
    }
}

