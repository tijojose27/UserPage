package com.example.tijoj.userpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.textView) TextView usrTV;
    @BindView(R.id.textView2) TextView passTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        String usrEmail="defaul@default.com";
        String usrPassword = "default";

        if(!getIntent().getExtras().isEmpty()){
            if(!getIntent().getStringExtra("EMAIL").isEmpty()){
                usrEmail = getIntent().getStringExtra("EMAIL");
            }
            if(!getIntent().getStringExtra("PASSWORD").isEmpty()){
                usrPassword = getIntent().getStringExtra("PASSWORD");
            }
        }

        usrTV.setText(usrEmail);
        passTV.setText(usrPassword);
    }
}
