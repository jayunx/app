package course.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;

public class ChatMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        initComeChat();
        intiViews();
    }


    private void intiViews(){
        EditText userIdEditText = findViewById(R.id.userIdEditText);
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(view -> CometChat.login(userIdEditText.getText().toString(), Constants.API_KEY, new CometChat.CallbackListener<User>(){
            @Override
            public void onSuccess(User user) {
                redirectToUserListScreen();
            }
            @Override
            public void onError(CometChatException e) {

            }
        }));

    }

    public void redirectToUserListScreen(){
        UserListActivity.start(this);
    }


    private void initComeChat() {
        String appID = Constants.APP_ID; // Replace with your App ID
        String region = "us"; // Replace with your App Region ("eu" or "us")

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();

        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {

            }
            @Override
            public void onError(CometChatException e) {

            }
        });
    }
}