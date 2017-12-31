package com.example.android.quakereport;

import static com.example.android.quakereport.R.id.date;

/**
 * Created by Kokorogiannis on 28/5/2017.
 */

public class Earthquake {
    /** Magnitude of the earthquake*/
    private double magnitude;
    /** The location of the earthquake*/
    private String location;
    /** The time of the earthquake in UNIX time millisecond */
    private long timeInMilliseconds;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param mTimeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public Earthquake(double magnitude, String location, long mTimeInMilliseconds) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilliseconds = mTimeInMilliseconds;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

}
