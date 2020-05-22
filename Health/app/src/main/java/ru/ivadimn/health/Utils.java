package ru.ivadimn.health;

import java.text.SimpleDateFormat;

/**
 * Created by vadim on 26.08.2017.
 */

public class Utils {
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static String string(long l) {
        return String.valueOf(l);
    }
    public static String string(int i) {
        return String.valueOf(i);
    }
    public static String string(byte b) {
        return String.valueOf(b);
    }

    public static String stringTime(long t) {
        return timeFormat.format(t);
    }

    public static String stringDate(long d) {
        return dateFormat.format(d);
    }
}
