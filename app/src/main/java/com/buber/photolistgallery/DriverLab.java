package com.buber.photolistgallery;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Than on 8/29/2017.
 */

public class DriverLab {
    private static DriverLab sDriverLab;
    private List<Driver> mDrivers;

    public static DriverLab get(Context context) {
        if (sDriverLab == null) {
            sDriverLab = new DriverLab(context);
        }
        return sDriverLab;
    }

    private DriverLab(Context context) {
        Random r = new Random();
        int rand;
        String fname;
        String lname;
        String city = "Boston";
        ArrayList<String> firstNames = new ArrayList<String>();
        ArrayList<String> lastNames = new ArrayList<String>();

        firstNames.add("Kitty");
        firstNames.add("Summer");
        firstNames.add("Jasmine");
        firstNames.add("Baby");
        firstNames.add("Bunny");
        firstNames.add("Sparkle");
        firstNames.add("Cracky");
        firstNames.add("Stormy");
        firstNames.add("Savannah");
        firstNames.add("Crabby");

        lastNames.add("Berry");
        lastNames.add("Blacky");
        lastNames.add("Sanchez");
        lastNames.add("Juggs");
        lastNames.add("Sweet");
        lastNames.add("Honey");
        lastNames.add("Cheese");
        lastNames.add("Louder");
        lastNames.add("Precious");
        lastNames.add("Mother");

        mDrivers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Driver driver = new Driver();
            rand = r.nextInt(10);
            fname = firstNames.get(rand);
            rand = r.nextInt(10);
            lname = lastNames.get(rand);
            driver.setStageName(fname + " " + lname);
            rand = r.nextInt(10);
            driver.setAge(28 - rand);
            rand = r.nextInt(2000);
            driver.setDistance((float)rand / 100.0f);
            rand = r.nextInt(999);
            driver.setNumRatings(rand);
            rand = r.nextInt(999);
            driver.setThumbNail(rand);
            rand = r.nextInt(51);
            driver.setRating((float)rand/10.0f);
            driver.setCaption(city);
            mDrivers.add(driver);
        }
    }

    public void addDriver(Driver d) {
        mDrivers.add(d);
    }

    public List<Driver> getDrivers() {
        return mDrivers;
    }

    public Driver getDriver(int id) {
        for (Driver driver : mDrivers) {
            if (driver.getId() == id) {
                return driver;
            }
        }
        return null;
    }
}
