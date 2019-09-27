package com.daeseong.businfo;

public class String_util {

    private String_util() {
        throw new UnsupportedOperationException("String_util");
    }

    public static boolean isNumeric(String sInput) {
        String regex = "[\\d]*$";
        return sInput.matches(regex);
    }
}
