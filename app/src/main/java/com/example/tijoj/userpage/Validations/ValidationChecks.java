package com.example.tijoj.userpage.Validations;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tijoj on 5/8/2018.
 */

public class ValidationChecks {
    //REGEX TO CHECK INPUT
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String ZIP_REGEX="^[0-9]{5}(?:-[0-9]{4})?$";
    private static final String HEIGHT_REGES="^[0-9]*((0[0-9])|(1[01]))$";

    //PATTERN OBJECT
    private static Pattern pattern;

    //MATCHER OBJECT
    private static Matcher matcher;


    public static boolean isValidEmail(String email){
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidZip(String zip){
        pattern = Pattern.compile(ZIP_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(zip);
        return matcher.matches();
    }

    public static boolean isAgeValid(String age){
        if(isNumber(age)){
            int currAge = Integer.parseInt(age);
            if(currAge>17 && currAge<100){
                return true;
            }
        }
        return false;
    }

    public static boolean isMinMaxValid(String max, String min){
        int maxAge = Integer.parseInt(max);
        int minAge = Integer.parseInt(min);
        if(minAge>maxAge){
            return false;
        }
        return true;
    }

    public static boolean isNumber(String num){
        return StringUtils.isNumeric(num);
    }

    public static boolean isGenderValid(int gender){
        return gender != 0;
    }

    public static boolean isYearValid(int year){
        if(year<1900 && year>2018){
            return false;
        }
        return true;
    }

    public static boolean isHeightValid(String height){
        pattern = Pattern.compile(HEIGHT_REGES, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(height);
        return matcher.matches();
    }
}
