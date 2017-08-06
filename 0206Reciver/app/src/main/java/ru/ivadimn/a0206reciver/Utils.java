package ru.ivadimn.a0206reciver;

import java.util.Random;

/**
 * Created by vadim on 27.07.2017.
 */

public class Utils {
    private static final Random RANDOM = new Random();
    private static final int MAX_SUFFIX_LENGHT = 5;

    public static String getFileName() {
        String suffix = String.valueOf(RANDOM.nextInt(Character.MAX_VALUE));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX_SUFFIX_LENGHT - suffix.length(); i++) {
            sb.append("0");
        }
        sb.append(suffix + "." + String.valueOf(RANDOM.nextInt(255)));
        return "file" + sb.toString();
    }

    public static int getFileSize() {
        return RANDOM.nextInt(100);
    }
}
