package course.example.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
        String url = "android.resource://"+getPackageName()+"/"+R.raw.mov1;
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
    }

    public void video1(View view){
        videoView.start();
    }

    public void video2(View view){
        videoView.pause();
    }

    public void video3(View view){ videoView.resume();
    }
}