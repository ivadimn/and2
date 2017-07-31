package ru.ivadimn.a0205threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import ru.ivadimn.a0205threads.model.Person;

/**
 * Created by vadim on 30.07.2017.
 */

public class Utils {

    private static final Random RANDOM = new Random();
    private static final int MAX_SUFFIX_LENGHT = 5;

    private static String[] fnames = {"JOHN", "AMELIA", "OLIVER", "JACK", "HARRY", "JACOB",
    "CHARLIE", "THOMAS", "OSCAR", "WILLIAM", "JAMES", "GEORGE", "OLIVIA", "EMILY", "ISLA", "JESSICA",
    "ALFIE", "JOSHUA", "NOAH", "ETHAN" };

    private static String[] snames = {"Cook", "Bush", "Calhoun", "Clifford", "Conors", "Dodson-",
            "Ferguson", "Flatcher", "Freeman", "Fulton", "Gill", "Goldman", "Hancock", "Holiday", "James", "Jenkin",
            "Lamberts", "Lawman", "Leman", "Marshman" };

    private static int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
                R.drawable.six, R.drawable.seven};

    public static Person getPerson() {
        String fname = fnames[RANDOM.nextInt(fnames.length)];
        String sname = snames[RANDOM.nextInt(snames.length)];
        String phone = "+7" + String.valueOf(Integer.MAX_VALUE);
        String email = fname + sname + "@gmail.com";
        long birthDay = RANDOM.nextLong();
        Person p = new Person(fname + " " + sname, phone, email, birthDay);
        Bitmap bmp = BitmapFactory.decodeResource(App.getInstance().getResources(), images[RANDOM.nextInt(images.length)]);
        p.setBmpPhoto(bmp);
        return  p;
    }


}
