package com.example.tijoj.userpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.unstoppable.submitbuttonview.SubmitButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tijoj.userpage.Validations.ValidationChecks.isValidEmail;

public class MainActivity extends AppCompatActivity {

    //BINDING VIEWS USING BUTTERKNIFE
    @BindView(R.id.edit_text_usr_email_main) EditText userNameET;
    @BindView(R.id.edit_text_usr_password_main) EditText passwordET;
    @BindView(R.id.coordinator_main) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.card_view_main) CardView cardView;
    @BindView(R.id.btn_register_main)
    SubmitButton regBtn;

    Animation cardSlideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //ANIMATION INITIALIZATION
        cardSlideDown = AnimationUtils.loadAnimation(this, R.anim.slidedowncard);

        cardView.setAnimation(cardSlideDown);


        //SETTING CLICK LISTENER TO BUTTON PRESS
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GETTTING TEXT FROM EDITTEXT
                final String usrEmail = userNameET.getText().toString().trim();
                final String usrPassword = passwordET.getText().toString().trim();

                userNameET.onEditorAction(EditorInfo.IME_ACTION_DONE);
                passwordET.onEditorAction(EditorInfo.IME_ACTION_DONE);

//                boolean valid = true;

                final boolean valid = chkusrpass(usrEmail, usrPassword);

                Handler handler = new Handler();

                if (valid) {

                    regBtn.doResult(valid);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);

                            //PASSING IN EMAIL AND PASSWORD AS STRING TO ACTIVITY
                            detailIntent.putExtra("EMAIL", usrEmail);
                            detailIntent.putExtra("PASSWORD", usrPassword);

                            startActivity(detailIntent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }, 1500);


                }else{
                    regBtn.doResult(valid);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            regBtn.reset();
                        }
                    }, 1500);
                }
            }
        });
    }


    //HELPER TO CHECK USR AND PASSWORD
    public boolean chkusrpass(String usr, String pass){

        //IF USERNAME OR PASSWORD IS WRONG A SNACKBAR IS SHOWN
        Snackbar snackbar;

        userNameET.setBackgroundColor(Color.TRANSPARENT);
        passwordET.setBackgroundColor(Color.TRANSPARENT);


        //VALIDATING EMAIL
        if(TextUtils.isEmpty(usr) || !isValidEmail(usr)){
            String msg = "Check Email";
            snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT);
            snackbar.show();
            userNameET.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            return false;
        }

        //VALIDATING PASSWORD
        else if(TextUtils.isEmpty(pass)){
            String msg = "Check Password";
            snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT);
            snackbar.show();
            passwordET.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            return false;
        }
        return true;
    }

}
