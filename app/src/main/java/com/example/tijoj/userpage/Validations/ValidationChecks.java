package com.example.tijoj.userpage.Validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tijoj on 5/8/2018.
 */

public class ValidationChecks {
    //REGEX TO CHECK EMAIL
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    //PATTERN OBJECT
    private static Pattern pattern;

    //MATCHER OBJECT
    private static Matcher matcher;


    public static boolean isValidEmail(String email){
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
