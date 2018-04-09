package com.buber.photolistgallery;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Than on 8/29/2017.
 */

public class DriverLab {
    private static final String TAG = "DriverLab";
    private static DriverLab sDriverLab;
    private List<Driver> mDrivers;
    private ArrayList<String> mPhotoPageUrls = new ArrayList<String>();
    public static final int USE_BUBER_SERVER = 0;
    private String[] urlList1 = {
            "https://b.thumbs.redditmedia.com/KfAezmfpfhBT_NVTVQrSDvzQg9b1-WW-BoiWDbrSX3s.jpg",
            "https://i.redd.it/qip4hbse50yz.jpg",
            "https://i.redd.it/n4jojlz2vrxz.jpg",
            "https://i.redd.it/oxvwne7muywz.jpg",
            "https://i.redd.it/jscodlkscswz.jpg",
            "https://i.redd.it/pdi50wfb08sz.jpg",
            "https://i.redd.it/rljvflhqb0sz.jpg"
    };
    private String[] urlList2 = {
            "https://b.thumbs.redditmedia.com/Fet1jpLaR0loN_FX3BVB5lnCnJ4uO6QpgCODNCTDn9U.jpg",
            "https://i.redd.it/jzyq9v7s2saz.jpg",
            "https://i.redd.it/cj8f5mmxszaz.jpg",
            "https://i.redd.it/5nd233ywgldz.jpg",
            "https://i.redd.it/1h63teutddgz.jpg",
            "https://i.redd.it/veg9l5fofcjz.jpg",
            "https://i.redd.it/zw7n3gyleqkz.jpg",
            "https://i.redd.it/t4ajudq03ppz.jpg"
    };
    private String[] urlList3 = {
            "https://b.thumbs.redditmedia.com/e8IJLYTI2ws2PMVoEXpUn1aGCsEx4QFjiQggZItEsdQ.jpg",
            "https://i.redd.it/zjod12fxd5701.jpg",
            "https://i.redd.it/977dlauqes501.jpg",
            "https://i.redd.it/sbhpngkkpk401.jpg",
            "https://i.redd.it/rv0znswwbk401.jpg",
            "https://i.redd.it/idmv97zve5301.jpg",
            "https://i.redd.it/6t5rhyhshy201.jpg",
            "https://i.redd.it/b3fjjiircl101.jpg"
    };

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
        String thumbUrl = "https://i.imgur.com/d5tqW7o.jpg";
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
        firstNames.add("Sweaty");

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
        lastNames.add("Longsnapper");
        Log.d(TAG, "Start DriverLab");
        mDrivers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Driver driver = new Driver();
            rand = r.nextInt(11);
            fname = firstNames.get(rand);
            rand = r.nextInt(11);
            lname = lastNames.get(rand);
            driver.setStageName(fname + " " + lname);
            rand = r.nextInt(10);
            driver.setAge(28 - rand);
            rand = r.nextInt(2000);
            driver.setDistance((float)rand / 100.0f);
            rand = r.nextInt(999);
            driver.setNumRatings(rand);
            rand = r.nextInt(51);
            driver.setRating((float)rand/10.0f);
            driver.setCity(city);
            rand = r.nextInt(3);
            driver.setTestInt(rand);
            if (rand == 0) {
                setTestUrlArray(urlList1, driver);
                // The first json call will return a list of thumb urls and medium home pic urls.
                // when you click the List it will load the medium home pic urls and then download the list of medium urls.
            } else if (rand == 1) {
                setTestUrlArray(urlList2, driver);
            } else {
                setTestUrlArray(urlList3, driver);
            }
            mDrivers.add(driver);
            // each d should have it's own list of urls. Until you get ur server setup, use a random mix
            // of 5 ireddit's. Always default to the PLACEHOLDER 10, but then over write it with the random
            // when it's ready.
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

    private void setTestUrlArray(String[] ar, Driver d, Random r) {
        String[] urlArray = new String[ar.length-1];
        String homePicUrl = "";
        int j = 0;
        for (String url : ar) {
            if (j == 0) {
                d.setThumbUrl(url);
                Log.d(TAG, "setTestUrlArray(): " + url);
            } else {
                urlArray[j-1] = url;
            }
            if (j == 1) {
                homePicUrl = url;
            }
            j++;
        }
        shuffleArray(urlArray, r);
        swapArrayIndex(urlArray, homePicUrl);
        d.setImageUrlList(urlArray);
    }

    private void setTestUrlArray(String[] ar, Driver d) {
        String[] urlArray = new String[ar.length-1];
        int j = 0;
        for (String url : ar) {
            if (j == 0) {
                d.setThumbUrl(url);
                Log.d(TAG, "setTestUrlArray(): thumb = " + url);
            } else if (j == 1) {
                d.setHomeImageUrl(url);
                Log.d(TAG, "setTestUrlArray(): homeImage = " + url);
            } else {
                break;
            }
            j++;
        }
    }

    // Implementing Fisher–Yates shuffle
    private void shuffleArray(String[] ar, Random rnd) {
        int index;
        String a;
        for (int i = ar.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            // Simple swap
            a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void swapArrayIndex(String[] ar, String swapString) {
        int swapIndex = -1;
        String tempUrl = "";
        int i = 0;
        if (swapString == "") {
            return;
        }

        for (String url : ar)
        {
            if (url == swapString) {
                swapIndex = i;
                tempUrl = ar[0];
                break;
            }
            i++;
        }
        if (swapIndex > -1) {
            ar[swapIndex] = tempUrl;
            ar[0] = swapString;
        }
    }

    public String[] getTestUrlList(int listNum) {
        String[] retArr;
        switch (listNum) {
            case 0:
                retArr = urlList1;
                break;
            case 1:
                retArr = urlList2;
                break;
            case 2:
                retArr = urlList3;
                break;
            default:
                retArr = urlList1;
                break;
        }
        return retArr;
    }
}

