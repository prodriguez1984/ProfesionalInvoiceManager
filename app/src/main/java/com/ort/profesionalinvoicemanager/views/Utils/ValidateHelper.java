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
        return  !TextUtils.isEmpty(Objects.requireNonNull(str));
    }

    public static boolean isEmailValid(String eMail) {
        matcher = pattern.matcher(eMail);
        return matcher.matches();
    }

    public static boolean passwordLength(String password, int length) {
        return password.length() != length;
    }

}
