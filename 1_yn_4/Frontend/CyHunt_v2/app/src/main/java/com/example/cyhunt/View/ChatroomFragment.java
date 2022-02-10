package com.example.cyhunt.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyhunt.Message;
import com.example.cyhunt.MessagesListAdapter;
import com.example.cyhunt.R;
import com.example.cyhunt.Utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;


import java.net.URI;
import java.net.URISyntaxException;

import androidx.appcompat.app.AppCompatActivity;


//import src.com.codebutler.android_websockets.WebSocketClient;

/**
 * Ravi Tamada (2017) Android Building Group Chat App using Sockets
 * [Source Code] https://www.androidhive.info/2014/10/android-building-group-chat-app-using-sockets-part-2/
 */

public class ChatroomFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    public Button btnSend;
    private EditText inputMsg;

    private WebSocketClient client;

    // Chat messages list adapter
    private MessagesListAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    private Utils utils;
    private String mParam1;
    private String mParam2;

    // Client name
    private String name = null;
    private int userID = 0;
    private SharedPreferences sharedPreferences;

    private WebSocketClient mWebSocketClient;

    // JSON flags to identify the kind of JSON response
    private static final String TAG_SELF = "self", TAG_NEW = "new",
            TAG_MESSAGE = "message", TAG_EXIT = "exit";

    public static ChatroomFragment newInstance(String param1, String param2) {
        ChatroomFragment fragment = new ChatroomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ChatroomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        btnSend = (Button)view.findViewById(R.id.btnSend);
        inputMsg = (EditText)view.findViewById(R.id.inputMsg);
        listViewMessages = (ListView)view.findViewById(R.id.list_view_messages);
        utils = new Utils(getActivity().getApplicationContext());
        name =  sharedPreferences.getString("USERNAME", "GUEST");
        userID =  sharedPreferences.getInt("USER_ID", -1);
        connectWebSocket();
        // Getting the person name from previous screen
        //Intent i = getIntent();
        //name = i.getStringExtra("name");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sending message to web socket server
                try {
                    mWebSocketClient.send(utils.getSendMessageJSON(inputMsg.getText()
                            .toString()));
                    // Clearing the input filed once message was sent
                    inputMsg.setText("");
                }
                catch(Exception e)
                {
                    Log.e("WEBSOCKET_ERROR", e.toString());
                }
            }
        });

        listMessages = new ArrayList<Message>();

        adapter = new MessagesListAdapter(getActivity().getApplicationContext(), listMessages);
        listViewMessages.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    /**
     * Appending message to list view
     * */
    private void appendMessage(final Message m) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                listMessages.add(m);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showToast(final String message) {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getActivity().getApplicationContext(), message,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void connectWebSocket() {
        URI uri;
        try {
            /*
             * To test the clientside without the backend, simply connect to an echo server such as:
             *  "ws://echo.websocket.org"
             */
            uri = new URI("ws://coms-309-020.cs.iastate.edu:8080/chat/" + name); // 10.0.2.2 = localhost
            // uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String messages) {
                while (messages.length() > 10) {
                    String msg = messages.substring(0, messages.indexOf('}'));
                    Log.i("Websocket", "Message Received" + msg);
                    // Appends the message received to the previous messages
                    try {
                        String senderName = msg.substring(0, msg.indexOf('{') - 2);
                        String mess = msg.substring(msg.indexOf("message\":\"") + 10, msg.length() - 1);
                        boolean yourself = false;
                        if (senderName.equals(name)) {
                            yourself = true;
                        }
                        Log.d("usernames", "Sender(" + senderName + "), Self("+ name + ")");
                        Message m = new Message(senderName, mess, yourself);
                        appendMessage(m);
                    } catch (Exception e) {
                        Log.d("Parsing exception", e.toString());
                    }
                    messages = messages.substring(msg.length() + 2, messages.length());
                }
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }


}