package com.example.tijoj.userpage;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tijoj.userpage.POJO.User;
import com.google.gson.Gson;
import com.spark.submitbutton.SubmitButton;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendingActivity extends AppCompatActivity {

    SubmitButton submitButton;
    User user;

    TextView TVStatus;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String base_url = "https://external.dev.pheramor.com/#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        //SETTING UP VIEW
        submitButton = findViewById(R.id.btn_send_sending);
        TVStatus = findViewById(R.id.status);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }

        Gson gson = new Gson();
        final String json = gson.toJson(user);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postRequest(base_url, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("PHERAMORE", "DONST WORK");
                call.cancel();
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TVStatus.setText(response.message().toString());
                        TVStatus.setTextColor(ContextCompat.getColor(SendingActivity.this, R.color.good));
                        TVStatus.setVisibility(View.VISIBLE);
                        ObjectAnimator anim = ObjectAnimator.ofFloat(TVStatus, "Alpha", 0, 1);
                        anim.setDuration(1500);
                        anim.start();
                    }
                });
            }

        });
    }

}
