package com.techdevs.practis;

import android.text.TextUtils;
import android.widget.EditText;

public class RegisterUtils {
    public static boolean checkPasswordsMatch(EditText mConfirmPassword, String password, String confirmPassword) {
        if(!(password.equals(confirmPassword)))
        {
            mConfirmPassword.setError("Passwords don't match");
            return true;
        }
        return false;
    }

    public static boolean checkPassLength(EditText mPassword, String password) {
        if(password.length() < 6){
            mPassword.setError("Password is too short");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyPassword(EditText mPassword, String password) {
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Password is Required!");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyEmail(EditText mEmail, String email) {
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required!");
            return true;
        }
        return false;
    }
}
