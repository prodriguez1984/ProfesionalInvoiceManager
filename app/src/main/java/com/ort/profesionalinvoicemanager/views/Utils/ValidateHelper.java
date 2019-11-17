package com.ort.profesionalinvoicemanager.views.Utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidateHelper {
   private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Matcher matcher;
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private  ValidateHelper(){

    }

    public static boolean  validateEmptyString(String str){
        boolean error = false;
        if ( str == null){
            error = true;
        }
        else{
           error =  TextUtils.isEmpty(Objects.requireNonNull(str));
        }
        return error;
    }

    public static boolean isEmailValid(String eMail) {
        if (eMail != null){
            matcher = pattern.matcher(eMail);
            return matcher.matches();
        }else{
            return false;
        }

    }

    public static boolean passwordLength(String password, int length) {
        return password.length() != length;
    }

    public static boolean comparatePass(String password, String repeatPass) {
        boolean error = false;
        if (password != null && repeatPass != null ){
            if (!password.equals(repeatPass) ){
                error = true;
            }
        }
        return !error;
    }
}
