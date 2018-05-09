package com.example.tijoj.userpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.tijoj.userpage.POJO.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tijoj.userpage.Validations.ValidationChecks.isValidEmail;

public class MainActivity extends AppCompatActivity {


    //BINDING VIEWS USING BUTTERKNIFE
    @BindView(R.id.edit_text_usr_email_main) EditText userNameET;
    @BindView(R.id.edit_text_usr_password_main) EditText passwordET;
    @BindView(R.id.btn_register_main) Button regBtn;
    @BindView(R.id.coordinator_main) CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //SETTING CLICK LISTENER TO BUTTON PRESS
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GETTTING TEXT FROM EDITTEXT
                String usrEmail = userNameET.getText().toString().trim();
                String usrPassword = passwordET.getText().toString().trim();

                userNameET.onEditorAction(EditorInfo.IME_ACTION_DONE);
                passwordET.onEditorAction(EditorInfo.IME_ACTION_DONE);

                boolean valid = chkusrpass(usrEmail, usrPassword);
                if(valid){
                    Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);

                    //PASSING IN EMAIL AND PASSWORD AS STRING TO ACTIVITY
                    detailIntent.putExtra("EMAIL", usrEmail);
                    detailIntent.putExtra("PASSWORD", usrPassword);

                    startActivity(detailIntent);
                }

            }
        });
    }

    //HELPER TO CHECK USR AND PASSWORD
    public boolean chkusrpass(String usr, String pass){

        //IF USERNAME OR PASSWORD IS WRONG A SNACKBAR IS SHOWN
        Snackbar snackbar;

        userNameET.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        passwordET.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));


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
