package com.techdevs.practis;

import android.widget.EditText;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterUtilsTest {

    @Test
    public void checkPasswordsMatch() {
        String pass1 = "password";
        String pass2 = "password";
        EditText editText = null;
        assertFalse(RegisterUtils.checkPasswordsMatch(editText, pass1, pass2));
    }

    @Test
    public void checkPassLength() {
        String pass = "password";
        EditText editText=null;
        assertFalse(RegisterUtils.checkPassLength(editText, pass));
    }

    @Test
    public void checkEmptyPassword() {
        String pass = "password";
        EditText editText = null;
        assertFalse(RegisterUtils.checkEmptyPassword(editText, pass));
    }

    @Test
    public void checkEmptyEmail() {
        String email = "email";
        EditText editText=null;
        assertFalse(RegisterUtils.checkEmptyEmail(editText,email));
    }
}