package com.example.test_utils;

import java.util.regex.Pattern;

public class Validators {
    private final static Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean isIdValid(String input) {
        var m = ID_PATTERN.matcher(input);
        return m.matches();
    }
}
