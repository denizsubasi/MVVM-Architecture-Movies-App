package com.denizsubasi.moviesapp.ui.flow.moviesdetails.playerflow

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.denizsubasi.moviesapp.R
import com.denizsubasi.moviesapp.di.ViewModelFactory
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.util.ApplicationConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_video_player.*
import javax.inject.Inject


class VideoPlayerActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VideoPlayerViewModel

    var movieObject: MovieObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoPlayerViewModel::class.java)
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView?.enterFullScreen()
        intent.extras?.let {
            movieObject = it.getParcelable(ApplicationConstants.MOVIE_ITEM)
            movieObject?.let {
            } ?: onBackPressed()
        }
        youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            }

            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) {
            }

            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) {
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
            }

            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(movieObject?.videos?.videoList?.first()?.key ?: "", 0f)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

}