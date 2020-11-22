package course.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.MessagesRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.CustomMessage;
import com.cometchat.pro.models.MediaMessage;
import com.cometchat.pro.models.TextMessage;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private String userId;
    private MessagesListAdapter<IMessage> adapter;

    public static void start(Context context, String userId) {
        Intent starter = new Intent(context, ChatActivity.class);
        starter.putExtra(Constants.GROUP_ID,userId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        if (intent != null){
            userId = intent.getStringExtra(Constants.GROUP_ID);
        }

        initViews();
        addListener();
        fetchPreviousMessgaes();
    }

    private void fetchPreviousMessgaes() {
        MessagesRequest messagesRequest = new MessagesRequest.MessagesRequestBuilder().setUID(userId).build();
        messagesRequest.fetchPrevious(new CometChat.CallbackListener<List<BaseMessage>>() {
            @Override
            public void onSuccess(List<BaseMessage> baseMessages) {
                addMessages(baseMessages);
            }

            @Override
            public void onError(CometChatException e) {

            }
        });
    }

    private void addMessages(List<BaseMessage> baseMessages) {
        List<IMessage> list = new ArrayList();
        for (BaseMessage message : baseMessages){
            if (message instanceof TextMessage){
                list.add(new MessageWrapper((TextMessage)message));
            }
        }
        adapter.addToEnd(list,true);
    }

    private void addListener() {
        String listenerID = "listener 1";
        CometChat.addMessageListener(listenerID, new CometChat.MessageListener() {
            @Override
            public void onTextMessageReceived(TextMessage textMessage) {
                addMessage(textMessage);
            }
            @Override
            public void onMediaMessageReceived(MediaMessage mediaMessage) {
            }
            @Override
            public void onCustomMessageReceived(CustomMessage customMessage) {
            }
        });
    }

    private void initViews(){
        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);
        inputView.setInputListener(input -> {
            sendMessage(input.toString());
            return true;
        });
        String senderId = CometChat.getLoggedInUser().getUid();
        ImageLoader imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);
        adapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesList.setAdapter(adapter);
    }

    private void sendMessage(String message) {
        TextMessage textMessage = new TextMessage(userId, message, CometChatConstants.RECEIVER_TYPE_USER);
        CometChat.sendMessage(textMessage, new CometChat.CallbackListener<TextMessage>() {
            @Override
            public void onSuccess(TextMessage textMessage) {
                addMessage(textMessage);
            }
            @Override
            public void onError(CometChatException e) {
            }
        });
    }

    private void addMessage(TextMessage textMessage){
        adapter.addToStart(new MessageWrapper(textMessage), true);
    }
}