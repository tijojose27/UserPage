package com.example.tijoj.userpage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tijoj.userpage.POJO.User;
import com.example.tijoj.userpage.Validations.ValidationChecks;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private User user;

    int gend;
    int genderPref;
    int usrYear;
    int usrMonth;
    int usrDay;
    String date;
    String race;
    String religion;
    public boolean imagePicked;
    public Uri profileImgUri;

    public static int PICK_IMG;

    @BindView(R.id.coordinator_detail)
    CoordinatorLayout DetailCorrdinator;
    @BindView(R.id.edit_text_detail_name)
    TextView ETName;
    @BindView(R.id.edit_text_detail_height)
    TextView ETHeight;
    @BindView(R.id.edit_text_detail_zip)
    TextView ETZip;
    @BindView(R.id.spinner_detail_gender)
    Spinner SGender;
    @BindView(R.id.spinner_detail_gender_pref)
    Spinner SGenderPref;
    @BindView(R.id.btn_detail_dob)
    Button ButDob;
    @BindView(R.id.edit_text_detail_age_max)
    TextView ETAgeMax;
    @BindView(R.id.edit_text_detail_age_min)
    TextView ETAgeMin;
    @BindView(R.id.spinner_detail_race)
    Spinner SRace;
    @BindView(R.id.spinner_detail_religion)
    Spinner SReligion;
    @BindView(R.id.btn_save_profile_detail)
    Button ButSubmit;
    @BindView(R.id.image_view_profile)
    ImageView IVProfile;
    @BindView(R.id.scrollView_detail)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //BINDING OBJECTS
        ButterKnife.bind(this);

        //SETTING UP DEFAULT PARAMS
        getLoginDetails();
        gend = 0;
        genderPref = 0;
        imagePicked = false;

        //SETTING ARRAY FOR GENDER
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_item);
        genderAdapter.setDropDownViewResource(R.layout.spinner_item);

        //SETTING ARRAY FOR RACE
        ArrayAdapter<CharSequence> raceAdapter = ArrayAdapter.createFromResource(this, R.array.race, R.layout.spinner_item);
        raceAdapter.setDropDownViewResource(R.layout.spinner_item);

        //SETTING ARRAY FOR RELEGION
        ArrayAdapter<CharSequence> relegionAdapter = ArrayAdapter.createFromResource(this, R.array.relegion, R.layout.spinner_item);
        relegionAdapter.setDropDownViewResource(R.layout.spinner_item);


        //SETTING SPINNERS
        SGender.setAdapter(genderAdapter);
        SGender.setOnItemSelectedListener(this);

        SGenderPref.setAdapter(genderAdapter);
        SGenderPref.setOnItemSelectedListener(this);

        SRace.setAdapter(raceAdapter);
        SRace.setOnItemSelectedListener(this);

        SReligion.setAdapter(relegionAdapter);
        SReligion.setOnItemSelectedListener(this);


        //DOB DATEPICKER WIDGET
        ButDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        usrYear = mYear;
                        usrDay = mDay;
                        usrMonth = mMonth + 1;
                        date = usrMonth + "/" + mDay + "/" + mYear;
                        ButDob.setText(date);
                    }
                }, day, month, year);

                datePickerDialog.show();
            }
        });

        //IMAGE UPLOAD
        IVProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, PICK_IMG);
            }
        });


        ButSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkAllInput()) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollY", 0);
                    animator.setDuration(800);

                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Intent summaryIntent = new Intent(getBaseContext(), SummaryActivity.class);
                            summaryIntent.putExtra("USER", user);
                            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                                    .makeSceneTransitionAnimation(DetailActivity.this, IVProfile, ViewCompat.getTransitionName(IVProfile));
                            startActivity(summaryIntent, activityOptionsCompat.toBundle());
                        }
                    });
                }
            }
        });

    }

    //VALIDATE INPUT AND ADD IT TO USER
    private boolean chkAllInput() {
        //CHECK NAME
        String name = ETName.getText().toString().trim();
        clearBackground(ETName);
        if (TextUtils.isEmpty(name)) {
            ETName.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            Snackbar.make(DetailCorrdinator, "Check Name", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setName(name);

        //CHECK ZIP
        String zip = ETZip.getText().toString().trim();
        clearBackground(ETZip);
        if (!ValidationChecks.isValidZip(zip)) {
            ETZip.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            Snackbar.make(DetailCorrdinator, "Check Zip", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setZip(Integer.parseInt(zip));

        //CHECK AGE
        String maxAge = ETAgeMax.getText().toString().trim();
        String minAge = ETAgeMin.getText().toString().trim();
        clearBackground(ETAgeMin);
        if (!ValidationChecks.isAgeValid(minAge)) {
            ETAgeMin.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            Snackbar.make(DetailCorrdinator, "Check Min Age", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationChecks.isAgeValid(maxAge)) {
            ETAgeMax.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            Snackbar.make(DetailCorrdinator, "Check Max Age", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationChecks.isMinMaxValid(maxAge, minAge)) {
            ETAgeMin.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            ETAgeMax.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
            Snackbar.make(DetailCorrdinator, "Check Age Range", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setAgeMax(Integer.parseInt(maxAge));
        user.setAgeMin(Integer.parseInt(minAge));

        //CHECK GENDER
        if (!ValidationChecks.isGenderValid(gend)) {
            Snackbar.make(DetailCorrdinator, "Check Gender", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setGender(gend);

        //CHECK GENDER PREF
        if (!ValidationChecks.isGenderValid(genderPref)) {
            Snackbar.make(DetailCorrdinator, "Check Gender Prefrence", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setGenderPref(genderPref);

        //CHECK DOB
        if (!ValidationChecks.isYearValid(usrYear)) {
            Snackbar.make(DetailCorrdinator, "Check Date of Birth", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        //CHECK HEIGHT
        String height = ETHeight.getText().toString().trim();
        if (TextUtils.isEmpty(height)) {
            Snackbar.make(DetailCorrdinator, "Check height", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationChecks.isHeightValid(height)) {
            Snackbar.make(DetailCorrdinator, "Height format example 5'5\" is 505", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setHeight(height);

        if (!imagePicked) {
            Snackbar.make(DetailCorrdinator, "Profile picture needs to be set", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        user.setProfilePicUri(profileImgUri);

        user.setDob(date);

        user.setRace(race);

        user.setReligion(religion);


        return true;
    }

    //CLEARING BACKGROUND FOR VIEWS IF ERROR
    public void clearBackground(View view) {
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    public void getLoginDetails() {

        //getting USER INFO FROM PREVIOUS ACTIVITY
        String usrEmail = "defaul@default.com";
        String usrPassword = "default";
        //GETTING EXTRA FORM INTENT
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (!getIntent().getStringExtra("EMAIL").isEmpty()) {
                usrEmail = getIntent().getStringExtra("EMAIL");
            }
            if (!getIntent().getStringExtra("PASSWORD").isEmpty()) {
                usrPassword = getIntent().getStringExtra("PASSWORD");
            }
        }

        //CREATING A USER OBJECT
        user = new User(usrEmail, usrPassword);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.spinner_detail_gender) {
            gend = spinner.getSelectedItemPosition();

        }
        if (spinner.getId() == R.id.spinner_detail_gender_pref) {
            genderPref = spinner.getSelectedItemPosition();
        }
        if (spinner.getId() == R.id.spinner_detail_race) {
            race = spinner.getItemAtPosition(position).toString();
        }
        if (spinner.getId() == R.id.spinner_detail_religion) {
            religion = spinner.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Spinner spinner = (Spinner) adapterView;
        spinner.setBackgroundColor(ContextCompat.getColor(this, R.color.warning));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImgUri = imageUri;
                IVProfile.setImageBitmap(selectedImage);
                imagePicked = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Snackbar.make(DetailCorrdinator, "Image could'nt be loaded", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(DetailCorrdinator, "No Image was Picked", Snackbar.LENGTH_SHORT).show();
        }
    }
}
