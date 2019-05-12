package main.java.utilities.chars;

import java.util.Arrays;

public abstract class strings {

    public static final <T> String genericToString(T... values) {
        return Arrays.deepToString(values);
    }


    public static final <T> String genericToStringSeparated(String separator, T... values) {
        String retString = "";
        for (T value : values) {
            retString += value.toString() + separator;
        }
        return retString;
    }


    // TODO : put in formatting utilities
    public static String numbersOnly(String inputString) {
        String numbers = "-+0123456789.";
        String retString = "";
        for (int index = 0; index < inputString.length(); index++) {
            if (numbers.contains("" + inputString.charAt(index))) {
                retString += inputString.charAt(index);
            }
        }
        return retString;
    }


    public static boolean parseBoolean(String value) {
        try {
            Boolean retBoolean = Boolean.parseBoolean(value);
            return retBoolean;
        } catch (Exception e) {
        }
        Integer intValue = Integer.parseInt(strings.numbersOnly(value));
        if (intValue > 0) {
            return true;
        }
        return false;
    }


    public static boolean[] parseBooleanArray(String... values) {
        boolean[] retArray = new boolean[values.length];
        for (int index = 0; index < retArray.length; index++) {
            retArray[index] = parseBoolean(values[index]);
        }
        return retArray;
    }


    public static int[] parseIntArray(String... values) {
        int[] retArray = new int[values.length];
        for (int index = 0; index < retArray.length; index++) {
            retArray[index] = Integer.parseInt(strings.numbersOnly(values[index]));
        }
        return retArray;
    }


    public static final String repeatString(String repeated, String separator, int number) {
        String retString = "";
        for (int index = 0; index < number; index++) {
            retString += repeated + separator;
        }
        return retString;
    }


    private strings() {

    }


}
