package com.ort.profesionalinvoicemanager.views.Utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Objects;

public final class ValidateHelper {

    private  ValidateHelper(){

    }

    public static boolean  validateEmptyString(String str){
        return  !TextUtils.isEmpty(Objects.requireNonNull(str));
    }

    public static boolean isEmailValid(String eMail) {
        return !Patterns.EMAIL_ADDRESS.matcher(eMail).matches();
    }

    public static boolean passwordLength(String password, int length) {
        return password.length() != length;
    }

}
