package course.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.UsersRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, UserListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getUserList();
    }

    private void getUserList(){
        UsersRequest usersRequest = new UsersRequest.UsersRequestBuilder().build();
        usersRequest.fetchNext(new CometChat.CallbackListener<List<User>>(){
            @Override
            public void onSuccess(List<User> list) {
                updateUI(list);
            }
            @Override
            public void onError(CometChatException e) {

            }
        });
    }

    private void updateUI(List<User> list){
        RecyclerView groupsRecyclerView = findViewById(R.id.userRecyclerView);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter usersAdapter = new course.example.app.UserAdapter(list,this);
        groupsRecyclerView.setAdapter(usersAdapter);
    }
}