package course.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void news(View view){
        Intent intent = new Intent("action.newsActivity");
        startActivity(intent);
    }

    public void video(View view){
        Intent intent = new Intent("action.videoActivity");
        startActivity(intent);
    }
    public void chatmain(View view){
        Intent intent = new Intent("action.chatMainActivity");
        startActivity(intent);
    }
}