package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * MoodData is the class which holds all relevant fields and methods of a Mood.
 * MoodObjects are immutable //Todo: Sachen deswegen final machen?
 *
 *
 * Created by Jannika
 */

public class MoodData {
    private static final Logger log = LogManager.getLogger(MoodData.class);
    //variable for auto incrementation of MoodID
    private static int id = 0;

    private final String name;
    private final String description;
    private final int moodID;
    private final LocalDateTime timeStamp; //Todo: which time format is good? Sys-Time as a default value on object creation
    private final int activityLevel; //Todo: range from -10 to 10 useful?
    private final int moodValue; //Todo: range from -10 to 10 useful?

    //construction for creating specific moodID
    public MoodData(int moodID, String name, String description , LocalDateTime timeStamp, int activityLevel, int moodValue) {
        this.name = name;
        this.description = description;
        this.moodID = moodID;
        this.timeStamp = timeStamp;
        this.activityLevel = activityLevel;
        this.moodValue = moodValue;
    }

    //standard Constructor with auto generated MoodID
    public MoodData(String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue) {
        this.name = name;
        this.description = description;
        this.moodID = id++;
        this.timeStamp = timeStamp;
        this.activityLevel = activityLevel;
        this.moodValue = moodValue;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMoodID() {
        return moodID;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getMoodValue() {
        return moodValue;
    }
    
    @Override
    public String toString() {
        return "MoodData{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", moodID=" + moodID +
                ", timeStamp=" + timeStamp.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
                ", activityLevel=" + activityLevel +
                ", moodValue=" + moodValue +
                '}';
    }
}