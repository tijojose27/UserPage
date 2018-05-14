package com.example.tijoj.userpage;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tijoj.userpage.POJO.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity {

    public User user;

    @BindView(R.id.coordinator_summary)
    CoordinatorLayout summaryCoordinatorLayout;
    @BindView(R.id.collapsing_toolbar_summary)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.gender_text_view_summary)
    TextView TVGender;
    @BindView(R.id.age_text_view_summary)
    TextView TVAge;
    @BindView(R.id.zip_text_view_summary)
    TextView TVZip;
    @BindView(R.id.summary_text_view)
    TextView TVSummary;
    @BindView(R.id.height_text_view_summary)
            TextView TVHeight;
    @BindView(R.id.gender_pref_text_view_summary)
            TextView TVGenderPref;
    @BindView(R.id.age_range_text_view_summary)
            TextView TVAgeRange;
    @BindView(R.id.religion_pref_text_view_summary)
            TextView TVReligion;
    @BindView(R.id.race_pref_text_view_summary)
            TextView TVRace;
    @BindView(R.id.fab_send)
    FloatingActionButton FABSend;

    ImageView IVprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //BINDING
        ButterKnife.bind(this);
        IVprofile = findViewById(R.id.image_view_detail_logo);

//        GETTING INFO FROM ACTIVITY
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("USER");
        }


        //SETTTING UP DETAILS
        collapsingToolbarLayout.setTitle(user.getName());
        TVGender.setText(user.getGender());
        TVZip.setText("Zip : "+user.getZip());
        IVprofile.setImageURI(Uri.parse(user.getProfilePicUri().toString()));
        TVAge.setText(user.getAge());
        TVGenderPref.setText("Gender Preference : "+user.getGenderPref());
        TVHeight.setText("Height : "+user.getHeight());

        TVAgeRange.setText("Age Range : "+user.getAgeMin()+" - "+user.getAgeMax());
        if(!user.getReligion().equals("N/A")){
            TVReligion.setText("Religion : "+user.getReligion());
            TVReligion.setVisibility(View.VISIBLE);
        }
        if(!user.getRace().equals("N/A")){
            TVRace.setText("Race : "+user.getRace());
            TVRace.setVisibility(View.VISIBLE);
        }

        String selfSummaryText = "Hi, I am a "+ifRelegion(user.getReligion())+ifRace(user.getRace())+user.getGender().toLowerCase()+" looking for a "+user.getGenderPref().toLowerCase()+" between the ages "+
                "of "+user.getAgeMin()+" - "+user.getAgeMax();
        TVSummary.setText(selfSummaryText);


        FABSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, SendingActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                SummaryActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public String ifRelegion(String relegion){
        if(relegion.equals("N/A")){
            return "";
        }
        return relegion+" ";

    }
    public String ifRace(String race){
        if(race.equals("N/A")){
            return "";
        }
        return race+" ";

    }
}
