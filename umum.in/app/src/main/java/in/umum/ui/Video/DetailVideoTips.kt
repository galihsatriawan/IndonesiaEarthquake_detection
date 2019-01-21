package `in`.umum.ui.Video

import `in`.umum.R
import `in`.umum.model.Video.VideoModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class DetailVideoTips : AppCompatActivity() {
    lateinit var video : VideoModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_video_tips)
        var videoId : String =""
        if(intent!=null){
            videoId = intent.getStringExtra("videoId")
        }
        var youtubePlayer = findViewById<YouTubePlayerView>(R.id.youtubesupportfragment)
        playVideo(videoId, youtubePlayer)

    }
    private fun playVideo(videoId:String , view : YouTubePlayerView ){
        view.initialize("AIzaSyDqictihvR0XQBzTBgzSd0BiCKOKlhLN-w",
                    object : YouTubePlayer.OnInitializedListener{
                        override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, p2: Boolean) {
                            player?.cueVideo(videoId)
                            player?.setFullscreen(true)
                            player?.play()
                        }

                        override fun onInitializationFailure(provider: YouTubePlayer.Provider?, player: YouTubeInitializationResult?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    }
                )
    }
}
