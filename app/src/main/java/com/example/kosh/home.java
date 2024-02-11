package com.example.kosh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class home extends AppCompatActivity {
    private RecyclerView chatsRV;
    private EditText userMsg;
    private FloatingActionButton sendMsg;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatsAdapter chatRVAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chatsRV=findViewById(R.id.icchats);
        userMsg=findViewById(R.id.user);
        sendMsg=findViewById(R.id.msg);
        chatsModalArrayList= new ArrayList<>();
        chatRVAdapter=new ChatsAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsg.getText().toString().isEmpty()){
                    Toast.makeText(home.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsg.getText().toString());
                userMsg.setText("");
            }
        });
    }
    private void getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=180308&key=y4rNgX5314Uhf7eP&uid=[uid]&msg="+message;
        String BASE_URL="http://api.brainshop.ai/";
        Retrofit retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConertorFactory.create()).build();
        RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call=retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal modal=response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();

            }
        });
    }
}